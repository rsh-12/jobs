-- See "table_structure.md" file.


/*
  The "user" table will be created on the Authorization server side,
  here it is presented as an example.
*/
CREATE TABLE "user"
(
    id                        SERIAL PRIMARY KEY,
    last_name                 VARCHAR(50),
    first_name                VARCHAR(50),
    email                     VARCHAR(60), -- todo: add regex
    phone                     VARCHAR(25), -- todo: add regex
    password                  VARCHAR(250) NOT NULL,
    date_of_birth             DATE,
    gender                    VARCHAR(15), -- todo: add regex, convert to lowercase
    sms_notification_actice   BOOLEAN   DEFAULT FALSE,
    email_notification_active BOOLEAN   DEFAULT FALSE,
    created_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


/* The "language" table stores a list of languages that seekers can speak. */
CREATE TABLE language
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(150) UNIQUE NOT NULL -- example: English (Australia)
);


/* A table for storing user profiles. */
CREATE TABLE seeker_profile
(
    id                   SERIAL PRIMARY KEY,
    description          TEXT,
    salary               NUMERIC(12, 2), -- todo: add some checks
    currency             VARCHAR(3) DEFAULT 'RUB',
    desired_job_position VARCHAR(50),    -- todo: add some checks
    user_id              INT NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    created_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    CHECK ( salary >= 0 OR salary IS NULL )
);


CREATE TABLE seeker_language
(
    level       VARCHAR(50), -- todo: add some checks
    seeker_id   INT NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    language_id INT NOT NULL REFERENCES language (id) ON DELETE CASCADE,
    UNIQUE (level, seeker_id, language_id)
);


CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE citizenship
(
    country_id INT NOT NULL REFERENCES country (id) ON DELETE CASCADE,
    seeker_id  INT NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    UNIQUE (country_id, seeker_id)
);


CREATE TABLE skill_set
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(150) -- todo: add some checks
);


CREATE TABLE seeker_skill_set
(
    level        VARCHAR(50),
    seeker_id    INT NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    skill_set_id INT NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    UNIQUE (level, seeker_id, seeker_id)
);


CREATE TABLE education_detail
(
    id              SERIAL PRIMARY KEY,
    istitution_name VARCHAR(200) NOT NULL,
    faculty         VARCHAR(200) NOT NULL,
    starting_date   DATE,
    completion_date DATE,
    seeker_id       INT          NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE
);


CREATE TABLE experience_detail
(
    id                   SERIAL PRIMARY KEY,
    is_current_job       BOOLEAN DEFAULT FALSE,
    start_date           DATE        NOT NULL,
    end_date             DATE        NOT NULL, -- todo: > start_date
    job_title            VARCHAR(50) NOT NULL,
    description          VARCHAR(250),
    compnay_name         VARCHAR(150),
    job_location_city    VARCHAR(100),
    job_location_state   VARCHAR(100),
    job_location_country VARCHAR(100),
    seeker_id            INT         NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE
);


CREATE TABLE business_stream
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE company
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(100) NOT NULL,
    description        TEXT,
    email              VARCHAR(50),
    phone              VARCHAR(25),
    establishment_date DATE,
    website_url        VARCHAR(250),
    business_stream_id INT          NOT NULL REFERENCES business_stream (id)
);


CREATE TABLE company_image
(
    id         SERIAL PRIMARY KEY,
    image      VARCHAR(250) NOT NULL UNIQUE,
    company_id INT          NOT NULL REFERENCES company (id) ON DELETE CASCADE
);


CREATE TABLE job_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE job_location
(
    id      SERIAL PRIMARY KEY,
    street  VARCHAR(100),
    city    VARCHAR(100),
    state   VARCHAR(100),
    country VARCHAR(100)
);


CREATE TABLE job_post
(
    id              SERIAL PRIMARY KEY,
    description     TEXT,
    is_active       BOOLEAN    DEFAULT TRUE,
    salary_from     NUMERIC(12, 2),
    salary_up_to    NUMERIC(12, 2),
    currency        VARCHAR(3) DEFAULT 'RUB',
    job_type_id     INT NOT NULL REFERENCES job_type (id),
    posted_by_id    INT NOT NULL REFERENCES company (id) ON DELETE CASCADE,
    job_location_id INT REFERENCES job_location (id) ON DELETE SET NULL,
    created_at      TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP  DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE job_post_skill_set
(
    level        VARCHAR(50) NOT NULL,
    skill_set_id INT         NOT NULL REFERENCES skill_set (id),
    job_post_id  INT         NOT NULL REFERENCES job_post (id),
    UNIQUE (level, skill_set_id, job_post_id)
);


CREATE TABLE job_post_activity
(
    job_post_id INT NOT NULL REFERENCES job_post (id),
    seeker_id   INT NOT NULL REFERENCES seeker_profile (id),
    UNIQUE (job_post_id, seeker_id)
);