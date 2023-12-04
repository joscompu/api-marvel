CREATE TABLE audit_log
(
    id                 INT auto_increment NOT NULL,
    method             VARCHAR(50) NOT NULL,
    duration_in_millis INT         NOT NULL,
    register_date      DATE        NOT NULL
);