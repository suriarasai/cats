package sg.edu.nus.cats.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.model.Employee;

@Component
@Slf4j
public class EmployeeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Employee employee = (Employee) target;
		ValidationUtils.rejectIfEmpty(errors, "employeeId", "error.employee.employeeid.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "error.employee.name.empty");
		log.debug("Validating employee: {}", employee);
	}

}
