INSERT INTO cm_group
 (group_id, name, description)
VALUES
 (2147483647, 'Supers', 'Roles Administrators'),
 (nextval('group_id_seq'), 'Admins', 'Administrators'),
 (nextval('group_id_seq'), 'Coordinators', 'Test Coordinators'),
 (nextval('group_id_seq'), 'Actuaries', 'Test Actuaries'),
 (nextval('group_id_seq'), 'Users', 'Test Users');

INSERT INTO cm_user_group 
 (user_id, group_id) 
VALUES
 (2147483647, 1),
 (2147483647, 2),
 (2147483647, 3),
 (2147483647, 4);

ALTER TABLE cm_user 
 ADD COLUMN password VARCHAR NOT NULL
 DEFAULT 'e1X6Nk2/9ydYmDO74y89BK0aNQmQnQjK59X46mMRV9Q=';

INSERT INTO cm_user
 (user_id, user_name, description, primary_group_id, password)
VALUES
 (2147483647, 'superuser', 'Administrator', 2147483647),
 (nextval('user_id_seq'), 'admin', 'Administrator', 1),
 (nextval('user_id_seq'), 'coordinator', 'Test Coordinator', 2),
 (nextval('user_id_seq'), 'actuary', 'Test Actuary', 3),
 (nextval('user_id_seq'), 'user', 'Test User', 4);

CREATE VIEW glassfish_groups AS
SELECT g1.name AS group_name, u1.user_name AS login
  FROM cm_group g1
  LEFT JOIN cm_user u1 ON g1.group_id = u1.primary_group_id
 UNION
SELECT g2.name, u2.user_name
  FROM cm_user u2
 INNER JOIN cm_user_group ug ON u2.user_id = ug.user_id
 INNER JOIN cm_group g2 ON ug.group_id = g2.group_id;

CREATE VIEW glassfish_users AS 
SELECT user_name AS login, password AS password
  FROM cm_user g;

INSERT INTO pm_status
 (status_id, status, description)
VALUES
 (nextval('status_id_seq'), 'Создано', 'description_1'),
 (nextval('status_id_seq'), 'В работе', 'description_2'),
 (nextval('status_id_seq'), 'Решено', 'description_3');

INSERT INTO pm_incident
 (incident_id, incident_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('incident_id_seq'), 'incident_title_1', 'description_text_1', 1, 1),
 (nextval('incident_id_seq'), 'incident_title_2', 'description_text_2', 2, 2);

INSERT INTO pm_message
 (message_id, message_text)
VALUES
 (nextval('message_id_seq'), 'message_text_1'),
 (nextval('message_id_seq'), 'message_text_2'),
 (nextval('message_id_seq'), 'message_text_3'),
 (nextval('message_id_seq'), 'message_text_4'),
 (nextval('message_id_seq'), 'message_text_5'),
 (nextval('message_id_seq'), 'message_text_6'),
 (nextval('message_id_seq'), 'message_text_7'),
 (nextval('message_id_seq'), 'message_text_8'),
 (nextval('message_id_seq'), 'message_text_9');

INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 1);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 5);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (1, 9);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (2, 2);
INSERT INTO pm_inc_record (incident_id, message_id) VALUES (2, 6);

INSERT INTO pm_task
 (task_id, task_title, description_text, consumer_user_id, status_id)
VALUES
 (nextval('task_id_seq'), 'task_title_1', 'description_text_1', 1, 1),
 (nextval('task_id_seq'), 'task_title_2', 'description_text_2', 2, 2);
