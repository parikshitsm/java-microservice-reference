# java-microservices-reference

- This repository can be used as a reference to write well-designed, layered Spring Boot applications.
- This repository has a standard 3-tier application architecture which you can use to build any standard springboot-based microservice application. 
- Application developed in this repository also covers TDD approach using Mockito. 
- Lombok integration makes this application easy in case of code refactoring. 
- The repository is built based on an excellent book - Learn Microservices with Spring Boot. (Comprising first 3 chapters)

Steps to run this application : 
- Download and import the project in an IDE of your choice. 
- Right-click on the pom.xml and update the project. In case of IntelliJ, right click on pom.xml and select Reload project.
- Once done, run the SocialMultiplicationApplication.java
- Post successful startup of the application - open link : http://localhost:8080/index.html
- Perform multiplication operations & verify your results.
- In case want to verify database details open : http://localhost:8080/h2-console 