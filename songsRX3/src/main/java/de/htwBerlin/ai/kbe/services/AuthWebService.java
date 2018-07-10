package de.htwBerlin.ai.kbe.services;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.htwBerlin.ai.kbe.DBStorage.IUserDAO;
import de.htwBerlin.ai.kbe.api.filter.IAuthContainer;
import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.UsersStore;

//Accept          <--->      @Produces
//Content-Type    <--->      @Consumes
//curl -X GET \
//-v "http://localhost:8080/songsRX/rest/auth?userId=eschuler"

@Path("/auth")
public class AuthWebService {

	private IAuthContainer authContainer ;
	private IUserDAO userDao ;
	
	
	@Context
	HttpServletRequest request;
	
	@Inject
	public AuthWebService(IAuthContainer authContainer,IUserDAO userDao ) {
		this.authContainer = authContainer;
		this.userDao = userDao;
	}
	

	// GET http://localhost:8080/songsRX/rest/songs/1
	// Returns: 200 and contact with id 1
	// Returns: 404 on provided id not found
	@GET
	@Path("/")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response generateAuth(@QueryParam("userId") String userId) {
		User userID = UsersStore.getInstance().getUser(userId);
		if (userID != null) {
			if (authContainer.containsVal(userId)) {
				return Response.status(Response.Status.FOUND).entity(
						Response.Status.FORBIDDEN + ": You allready logged in, your token is: " + authContainer.getUserIdByToken(userId))
						.build();
			}
			String token = request.getSession().getId();
			authContainer.setUserIdByToken(token, userId);
			return Response.status(Response.Status.OK).entity("Token: " + token).build();
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity(Response.Status.FORBIDDEN + ": Not Auth, No User found with id " + userId).build();
		}

	}

	
	public boolean authenticate(String authToken) {
		String userId = authContainer.getUserIdByToken(authToken);
		if(userId != null) {
			return true;
		}
		return false;
	}
	

}
