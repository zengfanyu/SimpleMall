package com.zfy.simplemall.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.listener.onToolbarLeftButtonClickListener;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.TypeCastUtils;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.widget.SearchToolBar;

import java.io.Serializable;

/**
 * Created by ZFY on 2017/05/11.
 *
 * @function:商品详情页 与HTML交互
 */

public class WareDetailActivity extends AppCompatActivity implements onToolbarLeftButtonClickListener {

    private SearchToolBar mToolBar;
    private WebView mWebView;
    private WebAppInterface mWebAppInterface;
    private Wares mWares;
    private CartProvider mCartProvider;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_detail);
        Serializable serializableExtra = getIntent().getSerializableExtra(Constant.EXTRA_WARE_NAME);
        if (serializableExtra == null) {
            ToastUtils.showToast(getApplicationContext(), "没有拿到EXTRA");
        }
        mWares = (Wares) serializableExtra;
        mCartProvider = CartProvider.getInstance(this);
        initToolBar();
        initWebView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWebView.restoreState(savedInstanceState);
    }

    private void initToolBar() {
        mToolBar = (SearchToolBar) findViewById(R.id.id_tool_bar);
        mToolBar.setLeftButtonOnClickListener(this);
    }

    private void initWebView() {
        mContainer = (FrameLayout) findViewById(R.id.id_webView_container);
        mWebView = new WebView(getApplication());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        mContainer.addView(mWebView);
        WebSettings settings = mWebView.getSettings();
        //开启JavaScript脚本功能
        settings.setJavaScriptEnabled(true);
        //不阻塞网络图片
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);
        mWebView.loadUrl(Constant.URL_WARE_DETAIL);
        mWebAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mWebAppInterface, "appInterface");
        mWebView.setWebViewClient(new DetailWebViewClient());
    }


    @Override
    public void onClick() {
        finish();
    }

    /**
     * 主要用于辅助WebView处理各种通知和请求的类
     *
     * @author zfy
     * @created at 2017/5/11/011 12:12
     */
    class DetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebAppInterface.showDetail();
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return false;
        }

    }

    class WebAppInterface {
        private Context mContext;

        public WebAppInterface(Context context) {
            this.mContext = context;
        }

        /**
         * 调用前端的方法，此方法一定要在主线程中调用
         *
         * @param
         * @return
         * @author zfy
         * @created at 2017/5/11/011 11:22
         */
        @JavascriptInterface
        public void showDetail() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail(" + mWares.getId() + ")");
                }
            });
        }

        /**
         * 供前端调用的购买的方法
         *
         * @param
         * @return
         * @author zfy
         * @created at 2017/5/11/011 11:29
         */
        @JavascriptInterface
        public void buy(int id) {
            mCartProvider.put(TypeCastUtils.WaresToShoppingCart(mWares));
            ToastUtils.showToast(mContext, "已添加至购物车");
        }

        /**
         * 供前端调用的添加至购物车的方法
         *
         * @param
         * @return
         * @author zfy
         * @created at 2017/5/11/011 11:28
         */
        @JavascriptInterface
        public void addToCart(int id) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContainer.removeView(mWebView);
        mWebView.stopLoading();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
