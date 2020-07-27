package com.ming.service;

import com.ming.pojo.Resource;

import java.util.List;

public interface ResourceService {

    void addResource(Resource resource);

    void delResource(Integer resourceId);

    void updateResource(Resource resource);

    Resource findResource(Integer resourceId);

    List<Resource> findResources();
}
