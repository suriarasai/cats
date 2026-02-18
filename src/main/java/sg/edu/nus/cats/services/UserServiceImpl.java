package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.Role;
import sg.edu.nus.cats.model.User;
import sg.edu.nus.cats.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findUser(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	@Transactional
	public User createUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional
	public User changeUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	@Transactional
	public void removeUser(User user) {
		userRepository.delete(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findRolesForUser(String userId) {
		return userRepository.findById(userId)
				.map(User::getRoleSet)
				.orElse(List.of());
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findRoleNamesForUser(String userId) {
		return userRepository.findById(userId)
				.map(User::getRoleSet)
				.orElse(List.of())
				.stream()
				.map(Role::getName)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findManagerNameByUID(String userId) {
		return userRepository.findManagerNameByUID(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> authenticate(String uname, String pwd) {
		return userRepository.findUserByNamePwd(uname, pwd);
	}

}
