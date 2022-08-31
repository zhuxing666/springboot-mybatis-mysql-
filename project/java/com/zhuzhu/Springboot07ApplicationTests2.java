package com.zhuzhu;

import com.zhuzhu.store.entity.Address;
import com.zhuzhu.store.entity.District;
import com.zhuzhu.store.entity.User;
import com.zhuzhu.store.mapper.AddressMapper;
import com.zhuzhu.store.mapper.DistrictMapper;
import com.zhuzhu.store.mapper.UserMapper;
import com.zhuzhu.store.service.AdressService;
import com.zhuzhu.store.service.DistrictService;
import com.zhuzhu.store.service.UserService;
import com.zhuzhu.store.service.exception.ServiceException;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = Springboot07Application.class)
class Springboot07ApplicationTests2 {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AdressService adressService;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private DistrictService districtService;

    @Test
    void selectaddress() {
        Address address = new Address();
        address.setUid(32);
        address.setName("xiaoeka ");
        address.setPhone("5464554");
        address.setAddress("无第小美俄米");
        Integer rows = addressMapper.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    void selecByID() {
        Integer row = addressMapper.countByUid(18);
        System.out.println(row);
    }

    @Test
    void addAdress() {
        Address address = new Address();
        address.setName("姚曼男朋友zx");
        address.setPhone("17858805555");
        address.setAddress("雁塔区小寨华旗");
        adressService.addNewaddress(18, "姚曼妖精", address);
    }

    @Test
    void District() {
        List<District> byParent = districtMapper.findByParent("210100");
        for (District district : byParent) {
            System.out.println(district);
        }
    }


    @Test
    void Districtservice() {
        List<District> byParent = districtService.findByParent("86");
        for (District district : byParent) {
            System.out.println(district);
        }

    }

    @Test
    void selectadressByUid() {
        List<Address> addressList = addressMapper.findByUid(19);
        for (Address address : addressList) {
            System.out.println(address);
        }
    }

    @Test
    void test() {
        Address address = addressMapper.findByAid(12);
        System.out.println(address);
    }

    @Test
    void test1() {
        addressMapper.updateNodefault(19);
    }

    @Test
    void test3() {
        Integer integer = addressMapper.updateDefaultByAid(12, "团子", new Date());
    }

    @Test
    void test4() {
        adressService.setDefault(11, 19, "粽子");
    }


    @Test
    void deleteByUid() {
       addressMapper.deleteByAid(11);
    }

    @Test
    void findLastModified() {
        Address lastModified = addressMapper.findLastModified(19);
        System.out.println(lastModified);
    }

    @Test
    void delete()
    {
         adressService.delete(10,19,"修改人");
    }
}










