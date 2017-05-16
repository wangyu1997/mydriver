package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.util.AppManager;

/**
 * Created by wangyu on 13/02/2017.
 */

public class jiakao_activity_kemu1 extends AppCompatActivity {
    @BindView(R.id.s_jiazhao)
    Spinner sJiazhao;
    @BindView(R.id.shunxu_tv)
    LinearLayout shunxuTv;
    @BindView(R.id.suiji_tv)
    LinearLayout suijiTv;
    @BindView(R.id.cuoti_tv)
    LinearLayout cuotiTv;
    @BindView(R.id.shoucang_tv)
    LinearLayout shoucangTv;
    private String[] jiazhaos;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.jiakao_activity_kemu1);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        initData();
    }

    public void initData() {
        jiazhaos = new String[]{"c1", "c2", "a1", "a2", "b1", "b2"};
        adapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, jiazhaos);
        sJiazhao.setAdapter(adapter);
        sJiazhao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MyApplication.jiazhaoID = jiazhaos[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.tv_kemu2, R.id.tv_kemu3, R.id.tv_kemu4, R.id.shunxu_tv, R.id.suiji_tv, R.id.cuoti_tv, R.id.shoucang_tv, R.id.xueche_layout, R.id.person_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kemu2:
                startActivity(new Intent(this, jiakao_activity_kemu2.class));
                break;
            case R.id.tv_kemu3:
                startActivity(new Intent(this, jiakao_activity_kemu3.class));
                break;
            case R.id.tv_kemu4:
                startActivity(new Intent(this, jiakao_activity_kemu4.class));
                break;
            case R.id.xueche_layout:
                startActivity(new Intent(this, xueche_activity.class));
                break;
            case R.id.person_layout:
                startActivity(new Intent(this, person_activity.class));
                break;
            case R.id.shunxu_tv:
                Intent intent = new Intent(jiakao_activity_kemu1.this, exec_activity.class);
                intent.putExtra("rand", "order");
                intent.putExtra("kemu", "1");
                startActivity(intent);
                break;
            case R.id.suiji_tv:
                intent = new Intent(jiakao_activity_kemu1.this, exec_activity.class);
                intent.putExtra("rand", "rand");
                intent.putExtra("kemu", "1");
                startActivity(intent);
                break;
            case R.id.cuoti_tv:
                intent = new Intent(jiakao_activity_kemu1.this, cuoti_activity.class);
                intent.putExtra("kemu", "1");
                startActivity(intent);
                break;
            case R.id.shoucang_tv:
                intent = new Intent(jiakao_activity_kemu1.this, shoucang_activity.class);
                intent.putExtra("kemu", "1");
                startActivity(intent);
                break;
        }
    }
}
