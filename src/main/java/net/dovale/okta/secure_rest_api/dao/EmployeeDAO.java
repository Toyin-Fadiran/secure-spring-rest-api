package net.dovale.okta.secure_rest_api.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.dovale.okta.secure_rest_api.model.Employee;
import net.dovale.okta.secure_rest_api.model.Employees;

@Repository
public class EmployeeDAO {

	private static Employees list = new Employees();

	private Map<Integer, Employee> hashMap = new HashMap<>();

	static {
		list.getEmployeeList().add(new Employee(1, "Lebron", "James", "hoopdreams@gmail.com", 100_000));
		list.getEmployeeList().add(new Employee(2, "Joseph", "Biden", "whitehouse@gmail.com", 76_000));
		list.getEmployeeList().add(new Employee(3, "Salil", "Parekh", "CEO@gmail.com", 250_000));
	}

	public Employees getAllEmployees() {
		return list;
	}

	public void addEmployee(Employee employee) {
		list.getEmployeeList().add(employee);
	}

	public Employee getEmployeeByID(int id) {

		list.getEmployeeList().stream().filter(employee -> employee.getId() != id);

		for (int i = 0; i < list.getEmployeeList().size(); i++) {
			hashMap.put(list.getEmployeeList().get(i).getId(), list.getEmployeeList().get(i));

		}
		return hashMap.get(id);

	}

	public boolean deleteEmployee(int employeeID) {

		return list.getEmployeeList().removeIf(employee -> employee.getId() == employeeID);
	}

}
