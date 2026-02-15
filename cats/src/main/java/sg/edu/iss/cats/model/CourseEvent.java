package sg.edu.iss.cats.model;


import java.time.LocalDateTime;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import sg.edu.iss.cats.helper.CourseEventEnum;

/**
 * CourseEvent entity class with Envers auditing and Lombok
 * 
 * IMPORTANT: This entity has a ManyToOne relationship to Course.
 * We exclude 'course' from toString to avoid circular references and lazy loading issues.
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "courseevent")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"course"})  // CRITICAL: Avoid circular reference with parent Course
public class CourseEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseeventid")
	@EqualsAndHashCode.Include  // Only use ID for equality
	private Integer courseEventId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "eventDate")
	private LocalDateTime eventDate;
	
	
	@Column(name = "eventtype", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'WITHDRAWN', 'UPDATED', 'REJECTED')")
	@Enumerated(EnumType.STRING)
	private CourseEventEnum eventType;
	
	@Column(name = "eventby")
	private String eventBy;
	
	@Column(name = "comment")
	private String comment;
	
	/**
	 * ManyToOne relationship to Course
	 * Important: No cascade operations on child-to-parent relationships
	 * Excluded from toString to avoid circular references
	 */
	@ManyToOne
	@JoinColumn(name = "courseid")
	private Course course;
}
