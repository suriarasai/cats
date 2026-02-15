package sg.edu.iss.cats.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.cats.model.Role;

/**
* @version $Revision: 2.0 //Used Optional and newer Query interface
* @author Suria
*/


public interface RoleRepository extends JpaRepository<Role, String> {
	
	// Projection for names only
	@Query("SELECT r.name FROM Role r")
	List<String> findAllRoleNames();
	
	// Use Optional if expecting single result
	Optional<Role> findByName(String name);
	
	// If multiple roles can have same name, use List
	List<Role> findAllByName(String name);
}