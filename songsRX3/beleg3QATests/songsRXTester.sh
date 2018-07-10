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

echo "--- REQUESTING NON-EXISTING SONG 11:--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/11"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING NON-EXISTING SONG 'OLD':--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/OLD"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A JSON SONG ------------------"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@einSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A XML SONG ------------------"
curl -X POST \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@einSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING NEW SONG 92 IN XML--------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/92"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING NEW SONG 93 IN JSON--------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/93"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING JSON-SONG 9 ------------------"
curl -X PUT \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@updatedSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs/9"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING UPDATED SONG 9 ------------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songsRX/rest/songs/9"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING XML-SONG 10 ------------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@updatedSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs/10"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING UPDATED SONG 10 ------------------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/10"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING NON-EXISTING SONG 22 WITH PAYLOAD SONG 10 -----------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@updatedSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs/22"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING UPDATED SONG 10: IT SHOULD NOT BE UPDATED ------------------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/10"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING UPDATED SONG 22: SHOULD GIVE 404 ------------------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/22"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING SONG 37 WITH PAYLOAD SONG 10 -----------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@updatedSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING UPDATED SONG 37: IT SHOULD BE LUKAS WITH ID 37 ------------------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- UPDATING SONG 37 WITH BAD PAYLOAD -----------------"
curl -X PUT \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@notALoveSong.txt" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG 37: IT SHOULD STILL BE LUKAS WITH ID 37 ------------------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs/37"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING SONG 28 --------"
curl -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/songs/28"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- DELETING SONG 28 AGAIN: SHOULD PRODUCE 404 --------"
curl -X DELETE \
     -H "Accept: text/plain" \
     -v "http://localhost:8080/songsRX/rest/songs/28"
echo " "
echo "-------------------------------------------------------------------------------------------------"

