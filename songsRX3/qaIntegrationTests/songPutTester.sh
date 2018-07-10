#! /bin/sh

echo "--- PUTTING A SONG ------------------"
curl -X PUT \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@updatedSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs/9"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- PUTTING A SONG ------------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@updatedSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs/10"
echo " "
echo "-------------------------------------------------------------------------------------------------"
