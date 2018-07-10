#! /bin/sh

echo "--- REQUESTING ALL JSON SONGS ------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING ALL XML SONGS--------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 28 IN XML--------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/28"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 37 IN JSON--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"
