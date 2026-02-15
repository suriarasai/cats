package sg.edu.iss.cats.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.cats.model.User;

/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/

public interface UserRepository extends JpaRepository<User, String> {
	
	// Improved with explicit JOIN
	@Query("SELECT DISTINCT e2.name FROM User u " +
	       "JOIN Employee e1 ON u.employeeId = e1.employeeId " +
	       "JOIN Employee e2 ON e1.managerId = e2.employeeId " +
	       "WHERE u.userId = :uid")
	List<String> findManagerNameByUserId(@Param("uid") String uid);
    
	// SECURITY WARNING: This should be replaced with Spring Security
	// Do NOT store or compare plain passwords
	// Use findByUsername and let Spring Security handle password verification
	
	Optional<User> findByName(String username);
	
	// Using password directly for simplicity of learning; do not do this in real-life projects
	@Query("SELECT u FROM User u WHERE u.name = :un AND u.password = :pwd")
	Optional<User> findUserByNameAndPassword(@Param("un") String username, @Param("pwd") String password);
}
