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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
 * Created by wangyu on 06/02/2017.
 */

public class Regist1Activity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ibtn_male)
    ImageButton ibtnMale;
    @BindView(R.id.ibtn_female)
    ImageButton ibtnFemale;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.school_select)
    LinearLayout schoolSelect;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.academy_select)
    LinearLayout academySelect;
    @BindView(R.id.et_passwd)
    EditText etPasswd;
    @BindView(R.id.makeupinfo)
    RelativeLayout makeupinfo;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    private UserInfo userInfo;
    private int gender = 1;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.queryNoneCorrect:
                    ToastUtil.toast("个人信息设置成功!");
                    Intent intent = new Intent(Regist1Activity.this, Regist2Activity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putSerializable("userInfo", userInfo);
                    intent.putExtra("bundle", dataBundle);
                    startActivity(intent);
                    break;
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.queryCorrect:
                    ToastUtil.toast("用户已存在，请尝试登陆或者找回密码");
                    break;
                case MsgUtil.birthSetCorrect:
                    tvBirth.setText((CharSequence) msg.obj);
                    break;
            }
            btnSubmit.setEnabled(true);
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist1);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        AppManager.getAppManager().addActivity(this);
        tvToolbar.setText("填写个人信息");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibtnMale.setBackgroundResource(R.mipmap.maleshow);
    }

    @OnClick({R.id.ibtn_male, R.id.ibtn_female, R.id.tv_birth, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_male:
                resetGender();
                ibtnMale.setBackgroundResource(R.mipmap.maleshow);
                gender = 1;
                break;
            case R.id.ibtn_female:
                resetGender();
                ibtnFemale.setBackgroundResource(R.mipmap.femaleshow);
                gender = 2;
                break;
            case R.id.tv_birth:
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.handler = handler;
                datePicker.show(getFragmentManager(), "datePicker");
                break;
            case R.id.btn_submit:
                regist1();
                break;
        }
    }

    public void resetGender() {
        ibtnMale.setBackgroundResource(R.mipmap.malehide);
        ibtnFemale.setBackgroundResource(R.mipmap.femalehide);

    }

    public void regist1() {
        String mobile = etContact.getText().toString();
        String name = etName.getText().toString();
        String birth = tvBirth.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPasswd.getText().toString();
        userInfo = new UserInfo();
        userInfo.setMobile(mobile);
        userInfo.setName(name);
        userInfo.setBirth(birth);
        userInfo.setGender(gender);
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        if (mobile.equals("")) {
            ToastUtil.toast("请输入手机号");
        } else if (name.equals("")) {
            ToastUtil.toast("请输入昵称");
        } else if (email.equals("")) {
            ToastUtil.toast("请输入电子邮箱");
        } else if (password.equals("")) {
            ToastUtil.toast("请设置密码");
        } else {
            btnSubmit.setEnabled(false);
            updateBaseInfoProgress.setVisibility(View.VISIBLE);
            BmobUserUtil.query("mobile", userInfo.getMobile(), 1, handler);
        }
    }
}
