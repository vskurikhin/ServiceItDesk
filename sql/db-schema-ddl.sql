--
-- db-schema-ddl.sql
-- This file was last modified at 2019.01.26 23:59 by Victor N. Skurikhin.
-- $Id$
-- This is free and unencumbered software released into the public domain.
-- For more information, please refer to <http://unlicense.org>
--
--------------------------------------------------------------------------------
--
ALTER TABLE cm_cunit      DROP CONSTRAINT IF EXISTS FK5qgwsmdfqrmaemw5h8ci3m3j9;
ALTER TABLE cm_cunit      DROP CONSTRAINT IF EXISTS FK5k1lcqvea07098m9ga461gfqi;
ALTER TABLE cm_cunit      DROP CONSTRAINT IF EXISTS FKptrc6h66gew6oi6wbc0suh1gt;
ALTER TABLE cm_user       DROP CONSTRAINT IF EXISTS FKp4yk86u76bcytks3jkqpg4av9;
ALTER TABLE cm_user_group DROP CONSTRAINT IF EXISTS FKholtyudf3batwi2xgdlwh8dr2;
ALTER TABLE cm_user_group DROP CONSTRAINT IF EXISTS FK7cq4oi8q8wfpaa3c9bwjo712;
ALTER TABLE pm_incident   DROP CONSTRAINT IF EXISTS FKarhc1jmkq29t4qvldc9o3id0n;
ALTER TABLE pm_incident   DROP CONSTRAINT IF EXISTS FKhb9hg2pvfeuht83ctdww92ehx;
ALTER TABLE pm_task       DROP CONSTRAINT IF EXISTS FKew4b31exqqhq9hfstsvwrs95h;
ALTER TABLE pm_task       DROP CONSTRAINT IF EXISTS FK590s8dm48javsebsqk93l49kf;
--
DROP TABLE IF EXISTS cm_ctype      CASCADE;
DROP TABLE IF EXISTS cm_cunit      CASCADE;
DROP TABLE IF EXISTS cm_group      CASCADE;
DROP TABLE IF EXISTS cm_status     CASCADE;
DROP TABLE IF EXISTS cm_user       CASCADE;
DROP TABLE IF EXISTS cm_user_group CASCADE;
DROP TABLE IF EXISTS pm_incident   CASCADE;
DROP TABLE IF EXISTS pm_message    CASCADE;
DROP TABLE IF EXISTS pm_task       CASCADE;
--
DROP SEQUENCE IF EXISTS ctype_id_seq;
DROP SEQUENCE IF EXISTS cunit_id_seq;
DROP SEQUENCE IF EXISTS group_id_seq;
DROP SEQUENCE IF EXISTS incident_id_seq;
DROP SEQUENCE IF EXISTS message_id_seq;
DROP SEQUENCE IF EXISTS status_id_seq;
DROP SEQUENCE IF EXISTS task_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
--
CREATE SEQUENCE ctype_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE cunit_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE group_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE incident_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE message_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE status_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE task_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE user_id_seq START 1 INCREMENT 1;
--
CREATE TABLE cm_ctype (
  group_id          BIGINT       NOT NULL AUTO_INCREMENT,
  ctype_id          INT8         NOT NULL,
  description       VARCHAR(255),
  ctype_name        VARCHAR(255) NOT NULL,
  PRIMARY KEY (ctype_id)
);
--
CREATE TABLE cm_cunit (
  cunit_id          INT8         NOT NULL,
  description       VARCHAR(255),
  cunit_name        VARCHAR(255) NOT NULL,
  group_id          INT8         NOT NULL,
  owner_user_id     INT8         NOT NULL,
  type_id           INT8         NOT NULL,
  PRIMARY KEY (cunit_id)
);
--
CREATE TABLE cm_group (
  group_id          INT8         NOT NULL,
  description       VARCHAR(255),
  name              VARCHAR(255) NOT NULL,
  PRIMARY KEY (group_id)
);
--
CREATE TABLE cm_status (
  status_id         INT8         NOT NULL,
  description       VARCHAR(255),
  status            VARCHAR(255) NOT NULL,
  PRIMARY KEY (status_id)
);
--
CREATE TABLE cm_user (
  user_id           INT8         NOT NULL,
  description       VARCHAR(255),
  user_name         VARCHAR(255) NOT NULL,
  primary_group_id  INT8         NOT NULL,
  PRIMARY KEY (user_id)
);
--
CREATE TABLE cm_user_group (
  group_id INT8 NOT NULL,
  user_id  INT8 NOT NULL
);
--
CREATE TABLE pm_incident (
  incident_id       INT8         NOT NULL,
  description_text  VARCHAR(255),
  incident_title    VARCHAR(255) NOT NULL,
  consumer_user_id  INT8         NOT NULL,
  status_id         INT8         NOT NULL,
  PRIMARY KEY (incident_id)
);
--
CREATE TABLE pm_message (
  message_id        INT8         NOT NULL,
  message_text      VARCHAR(255) NOT NULL,
  PRIMARY KEY (message_id)
);
--
CREATE TABLE pm_task (
  task_id           INT8         NOT NULL,
  description_text  VARCHAR(255),
  task_title        VARCHAR(255) NOT NULL,
  consumer_user_id  INT8         NOT NULL,
  status_id         INT8         NOT NULL,
  PRIMARY KEY (task_id)
);
--
ALTER TABLE cm_ctype    ADD CONSTRAINT UK_dqtvdrqiusyy0iove0wlg6wl9
UNIQUE (ctype_name);
ALTER TABLE cm_cunit    ADD CONSTRAINT UK_n672vnr5bxf6w3sc2l38b0ajx
UNIQUE (cunit_name);
ALTER TABLE cm_group    ADD CONSTRAINT UK_sje8ronew46glkfvntuq9xf3k
UNIQUE (name);
ALTER TABLE cm_status   ADD CONSTRAINT UK_h0pe6v1sdpor1hip8wh2pbvho
UNIQUE (status);
ALTER TABLE cm_user     ADD CONSTRAINT UK_ew9d9i73jwutvfjoi1uwe9wfr
UNIQUE (user_name);
ALTER TABLE pm_incident ADD CONSTRAINT UK_6qh0n5r41sjr45nmuib06aaq
UNIQUE (incident_title);
ALTER TABLE pm_task     ADD CONSTRAINT UK_3woyuyd1x9eioxvpfboalw8ij
UNIQUE (task_title);
--
ALTER TABLE cm_cunit      ADD CONSTRAINT FK5qgwsmdfqrmaemw5h8ci3m3j9
FOREIGN KEY (group_id)        REFERENCES cm_group;
ALTER TABLE cm_cunit      ADD CONSTRAINT FK5k1lcqvea07098m9ga461gfqi
FOREIGN KEY (owner_user_id)   REFERENCES cm_user;
ALTER TABLE cm_cunit      ADD CONSTRAINT FKptrc6h66gew6oi6wbc0suh1gt
FOREIGN KEY (type_id)         REFERENCES cm_ctype;
ALTER TABLE cm_user       ADD CONSTRAINT FKp4yk86u76bcytks3jkqpg4av9
FOREIGN KEY (primary_group_id) REFERENCES cm_group;
ALTER TABLE cm_user_group ADD CONSTRAINT FKholtyudf3batwi2xgdlwh8dr2
FOREIGN KEY (user_id)         REFERENCES cm_user;
ALTER TABLE cm_user_group ADD CONSTRAINT FK7cq4oi8q8wfpaa3c9bwjo712
FOREIGN KEY (group_id)        REFERENCES cm_group;
ALTER TABLE pm_incident   ADD CONSTRAINT FKarhc1jmkq29t4qvldc9o3id0n
FOREIGN KEY (consumer_user_id) REFERENCES cm_user;
ALTER TABLE pm_incident   ADD CONSTRAINT FKhb9hg2pvfeuht83ctdww92ehx
FOREIGN KEY (status_id)       REFERENCES cm_status;
ALTER TABLE pm_task       ADD CONSTRAINT FKew4b31exqqhq9hfstsvwrs95h
FOREIGN KEY (consumer_user_id) REFERENCES cm_user;
ALTER TABLE pm_task       ADD CONSTRAINT FK590s8dm48javsebsqk93l49kf
FOREIGN KEY (status_id)       REFERENCES cm_status;
--
--------------------------------------------------------------------------------
