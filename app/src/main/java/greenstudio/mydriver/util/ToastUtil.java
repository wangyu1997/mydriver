package greenstudio.mydriver.util;

import android.widget.Toast;

import greenstudio.mydriver.MyApplication;

/**
 * Created by wangyu on 07/02/2017.
 */

public class ToastUtil {
    public static void toast(String msg){
        Toast.makeText(MyApplication.getGlobalContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
