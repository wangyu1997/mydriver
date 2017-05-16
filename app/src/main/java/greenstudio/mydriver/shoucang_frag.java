package greenstudio.mydriver;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;
import greenstudio.mydriver.entity.cuotiEntity;
import greenstudio.mydriver.entity.shoucangEntity;
import greenstudio.mydriver.util.AnswerCheckUtil;
import greenstudio.mydriver.util.CuotiUtil;
import greenstudio.mydriver.util.DensityUtils;
import greenstudio.mydriver.util.MsgUtil;
import greenstudio.mydriver.util.ShoucangUtil;
import greenstudio.mydriver.util.ToastUtil;

/**
 * Created by wangyu on 14/02/2017.
 */

public class shoucang_frag extends Fragment {
    @BindView(R.id.question)
    TextView question;
    @BindView(R.id.picture)
    SimpleDraweeView picture;
    @BindView(R.id.check_a)
    CheckBox checkA;
    @BindView(R.id.check_b)
    CheckBox checkB;
    @BindView(R.id.check_c)
    CheckBox checkC;
    @BindView(R.id.check_d)
    CheckBox checkD;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.right_layout)
    LinearLayout rightLayout;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.wrong_layout)
    LinearLayout wrongLayout;
    @BindView(R.id.updateBaseInfoProgress)
    ProgressBar updateBaseInfoProgress;
    @BindView(R.id.btn_collect)
    Button btnCollect;
    private shoucangEntity resultEntity;
    private shoucang_activity activity;
    private String kemu;

    public static shoucang_frag newInstance(shoucangEntity resultEntity) {
        shoucang_frag newFragment = new shoucang_frag();
        newFragment.setResultEntity(resultEntity);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (shoucang_activity) getActivity();
        View view = inflater.inflate(R.layout.question_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kemu = activity.getKemu();
        btnCollect.setText("已收藏");
        btnCollect.setBackgroundColor(getResources().getColor(R.color.textcolor1));
        if (resultEntity.getUrl().trim().equals("")) {
            picture.setVisibility(View.GONE);
        } else {
            picture.setVisibility(View.VISIBLE);
            showThumb(Uri.parse(resultEntity.getUrl()), picture);
        }
        question.setText(resultEntity.getQuestion());
        String item1 = resultEntity.getItem1();
        String item2 = resultEntity.getItem2();
        String item3 = resultEntity.getItem3();
        String item4 = resultEntity.getItem4();
        if (item3.isEmpty() || item3.equals("")) {
            checkC.setVisibility(View.INVISIBLE);
            checkD.setVisibility(View.INVISIBLE);
        }
        checkA.setText(item1);
        checkB.setText(item2);
        checkC.setText(item3);
        checkD.setText(item4);
        explain.setText(resultEntity.getExplains());
    }


    public void showThumb(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.dp2px(this.getContext(), 400), DensityUtils.dp2px(getContext(), 180)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

    public void setResultEntity(shoucangEntity resultEntity) {
        this.resultEntity = resultEntity;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MsgUtil.alreadyCorrect:
                    break;
                case MsgUtil.errorcode:
                    ToastUtil.toast("请求超时,请稍后重试");
                    break;
                case MsgUtil.insertCorrect_sc:
                    btnCollect.setText("已收藏");
                    btnCollect.setBackgroundColor(getResources().getColor(R.color.textcolor1));
                    ToastUtil.toast("成功添加到收藏!");
                    break;
                case MsgUtil.deleteCorrect_sc:
                    btnCollect.setText("收藏");
                    btnCollect.setBackgroundColor(getResources().getColor(R.color.app_primary));
                    ToastUtil.toast("已成功取消收藏!");
                    break;
                case MsgUtil.insertCorrect_ct:
                    ToastUtil.toast("成功添加到错题集!");
                    break;
                case MsgUtil.alreadyCorrect_ct:
                    ToastUtil.toast("这道题在错题集中存在，你可要注意咯！");
                    break;
                case MsgUtil.queryNoneCorrect_sc:
                    btnCollect.setText("收藏");
                    btnCollect.setBackgroundColor(getResources().getColor(R.color.app_primary));
                    ToastUtil.toast("本题还未收藏.");
                    break;
                case MsgUtil.alreadyCorrect_sc:
                    btnCollect.setText("已收藏");
                    btnCollect.setBackgroundColor(getResources().getColor(R.color.textcolor1));
                    ToastUtil.toast("本题已经收藏过了.");
                    break;
            }
            btnSubmit.setEnabled(true);
            updateBaseInfoProgress.setVisibility(View.INVISIBLE);
        }
    };


    @OnClick({R.id.btn_collect, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_collect:
                shoucangEntity entity1 = new shoucangEntity();
                entity1.setId(resultEntity.getId());
                entity1.setQuestion(resultEntity.getQuestion());
                entity1.setAnswer(resultEntity.getAnswer());
                entity1.setItem1(resultEntity.getItem1());
                entity1.setItem2(resultEntity.getItem2());
                entity1.setItem3(resultEntity.getItem3());
                entity1.setItem4(resultEntity.getItem4());
                entity1.setExplains(resultEntity.getExplains());
                entity1.setUrl(resultEntity.getUrl());
                entity1.setUserID(MyApplication.getThisUser().getObjectId());
                entity1.setKemu(kemu);
                if (!kemu.equals("4"))
                    entity1.setType(MyApplication.jiazhaoID);
                if (btnCollect.getText().toString().equals("收藏")) {
                    btnSubmit.setEnabled(false);
                    updateBaseInfoProgress.setVisibility(View.VISIBLE);
                    ShoucangUtil.insertExec(entity1, handler, MyApplication.jiazhaoID, kemu);
                } else {
                    btnSubmit.setEnabled(false);
                    updateBaseInfoProgress.setVisibility(View.VISIBLE);
                    ShoucangUtil.delete(entity1, handler, MyApplication.jiazhaoID, kemu);
                }
                break;
            case R.id.btn_submit:
                StringBuffer buffer = new StringBuffer();
                if (checkA.isChecked())
                    buffer.append("A");
                if (checkB.isChecked())
                    buffer.append("B");
                if (checkC.isChecked())
                    buffer.append("C");
                if (checkD.isChecked())
                    buffer.append("D");
                String result = buffer.toString();
                boolean isRight = false;
                if (AnswerCheckUtil.judgeAnswer(resultEntity.getAnswer(), result))
                    isRight = true;
                if (isRight) {
                    rightLayout.setVisibility(View.VISIBLE);
                    wrongLayout.setVisibility(View.INVISIBLE);
                } else {
                    rightLayout.setVisibility(View.INVISIBLE);
                    wrongLayout.setVisibility(View.VISIBLE);
                    btnSubmit.setEnabled(false);
                    updateBaseInfoProgress.setVisibility(View.VISIBLE);
                    cuotiEntity entity = new cuotiEntity();
                    entity.setId(resultEntity.getId());
                    entity.setQuestion(resultEntity.getQuestion());
                    entity.setAnswer(resultEntity.getAnswer());
                    entity.setItem1(resultEntity.getItem1());
                    entity.setItem2(resultEntity.getItem2());
                    entity.setItem3(resultEntity.getItem3());
                    entity.setItem4(resultEntity.getItem4());
                    entity.setExplains(resultEntity.getExplains());
                    entity.setUrl(resultEntity.getUrl());
                    entity.setUserID(MyApplication.getThisUser().getObjectId());
                    entity.setKemu(kemu);
                    if (!kemu.equals("4"))
                        entity.setType(MyApplication.jiazhaoID);
                    CuotiUtil.insertExec(entity, handler, MyApplication.jiazhaoID, kemu);
                }
                break;
        }
    }
}
