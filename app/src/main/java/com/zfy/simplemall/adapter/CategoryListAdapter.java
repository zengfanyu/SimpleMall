package com.zfy.simplemall.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.CategoryBean;

import java.util.List;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function: CategoryFragment中一级菜单的RecyclerView的Adapter
 */

public class CategoryListAdapter extends BaseAdapter<CategoryBean> {
    public CategoryListAdapter(List<CategoryBean> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, CategoryBean categoryBean) {
        TextView tvCategoryName = holder.getView(R.id.id_category_name_tv);
        tvCategoryName.setText(categoryBean.getName());
    }
}
