insert into users(user_name,password,enabled,name,last_name,email) values('mevc','$2a$10$2I5/qH5yHCxNr13xJenzW.PZHgzPtBNCycCdygy/WNW2RGqOp5f2u',true,'manuel','villa','mevc@gmail.com');
insert into users(user_name,password,enabled,name,last_name,email) values('sofi','$2a$10$.y3GY0mzK2DaIJVFyszcrOmVRoobF7ovKtEG..Rd62RmBzw11Ubka',true,'sofi','porti','sofi@gmail.com');

insert into roles(name) values('ROLE_USER');
insert into roles(name) values('ROLE_ADMIN');

insert into users_roles(user_id,role_id) values(1,1);
insert into users_roles(user_id,role_id) values(2,2);
insert into users_roles(user_id,role_id) values(2,1);