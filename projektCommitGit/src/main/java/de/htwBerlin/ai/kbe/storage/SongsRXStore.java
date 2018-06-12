package de.htwBerlin.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.Song;

/**
 * Diese Klasse stellt per Singleton-Pattern unsere In-Memory-DB zur Verfuegung
 * 
 *
 */
public class SongsRXStore {
	volatile private static Map<Integer, Song> songStore = null;
	private final static String SONGFILENAME = "songs.json";
	private static SongsRXStore SINGLETON = null;

	/**
	 * Privater Konstruktor
	 * @throws JsonParseException falls eine Parse-Exception geworfen wird
	 * @throws JsonMappingException falls eine MappingException geworfen wird
	 * @throws IOException falls eine IOException geworfen wird
	 */
	private SongsRXStore() throws JsonParseException, JsonMappingException, IOException {
		this(SONGFILENAME);
	}

	/**
	 * Privater parameterisierter Kontstruktor
	 * @param songFileName der Name der Song Datei
	 * @throws JsonParseException falls eine Parse-Exception geworfen wird
	 * @throws JsonMappingException falls eine MappingException geworfen wird
	 * @throws IOException falls eine IOException geworfen wird
	 */
	private SongsRXStore(String songFileName) throws JsonParseException, JsonMappingException, IOException {
		InputStream url = SongsRXStore.class.getClassLoader().getResourceAsStream(songFileName);

		List<Song> songsList = (List<Song>) new ObjectMapper().readValue(url, new TypeReference<List<Song>>() {
		});

		songStore = new ConcurrentHashMap<>();

		for (Song song : songsList) {
			songStore.put(song.getId(), song);
		}
	}

	/**
	 * Gibt die Singletoninstanz zurueck
	 * @return die Singletoninstanz
	 */
	synchronized public static SongsRXStore getInstance() {
		return getInstance(SONGFILENAME);
	}

	/**
	 * gibt die Singletoninstanz zurueck
	 * @param songFileName der Name der Song Datei
	 * @return die Singletoninstanz
	 */
	synchronized public static SongsRXStore getInstance(String songFileName) {
		try {
			return SINGLETON == null ? SINGLETON = new SongsRXStore(songFileName) : SINGLETON;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Gibt alle Songs zurueck
	 * @return alle Songs
	 */
	synchronized public Collection<Song> getAllSongs() {
		return songStore.values();
	}

	/**
	 * Gibt einen Song anhand einer id zurueck
	 * @param id die id des Songes
	 * @return den Song mit der id
	 */
	synchronized public Song getSongById(Integer id) {
		return songStore.get(id);
	}

	/**
	 * fuegt einen neuen Song ein
	 * @param song der Song
	 * @return die Id des neuen Songes
	 */
	synchronized public Integer addNewSong(Song song) {
		Integer newId = getFreeId();
		
		if(song==null || song.getTitle() == null)
			newId = null;
		
		if (newId != null && !songStore.containsValue(song)&& !songStore.containsKey(newId)) {
			
			
			
			System.out.println("AddNewSong");
			songStore.put(newId, song);
			song.setId(newId);
		}
		return newId;
	}

	/**
	 * Updated einen Song
	 * @param song der Song zum updaten
	 * @return true wenne rfolgreich geupdated
	 */
	/**
	 * Updated einen Song
	 * @param id die id des Songes
	 * @param song der Song zum updaten
	 * @return true wenne rfolgreich geupdated
	 */
	synchronized public boolean updateSong(Integer id, Song song) {
		if(song == null || id == null || !songStore.containsKey(id))
			return false;
		
		return updateSong(song);
	}

	/**
	 * Updated einen Song
	 * @param song der Song zum updaten
	 * @return true wenne rfolgreich geupdated
	 */
	synchronized public boolean updateSong(Song song) {
		if (
				songStore == null || 
				song == null || 
				song.getId() == null || 
				!songStore.containsKey(song.getId()) || 
				song.getArtist() == null)
			return false;

		songStore.put(song.getId(), song);
		return true;
	}

	/**
	 * Loescht einen Song
	 * @param id die id des Songes zum loeschen
	 * @return true wenn erfolgreich geloescht
	 */
	synchronized public boolean deleteSong(Integer id) {
		if (songStore == null)
			return false;
		
		return deleteSong(songStore.get(id));
	}

	/**
	 * Loescht einen Song
	 * @param song der Song zum loeschen
	 * @return true wenn erfolgreich geloescht
	 */
	synchronized public boolean deleteSong(Song song) {
		if (songStore == null || song ==null || song.getId() == null || !songStore.containsKey(song.getId()))
			return false;

		songStore.remove(song.getId());
		return true;
	}

	/**
	 * Gibt eine freie unbenutze id zurueck
	 * @return eine freie unbenutze id
	 */
	synchronized public Integer getFreeId() {
		for (int i = 1; i <= Integer.MAX_VALUE; i++)
			if (!songStore.containsKey(i))
				return i;
		
		return null;
	}
}