![Bot](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/scrapper.yml/badge.svg)

# Link Tracker

ФИО: Приступ Сергей Александрович

Приложение для отслеживания обновлений контента по ссылкам.
При появлении новых событий отправляется уведомление в Telegram.

Проект написан на `Java 21` с использованием `Spring Boot 3`.

Проект состоит из 2-х приложений:
* Bot
* Scrapper

Для работы требуется БД `PostgreSQL`. Присутствует опциональная зависимость на `Kafka`.

## Технологии:
### Проект
- Spring Boot 3
- Spring Data JPA
- JDBC
- Docker
- Postgres
- liquibase
- Kafka
- Prometheus
- Grafana 
- Maven
- REST API

### Тестирование
- JUnit 5
- Mockito
- WireMock
