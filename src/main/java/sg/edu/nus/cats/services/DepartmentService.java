package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.Department;

public interface DepartmentService {

	List<Department> findAllDepartments();

	Optional<Department> findDepartment(String did);

	Department createDepartment(Department dep);

	Department changeDepartment(Department dep);

	void removeDepartment(Department dep);

}
