-- Link assignments and problems via join table
CREATE TABLE assignment_problem_links (
    id UUID PRIMARY KEY,
    assignment_id UUID NOT NULL,
    problem_id UUID NOT NULL,
    CONSTRAINT uq_assignment_problem UNIQUE (assignment_id, problem_id),
    CONSTRAINT fk_apl_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
    CONSTRAINT fk_apl_problem FOREIGN KEY (problem_id) REFERENCES problems(id) ON DELETE CASCADE
); 