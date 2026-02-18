package sg.edu.nus.cats.helper;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.cats.model.Course;
import sg.edu.nus.cats.model.Employee;

@Data
@NoArgsConstructor
public class EmployeeCourses {

	private Employee employee;
	private List<Course> courses;

}
