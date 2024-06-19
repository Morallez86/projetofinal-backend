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

INSERT INTO resource (expiration_date, brand, contact, description, identifier, name, supplier)
VALUES ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier35', 'name2', 'supplier2'), ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier376', 'name10', 'supplier2'), ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier3767', 'name3', 'supplier2'), ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier3764', 'name4', 'supplier2');


INSERT INTO component(project_id, workplace_id, brand, contact, description, identifier, name, observation, supplier)
VALUES (NULL, 1, 'brand3', '999-999', 'description2', 'identifier2', 'component2', 'observation2', 'supplier2');

INSERT INTO project(approved, max_users, status, approved_date, creation_date, end_date, owner_id, planned_end_date,
                    starting_date, description, motivation, title, workplace_id)
VALUES (true, 5, 'IN_PROGRESS', '2024-06-02T10:00:00', '2024-06-02T10:30:00', '2024-06-10T17:00:00', 1,
        '2024-06-10T17:00:00', '2024-06-03T09:00:00', 'This is a test project created in Postman.',
        'To test the functionality.', 'Test Project 40', 1),
       (true, 5, 'PLANNING', '2024-06-02T11:00:00', '2024-06-02T11:30:00', '2024-07-01T17:00:00', 2,
        '2024-07-01T17:00:00', '2024-06-04T09:00:00', 'Another test project.', 'To check integration.',
        'Project Alpha', 2),
       (true, 4, 'READY', '2024-06-02T12:00:00', '2024-06-02T12:30:00', '2024-06-30T17:00:00', 3, '2024-06-30T17:00:00',
        '2024-06-05T09:00:00', 'Yet another test project.', 'To ensure robustness.', 'Project Beta', 3),
       (true, 3, 'IN_PROGRESS', '2024-06-02T13:00:00', '2024-06-02T13:30:00', '2024-07-15T17:00:00', 4,
        '2024-07-15T17:00:00', '2024-06-06T09:00:00', 'Test project for new features.', 'To implement new features.',
        'Project Gamma', 4),
       (true, 6, 'FINISHED', '2024-06-02T14:00:00', '2024-06-02T14:30:00', '2024-06-25T17:00:00', 5,
        '2024-06-25T17:00:00', '2024-06-07T09:00:00', 'Completed test project.', 'To finalize testing.',
        'Project Delta',5 ),
       (true, 5, 'CANCELLED', '2024-06-02T15:00:00', '2024-06-02T15:30:00', '2024-06-20T17:00:00', 1,
        '2024-06-20T17:00:00', '2024-06-08T09:00:00', 'Cancelled test project.', 'To halt progress.',
        'Project Epsilon', 6);

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
       ('Task 3', 'Description for Task 3', '2024-06-13 09:00:00', NULL, '2024-06-18 17:00:00', NULL, 'TODO', 'MEDIUM', 'Bob Brown', 1, 1),
       ('Task 4', 'Description for Task 4', '2024-06-14 09:00:00', NULL, '2024-06-19 17:00:00', NULL, 'TODO', 'HIGH', 'Carol White', 1, 2),
       ('Task 5', 'Description for Task 5', '2024-06-15 09:00:00', NULL, '2024-06-22 17:00:00', NULL, 'DONE', 'LOW', 'Dave Green', 1, 1),
       ('Task 6', 'Description for Task 6', '2024-06-16 09:00:00', NULL, '2024-06-23 17:00:00', NULL, 'TODO', 'MEDIUM', 'Eva Black', 1, 2),
       ('Task 7', 'Description for Task 7', '2024-06-17 09:00:00', NULL, '2024-06-24 17:00:00', NULL, 'DOING', 'LOW', 'Frank Red', 1, 1),
       ('Task 8', 'Description for Task 8', '2024-06-18 09:00:00', NULL, '2024-06-25 17:00:00', NULL, 'DONE', 'HIGH', 'Grace Yellow', 1, 2),
       ('Task 9', 'Description for Task 9', '2024-06-19 09:00:00', NULL, '2024-06-26 17:00:00', NULL, 'TODO', 'MEDIUM', 'Hank Blue', 1, 1),
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

INSERT INTO project_history(project_id, task_id, timestamp, user_id, new_description, old_description, title, type)
VALUES (1,null, '2024-01-15 11:30:00',1,null,null,'Skill added: Backend','ADD'),
(1,null, '2024-01-16 11:32:00',1,null,null, 'Alistar123 was removed','REMOVE'),
       (1,null, '2024-01-16 11:33:00',1,null,null,'New State: IN PROGRESS','PROJECTSTATE'),
       (1,null, '2024-01-16 11:34:00',1,null,null,'Task "1st meeting": FINISHED','TASKS'),
       (1,null, '2024-01-16 11:35:00',1,'I need help to finish this task',null, null,'NORMAL'),
(1,2, '2024-06-12 11:32:00',2,null,null, 'Task 2 was created','ADD'),
       (1,1, '2024-01-17 11:33:00',2,null,null, 'Task 1 was removed','REMOVE'),
       (1,2, '2024-01-16 11:32:00',1,'I need more time to this task, like more 2 or 3 days',null, null,'NORMAL');

