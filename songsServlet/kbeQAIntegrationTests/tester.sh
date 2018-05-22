#! /bin/sh

echo "--- REQUESTING ALL SONGS ------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?all"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING SONG NUMBER 7 --------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?songId=7"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A SONG ------------------"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: test/plain" \
     -d "@einSong.json" \
     -v "http://localhost:8080/songWebStore"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING THE NEW SONG NUMBER --"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?songId=11"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING ANOTHER SONG ------------"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: test/plain" \
     -d "@einZweiterSong.json" \
     -v "http://localhost:8080/songWebStore"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- BAD USER REQUESTS ---------------"

echo "--- GET WITH NON-EXISTING ID ---------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?songId=22"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- GET WITH BAD ID ---------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?songId=notANumber"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- GET WITH NO PARAM ---------------"
curl -X GET \
     -H "Accept: application/json" \
     -v "http://localhost:8080/songWebStore?NO"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A NON-JSON LOVE SONG ----"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: test/plain" \
     -d "@notALoveSong.txt" \
     -v "http://localhost:8080/songWebStore"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING AN EMPTY FILE -----------"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: test/plain" \
     -d "@emptyFile.txt" \
     -v "http://localhost:8080/songWebStore"
echo = " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- SHUTTING DOWN TOMCAT -----------"
$CATALINA_HOME/bin/shutdown.sh

sleep 1

echo "NUMBER OF SONGS IN DB-FILE: "
cat $CATALINA_HOME/webapps/songWebStore/WEB-INF/classes/songs.json | grep \"id\" | wc -l