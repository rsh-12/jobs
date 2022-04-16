INSERT INTO job_type(name)
VALUES ('Full time'),
       ('Part time');


INSERT INTO job_location (street, city, state, country)
VALUES ('Pushkin, 12', 'Omsk', 'Omsk Oblast', 'Russia'),
       ('Lenina 45', 'Yekaterinburg', 'Sverdlovsk Oblast', 'Russia');


INSERT INTO job_post(title, description, is_active, salary_from, salary_up_to, currency,
                     email, phone, job_type_id, job_location_id, posted_by_id)
VALUES ('Frontend Developer', 'Office work, full time', DEFAULT, 3000, 5000, 'USD',
        'dev@mail.com', '8-800-300-80-80', 1, 1, 10),
       ('Manager', 'Sales Manager', DEFAULT, 1000, 6000, 'USD',
        'sales@mail.com', '8-800-300-80-81', 1, 2, 20),
       ('DBA', 'Office work, full time', DEFAULT, 4000, 7000, 'USD',
        'dev@mail.com', '8-800-300-80-80', 1, 1, 10);


INSERT INTO job_post_skill_set(level, skill_set_id, job_post_id)
VALUES ('Specialist', 1, 1),
       ('Beginner', 2, 1),
       ('Specialist', 3, 1),
       ('Expert', 8, 2),
       ('Advanced', 45, 2),
       ('Expert', 128, 2);


INSERT INTO job_post_activity(job_post_id, resume_id, status)
VALUES (1, 20, DEFAULT),
       (2, 30, 'invitation'),
       (3, 30, 'rejection');


INSERT INTO specialization(name)
VALUES ('Developer'),
       ('Manager'),
       ('DBA'),
       ('Designer');

INSERT INTO specialization_job_post(job_post_id, specialization_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);