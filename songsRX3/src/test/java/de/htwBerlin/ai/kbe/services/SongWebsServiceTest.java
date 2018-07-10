package de.htwBerlin.ai.kbe.services;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.htwBerlin.ai.kbe.DBStorage.ISongsDAO;
import de.htwBerlin.ai.kbe.bean.Song;

import de.htwBerlin.ai.kbe.storage.TestContactsDAO;
import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class SongWebsServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebsService.class).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(TestContactsDAO.class).to(ISongsDAO.class).in(Singleton.class);
            }
        });
	}

	@Test
	public void putRequestInJsonWithIDExistingInDBShouldReturn204() {
//		Song song = new Song();
//		song.setArtist("Meghan Trainor, Kelli Trainor");
//		song.setTitle("Mom");
//		song.setAlbum("Thank You");
//		song.setId(82);
//		song.setReleased(2016);
//
//		Response response = target("/songs/82").request().put(Entity.json(song));
//
//		Assert.assertEquals(204, response.getStatus());

	}

	@Test
	public void putRequestInXMLWithIDExistingInDBShouldReturn204() {
//		Song song = new Song();
//		song.setArtist("Britney Spears");
//		song.setTitle("Private Show");
//		song.setAlbum("Glory");
//		song.setId(9);
//		song.setReleased(2016);
//
//		Response response = target("/songs/46").request().put(Entity.xml(song));
//		Assert.assertEquals(204, response.getStatus());

	}

	@Test
	public void putRequestInJsonWithIDNotExistingInDBShouldReturn404() {
//		Song song = new Song();
//		song.setArtist("Britney Spears");
//		song.setTitle("Private Show");
//		song.setAlbum("Glory");
//		song.setId(5000);
//		song.setReleased(2016);
//
//		Response response = target("/songs/5000").request().put(Entity.json(song));
//		Assert.assertEquals(404, response.getStatus());

	}

	@Test
	public void putRequestInXMLWithIDNotExistingInDBShouldReturn404() {
//		Song song = new Song();
//		song.setArtist("Ilja");
//		song.setTitle("Private Show");
//		song.setAlbum("Glory");
//		song.setId(2000);
//		song.setReleased(2016);
//
//		Response response = target("/songs/2").request().put(Entity.xml(song));
//		Assert.assertEquals(404, response.getStatus());

	}

	@Test
	public void putRequestInXMLWithBadPayloadShouldReturn400() {
//		String str = "xml";
//
//		Response response = target("/songs/775").request().put(Entity.xml(str));
//		Assert.assertEquals(400, response.getStatus());

	}

	@Test
	public void putRequestInJsonWithBadPayloadShouldReturn400() {
//		String str = "json";
//
//		Response response = target("/songs/888").request().put(Entity.json(str));
//		Assert.assertEquals(400, response.getStatus());

	}

	@Test
	public void putRequestInJSONWithEmptyPayloadShouldReturn409() {

//		Song song = new Song();
//
//		Response response = target("/songs/81").request().put(Entity.json(song));
//
//		Assert.assertEquals(409, response.getStatus());

	}

	@Test
	public void putRequestInXMLWithEmptyPayloadShouldReturn409() {

//		Song song = new Song();
//
//		Response response = target("/songs/81").request().put(Entity.xml(song));
//
//		assertEquals(409, response.getStatus());

	}

	@Test
	public void deleteRequestWithIDExistShouldReturn204() {
//
//		Response response = target("/songs/9").request().delete();
//		assertEquals(204, response.getStatus());

	}

	@Test
	public void deleteRequestWithIDNotExistShouldReturn404() {
//
//		Response response = target("/songs/10000").request().delete();
//		assertEquals(404, response.getStatus());

	}

}
