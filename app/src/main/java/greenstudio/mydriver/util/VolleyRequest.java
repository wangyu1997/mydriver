package greenstudio.mydriver.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import greenstudio.mydriver.MyApplication;

/**
 * Created by wangyu on 18/02/2017.
 */

public class VolleyRequest {
    public static StringRequest stringRequest;
    public static Context mContext;

    public static void RequestGet(Context mContext, String url, String tag,
                                  VolleyInterface vif) {
        stringRequest = new StringRequest(Request.Method.GET, url,
                vif.loadingListener(), vif.errorListener());
        // 设置标签
        stringRequest.setTag(tag);
        // 加入队列
        MyApplication.getHttpQueues().add(stringRequest);
    }

    public static void RequestPost(Context mContext, String url, String tag,
                                   final Map<String, String> params, VolleyInterface vif) {

        stringRequest = new StringRequest(Request.Method.POST, url,
                vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        // 设置标签
        stringRequest.setTag(tag);
        // 加入队列
        MyApplication.getHttpQueues().add(stringRequest);
    }
}