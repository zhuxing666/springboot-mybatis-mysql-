package com.zhuzhu.store.service;

import com.zhuzhu.store.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    /**
     * 查询热销产品前4名
     * @return
     */
    List<Product> findHotList();

    /**
     *  根据商品id查询商品信息
     * @param id
     * @return 商品对象
     */
    Product findById(Integer id);

}
