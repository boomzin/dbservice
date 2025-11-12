### Development Environment

1. JDK 21
2. PostgreSql
3. Docker

* Склонируйте проект. 
````
  git clone git@github.com:boomzin/dbservice.git
````
* Перед первым запуском создайте папку для файлов бд командой:
````
  cd dbservice && \
  mkdir -pv ./docker/pgdata
````
* Запустите контенеры:
````
docker compose up -d --force-recreate
````
После завершения запуска у вас останется два контейнера: контейнер с postgresql и контейнер с приложением.
Бд будет доступна на стандартном порту:
````
jdbc:postgresql://localhost:5432/db_service
````
Учетная запись для подключения:
login:    boomzin
password: boomzin

Приложение будет отвечать на запросы напрямую на порту 8090:
````
curl --location '127.0.0.1:8090/api/rest/...
````
Также можно запустить параллельно еще один экземпляр приложения в IDE, оно подключиться к бд в контейнере и будет отвечать на запросы на порту 8080:
````
curl --location '127.0.0.1:8080/api/rest/...
````

Остановить контейнеры:
````
docker compose down -v
````
Если нужно обнулить значения в бд нужно остановить контейнеры и пересоздать каталог с файлами бд:
````
  sudo rm -rf ./docker/pgdata && \
  mkdir -pv ./docker/pgdata
````

### Схема резервирования товара при движении склад-корзина-заказ-покупатель

|  |  | initial state | user put 10 pcs to cart | user confirm this cart | After confirming the cart, an order will be automatically created | user paid this order | manager close order |
|---|---|---|-------------------------|---|---|---|---|
| stock |  | 100 | 100                     | 100 | 100 | 100 | 90 |
| cart status | NEW |  | 10                      |  |  |  |  |
|  | CONFIRMED |  |                         | 10 |  |  |  |
|  | ARCHIVED |  |                         |  | 10 | 10 | 10 |
| order status | ACTIVE |  |                         |  | 10 |  |  |
|  | PAID |  |                         |  |  | 10 |  |
|  | DONE |  |                         |  |  |  | 10 |
|  | EXPIRED |  |                         |  |  |  |  |
|  | CANCELLED |  |                         |  |  |  |  |
|  | PENDING |  |                         |  |  |  |  |
|  | SUSPENDED |  |                         |  |  |  |  |
| reserved |  | 0 | 0                       | 10 | 10 | 10 | 0 |
| available |  | 100 | 100                     | 90 | 90 | 90 | 90 |