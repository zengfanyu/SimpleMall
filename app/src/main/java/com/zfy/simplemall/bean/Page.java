package com.zfy.simplemall.bean;

import java.util.List;

/**
 * Created by ZFY on 2017/04/13.
 *
 * @function:
 */

public class Page {
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;

    private List<Wares> list;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Wares> getList() {
        return list;
    }

    public void setList(List<Wares> list) {
        this.list = list;
    }
}
