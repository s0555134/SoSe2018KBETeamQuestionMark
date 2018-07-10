
cp -r  beleg4Tests/ songsRX/

Same procedure as usual:

cd songsRX

git log | less

git status

mvn clean package

deploy songsRX.war in Tomcat

cd beleg4Tests

Alle Skripte in diesem Verzeichnis muessen mit Argumenten aufgerufen werden.
Ein Skript-Aufruf ohne Argument, zeigt eine "Usage"-Message an. Zum Beispiel:

beleg4Tests-> ./getToken.sh 
    Illegal number of parameters
    Usage: ./getToken.sh userId
    Example: ./getToken.sh eschuler


TESTING "/auth" endpoint:

Einen Token fuer einen nicht-existierenden User holen:
./getToken.sh asdasda

Einen Token fuer einen Ihrer User holen:
./getToken.sh me


TESTING "/songs" endpoint: 
Der Song mit 'songId' wird aus der DB entfernt!

./songsTester.sh token songId


TESTING "/songLists" endpoint:

GET ALL song lists:
./GETALLsongListTester.sh token userIdForToken
./GETALLsongListTester.sh token otherUserId

GET A song list
./GETsongListTester.sh token userIdForToken songListId
./GETsongListTester.sh token userIdForToken non-existing-songId
./GETsongListTester.sh token otherUserId publicSongListId 
./GETsongListTester.sh token otherUserId privateSongListId

POST a song list: Sie sollten die folgenden Payloads in diesem Verzeichnis:
aSongList.xml, aSongList.json, aSongListBad.xml 
mit Ihren Payloads ueberschreiben. 
POSTsongListTester.sh schickt diese Payloads an Ihren Service.

./POSTsongListTester.sh token userIdForToken
./POSTsongListTester.sh token otherUserId

DELETE a song list: 
./DELETEsongListTester.sh token userIdForToken songListId
./DELETEsongListTester.sh token userIdForToken non-existing-songListId
./DELETEsongListTester.sh token otherUserId songListId

DELETE a song which is in a song list:
./deleteSong.sh token songIdInAList

cd ..
git add beleg4Tests/*
git commit -m "Test results" beleg4Tests/*
git push