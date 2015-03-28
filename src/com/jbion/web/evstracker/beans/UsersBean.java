package com.jbion.web.evstracker.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.jbion.web.evstracker.dao.UserDao;
import com.jbion.web.evstracker.entities.User;

@ManagedBean
@ViewScoped
public class UsersBean {

    private Integer nbUsers;
    private List<User> users;

    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init() {
        users = userDao.list();
        nbUsers = userDao.getTotalNumber();
    }

    public List<User> getUsers() {
        return users;
    }

    public Integer getNbUsers() {
        return nbUsers;
    }
}
