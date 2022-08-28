-- -----------------------------------------------------
-- Staffing faculty
-- -----------------------------------------------------
INSERT INTO faculty
VALUES (DEFAULT, 'Biology', 1, 3, 'Botanic', 'Sociology');
INSERT INTO faculty
VALUES (DEFAULT, 'Physics', 1, 3, 'Math', 'Physics');
INSERT INTO faculty
VALUES (DEFAULT, 'Engineering', 1, 3, 'Geometry', 'Physics');
INSERT INTO faculty
VALUES (DEFAULT, 'Computer Science', 1, 3, 'Computer science', 'Math');
INSERT INTO faculty
VALUES (DEFAULT, 'Economics', 1, 3, 'Economics', 'Math');

-- -----------------------------------------------------
-- Staffing applicants  `user_name` `password` `admin_status` `first_name` `last_name` `email` 
-- `city` `region` `educational_institution` `block_status` `enroll_status`
-- -----------------------------------------------------
INSERT INTO applicant
VALUES (DEFAULT, 'super_admin', 'MTIzNjk', TRUE, 'N/A', 'N/A', 'borbur4yt@gmail.com',
        'N/A', 'N/A', 'N/A', DEFAULT, DEFAULT);
INSERT INTO applicant
VALUES (DEFAULT, 'Boniadi99', 'MTIzNjk', DEFAULT, 'Boniadi', 'Deninio', 'borbur4yt1@gmail.com', 'Celebrimbor', 'West',
        'Celebrimbor High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (2, 10, 10, 12, 11, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'aramayo', 'MTIzNjk', DEFAULT, 'Aramayo', 'Delfy', 'borbur4yt2@gmail.com', 'Isildur', 'East',
        'Isildur High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (3, 9, 8, 11, 10, 9, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'NotMulla', 'MTIzNjk', DEFAULT, 'Mullan', 'Chee', 'borbur4yt3@gmail.com', 'Elendil', 'West',
        'Elendil High Scool', TRUE, DEFAULT);
INSERT INTO applicant_results
VALUES (4, 12, 11, 12, 11, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, '999eddyy999', 'MTIzNjk', DEFAULT, 'Edwards', 'Norton', 'borbur4yt4@gmail.com', 'Halbrand', 'East',
        'Halbrand High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (5, 6, 8, 6, 6, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'oldGraver', 'MTIzNjk', DEFAULT, 'Gravelle', 'Tornthon', 'Gravelle@gmail.com', 'Kemen', 'South',
        'Kemen High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (6, 6, 8, 6, 6, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'El_Diablo', 'MTIzNjk', DEFAULT, 'Hernandez', 'Mutto', 'Hernandez@gmail.com', 'MarcSpector', 'South',
        'MarcSpector High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (7, 8, 11, 10, 9, 8, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'abby555', 'MTIzNjk', DEFAULT, 'Abraham', 'Milton', 'Abraham@gmail.com', 'Khonshu', 'North',
        'Khonshu High Scool', TRUE, DEFAULT);
