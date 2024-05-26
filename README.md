1. Настройка среды
   Убедитесь, что у вас установлены следующие инструменты:

Java Development Kit (JDK) 11 или выше
Apache Maven
PostgreSQL (или другой поддерживаемый базой данных)
2. Настройка базы данных
   Запустите PostgreSQL и создайте базу данных:

sql
Копировать код
CREATE DATABASE leafgrow;
Настройте имя пользователя и пароль для базы данных, соответствующие конфигурации в application-dev.yml.

3. Запуск миграций Liquibase
   Liquibase автоматически выполнит миграции при запуске приложения, если настройки в application-dev.yml корректны.

4. Компиляция и запуск проекта
   Откройте терминал, перейдите в корневую директорию проекта и выполните команды:

bash
Копировать код
mvn clean install
mvn spring-boot:run
5. Настройка отладки (debug)
   Для отладки в вашей IDE (например, IntelliJ IDEA или Eclipse), выполните следующие шаги:

Откройте проект в IDE.

Настройте конфигурацию запуска:

В IntelliJ IDEA:
Откройте "Run/Debug Configurations".
Нажмите на кнопку "+" и выберите "Spring Boot".
Укажите главный класс Application (de.leafgrow.project.Application).
Убедитесь, что профиль dev активирован (если это нужно).
В Eclipse:
Перейдите в "Run" > "Debug Configurations".
Создайте новую конфигурацию "Spring Boot App".
Укажите главный класс Application (de.leafgrow.project.Application).
Запустите приложение в режиме отладки:

Нажмите на кнопку "Debug" (обычно выглядит как жук).
6. Тестирование
   Откройте браузер и перейдите на страницу регистрации:

bash
Копировать код
http://localhost:8080/register.html
Заполните форму и отправьте данные для регистрации. Повторите процесс для входа:

bash
Копировать код
http://localhost:8080/login.html
Используйте предоставленный JWT токен для тестирования эндпоинтов, связанных с горшками:

bash
Копировать код
http://localhost:8080/pots.html
7. Проверка логов и ошибок
   В режиме отладки вы можете установить точки останова (breakpoints) в коде и шаг за шагом отслеживать выполнение программы. Это поможет вам выявить и устранить любые ошибки.

8. Возможные проблемы и их решения
   Проблемы с подключением к базе данных:
   Убедитесь, что PostgreSQL запущен и доступен. Проверьте настройки в application-dev.yml.

Ошибки в Liquibase:
Проверьте файл db.changelog-master.xml на наличие синтаксических ошибок.

Ошибки компиляции:
Убедитесь, что все зависимости правильно указаны в pom.xml.

Полный проект
Вот полная структура проекта с учетом всех пакетов и классов:

lua
Копировать код
src/main/java/de/leafgrow/project/
|-- config
|   |-- SecurityConfig.java
|   |-- WebConfig.java
|-- controller
|   |-- AuthController.java
|   |-- PotController.java
|   |-- WebController.java
|-- service
|   |-- AuthService.java
|   |-- PotService.java
|   |-- UserService.java
|-- repository
|   |-- PotRepository.java
|   |-- UserRepository.java
|-- model
|   |-- Pot.java
|   |-- User.java
|   |-- Role.java
|-- dto
|   |-- UserRegistrationDto.java
|   |-- UserLoginDto.java
|   |-- PotDto.java
|-- security
|   |-- JwtAuthenticationEntryPoint.java
|   |-- JwtRequestFilter.java
|   |-- JwtTokenUtil.java
|-- util
|   |-- EmailUtil.java
|-- exception
|   |-- CustomExceptionHandler.java
|   |-- ResourceNotFoundException.java
|-- Application.java
HTML страницы
Положите HTML страницы в src/main/resources/templates/.


Для создания проекта с использованием Spring Initializr, вам нужно указать следующие настройки:

1. Основные параметры проекта
   Group: de.leafgrow
   Artifact: project
   Name: LeafGrow.de
   Description: MWP website backend for daily plant care tips
   Package name: de.leafgrow.project
   Packaging: Jar
   Java: 17 (или выше, если требуется)
2. Зависимости
   Выберите следующие зависимости:

Spring Boot DevTools (опционально для разработки)
Spring Web (для создания REST API)
Spring Security (для обеспечения безопасности)
Spring Data JPA (для работы с базой данных через Hibernate)
Liquibase (для управления миграциями базы данных)
PostgreSQL Driver (для подключения к PostgreSQL)
Spring Boot Actuator (для мониторинга и управления приложением)
Thymeleaf (для создания HTML страниц)
Spring Mail (для отправки email сообщений)
Шаги для настройки проекта в Spring Initializr:
Перейдите на сайт Spring Initializr.

Укажите настройки проекта:

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