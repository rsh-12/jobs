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