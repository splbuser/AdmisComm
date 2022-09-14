-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema AdmissionsCommittee
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema AdmissionsCommittee
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `AdmissionsCommittee` DEFAULT CHARACTER SET utf8;
USE `AdmissionsCommittee`;

-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`faculty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`faculty`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`faculty`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(255) NOT NULL,
    `budget_places`  INT UNSIGNED NOT NULL,
    `total_places`   INT UNSIGNED NOT NULL,
    `first_subject`  VARCHAR(32)  NOT NULL,
    `second_subject` VARCHAR(32)  NOT NULL,
    PRIMARY KEY (`id`),
    FULLTEXT INDEX `idx_faculty_name` (`name`) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`applicant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`applicant`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`applicant`
(
    `id`                      INT          NOT NULL AUTO_INCREMENT,
    `user_name`               VARCHAR(24)  NOT NULL,
    `password`                VARCHAR(16)  NOT NULL,
    `admin_status`            BOOLEAN      NULL DEFAULT 0,
    `first_name`              VARCHAR(32)  NOT NULL,
    `last_name`               VARCHAR(24)  NOT NULL,
    `email`                   VARCHAR(255) NOT NULL,
    `city`                    VARCHAR(32)  NOT NULL,
    `region`                  VARCHAR(32)  NOT NULL,
    `educational_institution` VARCHAR(64)  NOT NULL,
    `block_status`            BOOLEAN      NULL DEFAULT 0,
    `enroll_status`           TINYINT      NULL DEFAULT 3,
    `upload_status`           VARCHAR(128) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    UNIQUE INDEX `user-name_UNIQUE` (`user_name` ASC) VISIBLE
);


-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`applicant_has_faculty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`applicant_has_faculty`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`applicant_has_faculty`
(
    `applicant_id`       INT NOT NULL,
    `faculty_id`         INT NOT NULL,
    `first_subj_result`  INT NOT NULL,
    `second_subj_result` INT NOT NULL,
    PRIMARY KEY (`applicant_id`, `faculty_id`),
    INDEX `fk_applicant_has_faculty_faculty1_idx` (`faculty_id` ASC) VISIBLE,
    INDEX `fk_applicant_has_faculty_applicant_idx` (`applicant_id` ASC) VISIBLE,
    CONSTRAINT `fk_applicant_has_faculty_applicant`
        FOREIGN KEY (`applicant_id`)
            REFERENCES `AdmissionsCommittee`.`applicant` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_applicant_has_faculty_faculty1`
        FOREIGN KEY (`faculty_id`)
            REFERENCES `AdmissionsCommittee`.`faculty` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`applicant_results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`applicant_results`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`applicant_results`
(
    `applicant_id` INT          NOT NULL,
    `algebra`      INT UNSIGNED NULL,
    `biology`      INT UNSIGNED NULL,
    `chemistry`    INT UNSIGNED NULL,
    `english`      INT UNSIGNED NULL,
    `literature`   INT UNSIGNED NULL,
    `history`      INT UNSIGNED NULL,
    PRIMARY KEY (`applicant_id`),
    INDEX `fk_applicant_results_applicant1_idx` (`applicant_id` ASC) VISIBLE,
    CONSTRAINT `fk_applicant_results_applicant1`
        FOREIGN KEY (`applicant_id`)
            REFERENCES `AdmissionsCommittee`.`applicant` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`statement_app`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`statement_app`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`statement_app`
(
    `faculty__id`  INT NOT NULL,
    `applicant_id` INT NOT NULL,
    `total_score`  INT NOT NULL,
    PRIMARY KEY (`faculty__id`, `applicant_id`),
    INDEX `fk_statement_app_applicant1_idx` (`applicant_id` ASC) VISIBLE,
    INDEX `fk_statement_app_faculty1_idx` (`faculty__id` ASC) VISIBLE,
    CONSTRAINT `fk_statement_app_applicant1`
        FOREIGN KEY (`applicant_id`)
            REFERENCES `AdmissionsCommittee`.`applicant` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_statement_app_faculty1`
        FOREIGN KEY (`faculty__id`)
            REFERENCES `AdmissionsCommittee`.`faculty` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table `AdmissionsCommittee`.`statement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AdmissionsCommittee`.`enrollment`;

