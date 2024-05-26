Я создал инструкцию и внес некоторые уточнения и исправления для более четкого понимания и соответствия с требованиями.

Для создания проекта с использованием Spring Initializr, нужно указать следующие настройки:

1. ### Основные параметры проекта


   Group: de.leafgrow

   Artifact: project

   Name: LeafGrow.de

   Description: MWP website backend for daily plant care tips

   Package name: de.leafgrow.project

   Packaging: Jar

   Java: 11 (или выше, если требуется)

2. ### Зависимости
                       

   Нужны следующие зависимости:

Spring Boot DevTools (опционально для разработки)   

Spring Web (для создания REST API)          

Spring Security (для обеспечения безопасности)

Spring Data JPA (для работы с базой данных через Hibernate)

Liquibase (для управления миграциями базы данных)

PostgreSQL Driver (для подключения к PostgreSQL)

Spring Boot Actuator (для мониторинга и управления приложением)

Thymeleaf (для создания HTML страниц)

Spring Mail (для отправки email сообщений)

### Шаги для настройки проекта в Spring Initializr:

Перейдите на сайт Spring Initializr.

Настройки проекта:


Project: Maven Project

Language: Java

Spring Boot: 2.6.6 (или любая актуальная версия)

Group: de.leafgrow

Artifact: project

Name: LeafGrow.de

Description: MWP website backend for daily plant care tips

Package name: de.leafgrow.project

Packaging: Jar

Java: 11

Выберите зависимости:


Spring Boot DevTools (опционально)

Spring Web

Spring Security

Spring Data JPA

Liquibase

PostgreSQL Driver

Spring Boot Actuator

Thymeleaf

Spring Mail

Нажмите на кнопку "Generate", чтобы скачать проект. Распакуйте архив в удобное для вас место.

### Дополнительные настройки
application-dev.yml




### Инструкция по созданию проекта MWP Spring Boot "LeafGrow.de"

1. **Создать проект MWP Spring Boot с именем LeafGrow.de**:
   - Использовать Spring Initializr для создания базового проекта.

2. **Основные требования**:
   - Backend на фреймворке Spring для MWP сайта. Фронтенд будет разрабатываться другой командой.
   - Использовать Spring, Hibernate, Liquibase, Maven, PostgreSQL. Для авторизации использовать JWT токены.
   - Не использовать библиотеку `javax`, использовать `jakarta`. (Учили именно джакарту)

3. **Архитектура кода**:
   - Каждый класс и интерфейс распределить по соответствующим пакетам.

4. **Тема проекта**:
   - Ежедневные советы по выращиванию растений в домашних условиях.
   - Ограничение на выращивание максимально трех растений в трех горшках.
   - Связать в базе данных пользователя с ролями и горшками.

### Основные данные:

1. **Функции backend**:
   - Регистрация, логин, добавление горшков для растений, ежедневные советы, удаление пользователя, обновление пользователя, удаление горшков и обновление горшков, email при регистрации и новости на email.

2. **Роли пользователей**:
   - ROLE_CUSTOMER, ROLE_ADMIN.

3. **Данные в базе данных**:
   - Горшок (Pot), Пользователь (User), Роли (Role), Инструкция (Instruction).

4. **API эндпоинты**:
   - Создание нового пользователя (name, password, email, roles): `POST /api/register`
   - Аутентификация пользователя (email, password): `POST /api/login`
   - Добавление горшка: `POST /api/pots`
   - Получение горшков пользователя: `GET /api/pots`
   - Удаление горшка: `DELETE /api/pots/{potId}`
   - Получение ежедневных советов: `GET /api/tips`
   - Подтверждение электронной почты (token): `GET /confirm-email`
   - Повторное письмо с подтверждением на электронную почту (email): `POST /resend-confirmation`
   - Выйти/завершить сессию пользователя: `POST /logout`
   - Изменяем пароль пользователя (old_password, new_password): `PUT /change-password`
   - Удаляем аккаунт пользователя (email): `DELETE /delete-account`
   - Активируем горшок: `PUT /pots?id=*/activate`

5. **Архитектура бэкенда**:
   - Классическая MVP через реализацию Spring Boot.

6. **Безопасность и авторизация**:
   - Аутентификация и авторизация через JWT токены.

7. **Конфигурационные файлы**:
   - `application-dev.yml` для настроек окружения разработки.

### User Story for Backend:

1. **Регистрация и авторизация**:
   - Регистрация новых пользователей с выдачей JWT токена доступа.
   - Логин существующих пользователей для получения JWT токена доступа.

2. **Управление горшками для растений**:
   - Добавление горшков с информацией о растениях.
   - Получение списка горшков пользователя.
   - Удаление горшков пользователя.
   - Обновление горшков (только ADMIN).
   - Обновление пользователей (только ADMIN).

