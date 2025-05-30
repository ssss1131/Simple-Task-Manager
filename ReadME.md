# Task Manager Backend

## Краткое описание

REST-сервис для учёта личных задач.
Пользователь может:

* зарегистрироваться и войти (JWT-аутентификация);
* создавать, просматривать, изменять и удалять **свои** задачи;
* фильтровать список задач по статусу (`TODO / IN_PROGRESS / DONE`);
* получать список постранично (`page / size / sort`).

Все операции выполняются только в контексте текущего пользователя: чужие задачи недоступны.

---

## Стек и библиотеки

| Область         | Технологии                                       |
| --------------- |--------------------------------------------------|
| Язык / рантайм  | **Java 17**, Maven                               |
| Веб- и DI-слой  | Spring Boot 3, Spring Web                        |
| Безопасность    | Spring Security 6 + JWT                          |
| Работа с БД     | Spring Data JPA (Hibernate 6), **PostgreSQL 16** |
| Миграции        | **Liquibase** (YAML change-log’и)                |
| Маппинг DTO     | MapStruct 1.6                                    |
| Логирование     | SLF4J / Logback                                  |
| Вспомогательные | Lombok                                           |

> Для локального запуска прилагается файл **docker-compose**, поднимающий PostgreSQL.

---

## Требования

* JDK 17+
* Maven 3.9+
* Docker + Docker Compose (только если хотите поднимать Postgres контейнером)

---

## Запуск проекта

1. **Клонировать репозиторий**

   ```bash
   git clone https://github.com/ssss1131/Simple-Task-Manager
   cd Simple-Task-Manager
   ```

2. **Поднять PostgreSQL (Docker)**

   ```bash
   docker compose up -d
   ```

   Контейнер стартует Postgres 16 на порту **5433**, БД `task_manager`, пользователь `ssss`, пароль `ssss`.

3.**Запустить Spring Boot**

   ```bash
   mvn clean spring-boot:run
   ```

   Приложение поднимется на `http://localhost:8080`.

Liquibase при первом старте создаст таблицы `users` и `tasks`, а также таблицы-служебники `DATABASECHANGELOG*`.

---

## Тестовая коллекция Postman

В корне проекта лежит файл **simple task-manager.postman_collection.json**.
Импортируйте его в Postman — там готовые запросы:

1. **User ➜ Register / Login** – получаем JWT.
2. **Task ➜ CRUD** – Надо поставить в коллекцию тот токен что получите после регистрации/авторизации. Как ниже, после того как поставите токен обязательно надо сохранить это изменение

![image](https://github.com/user-attachments/assets/a7f89126-e7d1-43d2-a25e-542d9b42cbc1)


---

## Краткий обзор API

| Метод    | URL                  | Защита | Описание                                                            |
| -------- | -------------------- |--------|---------------------------------------------------------------------|
| `POST`   | `/api/auth/register` | нет    | Регистрация,ответ JWT                                               |
| `POST`   | `/api/auth/login`    | нет    | Логин, ответ — JWT                                                  |
| `GET`    | `/api/tasks`         | да     | Список задач текущего пользователя <br>`?status=&page=&size=&sort=` |
| `POST`   | `/api/tasks`         | да     | Создать задачу                                                      |
| `GET`    | `/api/tasks/{id}`    | да     | Получить одну задачу                                                |
| `PUT`    | `/api/tasks/{id}`    | да     | Обновить задачу                                                     |
| `DELETE` | `/api/tasks/{id}`    | да     | Удалить задачу                                                      |

