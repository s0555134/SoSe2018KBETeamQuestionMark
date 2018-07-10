package de.htwBerlin.ai.kbe.DBStorage;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.bean.SongLists;
import de.htwBerlin.ai.kbe.bean.User;

@Singleton
public class DBSongListsDAO implements ISongListsDAO {

	private EntityManagerFactory emf;
	private DBSongsDAO songsdao;
	

	public DBSongListsDAO() {
	}

	@Inject
	public DBSongListsDAO(EntityManagerFactory emf) {
		this.emf = emf;
		
	}

	@Override
	public SongLists findSongListsById(Integer id) {
		EntityManager em = emf.createEntityManager();
		SongLists entity = null;
		try {
			entity = em.find(SongLists.class, id);
		} finally {
			em.close();
		}
		return entity;
	}

	@Override
	public Collection<SongLists> findAllSongLists(String id) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<SongLists> query = em.createQuery("SELECT c FROM SongLists c where ownerID = " + "'" + id + "'",
					SongLists.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}


	@Override
	public Collection<SongLists> findPublicSongById(String id) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<SongLists> query = em.createQuery(
					"SELECT c FROM SongLists c where ownerID = " + "'" + id + "' and isPublic=1 ", SongLists.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Integer saveSongLists(SongLists songLists) throws PersistenceException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			// MUST set the Song in every SongLists

			// for (Song song : songLists.getSongs()) {
			// song.setSonglists(songLists);
			// }

			List<Song> ssa = songLists.getSongs();
			System.out.println(ssa + "Liste");
		

			for (int i = 0; i > ssa.size(); i++) {
				Song a = ssa.get(i);
				String albumNameSong = a.getAlbum();
				System.out.println(albumNameSong);
				songsdao.findSongWithSongName(albumNameSong);
				System.out.println(songsdao.findSongWithSongName(albumNameSong));
				if (songsdao.findSongWithSongName(albumNameSong)) {
					em.persist(songLists);
					transaction.commit();
					System.out.println(albumNameSong);
				}

			}
			return songLists.getSongListsId();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error adding SongLists: " + e.getMessage());
			transaction.rollback();
			throw new PersistenceException("Could not persist entity: " + e.toString());
		} finally {
			em.close();
		}
	}
	
	@Override
	public void deleteSongLists(Integer id, String userId) throws PersistenceException {

		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		SongLists SongLists = null;
		try {
			System.out.println("bin bei delete Methode Dao");
			SongLists = em.find(SongLists.class, id);
			
				
			if (SongLists != null ) {
				System.out.println("Deleting: " + SongLists.getSongListsId() + " with firstName: ");
				transaction.begin();
				em.remove(SongLists);
				transaction.commit();

			}
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println("Error removing SongLists: " + e.getMessage());
			transaction.rollback();

		} finally {
			em.close();
		}

	}

	@Override
	public SongLists findPrivateSongListById(String id, Integer songListId) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<SongLists> query;

			query = em.createQuery("SELECT  c FROM SongLists c where ownerID = " + "'" + id
					+ "' and isPublic=0 and songlistsId=" + songListId, SongLists.class);
			return query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("Private songs list by ID ERROR ///////");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SongLists findPublicSongListById(String id, Integer songListId) {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<SongLists> query;

			query = em.createQuery("SELECT  c FROM SongLists c where ownerID = " + "'" + id
					+ "' and isPublic=1 and songlistsId=" + songListId, SongLists.class);
			return query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("Public songs list by ID ERROR ///////");
			//e.printStackTrace();
			return null;
		}
	}

}
