package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.Employee;

import java.util.List;

/**
 * Created by lu on 2016/7/2.
 */
public class AssistantListResposne {
    private List<Employee> employeeList;

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
