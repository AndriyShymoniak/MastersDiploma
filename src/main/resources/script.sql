DROP DATABASE IF EXISTS masters_project;
CREATE DATABASE masters_project;

-- Contains personal information about users
CREATE SEQUENCE person_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE person
(
    person_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('person_sequence'::regclass),
    name      VARCHAR(100),
    surname   VARCHAR(100),
    email     VARCHAR(100),
    PRIMARY KEY (person_id)
);

-- Contains all possible user roles
CREATE SEQUENCE user_role_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE user_role
(
    user_role_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('user_role_sequence'::regclass),
    role         VARCHAR(50),
    PRIMARY KEY (user_role_id)
);

-- Contains all users
CREATE SEQUENCE user_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE user
(
    user_id         NUMERIC(16)  NOT NULL DEFAULT NEXTVAL('user_sequence'::regclass),
    username        VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    user_role_id    NUMERIC(16),
    person_id       NUMERIC(16),
    registered_date TIMESTAMP,
    PRIMARY KEY (user_id),
    CONSTRAINT "fk_user_user_role_id" FOREIGN KEY (user_role_id) REFERENCES user_role (user_role_id),
    CONSTRAINT "fk_user_person_id" FOREIGN KEY (person_id) REFERENCES person (person_id)
);

-- Objects to be recognized (tank, atv, ...)
CREATE SEQUENCE observed_object_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE observed_object
(
    observed_object_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('observed_object_sequence'::regclass),
    object_name        VARCHAR(50),
    PRIMARY KEY (observed_object_id)
);

-- Stores links to media
CREATE SEQUENCE media_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE media
(
    media_id            NUMERIC(16) NOT NULL DEFAULT NEXTVAL('media_sequence'::regclass),
    original_media_url  VARCHAR(500),
    processed_media_url VARCHAR(500),
    create_user         NUMERIC(16),
    create_date         TIMESTAMP,
    PRIMARY KEY (media_id),
    CONSTRAINT "fk_media_create_user" FOREIGN KEY (create_user) REFERENCES user (user_id)
);

-- Contains locations of detected objects
CREATE SEQUENCE location_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE location
(
    location_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('location_sequence'::regclass),
    longitude   VARCHAR(100),
    latitude    VARCHAR(100),
    PRIMARY KEY (location_id)
);

-- Stores information about used ML models
CREATE SEQUENCE ml_model_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE ml_model
(
    ml_model_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('ml_model_sequence'::regclass),
    model_name  VARCHAR(100),
    model_path  VARCHAR(200),
    is_custom   NUMERIC(1),
    is_active   NUMERIC(1),
    create_user NUMERIC(16),
    create_date TIMESTAMP,
    PRIMARY KEY (ml_model_id)
);

-- Relation between ml_model and observed_object tables
CREATE SEQUENCE ml_model_observed_object_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE ml_model_observed_object
(
    ml_model_observed_object_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('ml_model_observed_object_sequence'::regclass),
    ml_model_id                 NUMERIC(16),
    observed_object_id          NUMERIC(16),
    PRIMARY KEY (ml_model_observed_object_id),
    CONSTRAINT "fk_mmoo_ml_model_id" FOREIGN KEY (ml_model_id) REFERENCES ml_model (ml_model_id),
    CONSTRAINT "fk_mmoo_observed_object_id" FOREIGN KEY (observed_object_id) REFERENCES observed_object (observed_object_id)
);

-- Contains all necessary data about recognized objects
CREATE SEQUENCE recognition_result_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE recognition_result
(
    recognition_result_id  NUMERIC(16) NOT NULL DEFAULT NEXTVAL('recognition_result_sequence'::regclass),
    description            VARCHAR(500),
    is_object_present      NUMERIC(1),
    is_recognition_correct NUMERIC(1),
    ml_model_id            NUMERIC(16),
    media_id               NUMERIC(16),
    location_id            NUMERIC(16),
    create_user            NUMERIC(16),
    create_date            TIMESTAMP,
    update_user            NUMERIC(16),
    update_date            TIMESTAMP,
    PRIMARY KEY (recognition_result_id),
    CONSTRAINT "fk_recognition_result_ml_model_id" FOREIGN KEY (ml_model_id) REFERENCES ml_model (ml_model_id),
    CONSTRAINT "fk_recognition_result_media_id" FOREIGN KEY (media_id) REFERENCES media (media_id),
    CONSTRAINT "fk_recognition_result_location_id" FOREIGN KEY (location_id) REFERENCES location (location_id),
    CONSTRAINT "fk_recognition_result_create_user" FOREIGN KEY (create_user) REFERENCES user (user_id),
    CONSTRAINT "fk_recognition_result_update_user" FOREIGN KEY (update_user) REFERENCES user (user_id)
);

-- Relation between recognition_result and observed_object tables
CREATE SEQUENCE recognition_result_observed_object_sequence INCREMENT BY 1 MINVALUE 0 MAXVALUE 9223372036854775807 START 0 NO CYCLE;
CREATE TABLE recognition_result_observed_object
(
    recognition_result_observed_object_id NUMERIC(16) NOT NULL DEFAULT NEXTVAL('recognition_result_observed_object_sequence'::regclass),
    recognition_result_id                 NUMERIC(16),
    observed_object_id                    NUMERIC(16),
    PRIMARY KEY (recognition_result_observed_object_id),
    CONSTRAINT "fk_rsoo_recognition_result_id" FOREIGN KEY (recognition_result_id) REFERENCES recognition_result (recognition_result_id),
    CONSTRAINT "fk_rsoo_observed_object_id" FOREIGN KEY (observed_object_id) REFERENCES observed_object (observed_object_id)
);
