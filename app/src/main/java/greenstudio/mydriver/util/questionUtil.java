package greenstudio.mydriver.util;


import android.os.Handler;
import android.os.Message;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import greenstudio.mydriver.MyApplication;
import greenstudio.mydriver.entity.*;

/**
 * Created by FJS0420 on 2016/8/31.
 */

public class questionUtil {
    public static void getQuestions(final Handler handler, Map<String, String> params) {
        VolleyRequest.RequestPost(MyApplication.getGlobalContext(), "http://v.juhe.cn/jztk/query", "tag", params, new VolleyInterface(MyApplication.getGlobalContext()) {

            @Override
            public void onMySuccess(String result) {
                // 成功的回调
                try {
                    JSONObject object = new JSONObject(result);
                    int error = object.getInt("error_code");
                    if (error != 0) {
                        handler.sendEmptyMessage(MsgUtil.errorcode);
                    } else {
                        List<ResultEntity> data = new ArrayList<ResultEntity>();
                        ResultEntity entity;
                        JSONArray array = object.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String question = object.getString("question");
                            String answer = object.getString("answer");
                            String item1 = object.getString("item1");
                            String item2 = object.getString("item2");
                            String item3 = object.getString("item3");
                            String item4 = object.getString("item4");
                            String explains = object.getString("explains");
                            String url = object.getString("url");
                            entity = new ResultEntity(id, question, answer, item1, item2, item3, item4, explains, url);
                            data.add(entity);
                        }
                        Message message = handler.obtainMessage();
                        message.what = MsgUtil.questionCorrect;
                        message.obj = data;
                        message.sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                //失败的
                handler.sendEmptyMessage(MsgUtil.errorcode);
            }
        });
    }
}
