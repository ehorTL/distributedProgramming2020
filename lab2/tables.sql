use world_map;
create table country(
    country_id int auto_increment,
    country_name varchar(255),
    country_code varchar(20),
    CONSTRAINT pk_country_id PRIMARY KEY (country_id)
)

create table city(
    city_id int auto_increment,
    country_id int,
    city_code varchar(20),
    city_name varchar(255),
    city_population bigint,
    city_iscapital tinyint(1),
    constraint pk_city_id primary key (city_id),
    CONSTRAINT fk_pgid FOREIGN KEY (country_id) REFERENCES country(country_id) ON DELETE CASCADE
)

ALTER TABLE country
ADD CONSTRAINT uniq_country_code UNIQUE (country_code); 
