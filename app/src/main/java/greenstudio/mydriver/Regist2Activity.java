package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
 * Created by wangyu on 07/02/2017.
 */

public class Regist2Activity extends AppCompatActivity {
    @BindView(R.id.s_q1)
    Spinner sQ1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_an1)
    EditText etAn1;
    @BindView(R.id.s_q2)
    Spinner sQ2;
    @BindView(R.id.et_an2)
    EditText etAn2;
    @BindView(R.id.s_q3)
    Spinner sQ3;
    @BindView(R.id.et_an3)
    EditText etAn3;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;

    private String[] questions1;
    private ArrayAdapter<String> sq1_adapter;
    private String[] questions2;
    private ArrayAdapter<String> sq2_adapter;
    private String[] questions3;
    private ArrayAdapter<String> sq3_adapter;
    private int sq1_select_flag = 0;
    private int sq2_select_flag = 0;
    private int sq3_select_flag = 0;
    private Intent data;
    private UserInfo userInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist2);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        AppManager.getAppManager().addActivity(this);
        tvToolbar.setText("设置安全问题");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        data = getIntent();
        Bundle dataBundle = data.getBundleExtra("bundle");
        userInfo = (UserInfo) dataBundle.getSerializable("userInfo");
        initData();
        sQ1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sq1_select_flag = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sQ2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sq2_select_flag = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sQ3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sq3_select_flag = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String A1 = etAn1.getText().toString();
        String A2 = etAn2.getText().toString();
        String A3 = etAn3.getText().toString();
        if (sq1_select_flag == 0) {
            ToastUtil.toast("请选择问题1");
        } else if (A1.isEmpty()) {
            ToastUtil.toast("请输入问题1的答案");
        } else if (sq2_select_flag == 0) {
            ToastUtil.toast("请选择问题2");
        } else if (A2.isEmpty()) {
            ToastUtil.toast("请输入问题2的答案");
        } else if (sq3_select_flag == 0) {
            ToastUtil.toast("请选择问题3");
        } else if (A3.isEmpty()) {
            ToastUtil.toast("请输入问题3的答案");
        } else {
            userInfo.setQ1(questions1[sq1_select_flag]);
            userInfo.setQ2(questions2[sq2_select_flag]);
            userInfo.setQ3(questions3[sq3_select_flag]);
            userInfo.setA1(A1);
            userInfo.setA2(A2);
            userInfo.setA3(A3);
            btnSubmit.setEnabled(false);
            updateBaseInfoProgress.setVisibility(View.VISIBLE);
            BmobUserUtil.register(userInfo, handler);
        }
    }

    public void initData() {
        questions1 = new String[]{"请选择", "你小时候最喜欢哪一本书?", "你的理想工作是什么?", "你童年时代的绰号是什么?", "你拥有的第一辆车是什么型号?", "你在学生时代最喜欢哪个歌手或乐队？", "你在学生时代最喜欢哪个电影明星或角色?"};
        questions2 = new String[]{"请选择", "你的第一个上司叫什么名字?", "你的父母是在哪里认识的?", "你的第一个宠物叫什么名字?", "你少年时代最好的朋友叫什么名字?", "你第一次去电影院看的是哪一部电影?", "你学会做的第一道菜叫什么名字?"};
        questions3 = new String[]{"请选择", "你上小学时最喜欢的老师姓什么?", "你第一次坐飞机是去哪里?", "你从小长大的那条街叫什么?", "你去过的第一个海滨浴场是哪一个?", "你购买的第一张专辑是什么?", "您最喜欢哪个球队?"};
        sq1_adapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, questions1);
        sq2_adapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, questions2);
        sq3_adapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, questions3);
        sQ1.setAdapter(sq1_adapter);
        sQ2.setAdapter(sq2_adapter);
        sQ3.setAdapter(sq3_adapter);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求错误，请稍后重试");
                    break;
                case MsgUtil.registCorrect:
                    ToastUtil.toast("恭喜您，注册成功,请重新登陆");
                    MyApplication.restartApp();
                    break;
            }
            btnSubmit.setEnabled(true);
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);
        }
    };

}
