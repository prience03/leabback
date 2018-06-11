package com.duolebo.appbase.prj.bmtv.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.duolebo.appbase.AppBaseHandler;
import com.duolebo.appbase.IAppBaseCallback;
import com.duolebo.appbase.IProtocol;
import com.duolebo.appbase.prj.Protocol;
import com.duolebo.appbase.prj.bmtv.model.BatchCheckUpdateData;
import com.duolebo.appbase.prj.bmtv.model.GetADsData;
import com.duolebo.appbase.prj.bmtv.model.GetAboutHtmlData;
import com.duolebo.appbase.prj.bmtv.model.GetAppDetailData;
import com.duolebo.appbase.prj.bmtv.model.GetAppDownloadUrlData;
import com.duolebo.appbase.prj.bmtv.model.GetAreaListData;
import com.duolebo.appbase.prj.bmtv.model.GetChannelListData;
import com.duolebo.appbase.prj.bmtv.model.GetContentDetailData;
import com.duolebo.appbase.prj.bmtv.model.GetContentListData;
import com.duolebo.appbase.prj.bmtv.model.GetContentTVPlayUrlData;
import com.duolebo.appbase.prj.bmtv.model.GetHotWordsData;
import com.duolebo.appbase.prj.bmtv.model.GetMenuData;
import com.duolebo.appbase.prj.bmtv.model.GetSaleDetailData;
import com.duolebo.appbase.prj.bmtv.model.GetSaleQRCodeData;
import com.duolebo.appbase.prj.bmtv.model.ReportStatusData;
import com.duolebo.appbase.prj.bmtv.model.ReportStatusData.OperType;
import com.duolebo.appbase.prj.bmtv.model.SearchContentListData;
import com.duolebo.appbase.prj.bmtv.model.TVGetTokenData;
import com.duolebo.appbase.prj.bmtv.model.TVLoginData;
import com.duolebo.appbase.prj.bmtv.model.TVRegistrationData;
import com.duolebo.appbase.prj.bmtv.protocol.BatchCheckUpdate;
import com.duolebo.appbase.prj.bmtv.protocol.GetADs;
import com.duolebo.appbase.prj.bmtv.protocol.GetAboutHtml;
import com.duolebo.appbase.prj.bmtv.protocol.GetAppDetail;
import com.duolebo.appbase.prj.bmtv.protocol.GetAppDownloadUrl;
import com.duolebo.appbase.prj.bmtv.protocol.GetAreaList;
import com.duolebo.appbase.prj.bmtv.protocol.GetChannelList;
import com.duolebo.appbase.prj.bmtv.protocol.GetContentDetail;
import com.duolebo.appbase.prj.bmtv.protocol.GetContentList;
import com.duolebo.appbase.prj.bmtv.protocol.GetContentTVPlayUrl;
import com.duolebo.appbase.prj.bmtv.protocol.GetHotWords;
import com.duolebo.appbase.prj.bmtv.protocol.GetMenu;
import com.duolebo.appbase.prj.bmtv.protocol.GetSaleDetail;
import com.duolebo.appbase.prj.bmtv.protocol.GetSaleQRCode;
import com.duolebo.appbase.prj.bmtv.protocol.IProtocolConfig;
import com.duolebo.appbase.prj.bmtv.protocol.ReportStatus;
import com.duolebo.appbase.prj.bmtv.protocol.SearchContentList;
import com.duolebo.appbase.prj.bmtv.protocol.TVGetToken;
import com.duolebo.appbase.prj.bmtv.protocol.TVLogin;
import com.duolebo.appbase.prj.bmtv.protocol.TVRegistration;

public class Bmtv implements IAppBaseCallback {

    private static final String TAG = "Bmtv";

    private Context context;
    private IProtocolConfig config;
    private AppBaseHandler handler;

    public Bmtv(Context context, IProtocolConfig config) {
        this.context = context;
        this.config = config;
        handler = new AppBaseHandler(this);
    }

    public interface Callback {
        public void onAuth(boolean succeed);

        public void onRegister(boolean succeed, TVRegistrationData data);

        public void onGetToken(boolean succeed, TVGetTokenData data);

        public void onLogin(boolean succeed, TVLoginData data);

        public void onGetMenu(boolean succeed, GetMenuData data);

        public void onGetContentList(boolean succeed, GetContentListData data);

        public void onGetAppDetail(boolean succeed, GetAppDetailData data);

        public void onGetAppDownloadUrl(boolean succeed, GetAppDownloadUrlData data);

        public void onGetContentDetail(boolean succeed, GetContentDetailData data);

