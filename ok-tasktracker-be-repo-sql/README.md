# Launch postgresql container manually
```shell
docker run \
--name some-postgres \
-e POSTGRES_PASSWORD=tasktracker-pass \
-e POSTGRES_USER=postgres \
-e POSTGRES_DB=tasktrackerdevdb \
-p 5432:5432 \
-d postgres
```
