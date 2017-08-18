# imdb-title-service

Run Instructions:


Code Structure:
I could think of two possible way to load the data into the system. 
  1. MySQL Load File command mapping each file as a separate table (Quick and Easiest way to load data)
     However, due to some parameters being stored as the comma separated values, it would be hard to answer queries like, 
     give me all titles for a principal cast with name = ?
  2. That is why i chose to write a basic ETL (Extract, Transform, Load ) process to insert the data into my sql table,
  where i store the comma separated values as normalized table (e.g. title_id, cast_id) for titles.principals.tsv file. 
  
1. Data Model:
