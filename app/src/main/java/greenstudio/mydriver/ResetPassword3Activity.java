package greenstudio.mydriver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.entity.UserInfo;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.BmobUserUtil;
import greenstudio.mydriver.util.MsgUtil;
import greenstudio.mydriver.util.ToastUtil;

/**
 * Created by wangyu on 10/02/2017.
 */

public class ResetPassword3Activity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.edit_confirm)
    EditText editConfirm;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd3);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        AppManager.getAppManager().addActivity(this);
        tvToolbar.setText("找回密码");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userInfo = (UserInfo) getIntent().getBundleExtra("bundle").getSerializable("userInfo");
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.updateCorrect:
                    ToastUtil.toast("修改成功,请重新登录");
                    MyApplication.restartApp();
                    break;
            }
            btnNextStep.setEnabled(true);
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);
        }
    };


    @OnClick(R.id.btn_next_step)
    public void onClick() {
        String password = editCode.getText().toString();
        String passConfirm = editConfirm.getText().toString();
        if (password.isEmpty()) {
            ToastUtil.toast("请输入密码");
        } else if (passConfirm.isEmpty()) {
            ToastUtil.toast("请再次输入密码");
        } else if (!password.equals(passConfirm)) {
            ToastUtil.toast("两次密码输入不一致，请检查后重新输入");
        } else if (password.equals(userInfo.getPassword())) {
            ToastUtil.toast("请确认新密码与之前密码不相同");
        } else {
            userInfo.setPassword(password);
            ToastUtil.toast("正在修改,请稍等...");
            btnNextStep.setEnabled(false);
            updateBaseInfoProgress.setVisibility(View.VISIBLE);
            BmobUserUtil.update(userInfo, "mobile", userInfo.getMobile(), handler);
        }
    }
}