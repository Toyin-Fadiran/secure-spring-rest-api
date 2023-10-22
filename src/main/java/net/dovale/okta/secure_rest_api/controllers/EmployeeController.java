package net.dovale.okta.secure_rest_api.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.dovale.okta.secure_rest_api.dao.EmployeeDAO;
import net.dovale.okta.secure_rest_api.model.Employee;
import net.dovale.okta.secure_rest_api.model.EmployeeIDWrapper;
import net.dovale.okta.secure_rest_api.model.Employees;

@RestController
@RequestMapping(path = "/employees/v1")
public class EmployeeController {

	@Autowired
	private EmployeeDAO employeeDao;


	/**
	 * Method for retrieving employee metadata
	 * 
	 * @return employee
	 */
	@PreAuthorize("hasAnyAuthority('SCOPE_employee', 'SCOPE_admin')")
	@PostMapping(path = "/getmyemp", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getInvidualEmployee(@RequestBody EmployeeIDWrapper employeeId) {
		int id = employeeId.getId();
		
		List<Integer> integerList = employeeDao.getAllEmployees().getEmployeeList().stream().map(x -> x.getId())
				.collect(Collectors.toList());

		if (!integerList.contains(id)) {

			// Handle the case when the Employee or its ID is not provided
			return ResponseEntity.badRequest().body("Invalid request. Please provide a valid Employee with an ID.");
		}
		Employee employee = employeeDao.getEmployeeByID(id);

		return ResponseEntity.ok(employee);
	}

	
	/**
	 * Method to return total list of employee and their metadata
	 * 
	 * @return EmployeeList
	 */
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@GetMapping(path = "/getallemp", produces = "application/json")
	public Employees getEmployees() {
		return employeeDao.getAllEmployees();
	}
	

	/**
	 * Method to add a new employee with required metadata
	 * 
	 * @return SuccessfulCreation of employee
	 */	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@PostMapping(path = "/addemp", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
		Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
		employee.setId(id);

		employeeDao.addEmployee(employee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId())
				.toUri();

		// Return the response with HTTP status 201 (Created) and the 'Location' header
        return ResponseEntity.created(location).body("Employee created successfully.");
	}

	
	/**
	 * Method to add a new employee with required metadata
	 * 
	 * @return Successful deletion of employee
	 */	
	@PreAuthorize("hasAuthority('SCOPE_admin')")
	@DeleteMapping(path = "/deleteemp", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> deleteEmployee(@RequestBody Employee employee) {

		if (employee == null || employee.getId() == null) {
			// Handle the case when the Employee or its ID is not provided
			return ResponseEntity.badRequest().body("Invalid request. Please provide a valid Employee with an ID.");
		}

		int employeeToDelete = employee.getId();

		boolean deleteSuccessful = employeeDao.deleteEmployee(employeeToDelete);

		if (deleteSuccessful) {
			return ResponseEntity.ok("Record deleted successfully");
		} else {
			return ResponseEntity.badRequest().body("Error during Deletion Request. Please Contact Admin");
		}

	}

}
