# Camunda Example
In this project you will find an example camunda implementation, including keycloak and more.

First thing you must do in order to run this project is to build the docker image of camunda:

```
./gradlew bootBuildImage --imageName=camunda-demo
```

## Running 
Use docker-compose to start the containers
```
docker-compose up
```

