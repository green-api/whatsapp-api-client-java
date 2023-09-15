# GREEN API Java Client EN

This library is an example of how you can interact with the GREEN API
using the java programming language and the Spring framework.

## Installation

1. Download the JAR file.
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

1. Use a ready-made bean that the client builds according to the parameters in the yml file. To do this, specify the parameters of your instance in application.yml in the following form:

```
green-api:
   host: https://api.green-api.com
   hostMedia: https://media.green-api.com
   instanceId: {{yourInstance}}
   token: {{yourToken}}
```

Create RestTemplate bean with your configuration, for example:

```
@Bean
public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
return restTemplateBuilder.build();
}
```

And add "com.greenapi" in @ComponentScan base packages:

```
@SpringBootApplication
@ComponentScan(basePackages = {"com.greenapi", "com.example"})
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
    }
}
```

Now you can inject the WhatsApp client instance into any part of your application.

```
@Autowired
private GreenApiClient greenApiClient;
```

Another way to use yours configuration, for example:

```
@Configuration
public class GreenApiConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public GreenApiClient greenApiClient(RestTemplate restTemplate) {
        return new GreenApiClient(
            restTemplate,
            "https://media.greenapi.com",
            "https://api.greenapi.com",
            "{{YOUR-ID}}",
            "{{YOUR-TOKEN}}");
    }
}
```

2. Use a constructor to create a new instances, if your application simultaneously manages several of it:

```
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
```

You can use various GREEN API methods to receive and send data
on WhatsApp:

```
var response = greenApiClient.service.checkWhatsapp({{phoneNumber}});
log.info(response);
```

The methods are divided into groups just like in the documentation for ease of use.

Example of creating group and sending message - [link](https://github.com/green-api/whatsapp-api-client-java/blob/master/src/main/java/com/greenapi/examples/Examples.java):

```
public void createGroupAndSendMessage(GreenApiClient greenApiClient, ArrayList<String> chatIds) {

        var groupResponse = greenApiClient.groups.createGroup(
            CreateGroupReq.builder()
                .groupName("Example Group")
                .chatIds(chatIds)
                .build());

        if (groupResponse.getStatusCode().is2xxSuccessful()) {
            var messageResponse = greenApiClient.sending.sendMessage(
                OutgoingMessage.builder()
                    .chatId(groupResponse.getBody().getChatId())
                    .message("Hola a todos")
                    .build());

            if (messageResponse.getStatusCode().is2xxSuccessful()) {
                log.info(
                    "\nCreate group: " + groupResponse.getBody().isCreated() +
                    "\nSent message id: " + messageResponse.getBody().getIdMessage()
                );
            } else {
                log.warn("Couldn't send a message. Status code: " + messageResponse.getStatusCode());
            }
            
        } else {
            log.warn("Couldn't create a group. Status code: " + groupResponse.getStatusCode());
        }
    }
```
