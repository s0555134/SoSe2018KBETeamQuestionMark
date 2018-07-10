package de.htwBerlin.ai.kbe.DBStorage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.bean.Song;

public interface ISongsDAO {

	/**
	 * Retrieves a Song
	 * 
	 * @param id
	 * @return
	 */
	public Song findSongById(Integer id);

	/**
	 * Retrieves all Songs
	 * 
	 * @return
	 */
	public Collection<Song> findAllSongs();

	/**
	 * Save a new Song
	 * 
	 * @param Song
	 * @return the Id of the new Songs
	 */
	public Integer saveSong(Song Song);

	/**
	 * Deletes the Song for the provided id
	 * 
	 * @param id
	 */
	public boolean deleteSong(Integer id);

	boolean findSongWithSongName(String song);
}
