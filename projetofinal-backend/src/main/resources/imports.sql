INSERT INTO user (first_name, last_name, username, password, email, role, active, pending, visibility)
VALUES ('John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', 'ADMIN', true, false, true),
       ('Alice', 'Smith', 'alicesmith', 'password123', 'alicesmith@example.com', 'USER', true, false, true),
       ('Bob', 'Brown', 'bobbrown', 'password123', 'bobbrown@example.com', 'USER', true, false, true),
       ('Charlie', 'Davis', 'charliedavis', 'password123', 'charliedavis@example.com', 'USER', true, false, true),
       ('Dana', 'Evans', 'danaevans', 'password123', 'danaevans@example.com', 'USER', true, false, true),
       ('Evan', 'Garcia', 'evangarcia', 'password123', 'evangarcia@example.com', 'USER', true, false, true);


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
       (true, 1, 1),
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
        'Dana Evans', 3, 5),
       ('Task 6', 'Description for Task 6', '2024-06-18 09:00:00', NULL, '2024-07-05 17:00:00', NULL, 'TODO', 'LOW',
        'Dana Evans', 6, 1);

INSERT INTO task_dependencies (task_id, dependency_id)
VALUES (2, 1),
       (4, 3);

INSERT INTO component(project_id, workplace_id, brand, contact, description, identifier, name, observation, supplier)
VALUES (2, 2, 'brand4', '888-888', 'description3', 'identifier3', 'component3', 'observation3', 'supplier3'),
       (3, 3, 'brand5', '777-777', 'description4', 'identifier4', 'component4', 'observation4', 'supplier4'),
       (4, 4, 'brand6', '666-666', 'description5', 'identifier5', 'component5', 'observation5', 'supplier5'),
       (5, 5, 'brand7', '555-555', 'description6', 'identifier6', 'component6', 'observation6', 'supplier6'),
       (6, 6, 'brand8', '444-444', 'description7', 'identifier7', 'component7', 'observation7', 'supplier7'),
       (1, 1, 'brand9', '333-333', 'description8', 'identifier8', 'component8', 'observation8', 'supplier8'),
       (2, 2, 'brand1', '222-222', 'description9', 'identifier9', 'component9', 'observation9', 'supplier1'),
       (3, 3, 'brand2', '111-111', 'description10', 'identifier10', 'component10', 'observation10', 'supplier2'),
       (4, 4, 'brand3', '000-000', 'description11', 'identifier11', 'component11', 'observation11', 'supplier3');

INSERT INTO resource (expiration_date, brand, contact, description, identifier, name, supplier)
VALUES ('2024-07-15 12:00:00', 'brand1', 'contact1', 'description1', 'identifier36', 'name3', 'supplier3'),
       ('2024-08-20 08:30:00', 'brand2', 'contact3', 'description3', 'identifier37', 'name4', 'supplier1'),
       ('2024-09-25 14:45:00', 'brand3', 'contact4', 'description4', 'identifier38', 'name5', 'supplier2'),
       ('2024-10-30 19:00:00', 'brand4', 'contact5', 'description5', 'identifier39', 'name6', 'supplier3'),
       ('2024-11-05 07:15:00', 'brand5', 'contact6', 'description6', 'identifier40', 'name7', 'supplier1'),
       ('2024-12-10 10:00:00', 'brand6', 'contact7', 'description7', 'identifier41', 'name8', 'supplier4'),
       ('2025-01-15 11:30:00', 'brand7', 'contact8', 'description8', 'identifier42', 'name9', 'supplier5'),
       ('2025-02-20 09:45:00', 'brand8', 'contact9', 'description9', 'identifier43', 'name10', 'supplier6'),
       ('2025-03-25 16:30:00', 'brand9', 'contact10', 'description10', 'identifier44', 'name11', 'supplier7');

INSERT INTO project_resource (project_id, resource_id)
VALUES (1,1),
       (2,2),
       (3,3),
       (4,4),
       (5,5);



