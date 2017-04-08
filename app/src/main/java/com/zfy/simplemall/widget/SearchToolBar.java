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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zfy.simplemall.R;

/**
 * 自定义的ToolBar
 * Created by zfy on 2017/4/8.
 */

public class SearchToolBar extends Toolbar {
    private LayoutInflater mInflater;
    private EditText mSearchView;
    private TextView mTitleView;
    private ImageButton mRightImageBtn;
    private View mView;

    public SearchToolBar(Context context) {
        this(context, null);
    }

    public SearchToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        setContentInsetsRelative(10,10);
        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.SearchToolBar, defStyleAttr, 0);


            final Drawable rightIcon = a.getDrawable(R.styleable.SearchToolBar_rightButtonIcon);
            if (rightIcon != null) {
//                setNavigationIcon(navIcon);
                setRightButtonIcon(rightIcon);

            }

            boolean isShowSearchView=a.getBoolean(R.styleable.SearchToolBar_isShowSearchView,false);
            if (isShowSearchView){
                showSearchView();
                hideTitleView();
            }
            a.recycle();
        }
    }

    private void initView() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar_tab, null);
            mSearchView = (EditText) mView.findViewById(R.id.et_toolbar_search);
            mTitleView = (TextView) mView.findViewById(R.id.tv_toolbar_title);
            mRightImageBtn = (ImageButton) mView.findViewById(R.id.ib_toolbar_right);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }

    public void setRightButtonIcon(Drawable icon) {
        if (mRightImageBtn != null) {
            mRightImageBtn.setImageDrawable(icon);
            mRightImageBtn.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        mRightImageBtn.setOnClickListener(listener);
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

    public void showSearchView() {
        mSearchView.setVisibility(VISIBLE);
    }

    public void hideSearchView() {
        mSearchView.setVisibility(GONE);
    }

    public void showTitleView() {
        mTitleView.setVisibility(VISIBLE);
    }

    public void hideTitleView() {
        mTitleView.setVisibility(GONE);
    }
}
