--Restaurant and menu items are created in Restaurant bounded context - this info here is just
--necessary for use with the orders - hence why ids for project and menu_item are not generated
--in this context
CREATE TABLE project
(
    project_id   VARCHAR PRIMARY KEY,
    project_name VARCHAR NOT NULL
);

CREATE TABLE task
(
    task_id              LONG PRIMARY KEY,
    task_name            VARCHAR       NOT NULL,
    task_estimated_hours DECIMAL(6, 2) NOT NULL,
    project_id           VARCHAR       NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (project_id)
);

CREATE TABLE task_assignment
(
    task_assignment_id VARCHAR PRIMARY KEY,
    consumer_id        VARCHAR NOT NULL,
    project_id         VARCHAR NOT NULL,
    task_state         int     NOT NULL
);
CREATE TABLE assigned_task
(
    assigned_task_id     int AUTO_INCREMENT PRIMARY KEY,
    task_id              VARCHAR       NOT NULL,
    task_name            VARCHAR       NOT NULL,
    task_estimated_hours DECIMAL(6, 2) NOT NULL,
    task_assignment_id   VARCHAR       NOT NULL,
    FOREIGN KEY (task_assignment_id) REFERENCES task_assignment (task_assignment_id)
);