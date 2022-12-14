## План внедрения автоматизированного тестирования формы покупки тура.
### Перечень автоматизируемых сценариев.
Все сценарии можно разделить на две основные ветки: 
- Покупка. В форму вводятся данные карты.
- Получение кредита. Также вводятся данные карты. Поля идентичны, как и в первом случае.

Негативные сценарии проверки полей на невалидные значения автоматизируются только для формы "Покупка".
Также автоматизируются API тесты.

- Входные данные генерируются при помощи библиотеками Faker, LocalDate, генератором случайных чисел.
Также для проверки невалидных значений в полях используются специально подобранные символы.

Далее в каждом сценарии описаны только те данные, которые отличаются от валидных, указанных ниже.

Валидные данные:
- Номер карты: 16 цифр.
- Месяц: номер месяца состоит из двух цифр.
- Год окончания действия карты: состоит из двух цифр. Не более 5 лет с текущего года.
- Имя владельца:  имя составное (first name, last name). Локализация для имени владельца"EN").
- CVV код - 3 цифры.
Также в описании всех сценариев опускается первый перехода по кнопке в зависимости от вида покупки:

Для всех сценариев "Покупка" шаг:
- С главной страницы по кнопке **"купить"** переходим к форме заполнения. 

Для всех сценариев "Кредит"  шаг:
- С главной страницы по кнопке **"купить в кредит"** переходим к форме заполнения.

Последний шаг "отправка формы" по кнопке **"продолжить"** для всех сценариев одинаковый.

В API сценариях UI не используется.
### Позитивные сценарии.
#### Покупка.
1. Вариант с валидной картой. Покупка должна быть одобрена банком.
- Данные: Номер карты: 1111 2222 3333 4444.
- Ожидаемый результат: Нет сообщений об ошибках в полях, получено всплывающее сообщение *"операция одобрена банком"*.
В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *payment_id* записывается ID операции, в таблицу
payment_entity в поле *status* заносится значение "APPROVED" в поле transaction_id - тот же ID, что и в таблицу *order_entity*.
#### Кредит
2. Вариант с валидной картой (покупка должна быть одобрена банком).
- Данные: Номер карты: 1111 2222 3333 4444.
- Ожидаемый результат: Нет сообщений об ошибках в полях, получено всплывающее сообщение *"операция одобрена банком"*,
  В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *credit_id* записывается ID операции, в таблицу
  *credit_request_entity* в поле *status* заносится значение "APPROVED" в поле *bank_id* -тот же ID, что и в таблицу *order_entity*.
### Негативные сценарии
#### Покупка
3. Вариант с невалидной картой. Покупка должна быть отклонена банком.
- Данные: Номер карты: 5555 6666 7777 8888.
- Ожидаемый результат: Нет сообщений об ошибках в полях, получено всплывающее сообщение *"Ошибка. Банк отказал в проведении операции"*,
  В БД внесены данные об отказе в операции: в таблицу *order_entity* в поле *payment_id* записывается ID операции, в таблицу
  payment_entity в поле *status* заносится значение "DECLINED" в поле transaction_id -тот же ID, что и в таблицу *order_entity*.
#### Кредит
4. Вариант с невалидной картой (покупка должна быть отклонена банком).
- Данные: Номер карты: 5555 6666 7777 8888.
- Ожидаемый результат: Нет сообщений об ошибках в полях, получено всплывающее сообщение *"Ошибка. Банк отказал в проведении операции"*,
  В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *credit_id* записывается ID операции, в таблицу
  *credit_request_entity* в поле *status* заносится значение "DECLINED" в поле *bank_id* -тот же ID, что и в таблицу *order_entity*.

