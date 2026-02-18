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
import sg.edu.nus.cats.exception.EmployeeNotFound;
import sg.edu.nus.cats.model.Employee;
import sg.edu.nus.cats.services.EmployeeService;
import sg.edu.nus.cats.validators.EmployeeValidator;

@Controller
@RequestMapping("/admin/employee")
@SessionAttributes(value = "usession", types = UserSession.class)
@RequiredArgsConstructor
@Slf4j
public class AdminEmployeeController {

	private final EmployeeService eService;
	private final EmployeeValidator eValidator;

	@InitBinder("employee")
	private void initEmployeeBinder(WebDataBinder binder) {
		binder.addValidators(eValidator);
	}

	@GetMapping("/create")
	public ModelAndView newEmployeePage() {
		var mav = new ModelAndView("employee-new", "employee", new Employee());
		mav.addObject("eidlist", eService.findAllEmployeeIDs());
		return mav;
	}

	@PostMapping("/create")
	public ModelAndView createNewEmployee(@ModelAttribute @Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("employee-new");
		}
		log.info("New employee {} was successfully created.", employee.getEmployeeId());
		eService.createEmployee(employee);
		return new ModelAndView("forward:/admin/employee/list");
	}

	@RequestMapping("/list")
	public ModelAndView employeeListPage() {
		var mav = new ModelAndView("employee-list");
		mav.addObject("employeeList", eService.findAllEmployees());
		return mav;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editEmployeePage(@PathVariable String id) {
		var mav = new ModelAndView("employee-edit");
		eService.findEmployee(id).ifPresent(emp -> mav.addObject("employee", emp));
		mav.addObject("eidlist", eService.findAllEmployeeIDs());
		return mav;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView editEmployee(@ModelAttribute @Valid Employee employee, BindingResult result,
			@PathVariable String id) throws EmployeeNotFound {
		if (result.hasErrors()) {
			return new ModelAndView("employee-edit");
		}
		log.info("Employee {} was successfully updated.", employee.getEmployeeId());
		eService.changeEmployee(employee);
		return new ModelAndView("forward:/admin/employee/list");
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deleteEmployee(@PathVariable String id) throws EmployeeNotFound {
		eService.findEmployee(id).ifPresent(employee -> {
			eService.removeEmployee(employee);
			log.info("The employee {} was successfully deleted.", employee.getEmployeeId());
		});
		return new ModelAndView("forward:/admin/employee/list");
	}

}
