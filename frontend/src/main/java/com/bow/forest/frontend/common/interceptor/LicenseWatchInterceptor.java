package com.bow.forest.frontend.common.interceptor;

import com.bow.forest.frontend.common.enums.LicenseStatus;
import com.bow.forest.frontend.service.ILicenseCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vv on 2016/8/1.
 */
public class LicenseWatchInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ILicenseCheckService licenseService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LicenseStatus status = licenseService.checkLicense(request.getRequestURI());
        if(status==LicenseStatus.EXPIRED||status==LicenseStatus.INACTIVE){
            response.sendRedirect("showLicense");
            return false;
        }else if(status==LicenseStatus.TRIAL){
            request.setAttribute("licenseStatus",LicenseStatus.TRIAL);
            return true;
        }
        return true;
    }
}
