package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.Department;
import sg.edu.nus.cats.repositories.DepartmentRepository;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Department> findDepartment(String did) {
		return departmentRepository.findById(did);
	}

	@Override
	@Transactional
	public Department createDepartment(Department dep) {
		return departmentRepository.saveAndFlush(dep);
	}

	@Override
	@Transactional
	public Department changeDepartment(Department dep) {
		return departmentRepository.saveAndFlush(dep);
	}

	@Override
	@Transactional
	public void removeDepartment(Department dep) {
		departmentRepository.delete(dep);
	}

}
