# syntax=docker/dockerfile:experimental
FROM postgres:13

COPY pg_healthcheck /usr/local/bin

RUN chmod +x /usr/local/bin/pg_healthcheck && \
    localedef --alias-file=/usr/share/locale/locale.alias --charmap=UTF-8 --force --inputfile=pt_BR pt_BR.UTF-8 && \
    ln --force --symbolic --verbose /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    echo 'America/Sao_Paulo' | tee /etc/timezone && \
    apt-get -qqy --fix-missing update && \
    apt-get -qqy --fix-missing install postgresql-contrib && \
    rm -rf /var/lib/apt/lists/*

ENV LANG pt_BR.utf8

HEALTHCHECK --interval=4s --start-period=6s CMD pg_healthcheck
