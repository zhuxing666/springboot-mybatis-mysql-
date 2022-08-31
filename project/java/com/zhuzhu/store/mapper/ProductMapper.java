package com.zhuzhu.store.mapper;

import com.zhuzhu.store.entity.Product;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    /**
     * 查询热销排行榜前4名
     *
     * @return
     */
    @Select("select * from t_product where  status=1 order by priority DESC LIMIT 0,4")
    @Results(id = "productresultMap", value = {

            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "item_type", property = "itemType"),
            @Result(column = "sell_point", property = "sellPoint"),
            @Result(column = "created_user", property = "createdUser"),
            @Result(column = "created_time", property = "createdTime"),
            @Result(column = "modified_user", property = "modifiedUser"),
            @Result(column = "modified_time", property = "modifiedTime")
    })
    List<Product> findHotList();


    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */

    @Select("select * from t_product where id=#{id}")
    @ResultMap("productresultMap")
    Product findById(Integer id);

}
