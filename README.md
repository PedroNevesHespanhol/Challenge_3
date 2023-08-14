# Challenge 3 <img align="center" alt="Pedro-java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg"><img align="center" alt="Pedro-spring" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg">

This challenge is based on create an application that asynchronously fetches posts from an external API, enriches them with comment data, and keeps a log of processing updates. The client will then be able to search for posts and the history of states through the API immediately.<br> <br>

# Technologies
To this project i used Java, SpringBoot, H2 database to save the data, OpenFeign to consume the external API, and ActiveMQ Artemis for asynchronously fetches the posts and enriches them with comments and history.<br> <br>

# Steps to run the application 

1 - Run the application with an IDE of your preference
<br>
2 - Make that by going to the Main class(ChallengeThreeUolApplication) in the challengethreeuol package
<br>
3 - When running the application, the h2 database will be created and according with the requests, the posts, comments and history will be automatically added in the tables, and with the GET request, the array with all posts, with comments and history will be show. <br> <br>

# Documentation
The documentation of the API can be visualized at http://localhost:8080/swagger-ui.html with the application running.

