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

