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
    `name`           VARCHAR(20) NOT NULL,
    `budget_places`  INT UNSIGNED NOT NULL,
    `total_places`   INT UNSIGNED NOT NULL,
    `first_subject`  VARCHAR(20)  NOT NULL,
    `second_subject` VARCHAR(20)  NOT NULL,
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
    `user_name`               VARCHAR(20)  NOT NULL,
    `password`                VARCHAR(20)  NOT NULL,
    `admin_status`            BOOLEAN      NULL DEFAULT 0,
    `first_name`              VARCHAR(20)  NOT NULL,
    `last_name`               VARCHAR(20)  NOT NULL,
    `email`                   VARCHAR(64) NOT NULL,
    `city`                    VARCHAR(20)  NOT NULL,
    `region`                  VARCHAR(20)  NOT NULL,
    `educational_institution` VARCHAR(64)  NOT NULL,
    `block_status`            BOOLEAN      NULL DEFAULT 0,
    `enroll_status`           TINYINT      NULL DEFAULT 0,
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