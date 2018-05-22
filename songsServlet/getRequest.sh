#! /bin/sh

curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?all" \
     -o out.json  # payload des Response in out.json speichern

echo "------------------------------------------"

curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?songId=6"

 echo = "----------------------------------------"