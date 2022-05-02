INSERT INTO business_stream(name)
VALUES ('Transportation, Logistics, Warehousing, International Logistics'),
       ('IT, System Integration, Internet'),
       ('Electronics, Tool Engineering, Household Appliances, Computers and Office Equipment'),
       ('Telecommunications, Communications'),
       ('Media, Marketing, Advertising, PR, Design, Production'),
       ('Construction, Real Estate, Architecture'),
       ('Automotive Business'),
       ('Timber Industry'),
       ('Metallurgy, Metalwork'),
       ('Food Products'),
       ('Agriculture'),
       ('Heavy Engineering'),
       ('Chemical Production, Fertilizers'),
       ('Government Organizations'),
       ('Public Activity, Political Parties, Volunteering, Non-Profit Organizations'),
       ('Educational Institutions'),
       ('Retail'),
       ('FMCG (non-edible)'),
       ('Financial Sector'),
       ('Business Services'),
       ('Mining Industry'),
       ('Power Industry'),
       ('Oil and Gas'),
       ('Medicine, Pharmaceuticals, Pharmacies'),
       ('Public Services'),
       ('Hotels, Restaurants, Food Service Industry, Catering'),
       ('Utilities'),
       ('Art, Culture'),
       ('Industrial Equipment, Machine Tools and Components'),
       ('Multiprofile Asset Management');


INSERT INTO company(name, description, establishment_date, website_url, business_stream_id)
VALUES ('Jeffries Financial Group',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium, similique?',
        '2000-12-09',
        'http://some.site1.io',
        19),

       ('Harley-Davidson',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Est, et facere magni molestias quae quibusdam.',
        '1970-04-01',
        'http://some.site2.io',
        1),

       ('Harris',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Error excepturi hic labore mollitia nostrum odio, porro sint sit suscipit voluptatibus?',
        '1998-08-25',
        'http://some.site3.io',
        28);


INSERT INTO company_image(image, company_id)
VALUES ('https://avatars.mds.yandex.net/i?id=5197722cc452730542f1857e0eed9dd9-5546664-images-thumbs&n=13&exp=1', 1),
       ('https://www.clipartmax.com/png/full/39-395435_3d-art-logo-design-free-logos-online-logo-design-free-png.png', 2),
       ('https://i.pinimg.com/736x/ca/15/26/ca15264f275114dec66bbbb94416fcfc--s-logo-logo-google.jpg', 3);