/**
 * 
 */
package com.duolebo.appbase;

import android.os.Handler;
import android.os.Message;

import com.duolebo.appbase.net.HttpRequestTask;
import com.duolebo.appbase.utils.Log;

/**
 * @author zlhl
 * @date 2014年2月19日
 */
public class AppBaseHandler extends Handler {
	
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	private final static String TAG = "AppBaseHandler";
	private IAppBaseCallback callback;

	public AppBaseHandler(IAppBaseCallback callback) {
		this.callback = callback;
	}

	@Override
	public void handleMessage(Message msg) {
		
		switch (msg.what) {
		case SUCCESS:
		case HttpRequestTask.SUCCESS:
			if (msg.obj instanceof IProtocol) {
				IProtocol protocol = (IProtocol) msg.obj;

				if (protocol.succeed()) {
					callback.onProtocolSucceed(protocol);
				} else {
					Log.d(TAG, "A protocol error occured. protocol:" + protocol.getClass().getSimpleName());
					callback.onProtocolFailed(protocol);
				}
			}
			break;

		case FAIL:
		case HttpRequestTask.FAIL:
			if (msg.obj instanceof IProtocol) {
				IProtocol protocol = (IProtocol) msg.obj;
				Log.d(TAG, "A http error occured. protocol:" + protocol.getClass().getSimpleName() + " " + protocol.statusCode());
				callback.onHttpFailed((IProtocol) msg.obj);
			}
			break;

		default:
			break;
		}

		super.handleMessage(msg);
	}
}
