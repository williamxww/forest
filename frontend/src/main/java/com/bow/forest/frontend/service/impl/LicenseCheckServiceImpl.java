package com.bow.forest.frontend.service.impl;

import com.bow.forest.frontend.common.constant.LicenseStatus;
import com.bow.forest.frontend.entity.LicenseItem;
import com.bow.forest.frontend.service.ILicenseCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vv on 2016/8/1.
 */
@Service
public class LicenseCheckServiceImpl implements ILicenseCheckService {

    private static final Logger logger = LoggerFactory.getLogger(LicenseCheckServiceImpl.class);

    private Map<String,LicenseItem> licenseItems;
    @Override
    public LicenseStatus checkLicense(String requestUrl) {
        Map<String,LicenseItem> items = getLicense();
        if(items==null||items.size()==0){
            throw new RuntimeException("can't retrieve license from remote end");
        }
        if("/forest/login".equals(requestUrl)){
            return LicenseStatus.EXPIRED;
        }
        if("/updateWebUnit".equals(requestUrl)){
            return LicenseStatus.LIMIT_EXCEED;
        }
        //LicenseItem duration =  items.get("time");

        return LicenseStatus.ACTIVE;
    }

    private Map<String,LicenseItem> getLicense(){
        if(licenseItems==null){
            synchronized (this){
                if(licenseItems==null){
                    licenseItems = getRemoteLicense();
                }
            }
        }
        return licenseItems;
    }

    private Map<String,LicenseItem> getRemoteLicense(){
        Map<String,LicenseItem> lic = new HashMap<String,LicenseItem>();
        LicenseItem item = new LicenseItem();
        lic.put("vv",item);
        return lic;
    }

    private void updateLicense(){
        //调用后台服务更新license
        //然后刷新前台的license
        synchronized (this){
            licenseItems = getRemoteLicense();
        }
    }
}
