INSERT INTO business_stream(id, name)
VALUES (1, 'Transportation, Logistics, Warehousing, International Logistics'),
       (2, 'IT, System Integration, Internet');

INSERT INTO company(id, name, description, establishment_date, business_stream_id)
VALUES (1, 'MyDocuments', NULL, '2000-10-12', 2),
       (2, 'Zoo', 'Some description', '1989-03-10', 1),
       (3, 'Rails', NULL, '1989-03-10', 1);

INSERT INTO company_image(image, company_id)
VALUES ('path://to/some/image/1', 1),
       ('link://to/some/image/2', 2),
       ('path://to/some/image/3', 3),
       ('link://to/some/image/4', 1);