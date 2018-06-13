package de.htwBerlin.ai.kbe.services;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.htwBerlin.ai.kbe.bean.Song;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

/**
 * Setzt die Unittests fuer delete und put Anfragen um
 * 
 *
 */
public class SongWebsServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongsRXWebService.class);
	}

	@Test
	public void putSongWithExistingIdJsonShouldSuccess() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(2)
				.released(2016).build();

		Response response = target("/songs/2").request().put(Entity.json(song));

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void putSongWithExistingIdXMLShouldSuccess() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(8)
				.released(2016).build();

		Response response = target("/songs/6").request().put(Entity.xml(song));

		Assert.assertEquals(204, response.getStatus());
	}

	@Test
	public void putSongWithNonexistingIdJsonShouldFail() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(5000)
				.released(2016).build();

		Response response = target("/songs/5000").request().put(Entity.json(song));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putSongWithNonexistingIdXMLShouldFail() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(5000)
				.released(2016).build();

		Response response = target("/songs/5000").request().put(Entity.xml(song));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putSongWithNonmatchingIdsJsonShouldFail() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(5000)
				.released(2016).build();

		Response response = target("/songs/1").request().put(Entity.json(song));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putSongWithNonmatchingIdsXMLShouldFail() {
		Song song = new Song.Builder().artist("Meghan Trainor, Kelli Trainor").title("Mom").album("Thank You").id(5000)
				.released(2016).build();

		Response response = target("/songs/1").request().put(Entity.xml(song));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithBadPayLoadXMLShouldFail() {
		String str = "xml";

		Response response = target("/songs/775").request().put(Entity.xml(str));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithBadPayLoadJsonShouldFail() {
		String str = "json";

		Response response = target("/songs/888").request().put(Entity.json(str));
		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithNoSongInformationsJsonShouldFail() {
		Song song = new Song();

		Response response = target("/songs/81").request().put(Entity.json(song));

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void putWithNoSongInformationsXMLShouldFail() {
		Song song = new Song();

		Response response = target("/songs/81").request().put(Entity.xml(song));

		Assert.assertEquals(400, response.getStatus());
	}

//	@Test
//	public void deleteEsixtingIdShouldSuccess() {
//		Response response = target("/songs/9").request().delete();
//		Assert.assertEquals(204, response.getStatus());
//	}
//
//	@Test
//	public void deleteNonesixtingIdShouldFail() {
//		Response response = target("/songs/-1").request().delete();
//		Assert.assertEquals(404, response.getStatus());
//	}
//
//	@Test
//	public void deleteWithoutIdShouldFail() {
//		Response response = target("/songs/10000").request().delete();
//		Assert.assertEquals(404, response.getStatus());
//	}
//
//	@Test
//	public void deleteWithNullIdShouldFail() {
//		Response response = target("/songs/null").request().delete();
//		Assert.assertEquals(404, response.getStatus());
//	}
//
//	@Test
//	public void deleteWithNonnumberIdShouldFail() {
//		Response response = target("/songs/asd").request().delete();
//		Assert.assertEquals(404, response.getStatus());
//	}
//
//	@Test
//	public void deleteWithToobigIdShouldFail() {
//		Response response = target("/songs/999999999999999999").request().delete();
//		Assert.assertEquals(404, response.getStatus());
//	}

	//
	//
	// @Test
	// public void putSongWithExistingIdJsonShouldSuccess() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(82)
	// .released(2016).build();
	//
	// Response response = target("/songs/82").request().put(Entity.json(song));
	//
	// Assert.assertEquals(204, response.getStatus());
	// }
	//
	// @Test
	// public void putSongWithExistingIdXMLShouldSuccess() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(82)
	// .released(2016).build();
	//
	// Response response = target("/songs/46").request().put(Entity.xml(song));
	//
	// Assert.assertEquals(204, response.getStatus());
	// }
	//
	// @Test
	// public void putSongWithNonexistingIdJsonShouldFail() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(5000)
	// .released(2016).build();
	//
	// Response response = target("/songs/5000").request().put(Entity.json(song));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putSongWithNonexistingIdXMLShouldFail() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(5000)
	// .released(2016).build();
	//
	// Response response = target("/songs/5000").request().put(Entity.xml(song));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putSongWithNonmatchingIdsJsonShouldFail() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(5000)
	// .released(2016).build();
	//
	// Response response = target("/songs/1").request().put(Entity.json(song));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putSongWithNonmatchingIdsXMLShouldFail() {
	// Song song = new Song.Builder().artist("Meghan Trainor, Kelli
	// Trainor").title("Mom").album("Thank You").id(5000)
	// .released(2016).build();
	//
	// Response response = target("/songs/1").request().put(Entity.xml(song));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putWithBadPayLoadXMLShouldFail() {
	// String str = "xml";
	//
	// Response response = target("/songs/775").request().put(Entity.xml(str));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putWithBadPayLoadJsonShouldFail() {
	// String str = "json";
	//
	// Response response = target("/songs/888").request().put(Entity.json(str));
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putWithNoSongInformationsJsonShouldFail() {
	//
	// Song song = new Song();
	//
	// Response response = target("/songs/81").request().put(Entity.json(song));
	//
	// Assert.assertEquals(400, response.getStatus());
	// }
	//
	// @Test
	// public void putWithNoSongInformationsXMLShouldFail() {
	//
	// Song song = new Song();
	//
	// Response response = target("/songs/81").request().put(Entity.xml(song));
	//
	// Assert.assertEquals(400, response.getStatus());
	// }

}