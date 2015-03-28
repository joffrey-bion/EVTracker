package com.jbion.web.evstracker.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jbion.web.evstracker.dao.DaoException;
import com.jbion.web.evstracker.dao.UserDao;
import com.jbion.web.evstracker.entities.User;

@ManagedBean
@ViewScoped
public class SignUpBean implements Serializable {

    private User user;

    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public void signUp() {
        initSignupDate();
        FacesMessage message = null;
        try {
            userDao.create(user);
            message = new FacesMessage("Welcome, " + user.getLogin() + "!");
        } catch (final DaoException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure", "Unexpected database error: "
                    + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage("signUpForm", message);
        }
    }

    private void initSignupDate() {
        final Timestamp date = new Timestamp(System.currentTimeMillis());
        user.setSignUpDate(date);
    }

    public User getUser() {
        return user;
    }
}
