package com.ming.controller;

import com.ming.pojo.Resource;
import com.ming.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @ApiOperation("添加操作")
    @PostMapping("/add")
    public String addResource(@RequestBody Resource resource){
        resourceService.addResource(resource);
        return "添加操作成功！";
    }

    @ApiOperation("查看操作")
    @GetMapping("/find")
    public List<Resource> findResources(){
        return resourceService.findResources();
    }
}
