package com.jbion.web.evstracker.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.jbion.web.evstracker.dao.PokemonDao;
import com.jbion.web.evstracker.dao.SpeciesDao;
import com.jbion.web.evstracker.entities.Pokemon;
import com.jbion.web.evstracker.entities.Species;
import com.jbion.web.evstracker.entities.Stats;
import com.jbion.web.evstracker.model.Calculator;

@ManagedBean
@ViewScoped
public class AddPokemonBean implements Serializable {

    private static final String MSG_ID_NEW_POKEMON_FORM = "newPokemonForm";

    private Pokemon newPokemon;
    private Pokemon testPokemon;
    
    private Stats initEVs;
    
    private boolean ivsCalculated;
    private boolean ivsConsistent;

    @EJB
    private PokemonDao pokemonDao;
    @EJB
    private SpeciesDao speciesDao;

    @PostConstruct
    public void init() {
        newPokemon = new Pokemon();
        testPokemon = new Pokemon();
        initEVs = new Stats();
        ivsCalculated = false;
        ivsConsistent = false;
    }

    public List<Species> findSpecies(String query) {
        if (Character.isDigit(query.charAt(0))) {
            return speciesDao.findByNumBeginning(query);
        } else {
            return speciesDao.findByNameBeginning(query);
        }
    }
    
    public void calculateIVs() {
        Calculator.calculateIVs(newPokemon, initEVs);
        ivsCalculated = true;
        ivsConsistent = true;
    }

    public void create() {
        FacesMessage message = null;
        try {
            pokemonDao.create(newPokemon);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Your "
                    + newPokemon.getNature().name().toLowerCase() + " " + newPokemon.getSpecies()
                    + " was successfully added.");
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Unexpected database error: " + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(MSG_ID_NEW_POKEMON_FORM, message);
        }
    }
    
    public void test() {
        testPokemon.setSpecies(newPokemon.getSpecies());
        testPokemon.setNature(newPokemon.getNature());
        testPokemon.getLastCheckpoint().setLevel(newPokemon.getLastCheckpoint().getLevel());
        Calculator.calculateStats(testPokemon);
    }
    
    public void handleSelect(SelectEvent event) {
        // TODO nothing?
    }
    
    public boolean isSelected() {
        return newPokemon.getSpecies() != null;
    }

    public Pokemon getTestPokemon() {
        return testPokemon;
    }

    public Pokemon getNewPokemon() {
        return newPokemon;
    }

    public void setNewPokemon(Pokemon newPokemon) {
        this.newPokemon = newPokemon;
    }

    public Stats getInitEVs() {
        return initEVs;
    }

    public void setInitEVs(Stats initEVs) {
        this.initEVs = initEVs;
    }

    public boolean isIvsCalculated() {
        return ivsCalculated;
    }

    public void setIvsCalculated(boolean ivsCalculated) {
        this.ivsCalculated = ivsCalculated;
    }

    public boolean isIvsConsistent() {
        return ivsConsistent;
    }

    public void setIvsConsistent(boolean ivsConsistent) {
        this.ivsConsistent = ivsConsistent;
    }

}
