package com.jbion.web.evstracker.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.jbion.web.evstracker.model.Nature;
import com.jbion.web.evstracker.model.Stat;

@ManagedBean
@ApplicationScoped
public class Data {

    public Stat[] getStats() {
        return Stat.values();
    }
    
    public int getNbStats() {
        return Stat.values().length;
    }

    public Nature[] getNatures() {
        return Nature.values();
    }
}
