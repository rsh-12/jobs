CREATE TABLE business_stream
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK ( LENGTH(TRIM(name)) > 1 )
);

CREATE TABLE company
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(100) NOT NULL,
    description        TEXT,
    establishment_date DATE,
    website_url        VARCHAR(250),
    business_stream_id INT REFERENCES business_stream (id),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CHECK ( TRIM(name) != '' ),
    CHECK ( establishment_date <= CURRENT_DATE )
);

CREATE TABLE company_image
(
    id         SERIAL PRIMARY KEY,
    image      VARCHAR(250) NOT NULL UNIQUE,
    company_id INT          NOT NULL REFERENCES company (id) ON DELETE CASCADE
);