        public void onGetContentTVPlayUrl(boolean succeed, GetContentTVPlayUrlData data);

        public void onGetSaleDetail(boolean succeed, GetSaleDetailData data);

        public void onGetSaleQRCode(boolean succeed, GetSaleQRCodeData data);

        public void onSearchContentList(boolean succeed, SearchContentListData data);

        public void onGetHotWords(boolean succeed, GetHotWordsData data);

        public void onGetChannelList(boolean succeed, GetChannelListData data);

        public void onGetADs(boolean succeed, GetADsData data);

        public void onGetAboutHtml(boolean succeed, GetAboutHtmlData data);

        public void onGetAreaList(boolean succeed, GetAreaListData data);

        public void onBatchCheckUpdate(boolean succeed, BatchCheckUpdateData data);

        public void onReportStatus(boolean succeed, ReportStatusData data);
    }

    public static class CallbackAdapter implements Callback {
        @Override
        public void onAuth(boolean succeed) {
        }

        @Override
        public void onRegister(boolean succeed, TVRegistrationData data) {
        }

        @Override
        public void onGetToken(boolean succeed, TVGetTokenData data) {
        }

        @Override
        public void onLogin(boolean succeed, TVLoginData data) {
        }

        @Override
        public void onGetMenu(boolean succeed, GetMenuData data) {
        }

        @Override
        public void onGetContentList(boolean succeed, GetContentListData data) {
        }

        @Override
        public void onGetAppDetail(boolean succeed, GetAppDetailData data) {
        }

        @Override
        public void onGetAppDownloadUrl(boolean succeed, GetAppDownloadUrlData data) {
        }

        @Override
        public void onGetContentDetail(boolean succeed, GetContentDetailData data) {
        }

        @Override
        public void onGetContentTVPlayUrl(boolean succeed, GetContentTVPlayUrlData data) {
        }

        @Override
        public void onGetSaleDetail(boolean succeed, GetSaleDetailData data) {
        }

        @Override
        public void onGetSaleQRCode(boolean succeed, GetSaleQRCodeData data) {
        }

        @Override
        public void onSearchContentList(boolean succeed, SearchContentListData data) {
        }

        @Override
        public void onGetHotWords(boolean succeed, GetHotWordsData data) {
        }

        @Override
        public void onGetChannelList(boolean succeed, GetChannelListData data) {
        }

        @Override
        public void onGetADs(boolean succeed, GetADsData data) {
        }

        @Override
        public void onGetAboutHtml(boolean succeed, GetAboutHtmlData data) {
        }

        @Override
        public void onGetAreaList(boolean succeed, GetAreaListData data) {
        }

        @Override
        public void onBatchCheckUpdate(boolean succeed, BatchCheckUpdateData data) {
        }

        @Override
        public void onReportStatus(boolean succeed, ReportStatusData data) {
        }
    }

    public void auth(Callback callback) {
        final Callback cb = callback;
        register(new CallbackAdapter() {

            @Override
            public void onRegister(boolean succeed, TVRegistrationData data) {
                super.onRegister(succeed, data);
                if (succeed) {
                    getToken(this);
                } else {
                    if (null != cb) cb.onAuth(false);
                }
            }

            @Override
            public void onGetToken(boolean succeed, TVGetTokenData data) {
                super.onGetToken(succeed, data);
                if (succeed) {
                    login(this);
                } else {
                    if (null != cb) cb.onAuth(false);
                }
            }

            @Override
            public void onLogin(boolean succeed, TVLoginData data) {
                super.onLogin(succeed, data);
                if (null != cb) cb.onAuth(succeed);
            }

        });
    }

    public void register(Callback callback) {
        (new TVRegistration(context, config))
                .setTag(callback)
                .execute(handler);
    }

    public void login(Callback callback) {
        (new TVLogin(context, config))
                .setTag(callback)
                .execute(handler);
    }

    public void getToken(Callback callback) {
        (new TVGetToken(context, config))
                .setTag(callback)
                .execute(handler);
    }

    private void authorize(final IProtocol protocol) {
        if (TextUtils.isEmpty(TVGetToken.getUserToken(context))) {
            Log.i(TAG, "token is empty, let's do authorization first.");
            auth(new CallbackAdapter() {
                @Override
                public void onAuth(boolean succeed) {
                    super.onAuth(succeed);
                    if (succeed) {
                        protocol.execute(handler);
                    } else {
                        onProtocolFailed(protocol);
                    }
                }
            });
        } else {
            Log.i(TAG, "we have the token, let's do the request.");
            protocol.execute(handler);
        }
    }

