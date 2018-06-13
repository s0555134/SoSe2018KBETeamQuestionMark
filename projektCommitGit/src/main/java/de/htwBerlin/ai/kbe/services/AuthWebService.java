package de.htwBerlin.ai.kbe.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.SongsRXStore;
import de.htwBerlin.ai.kbe.storage.UserStore;

/**
 *  Diese Klasse dient zur Bereitstellung der Funktionalitaeten des
 *         Webservices : Authorisierungstoken senden
 * @
 *
 */
@Path("/auth")
public class AuthWebService {
	private static Map<String, String> tokenDB = new HashMap<String, String>();

	@Context
	HttpServletRequest request;

	// GET http://localhost:8080/songsRX/rest/songs/1
	// Returns: 200 and contact with id 1
	// Returns: 404 on provided id not found
	@GET
	@Path("/")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response generateAuth(@QueryParam("userId") String userId) {


		
		
//		if (tokenDB.containsValue(userId)) {
//
//			String token = "";
//
//			for (Object o : tokenDB.keySet()) {
//				if (tokenDB.get(o).equals(userId))
//					token = (String) o;
//			}
//
//			return Response.status(Response.Status.FORBIDDEN)
//					.entity(Response.Status.FORBIDDEN + "Security Token allready given" + "  " + token).build();
//		}
	

		UserStore u=UserStore.getInstance();
		if(u==null)
			throw new NullPointerException("Nullpointer");
		User user = u.getUserById(userId);
		if (user != null) {
			String token = request.getSession().getId();
			
			tokenDB.put(token, userId);

			return Response.status(Response.Status.OK).entity("Token: " + token ).build();
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity(Response.Status.FORBIDDEN + ": Not Auth, No User found with id " + userId).build();
		}

	}
}