package com.zfy.simplemall.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.listener.onToolbarLeftButtonClickListener;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.TypeCastUtils;
import com.zfy.simplemall.utils.toastutils.ToastUtils;
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

    private void initToolBar() {
        mToolBar = (SearchToolBar) findViewById(R.id.id_tool_bar);
        mToolBar.setLeftButtonOnClickListener(this);
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.id_web_view);
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

    class DetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mWebAppInterface.showDetail();
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
}
