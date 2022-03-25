SET SEARCH_PATH TO test;

INSERT INTO account(id, last_name, first_name, email, phone, password, gender)
VALUES ('34551743-d36e-407c-abab-6adcf9943912', 'Snow', 'Rianne', 'rianne@mail.com',
        '8 800 800 80 80', 'password1', 'FEMALE'),
       ('4b357abb-32b3-4bb5-a578-bad8224101a8', 'Burke', 'Liliana', 'lili@mail.com',
        '8 800 800 80 81', 'password2', 'FEMALE');


INSERT INTO resume(description, salary, currency, desired_job_position, account_id)
VALUES ('Looking for a job', NULL, DEFAULT, 'Manager', '34551743-d36e-407c-abab-6adcf9943912'),
       ('Looking for a job', 3000, 'USD', 'Product Manager',
        '34551743-d36e-407c-abab-6adcf9943912'),
       ('I have 3 years experience in React', 150000, DEFAULT, 'Frontend Developer',
        '4b357abb-32b3-4bb5-a578-bad8224101a8');


INSERT INTO country(name)
VALUES ('Russia'),
       ('France'),
       ('Kazakhstan');


INSERT INTO citizenship(resume_id, country_id)
VALUES (1, 2),
       (2, 2),
       (3, 1);


INSERT INTO language(name)
VALUES ('English'),
       ('Russian'),
       ('France'),
       ('German'),
       ('Kazakh');


INSERT INTO resume_language(level, resume_id, language_id)
VALUES ('Advanced', 1, 1),
       ('Native', 1, 3),

       ('Advanced', 2, 1),
       ('Native', 2, 3),

       ('Native', 3, 2),
       ('Advanced', 3, 1);



-- INSERT INTO experience_detail(is_current_job, start_date, end_date, job_title, description,
--                               company_name, resume_id)
-- VALUES (TRUE, '12-10-2010', NULL, 'Frontend Developer', NULL, 'IT-cat', 1);