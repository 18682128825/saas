package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.handler.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class PermissionController extends BaseController {
    @Autowired
    private PermissionService permissionService;
    /**
     * 保存
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map){
//        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }
    //查询列表
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(@RequestParam  Map map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS);
    }

    //修改
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Map map) throws Exception {
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id查询
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) throws CommonException {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id删除
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result detele(@PathVariable("id") String id){
        return new Result(ResultCode.SUCCESS);
    }
}
