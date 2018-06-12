package de.htwBerlin.ai.kbe.services;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.storage.SongsRXStore;



/**
 * Diese Klasse dient zur Bereitstellung der Funktionalitaeten des Webservices
 * 
 *
 *
 */
@Path("/songs")
public class SongsRXWebService {
	
	@Context
	private UriInfo uriInfo;

	/**
	 * Gibt alle Songs zurueck
	 * @return alle Songs
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSongs(@HeaderParam("Authorization") String token) {
		if (token == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		}
		@SuppressWarnings("rawtypes")
		GenericEntity entity = new GenericEntity<Collection<Song>>(SongsRXStore.getInstance().getAllSongs()) {
		};
		return Response.ok(entity).build();
	}

	/**
	 * Gibt einen Song mit der Nummer id zurueck
	 * @param id die id
	 * @return song mit Nummer id
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSong(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
		if (token == null) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		}
		System.out.println(token);
		Song song = SongsRXStore.getInstance().getSongById(id);
		
		if (song != null) 
			return Response.status(Response.Status.OK).entity(song).build();

		return Response.status(Response.Status.NOT_FOUND).entity(Response.Status.NOT_FOUND + ": No Song found with id " + id).build();
	}

	/**
	 * Fuegt einen neuen Song hinzu
	 * @param song der neue Song
	 * @return einen Response_Code
	 * @throws IOException falls etwas nciht in Ordnung war
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createSong( Song song) throws IOException {
		if (SongsRXStore.getInstance().addNewSong(song) != null) {
			int newId = SongsRXStore.getInstance().addNewSong(song);
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(Integer.toString(newId));
			System.out.println("createSongMethodeDrin");
			
			return Response.created(uriBuilder.build()).entity("ResponseStatus "+Response.Status.OK + " New Song added").build();
			
			//return Response.status(Response.Status.CREATED).entity(Response.Status.CREATED + String.format("Song with id %d added\n", song.getId())).build();
		}
		else
			System.out.println("createSongMethodeDrinElse");

		return Response.status(Response.Status.NOT_FOUND).entity(Response.Status.NOT_FOUND + "Failed to add a new song\n").build();
	}

	
	/**
	 * Updated einen existierenden Song
	 * @param id die Id
	 * @param song der Song
	 * @return einen Response-Code 
	 */
	@SuppressWarnings("static-access")
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateSong(@PathParam("id") Integer id, Song song) {
		if (SongsRXStore.getInstance().updateSong(id, song)) {
			boolean updateSong = false;
			updateSong = SongsRXStore.getInstance().updateSong(id, song);
			System.out.println("PutMethode");
			
			return Response.status(Response.Status.NO_CONTENT).entity(Response.Status.NO_CONTENT + String.format(": Song with id %d updated\n"+ "onSucces", id)).build();
		}	else {
			
			System.out.println("PutMethodeelse");
		return Response.status(Response.Status.BAD_REQUEST).entity(Response.Status.BAD_REQUEST + String.format(": Song with id %d failed to be updated\n", id)).build();
		}
	}

	/**
	 * Loescht einen Eintrag in der DB
	 * @param id die Id des Songes
	 * @return einen Response-Code
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") Integer id, @HeaderParam("Authorization") String token) {
		if (token == null)
			return Response.status(Response.Status.UNAUTHORIZED).entity("You don't have any permission").build();
		if(SongsRXStore.getInstance().deleteSong(id))
			return Response.status(Response.Status.NO_CONTENT).entity(Response.Status.NO_CONTENT + String.format("Song with id %d deleted", id)).build();

		return Response.status(Response.Status.NOT_FOUND).entity(Response.Status.NOT_FOUND + String.format("Song with id %d failed to be deleted", id)).build();
	}
}