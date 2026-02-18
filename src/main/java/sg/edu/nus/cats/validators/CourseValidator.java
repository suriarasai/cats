package sg.edu.nus.cats.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.nus.cats.model.Course;

@Component
public class CourseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Course.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Course course = (Course) target;

		if (course.getFromDate() != null && course.getToDate() != null
				&& course.getFromDate().isAfter(course.getToDate())) {
			errors.reject("toDate", "End date should be greater than start date.");
			errors.rejectValue("toDate", "error.dates", "to date must be > from date");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseName", "error.courseName", "Course name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDate", "error.fromDate", "From Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate", "error.toDate", "To Date is required.");
	}

}
