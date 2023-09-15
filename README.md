# GREEN API Java Client EN

This library is an example of how you can interact with the GREEN API
using the java programming language and the Spring framework.

## Installation

1. Download the JAR file of the required version.
2. Include the JAR file in your project manually by adding it to your libraries folder (usually lib or libs)
   and configure your development environment (eg IntelliJ IDEA) to include this JAR file in the project path:

    - Open your project in IntelliJ IDEA.
    - Go to menu "File" -> "Project Structure".
    - In the "Modules" section, select your module.
    - Go to the "Dependencies" tab.
    - Click on the "+" button and select "JARs or directories".
    - Select the downloaded JAR file and add it.

3. Now you can use classes and functions from the library in your project code. Import the appropriate packages and start using the library.

## Usage

To get started, you will need to create an object of the GreenApiClient class.
Since you are using Spring, you can do this in two ways:
1. Use a ready-made bean that is a street client according to the parameters in the yml file.
   To do this, specify the parameters of your instance in application.yml in the following form:


     green-api:
         host: https://api.green-api.com
         hostMedia: https://media.green-api.com
         instanceId: {{yourInstance}}
         token: {{yourToken}}

Create RestTemplate bean:


      @Bean
      public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
      return restTemplateBuilder.build();
      }

Now you can then inject the client into any part of your application using @Autowired.

     @Autowired
     private GreenApiClient greenApiClient;

2. Use a constructor, you can write your own factory if your application simultaneously manages several instances:


     var restTemplate = new RestTemplateBuilder().build();

     var greenApiClient1 = new GreenApiClient(
         restTemplate,
         "https://media.green-api.com",
         "https://api.green-api.com",
         {{instanceId1}},
         {{instanceToken1}});

     var greenApiClient2 = new GreenApiClient(
         restTemplate,
         "https://media.greenapi.com",
         "https://api.greenapi.com",
         {{instanceId2}},
         {{instanceToken2}});


You can use various GREEN API methods to receive and send data
on WhatsApp:


     var response = greenApiClient.service.checkWhatsapp({{phoneNumber}});
     log.info(response);

The methods are divided into groups just like in the documentation for ease of use.

# GREEN API Java Client RU

Данная библиотека является примером того, как можно взаимодействовать с GREEN API
с помощью языка программирования java и фреймворка Spring.

## Установка

1. Скачайте JAR файл нужной версии.
2. Включите JAR файл в ваш проект вручную, добавив его в папку с библиотеками (обычно lib или libs) 
   и настройте вашу среду разработки (например, IntelliJ IDEA) для включения этого JAR файла в путь проекта:

   - Откройте ваш проект в IntelliJ IDEA. 
   - Перейдите в меню "File" -> "Project Structure".
   - В разделе "Modules" выберите ваш модуль.
   - Перейдите во вкладку "Dependencies".
   - Нажмите на кнопку "+" и выберите "JARs or directories".
   - Выберите скачанный JAR файл и добавьте его.

3. Теперь вы можете использовать классы и функции из библиотеки в коде вашего проекта. Импортируйте соответствующие пакеты и начните использовать библиотеку.

## Использование

Для начала работы вам понадобится создать объект класса GreenApiClient.
Так как вы используете Spring, вы можете сделать это двумя способами:
1. Использовать готовый bean который стрит клиент по параметрам в yml файле.
    Для этого укажите параметры своего инстанса в application.yml в таком виде:


    green-api:
        host: https://api.green-api.com
        hostMedia: https://media.green-api.com
        instanceId: {{yourInstance}}
        token: {{yourToken}}

   Создайте свой RestTemplate bean:


      @Bean
      public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
      return restTemplateBuilder.build();
      }

   После этого вы можете внедрить клиент в любую часть вашего приложения:

    @Autowired
    private GreenApiClient greenApiClient;

2. Использовать конструктор, вы можете написать свой factory, если ваше приложение одновременно управляет несколькими инстансами:


    var restTemplate = new RestTemplateBuilder().build();

    var greenApiClient1 = new GreenApiClient(
        restTemplate,
        "https://media.green-api.com",
        "https://api.green-api.com",
        {{instanceId1}},
        {{instanceToken1}});

    var greenApiClient2 = new GreenApiClient(
        restTemplate,
        "https://media.greenapi.com",
        "https://api.greenapi.com",
        {{instanceId2}},
        {{instanceToken2}});


Вы можете использовать различные методы GREEN API чтобы получать и отправлять данные
в WhatsApp:


    var response = greenApiClient.service.checkWhatsapp({{phoneNumber}});
    log.info(response);

Методы разделены по группам так же как в документации для удобства использования.