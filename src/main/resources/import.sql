-- users
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('admin@wedealauction.com', 'passw0rd', TRUE, 'Default', 'Admin', 'Wedeal Auction', 'Default Amin', 'Administrator', 'Kolkata', 'West Bengal');


-- attachments

-- items


-- running auction

-- old auction

-- future auction

-- auction association with user

-- Security tables
--alter table group_members drop constraint fk_group_members_user_id;
--alter table group_members drop constraint fk_group_members_group_id;
--alter table group_authorities drop constraint fk_group_authorities_group_id;
--alter table resetpasswordreq drop constraint fk_resetpasswordreq_user_id;

drop table if exists groups;
drop table if exists group_members;
drop table if exists group_authorities;
drop table if exists resetpasswordreq; 

create table groups (id bigint not null auto_increment,	group_name varchar(255) not null, primary key (id));

create table group_members (user_id bigint not null, group_id bigint not null, unique key (user_id, group_id));
alter table group_members add constraint fk_group_members_user_id foreign key (user_id)	references users (user_id);
alter table group_members add constraint fk_group_members_group_id foreign key (group_id) references groups (id);	
	
create table group_authorities (group_id bigint not null, authority varchar(255) not null, unique key (group_id, authority));	
alter table group_authorities add constraint fk_group_authorities_group_id foreign key (group_id) references groups (id);
	
create table resetpasswordreq (code varchar(255) not null, user_id bigint not null, reqtime datetime not null, primary key (code));
alter table resetpasswordreq add constraint fk_resetpasswordreq_user_id foreign key (user_id) references users (user_id);

insert into groups (group_name) values ('BIDDER');
insert into groups (group_name) values ('ADMIN');
insert into groups (group_name) values ('OBSERVER');

insert into group_authorities values (1, 'ROLE_USER');
insert into group_authorities values (1, 'ROLE_BIDDER');

insert into group_authorities values (2, 'ROLE_USER');
insert into group_authorities values (2, 'ROLE_ADMIN');	

insert into group_authorities values (3, 'ROLE_USER');
insert into group_authorities values (3, 'ROLE_OBSERVER');

insert into group_members select user_id, 2 from users where username = 'admin@wedealauction.com';

