package com.zhuzhu.store.service.imp;

import com.zhuzhu.store.entity.Address;
import com.zhuzhu.store.mapper.AddressMapper;
import com.zhuzhu.store.service.AdressService;
import com.zhuzhu.store.service.DistrictService;
import com.zhuzhu.store.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdressServiceImp implements AdressService {
    @Autowired
    public AddressMapper addressMapper;

    @Autowired
    private DistrictService districtService;

    @Value("${user.adress.max-count}")
    private Integer maxCount;

    @Override
    public void addNewaddress(Integer uid, String username, Address address) {
        // 统计当前用户的收货地址个数
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AdressCountLimitException("超过收货地址的最大值20个");
        }

        // 对address对象中的数据进行补全：省市区
        String ProvinceName = districtService.findNameByCode(address.getProvinceCode());
        String cityName = districtService.findNameByCode(address.getCityCode());
        String AreaName = districtService.findNameByCode(address.getAreaCode());

        // uid isDelete0
        address.setUid(uid);
        address.setProvinceName(ProvinceName);
        address.setCityName(cityName);
        address.setAreaName(AreaName);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        // 补全日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());


        // 插入收货地址
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户收货地址产生未知的异常");
        }

    }

    @Override
    public List<Address> findByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
//            address.setAid(null);
//            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setIsDefault(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("用户的收货地址不存在");
        }
        // 监测当前获取的收货地址的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("用户访问非法异常，收货地址不匹配");
        }

        // 先将所有地址设为非默认 0
        Integer rows = addressMapper.updateNodefault(uid);
        if (rows < 1) {
            throw new updateException("跟新数据异常");
        }
        // 将用户地址设为1
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new updateException("跟新数据异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("用户地址信息不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new deleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            // 终止程序
            return;
        }
        // 将最后修改的数据设为1 即为默认
        Address address = addressMapper.findLastModified(uid);

        if (result.getIsDefault() == 1) {
            rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        }

        if (rows != 1) {
            throw new updateException("更新数据产生未知的异常");
        }


    }
}
