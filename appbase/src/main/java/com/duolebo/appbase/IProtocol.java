package com.duolebo.appbase;

import android.os.Handler;

public interface IProtocol {

    int PROTOCOL_RESULT_FORMAT_JSON = 0;
    int PROTOCOL_RESULT_FORMAT_XML = 1;
    int PROTOCOL_RESULT_FORMAT_JSON_ARRAY = 2;

    void execute(Handler handler);

    boolean succeed();

    IModel getData();

    int resultFormat();

    int statusCode();

    //TODO: suppurt callback style
    //public void execute(IAppBaseCallback callback);
}
