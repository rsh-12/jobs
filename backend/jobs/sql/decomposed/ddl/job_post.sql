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
    description     TEXT,
    is_active       BOOLEAN   DEFAULT TRUE,
    salary_from     INT,
    salary_up_to    INT,
    email           VARCHAR(50),
    phone           VARCHAR(50),
    job_type_id     INT NOT NULL REFERENCES job_type (id),
    job_location_id INT REFERENCES job_location (id),
    posted_by_id    INT NOT NULL,                                -- company
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CHECK ( salary_up_to >= salary_from ),
    CHECK ( phone ~ '\+?\d([\s-]?\d{3}){0,2}([\s-]?\d{2}){2}' ), -- 8-800-800-80-80
    CHECK ( email ~* '.+@.+\.+\w{2,8}' )                         -- some@address.com
);

CREATE TABLE job_post_skill_set
(
    level        VARCHAR(50) NOT NULL CHECK ( TRIM(level) != '' ),
    skill_set_id INT         NOT NULL, -- skill_set
    job_post_id  INT         NOT NULL REFERENCES job_type (id) ON DELETE CASCADE,
    UNIQUE (level, skill_set_id, job_post_id)
);

CREATE TABLE job_post_activity
(
    job_post_id INT NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    resume_id   INT NOT NULL, -- resume
    UNIQUE (job_post_id, resume_id)
);

CREATE TABLE specialization
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL UNIQUE CHECK ( TRIM(name) != '' )
);

CREATE TABLE specialization_job_post
(
    job_post_id       INT NOT NULL REFERENCES job_post (id) ON DELETE CASCADE,
    specialization_id INT NOT NULL REFERENCES specialization (id) ON DELETE CASCADE,
    UNIQUE (job_post_id, specialization_id)
);
