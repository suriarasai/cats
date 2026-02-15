package sg.edu.iss.cats.model;

import org.hibernate.envers.Audited;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Department entity class with Envers auditing and Lombok
 * 
 * This is a simple entity with no relationships, so @Data is safe to use.
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "department")
@Audited
@Data  // Safe to use @Data for simple entities without relationships
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Department {
	
	@Id
	@Column(name = "departmentid")
	@EqualsAndHashCode.Include  // Only use ID for equality
	private String departmentId;
	
	@Basic
	@Column(name = "managerid")
	private String managerInCharge;
}
