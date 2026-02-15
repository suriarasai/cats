package sg.edu.iss.cats.model;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Role entity class with Envers auditing and Lombok
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "role")
@Audited
@Data  // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Generates no-arg constructor
@AllArgsConstructor  // Generates all-args constructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  // Only use ID for equality
public class Role implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757690L;
	
	@Id
	@Column(name = "roleid")
	@EqualsAndHashCode.Include  // Include only ID in equals/hashCode
	private String roleId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
}
