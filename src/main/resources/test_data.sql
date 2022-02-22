INSERT INTO person (person_id, name, surname, email)
VALUES (1, 'Anton', 'Stefaniv', 'anton_s@email.com'),
       (2, 'Bohdan', 'Juk', 'bohdan_j@email.com'),
       (3, 'Dmytro', 'Levytskyy', 'dmytro_l@email.com'),
       (4, 'Evgen', 'Vovchuk', 'evgen_v@email.com');

INSERT INTO application_user (user_id, username, password, role, person_id, registered_date)
VALUES (1, 'admin', 'admin', 'ADMIN', 1, current_timestamp),
       (2, 'puser', 'puser', 'PRIVILEGED_USER', 2, current_timestamp),
       (3, 'user', 'user', 'USER', 3, current_timestamp),
       (4, 'engenvovk', 'password', 'USER', 4, current_timestamp);

INSERT INTO location (location_id, longitude, latitude)
VALUES (1, '49.444452', '39.847446'),
       (2, '49.653616', '39.142565'),
       (3, '49.384018', '39.322884'),
       (4, '48.940955', '39.197207'),
       (5, '48.519264', '39.087923'),
       (6, '48.305275', '39.350204');

INSERT INTO media (media_id, original_media_url, processed_media_url, create_user, create_date)
VALUES (1, NULL, NULL, 1, current_timestamp),
       (2, NULL, NULL, 1, current_timestamp),
       (3, NULL, NULL, 2, current_timestamp),
       (4, NULL, NULL, 2, current_timestamp),
       (5, NULL, NULL, 3, current_timestamp),
       (6, NULL, NULL, 3, current_timestamp);

INSERT INTO ml_model(ml_model_id, model_name, model_path, is_custom, is_active, create_user, create_date)
VALUES (1, 'TANK_model', NULL, 1, 1, 1, current_timestamp),
       (2, 'APC_model', NULL, 1, 1, 1, current_timestamp),
       (3, 'SHIP_model', NULL, 1, 1, 1, current_timestamp),
       (4, 'AERO_VEHICLES_model', NULL, 1, 1, 1, current_timestamp);

INSERT INTO recognition_result (recognition_result_id, description, is_object_present, is_recognition_correct,
                                ml_model_id, media_id, location_id, create_user, create_date, update_user, update_date)
VALUES (1, '1 tank spotted at location ... description', 1, 1, 1, 1, 1, 3, current_timestamp, 2, current_timestamp),
       (2, '2 tank spotted at location ... description', 1, 1, 1, 2, 2, 3, current_timestamp, 2, current_timestamp),
       (3, '3 tank spotted at location ... description', 1, 1, 1, 3, 3, 3, current_timestamp, 2, current_timestamp),
       (4, '4 APC spotted at location ... description', 1, 1, 2, 4, 4, 3, current_timestamp, 2, current_timestamp),
       (5, '5 APC spotted at location ... description', 1, 1, 2, 5, 5, 4, current_timestamp, 2, current_timestamp),
       (6, '6 APC spotted at location ... description', 1, 1, 2, 6, 6, 4, current_timestamp, 2, current_timestamp);

INSERT INTO observed_object (observed_object_id, object_name)
VALUES (1, 'TANK'),
       (2, 'APC'),
       (3, 'WARSHIP'),
       (4, 'MILITARY_AIRCRAFT');

INSERT INTO recognition_result_observed_object (recognition_result_observed_object_id, recognition_result_id,
                                                observed_object_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 4, 2),
       (5, 5, 2),
       (6, 6, 2);

INSERT INTO ml_model_observed_object (ml_model_observed_object_id, ml_model_id, observed_object_id)
VALUES (1, 1, 1),
       (2, 1, 1),
       (3, 1, 1),
       (4, 2, 2),
       (5, 2, 2),
       (6, 2, 2);
