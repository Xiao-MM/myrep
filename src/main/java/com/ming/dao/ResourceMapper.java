package com.ming.dao;

import com.ming.pojo.Resource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceMapper extends Mapper<Resource> {
    @Select({"<script>" +
            "select * from resource where " +
            "id in <foreach item = 'resourceId' collection = 'resourceIds' open='(' separator=',' close=')'>" +
            "</foreach>" +
            "</script>"})
    List<Resource> findResourcesInResourceIds(List<Integer> resourceIds);
}
