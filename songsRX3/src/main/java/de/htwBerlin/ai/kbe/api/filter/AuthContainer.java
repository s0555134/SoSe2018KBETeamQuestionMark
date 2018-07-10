package de.htwBerlin.ai.kbe.api.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

@Singleton
public class AuthContainer  implements IAuthContainer{
	
	/**
	 * key = token, value = userId
	 */
	private static Map<String,String> tokenDB = new ConcurrentHashMap<String,String>();
	

	@Override
	public boolean authenticate(String authToken) {
		if(tokenDB.containsKey(authToken)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getUserIdByToken(String token) {
		return tokenDB.get(token);
	}
	
	@Override
	public String setUserIdByToken(String token,String userId) {
		return tokenDB.put(token,userId);
	}

	@Override
	public boolean containsVal(String userId) {
		
		return tokenDB.containsValue(userId);
	}

}
