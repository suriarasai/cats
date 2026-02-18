package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.Employee;

public interface EmployeeService {

	List<Employee> findEmployeesByManager(String s);

	Optional<Employee> findEmployeeById(String s);

	List<Employee> findAllEmployees();

	Optional<Employee> findEmployee(String empid);

	Employee createEmployee(Employee emp);

	Employee changeEmployee(Employee emp);

	void removeEmployee(Employee emp);

	List<String> findAllManagerNames();

	List<Employee> findAllManagers();

	List<Employee> findSubordinates(String employeeId);

	List<String> findAllEmployeeIDs();

}
