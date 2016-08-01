package com.bow.forest.frontend.controller;

import com.bow.forest.frontend.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vv on 2016/7/31.
 */
@RestController
public class DemoController {


    @RequestMapping("/forward/{jspName}")
    public String forward(@PathVariable String jspName){
        return "demo/"+jspName;
    }

    @RequestMapping("/getUser/{name}")
    public User getUser(@PathVariable String name){
        User u = new User();
        u.setName(name);
        u.setAge(26);
        return u;
    }

    @RequestMapping("/upload")
    public void uploadFile(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
//      String rawName = file.getOriginalFilename();
//      File source = new File(localfileName.toString());
//      multipartFile.transferTo(source);
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = file.getInputStream();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhh24mmss");
            String fn = "upload"+format.format(date);
            File tmpFile = new File(fn);
            System.out.println(tmpFile.getAbsolutePath());
            fos = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len=is.read(buffer))!=-1){
                fos.write(buffer,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(is!=null) {
                    is.close();
                }
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
