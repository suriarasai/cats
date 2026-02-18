package sg.edu.nus.cats.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.model.User;

@Component
@Slf4j
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmpty(errors, "userId", "error.user.userid.empty");
		ValidationUtils.rejectIfEmpty(errors, "employeeId", "error.user.employeeid.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "error.user.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.user.password.empty");
		log.debug("Validating user: {}", user);
	}

}
