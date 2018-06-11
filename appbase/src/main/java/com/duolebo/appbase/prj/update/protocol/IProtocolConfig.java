package com.duolebo.appbase.prj.update.protocol;

public interface IProtocolConfig {

    /**
     * Url for update
     * 
     * @return url
     */
	public String getCheckUpdateProtocolUrl();
	
	/**
	 * Result format control.
	 * 
	 * @return IProtocol.PROTOCOL_RESULT_FORMAT_JSON 
	 *         or IProtocol.PROTOCOL_RESULT_FORMAT_XML.
	 */
	public int getResultFormat();

}
