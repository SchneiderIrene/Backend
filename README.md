## Настройка проекта для деплоя на DO

* Необходимо обеспечить наличие Environment Variables:
    * `DB_HOST`
    * `DB_PORT`
    * `DB_NAME`
    * `DB_USERNAME`
    * `DB_PASSWORD`

```text
DB_HOST=${leafgrow_project.HOSTNAME}
DB_PORT=${leafgrow_project.PORT}
DB_NAME=${leafgrow_project.NAME}
DB_USERNAME=${leafgrow_project.USERNAME}
DB_PASSWORD=${leafgrow_project.PASSWORD}
```