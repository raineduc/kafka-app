# kafka-app

Тестовое задание с использованием Apache Kafka.

В качестве БД используется PostgreSQL.

## Требования:
Необходимы **Docker** и **Docker Compose**

## Инструкция по запуску

* Склонировать репозиторий
```
    git clone git@github.com:raineduc/kafka-app.git
    cd kafka-app
```
* Запустить Kafka контейнер
```
    docker compose -f docker-compose-kafka.yml up kafka
```
* Запустить контейнер приложения
```
    docker compose up app 
```

## Параметры приложения
Параметры задаются в файле в формате:
```
    KEY1=VALUE1
    KEY2=VALUE2
    ...
```
(как в .properties файлах), файл задается с помощью аргумента *app_settings*, например:
```
    java -jar /build/app.jar --app_settings=./settings.properties
```
Для запуска в докере уже задан файл *settings.properties* в корне проекта,
туда внесены начальные данные для запуска в контейнере

### Существующие параметры:
* ```app_mode=<produce|consume>``` - запускает приложение в режиме отправителя или потребителя


* ```kafka_consumer_group-id``` - уникальное имя для группы Kafka потребителей
* ```kafka_topic``` - Имя Kafka топика
* ```kafka_producer_bootstrap-servers=<host1>:<port1>,<host2>:<port2>...``` -
  URL Kafka брокеров для клиента-отправителя
* ```kafka_consumer_bootstrap-servers=<host1>:<port1>,<host2>:<port2>...``` -
  URL Kafka брокеров для клиента-потребителя
* ```kafka_listener_idle-event-interval=<number>``` - время, прошедшее с момента прихода
последнего сообщения, после которого нужно завершить приложений


* ```db_url=jdbc:postgresql://$<host>:<port>/<db_name>``` - URL для подключения к БД PostgreSQL
* ```db_username``` - имя пользователя БД
* ```db_password``` - пароль к БД

* ```app_consumed_entity_table_name``` - имя таблицы пришедших сообщений
* ```app_produced_entity_table_name``` - имя таблицы отправленных сообщений 


* ```spring.flyway.enabled=<true|false>``` - Заполнить таблицы тестовыми данными (если они еще не были ими заполнены)


## Логи приложения
Подробные логи хранятся в папке ./logs в текущем рабочем каталоге (т.е. в корне проекта, если запускается через Docker)