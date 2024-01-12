package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/sys")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    //保存角色
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result save(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }
    //查询全部角色列表
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findAll(int page,int pagesize){
        Page<Role> all = roleService.findAll(companyId, page, pagesize);
        PageResult<Role> pageResult = new PageResult<>(all.getTotalElements(),all.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }
    /**
     * 查询用户
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public Result findById(String id){
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS,role);
    }
    /**
     * 更新用户
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id")String id,@RequestBody Role role){
        role.setId(id);
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }
    /**
     * 删除用户
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id){
        roleService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
