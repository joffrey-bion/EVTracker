package com.jbion.web.evstracker.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final String ENCRYPT_ALGORITHM = "SHA-256";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login")
    @NotNull(message = "Username required")
    @Size(min = 3, max = 20, message = "The username must be 3 to 20 character long.")
    @Pattern(regexp = "[a-zA-Z]+", message = "Incorrect username (only standard letters allowed)")
    private String login;

    @Column(name = "email")
    @NotNull(message = "Email address required")
    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "Invalid email address")
    private String email;

    @Column(name = "password")
    @NotNull(message = "Password required")
    @Size(min = 6, message = "Password too short (min 6 characters)")
    @Pattern(regexp = ".*(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", message = "Password unsecure (must contain a digit, a lowercase letter, and an uppercase letter)")
    private String password;

    @Column(name = "sign_up_date")
    private Timestamp signUpDate;

    @OneToMany(mappedBy = "owner", targetEntity = Pokemon.class, fetch = FetchType.EAGER)
    private List<Pokemon> pokemon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        final ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ENCRYPT_ALGORITHM);
        passwordEncryptor.setPlainDigest(false);
        this.password = passwordEncryptor.encryptPassword(password);
    }

    public boolean isPasswordCorrect(String plainPassword) {
        final ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ENCRYPT_ALGORITHM);
        passwordEncryptor.setPlainDigest(false);
        return passwordEncryptor.checkPassword(plainPassword, password);
    }

    public Timestamp getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Timestamp signUpDate) {
        this.signUpDate = signUpDate;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public String toString() {
        return login + " (" + email + ")";
    }
}
