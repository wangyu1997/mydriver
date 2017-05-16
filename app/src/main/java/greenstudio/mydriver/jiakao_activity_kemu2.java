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

public class jiakao_activity_kemu2 extends AppCompatActivity {

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.jiakao_activity_kemu2);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_kemu1, R.id.tv_kemu3, R.id.tv_kemu4, R.id.daoche_layout, R.id.cefang_layout, R.id.zhixian_layout, R.id.poting_layout, R.id.xueche_layout, R.id.person_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kemu1:
                startActivity(new Intent(this, jiakao_activity_kemu1.class));
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
            case R.id.daoche_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "倒车入库");
                intent.putExtra("url", URLUtils.daocheruku);
                startActivity(intent);
                break;
            case R.id.cefang_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "侧方停车");
                intent.putExtra("url", URLUtils.cefangtingche);
                startActivity(intent);
                break;
            case R.id.zhixian_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "直线&曲线");
                intent.putExtra("url", URLUtils.zhixianquxian);
                startActivity(intent);
                break;
            case R.id.poting_layout:
                intent = new Intent(this, PlayVideoWebViewActivity.class);
                intent.putExtra("title", "坡停起步");
                intent.putExtra("url", URLUtils.potingqibu);
                startActivity(intent);
                break;
        }
    }
}
