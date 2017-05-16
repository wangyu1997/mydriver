package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
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
import greenstudio.mydriver.util.ToastUtil;

/**
 * Created by wangyu on 10/02/2017.
 */

public class ResetPassword2Activity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_an1)
    EditText etAn1;
    @BindView(R.id.et_an2)
    EditText etAn2;
    @BindView(R.id.et_an3)
    EditText etAn3;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.tv_sq1)
    TextView tvSq1;
    @BindView(R.id.tv_sq2)
    TextView tvSq2;
    @BindView(R.id.tv_sq3)
    TextView tvSq3;
    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpasswd2);
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
        tvSq1.setText(userInfo.getQ1());
        tvSq2.setText(userInfo.getQ2());
        tvSq3.setText(userInfo.getQ3());
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        if (etAn1.getText().toString().isEmpty()) {
            ToastUtil.toast("请输入问题1的答案");
        } else if (etAn2.getText().toString().isEmpty()) {
            ToastUtil.toast("请输入问题2的答案");
        } else if (etAn3.getText().toString().isEmpty()) {
            ToastUtil.toast("请输入问题3的答案");
        } else if (!etAn1.getText().toString().equals(userInfo.getA1()) || !etAn2.getText().toString().equals(userInfo.getA2()) || !etAn3.getText().toString().equals(userInfo.getA3())) {
            ToastUtil.toast("密保答案不正确,请核对后重新输入");
        } else {
            Intent intent = new Intent(ResetPassword2Activity.this, ResetPassword3Activity.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putSerializable("userInfo", userInfo);
            intent.putExtra("bundle", dataBundle);
            startActivity(intent);
        }
    }
}