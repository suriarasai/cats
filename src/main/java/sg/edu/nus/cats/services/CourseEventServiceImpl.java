package sg.edu.nus.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.cats.model.CourseEvent;
import sg.edu.nus.cats.repositories.CourseEventRepository;

@Service
@RequiredArgsConstructor
public class CourseEventServiceImpl implements CourseEventService {

	@Autowired CourseEventRepository courseEventRepository;

	@Override
	@Transactional(readOnly = true)
	public List<CourseEvent> findAllCourseEvents() {
		return courseEventRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CourseEvent> findCourseEvent(Integer ceid) {
		return courseEventRepository.findById(ceid);
	}

	@Override
	@Transactional
	public CourseEvent createCourseEvent(CourseEvent courseEvent) {
		return courseEventRepository.saveAndFlush(courseEvent);
	}

	@Override
	@Transactional
	public CourseEvent changeCourseEvent(CourseEvent courseEvent) {
		return courseEventRepository.saveAndFlush(courseEvent);
	}

	@Override
	@Transactional
	public void removeCourseEvent(CourseEvent courseEvent) {
		courseEventRepository.delete(courseEvent);
	}

}
