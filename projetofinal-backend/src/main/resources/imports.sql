INSERT INTO user (first_name, last_name, username, password, email, role, active, pending, visibility,online, biography, workplace_id)
VALUES ('John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', 'ADMIN', true, false, true,false, 'My purpose is to be tested',1),
       ('Alice', 'Smith', 'alicesmith', 'password123', 'alicesmith@example.com', 'USER', true, false, true,false, 'My purpose is to be tested',2),
       ('Bob', 'Brown', 'bobbrown', 'password123', 'bobbrown@example.com', 'USER', true, false, true,false, 'My purpose is to exist in database',3),
       ('Charlie', 'Davis', 'charliedavis', 'password123', 'charliedavis@example.com', 'USER', true, false, true,false, 'My purpose is to exist in database',4),
       ('Dana', 'Evans', 'danaevans', 'password123', 'danaevans@example.com', 'USER', true, false, true,false, 'My purpose is to exist in database',5),
       ('Evan', 'Garcia', 'evangarcia', 'password123', 'evangarcia@example.com', 'USER', true, false, true,false, 'My purpose is to exist in database',6);

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
INSERT INTO user_skill (user_id, skill_id)
VALUES (2, 1);
INSERT INTO user_skill (user_id, skill_id)
VALUES (2, 3);
INSERT INTO user_skill (user_id, skill_id)
VALUES (2, 5);
INSERT INTO user_skill (user_id, skill_id)
VALUES (2, 6);
INSERT INTO user_skill (user_id, skill_id)
VALUES (3, 2);
INSERT INTO user_skill (user_id, skill_id)
VALUES (3, 4);
INSERT INTO user_skill (user_id, skill_id)
VALUES (4, 1);
INSERT INTO user_skill (user_id, skill_id)
VALUES (4, 3);
INSERT INTO user_skill (user_id, skill_id)
VALUES (5, 2);
INSERT INTO user_skill (user_id, skill_id)
VALUES (5, 4);
INSERT INTO user_skill (user_id, skill_id)
VALUES (6, 5);
INSERT INTO user_skill (user_id, skill_id)
VALUES (6, 6);

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

INSERT INTO user_project (is_admin, project_id, user_id,active)
VALUES (false, 1, 2,true),
       (true, 1, 1,true),
       (false, 1, 3,true),
       (false, 2, 1,true),
       (true, 2, 2,true),
       (false, 2, 3,true),
       (false, 3, 4,false),
       (true, 3, 3,false),
       (false, 4, 5,false),
       (true, 4, 4,false),
       (false, 5, 2,false),
       (true, 5, 5,false),
       (false, 5, 3,false),
       (false, 5, 1,false),
       (false, 6, 4,false),
       (true, 6, 1,false),
       (false, 6, 5,false);

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

/*INSERT INTO task_dependencies (task_id, dependency_id)
VALUES (2, 1),
       (4, 3);*/

       INSERT INTO task_dependencies (dependency_id, task_id) VALUES (1,2), (3,4);

