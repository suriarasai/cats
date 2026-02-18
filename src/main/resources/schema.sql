DROP TABLE IF EXISTS courseevent;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS userrole;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  employeeid VARCHAR(15) NOT NULL,
  managerid VARCHAR(15),
  name VARCHAR(45),
  PRIMARY KEY (employeeid)
);

CREATE TABLE role (
  roleid VARCHAR(15) NOT NULL,
  name VARCHAR(45),
  description VARCHAR(45),
  PRIMARY KEY (roleid)
);

CREATE TABLE `users` (
  userid VARCHAR(15) NOT NULL,
  name VARCHAR(45),
  password VARCHAR(45),
  employeeid VARCHAR(15),
  PRIMARY KEY (userid),
  CONSTRAINT efk FOREIGN KEY (employeeid) REFERENCES employee (employeeid)
);

CREATE TABLE userrole (
  roleid VARCHAR(15) NOT NULL,
  userid VARCHAR(15) NOT NULL,
  PRIMARY KEY (roleid, userid),
  CONSTRAINT ufk FOREIGN KEY (userid) REFERENCES `users` (userid),
  CONSTRAINT rfk FOREIGN KEY (roleid) REFERENCES role (roleid)
);

CREATE TABLE department (
  departmentid VARCHAR(15) NOT NULL,
  managerid VARCHAR(15) NOT NULL,
  PRIMARY KEY (departmentid),
  CONSTRAINT mfk FOREIGN KEY (managerid) REFERENCES employee (employeeid)
);

CREATE TABLE course (
  courseid INT AUTO_INCREMENT NOT NULL,
  employeeid VARCHAR(15),
  coursename VARCHAR(45),
  organiser VARCHAR(45),
  fromdate DATE,
  todate DATE,
  fees DOUBLE,
  gstincluded BOOLEAN,
  justification VARCHAR(100),
  status VARCHAR(20) NOT NULL,
  comments VARCHAR(100),
  PRIMARY KEY (courseid),
  CONSTRAINT efk1 FOREIGN KEY (employeeid) REFERENCES employee (employeeid)
);

CREATE TABLE courseevent (
  courseeventid INT AUTO_INCREMENT NOT NULL,
  courseid INT,
  timestamp DATE,
  eventtype VARCHAR(20),
  eventby VARCHAR(30),
  comment VARCHAR(100),
  PRIMARY KEY (courseeventid),
  CONSTRAINT cfk FOREIGN KEY (courseid) REFERENCES course (courseid)
);
