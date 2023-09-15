# GREEN API Java Client

Данная библиотека является примером того, как можно взаимодействовать с GREEN API
с помощью языка программирования java и фреймворка Spring.

## Установка

1. Скачайте JAR файл нужной версии.
2. Включите JAR файл в ваш проект вручную, добавив его в папку с библиотеками (обычно lib или libs) 
   и настройте вашу среду разработки (например, IntelliJ IDEA) для включения этого JAR файла в путь проекта:

    Откройте ваш проект в IntelliJ IDEA.
    Перейдите в меню "File" -> "Project Structure".
    В разделе "Modules" выберите ваш модуль.
    Перейдите во вкладку "Dependencies".
    Нажмите на кнопку "+" и выберите "JARs or directories".
    Выберите скачанный JAR файл и добавьте его.

Теперь вы можете использовать классы и функции из библиотеки в коде вашего проекта. Импортируйте соответствующие пакеты и начните использовать библиотеку.

## Использование

Для начала работы вам понадобится создать инстанс класса GreenApiClient.
Так как вы используете Spring, вы можете сделать это двумя способами:
1. Использовать готовый bean который:
   Для этого укажите параметры своего инстанса в application.yml в таком виде:

    green-api:
    host: https://api.green-api.com
    hostMedia: https://media.green-api.com
    instanceId: 1101848922
    token: ${GAPI_TOKEN}