# MastersDiploma

## Installation
1. Make sure that you have Java 11 JDK and PostgreSQL installed.
2. Clone this project.
3. Create a separate database in PostgreSQL.
4. Find "script.sql" file in "\src\main\resources\sql" folder, open it as SQL script and run it to create tables and sequences for the database.
5. You can also add test values for the database, using "test_data.sql" file which is located in "\src\main\resources\sql" folder.
6. You should also change a few database configuration fields in Java application properties located in "src/main/resources/application.properties":
- spring.datasource.username = (your PostgreSQL instance username)
- spring.datasource.password = (your PostgreSQL instance password)
5. Now you can run Java project. The main class is located in "src/main/java/com/nulp/shymoniak/mastersproject/MastersProjectApplication.java".
6. You can also use Postman requests collection, after importing it from "src/main/resources/postman/Masters(postman_collection).json" to test the application.