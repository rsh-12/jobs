-- Sample user table (Keycloak or other Auth Server)
CREATE TABLE account
(
    id                        SERIAL PRIMARY KEY,
    last_name                 VARCHAR(50),
    first_name                VARCHAR(50),
    email                     VARCHAR(50),
    phone                     VARCHAR(25),
    password                  VARCHAR(250),
    gender                    VARCHAR(10),
    sms_notification_active   BOOLEAN   DEFAULT FALSE,
    email_notification_active BOOLEAN   DEFAULT FALSE,
    created_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- PROFILE
CREATE TABLE seeker_profile
(
    id                   SERIAL PRIMARY KEY,
    description          VARCHAR(250),
    salary               INT,
    currency             VARCHAR(3) DEFAULT 'RUB',
    desired_job_position VARCHAR(70),
    account_id           INT NOT NULL REFERENCES account (id) ON DELETE CASCADE,
    created_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    CHECK ( TRIM(description) != '' AND TRIM(desired_job_position) != '' )
);

CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE citizenship
(
    seeker_id  INT NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    country_id INT NOT NULL REFERENCES country (id) ON DELETE CASCADE,
    UNIQUE (seeker_id, country_id)
);

CREATE TABLE language
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE seeker_language
(
    level       VARCHAR(50) NOT NULL,
    seeker_id   INT         NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    language_id INT         NOT NULL REFERENCES language (id) ON DELETE CASCADE,
    UNIQUE (level, seeker_id, language_id)
);

CREATE TABLE education_detail
(
    id               SERIAL PRIMARY KEY,
    institution_name VARCHAR(150) NOT NULL,
    faculty          VARCHAR(150),
    starting_date    DATE,
    completion_date  DATE,
    seeker_id        INT          NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE
);

CREATE TABLE experience_detail
(
    id             SERIAL PRIMARY KEY,
    is_current_job BOOLEAN DEFAULT FALSE,
    start_date     DATE,
    end_date       DATE,
    job_title      VARCHAR(50) NOT NULL,
    description    VARCHAR(250),
    company_name   VARCHAR(100),
    seeker_id      INT         NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE
);


-- COMPANY
CREATE TABLE business_stream
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
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
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE company_image
(
    id         SERIAL PRIMARY KEY,
    image      VARCHAR(250) NOT NULL UNIQUE,
    company_id INT          NOT NULL REFERENCES company (id) ON DELETE CASCADE
);


-- JOB
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
    country VARCHAR(100),

    CHECK (
                TRIM(street) != '' AND
                TRIM(city) != '' AND
                TRIM(state) != '' AND
                TRIM(country) != ''
        )
);

CREATE TABLE job_post
(
    id              SERIAL PRIMARY KEY,
    description     TEXT,
    is_active       BOOLEAN   DEFAULT TRUE,
    salary_from     INT,
    salary_up_to    INT,
    email           VARCHAR(50),
    phone           VARCHAR(50),
    job_type_id     INT REFERENCES job_type (id),
    job_location_id INT REFERENCES job_type (id),
    posted_by_id    INT REFERENCES company (id) ON DELETE CASCADE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


--
CREATE TABLE skill_set
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE job_post_skill_set
(

    level        VARCHAR(50) NOT NULL,
    skill_set_id INT         NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    job_post_id  INT         NOT NULL REFERENCES job_type (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, job_post_id)
);

CREATE TABLE seeker_skill_set
(
    level        VARCHAR(50) NOT NULL,
    skill_set_id INT         NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    seeker_id    INT         NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, seeker_id)
);

CREATE TABLE job_post_activity
(
    job_post_id INT NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    seeker_id   INT NOT NULL REFERENCES seeker_profile (id) ON DELETE CASCADE,
    UNIQUE (job_post_id, seeker_id)
);
