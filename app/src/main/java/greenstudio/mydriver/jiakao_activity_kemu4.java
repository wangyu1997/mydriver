package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.util.AppManager;

/**
 * Created by wangyu on 13/02/2017.
 */

public class jiakao_activity_kemu4 extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.jiakao_activity_kemu4);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.xueche_layout, R.id.person_layout, R.id.tv_kemu1, R.id.tv_kemu2, R.id.tv_kemu3, R.id.shunxu_tv, R.id.suiji_tv, R.id.cuoti_tv, R.id.shoucang_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kemu2:
                startActivity(new Intent(this, jiakao_activity_kemu2.class));
                break;
            case R.id.tv_kemu3:
                startActivity(new Intent(this, jiakao_activity_kemu3.class));
                break;
            case R.id.tv_kemu1:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
                break;
            case R.id.xueche_layout:
                startActivity(new Intent(this, xueche_activity.class));
                break;
            case R.id.person_layout:
                startActivity(new Intent(this, person_activity.class));
                break;
            case R.id.shunxu_tv:
                Intent intent = new Intent(jiakao_activity_kemu4.this, exec_activity.class);
                intent.putExtra("rand", "order");
                intent.putExtra("kemu", "4");
                startActivity(intent);
                break;
            case R.id.suiji_tv:
                intent = new Intent(jiakao_activity_kemu4.this, exec_activity.class);
                intent.putExtra("rand", "rand");
                intent.putExtra("kemu", "4");
                startActivity(intent);
                break;
            case R.id.cuoti_tv:
                intent = new Intent(jiakao_activity_kemu4.this, cuoti_activity.class);
                intent.putExtra("kemu", "4");
                startActivity(intent);
                break;
            case R.id.shoucang_tv:
                intent = new Intent(jiakao_activity_kemu4.this, shoucang_activity.class);
                intent.putExtra("kemu", "4");
                startActivity(intent);
                break;
        }
    }
}
