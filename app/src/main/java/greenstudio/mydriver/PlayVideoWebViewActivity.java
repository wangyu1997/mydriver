package greenstudio.mydriver;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import greenstudio.mydriver.util.AppManager;

/**
 * Created by wangyu on 23/02/2017.
 */

public class PlayVideoWebViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbara)
    TextView tvToolbara;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FrameLayout videoview;
    private WebView webView;
    private xWebChromeClient xwebchromeclient;
    private boolean islandport;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private View xCustomView;
    private String url, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉应用标题
        AppManager.getAppManager().addActivity(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_video_web_view);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("about:blank");
                finish();
            }
        });

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        tvToolbara.setText(title);
        initwidget();
        webView.getSettings().setJavaScriptEnabled(true);//启用js
        webView.getSettings().setBlockNetworkImage(false);
        webView.loadUrl(url);
    }

    private void initwidget() {
        videoview = (FrameLayout)

                findViewById(R.id.video_view);

        webView = (WebView)

                findViewById(R.id.video_webview);

        WebSettings ws = webView.getSettings();
        /**
         * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
         * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
         * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
         * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
         * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
         * setSupportZoom 设置是否支持变焦
         * */
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);// 启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        ws.setBlockNetworkImage(true);// 显示网络图像
        xwebchromeclient = new

                xWebChromeClient();

        webView.setWebChromeClient(xwebchromeclient);
        webView.setWebViewClient(new xWebViewClientent());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            } else {

                webView.loadUrl("about:blank");
                // mTestWebView.loadData("", "text/html; charset=UTF-8", null);
                PlayVideoWebViewActivity.this.finish();
                Log.i("testwebview", "===>>>2");
            }
        }
        return false;
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }

    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     *
     * @author
     */
    public class xWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        // 播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view,
                                     CustomViewCallback callback) {
            if (islandport) {

            } else {

                // ii = "1";
                // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webView.setVisibility(View.GONE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
//            toolbar.setVisibility(View.GONE);
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        // 视频播放退出全屏会被调用的
        public void onHideCustomView() {

            if (xCustomView == null)// 不是全屏播放状态
                return;

            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
//            toolbar.setVisibility(View.VISIBLE);
            webView.setVisibility(View.VISIBLE);

            // Log.i(LOGTAG, "set it to webVew");
        }

        // 视频加载添加默认图标
        @Override
        public Bitmap getDefaultVideoPoster() {
            // Log.i(LOGTAG, "here in on getDefaultVideoPoster");
            if (xdefaltvideo == null) {
                xdefaltvideo = BitmapFactory.decodeResource(getResources(),
                        R.drawable.progress);
            }
            return xdefaltvideo;
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            // Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater.from(PlayVideoWebViewActivity.this);
//                xprogressvideo = inflater.inflate(
//                        R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }

        // 网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            (PlayVideoWebViewActivity.this).setTitle(title);
        }

        // @Override
        // //当WebView进度改变时更新窗口进度
        // public void onProgressChanged(WebView view, int newProgress) {
        // (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
        // newProgress*100);
        // }

    }

    /**
     * 处理各种通知、请求等事件
     *
     * @author
     */
    public class xWebViewClientent extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("webviewtest", "shouldOverrideUrlLoading: " + url);
            return false;
        }

    }

    /**
     * 当横竖屏切换时会调用该方法
     *
     * @author
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("testwebview", "=====<<<  onConfigurationChanged  >>>=====");
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("webview", "   现在是横屏1");
            islandport = false;
            toolbar.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("webview", "   现在是竖屏1");
            islandport = true;
            toolbar.setVisibility(View.VISIBLE);
        }
    }
}
