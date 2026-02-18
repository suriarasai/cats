package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.Course;

public interface CourseService {

	List<Course> findAllCourses();

	Optional<Course> findCourse(Integer ceid);

	Course createCourse(Course course);

	Course changeCourse(Course course);

	void removeCourse(Course course);

	List<Course> findCoursesByEID(String eid);

	List<Course> findPendingCoursesByEID(String eid);

}
