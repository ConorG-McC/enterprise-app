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

--sequence for id's required for saves of new records in the application class (not when using sql here)
--check Task (infrastructure) for use of sequence
CREATE SEQUENCE task_sequence_id START WITH (SELECT MAX(task_id) + 1 FROM task);
