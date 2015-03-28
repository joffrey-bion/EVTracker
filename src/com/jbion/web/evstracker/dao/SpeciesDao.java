package com.jbion.web.evstracker.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jbion.web.evstracker.entities.Species;

@Stateless
public class SpeciesDao {

    private static final String PARAM_NUM = "pokedexNumber";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_MIN = "min";
    private static final String PARAM_MAX = "max";
    private static final String PARAM_BEGINNING = "beginning";

    private static final String JPQL_COUNT_ALL = "SELECT COUNT(s) FROM Species s";
    private static final String JPQL_SELECT_ALL = "SELECT s FROM Species s ORDER BY s.pokedexNum";
    private static final String JPQL_SELECT_BY_NUM = "SELECT s FROM Species s WHERE s.pokedexNum=:" + PARAM_NUM;
    private static final String JPQL_SELECT_BY_NAME = "SELECT s FROM Species s WHERE s.name=:" + PARAM_NAME;
    private static final String JPQL_SELECT_BY_NAME_START = "SELECT s FROM Species s WHERE s.name LIKE CONCAT(:"
            + PARAM_BEGINNING + " , '%')";
    private static final String JPQL_SELECT_BY_NUM_RANGE = "SELECT s FROM Species s WHERE s.pokedexNum >= :"
            + PARAM_MIN + " AND s.pokedexNum <= :" + PARAM_MAX;
    private static final String JPQL_SELECT_BY_NUM_START = "SELECT s FROM Species s WHERE s.pokedexNum LIKE CONCAT(:"
            + PARAM_BEGINNING + " , '%')";
    private static final String JPQL_SELECT_BY_NUM_START_MAX = "SELECT s FROM Species s WHERE s.pokedexNum <= :"
            + PARAM_MAX + " AND s.pokedexNum LIKE CONCAT(:" + PARAM_BEGINNING + " , '%')";
    private static final String JPQL_SELECT_BY_NUM_START_MAX_MIN = "SELECT s FROM Species s WHERE s.pokedexNum >= :"
            + PARAM_MIN + " AND s.pokedexNum <= :" + PARAM_MAX + " AND s.pokedexNum LIKE CONCAT(:" + PARAM_BEGINNING
            + " , '%')";

    @PersistenceContext(unitName = "pokemon_db_PU")
    private EntityManager em;

    public int getTotalNumber() {
        try {
            Query query = em.createQuery(JPQL_COUNT_ALL);
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<Species> list() {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_ALL, Species.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void create(Species species) {
        try {
            em.persist(species);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void delete(Species species) {
        try {
            em.remove(em.merge(species));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void deleteByID(Integer pokedexNumber) {
        try {
            delete(findFirstByPokedexNum(pokedexNumber));
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public void update(Species s) {
        try {
            em.merge(s);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public Species findById(Integer speciesId) {
        try {
            return em.find(Species.class, speciesId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Returns the first species matching the given name.
     *
     * @param speciesName
     *            the name of the species
     * @return the first species matching the given pokedex number
     * @deprecated the name does not identify uniquely a species, the same species could exist in
     *             different versions, all with the same name
     */
    @Deprecated
    public Species findFirstByName(String speciesName) {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_BY_NAME, Species.class);
            query.setParameter(PARAM_NAME, speciesName);
            List<Species> result = query.getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Returns the first species matching the given pokedex number.
     *
     * @param pokedexNumber
     *            the pokedex number of the species
     * @return the first species matching the given pokedex number
     * @deprecated the pokedexNumber does not identify uniquely a species, the same species could
     *             exist in different versions, all with the same pokedex number
     */
    @Deprecated
    public Species findFirstByPokedexNum(Integer pokedexNumber) {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_BY_NUM, Species.class);
            query.setParameter(PARAM_NUM, pokedexNumber);
            List<Species> result = query.getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Returns the species matching the given pokedex number. Multiple species may match.
     *
     * @param pokedexNumber
     *            the pokedex number of the species
     * @return the species matching the given pokedex number
     */
    public List<Species> findByPokedexNum(Integer pokedexNumber) {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_BY_NUM, Species.class);
            query.setParameter(PARAM_NUM, pokedexNumber);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<Species> findByPokedexNumRange(int min, int max) {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_BY_NUM_RANGE, Species.class);
            query.setParameter(PARAM_MIN, min);
            query.setParameter(PARAM_MAX, max);
            return query.getResultList();
        } catch (NoResultException e) {
            return new LinkedList<>();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<Species> findByNameBeginning(String nameBeginning) {
        try {
            TypedQuery<Species> query = em.createQuery(JPQL_SELECT_BY_NAME_START, Species.class);
            query.setParameter(PARAM_BEGINNING, nameBeginning);
            return query.getResultList();
        } catch (NoResultException e) {
            return new LinkedList<>();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<Species> findByNumBeginning(String numBeginning) {
        if ("00".equals(numBeginning)) {
            return findByPokedexNumRange(0, 9);
        } else if ("0".equals(numBeginning)) {
            return findByPokedexNumRange(0, 99);
        }
        try {
            TypedQuery<Species> query;
            if (numBeginning.startsWith("00")) {
                query = em.createQuery(JPQL_SELECT_BY_NUM_START_MAX, Species.class);
                query.setParameter(PARAM_BEGINNING, numBeginning.substring(2));
                query.setParameter(PARAM_MAX, 9);
            } else if (numBeginning.startsWith("0")) {
                query = em.createQuery(JPQL_SELECT_BY_NUM_START_MAX_MIN, Species.class);
                query.setParameter(PARAM_BEGINNING, numBeginning.substring(1));
                query.setParameter(PARAM_MIN, 10);
                query.setParameter(PARAM_MAX, 99);
            } else {
                query = em.createQuery(JPQL_SELECT_BY_NUM_START, Species.class);
                query.setParameter(PARAM_BEGINNING, numBeginning);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return new LinkedList<>();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
