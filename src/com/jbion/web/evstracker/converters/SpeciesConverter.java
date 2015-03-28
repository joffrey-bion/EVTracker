package com.jbion.web.evstracker.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.jbion.web.evstracker.dao.SpeciesDao;
import com.jbion.web.evstracker.entities.Species;

@FacesConverter("speciesConverter")
public class SpeciesConverter implements Converter {

    @EJB
    private SpeciesDao speciesDao;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        System.out.println("species converter getAsObject('" + submittedValue + "')");
        return speciesDao.findFirstByName(submittedValue);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        System.out.println("species converter getAsString(" + ((Species) value).getName() + ")");
        if (value instanceof Species) {
            return ((Species) value).getName();
        } else {
            return null;
        }
    }

}
