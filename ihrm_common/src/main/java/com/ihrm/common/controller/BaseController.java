package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
        //以后解决
        this.companyId = "1";
        this.companyName = "中汀国际";
    }
}
