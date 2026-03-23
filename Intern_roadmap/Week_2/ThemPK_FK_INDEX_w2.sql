use task_management

--Them PK, FK, Index 2
ALTER TABLE users
ADD CONSTRAINT PK_users PRIMARY KEY (user_id);

ALTER TABLE project
ADD CONSTRAINT PK_project PRIMARY KEY (project_id);

ALTER TABLE task
ADD CONSTRAINT PK_task PRIMARY KEY (task_id);

ALTER TABLE user_project
ADD CONSTRAINT PK_user_project PRIMARY KEY (user_id, project_id);

ALTER TABLE task ADD CONSTRAINT FK_task_user FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE task ADD CONSTRAINT FK_task_project FOREIGN KEY (project_id) REFERENCES project(project_id);

ALTER TABLE user_project ADD CONSTRAINT FK_user_project_user FOREIGN KEY (user_id) REFERENCES users(user_id);

ALTER TABLE user_project ADD CONSTRAINT FK_user_project_project FOREIGN KEY (project_id) REFERENCES project(project_id);

--Index	

CREATE INDEX IDX_task_user_id
ON task(user_id);

CREATE INDEX IDX_task_project_id
ON task(project_id);

CREATE INDEX IDX_user_project_user
ON user_project(user_id);

CREATE INDEX IDX_user_project_project
ON user_project(project_id);
