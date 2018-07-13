package com.kharkitecture.backoffice.dao.impl;

import com.kharkitecture.backoffice.dao.BuildingDAO;
import com.kharkitecture.backoffice.entity.Building;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Qualifier("hibernate")
public class BuildingDAOImpl implements BuildingDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private Session session;
    private Logger logger ;

    public BuildingDAOImpl() {
        this.logger = LogManager.getLogger(this.getClass());
        this.session = sessionFactory.openSession();
    }

    @Override
    public void add(Building building) {
        Transaction transaction = session.beginTransaction();
        session.save(building);
        try {
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.ERROR, "Adding building error", e);
            transaction.rollback();
        }
    }

    @Override
    public Building get(long id) {
        return session.get(Building.class, id);
    }

    @Override
    public void update(Building editedBuilding) {
        Transaction transaction = session.beginTransaction();
        session.update("building", editedBuilding);
        try {
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.ERROR, "Update error", e);
            transaction.rollback();
        }
    }

    @Override
    public void remove(long id) {
        Building building = session.get(Building.class, id);
        Transaction transaction = session.beginTransaction();
        session.delete(building);
        try {
            session.flush();
            transaction.commit();
        } catch (HibernateException e) {
            logger.log(Level.ERROR, "Deleting building error", e);
            transaction.rollback();
        }
    }

    @Override
    public boolean isBuildingExists(long id) {
        Building building = session.get(Building.class, id);
        return building != null;
    }

    @Override
    public List<Building> getBuildingsList() {
        //todo: return Building.class list from DB
        return Collections.emptyList();
    }
}
