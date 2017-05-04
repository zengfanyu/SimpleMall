package com.zfy.simplemall.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.listener.onToolbarLeftButtonClickListener;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;

/**
 * 自定义的ToolBar
 * Created by zfy on 2017/4/8.
 */

public class SearchToolBar extends Toolbar {
    private LayoutInflater mInflater;
    private EditText mSearchView;
    private TextView mTitleView;
    private Button mRightImageBtn;
    private View mToolbarContentView;
    private Button mLeftImageBtn;
    private boolean isShowSearchView;
    private Drawable mRightIcon;
    private Drawable mLeftIcon;

    public SearchToolBar(Context context) {
        this(context, null);
    }

    public SearchToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置padding
        setContentInsetsRelative(10, 10);
        //拿到自定义的属性
        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.SearchToolBar, defStyleAttr, 0);
            mRightIcon = a.getDrawable(R.styleable.SearchToolBar_rightButtonIcon);
            mLeftIcon = a.getDrawable(R.styleable.SearchToolBar_leftButtonIcon);
            isShowSearchView = a.getBoolean(R.styleable.SearchToolBar_isShowSearchView, false);
            a.recycle();
        }
        initView();
        initListener();
    }

    private void initListener() {
        mLeftImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftButtonClickListener != null) {
                    mLeftButtonClickListener.onClick();
                }
            }
        });
        mRightImageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightButtonClickListener != null) {
                    mRightButtonClickListener.onClick(v);
                }
            }
        });
    }

    private void initView() {
        if (mToolbarContentView == null) {
            mInflater = LayoutInflater.from(getContext());
            mToolbarContentView = mInflater.inflate(R.layout.toolbar_tab, null);
            mSearchView = (EditText) mToolbarContentView.findViewById(R.id.toolbar_search_et);
            mTitleView = (TextView) mToolbarContentView.findViewById(R.id.toolbar_title_tv);
            mRightImageBtn = (Button) mToolbarContentView.findViewById(R.id.toolbar_rightButton);
            mLeftImageBtn = (Button) mToolbarContentView.findViewById(R.id.toolbar_leftButton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mToolbarContentView, lp);
        }

        if (mRightIcon != null) {
            setRightButtonIcon(mRightIcon);

        }
        if (mLeftIcon != null) {
            setLeftButtonIcon(mLeftIcon);

        }
        if (isShowSearchView) {
            setShowSearchView();
        } else {
            setHideSearchView();
        }
    }

    public void setRightButtonIcon(Drawable rightButtonIcon) {
        if (mRightImageBtn != null) {
            mRightImageBtn.setBackground(rightButtonIcon);
            mRightImageBtn.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonIcon(int iconId) {
        setRightButtonIcon(getResources().getDrawable(iconId));
    }

    public void setLeftButtonIcon(Drawable leftButtonIcon) {
        if (mLeftImageBtn != null) {
            mLeftImageBtn.setBackground(leftButtonIcon);
        }
    }

    private onToolbarLeftButtonClickListener mLeftButtonClickListener;

    private onToolbarRightButtonClickListener mRightButtonClickListener;

    public void setRightButtonOnClickListener(onToolbarRightButtonClickListener listener) {
        mRightButtonClickListener = listener;
    }

    public void setLeftButtonOnClickListener(onToolbarLeftButtonClickListener listener) {
        mLeftButtonClickListener = listener;
    }

    public Button getLeftButton() {
        if (mLeftImageBtn != null) {
            return mLeftImageBtn;
        }
        return null;
    }

    public Button getRightButton() {
        if (mRightImageBtn != null) {
            return mRightImageBtn;
        }
        return null;
    }

    //重写Toolbar的setTitle方法
    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        mTitleView.setText(title);
        showTitleView();

    }

    public void setShowSearchView() {
        showSearchView();
        hideTitleView();
        hideLeftButton();
        hideRightButton();
    }

    public void setHideSearchView() {
        hideSearchView();
        showTitleView();
        showLeftButton();
        showRightButton();
    }

    public void showSearchView() {
        isShowSearchView = true;
        mSearchView.setVisibility(VISIBLE);
    }

    public void hideSearchView() {
        isShowSearchView = false;
        mSearchView.setVisibility(GONE);
    }

    public void showTitleView() {
        mTitleView.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        mTitleView.setVisibility(GONE);
    }

    public void showLeftButton() {
        mLeftImageBtn.setVisibility(VISIBLE);
    }

    public void hideLeftButton() {
        mLeftImageBtn.setVisibility(GONE);
    }

    public void showRightButton() {
        mRightImageBtn.setVisibility(VISIBLE);
    }

    public void hideRightButton() {
        mRightImageBtn.setVisibility(GONE);
    }
}
