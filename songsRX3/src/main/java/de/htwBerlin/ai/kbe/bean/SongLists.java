package de.htwBerlin.ai.kbe.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlists")
@Entity
@Table(name = "SongLists")
public class SongLists {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer songlistsId;
	private boolean isPublic;
	

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "SongSongListNMRelation", joinColumns = {
			@JoinColumn(name = "songId") }, inverseJoinColumns = { @JoinColumn(name = "songlistsId") })
	private List<Song> songs;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name ="ownerID")
	private User user;

	public SongLists() {
	}


	public SongLists(Integer ownerID, boolean isPublic,List<Song> songs, User user) {
		this.isPublic = isPublic;
		this.user = user;
		this.songs = songs;
	}

	public Integer getSongListsId() {
		return songlistsId;
	}

	public void setSongListsId(Integer id) {
		this.songlistsId = id;
	}


	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
