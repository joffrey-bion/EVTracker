package com.jbion.web.evstracker.model;

public enum Nature {

    ADAMANT(Stat.ATTACK, Stat.SPECIAL_ATTACK),
    BASHFUL(),
    BOLD(Stat.DEFENSE, Stat.ATTACK),
    BRAVE(Stat.ATTACK, Stat.SPEED),
    CALM(Stat.SPECIAL_DEFENSE, Stat.ATTACK),
    CAREFUL(Stat.SPECIAL_DEFENSE, Stat.SPECIAL_ATTACK),
    DOCILE,
    GENTLE(Stat.SPECIAL_DEFENSE, Stat.DEFENSE),
    HARDLY,
    HASTY(Stat.SPEED, Stat.DEFENSE),
    IMPISH(Stat.DEFENSE, Stat.SPECIAL_ATTACK),
    JOLLY(Stat.SPEED, Stat.SPECIAL_ATTACK),
    LAX(Stat.DEFENSE, Stat.SPECIAL_DEFENSE),
    LONELY(Stat.ATTACK, Stat.DEFENSE),
    MILD(Stat.SPECIAL_ATTACK, Stat.DEFENSE),
    MODEST(Stat.SPECIAL_ATTACK, Stat.ATTACK),
    NAIVE(Stat.SPEED, Stat.SPECIAL_DEFENSE),
    NAUGHTY(Stat.ATTACK, Stat.SPECIAL_DEFENSE),
    QUIET(Stat.SPECIAL_ATTACK, Stat.SPEED),
    QUIRKY,
    RASH(Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE),
    RELAXED(Stat.DEFENSE, Stat.SPEED),
    SASSY(Stat.SPECIAL_DEFENSE, Stat.SPEED),
    SERIOUS,
    TIMID(Stat.SPEED, Stat.ATTACK);

    private Stat bonus;
    private Stat malus;

    private Nature() {
        this.bonus = null;
        this.malus = null;
    }

    private Nature(Stat increasedStat, Stat decreasedStat) {
        this.bonus = increasedStat;
        this.malus = decreasedStat;
    }

    public double getCoefficient(Stat stat) {
        if (stat == bonus) {
            return 1.1;
        } else if (stat == malus) {
            return 0.9;
        } else {
            return 1.0;
        }
    }

    public String getDisplayName() {
        String name = name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
