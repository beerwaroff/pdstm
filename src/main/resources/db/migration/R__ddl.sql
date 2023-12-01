CREATE SCHEMA IF NOT EXISTS pdstm;

CREATE TABLE pdstm.users (
    email varchar(64) NOT NULL,
    password varchar(64),
    is_active boolean DEFAULT FALSE,
    code varchar(36),
    UNIQUE (email, code),
    PRIMARY KEY (email)
);

CREATE TABLE pdstm.risk_types (
    id varchar(4) NOT NULL,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.negative_consequences (
    id varchar NOT NULL,
	name varchar NOT NULL,
    risk_id varchar(512) NOT NULL,
    UNIQUE (name, risk_id),
    FOREIGN KEY (risk_id) REFERENCES pdstm.risk_types (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_negative_consequences_risk_id_index ON pdstm.negative_consequences (risk_id);

CREATE TABLE pdstm.influence_objects (
    id serial,
    name varchar NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.impact_types (
    id varchar(4) NOT NULL,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.influence_objects_impact_types (
    id serial,
    influence_object_id integer NOT NULL,
    impact_type_id varchar NOT NULL,
    UNIQUE (influence_object_id, impact_type_id),
    FOREIGN KEY (influence_object_id) REFERENCES pdstm.influence_objects (id),
    FOREIGN KEY (impact_type_id) REFERENCES pdstm.impact_types (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_influence_object_impact_types_influence_object_id_index ON pdstm.influence_objects_impact_types (influence_object_id);
CREATE INDEX pdstm_influence_object_impact_types_impact_type_id_index ON pdstm.influence_objects_impact_types (impact_type_id);

CREATE TABLE pdstm.violator_levels (
    id varchar(4) NOT NULL,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.violator_categories (
    id serial,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.violator_levels_categories (
    id serial,
    level_id varchar(4) NOT NULL,
    category_id integer NOT NULL,
    UNIQUE (level_id, category_id),
    FOREIGN KEY (level_id) REFERENCES pdstm.violator_levels (id),
    FOREIGN KEY (category_id) REFERENCES pdstm.violator_categories (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_violator_levels_categories_level_id_index ON pdstm.violator_levels_categories (level_id);
CREATE INDEX pdstm_violator_levels_categories_category_id_index ON pdstm.violator_levels_categories (category_id);

CREATE TABLE pdstm.violators (
    id serial,
    name varchar(128) NOT NULL,
    violator_level_category_id integer NOT NULL,
    UNIQUE (name),
    FOREIGN KEY (violator_level_category_id) REFERENCES pdstm.violator_levels_categories (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_violators_level_category_id_index ON pdstm.violators (violator_level_category_id);

CREATE TABLE pdstm.implementation_goals (
    id serial,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.violator_implementation_goals (
    id serial,
    violator_id integer NOT NULL,
    implementation_goal_id integer NOT NULL,
    UNIQUE (violator_id, implementation_goal_id),
    FOREIGN KEY (violator_id) REFERENCES pdstm.violators (id)
);

CREATE INDEX pdstm_violator_implementation_goals_violator_id_index ON pdstm.violator_implementation_goals (violator_id);
CREATE INDEX pdstm_violator_implementation_goals_implementation_goal_id_index ON pdstm.violator_implementation_goals (implementation_goal_id);