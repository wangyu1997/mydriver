package greenstudio.mydriver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import greenstudio.mydriver.entity.jiaxiaoInfoEntity;
import greenstudio.mydriver.util.AppManager;
import greenstudio.mydriver.util.DensityUtils;

/**
 * Created by wangyu on 23/02/2017.
 */

public class JiaxiaoInfoActivity extends AppCompatActivity {
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.picture_img)
    SimpleDraweeView pictureImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.adress_tv)
    TextView adressTv;
    @BindView(R.id.range_tv)
    TextView rangeTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.lxname_tv)
    TextView lxnameTv;
    @BindView(R.id.contact_tv)
    TextView contactTv;

    private jiaxiaoInfoEntity entity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.jiaxiaoinfo_layout);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();
    }

    public void init() {
        entity = (jiaxiaoInfoEntity) getIntent().getBundleExtra("bundle").getSerializable("data");
        showThumb(Uri.parse(entity.getImgUrl()), pictureImg);
        nameTv.setText(entity.getName());
        adressTv.setText("地址：" + entity.getAddress());
        rangeTv.setText("招生范围：" + entity.getRange());
        priceTv.setText("¥ " + entity.getPrice());
        lxnameTv.setText("联系人：" + entity.getJlName());
        contactTv.setText(entity.getContact());
    }

    public void showThumb(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(this, 400), DensityUtils.dp2px(this, 180)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

}
