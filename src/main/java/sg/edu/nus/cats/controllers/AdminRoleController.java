package sg.edu.nus.cats.controllers;

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
import sg.edu.nus.cats.exception.RoleNotFound;
import sg.edu.nus.cats.model.Role;
import sg.edu.nus.cats.services.RoleService;
import sg.edu.nus.cats.validators.RoleValidator;

@Controller
@RequestMapping("/admin/role")
@SessionAttributes(value = "usession", types = UserSession.class)
@RequiredArgsConstructor
@Slf4j
public class AdminRoleController {

	private final RoleService rService;
	private final RoleValidator rValidator;

	@InitBinder("role")
	private void initRoleBinder(WebDataBinder binder) {
		binder.addValidators(rValidator);
	}

	@GetMapping("/create")
	public ModelAndView newRolePage() {
		return new ModelAndView("role-new", "role", new Role());
	}

	@PostMapping("/create")
	public ModelAndView createNewRole(@ModelAttribute @Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("role-new");
		}
		log.info("New role {} was successfully created.", role.getRoleId());
		rService.createRole(role);
		return new ModelAndView("forward:/admin/role/list");
	}

	@RequestMapping("/list")
	public ModelAndView roleListPage() {
		var mav = new ModelAndView("role-list");
		mav.addObject("roleList", rService.findAllRoles());
		return mav;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editRolePage(@PathVariable String id) {
		var mav = new ModelAndView("role-edit");
		rService.findRole(id).ifPresent(role -> mav.addObject("role", role));
		return mav;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView editRole(@ModelAttribute @Valid Role role, BindingResult result,
			@PathVariable String id) throws RoleNotFound {
		if (result.hasErrors()) {
			return new ModelAndView("role-edit");
		}
		log.info("Role {} was successfully updated.", role.getRoleId());
		rService.changeRole(role);
		return new ModelAndView("forward:/admin/role/list");
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteRole(@PathVariable String id) throws RoleNotFound {
		rService.findRole(id).ifPresent(role -> {
			rService.removeRole(role);
			log.info("The role {} was successfully deleted.", role.getRoleId());
		});
		return new ModelAndView("forward:/admin/role/list");
	}

}
