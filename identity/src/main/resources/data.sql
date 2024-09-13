insert into role (type) values ('USER');
insert into role (type) values ('ADMIN');
insert into app_user (id, username, password, first_name, surname, email, role_id)
values ('0000',
        'user',
        'password',
        'first1',
        'surname1',
        'user@email.com',
        1);

insert into app_user (id, username,password, first_name, surname, email, role_id)
values ('0001',
        'admin',
        'password',
        'first2',
        'surname2',
        'admin@email.com',
        2);