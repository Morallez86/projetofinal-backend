INSERT INTO user (first_name, last_name, username, password, email, role, active, pending, visibility) VALUES
    ('John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', 'U', true, false, true);

INSERT INTO skill (name, type, creator_id) VALUES ('Java', 'SOFTWARE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Coding', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Placas Gráficas', 'HARDWARE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Parafusos', 'TOOLS', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Git', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('React', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Kraken', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('VSCode', 'KNOWLEDGE', 1);

INSERT INTO interest (creator_id, name) VALUES (1, 'CyberSecurity');
INSERT INTO interest (creator_id, name) VALUES (1, 'Animals');
INSERT INTO interest (creator_id, name) VALUES (1, 'Math');
INSERT INTO interest (creator_id, name) VALUES (1, 'Nature');

INSERT INTO user_skill (user_id, skill_id) VALUES (1, 1);
INSERT INTO user_skill (user_id, skill_id) VALUES (1, 3);
INSERT INTO user_skill (user_id, skill_id) VALUES (1, 2);
INSERT INTO user_skill (user_id, skill_id) VALUES (1, 4);
INSERT INTO user_skill (user_id, skill_id) VALUES (1, 5);
INSERT INTO user_skill (user_id, skill_id) VALUES (1, 6);

INSERT INTO resource (expiration_date, brand, contact, description, identifier, name, supplier)
VALUES ('2024-06-10 17:00:00', 'brand3', 'contact2', 'description2', 'identifier35', 'name2', 'supplier2');

INSERT INTO component(project_id, workplace_id, brand, contact, description, identifier, name, observation, supplier)
VALUES (NULL, 1, 'brand3', '999-999', 'description2', 'identifier2', 'component2', 'observation2', 'supplier2');

INSERT INTO project(approved, max_users, status, approved_date, creation_date, end_date, owner_id, planned_end_date, starting_date, description, motivation, title)
VALUES (true, 5, 'IN_PROGRESS', '2024-06-02T10:00:00', '2024-06-02T10:30:00', '2024-06-10T17:00:00', 1, '2024-06-10T17:00:00', '2024-06-03T09:00:00', 'This is a test project created in Postman.', 'To test the functionality.', 'Test Project 40')

INSERT INTO user_project (is_admin, project_id, user_id) VALUES (true, 1, 1);
