package com.bow.forest.frontend.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vv on 2016/8/1.
 */
@Controller
public class LicenseController {

    @RequestMapping("/login")
    public void login(HttpServletRequest request){
        Object status = request.getAttribute("licenseStatus");
        System.out.println(JSON.toJSONString(status));
    }

    @RequestMapping("/showLicense")
    public String showLicense(){
        return "license/showLicense";
    }
}
