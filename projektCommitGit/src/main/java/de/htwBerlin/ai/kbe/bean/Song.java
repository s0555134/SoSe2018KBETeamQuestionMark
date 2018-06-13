package de.htwBerlin.ai.kbe.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Stellt die POJO Klasse fuer unseren Webservice dar um Songs darzustellen
 * 
 *
 */
@XmlRootElement(name = "song")
public class Song {

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
				+ released + "]";
	}

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	/**
	 * Konstruktor
	 */
	public Song() {
	}

	/**
	 * Konstruktor mithilfe eines Builders
	 * @param songBuilder der Builder zum aufbauen des Songs
	 */
	public Song(Song.Builder songBuilder) {
		this(songBuilder.getId(), songBuilder.getTitle(), songBuilder.getArtist(), songBuilder.getAlbum(),songBuilder.getReleased());
	}

	/**
	 * Konstruktor der diesen Song mit allen Informationen aufbaut
	 * @param id die Id
	 * @param title der Titel
	 * @param artist der Artist
	 * @param album das Album
	 * @param released das Releasejahr
	 */
	public Song(Integer id, String title, String artist, String album, Integer released) {
		setId(id);
		setTitle(title);
		setArtist(artist);
		setAlbum(album);
		setReleased(released);
	}

	/**
	 * Gibt die Id zurueck
	 * @return die Id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Setzt die Id
	 * @param id die Id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gibt den Titel zurueck
	 * @return der Titel
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setzt den Titel
	 * @param title der Titel
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gibt den Artisten zurueck
	 * @return der Artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Setzt den Artisten
	 * @param artist der Artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Gibt das Album zurueck
	 * @return das Album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Setzt das Album
	 * @param album das Album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Gibt das Releasejahr zurueck
	 * @return das Releasejahr
	 */
	public Integer getReleased() {
		return released;
	}

	/**
	 * Setzt das Releasejahr
	 * @param released das Releasejahr
	 */
	public void setReleased(Integer released) {
		this.released = released;
	}

	/**
	 * Diese Klasse stellt den Builder fuer die Song-Klasse dar
	 * @author Elias Kechter s0555064
	 *
	 */
	public static class Builder {
		private Integer id;
		private String title;
		private String artist;
		private String album;
		private Integer released;

		/**
		 * Setzt die Id
		 * @param id die Id
		 * @return dieses Builder Objekt
		 */
		public Builder id(Integer id) {
			setId(id);
			return this;
		}

		/**
		 * Setzt den Titel
		 * @param title der Titel
		 * @return dieses Builder Objekt
		 */
		public Builder title(String title) {
			setTitle(title);
			return this;
		}

		/**
		 * Setzt den Artisten
		 * @param artist der Artist
		 * @return dieses Builder Objekt
		 */
		public Builder artist(String artist) {
			setArtist(artist);
			return this;
		}

		/**
		 * Setzt das album
		 * @param album das Album
		 * @return dieses Builder Objekt
		 */
		public Builder album(String album) {
			setAlbum(album);
			return this;
		}

		/**
		 * Setzt das Releasejahr
		 * @param released das Releasejahr
		 * @return dieses Builder Objekt
		 */
		public Builder released(Integer released) {
			setReleased(released);
			return this;
		}

		/**
		 * Baut den Song
		 * @return den Song
		 */
		public Song build() {
			return new Song(this);
		}

		/**
		 * Gibt die Id zurueck
		 * @return die Id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * Setzt die Id
		 * @param id die Id
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * Gibt den Titel zurueck
		 * @return der Titel
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Setzt den Titel
		 * @param title der Titel
		 */
		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * Gibt den Artisten zurueck
		 * @return der Artist
		 */
		public String getArtist() {
			return artist;
		}

		/**
		 * Setzt den Artisten
		 * @param artist der Artist
		 */
		public void setArtist(String artist) {
			this.artist = artist;
		}

		/**
		 * Gibt das Album zurueck
		 * @return das Album
		 */
		public String getAlbum() {
			return album;
		}

		/**
		 * Setzt das Album
		 * @param album das Album
		 */
		public void setAlbum(String album) {
			this.album = album;
		}

		/**
		 * Gibt das Releasejahr zurueck
		 * @return das Releasejahr
		 */
		public Integer getReleased() {
			return released;
		}

		/**
		 * Setzt das Releasejahr
		 * @param released das Releasejahr
		 */
		public void setReleased(Integer released) {
			this.released = released;
		}
	}
}