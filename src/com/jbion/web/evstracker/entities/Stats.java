package com.jbion.web.evstracker.entities;

import java.util.HashMap;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.jbion.web.evstracker.model.Stat;

@Embeddable
public class Stats extends HashMap<Stat, Integer> {

    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer hp;
    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer att;
    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer def;
    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer spa;
    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer spd;
    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    private Integer spe;

    public Stats() {
        super();
        for (final Stat s : Stat.values()) {
            put(s, 0);
        }
    }

    void postLoad() {
        put(Stat.HP, hp);
        put(Stat.ATTACK, att);
        put(Stat.DEFENSE, def);
        put(Stat.SPECIAL_ATTACK, spa);
        put(Stat.SPECIAL_DEFENSE, spd);
        put(Stat.SPEED, spe);
    }

    void prePersist() {
        hp = get(Stat.HP);
        att = get(Stat.ATTACK);
        def = get(Stat.DEFENSE);
        spa = get(Stat.SPECIAL_ATTACK);
        spd = get(Stat.SPECIAL_DEFENSE);
        spe = get(Stat.SPEED);
    }
}