3. **Ежедневные советы по выращиванию растений**:
   - Получение ежедневных советов по выращиванию растений в личном кабинете на сайте.

4. **Уведомления**:
   - Отправка email при регистрации нового пользователя.

### Сущности и связи в базе данных:

1. **Пользователь (User)**:
   - id
   - Имя
   - Email
   - Пароль (зашифрованный)
   - Роль (связь с таблицей ролей)
   - Список горшков (связь с таблицей горшков)

2. **Роль (Role)**:
   - id
   - Название роли

3. **Горшок (Pot)**:
   - id
   - Название растения
   - Описание
   - Ссылка на пользователя
   - Статус активации

4. **Инструкция (Instruction)**:
   - id
   - Текст инструкции

### Эндпоинты API:

1. **Пользователь**:
   - Создание нового пользователя: `POST /register`
   - Подтверждение электронной почты: `GET /confirm-email`
   - Повторное письмо с подтверждением: `POST /resend-confirmation`
   - Аутентификация пользователя: `POST /login`
   - Завершение сессии пользователя: `POST /logout`
   - Изменение пароля пользователя: `PUT /change-password`
   - Удаление аккаунта пользователя: `DELETE /delete-account`

2. **Горшки**:
   - Создание нового горшка для пользователя: `POST /pots`
   - Активирование горшка: `PUT /pots/{id}/activate`
   - Обновление горшка: `PUT /pots/{id}`
   - Удаление горшка: `DELETE /pots/{id}`
   - Получение списка горшков: `GET /pots`

3. **Советы**:
   - Получение ежедневных советов: `GET /tips`

### Пакеты:

```plaintext
de.leafgrow.project
|-- config            // Конфигурации Spring
|-- entity            // Классы сущностей
|-- controller        // Контроллеры для обработки HTTP запросов
|-- service           // Сервисы для бизнес-логики приложения
|-- repository        // Репозитории для взаимодействия с базой данных
|-- dto               // Data Transfer Objects для передачи данных между слоями
|-- security          // Классы для обеспечения безопасности и аутентификации
|-- util              // Утилитарные классы
|-- exception         // Обработка исключений
```

### Структура проекта

```plaintext
src/main/java/de/leafgrow/project/
|-- config
|-- controller
|-- domain
	|-- entity
	|-- dto
|-- exception	
|-- repository
|-- security
|-- service
|-- util
|-- Application.java
src/main/resources/
|-- templates
|   |-- register.html
|   |-- login.html
|   |-- pots.html
|-- application-dev.yml
|-- db/changelog/db.changelog-master.xml
```


### Требования к безопасности и авторизации:

- Аутентификация и авторизация через JWT токены.

### Пример архитектуры базы данных:

```plaintext
-- USERS TABLE
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INTEGER REFERENCES roles(id)
);

-- ROLES TABLE
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- POTS TABLE
CREATE TABLE pots (
    id SERIAL PRIMARY KEY,
    plant_name VARCHAR(255) NOT NULL,
    description TEXT,
    user_id INTEGER REFERENCES users(id),
    active BOOLEAN DEFAULT TRUE
);

-- INSTRUCTIONS TABLE
CREATE TABLE instructions (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL
);
```

### Основные зависимости Maven (pom.xml):

```xml
<dependencies>
    <!-- Spring Boot Dependencies -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>

    <!-- JWT Dependencies -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
    </dependency>

    <!-- Database Dependencies -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Hibernate Dependencies -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
    </dependency>

    <!-- Liquibase Dependencies -->
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>

    <!-- Jakarta Dependencies -->
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.0.0</version>
    </dependency>

    <!-- Other Dependencies -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### Пример конфигурационного файла application-dev.yml:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/leafgrow
    username: your_db_username
    password: your_db_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

  mail:
    host: smtp.example.com
    port: 587
    username: your_email@example.com
    password: your_email_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

### Дополнительная информация для запуска проекта:

1. **Сконфигурируйте базу данных PostgreSQL**:
   - Создайте базу данных `leafgrow`.
   - Обновите параметры подключения в `application-dev.yml`.

2. **Запуск проекта**:
   - Импортируйте проект в вашу IDE.
   - Убедитесь, что у вас установлен JDK 11 или выше.
   - Запустите проект как Spring Boot Application.

3. **Отладка проекта (Debugging)**:
   - Установите точки останова (breakpoints) в коде.
   - Запустите проект в режиме отладки через вашу IDE.

Эта инструкция должна помочь вам создать и запустить проект MWP Spring Boot с именем LeafGrow.de, удовлетворяющий всем указанным требованиям. Если у вас возникнут дополнительные вопросы или проблемы, не стесняйтесь спрашивать.