package com.jbion.web.evstracker.model;

public enum Stat {

    HP("HP", "HP"),
    ATTACK("Attack", "Att"),
    DEFENSE("Defense", "Def"),
    SPECIAL_ATTACK("Sp. Att", "SpA"),
    SPECIAL_DEFENSE("Sp. Def", "SpD"),
    SPEED("Speed", "SpE");

    private String fullName;
    private String shortName;

    private Stat(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCode() {
        return shortName.toLowerCase();
    }

    @Override
    public String toString() {
        return fullName;
    }
}