-- Insert messages data
INSERT INTO message (content, sender_id, receiver_id, timestamp, seen)
VALUES ('Hello, Alice! How are you?', 1, 2, '2024-06-10 10:00:00', false),
       ('Hi John! I am good, thank you!', 2, 1, '2024-06-10 10:05:00', true),
       ('Hey Bob, do you have the project report ready?', 1, 3, '2024-06-10 11:00:00', false),
       ('Yes, John. I will send it to you by EOD.', 3, 1, '2024-06-10 11:10:00', false),
       ('Alice, could you review my code?', 3, 2, '2024-06-10 12:00:00', true),
       ('Sure, Bob! I will take a look.', 2, 3, '2024-06-10 12:15:00', false),
       ('Hi Dana, any updates on the new features?', 1, 5, '2024-06-10 14:00:00', true),
       ('Yes, John. I have completed the initial implementation.', 5, 1, '2024-06-10 14:30:00', true),
       ('Charlie, can you join the meeting at 3 PM?', 1, 4, '2024-06-10 13:00:00', false),
       ('Got it, John. I will be there.', 4, 1, '2024-06-10 13:15:00', false),
       ('Evan, we need to discuss the design specs.', 1, 6, '2024-06-10 15:00:00', true),
       ('Sure, John. When are you available?', 6, 1, '2024-06-10 15:30:00', false),
       ('Alice, have you checked the new commits?', 5, 2, '2024-06-10 16:00:00', false),
       ('Yes, Dana. I have reviewed them.', 2, 5, '2024-06-10 16:30:00', true),
       ('Bob, please update the documentation.', 4, 3, '2024-06-10 17:00:00', false),
       ('Will do, Charlie.', 3, 4, '2024-06-10 17:15:00', false),
       ('Hey Evan, can you fix the bug in module X?', 5, 6, '2024-06-10 18:00:00', false),
       ('On it, Dana.', 6, 5, '2024-06-10 18:30:00', false),
       ('John, any updates on the project approval?', 2, 1, '2024-06-10 19:00:00', true),
       ('Yes, Alice. It has been approved.', 1, 2, '2024-06-10 19:30:00', true);

INSERT INTO notification (description, type, timestamp, sender_id, project_id)
VALUES
    ('You have a new message from Alice.', 'MESSAGE', '2024-06-10 10:00:00', 1, 1),
    ('Your task "Task 1" is due soon.', 'PROJECT', '2024-06-10 10:05:00', 1, 2),
    ('Project "Test Project 40" has been updated.', 'PROJECT', '2024-06-10 11:00:00', 1, 3),
    ('New skill added: Java.', 'MESSAGE', '2024-06-10 11:10:00', 1, 4),
    ('You have a new message from Bob.', 'MESSAGE', '2024-06-10 12:00:00', 2, 1),
    ('Reminder: Meeting at 3 PM.', 'MESSAGE', '2024-06-10 12:15:00', 2, 2),
    ('System maintenance scheduled for tonight.', 'MESSAGE', '2024-06-10 14:00:00', 3, 1),
    ('You have a new message from John.', 'MESSAGE', '2024-06-10 14:30:00', 1, 4),
    ('Your task "Task 2" is in progress.', 'PROJECT', '2024-06-10 13:00:00', 1, 5),
    ('Project "Project Alpha" has been updated.', 'PROJECT', '2024-06-10 13:15:00', 1, 6),
    ('You have a new message from John.', 'MESSAGE', '2024-06-10 15:00:00', 1, 2),
    ('Your task "Task 3" is due soon.', 'PROJECT', '2024-06-10 15:30:00', 1, 3),
    ('Project "Project Beta" has been updated.', 'PROJECT', '2024-06-10 16:00:00', 1, 4),
    ('You have a new message from John.', 'MESSAGE', '2024-06-10 16:30:00', 1, 5),
    ('Your task "Task 4" is in progress.', 'PROJECT', '2024-06-10 17:00:00', 1, 6),
    ('Project "Project Gamma" has been updated.', 'PROJECT', '2024-06-10 17:15:00', 1, 2),
    ('You have a new message from John.', 'MESSAGE', '2024-06-10 18:00:00', 1, 3),
    ('Your task "Task 5" is completed.', 'PROJECT', '2024-06-10 18:30:00', 1, 4),
    ('Project "Project Delta" has been updated.', 'PROJECT', '2024-06-10 19:00:00', 1, 5),
    ('You have a new message from John.', 'MESSAGE', '2024-06-10 19:30:00', 1, 6),
    ('Your task "Task 6" is in progress.', 'PROJECT', '2024-06-10 20:00:00', 1, 2),
    ('Project 1 needs approval.', 'MANAGING', '2024-06-10 20:00:00', 1, 1),
    ('User Alice has invited you to project 1', 'INVITATION', '2024-06-10 20:00:00', 1, 1),
    ('Project "Project Epsilon" has been updated.', 'PROJECT', '2024-06-10 20:30:00', 1, 3);

INSERT INTO user_notification (user_id, notification_id, seen)
VALUES
    (1, 1, false),
    (1, 2, false),
    (1, 3, false),
    (1, 4, true),
    (1, 5, false),
    (1, 6, false),
    (1, 7, true),
    (1, 8, true),
    (1, 9, true),
    (1, 10, false),
    (1, 17, true),
    (1, 18, false),
    (1, 19, true);




