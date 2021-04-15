# Vertica tutorial

This shows how to simply connect to Vertica via JDBC and export Vertica data into Hive table using Apache Spark

## Setting up

To make everything works you'd need:

### Setup Vertica in a Docker

To setup Vertica I used this image from [bryanherger](https://github.com/bryanherger/docker-vertica-10)

```
 docker run -p 5433:5433 -d \
           -v /your/data_path:/home/dbadmin/docker \
           bryanherger/vertica:10.0.0-0_centos-7
```

Then you need to connect with vsql to your Vertica DB. You can download vsql on your machine directly 
from Vertica [page](https://www.vertica.com/), but you also can grab it from an inside of a Docker

```
docker ps
docker exec -it VERTICA_CONTAINER_ID /bin/bash
cd /opt/vertica/
./bin/vsql -udbadmin
```

Then let's create a database 

```$sql
CREATE TABLE export_test
(
    event_ts timestamp NOT NULL,
    event_date date NOT NULL,
    game_id_str varchar(32) NOT NULL,
    clicks int,
    impressions int
)
PARTITION BY (export_test.event_date);
```

### Install libraries

To make all the parts of a tutorial work properly you need a few special libs:

- com.vertica.jdbc (available in Maven)
- com.hp.vertica.spark_connector (need to be installed on your local)

Switch to the ./libs/ folder and launch:

```$bash
mvn install:install-file \
   -Dfile=./vertica-spark2.1_scala2.11.jar \
   -DgroupId=com.hp.vertica.spark_connector \
   -DartifactId=vertica \
   -Dversion=spark2.1_scala2.11 \
   -Dpackaging=jar \
   -DgeneratePom=true
```

## Launching

Now we are ready to try out project in your IDE

- Run VerticaInserter.main() with args: amountOfRows, rowsDate
- Run VerticaExport to export data from Vertica via Spark.




