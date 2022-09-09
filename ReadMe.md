# Курсовой проект по модулю «Автоматизация тестирования»
Курсовой проект представляет собой автоматизацию тестирования сервиса покупки тура в путешествие.
После заполнения определенной формы сервис заносит информацию о покупке тура(одним из двух способов) в собственную БД
и через API отсылает данные банковским сервисам на обработку.
### Документы проекта
- [План автоматизации](docs/plan.md)
- [Отчет о тестировании](docs/report.md)
- [Итоги](docs/summary.md)
#### Порядок развертывания проекта и запуска автотестов.
На компьютере, где запускается проект, должно быть установлено ПО: Git, Java, Docker.
1. Скачать проект из репозитория Github локально с помощью команды
-  `git clone https://github.com/Boarderbare/AQA_diplom.git`
2. Запустить контейнер Docker с БД PostgreSQL с помощью файла docker-compose.yml командой
- `docker-compose up`
3. Запустить тестируемое приложение "Покупка тура" командой
- `java -jar .\artifacts\aqa-shop.jar`
4. Запустить сами автотесты командой
- `.\gradlew test "-Dselenide.headless=true"`
5. Для генерации отчета тестирования 
- `.\gradlew allurereport`
Лог выполнения команды покажет, где расположен отчет: `..\build\reports\allure-report\allureReport\index.html`
6. Остановить SUT 
- `CTRL+C`
7. Остановить работу контейнера Docker
-  `docker-compose down`
