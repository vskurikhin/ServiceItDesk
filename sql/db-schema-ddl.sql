ALTER TABLE cm_cunit DROP constraint IF EXISTS FKsfqiqh04bk5c8e9q8svyyj4ph;
ALTER TABLE cm_cunit DROP constraint IF EXISTS FK5qgwsmdfqrmaemw5h8ci3m3j9;
ALTER TABLE cm_cunit DROP constraint IF EXISTS FK5k1lcqvea07098m9ga461gfqi;
ALTER TABLE cm_cunit DROP constraint IF EXISTS FKptrc6h66gew6oi6wbc0suh1gt;
ALTER TABLE cm_user DROP constraint IF EXISTS FKp4yk86u76bcytks3jkqpg4av9;
ALTER TABLE cm_user_group DROP constraint IF EXISTS FKholtyudf3batwi2xgdlwh8dr2;
ALTER TABLE cm_user_group DROP constraint IF EXISTS FK7cq4oi8q8wfpaa3c9bwjo712;
ALTER TABLE pm_inc_record DROP constraint IF EXISTS FK38dsfy5wmydi60f4l0fgwhimi;
ALTER TABLE pm_inc_record DROP constraint IF EXISTS FKojtlxu1tcpw7waxwhdm1iu208;
ALTER TABLE pm_incident DROP constraint IF EXISTS FKarhc1jmkq29t4qvldc9o3id0n;
ALTER TABLE pm_incident DROP constraint IF EXISTS FK3uudlbppj7c97w8vuvaah5nin;
ALTER TABLE pm_task DROP constraint IF EXISTS FKew4b31exqqhq9hfstsvwrs95h;
ALTER TABLE pm_task DROP constraint IF EXISTS FKar10papjdviymrg7nmuvl4xue;
ALTER TABLE pm_task_record DROP constraint IF EXISTS FKivb6lpxyc2g5kxk8iidvfxsb8;
ALTER TABLE pm_task_record DROP constraint IF EXISTS FK3xpfyd81ifeyd05r1wwhp7s5h;

DROP TABLE IF EXISTS cm_ctype cascade;
DROP TABLE IF EXISTS cm_cunit cascade;
DROP TABLE IF EXISTS cm_group cascade;
DROP TABLE IF EXISTS cm_user cascade;
DROP TABLE IF EXISTS cm_user_group cascade;
DROP TABLE IF EXISTS pm_inc_record cascade;
DROP TABLE IF EXISTS pm_incident cascade;
DROP TABLE IF EXISTS pm_message cascade;
DROP TABLE IF EXISTS pm_status cascade;
DROP TABLE IF EXISTS pm_task cascade;
DROP TABLE IF EXISTS pm_task_record cascade;

DROP SEQUENCE IF EXISTS ctype_id_seq;
DROP SEQUENCE IF EXISTS cunit_id_seq;
DROP SEQUENCE IF EXISTS group_id_seq;
DROP SEQUENCE IF EXISTS incident_id_seq;
DROP SEQUENCE IF EXISTS message_id_seq;
DROP SEQUENCE IF EXISTS status_id_seq;
DROP SEQUENCE IF EXISTS task_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;

CREATE SEQUENCE ctype_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE cunit_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE group_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE incident_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE message_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE status_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE task_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE user_id_seq START 1 INCREMENT 1;

CREATE TABLE cm_ctype (
  ctype_id 			INT8 NOT NULL,
  ctype_name 		VARCHAR(255) NOT NULL,
  description 		VARCHAR(255),
  PRIMARY KEY (ctype_id)
);

CREATE TABLE cm_cunit (
  cunit_id 			INT8 NOT NULL,
  cunit_name 		VARCHAR(255) NOT NULL,
  description 		VARCHAR(255),
  admin_user_id 	INT8 NOT NULL,
  owner_user_id 	INT8 NOT NULL,
  group_id 			INT8 NOT NULL,
  type_id 			INT8 NOT NULL,
  PRIMARY KEY (cunit_id)
);

CREATE TABLE cm_group (
  group_id 			INT8 NOT NULL,
  name 				VARCHAR(255) NOT NULL,
  description 		VARCHAR(255),
  primary key (group_id)
);

CREATE TABLE cm_user (
  user_id 			INT8 NOT NULL,
  user_name 		VARCHAR(255) NOT NULL,
  description 		VARCHAR(255),
  primary_group_id 	INT8 NOT NULL,
  primary key (user_id)
);

CREATE TABLE cm_user_group (
  group_id 			INT8 NOT NULL,
  user_id 			INT8 NOT NULL
);

CREATE TABLE pm_message (
  message_id 		INT8 NOT NULL,
  message_text 		VARCHAR(255) NOT NULL,
  primary key (message_id)
);

CREATE TABLE pm_incident (
  incident_id 		INT8 NOT NULL,
  incident_title 	VARCHAR(255) NOT NULL,
  description_text 	VARCHAR(255) NOT NULL,
  consumer_user_id 	INT8 NOT NULL,
  status_id 		INT8 NOT NULL,
  primary key (incident_id)
);

CREATE TABLE pm_inc_record (
  incident_id 		INT8 NOT NULL,
  message_id 		INT8 NOT NULL
);

create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id));

create table pm_task_record (incident_id int8 not null, message_id int8 not null);

create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id));

alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name);
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name);
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name);
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name);
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title);
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status);
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title);
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user;
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group;
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user;
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype;
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group;
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user;
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group;
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message;
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident;
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user;
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status;
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user;
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status;
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message;
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task;
