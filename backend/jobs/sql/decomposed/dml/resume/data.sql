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
       ('Brazil'),
       ('Uganda'),
       ('Qatar'),
       ('Timor-Leste'),
       ('Mongolia'),
       ('Chile'),
       ('Liberia'),
       ('Nauru'),
       ('Réunion'),
       ('Montserrat'),
       ('Taiwan'),
       ('Antarctica'),
       ('Saint Pierre and Miquelon'),
       ('Argentina'),
       ('Togo'),
       ('Austria'),
       ('Grenada'),
       ('Faroe Islands'),
       ('Morocco'),
       ('Anguilla'),
       ('Palau'),
       ('Northern Mariana Islands'),
       ('Mauritania'),
       ('Ukraine'),
       ('China'),
       ('Lesotho'),
       ('Cyprus'),
       ('DR Congo'),
       ('Russia'),
       ('Bangladesh'),
       ('South Africa'),
       ('Curaçao'),
       ('Guatemala'),
       ('Puerto Rico'),
       ('Antigua and Barbuda'),
       ('Israel'),
       ('Guyana'),
       ('Cayman Islands'),
       ('Croatia'),
       ('Iceland'),
       ('Caribbean Netherlands'),
       ('Sint Maarten'),
       ('Namibia'),
       ('Dominica'),
       ('Gibraltar'),
       ('Senegal'),
       ('Saint Kitts and Nevis'),
       ('Oman'),
       ('Kuwait'),
       ('Åland Islands'),
       ('United States Virgin Islands'),
       ('Bouvet Island'),
       ('United Kingdom'),
       ('Honduras'),
       ('South Georgia'),
       ('Cambodia'),
       ('North Macedonia'),
       ('Iran'),
       ('Panama'),
       ('Cook Islands'),
       ('Andorra'),
       ('Burundi'),
       ('Trinidad and Tobago'),
       ('Comoros'),
       ('French Southern and Antarctic Lands'),
       ('Kosovo'),
       ('Nigeria'),
       ('Poland'),
       ('Niue'),
       ('New Caledonia'),
       ('Ethiopia'),
       ('Germany'),
       ('Azerbaijan'),
       ('Netherlands'),
       ('France'),
       ('Nepal'),
       ('Barbados'),
       ('Jersey'),
       ('India'),
       ('Kyrgyzstan'),
       ('South Sudan'),
       ('Iraq'),
       ('Italy'),
       ('Cuba'),
       ('Bhutan'),
       ('Bahamas'),
       ('Norway'),
       ('Lithuania'),
       ('Kenya'),
       ('Pitcairn Islands'),
       ('Sweden'),
       ('Guadeloupe'),
       ('Gabon'),
       ('Macau'),
       ('Guernsey'),
       ('Rwanda'),
       ('Syria'),
       ('Canada'),
       ('Algeria'),
       ('British Indian Ocean Territory'),
       ('Western Sahara'),
       ('Isle of Man'),
       ('Botswana'),
       ('Kazakhstan'),
       ('Venezuela'),
       ('French Polynesia'),
       ('Sudan'),
       ('Wallis and Futuna'),
       ('Slovakia'),
       ('Saint Barthélemy'),
       ('Christmas Island'),
       ('Solomon Islands'),
       ('Latvia'),
       ('British Virgin Islands'),
       ('Jamaica'),
       ('Afghanistan'),
       ('Serbia'),
       ('Tajikistan'),
       ('Tonga'),
       ('Kiribati'),
       ('Eritrea'),
       ('Mali'),
       ('Haiti'),
       ('Vanuatu'),
       ('Bosnia and Herzegovina'),
       ('Vatican City'),
       ('Benin'),
       ('Svalbard and Jan Mayen'),
       ('Samoa'),
       ('Bolivia'),
       ('Madagascar'),
       ('São Tomé and Príncipe'),
       ('Bahrain'),
       ('Greece'),
       ('Peru'),
       ('Suriname'),
       ('American Samoa'),
       ('Niger'),
       ('Thailand'),
       ('Tuvalu'),
       ('North Korea'),
       ('Ivory Coast'),
       ('Yemen'),
       ('New Zealand'),
       ('Armenia'),
       ('Pakistan'),
       ('Tokelau'),
       ('Malawi'),
       ('Ireland'),
       ('Philippines'),
       ('Czechia'),
       ('Myanmar'),
       ('Cocos (Keeling) Islands'),
       ('Romania'),
       ('Dominican Republic'),
       ('Equatorial Guinea'),
       ('Ghana'),
       ('Malta'),
       ('Turkey'),
       ('Egypt'),
       ('Mozambique'),
       ('Mayotte'),
       ('Belgium'),
       ('Slovenia'),
       ('Hungary'),
       ('United Arab Emirates'),
       ('Albania'),
       ('Heard Island and McDonald Islands'),
       ('Fiji'),
       ('San Marino'),
       ('Moldova'),
       ('Estonia'),
       ('Saint Vincent and the Grenadines'),
       ('United States Minor Outlying Islands'),
       ('Belarus'),
       ('Sri Lanka'),
       ('El Salvador'),
       ('Australia'),
       ('Bermuda'),
       ('Nicaragua'),
       ('Somalia'),
       ('Turks and Caicos Islands'),
       ('Micronesia'),
       ('Palestine'),
       ('Turkmenistan'),
       ('Burkina Faso'),
       ('Costa Rica'),
       ('Vietnam'),
       ('Cameroon'),
       ('French Guiana'),
       ('Eswatini'),
       ('Zambia'),
       ('Liechtenstein'),
       ('Montenegro'),
       ('Saint Lucia'),
       ('Uzbekistan'),
       ('Chad'),
       ('Aruba'),
       ('Denmark'),
       ('Japan'),
       ('Cape Verde'),
       ('Switzerland'),
       ('Hong Kong'),
       ('Bulgaria'),
       ('Jordan'),
       ('Republic of the Congo'),
       ('Laos'),
       ('Norfolk Island'),
       ('South Korea'),
       ('Tunisia'),
       ('Finland'),
       ('Zimbabwe'),
       ('Maldives'),
       ('Singapore'),
       ('Monaco'),
       ('Angola'),
       ('Malaysia'),
       ('Luxembourg'),
       ('Guinea'),
       ('Libya'),
       ('Spain'),
       ('Indonesia'),
       ('Seychelles'),
       ('Brunei'),
       ('Mexico'),
       ('Saint Helena, Ascension and Tristan da Cunha'),
       ('Sierra Leone'),
       ('Central African Republic'),
       ('Greenland'),
       ('Colombia'),
       ('Papua New Guinea'),
       ('Falkland Islands'),
       ('Portugal'),
       ('Guinea-Bissau'),
       ('Saint Martin'),
       ('Tanzania');


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