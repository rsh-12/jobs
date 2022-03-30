SET SEARCH_PATH TO test;

-- 1) Companies by business stream
SELECT *
FROM business_stream bs
         JOIN company c ON bs.id = c.business_stream_id
WHERE bs.name ~* 'it';

-- 2) Number of companies by business stream
SELECT bs.name, COUNT(c.id)
FROM business_stream bs
         JOIN company c ON bs.id = c.business_stream_id
GROUP BY bs.name;

-- 3) Number of company vacancies
SELECT bs.name, c.name, COUNT(jp.id)
FROM business_stream bs
         JOIN company c ON bs.id = c.business_stream_id
         JOIN job_post jp ON c.id = jp.posted_by_id
GROUP BY bs.name, c.name;