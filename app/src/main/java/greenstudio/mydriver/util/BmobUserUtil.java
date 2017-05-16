package greenstudio.mydriver.util;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import greenstudio.mydriver.entity.UserInfo;

/**
 * Created by wangyu on 07/02/2017.
 */

public class BmobUserUtil {
    public static void register(UserInfo info, final Handler handler) {
        info.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    handler.sendEmptyMessage(MsgUtil.registCorrect);
                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }

    public static void query(String key, String value, int limit, final Handler handler) {
        final BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo(key, value);
        query.setLimit(limit);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0)
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect);
                    else {
                        Message msg = handler.obtainMessage(MsgUtil.queryCorrect);
                        msg.obj = list;
                        msg.sendToTarget();
                    }
                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }

    public static void login(final String mobile, final String password, final Handler handler) {
        final BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("mobile", mobile);
        query.setLimit(1);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0)
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect);
                    else {
                        String password1 = list.get(0).getPassword();
                        if (!password.equals(password1)) {
                            handler.sendEmptyMessage(MsgUtil.passwordInCorrect);
                        } else {
                            Message message = handler.obtainMessage();
                            message.obj = list.get(0);
                            message.what = MsgUtil.loginCorrect;
                            message.sendToTarget();
                        }
                    }
                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }

    public static void update(final UserInfo userInfo, String key, String value, final Handler handler) {
        final BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo(key, value);
        query.setLimit(1);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0)
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect);
                    else {
                        String id = list.get(0).getObjectId();
                        userInfo.update(id, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    handler.sendEmptyMessage(MsgUtil.updateCorrect);
                                } else {
                                    handler.sendEmptyMessage(MsgUtil.errorcode);
                                }
                            }
                        });
                    }
                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }


}
