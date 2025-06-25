CREATE TABLE prices
(
    id          BIGSERIAL PRIMARY KEY,
    open_price  BIGINT    NOT NULL,
    high_price  BIGINT    NOT NULL,
    low_price   BIGINT    NOT NULL,
    close_price BIGINT    NOT NULL,
    date        TIMESTAMP NOT NULL,
    asset_id    BIGINT    NOT NULL,

    CONSTRAINT fk_price_asset FOREIGN KEY (asset_id) REFERENCES assets (id)
);

CREATE TABLE assets
(
    id            BIGSERIAL PRIMARY KEY,
    name       TEXT      NOT NULL,
    country       TEXT      NOT NULL,
    min_price     BIGINT    NOT NULL,
    total_price   BIGINT    NOT NULL,
    volume        BIGINT,
    asset_type    TEXT      NOT NULL,
    deleted       BOOLEAN DEFAULT FALSE,
    creation_date TIMESTAMP NOT NULL,
    source        TEXT      NOT NULL
);
