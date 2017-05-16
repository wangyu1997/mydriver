package greenstudio.mydriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.URLUtils;

/**
 * Created by wangyu on 13/02/2017.
 */

public class jiakao_activity_kemu3 extends AppCompatActivity {

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.jiakao_activity_kemu3);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_kemu1, R.id.tv_kemu2, R.id.tv_kemu4, R.id.qibu_layout, R.id.yuche_layout, R.id.luzhou_layout, R.id.qita_layout, R.id.xueche_layout, R.id.person_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kemu1:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
                break;
            case R.id.tv_kemu2:
                startActivity(new Intent(this, jiakao_activity_kemu2.class));
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
            case R.id.qibu_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "起步&夜间");
                intent.putExtra("url", URLUtils.qibuyejian);
                startActivity(intent);
                break;
            case R.id.yuche_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "遇车场景");
                intent.putExtra("url", URLUtils.yuchechangjing);
                startActivity(intent);
                break;
            case R.id.luzhou_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "路周场景");
                intent.putExtra("url", URLUtils.luzhouchangjing);
                startActivity(intent);
                break;
            case R.id.qita_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "其他场景");
                intent.putExtra("url", URLUtils.qitachangjing);
                startActivity(intent);
                break;
        }
    }
}
