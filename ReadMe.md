#### Порядок развертывания проекта и запуска автотестов.
1. Скачать проект из репозитория Github локально с помощью команды 
-  git clone https://github.com/Boarderbare/AQA_diplom.git
2. Запустить контейнер Docker с БД PostgreSQL с помощью файла docker-compose.yml командой
- docker-compose up
3. Запустить тестируемое приложение "Покупка тура" командой
- java -jar .\artifacts\aqa-shop.jar
4. Запустить сами автотесты командой
- .\gradlew clean test

