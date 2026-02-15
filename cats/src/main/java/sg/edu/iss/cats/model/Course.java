package sg.edu.iss.cats.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Basic;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sg.edu.iss.cats.helper.CourseEventEnum;

/**
 * Course entity class with Envers auditing and Lombok
 * 
 * NOTE: For entities with relationships, we use more granular Lombok annotations
 * instead of @Data to avoid common JPA pitfalls
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "course")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  // Critical: Only use ID
@ToString(exclude = {"courseEvent"})  // Critical: Exclude collections to avoid lazy load issues
public class Course {
	
	@Id
	@Column(name = "courseid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include  // Only ID for equality
	private Integer courseId;
	
	@Basic
	@Column(name = "employeeid")
	private String employeeId;
	
	@Column(name = "coursename")
	private String courseName;
	
	@Column(name = "organiser")
	private String organiser;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fromdate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "todate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate toDate;
	
	@Basic
	@Column(name = "fees")
	private double fees;
	
	@Basic
	@Column(name = "gstincluded", columnDefinition = "BIT", length = 1)
	private boolean gstIncluded;
	
	@Basic
	@Column(name = "justification")
	private String justification;
	
	@Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'WITHDRAWN', 'UPDATED', 'REJECTED')")
	@Enumerated(EnumType.STRING)
	private CourseEventEnum status;

	/**
	 * Important: Initialize collections and exclude from toString
	 * Set getter access to NONE and provide custom getter to return defensive copy
	 */
	@OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@NotAudited
	@Builder.Default  // Ensures builder initializes this field
	@Setter(AccessLevel.NONE)  // Don't generate setter - use helper methods instead
	private List<CourseEvent> courseEvent = new ArrayList<>();

	/**
	 * Custom getter returns defensive copy to prevent external modification
	 */
	public List<CourseEvent> getCourseEvent() {
		return new ArrayList<>(courseEvent);
	}
	
	/**
	 * Helper method to add a course event while maintaining bidirectional relationship
	 */
	public void addCourseEvent(CourseEvent event) {
		courseEvent.add(event);
		event.setCourse(this);
	}
	
	/**
	 * Helper method to remove a course event while maintaining bidirectional relationship
	 */
	public void removeCourseEvent(CourseEvent event) {
		courseEvent.remove(event);
		event.setCourse(null);
	}
	
	/**
	 * Custom setter for events that properly manages bidirectional relationship
	 */
	public void setEvents(List<CourseEvent> events) {
		this.courseEvent.clear();
		if (events != null) {
			events.forEach(this::addCourseEvent);
		}
	}
}
