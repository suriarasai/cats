package sg.edu.nus.cats.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 6529685098267757680L;

	@Id
	@Column(name = "userid")
	private String userId;

	@NotEmpty
	@Column(name = "name")
	private String name;

	@NotEmpty
	@Column(name = "password")
	private String password;

	@Column(name = "employeeid")
	private String employeeId;

	@ManyToMany(targetEntity = Role.class, cascade = { CascadeType.ALL, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinTable(name = "userrole",
			joinColumns = @JoinColumn(name = "userid", referencedColumnName = "userid"),
			inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid"))
	private List<Role> roleSet;

	@Transient
	private List<String> roleIds = new ArrayList<>();

	public User(String userId, String name, String password, String employeeId) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.employeeId = employeeId;
	}

	public User(String userId) {
		this.userId = userId;
	}

}
