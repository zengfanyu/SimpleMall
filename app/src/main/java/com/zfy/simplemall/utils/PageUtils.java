package com.zfy.simplemall.utils;

import android.content.Context;
import android.text.TextUtils;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.bean.Page;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.listener.onPageListener;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZFY on 2017/05/03.
 *
 * @function:用于分页的工具类（采用链式调用）
 */

public class PageUtils {
    private static Builder sBuilder;
    private int state = Constant.STATE_NORMAL;

    private PageUtils() {
        requestData();
        initRefreshLayout();
    }

    public static Builder Builder() {
        sBuilder = new Builder();
        return sBuilder;
    }

    public void updateParamsAndRequest() {
        requestData();
    }

    /**
     * 初始化refreshLayout
     */
    private void initRefreshLayout() {
        sBuilder.refreshLayout.setLoadMore(sBuilder.isLoadMore);
        sBuilder.refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.setLoadMore(sBuilder.isLoadMore);
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (sBuilder.curPage <= sBuilder.totalPage) {
                    loadMoreData();
                } else {
                    ToastUtils.showToast(sBuilder.context, "No more,sorry!");
                    materialRefreshLayout.finishRefreshLoadMore();
                    materialRefreshLayout.setLoadMore(false);
                }
            }
        });
    }

    /**
     * 刷新数据的方法
     */
    private void refreshData() {
        sBuilder.curPage = 1;
        state = Constant.STATE_REFRESH;
        requestData();
    }

    /**
     * 加载更多数据的方法
     */
    private void loadMoreData() {
        sBuilder.curPage = ++sBuilder.curPage;
        state = Constant.STATE_MORE;
        requestData();
    }

    /**
     * 更新请求参数的方法
     */
    private void updateRequestParams() {
        Map<String, String> paramsMap = sBuilder.params;
        paramsMap.put("curPage", sBuilder.curPage + "");
        paramsMap.put("pageSize", sBuilder.pageSize + "");
        sBuilder.setRequestParams(paramsMap);
    }

    /**
     * 请求服务端数据的方法
     */
    private void requestData() {
        updateRequestParams();

        CommonOkHttpClient.get(CommonRequest.createGetRequest(sBuilder.url,
                sBuilder.requestParams), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Page page = (Page) responseObj;
                sBuilder.curPage = page.getCurrentPage();
                sBuilder.pageSize = page.getPageSize();
                sBuilder.totalPage = page.getTotalCount() / sBuilder.pageSize + 1;
                Logger.d("curPage:" + sBuilder.curPage + ",pageSize:" + sBuilder.pageSize + ",totalPage" + sBuilder.totalPage);
                showData(page.getList(), sBuilder.pageSize, sBuilder.curPage);
            }

            @Override
            public void onFailure(Object reasonObj) {
                OkHttpException e = (OkHttpException) reasonObj;
                ToastUtils.showToast(sBuilder.context, e.getMessage());
            }
        }, Page.class));


    }


    /**
     * 根据当前状态去显示数据的方法
     */
    private void showData(List datas, int pageSize, int curPage) {
        switch (state) {
            case Constant.STATE_NORMAL: //一般状态，也就是第一次展示数据的时候，需要new一个adapter
                if (sBuilder.listener != null) {
                    //接口回调
                    sBuilder.listener.load(datas, pageSize, curPage);
                }
                break;
            case Constant.STATE_REFRESH://刷新数据的时候就不需要重新new一个adapter了，只需要刷新数据即可
                if (sBuilder.listener != null) {
                    //接口回调
                    sBuilder.listener.refresh(datas, pageSize, curPage);
                }
                sBuilder.refreshLayout.finishRefresh();
                break;
            case Constant.STATE_MORE://添加更多的数据
                if (sBuilder.listener != null) {
                    //接口回调
                    sBuilder.listener.loadMore(datas, pageSize, curPage);
                }
                sBuilder.refreshLayout.finishRefreshLoadMore();
                break;
            default:
                break;

        }

    }


    public static class Builder {
        private String url;
        private RequestParams requestParams;
        private int curPage = 1; //此处两个字段是要写进url中的，所以不能使用m
        private int pageSize = 10;
        private int totalPage;
        private MaterialRefreshLayout refreshLayout;
        private boolean isLoadMore;
        private onPageListener listener;
        private Context context;
        private HashMap<String, String> params = new HashMap<>(5);

        public Builder addParam(String key, String value) {
            params.put(key, value);
            return this;
        }

        public Builder setListener(onPageListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setLoadMore(boolean isLoadMore) {
            this.isLoadMore = isLoadMore;
            return this;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setRequestParams(Map<String, String> params) {
            this.requestParams = new RequestParams(params);
            return this;
        }

        public PageUtils build(Context context) {
            this.context = context;
            checkParams();
            return new PageUtils();
        }

        /**
         * 检测参数是否全部初始化的方法
         */
        private void checkParams() {
            if (context == null)
                throw new RuntimeException("content can't be null");
            if (TextUtils.isEmpty(url))
                throw new RuntimeException("url can't be null");
            if (refreshLayout == null)
                throw new RuntimeException("refresh can't be null");
        }
    }
}
