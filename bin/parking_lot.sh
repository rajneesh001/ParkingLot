#!/usr/bin/env bash

arg1=$1

mvn clean install

## Permform some validation on input arguments


if [ -z "$1" ] ; then
        java -jar target/gojek-1.0.jar
        exit 1
else
	java -jar target/gojek-1.0.jar $arg1

fi