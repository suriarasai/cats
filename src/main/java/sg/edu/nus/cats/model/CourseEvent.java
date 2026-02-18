package sg.edu.nus.cats.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.cats.helper.CourseEventEnum;

@Entity
@Table(name = "courseevent")
@Data
@NoArgsConstructor
public class CourseEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseeventid")
	private int courseEventId;

	@Column(name = "timestamp")
	private LocalDate timeStamp;

	@Column(name = "eventtype")
	@Enumerated(EnumType.STRING)
	private CourseEventEnum eventType;

	@Column(name = "eventby")
	private String eventBy;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "courseid")
	private Course course;

	public CourseEvent(int courseEventId, LocalDate timeStamp, CourseEventEnum eventType,
			String eventBy, String comment, Course course) {
		this.courseEventId = courseEventId;
		this.timeStamp = timeStamp;
		this.eventType = eventType;
		this.eventBy = eventBy;
		this.comment = comment;
		this.course = course;
	}

}
