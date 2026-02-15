package sg.edu.iss.cats.model;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Employee entity class with Envers auditing and Lombok
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "employee")
@Audited
@Data
@Builder  // Enables builder pattern: Employee.builder().employeeId("E001").name("John").build()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString  // Customize toString to avoid circular references
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757670L;
	
	@Id
	@Column(name = "employeeid")
	@EqualsAndHashCode.Include
	private String employeeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "managerid")
	private String managerId;
}
