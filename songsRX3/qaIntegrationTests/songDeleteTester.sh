#! /bin/sh

echo "--- DELETING SONG 28 --------"
curl -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/songs/28"
echo " "
echo "-------------------------------------------------------------------------------------------------"
