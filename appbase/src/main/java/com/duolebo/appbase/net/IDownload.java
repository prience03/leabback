package com.duolebo.appbase.net;

public interface IDownload {

	public void download(String fromUrl, String toPath, IDownloadListener listener);
	public void download(String fromUrl, IDownloadListener listener);
	public void cancel();
	
	//TODO:
	//public void pause();
	//public void resume();
	
	public interface IDownloadListener {
		public void onDownloadProgress(long totalSize, long downloadedSize);
		public void onDownloadCompleted(String toPath);
		public void onError(int code, String message);
	}
}
