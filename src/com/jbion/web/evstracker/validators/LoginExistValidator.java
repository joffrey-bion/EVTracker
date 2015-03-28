package com.jbion.web.evstracker.validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.jbion.web.evstracker.dao.DaoException;
import com.jbion.web.evstracker.dao.UserDao;

@FacesValidator(value = "loginExistValidator")
public class LoginExistValidator implements Validator {

    private static final String LOGIN_ALREADY_EXISTS_MSG = "This username is already used.";

    @EJB
    private UserDao userDao;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        final String login = (String) value;
        try {
            if (login != null && userDao.findByLogin(login) != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LOGIN_ALREADY_EXISTS_MSG,
                        null));
            }
        } catch (final DaoException e) {
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);
            context.addMessage(component.getClientId(context), message);
        }
    }

}
