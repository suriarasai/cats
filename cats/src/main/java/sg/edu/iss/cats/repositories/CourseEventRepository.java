package sg.edu.iss.cats.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.cats.model.Course;
import sg.edu.iss.cats.model.CourseEvent;

/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/

public interface CourseEventRepository extends JpaRepository<CourseEvent, Integer> {
	
	
    // Pass the entire Course object instead of just the ID
    List<CourseEvent> findByCourseOrderByEventDateDesc(Course course);
	

	
}