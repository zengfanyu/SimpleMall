package com.zfy.simplemall.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.TabBean;
import com.zfy.simplemall.fragment.CartFragment;
import com.zfy.simplemall.fragment.CategoryFragment;
import com.zfy.simplemall.fragment.HomeFragment;
import com.zfy.simplemall.fragment.HotFragment;
import com.zfy.simplemall.fragment.MineFragment;
import com.zfy.simplemall.widget.SearchToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<TabBean> mTabs = new ArrayList<>(5);
    private CartFragment mCartFragment;

    @Override
    protected int convertLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mToolBar = (SearchToolBar) findViewById(R.id.search_tool_bar);
        initTab();
    }


    private void initTab() {
        TabBean homeTab = new TabBean(R.string.home, R.drawable.selector_icon_home, HomeFragment.class);
        TabBean hotTab = new TabBean(R.string.hot, R.drawable.selector_icon_hot, HotFragment.class);
        TabBean categoryTab = new TabBean(R.string.category, R.drawable.selector_icon_categoy, CategoryFragment.class);
        TabBean cartTab = new TabBean(R.string.cart, R.drawable.selector_icon_cart, CartFragment.class);
        TabBean mineTab = new TabBean(R.string.mine, R.drawable.selector_icon_mine, MineFragment.class);
        mTabs.add(homeTab);
        mTabs.add(hotTab);
        mTabs.add(categoryTab);
        mTabs.add(cartTab);
        mTabs.add(mineTab);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //2.使用FragmentTabHost一定要调用setup（）方法
        mTabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        for (TabBean tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));

            tabSpec.setIndicator(buildIndicator(tab));

            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        //监听tab的切换，以便于根据不同的tab相应的操作Toolbar
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(getString(R.string.cart))) {
                    //在购物车页面需要隐藏Toolbar的搜索栏
                    mToolBar.setHideSearchView();

                    if (mCartFragment == null) {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
                        if (fragment != null) {
                            mCartFragment = (CartFragment) fragment;
                            mCartFragment.refreshData();
                        }
                    } else {
                        mCartFragment.refreshData();
                    }
                } else if (tabId.equals(getString(R.string.mine))) {
                    mToolBar.hideSearchView();
                    mToolBar.setTitle(R.string.mine);
                } else {
                    //在其他页面显示Toolbar的搜索栏
                    mToolBar.setShowSearchView();
                }
            }
        });
        //去掉Tab之间的分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //默认选择第1个
        mTabHost.setCurrentTab(0);
    }

    public View buildIndicator(TabBean tab) {
        View indicatorTabView = mInflater.inflate(R.layout.indicator_tab, null);
        ImageView ivTab = (ImageView) indicatorTabView.findViewById(R.id.iv_tab);
        TextView tvTab = (TextView) indicatorTabView.findViewById(R.id.tv_tab);

        ivTab.setBackgroundResource(tab.getIcon());
        tvTab.setText(tab.getTitle());
        return indicatorTabView;
    }
}
