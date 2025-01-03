
CREATE TABLE karticeSeznam (id SERIAL PRIMARY KEY,predmet VARCHAR(255) NOT NULL,opis VARCHAR(255),ustvarjen TIMESTAMP NOT NULL,uporabnikId INT NOT NULL);


CREATE TABLE kartica (id SERIAL PRIMARY KEY, vprasanje VARCHAR(255) NOT NULL, odgovor TEXT NOT NULL, karticeSeznamId INT NOT NULL, FOREIGN KEY (karticeSeznamId) REFERENCES karticeSeznam (id));
                                --#FOREIGN KEY (uporabnikId) REFERENCES uporabnik (id)

INSERT INTO karticeSeznam (predmet, opis, ustvarjen, uporabnikId) VALUES ('Undefined', '', '2016-05-28T17:39:44.937', 0);
INSERT INTO karticeSeznam (predmet, opis, ustvarjen, uporabnikId) VALUES ('DS', '1. semester, 1. letnik', '2016-05-28T17:39:44.937', 1);
INSERT INTO karticeSeznam (predmet, opis, ustvarjen, uporabnikId) VALUES ('PRPO', '1. semester, 3. letnik', '2016-05-28T17:39:44.937', 2);


INSERT INTO kartica (vprasanje, odgovor, karticeSeznamId) VALUES ('Kaj pomeni PRPO', 'Postopki razvoja programske opreme', 2);
INSERT INTO kartica (vprasanje, odgovor, karticeSeznamId) VALUES ('Kaj pomeni DS', 'Diskretne strukture.', 1);
INSERT INTO kartica (vprasanje, odgovor, karticeSeznamId) VALUES ('Kateri vrednosti ima binarno zapisano število', '0 in 1', 1);
INSERT INTO kartica (vprasanje, odgovor, karticeSeznamId) VALUES ('Kaj pomeni JAR', 'Java archive', 2);
INSERT INTO kartica (vprasanje, odgovor, karticeSeznamId) VALUES ('Kaj počnemo pri predmetu na vajah', 'Projekt', 2);
