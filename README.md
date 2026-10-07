# Place Finder API (Backend)

Spring Boot REST API that stores favourite places in SQL Server. It is used by the Place Finder frontend (separate repository).

## Tech Stack

- Java 21
- Spring Boot 4 (Web, Data JPA, Validation)
- Lombok
- Microsoft SQL Server
- Maven

## Prerequisites

- Java 21
- Maven
- SQL Server and SSMS

## Setup

---
### 1. Create the database

Open SSMS and run:

1. `database/001_schema.sql` creates `PlaceFinderDB` and the `favourite_places` table.

---
### 2. Configure the database connection

Create `src/main/resources/application-local.yaml` (this file is git-ignored, so credentials are not committed):

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=PlaceFinderDB;encrypt=true;trustServerCertificate=true
    username: <your SQL auth username>
    password: <your SQL auth password>
```

Make sure `application.properties` contains this line so the `local` profile loads automatically:

```properties
spring.profiles.default=local
```

---
### 3. Configure CORS (optional)

By default the API accepts requests from `http://localhost:5173` (the frontend dev server). To allow a different origin, add this to `application-local.yaml`:

```yaml
app:
  cors:
    allowed-origin: http://localhost:3000
```

---
### 4. Run the application

Simply navigate to `src/main/java/com/example/placefinder/PlaceFinderApplication.java` and click **Run**.


---
### 5. Run the frontend

The frontend lives in a separate repository. Follow its README and set:

```
VITE_API_BASE_URL=http://localhost:8080/api
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/favourites` | List all favourites, newest first |
| POST | `/api/favourites` | Add a favourite (idempotent) |
| DELETE | `/api/favourites/{placeId}` | Remove a favourite (idempotent) |

Request body for `POST`:

```json
{
  "placeId": "ChIJD1l71zxNzDERApMs9NFt5eM",
  "name": "Damansara",
  "address": "Damansara, Selangor, Malaysia",
  "latitude": 3.0632591,
  "longitude": 101.5567136
}
```

`latitude` ranges from -90 to 90 and `longitude` from -180 to 180.

Quick test:

```bash
curl http://localhost:8080/api/favourites

```

## Design Notes

- Layered design: controller, service, repository.
- DTOs are mapped to entities in the service layer, so the entity is never exposed.
- `POST` and `DELETE` are idempotent.
- A unique constraint on `place_id` prevents duplicate favourites, including under concurrent requests.
- Hibernate runs with `ddl-auto=validate`, so the schema comes from the SQL script only.

## Troubleshooting

| Problem | Fix                                                                 |
|---|---------------------------------------------------------------------|
| Schema validation error on start | Re-run `database/001_schema.sql` against `PlaceFinderDB`            |
| Cannot connect to SQL Server | Enable TCP/IP on port 1433 in SQL Server Configuration Manager      |
| 400 Bad Request on `POST` | Check the body uses `latitude` and `longitude`, not `lat` and `lng` |
| CORS error in the browser | Set `app.cors.allowed-origin` to the frontend's origin              |