INSERT INTO applicant_results
VALUES (8, 4, 6, 8, 8, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'preetyGirl', 'MTIzNjk', DEFAULT, 'Abdallena', 'Miller', 'Abdalla@gmail.com', 'Selim', 'West',
        'Selim High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (9, 9, 10, 11, 10, 9, 10);
INSERT INTO applicant
VALUES (DEFAULT, '83rmuddy', 'MTIzNjk', DEFAULT, 'Bermudez', 'Sohito', 'Bermudez@gmail.com', 'Yatzil', 'South',
        'Yatzil High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (10, 10, 9, 8, 10, 10, 8);
INSERT INTO applicant
VALUES (DEFAULT, 'samuray15', 'MTIzNjk', DEFAULT, 'Morimoto', 'Otagawa', 'Morimoto@gmail.com', 'Mogart', 'North',
        'Mogart High Scool', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (11, 9, 9, 8, 10, 9, 11);
INSERT INTO applicant
VALUES (DEFAULT, 'mrPippinn', 'MTIzNjk', DEFAULT, 'Василий', 'Углофф', 'vasaya_ug1991@gmail.com', 'Берлин', 'Окраины',
        'ЗОШ №5', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (12, 7, 8, 5, 6, 5, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'viper369', 'MTIzNjk', DEFAULT, 'Андреано', 'Адрионополи', 'nosmoker@yahoo.com', 'Кущипицы',
        'ЖД Вокзал', 'ПТУ 13', DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (13, 3, 3, 8, 3, 8, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'Kriger', 'MTIzNjk', DEFAULT, 'Вильгельм', 'Кригер', 'borbur4yt15@gmail.com', 'Баден-Баден', 'Вестфалия',
        'Школа рабочей молодежи', DEFAULT, DEFAULT);

-- -----------------------------------------------------
-- Staffing applicants' faculty
-- -----------------------------------------------------
INSERT INTO applicant_has_faculty
VALUES (2, 2, 10, 9);
INSERT INTO applicant_has_faculty
VALUES (2, 4, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (3, 1, 8, 11);
INSERT INTO applicant_has_faculty
VALUES (3, 4, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (4, 1, 7, 7);
INSERT INTO applicant_has_faculty
VALUES (4, 2, 7, 9);
INSERT INTO applicant_has_faculty
VALUES (4, 5, 10, 12);
INSERT INTO applicant_has_faculty
VALUES (4, 4, 11, 12);
INSERT INTO applicant_has_faculty
VALUES (5, 1, 10, 11);
INSERT INTO applicant_has_faculty
VALUES (5, 2, 12, 11);
INSERT INTO applicant_has_faculty
VALUES (6, 2, 10, 10);
INSERT INTO applicant_has_faculty
VALUES (6, 3, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (6, 5, 8, 8);
INSERT INTO applicant_has_faculty
VALUES (7, 4, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (7, 2, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (7, 5, 11, 12);
INSERT INTO applicant_has_faculty
VALUES (8, 4, 10, 12);
INSERT INTO applicant_has_faculty
VALUES (8, 5, 10, 8);
INSERT INTO applicant_has_faculty
VALUES (9, 1, 8, 8);
INSERT INTO applicant_has_faculty
VALUES (9, 4, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (10, 1, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (10, 3, 8, 9);
INSERT INTO applicant_has_faculty
VALUES (10, 4, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (11, 2, 10, 10);
INSERT INTO applicant_has_faculty
VALUES (11, 3, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (11, 5, 7, 9);
INSERT INTO applicant_has_faculty
VALUES (12, 2, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (12, 4, 8, 9);
INSERT INTO applicant_has_faculty
VALUES (12, 5, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (13, 1, 8, 10);
INSERT INTO applicant_has_faculty
VALUES (13, 2, 8, 8);
INSERT INTO applicant_has_faculty
VALUES (13, 3, 9, 11);

INSERT INTO statement_app VALUES (1, 2);
INSERT INTO statement_app VALUES (1, 3);
INSERT INTO statement_app VALUES (1, 4);
INSERT INTO statement_app VALUES (1, 5);
INSERT INTO statement_app VALUES (1, 7);
INSERT INTO statement_app VALUES (1, 8);
INSERT INTO statement_app VALUES (1, 9);
INSERT INTO statement_app VALUES (1, 10);
INSERT INTO statement_app VALUES (2, 2);
INSERT INTO statement_app VALUES (2, 3);
INSERT INTO statement_app VALUES (2, 4);
INSERT INTO statement_app VALUES (2, 5);
INSERT INTO statement_app VALUES (2, 7);

-- -----------------------------------------------------
-- Show tables
-- -----------------------------------------------------
SELECT *
FROM applicant;
SELECT *
FROM faculty;
SELECT *
FROM applicant_results;
SELECT *
FROM applicant_has_faculty
ORDER BY applicant_id;
SELECT *
FROM statement_app;
SELECT *
FROM enrollment
ORDER BY faculty_id;