#### Далее все негативные сценарии только для формы "Покупка".
5. Номер карты с полным номером - 16 цифр, но невалидная с точки зрения банка. Покупка должна быть отклонена банком.
- Данные: валидные данные.
- Ожидаемый результат: Нет сообщений об ошибках в полях, получено всплывающее сообщение *"Ошибка. Банк отказал в проведении операции"*.
#### Тестирование полей на ввод невалидных значений
6. Невалидный формат номера карты - одна цифра "0". Валидный формат значения должен быть 16 цифр.
- Данные: В поле "номер карты" вводится "0".
- Ожидаемый результат: Сообщение "неверный формат" под полем "номер карты".
7. Невалидный формат месяца - значение "00". Валидный формат значения должен быть от 01-12 (двузначный).
- Данные: В поле "месяц" вводится значение "00".
- Ожидаемый результат: Сообщение "неверно указан срок действия карты" под полем "месяц".
8. Невалидный формат месяца - значение "13". Валидный формат значения должен быть от 01-12 (двузначный).
- Данные: В поле "месяц" вводится значение "13".
- Ожидаемый результат: Сообщение "неверно указан срок действия карты" под полем "месяц".
9. Невалидный формат месяца - значение "1". Валидный формат значения должен быть от 01-12 (двузначный).
- Данные: В поле "месяц" вводится значение "1".
- Ожидаемый результат: Сообщение "неверный формат" под полем "месяц".
10. Невалидный формат года - значение "1". Валидный формат значения должен быть не более 5 лет с текущей даты (двузначный).
- Данные: В поле "месяц" вводится значение "1".
- Ожидаемый результат: Сообщение "неверный формат" под полем "месяц".
11. Невалидный формат года - значение "30". 
- Данные: В поле "год" вводится значение "30". Валидный формат значения должен быть не более 5 лет с текущей даты (двузначный).
- Ожидаемый результат: Сообщение "неверно указан срок действия карты" под полем "год".
12. Невалидный формат года - значение "00". 
- Данные: В поле "год" вводится значение "00". Валидный формат значения должен быть не более 5 лет с текущей даты (двузначный).
- Ожидаемый результат: Сообщение "неверно указан срок действия карты" под полем "год".
13. Поля месяц и год заполняются значениями с прошедшей датой. Год указывается текущий (важно!), а месяц - любой из предыдущего.
  Например, если текущая дата месяц-год 09/22, вводимое значение, например 07/22.
