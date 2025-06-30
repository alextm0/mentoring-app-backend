CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    total_xp INT NOT NULL DEFAULT 0,
    current_level INT NOT NULL DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE mentorships (
    id UUID PRIMARY KEY,
    mentor_id UUID NOT NULL,
    mentee_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_mentor FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_mentee FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_mentor_mentee UNIQUE (mentor_id, mentee_id),
    CONSTRAINT uq_mentee UNIQUE (mentee_id)
);

-- Relationship: One-To-Many (users - resources)
-- A user can create multiple resources, but a resource can be created by only a single user
CREATE TABLE resources (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    external_url VARCHAR(255) NOT NULL,
    in_bank BOOLEAN NOT NULL DEFAULT FALSE,
    mentor_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_resource_mentor FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Relationship: One-To-Many (users - problems)
-- A user can create multiple problems, but a problem is created only by a single user
CREATE TABLE problems (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    difficulty VARCHAR(20) NOT NULL,
    external_url VARCHAR(255) NOT NULL,
    mentor_id UUID NOT NULL,
    in_bank BOOLEAN NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_problem_mentor FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Relationship: One-To-Many (users - assignments)
-- A mentor or mentee can create or complete many assignments,
-- but an assignment can be created or completed by one mentor or mentee
CREATE TABLE assignments (
    id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    xp_reward INTEGER NOT NULL DEFAULT 0,
    mentor_id UUID NOT NULL,
    mentee_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_assignment_mentor FOREIGN KEY (mentor_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_assignment_mentee FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Relationship:
--  One-To-Many (users - submissions): A user can have many submissions, but a submission is specific to only a user
--  One-To-Many (problems - submissions): A problem can have many submissions, but a submission be for a specific problem
--  One-To-Many (assignments - submissions): An assignment can have many submissions for problems, but a submission can be part of a single assignment
CREATE TABLE submissions (
    id UUID PRIMARY KEY,
    assignment_id UUID NOT NULL,
    mentee_id UUID NOT NULL,
    problem_id UUID NOT NULL,
    content TEXT NOT NULL,
    feedback TEXT, -- Feedback provided by the mentor
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_submission_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_mentee FOREIGN KEY (mentee_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_submission_problem FOREIGN KEY (problem_id) REFERENCES problems(id) ON DELETE CASCADE
);