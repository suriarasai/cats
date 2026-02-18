package sg.edu.nus.cats.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
public class Department {

	@Id
	@Column(name = "departmentid")
	private String departmentId;

	@Column(name = "managerid")
	private String managerInCharge;

	public Department(String departmentId) {
		this.departmentId = departmentId;
	}

}
