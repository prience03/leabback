package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;

import com.duolebo.appbase.prj.bmtv.model.GetInitIndexData;

import java.util.Map;

/**
 *   进入界面初始化时调用
 * @author wang
 * created at 2017/3/30 15:55
 */
public class GetInitIndex extends ProtocolBase {

	private String subsize = null;
	private String menuNum = null;

	private String menutypearr = null ;
	private String isIndex = null ;
	private String menuid = null ;

	private GetInitIndexData data = null;

	public GetInitIndex(Context context, IProtocolConfig config) {
		super(context, config);
	}

	public GetInitIndex with(String subsize,String menuNum,String isIndex) {
		this.subsize = subsize;
		this.menuNum = menuNum ;
		this.isIndex = isIndex;
		data = new GetInitIndexData(true);
		return this;
	}

	public GetInitIndex with(String subsize,String menuNum,String menutypearr,String isIndex ,String menuid) {
		this.subsize = subsize;
		this.menuNum = menuNum ;
		this.menutypearr = menutypearr ;
		this.isIndex = isIndex ;
		this.menuid = menuid ;
		data = new GetInitIndexData(false);
		return this;
	}



	@Override
	protected void prepareProtocolBody(Map<String, String> body) {
		body.put("subsize",subsize);
		body.put("menunum",menuNum);

		if (null != menuid)
			body.put("menuid", menuid);
		if (null != isIndex )
			body.put("isIndex", isIndex);
		if(null != menutypearr )
			body.put("menutypearr", menutypearr);


	}

	@Override
	protected String prepareProtocolRequestKey() {
		return "InitIndex";
	}

	@Override
	public GetInitIndexData getData() {
		return data;
	}

	@Override
	public long getExpire() {
		return -1;
//		return  1000 * 60 * 10  + System.currentTimeMillis();
	}
}
