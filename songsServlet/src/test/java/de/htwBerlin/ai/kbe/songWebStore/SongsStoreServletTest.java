package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Assert;

public class SongsStoreServletTest {

	private SongsStoreServlet servlet = new SongsStoreServlet();
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ObjectMapper objectMapper;

	@Before
	public void setUp() throws ServletException {
		objectMapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void playingWithJackson() throws IOException {
		// songs.json and testSongs.json contain songs from this Top-10:
		// http://time.com/collection-post/4577404/the-top-10-worst-songs/

		// Read a JSON file and create song list:
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");

		List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>() {
		});

		// write a list of objects to a JSON-String with prettyPrinter
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songList);

		// write a list of objects to an outputStream in JSON format
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream("output.json"), songList);

		// Create a song and write to JSON
		Song song = new Song(null, "titleXX", "artistXX", "albumXX", 1999);
		byte[] jsonBytes = objectMapper.writeValueAsBytes(song);

		// Read JSON from byte[] into Object
		Song newSong = (Song) objectMapper.readValue(jsonBytes, Song.class);
		assertEquals(song.getTitle(), newSong.getTitle());
		assertEquals(song.getArtist(), newSong.getArtist());
	}
	
	//initialisieren, dann doGet mit Parameter "all". Die Datei selbst laden alles verpacken und und assert auf jedes einzelne Objekt und deren Eigenschaften durchfuehren

	 @Test
	public void initShouldLoadSongList() {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			request.addParameter("all", "7");

			config.addInitParameter("json", "songs.json");

			servlet.init(config);

			String songFilename = config.getInitParameter("json");
			
			InputStream url = this.getClass().getClassLoader().getResourceAsStream(songFilename);

			List<Song> songsListExpected = (List<Song>) new ObjectMapper().readValue(url, new TypeReference<List<Song>>() {});
			
			Map<Integer, Song> songStoreExpected = new ConcurrentHashMap<Integer, Song>();
			
			for (Song song : songsListExpected)
				songStoreExpected.put(song.getId(), song);

			servlet.doGet(request, response);
			
			List<Song> songsListActual = (List<Song>) new ObjectMapper().readValue(response.getContentAsString(),new TypeReference<List<Song>>() {});	
			
			Map<Integer, Song> songStoreActual = new ConcurrentHashMap<Integer, Song>();
			
			for (Song song : songsListActual)
				songStoreActual.put(song.getId(), song);
			
			for(int i=0;songStoreActual.values()==songStoreExpected.values()&&i<songStoreActual.values().size();i++) {
				assertEquals(songStoreExpected.get(i).getId(), songStoreActual.get(i).getId());

				assertEquals(songStoreExpected.get(i).getTitle(), songStoreActual.get(i).getTitle());

				assertEquals(songStoreExpected.get(i).getArtist(), songStoreActual.get(i).getArtist());

				assertEquals(songStoreExpected.get(i).getAlbum(), songStoreActual.get(i).getAlbum());

				assertEquals(songStoreExpected.get(i).getReleased(), songStoreActual.get(i).getReleased());
			}



		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doGetShouldRespondWithExistingRecord() throws ServletException {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			request.addParameter("songId", "7");

			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doGet(request, response);

			Song expectedSong = new Song(7, "i hate u, i love u", "Gnash", "Top Hits 2017", 2017);

			Song respondedSong = objectMapper.readValue(response.getContentAsString(), Song.class);

			assertEquals(expectedSong.getId(), respondedSong.getId());

			assertEquals(expectedSong.getTitle(), respondedSong.getTitle());

			assertEquals(expectedSong.getArtist(), respondedSong.getArtist());

			assertEquals(expectedSong.getAlbum(), respondedSong.getAlbum());

			assertEquals(expectedSong.getReleased(), respondedSong.getReleased());

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doGetShouldRespondWithFailMessageToNonexistingRecord() throws ServletException {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String WRONG_ID = "Fehler, Song mit Id 22 nicht vorhanden";

			request.addParameter("songId", "22");

			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doGet(request, response);

			assertTrue(response.getContentAsString().contains(WRONG_ID));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doGetShouldRespondWithFailMessageToNoParameterProvided() throws ServletException {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String WRONG_PARAMETERS = "Fehler, bitte mit Parameter songId oder all versuchen. Bsp. ?songId=\"1\" oder ?all";

			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doGet(request, response);

			assertTrue(response.getContentAsString().contains(WRONG_PARAMETERS));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doGetShouldRespondWithFailMessageToWrongParameterFormat() throws ServletException {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String WRONG_VALUE_FORMAT = "Fehler. Falsches Format fuer Parameter songId angegeben";

			request.addParameter("songId", "WrongValue");

			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doGet(request, response);

			assertTrue(response.getContentAsString().contains(WRONG_VALUE_FORMAT));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doGetShouldRespondWithFailMessageToNoValueForSongIdProvided() throws ServletException {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String NO_VALUE = "Fehler. Kein Wert fuer Parameter songId angegeben";

			request.addParameter("songId", "");

			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doGet(request, response);
			assertTrue(response.getContentAsString().contains(NO_VALUE));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doPostShouldReturnSuccessMessageWhenValidSongProvided() {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String DO_POST_SUCCESS = "Song 888 Year von Lukas 888 aus dem Album Lukas 888 (Album Eight) erschienen in 2018 mit der Id 11 hinzugefuegt";

			request.setContent(
					"{\n\"title\" : \"888 Year\",\n\"artist\" : \"Lukas 888\",\n\"album\" : \"Lukas 888 (Album Eight)\",\n\"released\" : 2018\n}"
							.getBytes());
			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doPost(request, response);
			assertTrue(response.getContentAsString().contains(DO_POST_SUCCESS));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void doPostShouldReturnFailMessageWhenInValidSongProvided() {

		try {
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			config = new MockServletConfig();

			String JSON_EXCEPTION = "Fehler. Die JSON payload war leider nicht wohlgeformt oder invalide.";

			request.setContent("Das ist nicht gut!".getBytes());
			config.addInitParameter("json", "songs.json");

			servlet.init(config);
			servlet.doPost(request, response);
			assertTrue(response.getContentAsString().contains(JSON_EXCEPTION));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
