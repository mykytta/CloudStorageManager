create table if not exists files
(
    id  bigint auto_increment primary key,
    file_name varchar(255) not null,
    location varchar(255) not null,
    status varchar(45) not null
);

create table if not exists roles
(
    id  bigint auto_increment primary key,
    name varchar(100) not null,
    status varchar(45) not null
);

create table if not exists users
(
    id  bigint auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    status varchar(45) not null
);

create table if not exists user_roles
(
    user_id  bigint,
    role_id bigint,
    CONSTRAINT u_ID FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT r_ID FOREIGN KEY (role_id) REFERENCES roles (id)
);

create table if not exists events
(
    id  bigint auto_increment primary key,
    user_id bigint not null,
    event_name varchar(255) not null,
    file_id bigint not null,
    status varchar(45) not null,
    constraint file_id
        foreign key (file_id) references files (id),
    constraint user_id
        foreign key (user_id) references users (id)
);

create index file_id_idx
    on events (file_id);

create index user_id_idx
    on events (user_id);

INSERT INTO `jwtapp`.`users` (`id`, `username`, `password`, `status`) VALUES ('1', 'Orest', '$2a$04$qVdui464V/k1LmJcMqScnu.JEX7Mu55lHIJCGpagnuBdGrwYL1Mai', 'ACTIVE');
INSERT INTO `jwtapp`.`roles`(id, name, status) VALUES ('1', 'ROLE_USER', 'ACTIVE');
INSERT INTO `jwtapp`.`roles`(id, name, status) VALUES ('2', 'ROLE_MODERATOR', 'ACTIVE');
INSERT INTO `jwtapp`.`roles`(id, name, status) VALUES ('3', 'ROLE_ADMIN', 'ACTIVE');
INSERT INTO `jwtapp`.`user_roles`(user_id, role_id) VALUES ('1', '3');
INSERT INTO `jwtapp`.`user_roles`(user_id, role_id) VALUES ('1', '2');
INSERT INTO `jwtapp`.`user_roles`(user_id, role_id) VALUES ('1', '1');
