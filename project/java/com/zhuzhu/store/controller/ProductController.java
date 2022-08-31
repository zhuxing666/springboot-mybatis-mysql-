package com.zhuzhu.store.controller;

import com.zhuzhu.store.entity.Product;
import com.zhuzhu.store.service.ProductService;
import com.zhuzhu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    @RequestMapping("hot_list")
    public JsonResult getHotList()
    {
        List<Product> data = productService.findHotList();
        return new JsonResult(OK,data);
    }

    @GetMapping("/{id}/details")
    public JsonResult findById(@PathVariable("id") Integer id)
    {
        Product data = productService.findById(id);

        return new JsonResult(OK,data);
    }
}
