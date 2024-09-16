INSERT INTO project (project_id, project_name)
VALUES ('p1', 'Software Development');
INSERT INTO project (project_id, project_name)
VALUES ('p2', 'Website Redesign');

INSERT INTO task (task_id, task_name, task_estimated_hours, project_id)
VALUES (1, 'Develop Backend API', 20.5, 'p1');
INSERT INTO task (task_id, task_name, task_estimated_hours, project_id)
VALUES (2, 'Design Frontend UI', 12.0, 'p1');
INSERT INTO task (task_id, task_name, task_estimated_hours, project_id)
VALUES (4, 'Update Homepage', 5.0, 'p2');

-- Insert task assignments for users
INSERT INTO task_assignment (task_assignment_id, consumer_id, project_id, task_state)
VALUES ('a1', 'user123', 'p1', 0);


-- Insert assigned tasks for task_assignment a1
INSERT INTO assigned_task (assigned_task_id, task_id, task_name, task_estimated_hours, task_assignment_id)
VALUES (1, 1, 'Develop Backend API', 20.5, 'a1');

INSERT INTO assigned_task (assigned_task_id, task_id, task_name, task_estimated_hours, task_assignment_id)
VALUES (2, 2, 'Design Frontend UI', 12.0, 'a1');

--sequence for id's required for saves of new records in the application class (not when using sql here)
--check OrderLineItem (infrastructure) for use of sequence
CREATE SEQUENCE task_sequence_id START WITH (SELECT MAX(task_id) + 1 FROM task);
CREATE SEQUENCE assigned_task_sequence_id START WITH (SELECT MAX(assigned_task_id) + 1 FROM assigned_task);