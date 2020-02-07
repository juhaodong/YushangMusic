package us.haodongandyushang.yushangmusic;

import android.app.Application;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.facebook.drawee.backends.pipeline.Fresco;

import us.haodongandyushang.yushangmusic.utils.NetworkUtils;

public class YushangApp extends Application {
    NetworkUtils n;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        XLog.init(LogLevel.ALL, new LogConfiguration.Builder().b().build());
        n = new NetworkUtils();
    }
}
