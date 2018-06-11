package com.duolebo.appbase.net;

import android.os.Environment;
import android.os.Looper;

import com.duolebo.appbase.utils.StorageUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDownload extends Thread implements IDownload {

	private FileDownloadThread mFileDownloadThread;

	@Override
	public void download(final String fromUrl, final String toPath,
			final IDownloadListener listener) {
		if (listener == null || fromUrl == null || fromUrl.trim() == "") {
			return;
		}

		mFileDownloadThread = new FileDownloadThread();
		mFileDownloadThread.setListener(listener);
		mFileDownloadThread.setFromUrl(fromUrl);
		
		String fileName = "";
		String fileDirectoryPath = "";

		if (toPath == null || toPath.trim().equals("")) {
			int fileNameStart1 = fromUrl.lastIndexOf("/");
			int fileNameStart2 = fromUrl.lastIndexOf("\\");
			if (fileNameStart2 > fileNameStart1) {
				fileNameStart1 = fileNameStart2;
			}

			fileName = fromUrl.substring(fileNameStart1 + 1);
			fileDirectoryPath = Environment.getExternalStorageDirectory().getPath();
		} else {
			int fileNameStart1 = toPath.lastIndexOf("/");
			int fileNameStart2 = toPath.lastIndexOf("\\");
			if (fileNameStart2 > fileNameStart1) {
				fileNameStart1 = fileNameStart2;
			}

			fileName = toPath.substring(fileNameStart1 + 1);
			fileDirectoryPath = toPath.substring(0, fileNameStart1);
		}

		if (null == fileDirectoryPath || fileDirectoryPath.trim() == ""
				|| !fileName.contains(".")) {
			listener.onError(-1, "fileDirectoryPath or fileName didn't match");
			return;
		}

		String fileUrl = fileDirectoryPath + File.separator + fileName;
		
		mFileDownloadThread.setFileDirectoryPath(fileDirectoryPath);
		mFileDownloadThread.setFileUrl(fileUrl);
		mFileDownloadThread.start();
	}

	@Override
	public void download(String fromUrl, IDownloadListener listener) {
		this.download(fromUrl, null, listener);
	}

	@Override
	public void cancel() {
		if (null != mFileDownloadThread) {
			mFileDownloadThread.setCancel(true);
		}
	}
	
	private static class FileDownloadThread extends Thread {
	    
	    private IDownloadListener listener;
	    private String fromUrl;
	    private String fileUrl;
	    private String fileDirectoryPath;
	    
	    private boolean mIsCancel = false;
	    
	    InputStream inputStream = null;
	    FileOutputStream fileOutputStream = null;

		public void setListener(IDownloadListener listener) {
            this.listener = listener;
        }

        public void setFromUrl(String fromUrl) {
            this.fromUrl = fromUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public void setFileDirectoryPath(String fileDirectoryPath) {
            this.fileDirectoryPath = fileDirectoryPath;
        }

        public void setCancel(boolean cancel) {
        	mIsCancel = cancel;
        }
        
        @Override
		public void run() {
			Looper.prepare();
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(fromUrl);
			HttpResponse httpResponse;

			try {
				httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				long contentLength = httpEntity.getContentLength();

				if (mIsCancel) {
					throw new StopException();
				}

				if (!StorageUtils.isEnoughForDownload(httpEntity.getContentLength())) {
					listener.onError(-1, "");
					return;
				}

				inputStream = httpEntity.getContent();
				if (inputStream != null) {
					File fileDirectory = new File(fileDirectoryPath);
					if (!fileDirectory.exists()) {
						fileDirectory.mkdirs();
					}

					fileOutputStream = new FileOutputStream(fileUrl);

					byte[] buf = new byte[1024];
					int len = -1;
					long downloadLength = 0;
					while ((len = inputStream.read(buf)) != -1) {
						if (mIsCancel) {
							throw new StopException();
						}

						fileOutputStream.write(buf, 0, len);
						downloadLength += len;
						listener.onDownloadProgress(contentLength, downloadLength);
					}

					fileOutputStream.flush();


					listener.onDownloadCompleted(fileUrl);
				}
			} catch (StopException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				listener.onError(-1, "exception happend " + e.toString());
			} finally {
				try {

					if (fileOutputStream != null) {
						fileOutputStream.close();
					}

					if (inputStream != null) {
						inputStream.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		
		}

		class StopException extends Exception {

		}
	}

}
