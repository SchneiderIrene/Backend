## Настройка проекта для деплоя на DO

* Необходимо обеспечить наличие Environment Variables:
    * `DB_HOST`
    * `DB_PORT`
    * `DB_NAME`
    * `DB_USERNAME`
    * `DB_PASSWORD`
 
```text
DB_HOST=${leafgrowproject.HOSTNAME}
DB_PORT=${leafgrowproject.PORT}
DB_NAME=${leafgrowproject.NAME}
DB_USERNAME=${leafgrowproject.USERNAME}
DB_PASSWORD=${leafgrowproject.PASSWORD}
```