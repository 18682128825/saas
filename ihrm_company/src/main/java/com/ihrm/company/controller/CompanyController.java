package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.handler.CommonException;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    //保存企业
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody Company company){
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }
    //根据Id更新企业
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody Company company){
        //业务操作
        company.setId(id);
        companyService.update(company);
        return new  Result(ResultCode.SUCCESS);
    }
    //根据Id删除企业
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
    //根据Id查询企业
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;
    }
    //查询全部企业列表
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll()  {
//        throw new CommonException(ResultCode.UNAUTHORISE);
        List<Company> companyList = companyService.findAll();
        return new Result(ResultCode.SUCCESS,companyList);
    }
}
