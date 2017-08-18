# imdb-title-service

Project Structure:
  The project has two main components. 
   1. BATCH JOB : to responsible to insert data into the mysql database. 
        Everything with java package: com.netflix.exercise.batch will have classes related to insert job.  For Load we could have simply use the mysql load from file using commands like "LOAD DATA LOCAL INFILE '/Users/anand/exercise/title.basics.tsv' into table imdb_movies.title_basic;" however, because some fileds are comma separated values which would be hard to join otherwise, I decided to write my own ETL (Extract, Transform, Load) component. With this, I can map titles.principas.tsv data to  title_id, cast_id relation ship. This will allow me to answer queries like give all titles for cast id = ? 
        
   2. REST Service: to provide read api to retrieve data back from the database.
        Every class other than batch framework is for the reader service. Reader uses hibernate & spring boot to retrieve the data back over http service.
        
        API Provided:
        * http://localhost:8080/titles?year=2017&type=movie  (All movies by year and type)
        * http://localhost:8080/titles?year=2017&type=movie&genre=Comedy  (Optional genre to filter further on type and movie)
        * http://localhost:8080/titles/id/tt0020172   (All movies for given title id)
        * http://localhost:8080/titles/cast/nm0000300  (All titles where given name_id was primaryCast on the title)
   
   
Run Instructions:
In order to run and build the service, it will need maven and jdk 8 installed on the box. the project war file can be build with mvn install command.

Database details:

* server: test-mysql.cfqdpi5eyjla.us-east-1.rds.amazonaws.com
* port: 3306
* username: imdb_movies_op2
* password: imdb_movies_op2
