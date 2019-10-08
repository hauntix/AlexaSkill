## Prerequisites 
- have an [amazon developer account](https://developer.amazon.com/)
- an account on [amazon AWS](https://aws.amazon.com/free/)
- Maven installed on your computer
    - MacOS: ```brew install maven```
    - Windows: [download](http://apache.mirrors.ionfish.org/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.zip) 
        - extract the maven.zip files to C:/Program Files/Maven
        - then set up the environment variables
        ### adding the environment variables
        1. Windows 10 - Search for **Environment Variables** then select **Edit the system environment variables**
        2. click the ** Environment Variables** button
        3. under **System Variables** click **New**
        4. in **Variable Name** type: `JAVA_HOME` 
        5. in **Variable Value** type your jdk install path: `C:\Progra~1\Java\jdk1.8.0_221` ('Progra~1' replaces 
        'Program Files')
        6. click ok
        7. Now to add Maven so repeat steps c - f but for **Variable Name** type `MAVEN_HOME` and for 
        **Variable value** type `C:\Progra~1\Maven\apache-maven-3.6.2`
        #### Now to add these vairables to our path
        9. click __Path__ found in system variables and then click edit
        10. Click new and type `%JAVA_HOME%\bin`
        11. Click new and type `%MAVEN_HOME%\bin`
        12. Click ok and then click ok again
        #### Verify that you added the paths correctly
        1. open terminal and enter `java -version` output should look similar to this:
        // todo add image
        2. next enter `mvn -v` the output will look similar to this:
        // todo add image
        
# Instructions
- Create a new maven project in intellij, Go to File -> New -> Project and select Maven
- click next and go through setting up your project name 
    - enter any package name and name for your project
- Once your project is set up and created add the sdk to alexa sdk's to your pom.xml dependencies like so:
```
    <dependencies>
       <dependency>
           <groupId>com.amazon.alexa</groupId>
           <artifactId>ask-sdk</artifactId>
           <version>2.20.2</version>
       </dependency>
       <dependency>
           <groupId>com.amazon.alexa</groupId>
           <artifactId>ask-sdk-lambda-support</artifactId>
           <version>2.20.2</version>
       </dependency>
       <dependency>
           <groupId>com.amazon.alexa</groupId>
           <artifactId>ask-sdk-lambda-support</artifactId>
           <version>2.20.2</version>
       </dependency>
    </dependencies>
```
- [example pom.xml]()

Alexa skills have two things that handle stuff in Alexa, One being handlers and the other a SteamHandler class that 
calls our handlers so we need to make two packages to hold these files like so:
```
    com.example.handlers
    com.main
```
next you will want to make a new class inside your handler package and name it "**CancelandStopIntentHandler**" and add this
 block of code
```
    package com.example.handler;import java.util.Optional;
    import com.amazon.ask.dispatcher.request.handler.HandlerInput;
    import com.amazon.ask.dispatcher.request.handler.RequestHandler;
    import static com.amazon.ask.request.Predicates.intentName;
    import com.amazon.ask.model.Response;
    public class CancelandStopIntentHandler implements RequestHandler {
     
        public boolean canHandle(HandlerInput input) {
            return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
        }

        public Optional<Response> handle(HandlerInput input) {
            String speechText = "Bye Bye";
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("HelloWorld", speechText)
                    .build();
        }
    }
```

