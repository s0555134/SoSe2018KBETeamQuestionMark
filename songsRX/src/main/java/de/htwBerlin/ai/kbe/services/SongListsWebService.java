package de.htwBerlin.ai.kbe.services;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.DBStorage.DBUserDAO;
import de.htwBerlin.ai.kbe.DBStorage.ISongListsDAO;
import de.htwBerlin.ai.kbe.api.filter.AuthContainer;
import de.htwBerlin.ai.kbe.api.filter.IAuthContainer;

//URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/userId 
@Path("/userId")
public class SongListsWebService {

	private ISongListsDAO songListsDao;

	private DBUserDAO userDBDao;

	private IAuthContainer userAuth;

	@Inject
	public SongListsWebService(ISongListsDAO dao, IAuthContainer userAuth) {
		this.songListsDao = dao;
		this.userAuth = userAuth;

	}

	@GET
	@Path("/{id}/songLists")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<SongLists> getAllSongLists(@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {
		// System.out.println("Drinne");
		// String userIdAuthToken = userAuth.getUserIdByToken(token);
		// System.out.println("Oke, userIDAuth gesetzt");
		// if (token != null || userIdAuthToken != null) {
		// System.out.println("getAllSongLists: Returning all SongLists!");
		// return songListsDao.findAllSongLists(id);
		// } else {
		// System.out.println("userDBDAO????");
		// User userInDB = userDBDao.findUserById(id);
		// if (userInDB != null) {
		// System.out.println("get all Public Songs from User: " + id);
		// return songListsDao.findPublicSongById(id);
		// }
		// return null;
		// }
		System.out.println(songListsDao);

		System.out.println("getAllSongLists: Returning all SongLists!");
		System.out.println(songListsDao.findAllSongLists(id));

		if (userAuth.getUserIdByToken(token).equals(id)) {
			return songListsDao.findAllSongLists(id);

		}

		return songListsDao.findPublicSongById(id);

	}

	@GET
	@Path("/{Userid}/songLists/{songId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSongLists(@HeaderParam("Authorization") String token, @PathParam("Userid") String id,
			@PathParam("songId") Integer songId) {

		System.out.println("getAllSongLists: Returning all SongLists!");

		// return all if same user
		// System.ou t.println(SongListsDao.findAllSongLists(id,false));

		SongLists s;
		if (userAuth.getUserIdByToken(token).equals(id)) {
			System.out.println("hi");
			s = songListsDao.findPrivateSongListById(id, songId);
			if (s == null)
				s = songListsDao.findPublicSongListById(id, songId);
		} else {
			System.out.println("haaaaaai");
			s = songListsDao.findPublicSongListById(id, songId);
		}
		if (s == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("No Song found with id " + songId).build();
		} else {
			return Response.ok(s).build();
		}

	}

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{Userid}/songLists")
	@Produces(MediaType.TEXT_PLAIN)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public Response createSongLists(@HeaderParam("Authorization") String token, SongLists songLists,
			@PathParam("Userid") String id) {

		if (userAuth.getUserIdByToken(token).equals(id)) {

			if (songLists != null) {
				System.out.println("geh in saveSonglist rein");
				String newId = songListsDao.saveSongLists(songLists).toString();

				if (newId == null) {
					System.out.println("bad reqeust by Post Methode1");
					return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request/Payload").build();

				}
				System.out.println("new Id ist nicht null");
				UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
				uriBuilder.path(newId);// Integer.parseInt(newId)
				return Response.created(uriBuilder.build()).build();

			} else {
				System.out.println("bad reqeust by Post Methode2");
				return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request/Payload").build();

			}
		} else {

			System.out.print("lul brauchst token");

			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		}

	}

	@DELETE
	@Path("/{Userid}/songLists/{songListID}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("songListID") Integer id, @HeaderParam("Authorization") String token,
			@PathParam("Userid") String userId) {

		if (!userAuth.getUserIdByToken(token).equals(userId)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		} else {
			System.out.print("delet by Songlistwebservice!");
			songListsDao.deleteSongLists(id, userId);

			return Response.status(Response.Status.NO_CONTENT).entity(": Succes to deleted SongList").build();

		}
	}

}
