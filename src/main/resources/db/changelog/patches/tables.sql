-- liquibase formatted sql
-- changeSet 1:1 failOnError:true logicalFilePath:tables.sql

CREATE TABLE IF NOT EXISTS user
(
    id              bigint                 auto_increment primary key,
    user_name       varchar(255)                          not null,
    password        varchar(255)                          not null,
    role            enum('USER', 'ADMIN')                 null,
    created_date    timestamp,
    lastMod_date    timestamp,
    created_by      varchar(255),
    modified_by     varchar(255)
);

CREATE TABLE IF NOT EXISTS audit_event
(
    id                bigint       auto_increment  primary key,
    timestamp         timestamp    null,
    principal         varchar(255) null,
    type              varchar(255) null,
    request_url       varchar(255) null,
    message           varchar(255) null,
    event_type        varchar(255) null,
    remote_ip_address varchar(255) null,
    session_id        varchar(255) null
)
