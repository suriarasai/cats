package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.CourseEvent;

public interface CourseEventService {

	List<CourseEvent> findAllCourseEvents();

	Optional<CourseEvent> findCourseEvent(Integer ceid);

	CourseEvent createCourseEvent(CourseEvent courseEvent);

	CourseEvent changeCourseEvent(CourseEvent courseEvent);

	void removeCourseEvent(CourseEvent courseEvent);

}
