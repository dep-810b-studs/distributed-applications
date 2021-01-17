# distributed-applications
## Учебный проект по курсу "Разработка распределенных приложений"

## Задача - разработать прототип распределенного приложения для бронирования комнат (квартир, апартаментов, ...)
## Ключевые сущности:
- клиент (id, имя)
- комната (id, адрес, описание, набор атрибутов)
- бронь (id комнаты, id клиента, даты бронирования, статус бронирования).
## Сценарии:
- Бронирование комнаты. Клиент после выбора комнаты и дат бронирования, нажимает "бронировать", бронь сохраняется в MongoDB, комната блокируется в Hazelcast. После оплаты (символически), статус брони меняется на "оплачено", комната разблокируется в Hazelcast.
- Поиск свободных комнат (Elastic Search). Поиск по описанию, адресу и датам бронирования. Для удобства поиска по датам, при сохранении брони в MongoDB в ElasticSearch можно индексировать свободные даты.
## План работы:
1. Подготовка:
- Собрать мини-команду и выбрать один из предлагаемых проектов
- Совместно обсудить постановку задачи, задать вопросы преподавателю
- Совместно разработать модель предметной области в коде в виде классов
- Завести репозиторий в GitHub или BitBucket и закоммитить туда код модели
- Договориться о распределении задач между участниками, ввести задачи в трекер GitHub или BitBucket
2. Разработка:
- Заполнить тестовыми данными MongoDB (Клиент, комната)
- Разработать класс-сервис бронирования
- Расширить сервис бронирования индексированием данных в ElasticSearch
- Разработать веб-страницу для бронирования
- Разработать веб-страницу для поиска комнат
3. Интеграция и тестирование
- Подготовить данные (порядка десятков тысяч клиентов, десятков тысяч комнат, миллионов броней) Комнаты можно взять отсюда http://insideairbnb.com/get-the-data.html.  http://data.insideairbnb.com/united-kingdom/england/london/2017-03-04/data/listings.csv.gz содержит более 140 000 записей. Клиентов - пользователей stackoverflo,  брони - сгенерировать.
- Функциональное тестирование и исправление ошибок
- Протестировать поведение системы при отключении одного узла
## Трекер задач:
https://clck.ru/SpwfN