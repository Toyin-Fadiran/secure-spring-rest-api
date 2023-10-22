package net.dovale.okta.secure_rest_api.model;

import java.util.ArrayList;
import java.util.List;

public class Employees {
	
	private ArrayList<Employee> employeeList;
    
    public ArrayList<Employee> getEmployeeList() {
        if(employeeList == null) {
            employeeList = new ArrayList<>();
        }
        return employeeList;
    }
  
    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

}
