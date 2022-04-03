CREATE TABLE resume
(
    id                   SERIAL PRIMARY KEY,
    description          VARCHAR(250),
    salary               INT,
    currency             VARCHAR(3) DEFAULT 'RUB',
    desired_job_position VARCHAR(70),
    account_id           VARCHAR(100) NOT NULL, -- account
    created_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    CHECK ( LENGTH(currency) = 3 ),
    CHECK (
                TRIM(description) != '' AND
                TRIM(currency) != '' AND
                TRIM(desired_job_position) != ''
        )
);

CREATE TABLE skill_set
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE resume_skill_set
(
    level        VARCHAR(50) NOT NULL CHECK ( TRIM(level) != '' ),
    skill_set_id INT         NOT NULL REFERENCES skill_set (id) ON DELETE CASCADE,
    resume_id    INT         NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, resume_id)
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

CREATE TABLE specialization_resume
(
    resume_id         INT NOT NULL REFERENCES resume (id) ON DELETE CASCADE,
    specialization_id INT NOT NULL, -- specialization
    UNIQUE (resume_id, specialization_id)
);