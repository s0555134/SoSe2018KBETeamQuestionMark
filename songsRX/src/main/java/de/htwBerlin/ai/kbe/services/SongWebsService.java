package de.htwBerlin.ai.kbe.services;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.DBStorage.ISongsDAO;
import de.htwBerlin.ai.kbe.api.filter.IAuthContainer;
import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.storage.SongsStore;

// Accept          <--->      @Produces
// Content-Type    <--->      @Consumes

@Path("/songs")
public class SongWebsService {

	@Inject
	private IAuthContainer userAuth;

	private ISongsDAO songsDao;

	@Inject
	public SongWebsService(ISongsDAO dao) {
		this.songsDao = dao;

	}

	// GET http://localhost:8080/songsRX/rest/songs
	// Returns all contacts
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs(@HeaderParam("Authorization") String token) {
		if (token != null) {
			System.out.println("getAllSongs: Returning all Songs!");
			return songsDao.findAllSongs();
		}
		return null;

	}

	// GET http://localhost:8080/songsRX/rest/songs/1
	// Returns: 200 and contact with id 1
	// Returns: 404 on provided id not found
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSong(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
		if (token == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		}

		Song song = songsDao.findSongById(id);
		if (song != null) {
			System.out.println("getsong: Returning song for id " + id);
			return Response.ok(song).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("No Song found with id " + id).build();
		}
	}

	// POST http://localhost:8080/songsRX/rest/songs with contact in payload
	// Returns: Status Code 201 and the new id of the contact in the payload

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createContact(@HeaderParam("Authorization") String token, Song song) throws IOException {
		String userIdAuthToken = userAuth.getUserIdByToken(token);

		if (token == null || userIdAuthToken == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		} else {
			if (song != null && song.getTitle() != null) {

				SongsStore.getInstance().addSong(song);
				System.out.println("createSong: Received Song: " + song.toString());
				return Response.status(Response.Status.CREATED).entity(Response.Status.CREATED + ": " + "Album - Name: "
						+ song.getAlbum() + "Album - ID: " + song.getId()).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(Response.Status.NOT_FOUND + ": " + "Bad Payload ").build();
			}
		}

	}

	// PUT http://localhost:8080/songsRX/rest/songs/37 with updated contact in
	// payload
	// Returns 204 on successful update
	// Returns 404 on contact with provided id not found
	// Returns 400 on id in URL does not match id in contact

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateSong(@PathParam("id") Integer id, @HeaderParam("Authorization") String token, Song song) {
		String userIdAuthToken = userAuth.getUserIdByToken(token);

		if (token == null || userIdAuthToken == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		} else {
			boolean updateSong = false;

			if (song != null && song.getTitle() != null) {
				if (song.getId() != null) {
					updateSong = SongsStore.getInstance().updateSong(song, id);
					if (updateSong) {
						return Response.status(Response.Status.NO_CONTENT).entity("Succes to updated Song").build();
					} else {
						return Response.status(Response.Status.NOT_FOUND)
								.entity(Response.Status.NOT_FOUND + ": updated Song: ").build();
					}
				} else {

					if (SongsStore.getInstance().getSong(id) != null) {
						updateSong = SongsStore.getInstance().updateSong(song, id);
						return Response.status(Response.Status.NO_CONTENT).entity("Succes to updated Song").build();

					} else {
						return Response.status(Response.Status.NOT_FOUND)
								.entity(Response.Status.NOT_FOUND + "Fail to updated Song").build();
					}
				}

			} else {
				return Response.status(Response.Status.CONFLICT)
						.entity(Response.Status.CONFLICT + "Fail to updated Song, because of a Bad Payload").build();
			}
		}

	}

	// DELETE http://localhost:8080/songsRX/rest/songs/37
	// Returns 204 on successful delete
	// Returns 404 on provided id not found
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
		if (token == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		}
		boolean check = songsDao.deleteSong(id);
		if (check) {
			return Response.status(Response.Status.NO_CONTENT).entity("Sucessfully deleted Song").build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Can't delete this Song. Song doesn't exists")
					.build();
		}
	}


}
