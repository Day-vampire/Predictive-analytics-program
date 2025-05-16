CREATE TABLE person
(
    id        BIGSERIAL PRIMARY KEY,
    name      TEXT NOT NULL,
    last_name TEXT NOT NULL,
    number    TEXT,
    country   TEXT
);

CREATE TABLE role
(
    id          BIGSERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE user_image_data
(
    id            BIGSERIAL PRIMARY KEY,
    name          TEXT NOT NULL,
    type          TEXT NOT NULL,
    image_content BYTEA        NOT NULL
);

CREATE TABLE "user"
(
    id                 BIGSERIAL PRIMARY KEY,
    email              TEXT NOT NULL,
    password           TEXT NOT NULL,
    user_image_data_id BIGINT,
    person_id          BIGINT,
    role_id            BIGINT       NOT NULL,
    is_delete          BOOLEAN,

    CONSTRAINT fk_user_user_image_data FOREIGN KEY (user_image_data_id) REFERENCES user_image_data (id),
    CONSTRAINT fk_user_person FOREIGN KEY (person_id) REFERENCES person (id),
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id)
);
