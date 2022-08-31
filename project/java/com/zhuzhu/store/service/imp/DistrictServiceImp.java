package com.zhuzhu.store.service.imp;

import com.zhuzhu.store.entity.District;
import com.zhuzhu.store.mapper.DistrictMapper;
import com.zhuzhu.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImp implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        /**
         * 将无效值设为null,提高性能
         */
        for (District d : list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String findNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
