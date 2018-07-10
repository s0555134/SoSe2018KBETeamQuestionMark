package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.htwBerlin.ai.kbe.DBStorage.ISongsDAO;
import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.bean.User;


public class TestContactsDAO implements ISongsDAO {

    private static Map<Integer, Song> storage;

    public TestContactsDAO() {
        storage = new ConcurrentHashMap<Integer, Song>();
        init();
    }

    private static void init() {
        Song testerContact = new Song("Alfred", "Tester", "+4917612345678", "as@gmx.de");
        testerContact.setId(1);
        User testAddress = new User();
        testerContact.setTitle("Hello");
        storage.put(testerContact.getId(), testerContact);
    }

    public Song findSongById(Integer id) {
    	
        return storage.get(id);
    }

    @Override
    public Collection<Song> findAllSongs() {
        return storage.values();
    }

    @Override
    public Integer saveSong(Song contact) {
        contact.setId((int) storage.keySet().stream().count() + 1);
        storage.put(contact.getId(), contact);
        return contact.getId();
    }
    
    @Override
    public boolean deleteSong(Integer id) {
    	if(storage.remove(id) != null) {
        storage.remove(id);
    	return true;
    	}
    	else {
    		return false;
    	}
		
    }

	

	


	@Override
	public boolean findSongWithSongName(String song) {
		// TODO Auto-generated method stub
		return false;
	}
}
