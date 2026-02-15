package sg.edu.iss.cats.model;

import java.io.Serializable;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * UserRole Join Entity (OPTIONAL)
 * 
 * NOTE: In your original code, 'userrole' is just a join table name in the 
 * @JoinTable annotation of User.java - NOT a separate entity.
 * 
 * This entity is ONLY needed if you want to:
 * 1. Add additional attributes to the user-role relationship
 * 2. Have explicit control over the join table
 * 3. Query the join table directly
 * 
 * If you don't need these features, you can delete this file and continue
 * using the @JoinTable approach in User.java (which is simpler).
 * 
 * WHEN TO USE THIS APPROACH:
 * - When you need to add attributes like: assignedDate, expiryDate, assignedBy
 * - When you need to track history of role assignments separately
 * 
 * WHEN NOT TO USE:
 * - Simple many-to-many relationships without extra attributes
 * - The @JoinTable approach in User.java is sufficient
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */


@Entity
@Table(name = "userrole")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class)  // Composite key
public class UserRole implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "userid")
	private String userId;
	
	@Id
	@Column(name = "roleid")
	private String roleId;
	
	// Optional: Add additional attributes here if needed
	// For example:
	// @Column(name = "assigned_date")
	// private Date assignedDate;
	// 
	// @Column(name = "assigned_by")
	// private String assignedBy;
	
	/**
	 * If you want to navigate from UserRole to User entity, add this:
	 */
	// @ManyToOne
	// @JoinColumn(name = "userid", insertable = false, updatable = false)
	// private User user;
	
	/**
	 * If you want to navigate from UserRole to Role entity, add this:
	 */
	// @ManyToOne
	// @JoinColumn(name = "roleid", insertable = false, updatable = false)
	// private Role role;
}

/**
 * Composite Primary Key class for UserRole
 * Required when using @IdClass
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class UserRoleId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String roleId;
}
