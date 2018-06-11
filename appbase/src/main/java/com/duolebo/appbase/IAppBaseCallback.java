/**
 * 
 */
package com.duolebo.appbase;

/**
 * @author zlhl
 * @date 2014年2月19日
 */
public interface IAppBaseCallback {
	public void onProtocolSucceed(IProtocol protocol);
	public void onProtocolFailed(IProtocol protocol);
	public void onHttpFailed(IProtocol protocol);
}
