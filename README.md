This service is the CRUD of people application.

To run it locally:

- DownLoad Mongodb image:
```docker pull mongo```

- Create Mongodb container:
```docker-compose up --no-start mongodb```

- Start container if not started already:
```docker-compose start```

- Run project with local profile:
```mvn spring-boot:run```

- Access documentation by Swagger:
```http://localhost:9005/swagger-ui.html#/```