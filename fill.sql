INSERT INTO cm_group
 (group_id, name, description)
VALUES
 (nextval('group_id_seq'), 'Admins', 'Administrators');
INSERT INTO cm_group
 (group_id, name, description)
VALUES
 (nextval('group_id_seq'), 'Coordicators', 'Test Coordicators');
INSERT INTO cm_group
 (group_id, name, description)
VALUES
 (nextval('group_id_seq'), 'Actuaries', 'Test Actuaries');
INSERT INTO cm_group
 (group_id, name, description)
VALUES
 (nextval('group_id_seq'), 'Users', 'Test Users');

INSERT INTO cm_user
 (user_id, user_name, description, primary_group_id)
VALUES
 (nextval('user_id_seq'), 'admin', 'Administrator', 1);
INSERT INTO cm_user
 (user_id, user_name, description, primary_group_id)
VALUES
 (nextval('user_id_seq'), 'coordicator', 'Test Coordicator', 2);
INSERT INTO cm_user
 (user_id, user_name, description, primary_group_id)
VALUES
 (nextval('user_id_seq'), 'actuary', 'Test Actuary', 3);
INSERT INTO cm_user
 (user_id, user_name, description, primary_group_id)
VALUES
 (nextval('user_id_seq'), 'user', 'Test User', 4);

ALTER TABLE cm_user 
 ADD COLUMN password VARCHAR NOT NULL
 DEFAULT 'e1X6Nk2/9ydYmDO74y89BK0aNQmQnQjK59X46mMRV9Q=';

CREATE VIEW sm_groups AS 
SELECT g.name, u.user_name
 FROM cm_group g
 LEFT JOIN cm_user u ON g.group_id = u.primary_group_id; 

INSERT INTO pm_status
 (status_id, status, description)
VALUES
 (nextval('status_id_seq'), 'Создано', 'description_1');
INSERT INTO pm_status
 (status_id, status, description)
VALUES
 (nextval('status_id_seq'), 'В работе', 'description_2');
INSERT INTO pm_status
 (status_id, status, description)
VALUES
 (nextval('status_id_seq'), 'Решено', 'description_3');

INSERT INTO pm_incident
 (incident_id, incident_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('incident_id_seq'), 'incident_title_1', 'description_text_1', 1, 1);
INSERT INTO pm_incident 
 (incident_id, incident_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('incident_id_seq'), 'incident_title_2', 'description_text_2', 2, 2);

INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_1');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_2');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_3');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_4');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_5');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_6');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_7');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_8');
INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_9');

INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 1);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 5);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 9);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (2, 2);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (2, 6);

INSERT INTO pm_task
 (task_id, task_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('task_id_seq'), 'task_title_1', 'description_text_1', 1, 1);
INSERT INTO pm_task 
 (task_id, task_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('task_id_seq'), 'task_title_2', 'description_text_2', 2, 2);