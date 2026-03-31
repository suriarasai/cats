package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.Course;
import sg.edu.nus.cats.repositories.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

	@Autowired CourseRepository courseRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Course> findCourse(Integer ceid) {
		return courseRepository.findById(ceid);
	}

	@Override
	@Transactional
	public Course createCourse(Course course) {
		return courseRepository.saveAndFlush(course);
	}

	@Override
	@Transactional
	public Course changeCourse(Course course) {
		return courseRepository.saveAndFlush(course);
	}

	@Override
	@Transactional
	public void removeCourse(Course course) {
		courseRepository.delete(course);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findCoursesByEID(String eid) {
		return courseRepository.findCoursesByEID(eid);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findPendingCoursesByEID(String eid) {
		return courseRepository.findPendingCoursesByEID(eid);
	}

}
