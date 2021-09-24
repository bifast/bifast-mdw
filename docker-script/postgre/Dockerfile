FROM postgres:12.8
ENV POSTGRES_PASSWORD=postgres 
ENV POSTGRES_DB=postgres
ENV POSTGRES_DB=komidb
COPY setupdb.sql /docker-entrypoint-initdb.d/
VOLUME /var/lib/postgresql/data

# docker build -t fransmzh/komi-db:1.4 .
# docker push fransmzh/komi-db:1.4