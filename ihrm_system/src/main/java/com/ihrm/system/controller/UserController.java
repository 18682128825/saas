package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//允许跨域
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    /**
     * 保存
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }
    //查询用户列表
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map){
        //获取当前的企业id
        map.put("companyId",companyId);
        Page pageUser = userService.findAll(map, page, size);
        PageResult<User> pageResult = new PageResult<User>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    //修改User
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id查询
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }
    //根据id删除
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result detele(@PathVariable("id") String id){
        userService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
