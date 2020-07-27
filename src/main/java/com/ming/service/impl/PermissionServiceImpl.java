package com.ming.service.impl;

import com.ming.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void reload() {

    }


}
