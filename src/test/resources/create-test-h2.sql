--
-- create-test-h2.sql
-- This file was last modified at 2019.01.26 23:59 by Victor N. Skurikhin.
-- $Id$
-- This is free and unencumbered software released into the public domain.
-- For more information, please refer to <http://unlicense.org>
--
--------------------------------------------------------------------------------
--
DROP TABLE IF EXISTS cm_cunit;
DROP TABLE IF EXISTS cm_user;
DROP TABLE IF EXISTS cm_group;
DROP TABLE IF EXISTS cm_admin;
DROP TABLE IF EXISTS cm_ctype;
DROP TABLE IF EXISTS pm_message;
DROP TABLE IF EXISTS pm_status;
DROP TABLE IF EXISTS pm_task;
DROP TABLE IF EXISTS pm_incident;
--
ALTER SEQUENCE ctype_id_seq    RESTART WITH 1;
ALTER SEQUENCE cunit_id_seq    RESTART WITH 1;
ALTER SEQUENCE group_id_seq    RESTART WITH 1;
ALTER SEQUENCE incident_id_seq RESTART WITH 1;
ALTER SEQUENCE message_id_seq  RESTART WITH 1;
ALTER SEQUENCE status_id_seq   RESTART WITH 1;
ALTER SEQUENCE task_id_seq     RESTART WITH 1;
ALTER SEQUENCE user_id_seq     RESTART WITH 1;
ALTER SEQUENCE incident_id_seq RESTART WITH 1;
--
--DROP ALL OBJECTS;
--
--------------------------------------------------------------------------------
-- Configuration Management ----------------------------------------------------
-- Groups
CREATE TABLE IF NOT EXISTS cm_group (
  --                BIGINT       NOT NULL DEFAULT nextval('group_id_seq')
  group_id          BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(127) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  UNIQUE (name),
  PRIMARY KEY (group_id)
);
--
-- Users
CREATE TABLE IF NOT EXISTS cm_user (
  user_id           BIGINT       NOT NULL AUTO_INCREMENT,
  user_name         VARCHAR(127) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  primary_group_id  BIGINT 	 NOT NULL REFERENCES cm_group (group_id),
  UNIQUE (user_name),
  PRIMARY KEY (user_id)
);
--
-- User group map
CREATE TABLE IF NOT EXISTS cm_user_group (
  user_id           BIGINT NOT NULL REFERENCES cm_user (user_id),
  group_id          BIGINT NOT NULL REFERENCES cm_group (group_id)
);
--
-- Administrators
CREATE TABLE IF NOT EXISTS cm_admin (
  admin_user_id     BIGINT NOT NULL REFERENCES cm_user (user_id),
  admin_group_id    BIGINT NOT NULL REFERENCES cm_group (group_id)
);
--
-- Configuration types
CREATE TABLE IF NOT EXISTS cm_ctype (
  ctype_id          BIGINT       NOT NULL AUTO_INCREMENT,
  ctype_name        VARCHAR(127) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  UNIQUE (ctype_name),
  PRIMARY KEY (ctype_id)
);
--
-- Configuration units
CREATE TABLE IF NOT EXISTS cm_cunit (
  cunit_id          BIGINT       NOT NULL AUTO_INCREMENT,
  name              VARCHAR(127) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  environ           ENUM('prod', 'test', 'dev'),
  admin_user_id     BIGINT NOT NULL REFERENCES cm_admin (admin_user_id),
  owner_user_id     BIGINT NOT NULL REFERENCES cm_user  (user_id),
  group_id          BIGINT          REFERENCES cm_group (group_id),
  type_id           BIGINT          REFERENCES cm_ctype (ctype_id),
  UNIQUE (name),
  PRIMARY KEY (cunit_id)
);
--
-- Process Management ----------------------------------------------------------
-- Messages
CREATE TABLE IF NOT EXISTS pm_message (
  message_id        BIGINT       NOT NULL AUTO_INCREMENT,
  message_text      VARCHAR(255) NOT NULL,
  PRIMARY KEY (message_id)
);
--
-- Status
CREATE TABLE IF NOT EXISTS pm_status (
  status_id         BIGINT       NOT NULL AUTO_INCREMENT,
  status            VARCHAR(127) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  UNIQUE (status),
  PRIMARY KEY (status_id)
);
--
-- Tasks
CREATE TABLE IF NOT EXISTS pm_task (
  task_id           BIGINT       NOT NULL AUTO_INCREMENT,
  task_title        VARCHAR(127) NOT NULL,
  description_text  VARCHAR(255) NOT NULL,
  consumer_user_id  BIGINT REFERENCES cm_user (user_id),
  status_id         BIGINT REFERENCES cm_status (status_id),
  PRIMARY KEY (task_id)
);
--
-- Task actors
CREATE TABLE IF NOT EXISTS pm_task_actor (
  task_id           BIGINT REFERENCES pm_task (task_id),
  user_id           BIGINT REFERENCES cm_user (user_id)
);
--
-- Task actuaries
CREATE TABLE IF NOT EXISTS pm_task_actuary (
  task_id           BIGINT REFERENCES pm_task (task_id),
  user_id           BIGINT REFERENCES cm_user (user_id)
);
--
-- Task records
CREATE TABLE IF NOT EXISTS pm_task_record (
  task_id           BIGINT REFERENCES pm_task (task_id),
  message_id        BIGINT REFERENCES pm_message (message_id)
);
--
-- Incidents
CREATE TABLE IF NOT EXISTS pm_incident (
  incident_id       BIGINT       NOT NULL AUTO_INCREMENT,
  incident_title    VARCHAR(127) NOT NULL,
  description_text  VARCHAR(254) NOT NULL,
  consumer_user_id  BIGINT REFERENCES cm_user (user_id),
  status_id         BIGINT REFERENCES cm_status (status_id),
  PRIMARY KEY (incident_id)
);
--
-- Incident records
CREATE TABLE IF NOT EXISTS pm_inc_record (
  incident_id       BIGINT REFERENCES pm_incident (incident_id),
  message_id        BIGINT REFERENCES pm_message (message_id)
);
--
--------------------------------------------------------------------------------
