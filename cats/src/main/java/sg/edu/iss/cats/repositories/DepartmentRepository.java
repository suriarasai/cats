package sg.edu.iss.cats.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.cats.model.Department;


/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/



public interface DepartmentRepository extends JpaRepository<Department, String> {
	
	// Option 1: Keep existing query but use List instead of ArrayList
	@Query("SELECT d.departmentId FROM Department d")
	List<String> findDepartmentNames();
	
	// Option 2: Use projection interface for type safety
	interface DepartmentIdProjection {
		String getDepartmentId();
	}
	
	List<DepartmentIdProjection> findAllProjectedBy();
	
	// Option 3: If Department entity has proper getId(), you can use:
	// List<String> findAllDepartmentIds(); with custom implementation
}