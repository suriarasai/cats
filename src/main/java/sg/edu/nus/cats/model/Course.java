package sg.edu.nus.cats.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.cats.helper.CourseEventEnum;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
public class Course {

	@Id
	@Column(name = "courseid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	@Column(name = "employeeid")
	private String employeeId;

	@Column(name = "coursename")
	private String courseName;

	@Column(name = "organiser")
	private String organiser;

	@Column(name = "fromdate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fromDate;

	@Column(name = "todate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate toDate;

	@Column(name = "fees")
	private double fees;

	@Column(name = "gstincluded")
	private boolean gstIncluded;

	@Column(name = "justification")
	private String justification;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CourseEventEnum status;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<CourseEvent> courseEvent = new ArrayList<>();

	public Course(int courseId) {
		this.courseId = courseId;
	}

	public Course(int courseId, String employeeId, String courseName, String organiser,
			LocalDate fromDate, LocalDate toDate, double fees, boolean gstIncluded,
			String justification, CourseEventEnum status, List<CourseEvent> events) {
		this.courseId = courseId;
		this.employeeId = employeeId;
		this.courseName = courseName;
		this.organiser = organiser;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fees = fees;
		this.gstIncluded = gstIncluded;
		this.justification = justification;
		this.status = status;
		this.courseEvent.addAll(events);
	}

	public void addCourseEvent(CourseEvent ce) {
		this.courseEvent.add(ce);
	}

}
