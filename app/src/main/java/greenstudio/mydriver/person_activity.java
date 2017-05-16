package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.entity.UserInfo;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.ToastUtil;

/**
 * Created by wangyu on 13/02/2017.
 */

public class person_activity extends AppCompatActivity {
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ibtn_male)
    ImageView ibtnMale;
    @BindView(R.id.et_email)
    TextView etEmail;
    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.person_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void init() {
        try {
            userInfo = MyApplication.getThisUser();
        } catch (NullPointerException e) {
            ToastUtil.toast("获取用户信息失败");
        }
        if (userInfo != null) {
            tvContact.setText(userInfo.getMobile());
            tvBirth.setText(userInfo.getBirth());
            tvName.setText(userInfo.getName());
            etEmail.setText(userInfo.getEmail());
            int gender = userInfo.getGender();
            ibtnMale.setBackgroundResource(gender == 1 ? R.mipmap.maleshow : R.mipmap.femaleshow);
        }
    }


    @OnClick({R.id.jiakao_layout, R.id.xueche_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xueche_layout:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
                break;
            case R.id.jiakao_layout:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
                break;
        }
    }

    @OnClick(R.id.update_layout)
    public void onClick() {
        startActivity(new Intent(this, MakeUpActivity.class));
    }
}
