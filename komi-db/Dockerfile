FROM postgres:12.8
ENV POSTGRES_PASSWORD=postgres 
ENV POSTGRES_DB=postgres
ENV POSTGRES_DB=komidb

COPY dbobject.sql /docker-entrypoint-initdb.d/
COPY insertdata.sql /docker-entrypoint-initdb.d/

VOLUME /var/lib/postgresql/data

# docker build -t fransmzh/komi-db:1.7 .
# docker push fransmzh/komi-db:1.7
# docker-compose -f "docker-compose.yml" up -d --build komidb
# docker-compose -f "docker-compose.yml" up -d --no-build komidb

#COPY dbobject.sql /docker-entrypoint-initdb.d/
#COPY insertdata.sql /docker-entrypoint-initdb.d/
#COPY kktadmdbdev.sql /docker-entrypoint-initdb.d/
#COPY kktkomidbdev.sql /docker-entrypoint-initdb.d/
#COPY kktinsertadmmantap.sql /docker-entrypoint-initdb.d/


