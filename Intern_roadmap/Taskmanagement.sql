select * from user_project

select * from users
select * from task
select * from project
select * from user_project
SELECT user_id FROM users WHERE email = 'manager1@gmail.com';
SELECT role_id FROM roles WHERE name = 'MANAGER';
SELECT project_id, name FROM project;
SELECT task_id, title, project_id, user_id FROM task WHERE task_id = 1;
INSERT INTO user_roles (user_id, role_id) VALUES (10, 2);
CREATE TABLE roles (
    role_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);
ALTER TABLE users DROP COLUMN user_role;
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

INSERT INTO roles (name, description) VALUES 
('USER', 'Regular user can view and manage tasks'),
('MANAGER', 'Manager can create projects and manage users');

SELECT u.email, r.name 
FROM users u
JOIN user_roles ur ON u.user_id = ur.user_id
JOIN roles r ON ur.role_id = r.role_id
WHERE u.email = 'manager1@gmail.com';