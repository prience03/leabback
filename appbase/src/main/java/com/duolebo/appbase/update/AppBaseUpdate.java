package com.duolebo.appbase.update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.duolebo.appbase.AppBaseHandler;
import com.duolebo.appbase.IAppBaseCallback;
import com.duolebo.appbase.IModel;
import com.duolebo.appbase.IProtocol;
import com.duolebo.appbase.net.FileDownload;
import com.duolebo.appbase.net.IDownload.IDownloadListener;
import com.duolebo.appbase.utils.Log;
import com.duolebo.appbase.utils.MD5sum;
import com.duolebo.appbase.utils.StorageUtils;

import java.io.File;

/**
 * @author 海迪
 * @date 2014年4月14日
 */
public class AppBaseUpdate implements IAppBaseCallback {
	private final static String TAG = AppBaseUpdate.class.getSimpleName();

	private IProtocol protocol;
	private IUpdateModel updateModel;
	private Context mContext;
	private AppBaseHandler handler;
	private OnAppBaseUpdateOperation op;
	private String packageName = "";
	
	SharedPreferences sharedPreferences = null;

	private String directoryPath = null;
	private String apkFileName = null;
	private String md5 = "";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// Bundle bundle = msg.getData();
			// String toPath = bundle.getString("toPath");
			if (null != op) {
				if (op.shouldDownloadBeforePrompt()) {
//					op.onCheckedPrompt(updateModel);
					if(null != downLoadListener) {
						downLoadListener.completed();
					}
				} else {
					if (!op.shallOverrideInstalling(directoryPath, apkFileName)) {
						install(directoryPath, apkFileName);
					}
				}
			} else {
				install(directoryPath, apkFileName);
			}
		}
	};

	public AppBaseUpdate(Context context, IProtocol protocol) {
		this.mContext = context;
		this.protocol = protocol;
		handler = new AppBaseHandler(this);
		sharedPreferences = mContext.getSharedPreferences("UPDATE_SP", mContext.MODE_PRIVATE);
	}

	public void check() {
		if (null == protocol)
			return;
		protocol.execute(handler);
	}

	public void downloadApk() {
		if (null == updateModel) {
			return;
		}

		downloadApk(updateModel.getDownloadUrl());
	}

	public void downloadApk(IDownloadListener listener) {
		if (null == updateModel) {
			return;
		}

		downloadApk(updateModel.getDownloadUrl(), listener);
	}

	public void downloadApk(String url) {
		downloadApk(url, null);
	}

	public void downloadApk(String url, IDownloadListener listener) {
		if (url == null || url.trim() == "" || updateModel == null) {
			return;
		}
		
		if(TextUtils.isEmpty(packageName)) {
			packageName = mContext.getPackageName();
		}
		
		if (StorageUtils.isSdCardWrittenable())
			directoryPath = Environment.getExternalStorageDirectory().getPath() + File.separator + packageName ;
		else
			directoryPath = mContext.getCacheDir().getPath();


		apkFileName = packageName + "_" + updateModel.getVersion() + ".apk";
		String toPath = directoryPath + File.separator + apkFileName;

		File file = new File(toPath);
		if(file.isFile()) {
			String update_version = sharedPreferences.getString("update_version_"+packageName, "");
			if(update_version.equals(updateModel.getVersion())) {
				sendInstallMessage(toPath, file);
				return;
			}
		}
		
		if (listener == null) {
			listener = new IDownloadListener() {
				@Override
				public void onDownloadProgress(long totalSize,
						long downloadedSize) {
					if(null != downLoadListener) {
						downLoadListener.progress(totalSize,
						downloadedSize);
					}

					if(null != op) {
					    op.downloadProgress(totalSize,downloadedSize);
					}
				}

				@Override
				public void onDownloadCompleted(String toPath) {
					try {
						File file = new File(toPath);
						if (!file.exists()) {
							throw new Exception("file not exists!");

						}
						if (!file.isFile()) {
							throw new Exception("not a apk file!");
						}

						Editor editor = sharedPreferences.edit();
						editor.putString("update_version"+packageName, updateModel.getVersion());
						editor.commit();
						
						sendInstallMessage(toPath, file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(int code, String message) {
					// op.onStorageSpaceIsFull();
					android.util.Log.e("AppBaseUpdate", "onError, code=" + code + ", message=" + message);
				}
			};
		}
		
		//删除旧版本apk
		try {
			File directoryFile = new File(directoryPath);
			if(directoryFile.isDirectory()) {
				File[] files = directoryFile.listFiles();
				if(directoryPath.contains(packageName)) {
					for(File f: files) {
						String fileName = f.getName();
						if(null != fileName && fileName.contains(".apk") && f.isFile()) {
							f.delete();
						}
					}
				} else {
					int fileNameStart1 = url.lastIndexOf("/");
					int fileNameStart2 = url.lastIndexOf("\\");
					if (fileNameStart2 > fileNameStart1) {
						fileNameStart1 = fileNameStart2;
					}

					String fromfileName = url.substring(fileNameStart1 + 1);

					for(File f: files) {
						String fileName = f.getName();
						if(null != fileName && (fileName.contains(packageName) || fileName.contains(fromfileName))) {
							f.delete();
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		new FileDownload().download(url, toPath, listener);
	}
	
	@SuppressLint("NewApi")
	private void sendInstallMessage(String toPath, File file) {
		file.setReadable(true, false);
		file.setWritable(true, false);
		file.setExecutable(true, false);
		if(md5==null || "".equals(md5.trim()) || "null".equals(md5.trim())) {
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("toPath", toPath);
			msg.setData(bundle);

			mHandler.sendMessage(msg);
		} else {
			String fileMd5 = MD5sum.md5sum(file);
			md5 = md5.trim().toUpperCase();
			fileMd5 = fileMd5.trim().toUpperCase();
			if(md5.equals(fileMd5)) {
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("toPath", toPath);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			} else {
				// remove file if md5 does not match.
				file.delete();
			}
		}
	}

	public void install() {
		install(null, null);
	}

	public void install(String dirPath, String fileName) {
		if (dirPath == null || fileName == null) {
			dirPath = directoryPath;
			fileName = apkFileName;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(dirPath, fileName)),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);

	}

	@Override
	public void onProtocolSucceed(IProtocol protocol) {
		IModel data = protocol.getData();
		if (!(data instanceof IUpdateModel)) {
			Log.d(TAG, "Update check failed, data is not a IUpdateModel!!!!");
			onHttpFailed(protocol);
			return;
		}

		updateModel = (IUpdateModel) data;

		if (null != updateModel && updateModel.hasUpdate(mContext)) {
			md5 = updateModel.getMd5();
			if(null != op) {
			    op.needUpdate(updateModel,this);
			}
//
//			if (null != op) {
//				if (op.shouldDownloadBeforePrompt()) {
//					downloadApk();
//				} else {
//					op.onCheckedPrompt(updateModel);
//				}
//			} else {
//				downloadApk();
//			}

		}
	}

	@Override
	public void onProtocolFailed(IProtocol protocol) {
		onHttpFailed(protocol);
	}

	@Override
	public void onHttpFailed(IProtocol protocol) {
		
	}

	// set interfaces, listeners, etc.;
	public interface OnAppBaseUpdateOperation {
		public void onCheckedPrompt(IUpdateModel updateModel);

		// return true if you want to takeover the installing process.
		public boolean shallOverrideInstalling(String dirPath,
				String apkFileName);

		/**
		 * weather download first or prompt first is controlled by client. true:
		 * download first false: prompt first
		 * 
		 * @return boolean
		 */
		public boolean shouldDownloadBeforePrompt();

		// 储存空间已满
		public void onStorageSpaceIsFull();

		void needUpdate(IUpdateModel updateModel, AppBaseUpdate appBaseUpdate);

		void downloadProgress(long totalSize, long downloadedSize);

//		public void progress(long progress);
	}

	public void setOnAppBaseUpdateOperation(OnAppBaseUpdateOperation op) {
		this.op = op;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public String getRealPath() {
		return directoryPath + File.separator + apkFileName;
	}

	public String getApkFileName() {
		return apkFileName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setDownLoadListener(DownLoadListener downLoadListener) {
		this.downLoadListener = downLoadListener;
	}

	DownLoadListener downLoadListener;

	public interface DownLoadListener {
		void completed();
		void progress(long totalSize, long downloadedSize);
	}
}