- Данные: Значение месяца-любой предыдущий в текущем году, год - текущий.
- Ожидаемый результат: Сообщение "неверно указан срок действия карты" под полем "месяц".
14. Невалидный формат значения в поле "Владелец" - цифры. Валидное значение - имя двусоставное владельца (last name, first name) на латинице.
- Данные: В поле "владелец" вводится любые три цифры.
- Ожидаемый результат: Сообщение "неверный формат" под полем "владелец".
15. Невалидный формат значения в поле "Владелец" - имя на кириллице. Валидное значение - имя владельца двусоставное(last name, first name) на латинице.
- Данные: В поле "владелец" вводится любое имя на кириллице.
- Ожидаемый результат: Сообщение "неверный формат" под полем "владелец".
16. Невалидный формат значения в поле "Владелец" - состоит из одного имени. Валидное значение - имя владельца двусоставное (last name, first name) на латинице.
- Данные: В поле "владелец" вводится любое first name.
- Ожидаемый результат: Сообщение "неверный формат" под полем "владелец".
17. Невалидный формат значения в поле "Владелец" - содержит спецсимволы. Валидное значение - имя владельца двусоставное (last name, first name) на латинице.
- Данные: В поле "владелец" вводится значение состоящее из валидного имени (генерируется Faker)с добавлением спецсимвола вначале.
- Ожидаемый результат: Сообщение "неверный формат" под полем "владелец".
18.  Невалидный формат значения в поле "Код" - содержит одну цифру. Валидное значение - три цифры.
- Данные: В поле "СVC/СVV" вводится значение состоящее из одной цифры.
- Ожидаемый результат: Сообщение "неверный формат" под полем "СVC/СVV".
19. Сценарий проверки сообщений о некорректных значениях под полями. Сообщения должны пропадать после ввода валидных значений.
- Данные: Во все поля формы вводится *невалидное* значение состоящее из одной цифры.
- Отправка формы.
- Под каждым полем появляется сообщение "неверный формат"
- Во все поля формы вводятся *валидные* значения.
- Отправка формы.
- Ожидаемый результат: Нет сообщений об ошибках в полях. Получено всплывающее сообщение *"Ошибка. Банк отказал в проведении операции"*.
20. Поле "Номер карты" пустое - не заполнено.
- Данные: Поле не заполняется.
- Ожидаемый результат: Под полем "Номер карты" появляется сообщение "Поле обязательно для заполнения".
21. Поле "Месяц" пустое - не заполнено.
- Данные: Поле не заполняется.
- Ожидаемый результат: Под полем "Месяц" появляется сообщение "Поле обязательно для заполнения".
22. Поле "Год" пустое - не заполнено.
- Данные: Поле не заполняется.
- Ожидаемый результат: Под полем  "Год" появляется сообщение "Поле обязательно для заполнения".
23. Поле "Владелец" пустое - не заполнено.
- Данные: Поле не заполняется.
- Ожидаемый результат: Под полем "Владелец" появляется сообщение "Поле обязательно для заполнения".
24. Поле "СVC/СVV" пустое - не заполнено.
- Данные: Поле не заполняется.
- Ожидаемый результат: Под полем "СVC/СVV" появляется сообщение "Поле обязательно для заполнения".
### Тестирование API
#### Позитивные сценарии
#### Покупка
Входные данные кроме номера карты генерируются при помощи библиотеки Faker.
Имя владельца составное (first name, last name), локализация для имени владельца"EN"). CVV код - 3 цифры.
Год окончания действия карты - не более 5 лет с текущей даты. Месяц - номер месяца состоит из двух цифр.
Номер карты: 1111 2222 3333 4444.
21. Отправка POST запроса c валидными данными. Покупка должна быть одобрена. 
- Отправка POST запроса с валидными данными в формате JSON на адрес http://localhost:8080//api/v1/pay
- Ожидаемый результат: "StatusCode" ответа "200", "status" операции ""APPROVED".
В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *payment_id* записывается ID операции, в таблицу
payment_entity в поле *status* заносится значение "APPROVED" в поле transaction_id - тот же ID, что и в таблицу *order_entity*.
#### Кредит
22.  Отправка POST запроса c валидными данными. Покупка должна быть одобрена.
- Отправка POST запроса с валидными данными в формате JSON на адрес http://localhost:8080//api/v1/credit
- Ожидаемый результат: "StatusCode" ответа "200", "status" операции ""APPROVED".
  В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *credit_id* записывается ID операции, в таблицу
  *credit_request_entity* в поле *status* заносится значение "APPROVED" в поле *bank_id* -тот же ID, что и в таблицу *order_entity*.
#### Негативные сценарии
Входные данные кроме номера карты генерируются при помощи библиотеки Faker.
Имя владельца составное (first name, last name), локализация для имени владельца"EN"). CVV код - 3 цифры.
Год окончания действия карты - не более 5 лет с текущей даты. Месяц - номер месяца состоит из двух цифр.
Для варианта "покупка" отправка POST запросов с данными в формате JSON на адрес http://localhost:8080//api/v1/pay
Для варианта "покупка в кредит" отправка POST запроса с данными в формате JSON на адрес http://localhost:8080//api/v1/credit
#### Покупка
23. Отправка POST запроса данными невалидной карты. Покупка должна быть отклонена.
- Данные: Номер карты: 5555 6666 7777 8888.
- Ожидаемый результат: "StatusCode" ответа "200", "status" операции "DECLINED".
В БД внесены данные об отказе в операции: в таблицу *order_entity* в поле *payment_id* записывается ID операции, в таблицу
payment_entity в поле *status* заносится значение "DECLINED" в поле transaction_id -тот же ID, что и в таблицу *order_entity*.
#### Кредит
24. Отправка POST запроса данными невалидной карты. Покупка должна быть отклонена.
- Данные: Номер карты: 5555 6666 7777 8888.
- Ожидаемый результат: "StatusCode" ответа "200", "status" операции DECLINED"
  В БД внесены данные об одобрении операции: в таблицу *order_entity* в поле *credit_id* записывается ID операции, в таблицу
  *credit_request_entity* в поле *status* заносится значение "DECLINED" в поле *bank_id* -тот же ID, что и в таблицу *order_entity*.
