package com.duolebo.appbase.update;

import android.content.Context;

public interface IUpdateModel {
	public boolean hasUpdate(Context context);
	public boolean isMandatory();
	public String getMessage();
	public String getVersion();
	public String getDownloadUrl();
	public String getMd5();
}
