package com.zfy.simplemall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.TabBean;
import com.zfy.simplemall.fragment.CartFragment;
import com.zfy.simplemall.fragment.CategoryFragment;
import com.zfy.simplemall.fragment.HomeFragment;
import com.zfy.simplemall.fragment.HotFragment;
import com.zfy.simplemall.fragment.MineFragment;
import com.zfy.simplemall.listener.onToolbarLeftButtonClickListener;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.widget.SearchToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<TabBean> mTabs = new ArrayList<>(5);
    private SearchToolBar mToolBar;

    //1.使用FragmentTabHost需要Activity继承FragmentActivity，AppCompatActivity已经继承了FragmentActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        initTab();
    }

    private void intiView() {
        mToolBar= (SearchToolBar) findViewById(R.id.search_tool_bar);
        mToolBar.setLeftButtonOnClickListener(new onToolbarLeftButtonClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this,"LeftButtonOnClick",Toast.LENGTH_SHORT).show();
                mToolBar.showSearchView();
                mToolBar.hideTitleView();
            }
        });
        mToolBar.setRightButtonOnClickListener(new onToolbarRightButtonClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(MainActivity.this,"RightButtonOnClick",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initTab() {
        TabBean homeTab = new TabBean(R.string.home, R.drawable.selector_icon_home, HomeFragment.class);
        TabBean hotTab = new TabBean(R.string.hot, R.drawable.selector_icon_hot, HotFragment.class);
        TabBean categoryTab = new TabBean(R.string.category, R.drawable.selector_icon_categoy, CategoryFragment.class);
        TabBean cartTab = new TabBean(R.string.cart, R.drawable.selector_icon_cart, CartFragment.class);
        TabBean mineTab = new TabBean(R.string.mine, R.drawable.selector_icon_mine, MineFragment.class);
        mTabs.add(homeTab);
        mTabs.add(cartTab);
        mTabs.add(categoryTab);
        mTabs.add(hotTab);
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