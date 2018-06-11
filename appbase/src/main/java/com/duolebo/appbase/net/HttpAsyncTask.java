package com.duolebo.appbase.net;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import com.duolebo.appbase.utils.Log;

public abstract class HttpAsyncTask<Params, Progress, Result> {
	private static final int CORE_POOL_SIZE = 5;
	private static final int KEEP_ALIVE = 10;
	private static final String LOG_TAG = "HttpAsyncTask";
	private static final int MAXIMUM_POOL_SIZE = 10;
	private static final int MESSAGE_POST_CANCEL = 3;
	private static final int MESSAGE_POST_PROGRESS = 2;
	private static final int MESSAGE_POST_RESULT = 1;
	private static RejectedExecutionHandler handle = new ThreadPoolExecutor.DiscardOldestPolicy();
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable paramRunnable) {
			return new Thread(paramRunnable, "HttpAsyncTask#"
					+ this.mCount.getAndIncrement());
		}
	};
	private static final LinkedBlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(10);
	private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			sWorkQueue, sThreadFactory, handle);
	private static final InternalHandler sHandler = new InternalHandler();;
	private final FutureTask<Result> mFuture;
	private volatile Status mStatus = Status.PENDING;
	private final WorkerRunnable<Params, Result> mWorker;
	
	
	public HttpAsyncTask(){
		mWorker = new WorkerRunnable<Params, Result>() {
			public Result call() throws Exception {
				Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
				return doInBackground(mParams);
			}
		};

		mFuture = new FutureTask<Result>(mWorker) {
			@SuppressWarnings("unchecked")
			@Override
			protected void done() {
				Message message;
				Result result = null;

				try {
					result = get();
				} catch (InterruptedException e) {
					Log.w(LOG_TAG, "HttpAsyncTask InterruptedException occured.");
				} catch (ExecutionException e) {
					throw new RuntimeException(
							"An error occured while executing doInBackground()",
							e.getCause());
				} catch (CancellationException e) {
					message = sHandler.obtainMessage(MESSAGE_POST_CANCEL,
							new HttpAsyncTaskResult<Result>(HttpAsyncTask.this,
									(Result[]) null));
					message.sendToTarget();
					return;
				} catch (Throwable t) {
					throw new RuntimeException(
							"An error occured while executing "
									+ "doInBackground()", t);
				}

				message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
						new HttpAsyncTaskResult<Result>(HttpAsyncTask.this, result));
				message.sendToTarget();
			}
		};
	}

	public static void clearQueue() {
		sWorkQueue.clear();
	}

	private void finish(Result paramResult) {
		onPostExecute(paramResult);
		this.mStatus = Status.FINISHED;
	}

	public final boolean cancel(boolean paramBoolean) {
		return this.mFuture.cancel(paramBoolean);
	}

	protected abstract Result doInBackground(Params[] paramArrayOfParams);

	public final HttpAsyncTask<Params, Progress, Result> execute(
			Params... params) {

		if (mStatus != Status.PENDING) {
			switch (mStatus) {
			case RUNNING:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task is already running.");
			case FINISHED:
				throw new IllegalStateException("Cannot execute task:"
						+ " the task has already been executed "
						+ "(a task can be executed only once)");
			}
		}

		mStatus = Status.RUNNING;

		onPreExecute();

		mWorker.mParams = params;
		sExecutor.execute(mFuture);

		return this;

	}

	public final Result get() throws InterruptedException, ExecutionException {
		return this.mFuture.get();
	}

	public final Result get(long paramLong, TimeUnit paramTimeUnit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return this.mFuture.get(paramLong, paramTimeUnit);
	}

	public final Status getStatus() {
		return this.mStatus;
	}

	public final boolean isCancelled() {
		return this.mFuture.isCancelled();
	}

	protected void onCancelled() {
	}

	protected void onPostExecute(Result paramResult) {
	}

	protected void onPreExecute() {
	}

	protected void onProgressUpdate(Progress[] paramArrayOfProgress) {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final void publishProgress(Progress[] paramArrayOfProgress) {
		sHandler.obtainMessage(2,
				new HttpAsyncTaskResult(this, paramArrayOfProgress))
				.sendToTarget();
	}

	@SuppressWarnings("rawtypes")
	private static class HttpAsyncTaskResult<Data> {
		final Data[] mData;
		final HttpAsyncTask mTask;

		HttpAsyncTaskResult(HttpAsyncTask task, Data... data) {
			this.mTask = task;
			this.mData = data;
		}
	}

	@SuppressWarnings("rawtypes")
	private static class InternalHandler extends Handler {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			HttpAsyncTaskResult result = (HttpAsyncTaskResult) msg.obj;
			switch (msg.what) {
			case MESSAGE_POST_RESULT:
				// There is only one result
				result.mTask.finish(result.mData[0]);
				break;
			case MESSAGE_POST_PROGRESS:
				result.mTask.onProgressUpdate(result.mData);
				break;
			case MESSAGE_POST_CANCEL:
				result.mTask.onCancelled();
				break;
			}
		}
	}

	public enum Status {

		/**
		 * Indicates that the task has not been executed yet.
		 */
		PENDING,
		/**
		 * Indicates that the task is running.
		 */
		RUNNING,
		/**
		 * Indicates that {@link AsyncTask#onPostExecute} has finished.
		 */
		FINISHED,

	}

	private static abstract class WorkerRunnable<Params, Result> implements
			Callable<Result> {
		Params[] mParams;
	}
}