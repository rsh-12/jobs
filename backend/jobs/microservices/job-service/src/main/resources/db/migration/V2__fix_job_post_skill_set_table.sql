ALTER TABLE job_post_skill_set
    DROP COLUMN job_post_id CASCADE,
    ADD COLUMN job_post_id INT REFERENCES job_post (id) ON DELETE CASCADE ;