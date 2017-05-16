package greenstudio.mydriver.util;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by wangyu on 18/02/2017.
 */

public abstract class VolleyInterface {
    public Context mContext;
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListtener;

    public VolleyInterface(Context context) {
        this.mContext = context;
    }

    public abstract void onMySuccess(String result);

    public abstract void onMyError(VolleyError error);

    public Response.Listener<String> loadingListener() {
        mListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                onMySuccess(result);
            }
        };
        return mListener;
    }

    public Response.ErrorListener errorListener() {
        mErrorListtener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);
            }
        };
        return mErrorListtener;
    }
}