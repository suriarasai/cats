package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import sg.edu.nus.cats.model.Role;
import sg.edu.nus.cats.model.User;

public interface UserService {

	List<User> findAllUsers();

	Optional<User> findUser(String userId);

	User createUser(User user);

	User changeUser(User user);

	void removeUser(User user);

	List<Role> findRolesForUser(String userId);

	List<String> findRoleNamesForUser(String userId);

	List<String> findManagerNameByUID(String userId);

	Optional<User> authenticate(String uname, String pwd);

}
