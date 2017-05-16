package greenstudio.mydriver;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greenstudio.mydriver.entity.jiaxiaoInfoEntity;
import greenstudio.mydriver.util.DensityUtils;

public class XuechelistItemAdapter extends BaseAdapter {

    private List<jiaxiaoInfoEntity> objects = new ArrayList<jiaxiaoInfoEntity>();

    private Context context;
    private LayoutInflater layoutInflater;

    public XuechelistItemAdapter(Context context, List<jiaxiaoInfoEntity> data) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        objects = data;
    }

    public void updatedata(List<jiaxiaoInfoEntity> data) {
        this.objects = data;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public jiaxiaoInfoEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.xuechelist_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((jiaxiaoInfoEntity) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(jiaxiaoInfoEntity object, ViewHolder holder) {
        //TODO implement
        showThumb(Uri.parse(object.getImgUrl()), holder.jxImg);
        holder.jxName.setText(object.getName());
        holder.jxPrice.setText("¥ " + object.getPrice());
    }

    public void showThumb(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(context, 400), DensityUtils.dp2px(context, 180)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

    class ViewHolder {
        @BindView(R.id.jx_img)
        SimpleDraweeView jxImg;
        @BindView(R.id.jx_name)
        TextView jxName;
        @BindView(R.id.jx_price)
        TextView jxPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
