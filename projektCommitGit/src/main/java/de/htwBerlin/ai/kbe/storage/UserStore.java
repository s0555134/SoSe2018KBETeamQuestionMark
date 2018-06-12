package de.htwBerlin.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.User;

public class UserStore {
	volatile private static Map<String, User> userStore = null;
	private final static String USER_FILE_NAME = "user.json";
	private static UserStore SINGLETON = null;

	/**
	 * Privater Konstruktor
	 * 
	 * @throws JsonParseException
	 *             falls eine Parse-Exception geworfen wird
	 * @throws JsonMappingException
	 *             falls eine MappingException geworfen wird
	 * @throws IOException
	 *             falls eine IOException geworfen wird
	 *             
	 */
	private UserStore() throws JsonParseException, JsonMappingException, IOException {
		this(USER_FILE_NAME);
	}

	/**
	 * Privater parameterisierter Kontstruktor
	 * 
	 * @param userFileName
	 *            der Name der Song Datei
	 * @throws JsonParseException
	 *             falls eine Parse-Exception geworfen wird
	 * @throws JsonMappingException
	 *             falls eine MappingException geworfen wird
	 * @throws IOException
	 *             falls eine IOException geworfen wird
	 */
	private UserStore(String userFileName) throws JsonParseException, JsonMappingException, IOException {
		InputStream url = SongsRXStore.class.getClassLoader().getResourceAsStream(userFileName);

		List<User> userList = (List<User>) new ObjectMapper().readValue(url, new TypeReference<List<User>>() {
		});

		userStore = new ConcurrentHashMap<>();

		for (User user : userList) {
			userStore.put(user.getUserId(), user);
		}
	}

	/**
	 * Gibt die Singletoninstanz zurueck
	 * 
	 * @return die Singletoninstanz
	 */
	synchronized public static UserStore getInstance() {
		
		
		return getInstance(USER_FILE_NAME);
	}

	/**
	 * gibt die Singletoninstanz zurueck
	 * 
	 * @param userFileName
	 *            der Name der User Datei
	 * @return die Singletoninstanz
	 */
	synchronized public static UserStore getInstance(String userFileName) {
		try {
			return SINGLETON == null ? SINGLETON = new UserStore(userFileName) : SINGLETON;
		} catch (IOException e) {
			
			return null;
		}
	}

	/**
	 * gibt alle User zurueck
	 * 
	 * @return alle User
	 */
	synchronized public Collection<User> getAllUser() {
		return userStore.values();
	}

	/**
	 * Gibt einen User anhand einer id zurueck
	 * 
	 * @param id
	 *            die id des User
	 * @return den User mit der id
	 */
	synchronized public User getUserById(String id) {
		return userStore.get(id);
	}

	/**
	 * fuegt einen neuen User ein
	 * 
	 * @param user
	 *            der User
	 * @return die Id des neuen User
	 */
	synchronized public String addNewUser(User user) {
		String newId = getFreeId();

		if (user == null || user.getFirstName() == null)
			newId = null;

		if (newId != null) {
			userStore.put(newId, user);
			user.setUserId(newId);
		}
		return newId;
	}

	/**
	 * Updated einen User
	 * 
	 * @param song
	 *            der User zum updaten
	 * @return true wenn erfolgreich geupdated
	 */
	synchronized public boolean updateUser(Integer id, User user) {
		if (user == null || id == null || !userStore.containsKey(id))
			return false;

		return updateUser(user);
	}

	/**
	 * Updated einen User
	 * 
	 * @param user
	 *            der User zum updaten
	 * @return true wenn erfolgreich geupdated
	 */
	synchronized public boolean updateUser(User user) {
		if (userStore == null || user == null || user.getId() == null || !userStore.containsKey(user.getId()))
			return false;

		userStore.put(user.getUserId(), user);
		return true;
	}

	/**
	 * Loescht einen User
	 * 
	 * @param id
	 *            die id des User zum loeschen
	 * @return true wenn erfolgreich geloescht
	 */
	synchronized public boolean deleteUser(Integer id) {
		if (userStore == null)
			return false;

		return deleteUser(userStore.get(id));
	}

	/**
	 * Loescht einen User
	 * 
	 * @param user
	 *            der User zum loeschen
	 * @return true wenn erfolgreich geloescht
	 */
	synchronized public boolean deleteUser(User user) {
		if (userStore == null || user == null || user.getId() == null || !userStore.containsKey(user.getId()))
			return false;

		userStore.remove(user.getId());
		return true;
	}

	/**
	 * Gibt eine freie unbenutze id zurueck
	 * 
	 * @return eine freie unbenutze id
	 */
	synchronized public String getFreeId() {
		for (int i = 0; i <= Integer.MAX_VALUE; i++)
			if (!userStore.containsKey(i))
				return String.valueOf(i);

		return null;
	}
}