#### Покупка
Далее идут негативные сценарии для проверки обработки данных бэкендом.
API запросы с невалидными данными в каждом из блоков данных (номер карты, месяц, год, владелец, код).
Поле или пустое или содержит один символ.
25. Отправка POST запроса без номера карты - поле пустое. 
- Данные: номер карты "".
- Ожидаемый результат: "StatusCode" ответа "400".
26. Отправка POST запроса с номером карты из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
27. Отправка POST запроса с пустым полем "месяц".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
28. Отправка POST запроса с полем "месяц" из одной цифры.
- Данные: месяц "1".
- Ожидаемый результат: "StatusCode" ответа "400".
29. Отправка POST запроса с пустым полем "год".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
30. Отправка POST запроса с полем "год" из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
31. Отправка POST запроса с пустым полем "владелец".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
32. Отправка POST запроса с полем "владелец" из одного символа.
- Данные: номер карты "A".
- Ожидаемый результат: "StatusCode" ответа "400".
33. Отправка POST запроса с пустым полем "код".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
34. Отправка POST запроса с полем "владелец" из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
#### Кредит
35. Отправка POST запроса без номера карты - поле пустое.
- Данные: номер карты "".
- Ожидаемый результат: "StatusCode" ответа "400".
36. Отправка POST запроса с номером карты из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
37. Отправка POST запроса с пустым полем "месяц".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
38. Отправка POST запроса с полем "месяц" из одной цифры.
- Данные: месяц "1".
- Ожидаемый результат: "StatusCode" ответа "400".
39. Отправка POST запроса с пустым полем "год".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
40. Отправка POST запроса с полем "год" из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
41. Отправка POST запроса с пустым полем "владелец".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
42. Отправка POST запроса с полем "владелец" из одного символа.
- Данные: номер карты "A".
- Ожидаемый результат: "StatusCode" ответа "400".
43. Отправка POST запроса с пустым полем "код".
- Данные: месяц  "".
- Ожидаемый результат: "StatusCode" ответа "400".
44. Отправка POST запроса с полем "владелец" из одной цифры.
- Данные: номер карты "1".
- Ожидаемый результат: "StatusCode" ответа "400".
#### Перечень используемых инструментов
- JDK 11. Код тестов на  Java.
- IntelliJ IDEA. Среда подготовки авто-тестов.
- JUnit Jupiter. Фреймворк для тестирования.
- Gradle. Система сборки кода.
- Selenide. Продвинутый плагин для тестирования веб-интерфейса.
- Lombok. Плагин для минимизации строк кода за счет аннотаций.
- Faker.  Плагин для генерации данных.
- Github. Как репозиторий для хранения кода.
- REST-assured. Плагин для тестирования API
– DBeaver. для запроса к БД
- Gradle. Инструмент для создания простых отчетов и анализа результатов авто-тестов.

### Перечень и описание возможных рисков при автоматизации
- Отсутствие требований к полям формы.
- Отсутствие спецификации к БД с инф. по таблицам, полям и значениям.
- Отсутствие спецификации по запросам REST API. 

### Интервальная оценка с учётом рисков (в часах)
Ориентировочно оценка внедрения автоматизации - 40 часов.

### План сдачи работ 
- автотесты - 06.08.2022
- результаты прогона -  07.08.22
- отчёт по автоматизации - 09.02.22
