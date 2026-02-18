package sg.edu.nus.cats.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role implements Serializable {

	private static final long serialVersionUID = 6529685098267757690L;

	@Id
	@Column(name = "roleid")
	private String roleId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	public Role(String roleId, String name, String description) {
		this.roleId = roleId;
		this.name = name;
		this.description = description;
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

}
