package com.ming.utils;

import com.github.pagehelper.PageHelper;
import com.ming.dto.PageDTO;

/**
 * 重写分页方法
 */
public class PageUtil {
    public static void startPage(PageDTO pageDTO){
        if (pageDTO==null){
            pageDTO =new PageDTO();
        }
        PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
    }
}
