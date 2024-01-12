package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DepartmentService extends BaseService<Department> {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 1、保存部门
     */
    public void save(Department department){
        String id = idWorker.nextId() + "";
        department.setId(id);
        departmentDao.save(department);
    }
    /**
     * 2、更新部门
     */
    public void update(Department department){
        //1、根据id查询部门
        Department dept = departmentDao.findById(department.getId()).get();
        //2、设置部门属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        //3、更新部门
        departmentDao.save(department);
    }
    /**
     * 3、根据id查询部门
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }
    /**
     * 4、查询全部部门列表
     */
    public List<Department> findAll(String companyId){
//        Specification<Department> spec = new Specification<Department>() {
//            /**
//             * 构造查询条件
//             * @param root  包含所有对象属性
//             * @param cq 一般不用
//             * @param cb 构造查询条件
//             * @return
//             */
//            @Override
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                //根据企业id查询
//                return cb.equal(root.get("companyId").as(String.class),companyId);
//            }
//        };
        //getSpec公共方法
        return departmentDao.findAll(getSpec(companyId));
    }
    /**
     * 5、根据id删除部门
     */
    public void delete(String id){
        departmentDao.deleteById(id);
    }
}
