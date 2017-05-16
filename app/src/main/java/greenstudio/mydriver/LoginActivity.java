package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import greenstudio.mydriver.entity.ResultEntity;
import greenstudio.mydriver.entity.UserInfo;
import greenstudio.mydriver.entity.jiaxiaoInfoEntity;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.BmobUserUtil;
import greenstudio.mydriver.util.MsgUtil;
import greenstudio.mydriver.util.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_passwd)
    EditText editPasswd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgetpd)
    TextView tvForgetpd;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        AppManager.getAppManager().addActivity(this);
        Bmob.initialize(this, MyApplication.ApplicationID);
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forgetpd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                updateBaseInfoProgress.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                login();
                break;
            case R.id.tv_register:
                intent = new Intent(this, Regist1Activity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgetpd:
                intent = new Intent(this, ResetPassword1Activity.class);
                startActivity(intent);
                break;
        }
    }

    public void login() {
        String mobile = editPhone.getText().toString();
        String password = editPasswd.getText().toString();
        if (mobile.isEmpty()) {
            ToastUtil.toast("请输入用户名");
        } else if (password.isEmpty()) {
            ToastUtil.toast("请输入密码");
        } else {
            BmobUserUtil.login(mobile, password, handler);
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求错误，请稍后重试");
                    break;
                case MsgUtil.queryNoneCorrect:
                    ToastUtil.toast("没有此用户");
                    break;
                case MsgUtil.loginCorrect:
                    MyApplication.setThisUser((UserInfo) msg.obj);
                    ToastUtil.toast("登陆成功,正在跳转页面...");
                    startActivity(new Intent(LoginActivity.this, jiakao_activity_kemu1.class));
                    break;
                case MsgUtil.passwordInCorrect:
                    ToastUtil.toast("密码不正确");
                    break;
                case MsgUtil.questionCorrect:
                    List<ResultEntity> data = (List<ResultEntity>) msg.obj;
                    ToastUtil.toast(data.get(0).getExplains());
                    break;
            }
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);
            btnLogin.setEnabled(true);
        }
    };
}
