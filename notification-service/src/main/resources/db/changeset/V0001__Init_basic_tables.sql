CREATE TABLE notification
(
    id                BIGSERIAL PRIMARY KEY,
    title             TEXT NOT NULL,
    body              TEXT NOT NULL,
    receiver          TEXT NOT NULL,
    notification_type TEXT NOT NULL
);