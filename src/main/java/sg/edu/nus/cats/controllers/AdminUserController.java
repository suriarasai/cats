package sg.edu.nus.cats.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.exception.UserNotFound;
import sg.edu.nus.cats.model.User;
import sg.edu.nus.cats.services.EmployeeService;
import sg.edu.nus.cats.services.RoleService;
import sg.edu.nus.cats.services.UserService;
import sg.edu.nus.cats.validators.UserValidator;

@Controller
@RequestMapping("/admin/user")
@SessionAttributes(value = "usession", types = UserSession.class)
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

	private final UserService uService;
	private final EmployeeService eService;
	private final RoleService rService;
	private final UserValidator uValidator;

	@InitBinder("user")
	private void initUserBinder(WebDataBinder binder) {
		binder.addValidators(uValidator);
	}

	@GetMapping("/create")
	public ModelAndView newUserPage() {
		var mav = new ModelAndView("user-new", "user", new User());
		mav.addObject("eidlist", eService.findAllEmployeeIDs());
		mav.addObject("roles", rService.findAllRoles());
		return mav;
	}

	@PostMapping("/create")
	public ModelAndView createNewUser(@ModelAttribute @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("user-new");
		}

		// Resolve role references using streams instead of Iterator loop
		List<sg.edu.nus.cats.model.Role> resolvedRoles = user.getRoleSet().stream()
				.map(role -> rService.findRole(role.getRoleId()))
				.flatMap(java.util.Optional::stream)
				.toList();

		user.setRoleSet(resolvedRoles);
		uService.createUser(user);
		return new ModelAndView("forward:/admin/user/list");
	}

	@RequestMapping("/list")
	public ModelAndView userListPage() {
		var mav = new ModelAndView("user-list");
		mav.addObject("userList", uService.findAllUsers());
		return mav;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editUserPage(@PathVariable String id) {
		var mav = new ModelAndView("user-edit");
		uService.findUser(id).ifPresent(user -> mav.addObject("user", user));
		mav.addObject("roles", rService.findAllRoles());
		return mav;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView editUser(@ModelAttribute @Valid User user, BindingResult result,
			@PathVariable String id) throws UserNotFound {
		if (result.hasErrors()) {
			return new ModelAndView("user-edit");
		}
		log.info("User {} was successfully updated.", user.getUserId());
		uService.changeUser(user);
		return new ModelAndView("forward:/admin/user/list");
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteUser(@PathVariable String id) throws UserNotFound {
		uService.findUser(id).ifPresent(user -> {
			uService.removeUser(user);
			log.info("The user {} was successfully deleted.", user.getUserId());
		});
		return new ModelAndView("forward:/admin/user/list");
	}

}
