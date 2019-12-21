# ascent-queue-service
Queue service project for Ascent HR

#Deployment

###If using postgreSql please uncomment below lines from application.yml

url: jdbc:postgresql://localhost:5432/postgres

driverClassName: org.postgresql.Driver

database-platform: org.hibernate.dialect.PostgreSQL94Dialect

# Packaging and Run
mvn clean package for packaging as jar

java - jar <jar file> to run the jar


# Rest API

### CreateQueue

/queues - method POST - request {"queueName", "size"}  

### UpdateQueue

/queues - method PUT - request {"queueName", "size"}  

### GetQueue

/queues?queueName= - method GET  

### DeleteQueue

/queues?queueName= - method DELETE  

### EnQueue

/queues/enqueue - method PUT - request {"queueName", "message"}  

### DeQueue

/queues/dequeue - method PUT - request {"queueName"}  

### Purge

/queues/purge - method PUT - request {"queueName"}  

### Peek

/queues/peek?queueName - method GET  
