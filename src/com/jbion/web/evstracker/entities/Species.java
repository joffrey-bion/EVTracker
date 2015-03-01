package com.jbion.web.evstracker.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Species")
public class Species {

    @Id
    @Column(name = "pokedex_number")
    @NotNull(message = "Pok�dex number required")
    @Min(value = 1, message = "The pok�dex number can't be below 1.")
    @Max(value = 999, message = "The pok�dex number can't be above 999.")
    private Integer pokedexNum;

    @Column(name = "name")
    @NotNull(message = "Species name required")
    private String name;

    @Valid
    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "hp", column = @Column(name = "base_hp")),
            @AttributeOverride(name = "att", column = @Column(name = "base_att")),
            @AttributeOverride(name = "def", column = @Column(name = "base_def")),
            @AttributeOverride(name = "spa", column = @Column(name = "base_spa")),
            @AttributeOverride(name = "spd", column = @Column(name = "base_spd")),
            @AttributeOverride(name = "spe", column = @Column(name = "base_spe")) })
    private Stats baseStats;
    
    public Species() {
        baseStats = new Stats();
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        baseStats.prePersist();
    }

    @PostLoad
    private void postLoad() {
        baseStats.postLoad();
    }
    
    public String getNum3Digits() {
        return String.format("%03d", pokedexNum);
    }
    
    public String getImgName() {
        return getNum3Digits();
    }

    public Integer getPokedexNum() {
        return pokedexNum;
    }

    public void setPokedexNum(Integer pokedexNum) {
        this.pokedexNum = pokedexNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }

    @Override
    public String toString() {
        return name + "(#" + pokedexNum + ")";
    }
}
