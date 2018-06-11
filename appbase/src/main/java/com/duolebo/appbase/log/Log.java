package com.duolebo.appbase.log;

public class Log implements ILog {
    
    private static Log instance = new Log();
    private ILogFactory defaultFactory = new DefaultFactory();
    private ILogFactory factory = defaultFactory;
    
    private Log() { }
    
    public static Log getInstance() {
        return instance;
    }

    public void setFactory(ILogFactory factory) {
        this.factory = (null == factory ? defaultFactory : factory);
    }
    
    @Override
    public void i(String tag, String message) {
        factory.createLog().i(tag, message);
    }

    @Override
    public void w(String tag, String message) {
        factory.createLog().w(tag, message);
    }

    @Override
    public void d(String tag, String message) {
        factory.createLog().d(tag, message);
    }

    @Override
    public void v(String tag, String message) {
        factory.createLog().v(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        factory.createLog().e(tag, message);
    }
}
