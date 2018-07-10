package de.htwBerlin.ai.kbe.DBStorage;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;

@Singleton
public class DBUserDAO implements IUserDAO {

	private EntityManagerFactory emf;

	@Inject
	public DBUserDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public User findUserById(String id) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT userid FROM Users where userid = id", User.class);
			return query.getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public boolean deleteUserById(Integer id) throws PersistenceException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		User user = null;
		try {
			user = em.find(User.class, id);
			if (user != null) {
				System.out.println("Deleting: " + user.getId() + " with firstName: " + user.getFirstName());
				transaction.begin();
				em.remove(user);
				transaction.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error removing User: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not remove entity: " + e.toString());
		} finally {
			em.close();
		}
	}

	@Override
	public Collection<User> findAllUser() {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT c FROM Users c", User.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Integer saveUser(User user) throws PersistenceException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			// MUST set the contact in every address
			/*
			 * 
			 * ACHTUNG
			 * 
			 * 
			 * 
			 */
//			for (SongLists a : user.getSongLists()) { // <------- BEARBEITEN????
//				a.setOwner(user.getId());
//			}
			em.persist(user);
			transaction.commit();
			return user.getId();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding contact: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not persist entity: " + e.toString());
		} finally {
			em.close();
		}
	}

}
