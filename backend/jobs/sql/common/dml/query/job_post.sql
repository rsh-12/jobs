SET SEARCH_PATH TO test;


-- 1) The last 10 posted jobs
SELECT c.name company,
       jp.title,
       jp.description,
       jl.country,
       jl.state,
       jl.city,
       jl.street,
       jp.created_at
FROM job_post jp
         JOIN company c ON c.id = jp.posted_by_id
         JOIN job_type jt ON jt.id = jp.job_type_id
         JOIN job_location jl ON jl.id = jp.job_location_id
ORDER BY jp.created_at DESC
LIMIT 10;
