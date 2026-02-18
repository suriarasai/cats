package sg.edu.nus.cats.controllers;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.exception.CourseNotFound;
import sg.edu.nus.cats.helper.CourseEventEnum;
import sg.edu.nus.cats.model.Course;
import sg.edu.nus.cats.model.CourseEvent;
import sg.edu.nus.cats.services.CourseEventService;
import sg.edu.nus.cats.services.CourseService;
import sg.edu.nus.cats.validators.CourseValidator;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

	private final CourseService cService;
	private final CourseValidator cValidator;
	private final CourseEventService ceService;

	@InitBinder("course")
	private void initCourseBinder(WebDataBinder binder) {
		binder.addValidators(cValidator);
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}

	@RequestMapping("/history")
	public String employeeCourseHistory(HttpSession session, Model model) {
		var usession = (UserSession) session.getAttribute("usession");

		if (usession == null || usession.getUser() == null) {
			return "forward:/home/login";
		}

		log.debug("Employee: {}", usession.getEmployee());

		List<Course> courses = cService.findCoursesByEID(usession.getEmployee().getEmployeeId());
		if (!courses.isEmpty()) {
			model.addAttribute("chistory", courses);
		}
		return "staff-course-history";
	}

	@GetMapping("/course/create")
	public ModelAndView newCoursePage() {
		var mav = new ModelAndView("staff-course-new");
		mav.addObject("course", new Course());
		return mav;
	}

	@PostMapping("/course/create")
	public ModelAndView createNewCourse(@ModelAttribute @Valid Course course, BindingResult result,
			HttpSession session) {

		var usession = (UserSession) session.getAttribute("usession");

		if (result.hasErrors()) {
			return new ModelAndView("staff-course-new");
		}

		log.info("New course {} was successfully created.", course.getCourseId());

		course.setEmployeeId(usession.getEmployee().getEmployeeId());
		course.setStatus(CourseEventEnum.SUBMITTED);

		var ce = new CourseEvent();
		ce.setCourse(course);
		ce.setEventBy(usession.getEmployee().getEmployeeId());
		ce.setEventType(CourseEventEnum.SUBMITTED);
		ce.setTimeStamp(LocalDate.now());

		cService.createCourse(course);
		return new ModelAndView("forward:/staff/history");
	}

	@GetMapping("/course/edit/{id}")
	public ModelAndView editCoursePage(@PathVariable Integer id) {
		return cService.findCourse(id)
				.map(course -> {
					var mav = new ModelAndView("staff-course-edit");
					mav.addObject("course", course);
					return mav;
				})
				.orElse(new ModelAndView("forward:/staff/history"));
	}

	@PostMapping("/course/edit/{id}")
	public ModelAndView editCourse(@ModelAttribute @Valid Course course, BindingResult result,
			@PathVariable Integer id, HttpSession session) throws CourseNotFound {

		var usession = (UserSession) session.getAttribute("usession");

		if (result.hasErrors()) {
			return new ModelAndView("staff-course-edit");
		}

		log.info("Course {} dates: {} - {}", course.getCourseId(), course.getFromDate(), course.getToDate());

		course.setEmployeeId(usession.getEmployee().getEmployeeId());
		course.setStatus(CourseEventEnum.UPDATED);

		var ce = new CourseEvent();
		ce.setCourse(course);
		ce.setEventBy(usession.getEmployee().getEmployeeId());
		ce.setEventType(CourseEventEnum.UPDATED);
		ce.setTimeStamp(LocalDate.now());
		course.addCourseEvent(ce);

		cService.changeCourse(course);
		return new ModelAndView("forward:/staff/history");
	}

	@GetMapping("/course/withdraw/{id}")
	public ModelAndView deleteCourse(@PathVariable Integer id, HttpSession session) throws CourseNotFound {
		var usession = (UserSession) session.getAttribute("usession");

		cService.findCourse(id).ifPresent(course -> {
			log.info("Course {} was successfully withdrawn.", course.getCourseId());

			course.setStatus(CourseEventEnum.WITHDRAWN);

			var ce = new CourseEvent();
			ce.setCourse(course);
			ce.setEventBy(usession.getEmployee().getEmployeeId());
			ce.setEventType(CourseEventEnum.WITHDRAWN);
			ce.setTimeStamp(LocalDate.now());

			cService.changeCourse(course);
			ceService.createCourseEvent(ce);
		});

		return new ModelAndView("forward:/staff/history");
	}

}
