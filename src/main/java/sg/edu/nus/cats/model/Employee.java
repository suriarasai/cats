package sg.edu.nus.cats.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee implements Serializable {

	private static final long serialVersionUID = 6529685098267757670L;

	@Id
	@Column(name = "employeeid")
	private String employeeId;

	private String name;

	@Column(name = "managerid")
	private String managerId;

	public Employee(String employeeId, String name, String managerId) {
		this.employeeId = employeeId;
		this.name = name;
		this.managerId = managerId;
	}

	public Employee(String employeeId) {
		this.employeeId = employeeId;
	}

}
