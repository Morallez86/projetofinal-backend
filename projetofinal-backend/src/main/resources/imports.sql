INSERT INTO user (first_name, last_name, username, password, email, role, active, pending, visibility) VALUES
    ('John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', 'U', true, false, true);

INSERT INTO skill (name, type, creator_id) VALUES ('Java', 'SOFTWARE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Coding', 'KNOWLEDGE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Placas Gráficas', 'HARDWARE', 1);
INSERT INTO skill (name, type, creator_id) VALUES ('Parafusos', 'TOOLS', 1);

INSERT INTO user_skill (user_id, skill_id) VALUES (1, 1);
