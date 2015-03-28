package com.jbion.web.evstracker.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.jbion.web.evstracker.entities.User;

@Stateless
public class UserDao {

    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_LOGIN = "login";

    private static final String JPQL_COUNT_ALL = "SELECT COUNT(u) FROM User u";
    private static final String JPQL_SELECT_ALL = "SELECT u FROM User u ORDER BY u.login";
    private static final String JPQL_SELECT_BY_EMAIL = "SELECT u FROM User u WHERE u.email=:" + PARAM_EMAIL;
    private static final String JPQL_SELECT_BY_LOGIN = "SELECT u FROM User u WHERE u.login=:" + PARAM_LOGIN;

    @PersistenceContext(unitName = "pokemon_db_PU")
    private EntityManager em;

    public Integer getTotalNumber() {
        try {
            Query query = em.createQuery(JPQL_COUNT_ALL);
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<User> list() {
        try {
            TypedQuery<User> query = em.createQuery(JPQL_SELECT_ALL, User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<User> list(int numPerPage, int pageNum) {
        // TODO replace by true paged request
        throw new NotImplementedException();
    }

    public void create(User user) {
        System.out.println("User.create(" + user + ")");
        try {
            em.persist(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public User findByID(Long id) {
        System.out.println("User.findByLogin(" + id + ")");
        try {
            return em.find(User.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public User findByEmail(String email) {
        System.out.println("User.findByEmail(" + email + ")");
        User user = null;
        Query requete = em.createQuery(JPQL_SELECT_BY_EMAIL);
        requete.setParameter(PARAM_EMAIL, email);
        try {
            user = (User) requete.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return user;
    }

    public User findByLogin(String login) {
        System.out.println("User.findByEmail(" + login + ")");
        User user = null;
        Query requete = em.createQuery(JPQL_SELECT_BY_LOGIN);
        requete.setParameter(PARAM_LOGIN, login);
        try {
            user = (User) requete.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return user;
    }
}
