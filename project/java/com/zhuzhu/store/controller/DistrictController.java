package com.zhuzhu.store.controller;

import com.zhuzhu.store.entity.District;
import com.zhuzhu.store.service.DistrictService;
import com.zhuzhu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.jar.JarEntry;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{
    @Autowired
    private DistrictService districtService;

   @RequestMapping({"/",""})
    public JsonResult getByParent(String parent)
    {
        List<District> data = districtService.findByParent(parent);
        return new JsonResult(OK,data);
    }
}
