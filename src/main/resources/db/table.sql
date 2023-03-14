create table user_tb (
    id int auto_increment primary key,
    email varchar not null unique,
    password varchar not null,
    created_at timestamp
);

commit;