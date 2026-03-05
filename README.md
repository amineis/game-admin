# GameAdmin Backend

GameAdmin is a Spring Boot backend for game operations and moderation workflows (players, matches, reports, bans, and
admin users).  
I built it to centralize moderation/admin tools in one API with role-based access and Keycloak JWT security.

## Installation

Clone and move into the project:

```bash
git clone https://github.com/amineis/game-admin.git
cd gameadmin
```

Create your local environment file by duplicating `.env.example` as `.env`.

Start dependencies (Postgres, Keycloak, PgAdmin):

```bash
docker compose --env-file .env up -d postgres keycloak pgadmin
```

Run the API locally:

```bash
mvn spring-boot:run
```

Optional: run the full stack in Docker (API on `8080`):

```bash
docker compose --env-file .env up -d --build
```

Docs after startup:

```text
http://localhost:8080/swagger-ui.html
http://localhost:8080/scalar.html
```

## Usage

Example input: create a game (requires `ADMIN` role JWT).

```bash
curl -X POST "http://localhost:8080/api/v1/games" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "League of Legends",
    "genre": "MOBA"
  }'
```

Example output:

```json
{
  "id": 1,
  "name": "League of Legends",
  "genre": "MOBA",
  "createdAt": "2026-02-17T13:20:41.915Z"
}
```

