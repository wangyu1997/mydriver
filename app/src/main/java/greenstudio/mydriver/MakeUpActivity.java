package greenstudio.mydriver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
 * Created by wangyu on 06/02/2017.
 */

public class MakeUpActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_contact)
    TextView etContact;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ibtn_male)
    ImageButton ibtnMale;
    @BindView(R.id.ibtn_female)
    ImageButton ibtnFemale;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    private UserInfo userInfo;
    private int gender = 1;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.queryNoneCorrect:
                    ToastUtil.toast("用户不存在");
                    break;
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.updateCorrect:
                    ToastUtil.toast("修改成功！");
                    MyApplication.setThisUser(userInfo);
                    finish();
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
        setContentView(R.layout.activity_makeinfo);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        AppManager.getAppManager().addActivity(this);
        tvToolbar.setText("修改个人信息");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            userInfo = MyApplication.getThisUser();
        } catch (NullPointerException e) {
            ToastUtil.toast("获取用户信息失败");
        }
        if (userInfo != null) {
            etContact.setText(userInfo.getMobile());
            tvBirth.setText(userInfo.getBirth());
            etName.setText(userInfo.getName());
            etEmail.setText(userInfo.getEmail());
            int gender = userInfo.getGender();
            ibtnMale.setBackgroundResource(gender == 1 ? R.mipmap.maleshow : R.mipmap.femaleshow);
        }
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
                updateinfo();
                break;
        }
    }

    public void resetGender() {
        ibtnMale.setBackgroundResource(R.mipmap.malehide);
        ibtnFemale.setBackgroundResource(R.mipmap.femalehide);
    }

    public void updateinfo() {
        String mobile = etContact.getText().toString();
        String name = etName.getText().toString();
        String birth = tvBirth.getText().toString();
        String email = etEmail.getText().toString();
        userInfo = new UserInfo();
        userInfo.setMobile(mobile);
        userInfo.setName(name);
        userInfo.setBirth(birth);
        userInfo.setGender(gender);
        userInfo.setEmail(email);
        if (mobile.equals("")) {
            ToastUtil.toast("请输入手机号");
        } else if (name.equals("")) {
            ToastUtil.toast("请输入昵称");
        } else if (email.equals("")) {
            ToastUtil.toast("请输入电子邮箱");
        } else {
            btnSubmit.setEnabled(false);
            updateBaseInfoProgress.setVisibility(View.VISIBLE);
            BmobUserUtil.update(userInfo, "mobile", mobile, handler);
        }
    }
}
