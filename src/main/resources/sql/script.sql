-- Drop-Create schema
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- Contains all users
CREATE SEQUENCE user_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE application_user
(
    user_id         BIGINT       NOT NULL DEFAULT NEXTVAL('user_sequence'::regclass),
    username        VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    registered_date TIMESTAMP,
    CONSTRAINT PK_ApplicationUser PRIMARY KEY (user_id)
);

-- Contains personal information about users
CREATE SEQUENCE person_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE person
(
    person_id BIGINT NOT NULL DEFAULT NEXTVAL('person_sequence'::regclass),
    name      VARCHAR(100),
    surname   VARCHAR(100),
    email     VARCHAR(100),
    user_id   BIGINT NOT NULL,
    CONSTRAINT PK_Person PRIMARY KEY (person_id),
    CONSTRAINT FK_Person_ApplicationUser FOREIGN KEY (user_id) REFERENCES application_user (user_id),
    CONSTRAINT UQ_Person_UserId UNIQUE (user_id),
    CONSTRAINT UQ_Person_Email UNIQUE (email)
);

-- Objects to be recognized (tank, atv, ...)
CREATE SEQUENCE observed_object_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE observed_object
(
    observed_object_id BIGINT      NOT NULL DEFAULT NEXTVAL('observed_object_sequence'::regclass),
    object_name        VARCHAR(50) NOT NULL,
    CONSTRAINT PK_ObservedObject PRIMARY KEY (observed_object_id)
);

-- Stores links to media
CREATE SEQUENCE media_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE media
(
    media_id            BIGINT       NOT NULL DEFAULT NEXTVAL('media_sequence'::regclass),
    original_media_url  VARCHAR(500) NOT NULL,
    processed_media_url VARCHAR(500) NOT NULL,
    create_user         BIGINT,
    create_date         TIMESTAMP,
    CONSTRAINT PK_Media PRIMARY KEY (media_id),
    CONSTRAINT FK_Media_ApplicationUser FOREIGN KEY (create_user) REFERENCES application_user (user_id)
);

-- Contains locations of detected objects
CREATE SEQUENCE location_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE location
(
    location_id BIGINT       NOT NULL DEFAULT NEXTVAL('location_sequence'::regclass),
    longitude   VARCHAR(100) NOT NULL,
    latitude    VARCHAR(100) NOT NULL,
    CONSTRAINT PK_Location PRIMARY KEY (location_id)
);

-- Stores information about used ML models
CREATE SEQUENCE ml_model_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE ml_model
(
    ml_model_id BIGINT       NOT NULL DEFAULT NEXTVAL('ml_model_sequence'::regclass),
    model_name  VARCHAR(100) NOT NULL,
    model_path  VARCHAR(200) NOT NULL,
    is_custom   NUMERIC(1),
    is_active   NUMERIC(1),
    create_user BIGINT,
    create_date TIMESTAMP,
    CONSTRAINT PK_MlModel PRIMARY KEY (ml_model_id),
    CONSTRAINT FK_MlModel_ApplicationUser FOREIGN KEY (create_user) REFERENCES application_user (user_id)
);

-- Relation between ml_model and observed_object tables
CREATE SEQUENCE ml_model_observed_object_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE ml_model_observed_object
(
    ml_model_observed_object_id BIGINT NOT NULL DEFAULT NEXTVAL('ml_model_observed_object_sequence'::regclass),
    ml_model_id                 BIGINT,
    observed_object_id          BIGINT,
    CONSTRAINT PK_MlModelObservedObject PRIMARY KEY (ml_model_observed_object_id),
    CONSTRAINT FK_MlModelObservedObject_MlModel FOREIGN KEY (ml_model_id) REFERENCES ml_model (ml_model_id),
    CONSTRAINT FK_MlModelObservedObject_ObservedObject FOREIGN KEY (observed_object_id) REFERENCES observed_object (observed_object_id)
);

-- Contains all necessary data about recognized objects
CREATE SEQUENCE recognition_result_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE recognition_result
(
    recognition_result_id  BIGINT NOT NULL DEFAULT NEXTVAL('recognition_result_sequence'::regclass),
    description            VARCHAR(500),
    is_object_present      NUMERIC(1),
    is_recognition_correct NUMERIC(1),
    ml_model_id            BIGINT,
    media_id               BIGINT,
    location_id            BIGINT,
    create_user            BIGINT,
    create_date            TIMESTAMP,
    update_user            BIGINT,
    update_date            TIMESTAMP,
    CONSTRAINT PK_RecognitionResult PRIMARY KEY (recognition_result_id),
    CONSTRAINT FK_RecognitionResult_MlModel FOREIGN KEY (ml_model_id) REFERENCES ml_model (ml_model_id),
    CONSTRAINT FK_RecognitionResult_Media FOREIGN KEY (media_id) REFERENCES media (media_id),
    CONSTRAINT FK_RecognitionResult_Location FOREIGN KEY (location_id) REFERENCES location (location_id),
    CONSTRAINT FK_RecognitionResult_ApplicationUser_CreateUser FOREIGN KEY (create_user) REFERENCES application_user (user_id),
    CONSTRAINT FK_RecognitionResult_ApplicationUser_UpdateUser FOREIGN KEY (update_user) REFERENCES application_user (user_id)
);

-- Relation between recognition_result and observed_object tables
CREATE SEQUENCE recognition_result_observed_object_sequence INCREMENT BY 20 MINVALUE 0 MAXVALUE 9223372036854775807 START 100 NO CYCLE;
CREATE TABLE recognition_result_observed_object
(
    recognition_result_observed_object_id BIGINT NOT NULL DEFAULT NEXTVAL('recognition_result_observed_object_sequence'::regclass),
    recognition_result_id                 BIGINT,
    observed_object_id                    BIGINT,
    CONSTRAINT PK_RecognitionResultObservedObject PRIMARY KEY (recognition_result_observed_object_id),
    CONSTRAINT FK_RecognitionResultObservedObject_RecognitionResult FOREIGN KEY (recognition_result_id) REFERENCES recognition_result (recognition_result_id),
    CONSTRAINT FK_RecognitionResultObservedObject_ObservedObject FOREIGN KEY (observed_object_id) REFERENCES observed_object (observed_object_id)
);
