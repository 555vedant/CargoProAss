# Load and Booking System

A Spring Boot backend for managing cargo loads and bookings, integrated with a PostgreSQL database and a simple HTML/JavaScript frontend. Provides RESTful APIs for CRUD operations on loads and bookings, with unit tests achieving >60% coverage.

## Features

- **Load Management**: Create, delete, and list loads (shipper ID, facility, product type, status)
- **Booking Management**: Create, update, delete, and list bookings with a @ManyToOne relationship to loads
- **REST APIs**: `/api/loads` and `/api/bookings` with pagination
- **Frontend**: HTML/JavaScript UI using fetch API
- **Testing**: JUnit 5 and Mockito tests for LoadService and BookingService
- **Error Handling**: ResourceNotFoundException for invalid IDs
- **Database**: PostgreSQL with JPA/Hibernate

## Tech Stack

- **Backend**: Spring Boot 3.x, Java 17, Maven
- **Database**: PostgreSQL
- **Frontend**: HTML, JavaScript
- **Testing**: JUnit 5, Mockito, JaCoCo
- **Dependencies**: Spring Data JPA, Spring Web, PostgreSQL Driver, OpenAPI (Swagger UI)

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL 13+ (database: cargoai)
- Node.js (optional, for frontend)
- Postman (optional, for API testing)

## Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd cargoai-backend
```

### 2. Configure PostgreSQL

Create database:
```sql
psql -U postgres
CREATE DATABASE cargoai;
```

Update `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cargoai
    username: postgres
    password: your-password
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        show_sql: true
        format_sql: true

logging:
  level:
    org.hibernate.SQL: DEBUG
    com.cargoai.assignment: DEBUG
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`.

### 4. Serve Frontend

Access UI at `http://localhost:8080`.

## API Endpoints

### Loads

#### POST /api/loads
Create a load.

**Request Body:**
```json
{
  "shipperId": "SHIP123",
  "facility": {
    "loadingPoint": "Delhi Warehouse",
    "unloadingPoint": "Mumbai Depot",
    "loadingDate": "2025-07-31T10:00:00",
    "unloadingDate": "2025-08-01T18:00:00"
  },
  "productType": "Electronics",
  "truckType": "Refrigerated",
  "noOfTrucks": 3,
  "weight": 4500.75,
  "comment": "Handle with care",
  "datePosted": "2025-07-30T15:30:00",
  "status": "POSTED"
}
```

#### GET /api/loads?page=0&size=10
List loads (paginated).

#### DELETE /api/loads/{id}
Delete a load.

### Bookings

#### POST /api/bookings
Create a booking (requires valid loadId).

**Request Body:**
```json
{
  "loadId": "123e4567-e89b-12d3-a456-426614174000",
  "transporterId": "TRANS123",
  "proposedRate": 1000.50,
  "comment": "Urgent delivery",
  "status": "PENDING",
  "requestedAt": "2025-07-30T15:30:00"
}
```

#### GET /api/bookings?page=0&size=10
List bookings.

#### PUT /api/bookings/{id}
Update a booking.

#### DELETE /api/bookings/{id}
Delete a booking.

#### GET /api/bookings/{id}
Get a booking.

## Testing

Unit tests cover LoadService and BookingService (>60% coverage).

### Run Tests
```bash
mvn clean test
```

### Check Coverage
```bash
mvn jacoco:report
```

View report: `target/site/jacoco/index.html`.

**Test Files:**
- `src/test/java/com/cargoai/assignment/cargoai_backend/service/LoadServiceTest.java`
- `src/test/java/com/cargoai/assignment/cargoai_backend/service/BookingServiceTest.java`

## Database

Hibernate auto-generates the schema (`ddl-auto: update`).

**Tables**: `loads`, `bookings` (with `load_id` foreign key).

Verify data:
```sql
psql -U postgres -d cargoai
SELECT * FROM loads;
SELECT * FROM bookings;
```

## Troubleshooting

- **API Errors**: If `POST /api/bookings` fails with ResourceNotFoundException, verify `loadId` exists in loads
- **Test Failures**: Ensure BookingService and LoadService match provided code
- **Frontend Issues**: Check `script.js` for `data.content` handling; use browser console (F12)
- **Logs**: `tail -f target/spring-boot.log`




