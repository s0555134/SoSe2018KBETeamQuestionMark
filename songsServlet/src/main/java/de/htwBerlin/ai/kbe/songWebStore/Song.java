package de.htwBerlin.ai.kbe.songWebStore;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Song {

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;
	
	public Song () {	}
	
	public Song (Integer id, String title, String artist, String album, Integer released) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.released = released;
	}
	


	@XmlElement
	public Integer getId() {
		return id;
	}
	
	void setId(Integer id) {
		this.id = id;
	}
	

	@XmlAttribute
	public String getTitle() {
		return title;
	}
	@XmlElement
	public String getArtist() {
		return artist;
	}
	@XmlElement
	public String getAlbum() {
		return album;
	}
	@XmlElement
	public Integer getReleased() {
		return released;
	}

	

	
}
