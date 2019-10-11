## Prerequisites 
- have an [amazon developer account](https://developer.amazon.com/)
- an account on [amazon AWS](https://aws.amazon.com/free/)
- Maven installed on your computer
    - MacOS: ```brew install maven```
    - Windows: [download](http://apache.mirrors.ionfish.org/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.zip) 
        - extract the maven.zip files to C:/Program Files/Maven
        - then set up the environment variables
        #### adding the environment variables
        1. Windows 10 - Search for **Environment Variables** then select **Edit the system environment variables**
            ![alt text](https://github.com/hauntix/AlexaSkill/raw/master/assets/images/environment_variables.png)
        2. click the ** Environment Variables** button
            ![alt text](https://github.com/hauntix/AlexaSkill/raw/master/assets/images/environment_variables_1.png)
        3. under **System Variables** click **New**
        4. in **Variable Name** type: `JAVA_HOME` 
        5. in **Variable Value** type your jdk install path: `C:\Progra~1\Java\jdk1.8.0_221` ('Progra~1' replaces 
        'Program Files')
            ![alt text](https://github.com/hauntix/AlexaSkill/raw/master/assets/images/adding_new_var.png)
        6. click ok
        7. Now to add Maven so repeat steps c - f but for **Variable Name** type `MAVEN_HOME` and for 
        **Variable value** type `C:\Progra~1\Maven\apache-maven-3.6.2`
        #### Now to add these vairables to our path
        9. click __Path__ found in system variables and then click edit
            ![alt text](https://github.com/hauntix/AlexaSkill/raw/master/assets/images/environment_variable.png)
        10. Click new and type `%JAVA_HOME%\bin`
        11. Click new and type `%MAVEN_HOME%\bin`
        12. Click ok and then click ok again
        #### Verify that you added the paths correctly
        1. open terminal and enter `java -version` output should look similar to this:
        // todo add image
        2. next enter `mvn -v` the output will look similar to this:
        // todo add image
- Have your Alexa skill id ready
    - to get your Amazon Skill Id, go to [Amazon Skill Kit Dashboard](https://developer.amazon.com/alexa/console/ask?). 
    Now create a new skill, you can name it whatever you want like "MyFirstAlexaSkill"
    - In the next page select the custom model and click create skill.
    - In the left side menu, click on “EndPoint” and then select “AWS Lambda ARN”. It will open a lots of field, 
    from there you can see your skill id. Copy this down in notepad for later.
        
# Instructions
1. Create a new maven project in intellij, Go to File -> New -> Project and select Maven
2. click next and go through setting up your project name 
    - enter any package name and name for your project
    
3. Once your project is set up and created add the sdk to alexa sdk's to your pom.xml dependencies like so:
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
    - [example pom.xml](https://github.com/hauntix/AlexaSkill/blob/master/pom.xml)

4. Alexa skills have two things that handle stuff in Alexa, One being handlers and the other a SteamHandler class that 
calls our handlers so we need to make two packages to hold these files like so:
    ```
        com.example.handlers
        com.main
    ```
5. make a new class inside handler package and name it "**CancelandStopIntentHandler**" and add the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/example/handlers/CancelandStopIntentHandler.java)
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

6. Make a new class inside handlers package called "**FallbackIntentHandler**" and add the 
[following]()
    ```
        package com.example.handlers;
        
        import com.amazon.ask.dispatcher.request.handler.HandlerInput;
        import com.amazon.ask.dispatcher.request.handler.RequestHandler;
        import com.amazon.ask.model.Response;
        
        import static com.amazon.ask.request.Predicates.intentName;
        
        import java.util.Optional;
        
        public class FallbackIntentHandler implements RequestHandler {
        	@Override
        	public boolean canHandle(HandlerInput handlerInput) {
        		return handlerInput.matches(intentName("AMAZON.FallbackIntent"));
        	}
        
        	@Override
        	public Optional<Response> handle(HandlerInput handlerInput) {
        		String speechTxt = "Sorry buddy, I don't know that. You can say try saying help tho!";
        		return handlerInput.getResponseBuilder()
        				.withSpeech(speechTxt)
        				.withSimpleCard("HelloWorld", speechTxt)
        				.withReprompt(speechTxt)
        				.build();
        	}
        }
    ```

7. Make a new class inside handlers package called "**HelpIntentHandler**" and add the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/example/handlers/HelpIntentHandler.java)
    ```
        package com.example.handlers;
    
        import java.util.Optional;
        import com.amazon.ask.dispatcher.request.handler.HandlerInput;
        import com.amazon.ask.dispatcher.request.handler.RequestHandler;
        import static com.amazon.ask.request.Predicates.intentName;
        import com.amazon.ask.model.Response;
    
        public class HelpIntentHandler implements RequestHandler {
         
            public boolean canHandle(HandlerInput input) {
                return input.matches(intentName("AMAZON.HelpIntent"));
            }
    
            public Optional<Response> handle(HandlerInput input) {
                String speechText = "I am here to say Hello World to You";
                return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .withSimpleCard("HelloWorld", speechText)
                        .withReprompt(speechText)
                        .build();
            }
        }
    ```
8. Make a new class inside handlers package called "**LaunchRequestHandler**" and add the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/example/handlers/LaunchRequestHandler.java)
    ```
        package com.example.handlers;
        
        import java.util.Optional;
        import com.amazon.ask.dispatcher.request.handler.HandlerInput;
        import com.amazon.ask.dispatcher.request.handler.RequestHandler;
        import com.amazon.ask.model.LaunchRequest;
        import com.amazon.ask.model.Response;
        import static com.amazon.ask.request.Predicates.requestType;
    
        public class LaunchRequestHandler implements RequestHandler  {
         
            public boolean canHandle(HandlerInput input) {
                return input.matches(requestType(LaunchRequest.class));
            }
    
            public Optional<Response> handle(HandlerInput input) {
                String speechText = "Welcome to Hello World, You can say Hello";
                return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .withSimpleCard("HelloWorld", speechText)
                        .withReprompt(speechText)
                        .build();
            }
        }
    ```
9. Make a new class inside handlers package and name it "**SessionEndedRequestHandler**" and add the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/example/handlers/SessionEndedRequestHandler.java)
    ```
        package com.example.handlers;
    
        import java.util.Optional;
        import com.amazon.ask.dispatcher.request.handler.HandlerInput;
        import com.amazon.ask.dispatcher.request.handler.RequestHandler;
        import com.amazon.ask.model.Response;
        import com.amazon.ask.model.SessionEndedRequest;
        import static com.amazon.ask.request.Predicates.requestType;
    
        public class SessionEndedRequestHandler implements RequestHandler  {
         
            public boolean canHandle(HandlerInput input) {
                return input.matches(requestType(SessionEndedRequest.class));
            }
    
            public Optional<Response> handle(HandlerInput input) {
                return input.getResponseBuilder().build();
            }
        }
    ```
10. Now we will make a class that says our message to the user. Create a "**HelloWorldIntentHandler**" class in the 
handlers package and fill it with the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/example/handlers/HelloWorldIntentHandler.java):
    ```
        package com.example.handlers;
    
        import java.util.Optional;
        import com.amazon.ask.dispatcher.request.handler.HandlerInput;
        import com.amazon.ask.dispatcher.request.handler.RequestHandler;
        import com.amazon.ask.model.Response;
        import static com.amazon.ask.request.Predicates.intentName;
    
        public class HelloWorldIntentHandler implements RequestHandler {
         
            public boolean canHandle(HandlerInput input) {
                return input.matches(intentName("HelloWorldIntent"));
            }
            
            public Optional<Response> handle(HandlerInput input) {
               String speechText = "I am alive Hello World";
               return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .withSimpleCard("HelloWorld", speechText)
                        .build();
            }
        }
    ```

11. Now we will make our final class "**HelloWorldStreamHandler**" inside the com.main package and add the 
[following](https://github.com/hauntix/AlexaSkill/blob/master/src/main/java/com/main/HelloWorldStreamHandler.java)
    ```
        package com.main;import com.amazon.ask.Skill;
        import com.amazon.ask.SkillStreamHandler;
        import com.amazon.ask.Skills;
        import com.example.handler.CancelandStopIntentHandler;
        import com.example.handler.HelloWorldIntentHandler;
        import com.example.handler.HelpIntentHandler;
        import com.example.handler.LaunchRequestHandler;
        import com.example.handler.SessionEndedRequestHandler;public class HelloWorldStreamHandler extends SkillStreamHandler {
         
            private static Skill getSkill() {
                //                  Replace this with your alexa skill id
                String skillID = "Replace this with your alexa skill id";
                
                return Skills.standard()
                        .addRequestHandlers(
                                new CancelandStopIntentHandler(),
                                new HelloWorldIntentHandler(),
                                new HelpIntentHandler(),
                                new LaunchRequestHandler(),
                                new SessionEndedRequestHandler())
                        .withSkillId(skillID)
                        .build();
            }public HelloWorldStreamHandler() {
                super(getSkill());
            }
        }
    ```
12. In **HelloWorldStreamHandler** update the value of skillID with your Alexa skill id that you copy down earlier as 
part of the requisites

13. Open your terminal or Power Shell (For Windows) and locate the alexa skill project root directory. 
This is the directory where your “pom.xml” file is located. After getting into the directory thru terminal, run this 
command: 
    ```
        mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
    ```
    This command will compile our project into 2 jar files for us in the target folder of our project
    we will be using the named `HelloAlexa-0.0.1-SNAPSHOT-jar-with-dependencies`
    
14. Go to [AWS Lambda Console](https://console.aws.amazon.com/lambda/home?region=us-east-1#/create) Name your function 
whatever you want and set the runtime to java 8. Then click create function

15.  In the left side of Menu, where it says triggers, select “Alexa Skill Kit”. Now scroll down a bit, it will show a 
text field which will say, “Skill id”. Here, copy and paste the Alexa skill Id from earlier. After that in the upper 
right corner, click on save.

16.  Click on the middle box with the function name in the Lambda Management Console and it will show a box below which 
says “Function Code”. In this upload the .jar file which we created in Step 13. In the handler, we have to writethe 
path of our class which is the entry point in the program, that is `HelloWorldStreamHandler`. So add the following line 
in the handler box and click save:
    ```
        com.main.HelloWorldStreamHandler
    ```

17. After the save is complete, from the upper right corner copy the ARN of Lambda. Now go to the Alexa Skill Kit page, 
click **Endpoint** (found on the lefthand side) and add this ARN in the “Default Region” Box. After that click on 
“Save Endpoints”.

18. From the left side of menu, click on “Invocation”. Here you have to write skill invocation name which can be any 
thing. Here, I am writing the name as “HelloAlexa”. After that click on Save Model.

19. Again from the left side of the menu click on the blue **Add** button next to Intent. Then enter the intent name as 
"**HelloWorldIntent" and click **Create Custom Intent**

20. This opens a page for "**Sample Utterances**" here add the following lines and then click save model and then build 
model
    ```
        say hello world
        say i am alive
        say are you there
        say is it working
    ```
    
21. Once its done building click on the **Test** button and write "tell HelloAlexa to say is it working". 
    It should say "I am alive Hello World"

22. Celebrate that you made your first alexa skill!
