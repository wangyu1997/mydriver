package greenstudio.mydriver;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;

import greenstudio.mydriver.util.MsgUtil;

/**
 * Created by wangyu on 07/02/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public Handler handler;
    private String birth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        birth = i + "年" + (i1 + 1) + "月" + i2 + "日";
        Message message = handler.obtainMessage();
        message.what = MsgUtil.birthSetCorrect;
        message.obj = birth;
        message.sendToTarget();
    }
}

