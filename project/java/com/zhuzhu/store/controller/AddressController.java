package com.zhuzhu.store.controller;

import com.zhuzhu.store.entity.Address;
import com.zhuzhu.store.service.AdressService;
import com.zhuzhu.store.service.DistrictService;
import com.zhuzhu.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {

    @Autowired
    private AdressService adressService;

    @Autowired
    private DistrictService districtService;

    @RequestMapping("/addNewaddress")
    public JsonResult addNewaddress(HttpSession session, Address address) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        adressService.addNewaddress(uid, username, address);
        return new JsonResult(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult findByUid(HttpSession session) {
        Integer uid = getuidFromSession(session);
        List<Address> data = adressService.findByUid(uid);
        return new JsonResult(OK, data);
    }

    @RequestMapping("/{aid}/set_default")
    public JsonResult setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        adressService.setDefault(aid, uid, username);
        return new JsonResult(OK);
    }

    @RequestMapping("/{aid}/delete")
    public JsonResult delete(@PathVariable("aid") Integer aid, HttpSession session) {
        String username = getUsernameFromSession(session);
        Integer uid = getuidFromSession(session);
        adressService.delete(aid, uid, username);
        return new JsonResult(OK);
    }
}
