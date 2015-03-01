package com.jbion.web.evstracker.entities;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "Checkpoints")
public class Checkpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkpoint_id")
    private Long id;

    @JoinColumn(name = "pokemon")
    @ManyToOne(optional = false)
    private Pokemon pokemon;

    @Column(name = "level")
    private Integer level;

    @Valid
    @Embedded
    private Stats stats;

    public Checkpoint() {
        stats = new Stats();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

}
