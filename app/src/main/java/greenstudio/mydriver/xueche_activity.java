package greenstudio.mydriver;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.entity.jiaxiaoInfoEntity;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.MsgUtil;
import greenstudio.mydriver.util.ToastUtil;
import greenstudio.mydriver.util.jiaxiaoInfoUtil;

/**
 * Created by wangyu on 13/02/2017.
 */

public class xueche_activity extends AppCompatActivity {
    @BindView(R.id.s_chengshi)
    Spinner sChengshi;
    @BindView(R.id.s_jiazhao)
    Spinner sJiazhao;
    @BindView(R.id.info_show)
    ListView infoShow;
    private Toolbar toolbar;
    private ArrayAdapter<String> cityAdapter, jiazhaoAdapter;
    private String city, jiazhao;
    private String[] citys = {"厦门"};
    private String[] jiazhaos = new String[]{"c1", "c2", "a1", "a2", "b1", "b2"};
    private List<jiaxiaoInfoEntity> data = new ArrayList<>();
    private XuechelistItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.xueche_activity);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        adapter = new XuechelistItemAdapter(this, data);
        infoShow.setAdapter(adapter);
        cityAdapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, citys);
        jiazhaoAdapter = new ArrayAdapter<String>(this, R.layout.sq_spinner_item, jiazhaos);
        sChengshi.setAdapter(cityAdapter);
        sJiazhao.setAdapter(jiazhaoAdapter);
        sJiazhao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jiazhao = jiazhaos[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sChengshi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = citys[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        infoShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jiaxiaoInfoEntity entity = data.get(i);
                Intent intent = new Intent(xueche_activity.this, JiaxiaoInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", entity);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_release) {
            jiaxiaoInfoUtil.query(handler, city, jiazhao);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.jiakao_layout, R.id.person_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiakao_layout:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
                break;
            case R.id.person_layout:
                startActivity(new Intent(this, person_activity.class));
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.queryCorrect_jx:
                    data = (List<jiaxiaoInfoEntity>) msg.obj;
                    adapter.updatedata(data);
                    adapter.notifyDataSetChanged();
                    break;
                case MsgUtil.queryNoneCorrect_jx:
                    ToastUtil.toast("暂时没有数据...");
                    break;
            }
        }
    };
}
