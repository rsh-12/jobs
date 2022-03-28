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


INSERT INTO citizenship(resume_id, country_id)
VALUES (1, 89), -- France
       (2, 89),
       (3, 43); -- Russia


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


INSERT INTO company(name, business_stream_id)
VALUES ('MyOffice', 2), -- IT, System Integration, Internet
       ('Rainbow', 5),  -- Media, Marketing, Advertising, PR, Design, Production
       ('BigLogistic', 1); -- Transportation, Logistics, Warehousing, International Logistics


INSERT INTO job_type(name)
VALUES ('Full time'),
       ('Part time');


INSERT INTO job_location(country, state, city)
VALUES ('Russia', 'Sverdlovsk Oblast', 'Yekateringburg');


/*INSERT INTO job_post(description, salary_from, salary_up_to, email, phone, job_type_id,
                     job_location_id, posted_by_id)
VALUES ()*/