package com.duolebo.appbase.log;

public interface ILog {

	public void i(String tag, String message);

	public void w(String tag, String message);

	public void d(String tag, String message);
	
	public void v(String tag, String message);
	
	public void e(String tag, String message);
}
