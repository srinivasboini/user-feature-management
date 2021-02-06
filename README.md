# user-feature-management

Installation instructions
=========================
i) Checkout the project to your local machine

ii) open your terminal and exeucte below command to build the project

    ./mvnw clean install
    
iii) go to target directory and run below command to start the application

    java -jar feature-0.0.1-SNAPSHOT.jar
    

Application has 3 different profiles dev, test and prod. Default profile is "prod".

If you want to run the application in a different profile use below command.

    java -jar feature-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
    java -jar feature-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
    

Application has swagger integrated already.Use below url to view swagger documentation.

    http://localhost:9999/swagger-ui/index.html



