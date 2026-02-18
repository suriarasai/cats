package sg.edu.nus.cats.controllers;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.nus.cats.model.Employee;
import sg.edu.nus.cats.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private Employee employee;
	private List<Employee> subordinates;

}
