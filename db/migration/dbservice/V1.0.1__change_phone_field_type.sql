ALTER TABLE users
    ALTER COLUMN phone TYPE VARCHAR(20)
    USING phone::VARCHAR;

ALTER TABLE users
    ADD CONSTRAINT chk_phone_format
    CHECK (phone ~ '^\+?\d{10,15}$');