package de.htwBerlin.ai.kbe.api.filter;

public interface IAuthContainer {
	
	
	/**
	 * 
	 * @param authToken
	 * @return true when authToken in tokenMap
	 */
	public boolean authenticate(String authToken);
	
	/**
	 * 
	 * @param token
	 * @return userId given token
	 */
	public String getUserIdByToken(String token);
	
	/**
	 * put token in tokenMap
	 * @param token
	 * @param userId
	 * @return
	 */
	public String setUserIdByToken(String token,String userId);
	
	
	public boolean containsVal(String userId);

}
