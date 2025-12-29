# Book Review

Ktor ile geliştirilmiş kitap inceleme uygulaması.

## Tech Stack

- Kotlin 2.3.0
- Ktor 3.3.3
- Exposed ORM
- PostgreSQL
- Liquibase
- Kotest

## Kurulum

```bash
# PostgreSQL başlat
cd docs && docker-compose up -d

# Uygulamayı çalıştır
./gradlew run
```

Uygulama: http://localhost:8080/books

## Test

```bash
./gradlew test
```

## Yapı

```
src/main/kotlin/club/ozgur/
├── models/          # Exposed table definitions
├── repositories/    # Data access layer
├── routes/          # Ktor routes
├── views/           # HTML DSL templates
└── plugins/         # Ktor plugins (DB config)
```
