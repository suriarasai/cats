package sg.edu.iss.cats.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.cats.model.Employee;

/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	// Use Optional for single result to handle null cases better
	Optional<Employee> findByEmployeeId(String employeeId);
	
	// Derived query method
	List<Employee> findByManagerId(String managerId);
	
	// Improved query with explicit JOIN
	@Query("SELECT DISTINCT m FROM Employee e JOIN Employee m ON e.managerId = m.employeeId")
	List<Employee> findAllManagers();

	// Projection for just names
	@Query("SELECT DISTINCT m.name FROM Employee e JOIN Employee m ON e.managerId = m.employeeId")
	List<String> findAllManagerNames();
    
	// Clearer query with explicit relationship
	@Query("SELECT e FROM Employee e WHERE e.managerId = :managerId")
	List<Employee> findSubordinatesByManagerId(@Param("managerId") String managerId);
	
	// Projection interface approach
	interface EmployeeIdProjection {
		String getEmployeeId();
	}
	
	@Query("SELECT e.employeeId FROM Employee e")
	List<String> findAllEmployeeIds();
}