package greenstudio.mydriver;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import greenstudio.mydriver.entity.ResultEntity;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.MsgUtil;
import greenstudio.mydriver.util.ToastUtil;
import greenstudio.mydriver.util.questionUtil;

/**
 * Created by wangyu on 14/02/2017.
 */

public class exec_activity extends FragmentActivity {


    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ViewPager mViewPager;
    private exec_frag_adapter mPagerAdapter;
    private List<ResultEntity> data;
    private String isRand;
    private String kemu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.question_activity);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        kemu = getIntent().getStringExtra("kemu");
        isRand = getIntent().getStringExtra("rand");
        tvToolbar.setText(isRand.equals("rand") ? "随机练习" : "顺序练习");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewPager();
        getQuestion();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化ViewPager控件
     */
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //关闭预加载，默认一次只加载一个Fragment
        mViewPager.setOffscreenPageLimit(3);
        //添加Fragment
        //适配器
        mPagerAdapter = new exec_frag_adapter(getSupportFragmentManager(), data);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(mPagerAdapter);
    }

    public void getQuestion() {
        updateBaseInfoProgress.setVisibility(View.VISIBLE);
        ToastUtil.toast("请稍后,正在请求数据...");
        Map<String, String> params = MyApplication.getParams();
        params.put("subject", kemu);
        params.put("model", MyApplication.jiazhaoID);
        params.put("testType", isRand);
        questionUtil.getQuestions(handler, params);
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("获取数据失败,请稍后重试");
                    break;
                case MsgUtil.questionCorrect:
                    data = new ArrayList<>();
                    List<ResultEntity> list = (List<ResultEntity>) msg.obj;
                    if (list != null || list.size() > 0)
                        data = list;
                    mPagerAdapter.updateData(data);
                    mPagerAdapter.notifyDataSetChanged();
                    break;
            }
            updateBaseInfoProgress.setVisibility(View.GONE);
        }
    };

    public String getKemu() {
        return kemu;
    }
}