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

CREATE TABLE pdstm.implementation_methods (
    id varchar(4) NOT NULL,
    name varchar NOT NULL,
    UNIQUE (name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.violator_lc_methods (
    id serial,
    violator_lc_id integer,
    impl_method_id varchar(4),
    UNIQUE (violator_lc_id, impl_method_id),
    FOREIGN KEY (violator_lc_id) REFERENCES pdstm.violator_levels_categories (id),
    FOREIGN KEY (impl_method_id) REFERENCES pdstm.implementation_methods (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_violator_lc_methods_violator_lc_id_index ON pdstm.violator_lc_methods (violator_lc_id);
CREATE INDEX pdstm_violator_lc_methods_impl_method_id_index ON pdstm.violator_lc_methods (impl_method_id);

CREATE TABLE pdstm.objects_methods (
    id serial,
    object_id integer NOT NULL,
    method_id varchar(4) NOT NULL,
    UNIQUE (object_id, method_id),
    FOREIGN KEY (object_id) REFERENCES pdstm.influence_objects (id),
    FOREIGN KEY (method_id) REFERENCES pdstm.implementation_methods (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_objects_methods_object_id_index ON pdstm.objects_methods (object_id);
CREATE INDEX pdstm_objects_methods_method_id_index ON pdstm.objects_methods (method_id);

CREATE TABLE pdstm.threats (
    id varchar(16) NOT NULL,
    name varchar NOT NULL,
    UNIQUE (id, name),
    PRIMARY KEY (id)
);

CREATE TABLE pdstm.threats_objects (
    id serial,
    threat_id varchar NOT NULL,
    object_id integer NOT NULL,
    UNIQUE (threat_id, object_id),
    FOREIGN KEY (threat_id) REFERENCES pdstm.threats (id),
    FOREIGN KEY (object_id) REFERENCES pdstm.influence_objects (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_threats_objects_threat_id_index ON pdstm.threats_objects (threat_id);
CREATE INDEX pdstm_threats_objects_object_id_index ON pdstm.threats_objects (object_id);

CREATE TABLE pdstm.threats_nc (
    id serial,
    threat_id varchar NOT NULL,
    nc_id varchar NOT NULL,
    UNIQUE (threat_id, nc_id),
    FOREIGN KEY (threat_id) REFERENCES pdstm.threats (id),
    FOREIGN KEY (nc_id) REFERENCES pdstm.negative_consequences (id),
    PRIMARY KEY (id)
);

CREATE INDEX pdstm_threats_nc_threat_id_index ON pdstm.threats_nc (threat_id);
CREATE INDEX pdstm_threats_nc_nc_id_index ON pdstm.threats_nc (nc_id);