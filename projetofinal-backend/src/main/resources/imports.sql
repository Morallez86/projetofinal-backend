INSERT INTO user (first_name, last_name, username, password, email, role, active, pending, visibility)
VALUES ('John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', 'U', true, false, true),
       ('Alice', 'Smith', 'alicesmith', 'password123', 'alicesmith@example.com', 'U', true, false, true),
       ('Bob', 'Brown', 'bobbrown', 'password123', 'bobbrown@example.com', 'U', true, false, true),
       ('Charlie', 'Davis', 'charliedavis', 'password123', 'charliedavis@example.com', 'U', true, false, true),
       ('Dana', 'Evans', 'danaevans', 'password123', 'danaevans@example.com', 'U', true, false, true),
       ('Evan', 'Garcia', 'evangarcia', 'password123', 'evangarcia@example.com', 'U', true, false, true);


INSERT INTO skill (name, type, creator_id)
VALUES ('Java', 'SOFTWARE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('Coding', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('Placas Gráficas', 'HARDWARE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('Parafusos', 'TOOLS', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('Git', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('React', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('Kraken', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id)
VALUES ('VSCode', 'KNOWLEDGE', 1);

INSERT INTO interest (creator_id, name)
VALUES (1, 'CyberSecurity');
INSERT INTO interest (creator_id, name)
VALUES (1, 'Animals');
INSERT INTO interest (creator_id, name)
VALUES (1, 'Math');
INSERT INTO interest (creator_id, name)
VALUES (1, 'Nature');

INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 1);
INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 3);
INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 2);
INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 4);
INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 5);
INSERT INTO user_skill (user_id, skill_id)
VALUES (1, 6);

INSERT INTO resource (expiration_date, brand, contact, description, identifier, name, supplier)
VALUES ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier35', 'name2', 'supplier2');


INSERT INTO component(project_id, workplace_id, brand, contact, description, identifier, name, observation, supplier)
VALUES (NULL, 1, 'brand3', '999-999', 'description2', 'identifier2', 'component2', 'observation2', 'supplier2');

INSERT INTO project(approved, max_users, status, approved_date, creation_date, end_date, owner_id, planned_end_date,
                    starting_date, description, motivation, title)
VALUES (true, 5, 'IN_PROGRESS', '2024-06-02T10:00:00', '2024-06-02T10:30:00', '2024-06-10T17:00:00', 1,
        '2024-06-10T17:00:00', '2024-06-03T09:00:00', 'This is a test project created in Postman.',
        'To test the functionality.', 'Test Project 40'),
       (true, 5, 'PLANNING', '2024-06-02T11:00:00', '2024-06-02T11:30:00', '2024-07-01T17:00:00', 2,
        '2024-07-01T17:00:00', '2024-06-04T09:00:00', 'Another test project.', 'To check integration.',
        'Project Alpha'),
       (true, 4, 'READY', '2024-06-02T12:00:00', '2024-06-02T12:30:00', '2024-06-30T17:00:00', 3, '2024-06-30T17:00:00',
        '2024-06-05T09:00:00', 'Yet another test project.', 'To ensure robustness.', 'Project Beta'),
       (true, 3, 'IN_PROGRESS', '2024-06-02T13:00:00', '2024-06-02T13:30:00', '2024-07-15T17:00:00', 4,
        '2024-07-15T17:00:00', '2024-06-06T09:00:00', 'Test project for new features.', 'To implement new features.',
        'Project Gamma'),
       (true, 6, 'FINISHED', '2024-06-02T14:00:00', '2024-06-02T14:30:00', '2024-06-25T17:00:00', 5,
        '2024-06-25T17:00:00', '2024-06-07T09:00:00', 'Completed test project.', 'To finalize testing.',
        'Project Delta'),
       (true, 5, 'CANCELLED', '2024-06-02T15:00:00', '2024-06-02T15:30:00', '2024-06-20T17:00:00', 1,
        '2024-06-20T17:00:00', '2024-06-08T09:00:00', 'Cancelled test project.', 'To halt progress.',
        'Project Epsilon');



INSERT INTO user_project (is_admin, project_id, user_id)
VALUES (false, 1, 2),
       (false, 1, 3),
       (false, 2, 1),
       (true, 2, 2),
       (false, 2, 3),
       (false, 3, 4),
       (true, 3, 3),
       (false, 4, 5),
       (true, 4, 4),
       (false, 5, 2),
       (true, 5, 5),
       (false, 5, 3),
       (false, 5, 1),
       (false, 6, 4),
       (true, 6, 1),
       (false, 6, 5);

INSERT INTO task (title, description, planned_starting_date, starting_date, planned_ending_date, ending_date, status,
                  priority, contributors, project_id, user_id)
VALUES ('Task 1', 'Description for Task 1', '2024-06-10 09:00:00', NULL, '2024-06-15 17:00:00', NULL, 'TODO', 'LOW',
        'John Doe', 1, 1),
       ('Task 2', 'Description for Task 2', '2024-06-12 09:00:00', NULL, '2024-06-20 17:00:00', NULL, 'DOING',
        'HIGH', 'Alice Smith', 1, 2),
       ('Task 3', 'Description for Task 3', '2024-06-14 09:00:00', NULL, '2024-06-25 17:00:00', NULL, 'DONE', 'HIGH',
        'Bob Brown', 2, 3),
       ('Task 4', 'Description for Task 4', '2024-06-16 09:00:00', NULL, '2024-06-30 17:00:00', NULL, 'DONE',
        'MEDIUM', 'Charlie Davis', 2, 4),
       ('Task 5', 'Description for Task 5', '2024-06-18 09:00:00', NULL, '2024-07-05 17:00:00', NULL, 'TODO', 'LOW',
        'Dana Evans', 3, 5);

INSERT INTO task_dependencies (task_id, dependency_id)
VALUES (2, 1),
       (4, 3);


