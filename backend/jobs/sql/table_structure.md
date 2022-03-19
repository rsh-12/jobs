### The table structure I follow during development.

1. **ID**
2. **Fields**
3. **Foreign keys**
4. **Metadata**

<hr>

<br>

Here's an example:

```postgresql
CREATE TABLE profile
(
    -- ID
    id         BIGSERIAL PRIMARY KEY,

    -- Fields
    last_name  VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,

    -- Foreign keys
    account_id BIGINT REFERENCES account (id) ON DELETE CASCADE,

    -- Metadata
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```