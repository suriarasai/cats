INSERT INTO employee (employeeid, managerid, name) VALUES ('dilbert', 'pointy', 'Dilbert');
INSERT INTO employee (employeeid, managerid, name) VALUES ('pointy', 'dogbert', 'Pointy');
INSERT INTO employee (employeeid, managerid, name) VALUES ('alice', 'pointy', 'Alice');
INSERT INTO employee (employeeid, managerid, name) VALUES ('wally', 'pointy', 'Wally');
INSERT INTO employee (employeeid, managerid, name) VALUES ('ashok', 'dilbert', 'Ashok');
INSERT INTO employee (employeeid, managerid, name) VALUES ('dogbert', '', 'Dogbert');
INSERT INTO employee (employeeid, managerid, name) VALUES ('ted', '', 'Ted');
INSERT INTO employee (employeeid, managerid, name) VALUES ('howard', '', 'Loud Howard');
INSERT INTO employee (employeeid, managerid, name) VALUES ('catbert', 'dogbert', 'Catbert HR');
INSERT INTO employee (employeeid, managerid, name) VALUES ('ratbert', 'dogbert', 'Ratbert low form');
INSERT INTO employee (employeeid, managerid, name) VALUES ('bob', 'pointy', 'Bob the dino');
INSERT INTO employee (employeeid, managerid, name) VALUES ('tina', '', 'Tina the technical writer');

INSERT INTO role (roleid, name, description) VALUES ('admin', 'Administrator', 'System administrator');
INSERT INTO role (roleid, name, description) VALUES ('staff', 'Staff', 'Staff members');
INSERT INTO role (roleid, name, description) VALUES ('manager', 'Manager', 'Manager');

INSERT INTO `users` (userid, name, password, employeeid) VALUES ('dilbert', 'dilbert', 'dilbert', 'dilbert');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('pointy', 'pointy', 'pointy', 'pointy');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('alice', 'alice', 'alice', 'alice');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('wally', 'wally', 'wally', 'wally');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('ashok', 'ashok', 'ashok', 'ashok');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('dogbert', 'dogbert', 'dogbert', 'dogbert');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('ted', 'ted', 'ted', 'ted');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('howard', 'howard', 'howard', 'howard');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('catbert', 'catbert', 'catbert', 'catbert');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('ratbert', 'ratbert', 'ratbert', 'ratbert');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('bob', 'bob', 'bob', 'bob');
INSERT INTO `users` (userid, name, password, employeeid) VALUES ('tina', 'tina', 'tina', 'tina');

INSERT INTO userrole (roleid, userid) VALUES ('manager', 'dilbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'dilbert');
INSERT INTO userrole (roleid, userid) VALUES ('manager', 'pointy');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'pointy');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'alice');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'wally');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'ashok');
INSERT INTO userrole (roleid, userid) VALUES ('manager', 'dogbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'dogbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'ted');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'howard');
INSERT INTO userrole (roleid, userid) VALUES ('admin', 'catbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'catbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'ratbert');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'bob');
INSERT INTO userrole (roleid, userid) VALUES ('staff', 'tina');

INSERT INTO department (departmentid, managerid) VALUES ('engineering', 'pointy');
INSERT INTO department (departmentid, managerid) VALUES ('hr', 'catbert');
