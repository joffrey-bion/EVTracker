package com.jbion.web.evstracker.beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;

import com.jbion.web.evstracker.dao.DaoException;
import com.jbion.web.evstracker.dao.UserDao;
import com.jbion.web.evstracker.entities.User;

@ManagedBean
@ViewScoped
public class SignInBean implements Serializable {
    
    private static final String DEFAULT_REDIRECT_URL_AFTER_SIGNIN = "/dashboard.xhtml";

    private String login;
    private String password;
    private String originalURL;

    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalURL = (String) externalContext.getRequestMap().get(
                RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + DEFAULT_REDIRECT_URL_AFTER_SIGNIN;
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(
                    RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        }
    }

    public void signIn() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        FacesMessage message = null;
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                externalContext.getSessionMap().remove("user");
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unknown username", "Please join us!");
                System.out.println("Login failed: unknown username " + login);
            } else if (!user.isPasswordCorrect(password)) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password", null);
                System.out.println("Login failed: incorrect password " + password);
            } else {
                externalContext.getSessionMap().put("user", user);
                externalContext.redirect(originalURL);
                message = new FacesMessage("Welcome back, " + login + "!");
                System.out.println("Login successful for " + login);
            }
        } catch (DaoException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Unexpected database error: " + e.getMessage());
        } catch (IOException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Unexpected I/O error: " + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage("signInForm", message);
        }
    }

    /*
     * public void login() throws IOException { FacesContext context =
     * FacesContext.getCurrentInstance(); ExternalContext externalContext =
     * context.getExternalContext(); HttpServletRequest request =
     * (HttpServletRequest) externalContext.getRequest();
     * 
     * try { request.login(login, password); User user = userDao.find(login,
     * password); externalContext.getSessionMap().put("user", user);
     * externalContext.redirect(originalURL); } catch (ServletException e) { //
     * Handle unknown username/password in request.login(). context.addMessage(null,
     * new FacesMessage("Unknown login")); } }
     * 
     * public void logout() throws IOException { ExternalContext externalContext =
     * FacesContext.getCurrentInstance().getExternalContext();
     * externalContext.invalidateSession();
     * externalContext.redirect(externalContext.getRequestContextPath() +
     * "/login.xhtml"); }
     */
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
