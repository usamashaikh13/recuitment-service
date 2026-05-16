# Recruitment Service

Recruitment Service is the core hiring workflow service for the Xplore hiring platform. It manages candidates, job openings, applications, interview scheduling, offer management, and dashboard metrics.

Note: the repository name is currently spelled `recuitment-service`, while the Maven artifact and application name use `recruitment-service`.

## Tech Stack

- Java 21
- Spring Boot 3.1.5
- Spring Web
- Spring Data JPA
- H2 in-memory database
- Lombok
- Maven

## Responsibilities

- Create and manage candidate profiles.
- Create and manage job openings.
- Track candidates through application pipeline stages.
- Schedule interviews by calling Interviewer Service for matching slots.
- Store recruitment/interview assignments.
- Manage offers and offer status changes.
- Provide dashboard summary metrics for recruiters.

## Runtime Configuration

The service runs on port `8082` by default.

Important properties:

```properties
server.port=8082
spring.application.name=recruitment-service
spring.datasource.url=jdbc:h2:mem:xplore
spring.jpa.hibernate.ddl-auto=create-drop
interviewer.service.url=${INTERVIEWER_SERVICE_URL:http://localhost:8081/api/slots}
```

The service uses H2 in-memory storage for local development. Because `ddl-auto=create-drop` is enabled, data is recreated on each restart.

For production, replace H2 with MySQL or PostgreSQL and add Flyway or Liquibase migrations.

## Main APIs

### Candidates

```http
POST /api/candidates
GET  /api/candidates
GET  /api/candidates/{id}
PUT  /api/candidates/{id}
GET  /api/candidates/test
```

Example candidate:

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "phone": "9999999999",
  "currentCompany": "Acme",
  "currentDesignation": "Software Engineer",
  "yearsExperience": 4.5,
  "skills": ["Java", "Spring Boot", "SQL"],
  "resumeUrl": "https://example.com/resume.pdf",
  "portfolioUrl": "https://portfolio.example.com",
  "linkedinUrl": "https://linkedin.com/in/rahul",
  "source": "LinkedIn",
  "notes": "Strong backend profile"
}
```

### Job Openings

```http
POST  /api/jobs
GET   /api/jobs
GET   /api/jobs/{id}
PUT   /api/jobs/{id}
PATCH /api/jobs/{id}/status?status=OPEN
GET   /api/jobs?status=OPEN
GET   /api/jobs?recruiterId=10
```

Job statuses:

```text
DRAFT, OPEN, PAUSED, CLOSED
```

Example job:

```json
{
  "title": "Backend Engineer",
  "department": "Engineering",
  "location": "Mumbai",
  "employmentType": "Full-time",
  "minExperience": 3,
  "maxExperience": 6,
  "requiredSkills": ["Java", "Spring Boot", "SQL"],
  "description": "Build backend services for the hiring platform.",
  "salaryRange": "12-18 LPA",
  "status": "OPEN",
  "hiringManagerId": 1,
  "recruiterId": 10
}
```

### Applications

```http
POST  /api/applications
GET   /api/applications
GET   /api/applications/{id}
PATCH /api/applications/{id}/stage
GET   /api/applications?jobId=1
GET   /api/applications?candidateId=1
GET   /api/applications?stage=SCREENING
```

Application stages:

```text
APPLIED, SCREENING, SHORTLISTED, INTERVIEW_SCHEDULED, FEEDBACK_PENDING, OFFER, HIRED, REJECTED, WITHDRAWN
```

Example application:

```json
{
  "jobId": 1,
  "candidateId": 1,
  "source": "LinkedIn",
  "ownerRecruiterId": 10,
  "screeningNotes": "Meets initial criteria"
}
```

Example stage update:

```json
{
  "stage": "SHORTLISTED",
  "notes": "Move to L1 technical round"
}
```

### Interview Scheduling And Recruitment Records

```http
POST  /api/recruitments/schedule
GET   /api/recruitments
PATCH /api/recruitments/{id}/status?status=COMPLETED
PUT   /api/recruitments/{id}/status?status=COMPLETED
GET   /api/recruitments/candidate/{candidateId}
GET   /api/recruitments/interviewer/{interviewerId}
GET   /api/recruitments/application/{applicationId}
GET   /api/recruitments/load/{interviewerId}
```

Interview statuses:

```text
SCHEDULED, COMPLETED, SELECTED, REJECTED, ON_HOLD
```

Example schedule request:

```json
{
  "candidateId": 1,
  "applicationId": 1,
  "requiredSkills": ["Java", "Spring Boot"],
  "minYearsExperience": 3,
  "round": "L1"
}
```

Scheduling calls Interviewer Service at:

```text
http://localhost:8081/api/slots
```

The flow is:

1. Validate candidate and optional application.
2. Fetch matching available slots from Interviewer Service.
3. Score slots by date and interviewer load.
4. Book the selected slot in Interviewer Service.
5. Save the recruitment record.
6. Move the application to `INTERVIEW_SCHEDULED`.
7. Trigger candidate and interviewer email notifications through Interviewer Service.

### Offers

```http
POST  /api/offers
GET   /api/offers
GET   /api/offers/{id}
PUT   /api/offers/{id}
PATCH /api/offers/{id}/status?status=SENT
GET   /api/offers?candidateId=1
GET   /api/offers?applicationId=1
GET   /api/offers?status=SENT
```

Offer statuses:

```text
DRAFT, APPROVAL_PENDING, APPROVED, SENT, ACCEPTED, DECLINED, WITHDRAWN
```

Example offer:

```json
{
  "applicationId": 1,
  "candidateId": 1,
  "jobId": 1,
  "title": "Backend Engineer",
  "salary": 1500000,
  "currency": "INR",
  "joiningDate": "2026-06-15",
  "status": "DRAFT",
  "notes": "Standard offer"
}
```

Offer status changes update the application pipeline:

- `SENT` or `APPROVED` moves the application to `OFFER`.
- `ACCEPTED` moves the application to `HIRED`.
- `DECLINED` or `WITHDRAWN` moves the application to `WITHDRAWN`.

### Dashboard

```http
GET /api/dashboard/summary
```

Returns totals for candidates, open jobs, applications, scheduled interviews, pending offers, and applications grouped by stage.

## Run Locally

Start Interviewer Service first on port `8081`, then run Recruitment Service:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

H2 console:

```text
http://localhost:8082/h2-console
JDBC URL: jdbc:h2:mem:xplore
User: sa
Password:
```

Override Interviewer Service URL if needed:

```bash
export INTERVIEWER_SERVICE_URL=http://localhost:8081/api/slots
```

## Local End-to-End Flow

1. Create an interviewer and available slot in Interviewer Service.
2. Create a candidate in Recruitment Service.
3. Create a job opening and set it to `OPEN`.
4. Create an application for the candidate and job.
5. Call `/api/recruitments/schedule` with the application id.
6. Submit feedback in Interviewer Service.
7. Create and send an offer in Recruitment Service.
