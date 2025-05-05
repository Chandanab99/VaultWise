INSERT INTO user (id, username, password) VALUES (1, 'demo', 'demo');

INSERT INTO expense (id, description, amount, date, user_id)
VALUES (1, 'Groceries', 250.0, '2025-04-01', 1),
       (2, 'Internet Bill', 800.0, '2025-04-03', 1);
