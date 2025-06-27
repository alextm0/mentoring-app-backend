CREATE TABLE users (
   id UUID PRIMARY KEY,
   name VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL UNIQUE,
   role VARCHAR(20) NOT NULL,
   total_xp INT NOT NULL DEFAULT 0,
   current_level INT NOT NULL DEFAULT 1,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE mentor_mentee_link (
    id UUID PRIMARY KEY,
    mentor_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    mentee_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_mentor_mentee UNIQUE (mentor_id, mentee_id),
    CONSTRAINT uq_mentee UNIQUE (mentee_id)
);