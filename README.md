# mentoring-app-backend

## User & Pairing Managemenet

- GET    /api/v1/mentors
- GET    /api/v1/mentees
- POST   /api/v1/mentor-links          # Pair mentor/mentee (body: { email })
- DELETE /api/v1/mentor-links/{id}     # Remove pairing (mentor or mentee)
- GET    /api/v1/me                    # Get current user's profile

## Problem & Resource Bank

- GET    /api/v1/problems
- POST   /api/v1/problems              # Create new problem (mentor)
- GET    /api/v1/problems/{id}
- POST   /api/v1/resources             # Create new resource (mentor)
- GET    /api/v1/resources
- GET    /api/v1/resources/{id}

## Assignments

- GET    /api/v1/mentees/{menteeId}/assignments   # Mentor fetches
- POST   /api/v1/mentees/{menteeId}/assignments   # Mentor assigns
- GET    /api/v1/assignments/{assignmentId}       # Details for mentor/mentee

## Code submission & Review

- POST   /api/v1/assignments/{assignmentId}/submission     # Mentee submits code
- GET    /api/v1/assignments/{assignmentId}/submissions    # Mentor/mentee view attempts
- POST   /api/v1/submissions/{submissionId}/review         # Mentor reviews submission

## XP / Gamification

- GET    /api/v1/mentees/{menteeId}/xp # XP for a mentee
