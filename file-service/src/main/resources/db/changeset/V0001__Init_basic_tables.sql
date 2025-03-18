CREATE TABLE file_info
(
    id         BIGSERIAL PRIMARY KEY,
    flname     TEXT           NOT NULL,
    flauthor   TEXT           NOT NULL,
    flsize     NUMERIC(10, 2) NOT NULL,
    flext      TEXT           NOT NULL,
    flstatus   TEXT           NOT NULL,
    fldatatype TEXT           NOT NULL,
    deleted    BOOLEAN DEFAULT FALSE,
    flcrtetm   TIMESTAMP      NOT NULL,
    fllstmodtm TIMESTAMP      NOT NULL,
    UNIQUE (flname, flauthor) -- Composite unique constraint for embedded ID
);