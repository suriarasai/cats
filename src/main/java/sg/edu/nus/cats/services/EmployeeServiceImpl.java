package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.Employee;
import sg.edu.nus.cats.repositories.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired EmployeeRepository employeeRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findEmployeesByManager(String s) {
		return employeeRepository.findEmployeesByManagerId(s);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> findEmployeeById(String s) {
		return employeeRepository.findEmployeeById(s);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> findEmployee(String empid) {
		return employeeRepository.findById(empid);
	}

	@Override
	@Transactional
	public Employee createEmployee(Employee emp) {
		return employeeRepository.saveAndFlush(emp);
	}

	@Override
	@Transactional
	public Employee changeEmployee(Employee emp) {
		return employeeRepository.saveAndFlush(emp);
	}

	@Override
	@Transactional
	public void removeEmployee(Employee emp) {
		employeeRepository.delete(emp);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findAllManagerNames() {
		return employeeRepository.findAllManagerNames();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAllManagers() {
		return employeeRepository.findAllManagers();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Employee> findSubordinates(String employeeId) {
		return employeeRepository.findSubordinates(employeeId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findAllEmployeeIDs() {
		return employeeRepository.findAllEmployeeIDs();
	}

}
