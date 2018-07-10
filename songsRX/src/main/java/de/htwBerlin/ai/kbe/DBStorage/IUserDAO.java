package de.htwBerlin.ai.kbe.DBStorage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.bean.User;

public interface IUserDAO {

	public User findUserById(String id);

	public Collection<User> findAllUser();

	public Integer saveUser(User user);

	public boolean deleteUserById(Integer id);
}
