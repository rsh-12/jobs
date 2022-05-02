INSERT INTO specialization(name)
VALUES ('Banking Software'),
       ('SEO'),
       ('Database Administrator'),
       ('Startups'),
       ('Video Games Development'),
       ('CRM Systems'),
       ('Other'),
       ('CTO, CIO, IT Director'),
       ('Web Engineer'),
       ('Web master'),
       ('Analyst'),
       ('Art Director'),
       ('ERP'),
       ('Engineer'),
       ('Internet'),
       ('IT Security'),
       ('Consulting, Outsourcing'),
       ('Content'),
       ('Testing'),
       ('Marketing'),
       ('Multimedia'),
       ('Entry Level, Little Experience'),
       ('Data Communication and Internet Access'),
       ('Support, Helpdesk'),
       ('Software Development'),
       ('Sales'),
       ('Producer'),
       ('Business Development'),
       ('Networks'),
       ('System Integration'),
       ('System Administrator'),
       ('Computer Aided Design Systems'),
       ('Mobile, Wireless Technology'),
       ('Telecommunications'),
       ('Technical Writer'),
       ('Project Management'),
       ('E-Commerce');


INSERT INTO job_type(name)
VALUES ('Full time'),
       ('Part time');


INSERT INTO job_location(country, state, city)
VALUES ('Russia', 'Sverdlovsk Oblast', 'Yekaterinburg');


INSERT INTO job_post(title, description, salary_from, salary_up_to, currency, email, phone,
                     job_type_id,
                     job_location_id, posted_by_id)
VALUES ('Manager',
        'Manager will be focused on the management and delivery of client engagements, ' ||
        'as well as sales and practice development. In this role, you will develop ' ||
        'and coach high performing people and teams, leading, ' ||
        'guiding and supporting them to make an impact that matters, ' ||
        'and setting the direction to deliver exceptional client services.',
        1000, 2000, 'EUR', 'docs@mail.com', '8-800-600-12-12', 1, 1, 3),

       ('Frontend Developer (React js)',
        'We make browser extensions that use lots of people around the globe. ' ||
        'We are constantly entering new markets and ' ||
        'adjusting products to the needs of users in different countries.',
        2900, 5000, 'EUR', 'web@mail.com', '8-800-600-20-21', 1, 1, 1);


INSERT INTO specialization_job_post(job_post_id, specialization_id)
VALUES (1, 6),
       (1, 4),
       (2, 9),
       (2, 10),
       (2, 25);


INSERT INTO job_post_activity(job_post_id, resume_id, status)
VALUES (1, 1, 'invitation'),
       (2, 3, DEFAULT);


INSERT INTO job_post_skill_set(level, skill_set_id, job_post_id)
VALUES ('Beginner', 1, 2),
       ('Average', 2, 2),
       ('Skilled', 3, 2),
       ('Specialist', 4, 2),
       ('Expert', 6, 2),
       ('Expert', 8, 1),
       ('Advanced', 9, 1);