package de.htwBerlin.ai.kbe.config;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htwBerlin.ai.kbe.api.filter.AuthContainer;
import de.htwBerlin.ai.kbe.api.filter.IAuthContainer;
import de.htwBerlin.ai.kbe.DBStorage.DBSongListsDAO;
import de.htwBerlin.ai.kbe.DBStorage.DBSongsDAO;
import de.htwBerlin.ai.kbe.DBStorage.DBUserDAO;
import de.htwBerlin.ai.kbe.DBStorage.ISongListsDAO;
import de.htwBerlin.ai.kbe.DBStorage.ISongsDAO;
import de.htwBerlin.ai.kbe.DBStorage.IUserDAO;



public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind (Persistence.createEntityManagerFactory("SongsRX_DB")).to(EntityManagerFactory.class);
        bind(DBSongsDAO.class).to(ISongsDAO.class).in(Singleton.class);
        bind(DBSongListsDAO.class).to(ISongListsDAO.class).in(Singleton.class);
        bind(AuthContainer.class).to(IAuthContainer.class).in(Singleton.class);
        bind(DBUserDAO.class).to(IUserDAO.class).in(Singleton.class);

    }
}
