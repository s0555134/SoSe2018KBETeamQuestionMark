package de.htwBerlin.ai.kbe.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.Song;

import java.util.Map;

public class SongsStore {

	// This class implement Singleton design patterns

	private static Map<Integer, Song> storage;
	private static SongsStore instance = null;

	private final String SONGFILENAME = "songs.json";

	private AtomicInteger currentID = null;
	private ObjectMapper mapper = new ObjectMapper();

	private List<Song> songList = new ArrayList<Song>();

	private SongsStore() {
		try {
			initializeSongStore(SONGFILENAME);
		} catch (IOException e) {
			System.out.println("Can't create Instance....");
			e.printStackTrace();

		}
	}

	public synchronized static SongsStore getInstance() {
		if (instance == null) {
			instance = new SongsStore();
		}
		return instance;
	}

	private void initializeSongStore(String songFilename) throws IOException {

		if (songFilename == null || songFilename.equals("")) {
			songFilename = "songs.json";
		}
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);

		songList = new ObjectMapper().readValue(input, new TypeReference<ArrayList<Song>>() {
		});

		storage = new ConcurrentHashMap<Integer, Song>();

		songList.stream().sorted((entry_1, entry_2) -> entry_1.getId().compareTo(entry_2.getId()))
				.forEach(entry -> storage.put(entry.getId(), entry));

		currentID = new AtomicInteger(Collections.max(storage.keySet()));

	}

	public Song getSong(Integer id) {
		return storage.get(id);
	}

	public Collection<Song> getAllSongs() {
		return storage.values();
	}

	public Integer addSong(Song song) {
		song.setId(currentID.incrementAndGet());
		storage.put(song.getId(), song);
		return song.getId();
	}

	// returns true (success), when contact exists in map and was updated
	// returns false, when contact does not exist in map
	public boolean updateSong(Song song, Integer id) {

		if (storage.get(id) != null) {
			song.setId(id);
			storage.replace(id, song);
			return true;
		}
		return false;
	}

	// returns deleted song
	public Song deleteSong(Integer id) {
		Song song = storage.get(id);
		if (song != null) {
			storage.remove(id);
			// saveInFile();
			return song;
		} else {
			return song;
		}
	}

	public void saveInFile() {
		String path = this.getClass().getClassLoader().getResource("songs.json").getPath();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(path), storage);
		} catch (JsonGenerationException e) {
			System.out.println("Fehler" + e);
		} catch (JsonMappingException e) {
			System.out.println("Fehler" + e);
		} catch (FileNotFoundException e) {
			System.out.println("Fehler" + e);
		} catch (IOException e) {
			System.out.println("Fehler" + e);
		}
	}

}
