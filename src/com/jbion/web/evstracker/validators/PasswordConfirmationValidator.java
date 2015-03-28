package com.jbion.web.evstracker.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "passwordConfirmationValidator")
public class PasswordConfirmationValidator implements Validator {

    private static final String PASSWORD_FIELD = "passwordComponent";
    private static final String MSG_DIFFERENT_PASSWORDS = "The password and confirmation have to match.";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        final UIInput passwordComponent = (UIInput) component.getAttributes().get(PASSWORD_FIELD);
        final String password = (String) passwordComponent.getValue();
        final String confirmation = (String) value;

        if (confirmation != null && !confirmation.equals(password)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_DIFFERENT_PASSWORDS, null));
        }
    }

}
