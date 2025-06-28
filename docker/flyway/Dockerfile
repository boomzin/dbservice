FROM flyway/flyway:latest-alpine
LABEL authors="Ernest Aidinov"

CMD ["/bin/sh"]

COPY ./docker/flyway/entrypoint.sh /
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
