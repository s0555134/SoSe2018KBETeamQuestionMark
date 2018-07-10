package de.htwBerlin.ai.kbe.bean;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
@Entity
@Table(name = "Song")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer songID;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "songs")
	private Set<SongLists> songlists;

	public Song() {
	}

	public Integer getId() {
		return songID;
	}

	public void setId(Integer id) {
		this.songID = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}

	public void setSonglists(Set<SongLists> songlists) {
		this.songlists = songlists;
	}

	@Override
	public String toString() {
		return "Song [id=" + songID + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
				+ released + "]";
	}

	public Set<SongLists> getSonglists() {
		return songlists;
	}

}
