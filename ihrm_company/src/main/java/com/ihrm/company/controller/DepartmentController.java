package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.response.DeptListResult;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域
@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department){
        //1、设置保存企业的id
        department.setCompanyId(companyId);
        //2、调用service完成保存企业
        departmentService.save(department);
        //3、构造返回结果
        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 根据指定企业id,查询企业的部门列表
     */
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
        Company company = companyService.findById(companyId);
        List<Department> list = departmentService.findAll(companyId);
        DeptListResult deptListResult = new DeptListResult(company,list);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }
    /**
     * 根据id查询department
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Department dept = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,dept);
    }
    /**
     * 修改Department
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody Department department){
        //修改部门id
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 根据id删除部门
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") String id){
        departmentService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
