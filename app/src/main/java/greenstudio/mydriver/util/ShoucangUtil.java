package greenstudio.mydriver.util;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import greenstudio.mydriver.MyApplication;
import greenstudio.mydriver.entity.shoucangEntity;

/**
 * Created by wangyu on 15/02/2017.
 */

public class ShoucangUtil {
    public static void queryIfExist(final shoucangEntity info, final Handler handler) {
        final BmobQuery<shoucangEntity> query = new BmobQuery<shoucangEntity>();
        query.addWhereEqualTo("id", info.getId());
        query.setLimit(1);
        query.findObjects(new FindListener<shoucangEntity>() {
            @Override
            public void done(List<shoucangEntity> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        handler.sendEmptyMessage(MsgUtil.queryNoneSc);
                    } else handler.sendEmptyMessage(MsgUtil.alreadyscCorrect);

                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }

    public static void insertExec(final shoucangEntity info, final Handler handler, String type, String kemu) {
        final BmobQuery<shoucangEntity> query = new BmobQuery<shoucangEntity>();
        query.addWhereEqualTo("id", info.getId());
        if (!kemu.equals("4")) {
            query.addWhereEqualTo("type", type);
        }
        query.addWhereEqualTo("kemu", kemu);
        query.setLimit(1);
        query.findObjects(new FindListener<shoucangEntity>() {
            @Override
            public void done(List<shoucangEntity> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        info.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    handler.sendEmptyMessage(MsgUtil.insertCorrect_sc);
                                } else {
                                    handler.sendEmptyMessage(MsgUtil.errorcode);
                                }
                            }
                        });
                    } else {
                        handler.sendEmptyMessage(MsgUtil.alreadyCorrect_sc);
                    }
                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });
    }


    public static void query(final Handler handler, String type, String kemu) {
        final BmobQuery<shoucangEntity> query = new BmobQuery<shoucangEntity>();
        query.addWhereEqualTo("userID", MyApplication.getThisUser().getObjectId());
        if (!kemu.equals("4")) {
            query.addWhereEqualTo("type", type);
        }
        query.addWhereEqualTo("kemu", kemu);
        query.setLimit(2000);
        query.findObjects(new FindListener<shoucangEntity>() {
            @Override
            public void done(List<shoucangEntity> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect_sc);
                    } else {
                        Message msg = handler.obtainMessage();
                        msg.obj = list;
                        msg.what = MsgUtil.queryCorrect_sc;
                        msg.sendToTarget();
                    }

                } else {
                    handler.sendEmptyMessage(MsgUtil.errorcode);
                }
            }
        });

    }

    /**
     * 删除对象
     */
    public static void delete(final shoucangEntity info, final Handler handler, String type, String kemu) {
        final BmobQuery<shoucangEntity> query = new BmobQuery<shoucangEntity>();
        query.addWhereEqualTo("id", info.getId());
        if (!kemu.equals("4")) {
            query.addWhereEqualTo("type", type);
        }
        query.addWhereEqualTo("kemu", kemu);
        query.setLimit(1);
        query.findObjects(new FindListener<shoucangEntity>() {
            @Override
            public void done(List<shoucangEntity> list, BmobException e) {
                if (e == null) {
                    if (list == null || list.size() == 0) {
                        handler.sendEmptyMessage(MsgUtil.queryNoneCorrect_sc);
                    } else {
                        info.delete(list.get(0).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    handler.sendEmptyMessage(MsgUtil.deleteCorrect_sc);
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
