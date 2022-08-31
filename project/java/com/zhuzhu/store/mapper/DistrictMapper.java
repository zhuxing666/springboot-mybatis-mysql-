package com.zhuzhu.store.mapper;

import com.zhuzhu.store.entity.District;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/** 处理省/市/区数据的持久层接口 */
public interface DistrictMapper {
    /**
     * 根据附带好查询区域列表
     * @param parent
     * @return
     */
    @Select("select *from t_dict_district where parent=#{parent} order by code ASC")
    List<District> findByParent(String parent);

    /**
     * 根据code查找名字
     * @param code
     * @return
     */
    @Select("select name from t_dict_district where code=#{code}")
    String findNameByCode(String code);
}
