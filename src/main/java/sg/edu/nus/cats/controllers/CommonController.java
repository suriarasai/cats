package sg.edu.nus.cats.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.model.User;
import sg.edu.nus.cats.services.EmployeeService;
import sg.edu.nus.cats.services.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {

	private final UserService uService;
	private final EmployeeService eService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/home")
	public String logic(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping("/home/authenticate")
	public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
			Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "login";
		}

		return uService.authenticate(user.getName(), user.getPassword())
				.map(authenticatedUser -> {
					var usession = new UserSession();
					usession.setUser(authenticatedUser);

					authenticatedUser.getRoleSet()
							.forEach(role -> log.debug("User role: {}", role));

					eService.findEmployeeById(authenticatedUser.getEmployeeId())
							.ifPresent(usession::setEmployee);

					var subordinates = eService.findSubordinates(authenticatedUser.getEmployeeId());
					if (subordinates != null) {
						usession.setSubordinates(subordinates);
					}

					session.setAttribute("usession", usession);
					return "forward:/staff/history";
				})
				.orElse("login");
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

}
