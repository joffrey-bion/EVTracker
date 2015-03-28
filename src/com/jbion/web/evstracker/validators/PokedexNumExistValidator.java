package com.jbion.web.evstracker.validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.jbion.web.evstracker.dao.DaoException;
import com.jbion.web.evstracker.dao.SpeciesDao;

@FacesValidator(value = "pokedexNumExistValidator")
public class PokedexNumExistValidator implements Validator {

    private static final String POKEDEX_NUM_ALREADY_EXISTS_MSG = "This pokedex number already exists.";

    @EJB
    private SpeciesDao speciesDao;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Integer pokedexNum = (Integer) value;
        try {
            if (pokedexNum != null && speciesDao.findFirstByPokedexNum(pokedexNum) != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        POKEDEX_NUM_ALREADY_EXISTS_MSG, null));
            }
        } catch (DaoException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            context.addMessage(component.getClientId(context), message);
        }
    }
}
