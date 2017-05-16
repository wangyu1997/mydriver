package greenstudio.mydriver;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import greenstudio.mydriver.entity.UserInfo;
import greenstudio.mydriver.util.AppManager;

/**
 * Created by FJS0420 on 2016/8/4.
 */

public class MyApplication extends Application {
    private static Context context;
    public static String ApplicationID = "b388a438e5aef33af460ece722d0a503";
    public static UserInfo thisUser;
    public static String jiazhaoID;
    private static RequestQueue queue;
    private static String JuheKey;

    @Override
    public void onCreate() {
        JuheKey = "a745ee881e86ccc3a99ab70f7e0b4c20";
        queue = Volley.newRequestQueue(getApplicationContext());
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "yingwoxy"))
                .setBaseDirectoryName("greenstudio.mydriver")
                .setMaxCacheSize(200 * 1024 * 1024)//200MB
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
        context = getApplicationContext();
    }

    public static Context getGlobalContext() {
        return context;
    }

    public static void restartApp() {
        AppManager.getAppManager().AppExit(getGlobalContext());
        Intent i = getGlobalContext().getPackageManager()
                .getLaunchIntentForPackage(getGlobalContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getGlobalContext().startActivity(i);
    }

    /**
     * 返回全局的请求队列
     *
     * @return
     */
    public static RequestQueue getHttpQueues() {
        return queue;
    }

    public static String getJuheKey() {
        return JuheKey;
    }

    public static UserInfo getThisUser() {
        return thisUser;
    }

    public static void setThisUser(UserInfo thisUser) {
        MyApplication.thisUser = thisUser;
    }

    public static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("key", getJuheKey());
        return params;
    }
}
