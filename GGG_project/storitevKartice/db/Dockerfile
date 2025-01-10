FROM postgres:latest

ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=passwd
ENV POSTGRES_DB=kartice_database


COPY ../entitete-kartice/src/main/resources/sql-scripts/init-db.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432