INSERT INTO component(project_id, workplace_id, brand, contact, description, identifier, name, observation, supplier, availability)
VALUES (2, 2, 'Intel', '800-555-1234', '8th Gen Core i5 Processor', 'CP-12345', 'Core i5-8600K', 'OEM packaging', 'TechSupply', false),
       (2, 2, 'Intel', '800-555-1234', '8th Gen Core i5 Processor', 'CP-12346', 'Core i5-8600K', 'OEM packaging', 'TechSupply', false),
       (null, 2, 'Intel', '800-555-1234', '8th Gen Core i5 Processor', 'CP-12347', 'Core i5-8600K', 'OEM packaging', 'TechSupply', true),
       (null, 1, 'Intel', '800-555-1234', '8th Gen Core i5 Processor', 'CP-12347', 'Core i5-8600K', 'OEM packaging', 'TechSupply', true),
       (null, 1, 'Intel', '800-555-1234', '8th Gen Core i5 Processor', 'CP-12348', 'Core i5-8600K', 'OEM packaging', 'TechSupply', true),
       (3, 3, 'Samsung', '800-555-5678', '1TB NVMe SSD', 'ST-67890', '970 EVO Plus', 'Retail packaging', 'MemoryWorld', false),
       (null, 3, 'Samsung', '800-555-5678', '1TB NVMe SSD', 'ST-67891', '970 EVO Plus', 'Retail packaging', 'MemoryWorld', true),
       (null, 3, 'Samsung', '800-555-5678', '1TB NVMe SSD', 'ST-67892', '970 EVO Plus', 'Retail packaging', 'MemoryWorld', true),
       (4, 4, 'Corsair', '800-555-2345', '16GB DDR4 RAM', 'RM-11223', 'Vengeance LPX', 'Heat spreader included', 'RAMSource', false),
       (null, 2, 'Corsair', '800-555-2345', '16GB DDR4 RAM', 'RM-11224', 'Vengeance LPX', 'Heat spreader included', 'RAMSource', true),
       (5, 5, 'Asus', '800-555-3456', 'Gaming Motherboard', 'MB-33445', 'ROG Strix Z390-E', 'Includes Wi-Fi adapter', 'BoardMakers', false),
       (null, 5, 'Asus', '800-555-3456', 'Gaming Motherboard', 'MB-33446', 'ROG Strix Z390-E', 'Includes Wi-Fi adapter', 'BoardMakers', true),
       (6, 6, 'Cooler Master', '800-555-4567', '120mm Case Fan', 'CF-55678', 'MasterFan Pro', 'Silent operation', 'FanSupplyCo', false),
       (null, 1, 'Cooler Master', '800-555-4567', '120mm Case Fan', 'CF-55678', 'MasterFan Pro', 'Silent operation', 'FanSupplyCo', true),
       (1, 1, 'NVIDIA', '800-555-6789', 'Graphics Card', 'GC-66789', 'GeForce RTX 2070', 'Ray tracing enabled', 'GraphixDepot', false),
       (2, 2, 'Seagate', '800-555-7890', '4TB External HDD', 'HD-77890', 'Backup Plus', 'USB 3.0 interface', 'StorageCentral', false),
       (3, 3, 'Logitech', '800-555-8901', 'Wireless Mouse', 'MS-88901', 'MX Master 3', 'Ergonomic design', 'PeripheralStore', false),
       (4, 4, 'Dell', '800-555-9012', '27-inch Monitor', 'MN-99012', 'UltraSharp U2719D', 'QHD resolution', 'ScreenSupplier', false);

INSERT INTO resource (expiration_date, brand, contact, description, identifier, name, supplier)
VALUES ('2025-12-31 23:59:59', 'Microsoft', 'contact@microsoft.com', 'License for Microsoft Office 365', 'ms365-001', 'Microsoft Office 365', 'Microsoft Inc.'),
       ('2026-06-30 23:59:59', 'JetBrains', 'support@jetbrains.com', 'Subscription for JetBrains All Products Pack', 'jbapp-002', 'JetBrains All Products Pack', 'JetBrains s.r.o.'),
       ('2025-11-15 23:59:59', 'Atlassian', 'sales@atlassian.com', 'License for Jira Software', 'jira-003', 'Jira Software', 'Atlassian Pty Ltd'),
       ('2026-09-01 23:59:59', 'Slack', 'help@slack.com', 'Subscription for Slack Workspace', 'slack-004', 'Slack Workspace', 'Slack Technologies'),
       ('2025-08-20 23:59:59', 'Adobe', 'support@adobe.com', 'License for Adobe Creative Cloud', 'adobe-005', 'Adobe Creative Cloud', 'Adobe Inc.'),
       ('2026-03-31 23:59:59', 'GitHub', 'contact@github.com', 'GitHub Enterprise License', 'ghub-006', 'GitHub Enterprise', 'GitHub Inc.'),
       ('2025-12-01 23:59:59', 'Amazon', 'aws-support@amazon.com', 'AWS Cloud Service Subscription', 'aws-007', 'Amazon Web Services', 'Amazon Web Services Inc.'),
       ('2026-07-15 23:59:59', 'Google', 'cloud-support@google.com', 'Google Cloud Platform Subscription', 'gcp-008', 'Google Cloud Platform', 'Google LLC'),
       ('2025-10-10 23:59:59', 'Oracle', 'support@oracle.com', 'Oracle Database License', 'oracle-009', 'Oracle Database', 'Oracle Corporation'),
       ('2026-04-25 23:59:59', 'Salesforce', 'contact@salesforce.com', 'Salesforce CRM License', 'sforce-010', 'Salesforce CRM', 'Salesforce Inc.');

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

INSERT INTO chat_message (project_id, sender_id, timestamp, content)
VALUES (1,1,'2024-06-10 18:00:00','Hey, someone is here?'),
       (1,2,'2024-06-10 18:15:00', 'Im here, what do you need?'),
       (1,3,'2024-06-10 18:30:00', 'Just give me 5min, pls...'),
       (1,2,'2024-06-11 18:15:00', 'So, did you fall asleep? Do you still need anything?'),
       (1,3, '2024-06-11 18:30:00', 'Yup, Im still here btw...'),
       (1,1, '2024-06-13 18:15:00', 'Sorry guys, was 3 crazy days to me... Are you available for a meeting in 1 hour?');

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




