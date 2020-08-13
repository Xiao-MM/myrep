package com.ming.vo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class PageVO<T> implements Serializable {
    private int pageNum;
    private int pageSize;
    private int size;
    private long total;
    private List<T> list;

    public PageVO(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.list = page;
            this.size = page.size();
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.list = list;
            this.size = list.size();
            this.total = (long) list.size();
        }

    }
}