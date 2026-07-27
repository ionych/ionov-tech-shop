# IONOV TECH SHOP — интернет-магазин электроники

Веб-приложение на Java 25 + Spring Boot 4.0.6. Магазин электроники 2026 года: смартфоны, ноутбуки, наушники, планшеты и аксессуары.

Это мой учебный проект, созданный для отработки навыков работы с Java/Spring. В данный момент я использую его как полигон для ручного и API-тестирования, чтобы показать свои навыки QA-инженера.

## Технологии

| Технология | Версия |
|------------|--------|
| Java | 25.0.2 (Eclipse Temurin) |
| Spring Boot | 4.0.6 |
| Сборщик | Maven |
| База данных | PostgreSQL 16 |
| Миграции | Liquibase |
| Безопасность | Spring Security + JWT |
| Сессии | Spring Session JDBC |
| Фронтенд | Thymeleaf + Bootstrap 5 |
| Мониторинг | Spring Boot Actuator |

## Сущности (9 штук)

Role, User, Category, Product, Cart, CartItem, Order, OrderItem, Review

## Как запустить

1. Установить PostgreSQL и создать базу:

```bash
sudo apt install postgresql -y
sudo systemctl start postgresql
sudo -u postgres psql

CREATE DATABASE ionov_shop;
CREATE USER ionov WITH PASSWORD 'ionov123';
GRANT ALL PRIVILEGES ON DATABASE ionov_shop TO ionov;
ALTER USER ionov CREATEDB;
GRANT ALL ON SCHEMA public TO ionov;
\q
```
2. Создать таблицы для Spring Session:
```bash
psql -U ionov -d ionov_shop -h localhost -W

CREATE TABLE SPRING_SESSION (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);
CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BYTEA NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
\q
```
3. Запустить приложение:
```bash
mvn spring-boot:run -DskipTests

Открыть в браузере: http://localhost:8080
```
4. Тестовый аккаунт:
```bash
Роль	Email	Пароль
Админ	admin@ionov.ru	admin
```
5. Запуск тестов:
```bash
mvn test

Результат: 27 тестов, BUILD SUCCESS

Покрытие кода: Entity 93%, Security 64% (JaCoCo)
```

API Endpoints
```bash
Метод	URL	              Доступ
POST	/api/auth/register	Все
POST	/api/auth/login	        Все
GET	/api/products	        Все
GET	/api/cart	   Авторизованные
POST	/api/cart	   Авторизованные
GET	/api/orders	   Авторизованные
POST	/api/orders	   Авторизованные
GET	/actuator	        Все
```
### Автор
ION 

