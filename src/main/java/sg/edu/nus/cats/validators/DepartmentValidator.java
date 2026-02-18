package sg.edu.nus.cats.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.model.Department;

@Component
@Slf4j
public class DepartmentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Department.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Department department = (Department) target;
		ValidationUtils.rejectIfEmpty(errors, "departmentId", "error.department.departmentId.empty");
		ValidationUtils.rejectIfEmpty(errors, "managerInCharge", "error.department.managerInCharge.empty");
		log.debug("Validating department: {}", department);
	}

}
