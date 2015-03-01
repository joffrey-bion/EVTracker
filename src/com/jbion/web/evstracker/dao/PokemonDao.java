package com.jbion.web.evstracker.dao;

import java.util.List;

import javax.ejb.Stateless;

import com.jbion.web.evstracker.entities.Pokemon;

@Stateless
public class PokemonDao {
    public void create(Pokemon pokemon) throws DaoException {
        // TODO Auto-generated method stub
        
    }

    public Pokemon findByID(Long id) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Pokemon> find(String ownerLogin) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteByID(Long pokemonId) {
        // TODO Auto-generated method stub
        
    }

    public void delete(Pokemon pokemon) {
        // TODO Auto-generated method stub
        
    }

    // TODO complete
}
