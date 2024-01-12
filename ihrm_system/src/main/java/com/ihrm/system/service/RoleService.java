package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 1、保存角色
     */
    public void save(Role role){
        String id = idWorker.nextId() + "";
        role.setId(id);
        roleDao.save(role);
    }
    /**
     * 2、查询全部角色
     */
    public Page<Role> findAll(String companyId,int page,int pagesize){
        Page<Role> all = roleDao.findAll(getSpec(companyId), PageRequest.of(page - 1, pagesize));
        return all;
    }
    /**
     * 3、根据Id查询用户
     */
    public Role findById(String id){
        return roleDao.findById(id).get();
    }
    /**
     * 4、更新角色
     */
    public void update(Role role){
        Role target = roleDao.findById(role.getId()).get();
        target.setName(role.getName());
        target.setDescription(role.getDescription());
        roleDao.save(target);
    }
    /**
     * 5、删除用户
     */
    public void delete(String id){
        roleDao.deleteById(id);
    }
}
