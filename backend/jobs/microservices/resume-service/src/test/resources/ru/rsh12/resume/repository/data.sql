INSERT INTO country(name)
VALUES ('Uruguay'),
       ('Paraguay'),
       ('Gambia'),
       ('Djibouti'),
       ('Martinique'),
       ('Guam'),
       ('Georgia'),
       ('United States'),
       ('Belize'),
       ('Mauritius'),
       ('Lebanon'),
       ('Saudi Arabia'),
       ('Ecuador'),
       ('Marshall Islands'),
       ('Brazil');


INSERT INTO resume(description, salary, currency, desired_job_position, account_id)
VALUES ('Looking for a job', NULL, DEFAULT, 'Manager', '34551743-d36e-407c-abab-6adcf9943912'),
       ('Looking for a job', 3000, 'USD', 'Product Manager',
        '34551743-d36e-407c-abab-6adcf9943912'),
       ('I have 3 years experience in React', 150000, DEFAULT, 'Frontend Developer',
        '4b357abb-32b3-4bb5-a578-bad8224101a8');

INSERT INTO citizenship(resume_id, country_id)
VALUES (1, 15),
       (2, 12),
       (3, 8);

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

INSERT INTO experience_detail(is_current_job, start_date, end_date, job_title, description,
                              company_name, resume_id)
VALUES (TRUE, '2010-10-12', NULL, 'Manager', NULL, 'IT-cat', 1),
       (TRUE, '2010-10-12', NULL, 'Manager', NULL, 'IT-cat', 2),
       (FALSE, '2015-01-05', '2020-03-11', 'Frontend Developer',
        'I also have some experience with Angular 8', 'MyDocuments', 3);

INSERT INTO education_detail(institution_name, faculty, starting_date, completion_date, resume_id)
VALUES ('UrSU', 'Energy and Hi-Tech Management', '2010-06-01', '2014-06-01', 1),
       ('UrSU', 'Energy and Hi-Tech Management', '2010-06-01', '2014-06-01', 2),
       ('UrSU', 'Information and Software Engineering', '2012-06-01', '2017-06-01', 3);

INSERT INTO specialization_resume(resume_id, specialization_id)
VALUES (1, 36), -- Project Management
       (2, 36), -- Project Management
       (3, 25); -- Software Development

INSERT INTO skill_set(name)
VALUES ('React'),
       ('CSS'),
       ('HTML'),
       ('Angular'),
       ('Java'),
       ('Docker'),
       ('Kubernetes'),
       ('Management'),
       ('Communication');

INSERT INTO resume_skill_set(level, skill_set_id, resume_id)
VALUES ('Expert', 1, 3),
       ('Average', 2, 3),
       ('Skilled', 3, 3),
       ('Specialist', 4, 3),
       ('Expert', 6, 3),
       ('Expert', 8, 1),
       ('Expert', 8, 2),
       ('Advanced', 9, 1),
       ('Advanced', 9, 2);
