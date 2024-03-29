package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.handler.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.*;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionApiDao permissionApiDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 1、保存权限
     */
    public void save(Map<String,Object> map) throws Exception {
        //设置主键Id
        String id = idWorker.nextId() + "";
        //1、通过map构造permission对象
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        perm.setId(id);
        //2、根据类型构造不同的资源对象(菜单、按钮、api）
        int type = perm.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //3、保存
        permissionDao.save(perm);
    }
    /**
     * 2、更新权限
     */

    public void update( Map<String,Object> map) throws Exception {
        Permission perm = BeanMapUtils.mapToBean(map, Permission.class);
        String id = perm.getId();
        //1、通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescription(perm.getDescription());
        permission.setEnVisible(perm.getEnVisible());
        //2、根据类型构造不同的资源
        int type = perm.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        //3、查询不同的资源设置修改的属性
        //4、更新仅限，更新资源
        permissionDao.save(permission);
    }
    /**
     * 3、根据id查询
     * //1、查询权限
     * //2、根据权限的类型查询资源
     * //3、构造map
     */
    public Map<String,Object> findById(String id) throws CommonException {
        Permission perm = permissionDao.findById(id).get();
        int type = perm.getType();
        Object object = null;
        if(type==PermissionConstants.PY_MENU){
            object = permissionMenuDao.findById(id);
        } else if (type == PermissionConstants.PY_POINT) {
            object = permissionPointDao.findById(id);
        } else if (type == PermissionConstants.PY_API) {
            object = permissionApiDao.findById(id);
        }else {
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String,Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());
        return map;
    }
    /**
     * 4、查询全部
     * type 查询全部权限列表type:0:菜单+按钮(权限点) 1:菜单 2：按钮(权限点) 3:API接口
     * enVisible: 是否查询全部权限 0：查询所有saas平台的最高权限 1：只查询企业所属权限
     * pid :父id
     */
    public List<Permission> findAll(Map<String,Object> map){
        //1、需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list =  new ArrayList<>();
                //根据请求的pid是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("pid"))){
                    list.add(cb.equal(root.get("pid").as(String.class),(String) map.get("pid")));
                }
                //根据enVisible是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(cb.equal(root.get("enVisible").as(String.class),(String) map.get("enVisible")));
                }
                //根据请求的类型type查询
                if(!StringUtils.isEmpty(map.get("type"))){
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = cb.in(root.get("type"));
                    if("0".equals(ty)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(ty));
                    }
                }

                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //2、分页
        return permissionDao.findAll(spec);
    }
    /**
     * 5、根据id删除用户
     */
    public void delete(String id) throws CommonException {
        //1、通过传递的权限id查询权限
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        //2、根据类型构造不同的资源
        int type = permission.getType();
        switch (type){
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }
}
