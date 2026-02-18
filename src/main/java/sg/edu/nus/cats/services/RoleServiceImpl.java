package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.Role;
import sg.edu.nus.cats.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Role> findRole(String roleId) {
		return roleRepository.findById(roleId);
	}

	@Override
	@Transactional
	public Role createRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	@Transactional
	public Role changeRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	@Transactional
	public void removeRole(Role role) {
		roleRepository.delete(role);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findAllRolesNames() {
		return roleRepository.findAllRolesNames();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}

}
