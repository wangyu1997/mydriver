package greenstudio.mydriver;

import android.content.Intent;
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

import java.util.List;

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

public class ResetPassword1Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.btn_next_step)
    Button btnNextStep;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd1);
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
    }

    @OnClick(R.id.btn_next_step)
    public void onClick() {
        String mobile = editCode.getText().toString();
        if (mobile.isEmpty()) {
            ToastUtil.toast("请输入正确的手机号");
        } else {
            btnNextStep.setEnabled(false);
            updateBaseInfoProgress.setVisibility(View.VISIBLE);
            BmobUserUtil.query("mobile", mobile, 1, handler);
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.queryNoneCorrect:
                    ToastUtil.toast("用户不存在，请检查输入是否正确");
                    break;
                case MsgUtil.queryCorrect:
                    UserInfo userInfo = ((List<UserInfo>) msg.obj).get(0);
                    ToastUtil.toast("输入正确,正在跳转...");
                    Intent intent = new Intent(ResetPassword1Activity.this, ResetPassword2Activity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putSerializable("userInfo", userInfo);
                    intent.putExtra("bundle", dataBundle);
                    startActivity(intent);
                    break;
            }
            btnNextStep.setEnabled(true);
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);

        }
    };
}