    public void getMenu(String menuid, Callback callback) {
        authorize((new GetMenu(context, config))
                .with(menuid)
                .setTag(callback));
    }

    public void getContentList(String contentId, Callback callback) {
        authorize((new GetContentList(context, config))
                .withContentId(contentId)
                .setTag(callback));
    }

    public void getContentList(String menuId, int pageSize, int pageNum, boolean needCells, Callback callback) {
        authorize((new GetContentList(context, config))
                .withCells(needCells)
                .withMenu(menuId)
                .withPageSize(pageSize)
                .withPageNo(pageNum)
                .setTag(callback));
    }

    public void getAppDetail(String contentId, Callback callback) {
        authorize((new GetAppDetail(context, config))
                .withContentId(contentId)
                .setTag(callback));
    }

    public void getAppDownloadUrl(String contentId, Callback callback) {
        authorize((new GetAppDownloadUrl(context, config))
                .withContentID(contentId)
                .setTag(callback));
    }

    public void getContentDetail(String contentId, Callback callback) {
        authorize((new GetContentDetail(context, config))
                .withContentIds(contentId)
                .setTag(callback));
    }

    public void getContentTVPlayUrl(String contentId, Callback callback) {
        getContentTVPlayUrl(contentId, "", callback);
    }

    public void getContentTVPlayUrl(String contentId, String episodeId, Callback callback) {
        authorize((new GetContentTVPlayUrl(context, config))
                .withContentId(contentId)
                .withEpisodeId(episodeId)
                .setTag(callback));
    }

    public void getContentTVPlayUrl(String contentId, String episodeId, boolean isHD, String tag, Callback callback) {
        authorize((new GetContentTVPlayUrl(context, config))
                .withContentId(contentId)
                .withEpisodeId(episodeId)
                .withIsHD(isHD)
                .withTag(tag)
                .setTag(callback));
    }

    public void getSaleDetail(String contentId, Callback callback) {
        authorize((new GetSaleDetail(context, config))
                .withContentId(contentId)
                .setTag(callback));
    }

    public void getSaleQRCode(String contentId, String payType, Callback callback) {
        authorize((new GetSaleQRCode(context, config))
                .withContentId(contentId)
                .withPayType(payType)
                .setTag(callback));
    }

    public void searcheContentList(String keyWord, boolean isPinYin, int pageSize, int pageNum, Callback callback) {
        authorize((new SearchContentList(context, config))
                .withKeyword(keyWord)
                .withPinYin(isPinYin ? 1 : 0)
                .withPageSize(pageSize)
                .withPageNo(pageNum)
                .setTag(callback));
    }

    public void getHotWords(Callback callback) {
        authorize((new GetHotWords(context, config))
                .setTag(callback));
    }

    public void getChannelList(Callback callback) {
        authorize((new GetChannelList(context, config))
                .setTag(callback));
    }

    public void getADs(Callback callback) {
        authorize((new GetADs(context, config))
                .setTag(callback));
    }

    public void getAboutHtml(Callback callback) {
        authorize((new GetAboutHtml(context, config))
                .setTag(callback));
    }

    public void getAreaList(int areaId, Callback callback) {
        authorize((new GetAreaList(context, config))
                .with(areaId)
                .setTag(callback));
    }

    public void batchCheckUpdate(Callback callback) {
        authorize((new BatchCheckUpdate(context, config))
                .setTag(callback));
    }

    public void reportStatus(String contentId, OperType operType, Callback callback) {
        authorize((new ReportStatus(context, config))
                .with(contentId, operType)
                .setTag(callback));
    }

