-- users
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, contactnumber, address, city, state, pincode) values ('cdiptangshu@gmail.com', 'passw0rd', TRUE, 'Diptangshu', 'Chakrabarty', 'IBM India Private Limited', 'Diptangshu Chakrabarty', 'Technical Lead', '919830905487', 'Behala', 'Kolkata', 'West Bengal', '700061');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, contactnumber, address, city, state, pincode) values ('sudipta.gms@gmail.com', 'passw0rd', TRUE, 'Sudipta', 'Mukherjee', 'mjunction services limited', 'Sudipta Mukherjee', '', '919163348124', 'Address line', 'Kolkata', 'West Bengal', '700001');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('admin1@regalauction.in', 'passw0rd', TRUE, 'Admin', '1', 'enilaam', 'Test Admin 1', 'Administrator', 'Kolkata', 'West Bengal');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('admin2@regalauction.in', 'passw0rd', TRUE, 'Admin', '2', 'enilaam', 'Test Admin 2', 'Administrator', 'Kolkata', 'West Bengal');

insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, contactnumber, address, city, state, pincode) values ('zubin_deep@yahoo.com', 'passw0rd', TRUE, 'Diptangshu', 'Chakrabarty', 'IBM India Private Limited', 'Diptangshu Chakrabarty', 'Technical Lead', '919830905487', 'Behala', 'Kolkata', 'West Bengal', '700061');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('bidder1@regalauction.in', 'passw0rd', TRUE, 'Bidder', '1', 'enilaam', 'Test Bidder 1', 'Bidder', 'Kolkata', 'West Bengal');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('bidder2@regalauction.in', 'passw0rd', TRUE, 'Bidder', '2', 'enilaam', 'Test Bidder 2', 'Bidder', 'Kolkata', 'West Bengal');
insert into users(username, password, enabled, firstname, lastname, organization, contactname, contactdesgn, city, state) values ('bidder3@regalauction.in', 'passw0rd', TRUE, 'Bidder', '3', 'enilaam', 'Test Bidder 3', 'Bidder', 'Kolkata', 'West Bengal');

insert into users(username, password, enabled, firstname, lastname) values ('observer1@regalauction.in', 'passw0rd', TRUE, 'Observer', '1');


-- attachments
insert into attachment(attachmenttype, code, name) values ('IMAGE', '1.png', 'car.png');
insert into attachment(attachmenttype, code, name) values ('IMAGE', '2.png', 'guitar.png');
insert into attachment(attachmenttype, code, name) values ('IMAGE', '3.png', 'machine.png');

insert into attachment(attachmenttype, code, name) values ('DOCUMENT', '1.txt', 'test.txt');

-- items
insert into item(code, name, thumbimg_id) values ('DUMMY-ITEM-1', 'Vintage Car', 1);
insert into item(code, name, thumbimg_id) values ('DUMMY-ITEM-2', 'Acoustic Guitar', 2);
insert into item(code, name, thumbimg_id) values ('DUMMY-ITEM-3', 'Machine', 3);


-- running auction
insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-1', 'ENGLISH', 'Well-maintained Vintage Car (1914)', item_id, 1, subdate(current_timestamp, interval 1 month), adddate(current_timestamp, interval 1 month), 100.00, 10000.00, 10.00, 1 from item where code = 'DUMMY-ITEM-1';
insert into auction_document select auction_id, (select attachment_id from attachment where code = '1.txt') from auction where auctioncode = 'DUMMY-AUCTION-1';

insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-2', 'DUTCH', 'Filtering Machine', item_id, 1, subdate(current_timestamp, interval 1 month), adddate(current_timestamp, interval 1 month), 10000.00, 100.00, 10.00, 2 from item where code = 'DUMMY-ITEM-3';

-- old auction
insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-3', 'ENGLISH', 'Vintage Car (good condition)', item_id, 1, subdate(current_timestamp, interval 1 month), subdate(current_timestamp, interval 1 day), 20.00, 870.00, 5.50, 3 from item where code = 'DUMMY-ITEM-1';

-- future auction
insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-4', 'ENGLISH', 'Dreadnought Acoustic Guitar (less than 2 years old)', item_id, 1, adddate(current_timestamp, interval 1 day), adddate(current_timestamp, interval 1 month), 10000.00, 100000.00, 100.00, 5 from item where code = 'DUMMY-ITEM-2';
insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-5', 'ENGLISH', 'German Vintage Luxary Car', item_id, 1, adddate(current_timestamp, interval 2 day), adddate(current_timestamp, interval 6 day), 50000.00, 1000000.00, 1000.00, 10 from item where code = 'DUMMY-ITEM-1';
insert into auction(auctioncode, auctiontype, name, item_id, quantity, startdate, enddate, baseprice, reserveprice, deltaprice, timeextension) select 'DUMMY-AUCTION-6', 'ENGLISH', 'Coffee Vending Machine', item_id, 1, adddate(current_timestamp, interval 1 month), adddate(current_timestamp, interval 2 month), 10000.00, 100000.00, 100.00, 5 from item where code = 'DUMMY-ITEM-3';

-- auction association with user
insert into auction_user select auction_id, (select user_id from users where username = 'zubin_deep@yahoo.com') from auction where auctioncode = 'DUMMY-AUCTION-1';
insert into auction_user select auction_id, (select user_id from users where username = 'bidder1@regalauction.in') from auction where auctioncode = 'DUMMY-AUCTION-1';
insert into auction_user select auction_id, (select user_id from users where username = 'cdiptangshu@gmail.com') from auction where auctioncode = 'DUMMY-AUCTION-1';

insert into auction_user select auction_id, (select user_id from users where username = 'cdiptangshu@gmail.com') from auction where auctioncode = 'DUMMY-AUCTION-2';
insert into auction_user select auction_id, (select user_id from users where username = 'zubin_deep@yahoo.com') from auction where auctioncode = 'DUMMY-AUCTION-2';
insert into auction_user select auction_id, (select user_id from users where username = 'bidder1@regalauction.in') from auction where auctioncode = 'DUMMY-AUCTION-2';
insert into auction_user select auction_id, (select user_id from users where username = 'bidder2@regalauction.in') from auction where auctioncode = 'DUMMY-AUCTION-2';
insert into auction_user select auction_id, (select user_id from users where username = 'bidder3@regalauction.in') from auction where auctioncode = 'DUMMY-AUCTION-2';

insert into auction_user select auction_id, (select user_id from users where username = 'zubin_deep@yahoo.com') from auction where auctioncode = 'DUMMY-AUCTION-3';

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

insert into group_members select user_id, 2 from users where username = 'cdiptangshu@gmail.com';
insert into group_members select user_id, 2 from users where username = 'sudipta.gms@gmail.com';
insert into group_members select user_id, 2 from users where username = 'admin1@regalauction.in';
insert into group_members select user_id, 2 from users where username = 'admin2@regalauction.in';

insert into group_members select user_id, 1 from users where username = 'zubin_deep@yahoo.com';
insert into group_members select user_id, 1 from users where username = 'bidder1@regalauction.in';
insert into group_members select user_id, 1 from users where username = 'bidder2@regalauction.in';
insert into group_members select user_id, 1 from users where username = 'bidder3@regalauction.in';

insert into group_members select user_id, 3 from users where username = 'observer1@regalauction.in';

