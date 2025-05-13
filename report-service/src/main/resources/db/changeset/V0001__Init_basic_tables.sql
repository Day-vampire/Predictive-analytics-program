CREATE TABLE report_info
(
    report_name         TEXT           NOT NULL,
    report_author_name   TEXT           NOT NULL,
    report_file_size     NUMERIC(10, 2) NOT NULL,
    report_file_extension TEXT           NOT NULL,
    report_type         TEXT           NOT NULL,
    report_data_file_name TEXT           NOT NULL,
    deleted            BOOLEAN DEFAULT FALSE,
    report_create_time           TIMESTAMP      NOT NULL,
    report_last_modification_time         TIMESTAMP      NOT NULL,
    PRIMARY KEY (report_name, report_author_name) -- Composite primary key
);