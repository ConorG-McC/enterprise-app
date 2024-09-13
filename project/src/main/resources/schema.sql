CREATE TABLE project (
                         project_id VARCHAR PRIMARY KEY,
                         project_name VARCHAR NOT NULL
);

CREATE TABLE task (
                      task_id LONG PRIMARY KEY,
                      task_name VARCHAR NOT NULL,
                      task_estimated_hours DECIMAL(6,2) NOT NULL,
                      project_id VARCHAR NOT NULL,
                      FOREIGN KEY (project_id) REFERENCES project (project_id)
);