SET SEARCH_PATH TO test;

-- 1) People from France
SELECT a.last_name,
       CONCAT(SUBSTR(a.first_name, 1, 1), '.') AS first_name,
       r.salary                                AS desired_salary,
       r.currency,
       r.desired_job_position,
       c.name                                  AS country
FROM account a
         JOIN resume r ON r.account_id = a.id
         JOIN citizenship cs ON cs.resume_id = r.id
         JOIN country c ON cs.country_id = c.id
WHERE c.name = 'France';
