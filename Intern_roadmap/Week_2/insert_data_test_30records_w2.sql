use task_management

INSERT INTO users (username, password, email, user_role) VALUES
('user1', '123', 'user1@mail.com', 'USER'),
('user2', '123', 'user2@mail.com', 'USER'),
('user3', '123', 'user3@mail.com', 'USER'),
('user4', '123', 'user4@mail.com', 'WORKER'),
('user5', '123', 'user5@mail.com', 'ADMIN');

INSERT INTO project (name, description, create_date) VALUES
('Project A', 'simple project', '2024-01-01'),
('Project B', 'test project', '2024-01-02'),
('Project C', 'demo project', '2024-01-03'),
('Project D', 'small project', '2024-01-04'),
('Project E', 'basic project', '2024-01-05');

INSERT INTO user_project (user_id, project_id)
VALUES
(1,1),(1,2),
(2,1),(2,3),
(3,2),(3,4),
(4,3),(4,5),
(5,1),(5,5);

INSERT INTO task (title, description, status, create_date, due_date, user_id, project_id) VALUES
('task 1','do task','TODO','2024-01-01','2024-01-05',1,1),
('task 2','do task','TODO','2024-01-01','2024-01-05',1,1),
('task 3','do task','DONE','2024-01-02','2024-01-06',2,2),
('task 4','do task','DONE','2024-01-02','2024-01-06',2,2),
('task 5','do task','TODO','2024-01-03','2024-01-07',3,3),
('task 6','do task','TODO','2024-01-03','2024-01-07',3,3),
('task 7','do task','DONE','2024-01-04','2024-01-08',4,4),
('task 8','do task','DONE','2024-01-04','2024-01-08',4,4),
('task 9','do task','TODO','2024-01-05','2024-01-09',5,5),
('task 10','do task','TODO','2024-01-05','2024-01-09',5,5),
('task 11','do task','DONE','2024-01-06','2024-01-10',1,2),
('task 12','do task','DONE','2024-01-06','2024-01-10',2,3),
('task 13','do task','TODO','2024-01-07','2024-01-11',3,4),
('task 14','do task','TODO','2024-01-07','2024-01-11',4,5),
('task 15','do task','DONE','2024-01-08','2024-01-12',5,1);
