use task_management

--Viet SQL tao bang 1
CREATE TABLE users (
    user_id BIGINT IDENTITY,
    username NVARCHAR(100) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    user_role NVARCHAR(50) NOT NULL
);

CREATE TABLE project (
    project_id BIGINT IDENTITY,
    name NVARCHAR(200) NOT NULL,
    description NVARCHAR(500),
    create_date DATE NOT NULL
);

CREATE TABLE task (
    task_id BIGINT IDENTITY,
    title NVARCHAR(200) NOT NULL,
    description NVARCHAR(500),
    status NVARCHAR(50) NOT NULL,
    create_date DATE NOT NULL,
    due_date DATE,
    user_id BIGINT,
    project_id BIGINT
);

CREATE TABLE user_project (
    user_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL
);

