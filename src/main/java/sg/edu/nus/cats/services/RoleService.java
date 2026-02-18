package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.Role;

public interface RoleService {

	List<Role> findAllRoles();

	Optional<Role> findRole(String roleId);

	Role createRole(Role role);

	Role changeRole(Role role);

	void removeRole(Role role);

	List<String> findAllRolesNames();

	List<Role> findRoleByName(String name);

}
