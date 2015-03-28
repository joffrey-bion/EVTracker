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

@FacesValidator(value = "speciesNameExistValidator")
public class SpeciesNameExistValidator implements Validator {
    
    private static final String SPECIES_NAME_ALREADY_EXISTS_MSG = "This name already exists.";

    @EJB
    private SpeciesDao speciesDao;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        String speciesName = (String) value;
        try {
            if (speciesName != null && !speciesName.equals("") && speciesDao.findFirstByName(speciesName) != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        SPECIES_NAME_ALREADY_EXISTS_MSG, null));
            }
        } catch (DaoException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
                    null);
            context.addMessage(component.getClientId(context), message);
        }
    }
}
