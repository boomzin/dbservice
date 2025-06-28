#!/bin/bash

set -e

/flyway/flyway -placeholderPrefix='#[' -placeholderSuffix=']' -user=boomzin -password=boomzin -url=jdbc:postgresql://postgres:5432/db_service -schemas=db_service -locations=filesystem:/flyway/sql/dbservice migrate