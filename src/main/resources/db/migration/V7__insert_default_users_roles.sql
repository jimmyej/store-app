INSERT INTO storedb.users_roles (user_id, role_id) VALUES(
    (select user_id from storedb.users where username = 'admin'),
    (select role_id from storedb.roles where role_name= 'ROLE_ADMIN')
);
INSERT INTO storedb.users_roles (user_id, role_id) VALUES(
    (select user_id from storedb.users where username = 'user.jwt'),
    (select role_id from storedb.roles where role_name= 'ROLE_USER')
);
