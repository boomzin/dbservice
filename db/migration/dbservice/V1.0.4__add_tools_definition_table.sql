CREATE TABLE tools (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    definition JSONB NOT NULL
);