    @Override
    public void onProtocolSucceed(IProtocol protocol) {
        final Callback callback = (Callback) ((Protocol) protocol).getTag();
        if (null == callback) return;

        if (protocol instanceof TVRegistration) {
            callback.onRegister(true, (TVRegistrationData) protocol.getData());
        } else if (protocol instanceof TVLogin) {
            callback.onLogin(true, (TVLoginData) protocol.getData());
        } else if (protocol instanceof TVGetToken) {
            callback.onGetToken(true, (TVGetTokenData) protocol.getData());
        } else if (protocol instanceof GetMenu) {
            callback.onGetMenu(true, (GetMenuData) protocol.getData());
        } else if (protocol instanceof GetContentList) {
            callback.onGetContentList(true, (GetContentListData) protocol.getData());
        } else if (protocol instanceof GetAppDetail) {
            callback.onGetAppDetail(true, (GetAppDetailData) protocol.getData());
        } else if (protocol instanceof GetAppDownloadUrl) {
            callback.onGetAppDownloadUrl(true, (GetAppDownloadUrlData) protocol.getData());
        } else if (protocol instanceof GetContentDetail) {
            callback.onGetContentDetail(true, (GetContentDetailData) protocol.getData());
        } else if (protocol instanceof GetContentTVPlayUrl) {
            callback.onGetContentTVPlayUrl(true, (GetContentTVPlayUrlData) protocol.getData());
        } else if (protocol instanceof GetSaleDetail) {
            callback.onGetSaleDetail(true, (GetSaleDetailData) protocol.getData());
        } else if (protocol instanceof GetSaleQRCode) {
            callback.onGetSaleQRCode(true, (GetSaleQRCodeData) protocol.getData());
        } else if (protocol instanceof SearchContentList) {
            callback.onSearchContentList(true, (SearchContentListData) protocol.getData());
        } else if (protocol instanceof GetHotWords) {
            callback.onGetHotWords(true, (GetHotWordsData) protocol.getData());
        } else if (protocol instanceof GetChannelList) {
            callback.onGetChannelList(true, (GetChannelListData) protocol.getData());
        } else if (protocol instanceof GetADs) {
            callback.onGetADs(true, (GetADsData) protocol.getData());
        } else if (protocol instanceof GetAboutHtml) {
            callback.onGetAboutHtml(true, (GetAboutHtmlData) protocol.getData());
        } else if (protocol instanceof GetAreaList) {
            callback.onGetAreaList(true, (GetAreaListData) protocol.getData());
        } else if (protocol instanceof BatchCheckUpdate) {
            callback.onBatchCheckUpdate(true, (BatchCheckUpdateData) protocol.getData());
        } else if (protocol instanceof ReportStatus) {
            callback.onReportStatus(true, (ReportStatusData) protocol.getData());
        }
    }

    @Override
    public void onProtocolFailed(IProtocol protocol) {
        final Callback callback = (Callback) ((Protocol) protocol).getTag();
        if (null == callback) return;
        if (protocol instanceof TVRegistration) {
            callback.onRegister(false, (TVRegistrationData) protocol.getData());
        } else if (protocol instanceof TVLogin) {
            callback.onLogin(false, (TVLoginData) protocol.getData());
        } else if (protocol instanceof TVGetToken) {
            callback.onGetToken(false, (TVGetTokenData) protocol.getData());
        } else if (protocol instanceof GetMenu) {
            callback.onGetMenu(false, (GetMenuData) protocol.getData());
        } else if (protocol instanceof GetContentList) {
            callback.onGetContentList(false, (GetContentListData) protocol.getData());
        } else if (protocol instanceof GetAppDetail) {
            callback.onGetAppDetail(false, (GetAppDetailData) protocol.getData());
        } else if (protocol instanceof GetAppDownloadUrl) {
            callback.onGetAppDownloadUrl(false, (GetAppDownloadUrlData) protocol.getData());
        } else if (protocol instanceof GetContentDetail) {
            callback.onGetContentDetail(false, (GetContentDetailData) protocol.getData());
        } else if (protocol instanceof GetContentTVPlayUrl) {
            callback.onGetContentTVPlayUrl(false, (GetContentTVPlayUrlData) protocol.getData());
        } else if (protocol instanceof GetSaleDetail) {
            callback.onGetSaleDetail(false, (GetSaleDetailData) protocol.getData());
        } else if (protocol instanceof GetSaleQRCode) {
            callback.onGetSaleQRCode(false, (GetSaleQRCodeData) protocol.getData());
        } else if (protocol instanceof SearchContentList) {
            callback.onSearchContentList(false, (SearchContentListData) protocol.getData());
        } else if (protocol instanceof GetHotWords) {
            callback.onGetHotWords(false, (GetHotWordsData) protocol.getData());
        } else if (protocol instanceof GetChannelList) {
            callback.onGetChannelList(false, (GetChannelListData) protocol.getData());
        } else if (protocol instanceof GetADs) {
            callback.onGetADs(false, (GetADsData) protocol.getData());
        } else if (protocol instanceof GetAboutHtml) {
            callback.onGetAboutHtml(false, (GetAboutHtmlData) protocol.getData());
        } else if (protocol instanceof GetAreaList) {
            callback.onGetAreaList(false, (GetAreaListData) protocol.getData());
        } else if (protocol instanceof BatchCheckUpdate) {
            callback.onBatchCheckUpdate(false, (BatchCheckUpdateData) protocol.getData());
        } else if (protocol instanceof ReportStatus) {
            callback.onReportStatus(false, (ReportStatusData) protocol.getData());
        }
    }

    @Override
    public void onHttpFailed(IProtocol protocol) {
        onProtocolFailed(protocol);
    }

}
