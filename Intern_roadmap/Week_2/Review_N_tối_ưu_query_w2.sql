use task_management

SELECT task_id, title, description, status, create_date, due_date, user_id
FROM task
WHERE user_id = 1;

SELECT task_id, title, description, status, create_date, due_date, project_id
FROM task
WHERE project_id = 1;

SELECT task_id, title, description, status, create_date, due_date
FROM task
WHERE status = 'TODO';

-- REVIEW
-- Avoid using SELECT * FROM
-- Same result as the original SQL
-- Explicitly select required columns for better control
-- Add INDEX on columns frequently used

-- RESULT
-- Query is more stable and maintainable
-- Reduce unnecessary data scanning
-- Improve database efficiency
