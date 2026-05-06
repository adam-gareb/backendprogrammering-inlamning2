#!/bin/bash
echo "Kör nu Client..."
if [ -f database.dat.properties ]; then
  rm database.dat.properties
fi
if [ -f database.dat.script ]; then
  rm database.dat.script
fi
mvn -q compile
mvn -q exec:java -Dexec.mainClass="se.yrgo.client.SimpleClient"
