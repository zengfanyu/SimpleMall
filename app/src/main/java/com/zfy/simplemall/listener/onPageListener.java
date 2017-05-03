package com.zfy.simplemall.listener;

import java.util.List;

/**
 * Created by ZFY on 2017/05/03.
 *
 * @function:用于分页工具的监听器
 */

public interface onPageListener {

    void load(List data, int pageSize, int curPage);

    void refresh(List data,int pageSize,int curPage);

    void loadMore(List data,int pageSize,int curPage);
}
