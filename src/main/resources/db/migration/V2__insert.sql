INSERT INTO app_user (username, password, role)
VALUES
    (
        'admin',
        '$2a$12$jr5MjkYV5QWRU.ggjHK9xuXBnMWCq6Sqog8IU1tk0emdCQXdg2KES', -- admin123
        'ROLE_ADMIN'
    ) ON CONFLICT (username) DO NOTHING;