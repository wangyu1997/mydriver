package greenstudio.mydriver.util;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import greenstudio.mydriver.MyApplication;
import greenstudio.mydriver.entity.cuotiEntity;
import greenstudio.mydriver.entity.jiaxiaoInfoEntity;

/**
 * Created by wangyu on 21/02/2017.
 */

public class jiaxiaoInfoUtil {
    public static void query(final Handler handler, String city, String type) {
        final BmobQuery<jiaxiaoInfoEntity> query = new BmobQuery<jiaxiaoInfoEntity>();
        query.addWhereEqualTo("city", city);
        query.addWhereEqualTo("type", type);
        query.setLimit(2000);
        query.findObjects(new FindListener<jiaxiaoInfoEntity>() {
            @Override
            public void done(List<jiaxiaoInfoEntity> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect_jx);
                    } else {
                        Message msg = handler.obtainMessage();
                        msg.obj = list;
                        msg.what = MsgUtil.queryCorrect_jx;
                        msg.sendToTarget();
                    }

                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });

    }
}
