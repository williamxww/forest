package com.bow.forest.frontend.service;

import com.bow.forest.frontend.common.constant.LicenseStatus;

/**
 * Created by vv on 2016/8/1.
 */
public interface ILicenseCheckService {

    /**
     * 对请求进行license校验
     * @param requestUrl
     */
    public LicenseStatus checkLicense(String requestUrl);
}
