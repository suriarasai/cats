package sg.edu.nus.cats.controllers;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sg.edu.nus.cats.helper.Approve;
import sg.edu.nus.cats.helper.CourseEventEnum;
import sg.edu.nus.cats.model.Course;
import sg.edu.nus.cats.model.CourseEvent;
import sg.edu.nus.cats.model.Employee;
import sg.edu.nus.cats.services.CourseService;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

	private final CourseService cService;

	@RequestMapping("/pending")
	public ModelAndView pendingApprovals(HttpSession session) {
		var usession = (UserSession) session.getAttribute("usession");
		log.debug("User session: {}", usession);

		if (usession == null || usession.getUser() == null) {
			return new ModelAndView("login");
		}

		// Build employee -> pending courses map using streams
		Map<Employee, List<Course>> pendingHistory = usession.getSubordinates().stream()
				.collect(Collectors.toMap(
						employee -> employee,
						employee -> cService.findPendingCoursesByEID(employee.getEmployeeId()),
						(a, b) -> a,
						LinkedHashMap::new));

		var mav = new ModelAndView("manager-pending-course-history");
		mav.addObject("pendinghistory", pendingHistory);
		return mav;
	}

	@RequestMapping("/shistory")
	public ModelAndView subordinatesHistory(HttpSession session) {
		var usession = (UserSession) session.getAttribute("usession");

		if (usession == null || usession.getUser() == null || usession.getSubordinates() == null) {
			return new ModelAndView("login");
		}

		// Build subordinate -> courses map using streams
		Map<Employee, List<Course>> submap = usession.getSubordinates().stream()
				.collect(Collectors.toMap(
						subordinate -> subordinate,
						subordinate -> cService.findCoursesByEID(subordinate.getEmployeeId()),
						(a, b) -> a,
						LinkedHashMap::new));

		var mav = new ModelAndView("subordinates-course-history");
		mav.addObject("submap", submap);
		return mav;
	}

	@GetMapping("/course/display/{id}")
	public ModelAndView displayCoursePage(@PathVariable int id) {
		return cService.findCourse(id)
				.map(course -> {
					var mav = new ModelAndView("manager-course-detail", "course", course);
					mav.addObject("approve", new Approve());
					return mav;
				})
				.orElse(new ModelAndView("login"));
	}

	@PostMapping("/course/edit/{id}")
	public ModelAndView approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve,
			BindingResult result, @PathVariable Integer id, HttpSession session) {

		var usession = (UserSession) session.getAttribute("usession");

		if (result.hasErrors()) {
			return new ModelAndView("manager-course-detail");
		}

		cService.findCourse(id).ifPresent(course -> {
			var eventType = approve.getDecision().trim().equalsIgnoreCase(CourseEventEnum.APPROVED.toString())
					? CourseEventEnum.APPROVED
					: CourseEventEnum.REJECTED;

			var ce = new CourseEvent();
			ce.setEventType(eventType);
			ce.setEventBy(usession.getEmployee().getEmployeeId());
			ce.setComment(approve.getComment());
			ce.setTimeStamp(LocalDate.now());
			ce.setCourse(course);

			course.setStatus(eventType);
			course.addCourseEvent(ce);
			cService.changeCourse(course);
			log.info("Course {} was successfully {}.", course.getCourseId(), eventType);
		});

		return new ModelAndView("forward:/manager/pending");
	}

}
