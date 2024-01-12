package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.UserDao;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService   {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 1、保存用户
     */
    public void save(User user){
        String id = idWorker.nextId() + "";
        user.setPassword("123456");
        user.setEnableState(1);
        user.setId(id);
        //保存
        userDao.save(user);
    }
    /**
     * 更新用户
     */

    public void update(User user){
        //查询用户
        User target = userDao.findById(user.getId()).get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        //更新
        userDao.save(target);
    }
    /**
     * 3、根据id查询用户
     */
    public User findById(String id){
        return userDao.findById(id).get();
    }
    /**
     * 4、查询全部用户列表
     * 参数：map集合的形式
     * hasDept
     * departmentId
     * companyId
     */
    public Page<User> findAll(Map<String,Object> map,int page,int size){
        //1、需要查询条件
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list =  new ArrayList<>();
                //根据请求的companyId是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(cb.equal(root.get("companyId").as(String.class),(String) map.get("companyId")));
                }
                //根据请求的部门id是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(cb.equal(root.get("departmentId").as(String.class),(String) map.get("departmentId")));
                }
                //根据请求的hasDept判断  是否分配部门 0未分配(department=null) 1分配
                if(!StringUtils.isEmpty(map.get("hasDept"))){
                    if("0".equals((String) map.get("hasDept"))){
                        list.add(cb.isNull(root.get("departmentId")));
                    }else {
                        list.add(cb.isNotNull(root.get("departmentId")));
                    }
                }

                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //2、分页
        Page<User> pageUser = userDao.findAll(spec, new PageRequest(page-1, size));
        return pageUser;
    }
    /**
     * 5、根据id删除用户
     */
    public void delete(String id){
        userDao.deleteById(id);
    }
}