CREATE TABLE IF NOT EXISTS `AdmissionsCommittee`.`enrollment`
(
    `faculty_id`   INT         NOT NULL,
    `applicant_id` INT         NOT NULL,
    `status`       VARCHAR(16) NOT NULL,
    PRIMARY KEY (`faculty_id`, `applicant_id`),
    INDEX `fk_enrollment_applicant1_idx` (`applicant_id` ASC) VISIBLE,
    CONSTRAINT `fk_enrollment_faculty1`
        FOREIGN KEY (`faculty_id`)
            REFERENCES `AdmissionsCommittee`.`faculty` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_enrollment_applicant1`
        FOREIGN KEY (`applicant_id`)
            REFERENCES `AdmissionsCommittee`.`applicant` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;



-- -----------------------------------------------------
-- Staffing faculty
-- -----------------------------------------------------
INSERT INTO faculty
VALUES (DEFAULT, 'Biology', 2, 4, 'Botanic', 'Sociology');
INSERT INTO faculty
VALUES (DEFAULT, 'Physics', 1, 4, 'Math', 'Physics');
INSERT INTO faculty
VALUES (DEFAULT, 'Engineering', 2, 4, 'Geometry', 'Physics');
INSERT INTO faculty
VALUES (DEFAULT, 'Computer Science', 1, 4, 'Computer science', 'Math');
INSERT INTO faculty
VALUES (DEFAULT, 'Economics', 2, 4, 'Economics', 'Math');

-- -----------------------------------------------------
-- Staffing applicants  `user_name` `password` `admin_status` `first_name` `last_name` `email` 
-- `city` `region` `educational_institution` `block_status` `enroll_status`
-- -----------------------------------------------------
INSERT INTO applicant
VALUES (DEFAULT, 'super_admin', 'MTIzNjk', TRUE, 'not available', 'not available', 'therea1ant@gmail.com',
        'not available', 'not available', 'not available High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant
VALUES (DEFAULT, 'Boniadi99', 'MTIzNjk', DEFAULT, 'Boniadi', 'Deninio', 'borbur4yt1@gmail.com', 'Celebrimbor', 'West',
        'Celebrimbor High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (2, 10, 10, 12, 11, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'aramayo', 'MTIzNjk', DEFAULT, 'Aramayo', 'Delfy', 'borbur4yt2@gmail.com', 'Isildur', 'East',
        'Isildur High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (3, 9, 8, 11, 10, 9, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'NotMulla', 'MTIzNjk', DEFAULT, 'Mullan', 'Chee', 'borbur4yt3@gmail.com', 'Elendil', 'West',
        'Elendil High Scool', TRUE, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (4, 12, 11, 12, 11, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, '999eddyy999', 'MTIzNjk', DEFAULT, 'Edwards', 'Norton', 'borbur4yt4@gmail.com', 'Halbrand', 'East',
        'Halbrand High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (5, 6, 8, 6, 6, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'oldGraver', 'MTIzNjk', DEFAULT, 'Gravelle', 'Tornthon', 'Gravelle@gmail.com', 'Kemen', 'South',
        'Kemen High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (6, 6, 8, 6, 6, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'El_Diablo', 'MTIzNjk', DEFAULT, 'Hernandez', 'Mutto', 'Hernandez@gmail.com', 'MarcSpector', 'South',
        'MarcSpector High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (7, 8, 11, 10, 9, 8, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'abby555', 'MTIzNjk', DEFAULT, 'Abraham', 'Milton', 'Abraham@gmail.com', 'Khonshu', 'North',
        'Khonshu High Scool', TRUE, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (8, 4, 6, 8, 8, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'preetyGirl', 'MTIzNjk', DEFAULT, 'Abdallena', 'Miller', 'Abdalla@gmail.com', 'Selim', 'West',
        'Selim High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (9, 9, 10, 11, 10, 9, 10);
INSERT INTO applicant
VALUES (DEFAULT, '83rmuddy', 'MTIzNjk', DEFAULT, 'Bermudez', 'Sohito', 'Bermudez@gmail.com', 'Yatzil', 'South',
        'Yatzil High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (10, 10, 9, 8, 10, 10, 8);
INSERT INTO applicant
VALUES (DEFAULT, 'samuray15', 'MTIzNjk', DEFAULT, 'Morimoto', 'Otagawa', 'Morimoto@gmail.com', 'Mogart', 'North',
        'Mogart High Scool', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (11, 9, 9, 8, 10, 9, 11);
INSERT INTO applicant
VALUES (DEFAULT, 'mrPippinn', 'MTIzNjk', DEFAULT, 'Василий', 'Углофф', 'vasaya_ug1991@gmail.com', 'Берлин', 'Окраины',
        'ЗОШ №5', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (12, 7, 8, 5, 6, 5, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'viper369', 'MTIzNjk', DEFAULT, 'Андреано', 'Адрионополи', 'nosmoker@yahoo.com', 'Кущипицы',
        'ЖДВокзал', 'ПТУ 13', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (13, 3, 3, 8, 3, 8, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'Kriger', 'MTIzNjk', DEFAULT, 'Шульц', 'Кригер', 'therealant@gmail.com', 'Баден-Баден', 'Вестфалия',
        'Секретно', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant
VALUES (DEFAULT, 'lokus', 'MTIzNjk', DEFAULT, 'Inhmar', 'Bergman', 'smoker@yahoo.com', 'Usual',
        'Downtown', 'ППТУ', DEFAULT, DEFAULT, DEFAULT);
-- INSERT INTO applicant_results
-- VALUES (14, 5, 9, 8, 12, 8, 8);
INSERT INTO applicant
VALUES (DEFAULT, 'ioioi', 'MTIzNjk', DEFAULT, 'Yoko', 'Ono', 'ononon@yahoo.com', 'Tokio',
        'Raiways', 'MFTTU', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (15, 10, 10, 5, 9, 12, 3);
INSERT INTO applicant
VALUES (DEFAULT, 'jason', 'MTIzNjk', DEFAULT, 'Killson', 'Foxtrot', 'toreador@yahoo.com', 'Jurmala',
        'Kostoro', 'Shhool12', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (16, 8, 8, 8, 6, 6, 6);
INSERT INTO applicant
VALUES (DEFAULT, 'kuokoo', 'MTIzNjk', DEFAULT, 'Hillen', 'Totty', 'totty@hoo.com', 'Hamp',
        'KGB', 'East Church School', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (17, 10, 11, 10, 11, 8, 11);
INSERT INTO applicant
VALUES (DEFAULT, 'uranus', 'MTIzNjk', DEFAULT, 'Toffler', 'Coldwell', 'toffycold@gmail.com', 'Reno',
        'Jusimo', 'Higher School', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (18, 9, 8, 9, 10, 7, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'olof', 'MTIzNjk', DEFAULT, 'Ошлаф', 'Нешольц', 'dasgot@ger.com', 'Кёнинсбёрг',
        'Фонтан', 'Штази', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (19, 11, 11, 12, 10, 10, 11);
INSERT INTO applicant
VALUES (DEFAULT, 'copper', 'MTIzNjk', DEFAULT, 'Мини', 'Купер', 'bigcooper@cooper.com', 'Соролья',
        'Минчетто', 'Акапуолло', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (20, 8, 7, 10, 9, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'tristan369', 'MTIzNjk', DEFAULT, 'Terro', 'Gobby', 'gopstop@gop.gop', 'Ellada',
        'Polis', 'Megapolis', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (21, 8, 8, 7, 7, 8, 8);
INSERT INTO applicant
VALUES (DEFAULT, 'pianto', 'MTIzNjk', DEFAULT, 'Giganto', 'Gigonne', 'gigig@cool.com', 'Sepretto',
        'Gilijjo', 'Pascale Customs', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (22, 6, 8, 10, 9, 9, 8);
INSERT INTO applicant
VALUES (DEFAULT, 'toristo', 'MTIzNjk', DEFAULT, 'Uno', 'Momento', 'memento@mem.com', 'Yuild',
        'Kolmon', 'JDLK', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (23, 1, 10, 8, 9, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'chemic', 'MTIzNjk', DEFAULT, 'Illfat', 'Gudinoff', 'nonokay@yahoo.com', 'Iliddy',
        'Ollas', 'TETinc.', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (24, 8, 9, 8, 9, 8, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'knight55', 'MTIzNjk', DEFAULT, 'Julia', 'Felsy', 'mailme@now.com', 'Verty',
        'Okotto', 'HTY tec', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (25, 11, 10, 8, 9, 10, 12);
INSERT INTO applicant
VALUES (DEFAULT, 'fukijy', 'MTIzNjk', DEFAULT, 'Ivan', 'Rathermenn', 'Rathermenn@now.com', 'Gojo',
        'Ikl', 'PtReCo', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (26, 9, 9, 10, 8, 8, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'jitter', 'MTIzNjk', DEFAULT, 'Петр', 'Филч', 'pfilch@now.com', 'Тифлис',
        'Turtl', 'HTY tec', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (27, 10, 10, 9, 9, 7, 7);
INSERT INTO applicant
VALUES (DEFAULT, 'kilos13', 'MTIzNjk', DEFAULT, 'Stephen', 'Queen', 'queenkiller@now.com', 'QVerty',
        'HeavyArmon', 'HUT', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (28, 8, 8, 8, 9, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, '123yuk', 'MTIzNjk', DEFAULT, 'Марат', 'Йогурт', 'maratus@now.com', 'Искоростень',
        'OkkoL', 'OOOl', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (29, 10, 10, 10, 9, 11, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'kreofirm', 'MTIzNjk', DEFAULT, 'Kobo', 'Steel', 'jojo@now.com', 'Centenary',
        'Fit Left', 'Ikurt', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (30, 9, 9, 8, 7, 8, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'pillertman', 'MTIzNjk', DEFAULT, 'Get', 'Ten', 'ten10@now.com', 'Temple',
        'Theano', 'Private', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (31, 9, 8, 7, 7, 9, 9);
INSERT INTO applicant
VALUES (DEFAULT, 'mer', 'MTIzNjk', DEFAULT, 'Post', 'Eleven', 'ten11@now.com', 'Of Elemental',
        'Theory', 'Public', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (32, 6, 5, 9, 10, 10, 10);
INSERT INTO applicant
VALUES (DEFAULT, 'hiper7', 'MTIzNjk', DEFAULT, 'Commit', 'Twelve', 'twelve10@now.com', 'Evil',
        'Fit Left', 'Tothen', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO applicant_results
VALUES (33, 10, 9, 11, 11, 10, 10);
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
VALUES (13, 3, 8, 8);
INSERT INTO applicant_has_faculty
VALUES (15, 1, 9, 9);
INSERT INTO applicant_has_faculty
VALUES (15, 2, 10, 11);
INSERT INTO applicant_has_faculty
VALUES (16, 3, 10, 10);
INSERT INTO applicant_has_faculty
VALUES (16, 4, 8, 9);
INSERT INTO applicant_has_faculty
VALUES (16, 5, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (17, 1, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (17, 3, 8, 12);
INSERT INTO applicant_has_faculty
VALUES (18, 2, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (19, 1, 7, 9);
INSERT INTO applicant_has_faculty
VALUES (19, 3, 6, 8);
INSERT INTO applicant_has_faculty
VALUES (19, 4, 5, 8);
INSERT INTO applicant_has_faculty
VALUES (20, 2, 8, 10);
INSERT INTO applicant_has_faculty
VALUES (20, 3, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (21, 3, 7, 10);
INSERT INTO applicant_has_faculty
VALUES (21, 4, 6, 9);
INSERT INTO applicant_has_faculty
VALUES (22, 2, 8, 9);
INSERT INTO applicant_has_faculty
VALUES (22, 3, 7, 10);
INSERT INTO applicant_has_faculty
VALUES (22, 4, 10, 10);
INSERT INTO applicant_has_faculty
VALUES (23, 1, 11, 8);
INSERT INTO applicant_has_faculty
VALUES (23, 2, 10, 8);
INSERT INTO applicant_has_faculty
VALUES (24, 1, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (24, 3, 12, 11);
INSERT INTO applicant_has_faculty
VALUES (24, 5, 8, 10);
INSERT INTO applicant_has_faculty
VALUES (25, 1, 7, 9);
INSERT INTO applicant_has_faculty
VALUES (25, 2, 10, 11);
INSERT INTO applicant_has_faculty
VALUES (25, 3, 12, 10);
INSERT INTO applicant_has_faculty
VALUES (25, 4, 10, 11);
INSERT INTO applicant_has_faculty
VALUES (26, 1, 10, 9);
INSERT INTO applicant_has_faculty
VALUES (26, 2, 11, 8);
INSERT INTO applicant_has_faculty
VALUES (27, 3, 9, 10);
INSERT INTO applicant_has_faculty
VALUES (27, 4, 8, 11);
INSERT INTO applicant_has_faculty
VALUES (28, 5, 7, 9);
INSERT INTO applicant_has_faculty
VALUES (28, 1, 7, 8);
INSERT INTO applicant_has_faculty
VALUES (28, 2, 6, 10);
INSERT INTO applicant_has_faculty
VALUES (29, 3, 7, 11);
INSERT INTO applicant_has_faculty
VALUES (29, 4, 9, 11);
INSERT INTO applicant_has_faculty
VALUES (30, 5, 11, 8);
INSERT INTO applicant_has_faculty
VALUES (30, 1, 10, 9);
INSERT INTO applicant_has_faculty
VALUES (30, 2, 10, 8);
INSERT INTO applicant_has_faculty
VALUES (31, 5, 11, 9);
INSERT INTO applicant_has_faculty
VALUES (31, 4, 12, 11);
INSERT INTO applicant_has_faculty
VALUES (32, 5, 11, 10);
INSERT INTO applicant_has_faculty
VALUES (32, 3, 9, 8);
INSERT INTO applicant_has_faculty
VALUES (32, 2, 10, 8);
INSERT INTO applicant_has_faculty
VALUES (33, 1, 8, 9);
INSERT INTO applicant_has_faculty
VALUES (33, 2, 8, 6);
INSERT INTO applicant_has_faculty
VALUES (33, 4, 9, 6);

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