package com.ihrm.company.response;

import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts; //公司联系人

    public DeptListResult(Company company,List depts){
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative(); //公司联系人
        this.depts = depts;
    }
}
