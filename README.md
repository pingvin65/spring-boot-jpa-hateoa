# spring-boot-jpa-hateoas

## Pagination  with Hateoas, Filtering & Sorting with Spring Boot and JPA 

## Spring Web
Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.

## Spring HATEOAS Web
Eases the creation of RESTful APIs that follow the HATEOAS principle when working with Spring / Spring MVC.

## H2 Database SQL
Provides a fast in-memory database that supports JDBC API and R2DBC access, with a small (2mb) footprint. Supports embedded and server modes as well as a browser based console application.

## Spring Data JPA SQL
Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.

For easy development, it is not needed for the project itself.

## Spring Boot DevTools Developer Tools
Provides fast application restarts, LiveReload, and configurations for enhanced development experience.


## Database Setup
### Database and Connectivity
For this application we are using an in memory H2 Database.
As this is a H2 DB no external installation needs to be done on the system running the application.
Once the application is running, it can be accessed using the web browser: http://localhost:8080/h2-console
Username: sa
Enter the above username, without any password and click connect.
### Raw Data Setup 
Using spring boot behaviour, there are 2 files in the resources folder
These 2 files will get auto executed each time the application starts up and needs no modification
1. schema.sql - this creates the required DB in our in memory H2 DB each time we launch our application.
   For this application, we are creating one table customer with 4 columns. 
2. data.sql - this contains the script to be run on our DB
   For this application, we are inserting  1000 rows of data into the customers table created in step 1. 


### Extra
The SQL table was generated on the web https://mockaroo.com/

https://springdoc.org/#Introduction


OpenApi 3 Spring Boot [springdoc.org](https://springdoc.org/#Introduction).

```
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.5.0</version>
   </dependency>
```

I used the tutorial from [https://dev.to/markbdsouza/paginationwith-hateoas-filtering-sorting-with-spring-boot-and-jpa-1mpp](https://dev.to/markbdsouza/paginationwith-hateoas-filtering-sorting-with-spring-boot-and-jpa-1mpp)  as a starting point



https://dev.to/markbdsouza/paginationwith-hateoas-filtering-sorting-with-spring-boot-and-jpa-1mpp
