-- Sample user table (Keycloak or other Auth Server)
CREATE TABLE account
(
    id                        VARCHAR PRIMARY KEY,
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


-- RESUME
CREATE TABLE resume
(
    id                   SERIAL PRIMARY KEY,
    description          VARCHAR(250),
    salary               INT,
    currency             VARCHAR(3) DEFAULT 'RUB',
    desired_job_position VARCHAR(70),
    account_id           VARCHAR(100) NOT NULL REFERENCES account (id) ON DELETE CASCADE,
    created_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    CHECK ( LENGTH(currency) = 3 ),
    CHECK (
                TRIM(description) != '' AND
                TRIM(currency) != '' AND
                TRIM(desired_job_position) != ''
        )
);

CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE citizenship
(
    resume_id  INT NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    country_id INT NOT NULL REFERENCES country (id) ON DELETE CASCADE,
    UNIQUE (resume_id, country_id)
);

CREATE TABLE language
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE resume_language
(
    level       VARCHAR(50) NOT NULL,
    resume_id   INT         NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    language_id INT         NOT NULL REFERENCES language (id) ON DELETE CASCADE,
    UNIQUE (level, resume_id, language_id)
);

CREATE TABLE education_detail
(
    id               SERIAL PRIMARY KEY,
    institution_name VARCHAR(150) NOT NULL,
    faculty          VARCHAR(150),
    starting_date    DATE,
    completion_date  DATE,
    resume_id        INT          NOT NULL REFERENCES resume (id) ON DELETE CASCADE,

    CHECK ( TRIM(institution_name) != '' ),
    CHECK ( completion_date > starting_date )
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
    resume_id      INT         NOT NULL REFERENCES resume (id) ON DELETE CASCADE,

    CHECK ( end_date > start_date ),
    CHECK ( TRIM(job_title) != '' )
);


-- COMPANY
CREATE TABLE business_stream
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE CHECK ( LENGTH(TRIM(name)) > 1 )
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


-- JOB
CREATE TABLE job_type
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE job_location
(
    id      SERIAL PRIMARY KEY,
    street  VARCHAR(100),
    city    VARCHAR(100),
    state   VARCHAR(100),
    country VARCHAR(100),

    UNIQUE (street, city, state, country),
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
    title           VARCHAR(150) NOT NULL,
    description     TEXT,
    is_active       BOOLEAN    DEFAULT TRUE,
    salary_from     INT,
    salary_up_to    INT,
    currency        VARCHAR(3) DEFAULT 'RUB',
    email           VARCHAR(50),
    phone           VARCHAR(50),
    job_type_id     INT          NOT NULL REFERENCES job_type (id),
    job_location_id INT REFERENCES job_location (id),
    posted_by_id    INT          NOT NULL REFERENCES company (id) ON DELETE CASCADE,
    created_at      TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    CHECK ( salary_up_to >= salary_from ),
    CHECK ( phone ~ '\+?\d([\s-]?\d{3}){0,2}([\s-]?\d{2}){2}' ), -- 8-800-800-80-80
    CHECK ( email ~* '.+@.+\.+\w{2,8}' ),                        -- some@address.com
    CHECK ( TRIM(title) != '' )
);


--
CREATE TABLE skill_set
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE job_post_skill_set
(
    level        VARCHAR(50) NOT NULL CHECK ( TRIM(level) != '' ),
    skill_set_id INT         NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    job_post_id  INT         NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, job_post_id)
);

CREATE TABLE resume_skill_set
(
    level        VARCHAR(50) NOT NULL CHECK ( TRIM(level) != '' ),
    skill_set_id INT         NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    resume_id    INT         NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, resume_id)
);

CREATE TABLE job_post_activity
(
    job_post_id INT                           NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    resume_id   INT                           NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    status      VARCHAR(30) DEFAULT 'pending' NOT NULL,
    UNIQUE (job_post_id, resume_id, status),

    CHECK (LOWER(status) = 'rejection' OR
           LOWER(status) = 'invitation' OR
           LOWER(status) = 'pending')
);

CREATE TABLE specialization
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
    -- business_stream_id INT         NOT NULL REFERENCES business_stream (id)
);

CREATE TABLE specialization_resume
(
    resume_id         INT NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    specialization_id INT NOT NULL REFERENCES specialization (id) ON DELETE CASCADE,
    UNIQUE (resume_id, specialization_id)
);

CREATE TABLE specialization_job_post
(
    job_post_id       INT NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    specialization_id INT NOT NULL REFERENCES specialization (id) ON DELETE CASCADE,
    UNIQUE (job_post_id, specialization_id)
);