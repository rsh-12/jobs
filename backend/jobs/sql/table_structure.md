### The table structure I follow during development.

1. **Primary keys**
2. **Fields**
3. **Foreign keys**
4. **Metadata**
5. **Checks**

<hr>

<br>

Here's an example:

```postgresql
CREATE TABLE profile
(
    -- Primary keys
    id         BIGSERIAL PRIMARY KEY,

    -- Fields
    last_name  VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,

    -- Foreign keys
    account_id BIGINT REFERENCES account (id) ON DELETE CASCADE,

    -- Metadata
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Database integrity checks
    CHECK ( TRIM(last_name) != '' AND TRIM(first_name) != '' )
);
```