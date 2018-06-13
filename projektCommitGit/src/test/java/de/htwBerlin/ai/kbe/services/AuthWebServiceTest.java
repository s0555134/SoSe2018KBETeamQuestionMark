package de.htwBerlin.ai.kbe.services;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.UserStore;

public class AuthWebServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(SongsRXWebService.class);
	}
	@Context
	HttpServletRequest request;
	
	
	@Test
	public void requestTokenForUserMe() {
		
//		User user1 = UserStore.getInstance().getUserById("me");
//		
//		
//		Response response = target("/auth").queryParam("userId", "me").request(MediaType.TEXT_PLAIN).get();
//		
//		assertEquals(user1.getToken(),200, response.getStatus());
//		
		
	}
	
	
	@Test
	public void requestTokenForUserEschueler() {
		
		
		
		
		
//		
//		//String token = request.getSession().getId();
//		//user.setToken("36BBBABB0E34BB5EF75A87CA296236EA");
//		
//		
//		Response response = target("/auth").queryParam("userId", "eschuler").request(MediaType.TEXT_PLAIN).get();
//		
//		assertEquals("36BBBABB0E34BB5EF75A87CA296236EA",200, response.getStatus());
		
		
	}
	
	@Test
	public void requestTokenNoUserFound() {
		
		Response response = target("/auth").queryParam("userId", "affe").request(MediaType.TEXT_PLAIN).get();
		
		assertEquals(404, response.getStatus());
		
		
	}
	
	@Test
	public void requestTokenIsNull() {
		
		Response response = target("/auth").queryParam("userId", null).request(MediaType.TEXT_PLAIN).get();
		
		assertEquals(404, response.getStatus());
		
		
	}

}
