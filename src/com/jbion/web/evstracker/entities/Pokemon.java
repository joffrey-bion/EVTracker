package com.jbion.web.evstracker.entities;

import javax.persistence.*;
import javax.validation.Valid;

import com.jbion.web.evstracker.model.Nature;

@Entity
@Table(name = "Pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pokemon_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne(optional = false)
    @JoinColumn(name = "species", referencedColumnName = "pokedex_number")
    private Species species;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    private User owner;

    @Column(name = "nature")
    @Enumerated(EnumType.STRING)
    private Nature nature;

    @Embedded
    @Valid
    @AttributeOverrides({@AttributeOverride(name = "hp", column = @Column(name = "iv_min_hp")),
        @AttributeOverride(name = "att", column = @Column(name = "iv_min_att")),
        @AttributeOverride(name = "def", column = @Column(name = "iv_min_def")),
        @AttributeOverride(name = "spa", column = @Column(name = "iv_min_spa")),
        @AttributeOverride(name = "spd", column = @Column(name = "iv_min_spd")),
        @AttributeOverride(name = "spe", column = @Column(name = "iv_min_spe"))})
    private Stats minIvs;

    @Embedded
    @Valid
    @AttributeOverrides({@AttributeOverride(name = "hp", column = @Column(name = "iv_max_hp")),
        @AttributeOverride(name = "att", column = @Column(name = "iv_max_att")),
        @AttributeOverride(name = "def", column = @Column(name = "iv_max_def")),
        @AttributeOverride(name = "spa", column = @Column(name = "iv_max_spa")),
        @AttributeOverride(name = "spd", column = @Column(name = "iv_max_spd")),
        @AttributeOverride(name = "spe", column = @Column(name = "iv_max_spe"))})
    private Stats maxIvs;

    @JoinColumn(name = "last_checkpoint")
    @OneToOne
    @Valid
    private Checkpoint lastCheckpoint;

    public Pokemon() {
        minIvs = new Stats();
        maxIvs = new Stats();
        lastCheckpoint = new Checkpoint();
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        minIvs.prePersist();
        maxIvs.prePersist();
    }

    @PostLoad
    private void postLoad() {
        minIvs.postLoad();
        maxIvs.postLoad();
    }

    public String getName() {
        return nickname == null ? species.getName() : nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public Stats getMinIvs() {
        return minIvs;
    }

    public void setMinIvs(Stats minIvs) {
        this.minIvs = minIvs;
    }

    public Stats getMaxIvs() {
        return maxIvs;
    }

    public void setMaxIvs(Stats maxIvs) {
        this.maxIvs = maxIvs;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Checkpoint getLastCheckpoint() {
        return lastCheckpoint;
    }

    public void setLastCheckpoint(Checkpoint lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
    }

    @Override
    public String toString() {
        return getName();
    }
}
