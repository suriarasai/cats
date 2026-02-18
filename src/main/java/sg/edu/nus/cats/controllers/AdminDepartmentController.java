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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.exception.DepartmentNotFound;
import sg.edu.nus.cats.model.Department;
import sg.edu.nus.cats.services.DepartmentService;
import sg.edu.nus.cats.services.EmployeeService;
import sg.edu.nus.cats.validators.DepartmentValidator;

@Controller
@RequestMapping("/admin/department")
@RequiredArgsConstructor
@Slf4j
public class AdminDepartmentController {

	private final DepartmentService dService;
	private final EmployeeService eService;
	private final DepartmentValidator dValidator;

	@InitBinder("department")
	private void initDepartmentBinder(WebDataBinder binder) {
		binder.addValidators(dValidator);
	}

	@GetMapping("/create")
	public ModelAndView newDepartmentPage() {
		var mav = new ModelAndView("department-new", "department", new Department());
		mav.addObject("eidlist", eService.findAllEmployeeIDs());
		return mav;
	}

	@PostMapping("/create")
	public ModelAndView createNewDepartment(@ModelAttribute @Valid Department department, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("department-new");
		}
		dService.createDepartment(department);
		return new ModelAndView("forward:/admin/department/list");
	}

	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView departmentListPage() {
		var mav = new ModelAndView("department-list");
		mav.addObject("departmentList", dService.findAllDepartments());
		return mav;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editDepartmentPage(@PathVariable("id") String id) {
		var mav = new ModelAndView("department-edit");
		dService.findDepartment(id).ifPresent(dept -> mav.addObject("department", dept));
		mav.addObject("eidlist", eService.findAllEmployeeIDs());
		return mav;
	}

	@PostMapping("/edit/{id}")
	public ModelAndView editDepartment(@ModelAttribute @Valid Department department, BindingResult result,
			@PathVariable String id) throws DepartmentNotFound {
		if (result.hasErrors()) {
			return new ModelAndView("department-edit");
		}
		dService.changeDepartment(department);
		return new ModelAndView("forward:/admin/department/list");
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteDepartment(@PathVariable("id") String id) throws DepartmentNotFound {
		log.debug("Deleting department: {}", id);
		dService.findDepartment(id).ifPresent(dService::removeDepartment);
		return new ModelAndView("forward:/admin/department/list");
	}

}
