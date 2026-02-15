package sg.edu.iss.cats.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.cats.model.Course;

/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/



public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	// Derived query method (no @Query needed)
	List<Course> findByEmployeeId(String employeeId);
	
	// More specific status filtering with IN clause
	List<Course> findByEmployeeIdAndStatusIn(String employeeId, List<String> statuses);
	
	// FIXED: Native query parameter index (was ?0, should be ?1)
	@Query(value = "SELECT * FROM course WHERE status = ?1", nativeQuery = true)
	List<Course> findByStatus(String status);
	
	// Alternative using JPQL (preferred over native query)
	@Query("SELECT c FROM Course c WHERE c.status = :status")
	List<Course> findByStatusJPQL(@Param("status") String status);
	
	// Or simply use derived query method:
	List<Course> findCoursesByStatus(String status);
	
	
	@Query("SELECT c from Course c WHERE c.employeeId = :eid AND (c.status ='SUBMITTED' OR c.status ='UPDATED')")
	List<Course> findPendingCoursesByEmployeeId(@Param("eid") String eid);
	
	@Query(value = "SELECT * FROM course WHERE status = ?1", nativeQuery = true)
	List<Course> findPendingCoursesByStatus(String status);
	
	
}