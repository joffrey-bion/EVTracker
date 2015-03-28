package com.jbion.web.evstracker.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.jbion.web.evstracker.dao.SpeciesDao;
import com.jbion.web.evstracker.entities.Species;

@ManagedBean
@ViewScoped
public class SpeciesBean implements Serializable {

    private static final String MSG_ID_NEW_SPECIES_FORM = "newSpeciesForm";
    private static final String MSG_ID_TABLE = "tableMsg";

    private Species newSpecies;

    private Species selectedSpecies;
    private Integer nbSpecies;
    private List<Species> allSpecies;

    @EJB
    private SpeciesDao speciesDao;

    @PostConstruct
    public void init() {
        newSpecies = new Species();
        refreshModel();
    }

    private void refreshModel() {
        allSpecies = speciesDao.list();
        nbSpecies = speciesDao.getTotalNumber();
    }

    public void create() {
        FacesMessage message = null;
        try {
            speciesDao.create(newSpecies);
            refreshModel();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "The species " + newSpecies.getName()
                    + " was successfully created.");
        } catch (final Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", "Unexpected database error: "
                    + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(MSG_ID_NEW_SPECIES_FORM, message);
        }
    }

    public void delete(Species s) {
        FacesMessage message = null;
        try {
            speciesDao.deleteByID(s.getPokedexNum());
            refreshModel();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "The species " + s.getName()
                    + " was successfully deleted.");
        } catch (final Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", "Unexpected database error: "
                    + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(MSG_ID_TABLE, message);
        }
    }

    public void onEdit(RowEditEvent event) {
        final Species s = (Species) event.getObject();
        try {
            speciesDao.update(s);
            refreshModel();
            final FacesMessage msg = new FacesMessage("Edits on " + s + " saved");
            FacesContext.getCurrentInstance().addMessage(MSG_ID_TABLE, msg);
        } catch (final Exception e) {
            final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Edits on " + s
                    + " could not be saved", "Database error: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(MSG_ID_TABLE, msg);
        }
    }

    public void onCancel(RowEditEvent event) {
        final Species s = (Species) event.getObject();
        final FacesMessage msg = new FacesMessage("Edits on " + s + " cancelled");
        FacesContext.getCurrentInstance().addMessage(MSG_ID_TABLE, msg);
    }

    public Species getNewSpecies() {
        return newSpecies;
    }

    public List<Species> getAllSpecies() {
        return allSpecies;
    }

    public Integer getNbSpecies() {
        return nbSpecies;
    }

    public Species getSelectedSpecies() {
        return selectedSpecies;
    }

    public void setSelectedSpecies(Species selectedSpecies) {
        this.selectedSpecies = selectedSpecies;
    }
}
