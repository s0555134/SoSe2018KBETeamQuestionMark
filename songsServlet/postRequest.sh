#! /bin/sh
#
# test.json ist eine Test-Datei, deren Inhalt als Payload an den Server geschickt wird
#

curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: test/plain" \
     -d "@test.json" \
     -v "http://localhost:8080/songWebStore"

echo "-------------------------------------"
