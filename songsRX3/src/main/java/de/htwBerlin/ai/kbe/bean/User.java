package de.htwBerlin.ai.kbe.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@Entity
@Table(name = "User")
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String lastName;
	private String firstName;
	@Id
	private String userID;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<SongLists> songLists;

	public User() {
	}

	public User(Integer ID, String userID, String lastName, String firstName) {
		this.id = ID;
		this.userID = userID;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getUserId() {
		return userID;
	}

	public void setUserId(String userID) {
		this.userID = userID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<SongLists> getSonglists() {
		if (songLists == null) {
			songLists = new ArrayList<SongLists>();
		}
		return songLists;
	}

	public void setSonglists(List<SongLists> songlists) {
		this.songLists = songlists;
		// Works for JSON, but not for XML
		if (songlists != null) {
			this.songLists.forEach(a -> a.setUser(this));
		}
	}

	@Override
	public String toString() {
		return "User [id=" + userID + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

}
