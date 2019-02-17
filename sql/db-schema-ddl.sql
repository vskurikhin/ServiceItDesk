alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FKhb9hg2pvfeuht83ctdww92ehx
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FK590s8dm48javsebsqk93l49kf
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_status cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_task cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255), incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_task (task_id int8 not null, description_text varchar(255), task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_status add constraint UK_h0pe6v1sdpor1hip8wh2pbvho unique (status)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FKhb9hg2pvfeuht83ctdww92ehx foreign key (status_id) references cm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FK590s8dm48javsebsqk93l49kf foreign key (status_id) references cm_status
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FKhb9hg2pvfeuht83ctdww92ehx
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FK590s8dm48javsebsqk93l49kf
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_status cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_task cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255), incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_task (task_id int8 not null, description_text varchar(255), task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_status add constraint UK_h0pe6v1sdpor1hip8wh2pbvho unique (status)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FKhb9hg2pvfeuht83ctdww92ehx foreign key (status_id) references cm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FK590s8dm48javsebsqk93l49kf foreign key (status_id) references cm_status
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255), incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255), task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
alter table cm_cunit drop constraint if exists FKsfqiqh04bk5c8e9q8svyyj4ph
alter table cm_cunit drop constraint if exists FK5qgwsmdfqrmaemw5h8ci3m3j9
alter table cm_cunit drop constraint if exists FK5k1lcqvea07098m9ga461gfqi
alter table cm_cunit drop constraint if exists FKptrc6h66gew6oi6wbc0suh1gt
alter table cm_user drop constraint if exists FKp4yk86u76bcytks3jkqpg4av9
alter table cm_user_group drop constraint if exists FKholtyudf3batwi2xgdlwh8dr2
alter table cm_user_group drop constraint if exists FK7cq4oi8q8wfpaa3c9bwjo712
alter table pm_inc_record drop constraint if exists FK38dsfy5wmydi60f4l0fgwhimi
alter table pm_inc_record drop constraint if exists FKojtlxu1tcpw7waxwhdm1iu208
alter table pm_incident drop constraint if exists FKarhc1jmkq29t4qvldc9o3id0n
alter table pm_incident drop constraint if exists FK3uudlbppj7c97w8vuvaah5nin
alter table pm_task drop constraint if exists FKew4b31exqqhq9hfstsvwrs95h
alter table pm_task drop constraint if exists FKar10papjdviymrg7nmuvl4xue
alter table pm_task_record drop constraint if exists FKivb6lpxyc2g5kxk8iidvfxsb8
alter table pm_task_record drop constraint if exists FK3xpfyd81ifeyd05r1wwhp7s5h
drop table if exists cm_ctype cascade
drop table if exists cm_cunit cascade
drop table if exists cm_group cascade
drop table if exists cm_user cascade
drop table if exists cm_user_group cascade
drop table if exists pm_inc_record cascade
drop table if exists pm_incident cascade
drop table if exists pm_message cascade
drop table if exists pm_status cascade
drop table if exists pm_task cascade
drop table if exists pm_task_record cascade
drop sequence if exists ctype_id_seq
drop sequence if exists cunit_id_seq
drop sequence if exists group_id_seq
drop sequence if exists incident_id_seq
drop sequence if exists message_id_seq
drop sequence if exists status_id_seq
drop sequence if exists task_id_seq
drop sequence if exists user_id_seq
create sequence ctype_id_seq start 1 increment 1
create sequence cunit_id_seq start 1 increment 1
create sequence group_id_seq start 1 increment 1
create sequence incident_id_seq start 1 increment 1
create sequence message_id_seq start 1 increment 1
create sequence status_id_seq start 1 increment 1
create sequence task_id_seq start 1 increment 1
create sequence user_id_seq start 1 increment 1
create table cm_ctype (ctype_id int8 not null, description varchar(255), ctype_name varchar(255) not null, primary key (ctype_id))
create table cm_cunit (cunit_id int8 not null, description varchar(255), cunit_name varchar(255) not null, admin_user_id int8 not null, group_id int8 not null, owner_user_id int8 not null, type_id int8 not null, primary key (cunit_id))
create table cm_group (group_id int8 not null, description varchar(255), name varchar(255) not null, primary key (group_id))
create table cm_user (user_id int8 not null, description varchar(255), user_name varchar(255) not null, primary_group_id int8 not null, primary key (user_id))
create table cm_user_group (group_id int8 not null, user_id int8 not null)
create table pm_inc_record (incident_id int8 not null, message_id int8 not null)
create table pm_incident (incident_id int8 not null, description_text varchar(255) not null, incident_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (incident_id))
create table pm_message (message_id int8 not null, message_text varchar(255) not null, primary key (message_id))
create table pm_status (status_id int8 not null, description varchar(255), status varchar(255) not null, primary key (status_id))
create table pm_task (task_id int8 not null, description_text varchar(255) not null, task_title varchar(255) not null, consumer_user_id int8 not null, status_id int8 not null, primary key (task_id))
create table pm_task_record (incident_id int8 not null, message_id int8 not null)
alter table cm_ctype add constraint UK_dqtvdrqiusyy0iove0wlg6wl9 unique (ctype_name)
alter table cm_cunit add constraint UK_n672vnr5bxf6w3sc2l38b0ajx unique (cunit_name)
alter table cm_group add constraint UK_sje8ronew46glkfvntuq9xf3k unique (name)
alter table cm_user add constraint UK_ew9d9i73jwutvfjoi1uwe9wfr unique (user_name)
alter table pm_incident add constraint UK_6qh0n5r41sjr45nmuib06aaq unique (incident_title)
alter table pm_status add constraint UK_o9cvgcg8ua7jy0lih7ctkcjn7 unique (status)
alter table pm_task add constraint UK_3woyuyd1x9eioxvpfboalw8ij unique (task_title)
alter table cm_cunit add constraint FKsfqiqh04bk5c8e9q8svyyj4ph foreign key (admin_user_id) references cm_user
alter table cm_cunit add constraint FK5qgwsmdfqrmaemw5h8ci3m3j9 foreign key (group_id) references cm_group
alter table cm_cunit add constraint FK5k1lcqvea07098m9ga461gfqi foreign key (owner_user_id) references cm_user
alter table cm_cunit add constraint FKptrc6h66gew6oi6wbc0suh1gt foreign key (type_id) references cm_ctype
alter table cm_user add constraint FKp4yk86u76bcytks3jkqpg4av9 foreign key (primary_group_id) references cm_group
alter table cm_user_group add constraint FKholtyudf3batwi2xgdlwh8dr2 foreign key (user_id) references cm_user
alter table cm_user_group add constraint FK7cq4oi8q8wfpaa3c9bwjo712 foreign key (group_id) references cm_group
alter table pm_inc_record add constraint FK38dsfy5wmydi60f4l0fgwhimi foreign key (message_id) references pm_message
alter table pm_inc_record add constraint FKojtlxu1tcpw7waxwhdm1iu208 foreign key (incident_id) references pm_incident
alter table pm_incident add constraint FKarhc1jmkq29t4qvldc9o3id0n foreign key (consumer_user_id) references cm_user
alter table pm_incident add constraint FK3uudlbppj7c97w8vuvaah5nin foreign key (status_id) references pm_status
alter table pm_task add constraint FKew4b31exqqhq9hfstsvwrs95h foreign key (consumer_user_id) references cm_user
alter table pm_task add constraint FKar10papjdviymrg7nmuvl4xue foreign key (status_id) references pm_status
alter table pm_task_record add constraint FKivb6lpxyc2g5kxk8iidvfxsb8 foreign key (message_id) references pm_message
alter table pm_task_record add constraint FK3xpfyd81ifeyd05r1wwhp7s5h foreign key (incident_id) references pm_task
