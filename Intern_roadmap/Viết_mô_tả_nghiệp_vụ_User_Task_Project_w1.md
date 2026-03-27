Business Rules

User - Task relationship
- A user can have many tasks assigned to them
- A user can also have zero tasks
- A task can exist without being assigned to anyone

Project - Task relationship
- A project can have many tasks
- A task can belong to one project or no project at all
- A project can exist without any tasks
- A task can exist without belonging to a project

User - Project relationship
- A user can join multiple projects
- A project can have multiple users in it

General
- All required fields cannot be null
- Email must be unique and in a valid email format
- Task status must be one of these: TODO, IN_PROGRESS, DONE
- User role must be one of these: USER, MANAGER