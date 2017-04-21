package com.zfy.simplemall.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfy.simplemall.R;

/**
 * Created by ZFY on 2017/04/18.
 *
 * @function:购物车中自定义的组合控件
 */

public class CartItemCombinationLayout extends LinearLayout implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Button mAddButton;
    private Button mSubButton;
    private TextView mNumTextView;
    private int value;
    private int minValue;
    private int maxValue;
    private onButtonClickListener mListener;

    public CartItemCombinationLayout(Context context) {
        this(context, null);
    }

    public CartItemCombinationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CartItemCombinationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CartItemCombinationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mInflater = LayoutInflater.from(context);
        initView();

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.CartItemCombinationLayout, defStyleAttr, 0);
            value = a.getInteger(R.styleable.CartItemCombinationLayout_value, 1);
            maxValue = a.getInteger(R.styleable.CartItemCombinationLayout_maxValue, 1);
            minValue = a.getInteger(R.styleable.CartItemCombinationLayout_minValue, 1);
            Drawable btnBgDrawable = a.getDrawable(R.styleable.CartItemCombinationLayout_btnDrawable);
            if (btnBgDrawable != null) {
                mAddButton.setBackground(btnBgDrawable);
                mSubButton.setBackground(btnBgDrawable);
            }
            Drawable tvBgDrawable = a.getDrawable(R.styleable.CartItemCombinationLayout_textViewBg);
            if (tvBgDrawable != null) {
                mNumTextView.setBackground(tvBgDrawable);
            }
            a.recycle();
        }
    }

    private void initView() {
        View view = mInflater.inflate(R.layout.widget_number_add_sub, this, true);
        mAddButton = (Button) view.findViewById(R.id.id_add_btn);
        mSubButton = (Button) view.findViewById(R.id.id_sub_btn);
        mNumTextView = (TextView) view.findViewById(R.id.id_num_tv);

        mAddButton.setOnClickListener(this);
        mSubButton.setOnClickListener(this);
    }

    public int getValue() {
        String val = mNumTextView.getText().toString();
        if (!TextUtils.isEmpty(val)) {
            this.value = Integer.parseInt(val);
        }

        return value;
    }

    public void setValue(int value) {
        mNumTextView.setText(value + "");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.id_add_btn) {
            numberAdd();
            if (mListener != null) {
                mListener.onButtonAddClick(v, value);
            }
        } else if (v.getId() == R.id.id_sub_btn) {
            numberSub();
            if (mListener != null) {
                mListener.onButtonSubClick(v, value);
            }
        }
    }

    private void numberAdd() {
        getValue();
        if (value < maxValue) {
            ++value;
        }
        mNumTextView.setText(value + "");
    }

    private void numberSub() {
        getValue();
        if (value > minValue) {
            --value;
        }
        mNumTextView.setText(value + "");
    }

    public interface onButtonClickListener {
        void onButtonAddClick(View view, int value);

        void onButtonSubClick(View view, int value);
    }

    public void setOnButtonClickListener(onButtonClickListener listener) {
        mListener = listener;
    }
}
