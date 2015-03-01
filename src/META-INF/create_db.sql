DROP DATABASE pokemon_db;

CREATE DATABASE pokemon_db CHARACTER SET 'utf8';
USE pokemon_db;

CREATE TABLE Species (
	species_id INT(11) AUTO_INCREMENT NOT NULL,
	pokedex_number SMALLINT(3) UNSIGNED NOT NULL,
	name VARCHAR(12) NOT NULL,
	version VARCHAR(25) NOT NULL,
	base_hp TINYINT UNSIGNED NOT NULL,
	base_att TINYINT UNSIGNED NOT NULL,
	base_def TINYINT UNSIGNED NOT NULL,
	base_spa TINYINT UNSIGNED NOT NULL,
	base_spd TINYINT UNSIGNED NOT NULL,
	base_spe TINYINT UNSIGNED NOT NULL,
	xp_yield SMALLINT(4) UNSIGNED,
    ev_yield_hp TINYINT(1) UNSIGNED,
    ev_yield_att TINYINT(1) UNSIGNED,
    ev_yield_def TINYINT(1) UNSIGNED,
    ev_yield_spa TINYINT(1) UNSIGNED,
    ev_yield_spd TINYINT(1) UNSIGNED,
    ev_yield_spe TINYINT(1) UNSIGNED,
	CONSTRAINT pk_species PRIMARY KEY (species_id),
	CONSTRAINT u_species_name_version UNIQUE (name, version)
)
ENGINE=INNODB;

CREATE TABLE Users (
	user_id INT(11) AUTO_INCREMENT,
	login VARCHAR(20) NOT NULL,
	password CHAR(56) NOT NULL,
	email VARCHAR(60) NOT NULL,
	sign_up_date DATETIME NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (user_id),
	CONSTRAINT u_user_login UNIQUE (login),
	CONSTRAINT u_user_email UNIQUE (email)
)
ENGINE=INNODB;

CREATE TABLE Pokemon (
	pokemon_id INT(11) AUTO_INCREMENT,
	nickname VARCHAR(10),
	owner INT(11),
	species INT(11),
	nature VARCHAR(15) NOT NULL,
	iv_min_hp TINYINT(2) UNSIGNED,
	iv_min_att TINYINT(2) UNSIGNED,
	iv_min_def TINYINT(2) UNSIGNED,
	iv_min_spa TINYINT(2) UNSIGNED,
	iv_min_spd TINYINT(2) UNSIGNED,
	iv_min_spe TINYINT(2) UNSIGNED,
	iv_max_hp TINYINT(2) UNSIGNED,
	iv_max_att TINYINT(2) UNSIGNED,
	iv_max_def TINYINT(2) UNSIGNED,
	iv_max_spa TINYINT(2) UNSIGNED,
	iv_max_spd TINYINT(2) UNSIGNED,
	iv_max_spe TINYINT(2) UNSIGNED,
	last_checkpoint INT(11),
	CONSTRAINT pk_pokemon PRIMARY KEY (pokemon_id),
	CONSTRAINT fk_pokemon_owner FOREIGN KEY (owner) REFERENCES Users (user_id),
	CONSTRAINT fk_pokemon_species FOREIGN KEY (species) REFERENCES Species (species_id)
)
ENGINE=INNODB;

CREATE TABLE Parties (
	owner INT(11) NOT NULL,
	pokemon1 INT(11),
	pokemon2 INT(11),
	pokemon3 INT(11),
	pokemon4 INT(11),
	pokemon5 INT(11),
	pokemon6 INT(11),
	CONSTRAINT pk_party_owner PRIMARY KEY (owner),
	CONSTRAINT fk_party_owner FOREIGN KEY (owner) REFERENCES Users (user_id),
	CONSTRAINT fk_party_pokemon1 FOREIGN KEY (pokemon1) REFERENCES Pokemon (pokemon_id),
	CONSTRAINT fk_party_pokemon2 FOREIGN KEY (pokemon2) REFERENCES Pokemon (pokemon_id),
	CONSTRAINT fk_party_pokemon3 FOREIGN KEY (pokemon3) REFERENCES Pokemon (pokemon_id),
	CONSTRAINT fk_party_pokemon4 FOREIGN KEY (pokemon4) REFERENCES Pokemon (pokemon_id),
	CONSTRAINT fk_party_pokemon5 FOREIGN KEY (pokemon5) REFERENCES Pokemon (pokemon_id),
	CONSTRAINT fk_party_pokemon6 FOREIGN KEY (pokemon6) REFERENCES Pokemon (pokemon_id)
)
ENGINE=INNODB;

CREATE TABLE Checkpoints (
	checkpoint_id INT(11) AUTO_INCREMENT,
	pokemon INT(11),
	level SMALLINT(3) UNSIGNED,
	hp SMALLINT(3) UNSIGNED,
	att SMALLINT(3) UNSIGNED,
	def SMALLINT(3) UNSIGNED,
	spa SMALLINT(3) UNSIGNED,
	spd SMALLINT(3) UNSIGNED,
	spe SMALLINT(3) UNSIGNED,
	CONSTRAINT pk_checkpoints PRIMARY KEY (checkpoint_id),
	CONSTRAINT u_checkpoints_pokemon_level UNIQUE (pokemon, level),
	CONSTRAINT fk_checkpoints_pokemon FOREIGN KEY (pokemon) REFERENCES Pokemon (pokemon_id)
)
ENGINE=INNODB;
	
ALTER TABLE Pokemon ADD CONSTRAINT fk_pokemon_last_checkpoint FOREIGN KEY (last_checkpoint) REFERENCES Checkpoints (checkpoint_id);
