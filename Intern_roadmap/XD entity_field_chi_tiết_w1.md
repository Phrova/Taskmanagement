Fields Table

Task
| Field Name | Data Type | Required | Description |

| id | Long | Yes | id |
| title | String | Yes | task title |
| description | String | No | task description |
| status | TaskStatus | Yes | task status |
| createDate | LocalDate | Yes | created date |
| dueDate | LocalDate | Yes | deadline |
| user | User | Yes | Task owner |
| project | Project | No | belonging project |

User
| Field Name | Data Type | Required | Description |

| id | Long | Yes | id |
| username | String | Yes | username |
| password | String | Yes | password |
| email | String | Yes | email (unique) |
| role | UserRole | Yes | role |
| tasks | List<Task> | No | User's tasks |
| project | List<Project> | No | Projects user in |

Project
| Field Name | Data Type | Required | Description |

| id | Long | Yes | id |
| name | String | Yes | name |
| description | String | No | description |
| createDate | LocalDate | Yes | created date |
| tasks | List<Task> | No | Project's task |
| users | List<users> | No | Project's members |

