package com.zhuzhu.store.service;

import com.zhuzhu.store.entity.District;

import java.util.List;

public interface DistrictService {
    /**
     *
     * @param parent  父代码编号
     * @return 多个区域信息
     */
    List<District> findByParent(String parent);

    /**
     * 根据code查找名字
     * @param code
     * @return
     */
    String findNameByCode(String code);
}
