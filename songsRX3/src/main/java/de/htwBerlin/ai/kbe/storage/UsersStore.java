package de.htwBerlin.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.User;

import java.util.Map;

public class UsersStore {

	// This class implement Singleton design patterns

	private static Map<String, User> storage;
	private static UsersStore instance = null;
	private final String SONGFILENAME = "user.json";

	private List<User> userList = new ArrayList<User>();

	private UsersStore() {
		try {
			initializeUserStore(SONGFILENAME);
		} catch (IOException e) {
			System.out.println("Can't create Instance....");
			e.printStackTrace();
		}
	}

	public synchronized static UsersStore getInstance() {
		if (instance == null) {
			instance = new UsersStore();
		}
		return instance;
	}

	private void initializeUserStore(String songFilename) throws IOException {

		if (songFilename == null || songFilename.equals("")) {
			songFilename = "user.json";
		}
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);

		userList = new ObjectMapper().readValue(input, new TypeReference<ArrayList<User>>() {
		});

		storage = new ConcurrentHashMap<String, User>();
		
		userList.stream().forEach(entry -> storage.put(entry.getUserId(), entry));
		
		
//		for (int index = 0; index > userList.size(); index++) {
//			storage.put(userList.get(index).getUserId(), userList.get(index));
//		}
		// currentID = new AtomicInteger(Collections.max(storage.keySet()));

	}

	public User getUser(String userId) {
		System.out.println("@@@@@@@@@@@@@@@@@"+ storage.get(userId));
		return storage.get(userId);
	}

	public Collection<User> getAllSongs() {
		return storage.values();
	}

}
