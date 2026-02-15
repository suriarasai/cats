package sg.edu.iss.cats.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.envers.Audited;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User entity class with Envers auditing and Lombok
 * 
 * IMPORTANT: This entity has a ManyToMany relationship with Role.
 * - roleSet is excluded from toString to avoid lazy loading issues
 * - Only userId is used for equals/hashCode
 * - Custom getter for roleIds to extract IDs from roleSet
 *
 * @version $Revision: 2.0 (Lombok)
 * @author Suria
 */
@Entity
@Table(name = "users")
@Audited
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User implements Serializable {
	
	private static final long serialVersionUID = 6529685098267757680L;
	
	@Id
	@Column(name = "userid")
	@EqualsAndHashCode.Include  // Only userId for equality
	private String userId;
	
	@NotEmpty
	@Column(name = "name")
	private String name;
	
	@NotEmpty
	@Column(name = "password")
	@ToString.Exclude  // Never include password in toString!
	private String password;
	
	@Column(name = "employeeid")
	private String employeeId;

	/**
	 * ManyToMany relationship with Role
	 * The join table 'userrole' connects users and roles
	 * CascadeType.ALL removed - not recommended for ManyToMany
	 */
	@ManyToMany(targetEntity = Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "userrole", 
		joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "userid")}, 
		inverseJoinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "roleid")}
	)
	@Builder.Default  // Ensures builder initializes this field
	@Setter(AccessLevel.NONE)  // Don't generate setter - use helper methods
	private List<Role> roleSet = new ArrayList<>();
	
	/**
	 * Transient field for roleIds
	 * Not persisted to database, used for data transfer
	 */
	@Transient
	@Getter(AccessLevel.NONE)  // Use custom getter below
	@Setter(AccessLevel.NONE)  // Use custom setter below
	private transient List<String> roleIds;

	/**
	 * Custom getter for roleSet that returns defensive copy
	 */
	public List<Role> getRoleSet() {
		return new ArrayList<>(roleSet);
	}

	/**
	 * Custom setter for roleSet
	 */
	public void setRoleSet(List<Role> roleSet) {
		this.roleSet = roleSet != null ? new ArrayList<>(roleSet) : new ArrayList<>();
	}

	/**
	 * Custom getter for roleIds - extracts IDs from roleSet
	 * Improved with Stream API for better performance
	 */
	public List<String> getRoleIds() {
		if (roleSet == null || roleSet.isEmpty()) {
			return new ArrayList<>();
		}
		return roleSet.stream()
				.map(Role::getRoleId)
				.collect(Collectors.toList());
	}

	/**
	 * Custom setter for roleIds
	 */
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
	/**
	 * Helper method to add a role
	 */
	public void addRole(Role role) {
		if (roleSet == null) {
			roleSet = new ArrayList<>();
		}
		roleSet.add(role);
	}
	
	/**
	 * Helper method to remove a role
	 */
	public void removeRole(Role role) {
		if (roleSet != null) {
			roleSet.remove(role);
		}
	}
	
	/**
	 * Helper method to clear all roles
	 */
	public void clearRoles() {
		if (roleSet != null) {
			roleSet.clear();
		}
	}
}
