INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Petra', 'Kos', 'petrakos', 'petra.kos@hotmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Miha', 'Novak', 'mihanovak', 'miha.novak@gmail.com');
INSERT INTO kartice_seznam (predmet, opis, ustvarjen, uporabnik_id) VALUES ('DS', '1. semester, 1. letnik', '2016-05-28T17:39:44.937', 1);
INSERT INTO kartice_seznam (predmet, opis, ustvarjen, uporabnik_id) VALUES ('PRPO', '1. semester, 3. letnik', '2016-05-28T17:39:44.937', 2);
INSERT INTO kartica (vprasanje, odgovor, kartice_seznam_id) VALUES ('Kaj pomeni PRPO', 'Postopki razvoja programske opreme', 2);
INSERT INTO kartica (vprasanje, odgovor, kartice_seznam_id) VALUES ('Kaj pomeni DS', 'Diskretne strukture.',1);
INSERT INTO kartica (vprasanje, odgovor, kartice_seznam_id) VALUES ('Kateri vrednosti ima binarno zapisano število', '0 in 1', 1);
INSERT INTO kartica (vprasanje, odgovor, kartice_seznam_id) VALUES ('Kaj pomeni JAR', 'Java archive', 2);
INSERT INTO kartica (vprasanje, odgovor, kartice_seznam_id) VALUES ('Kaj počnemo pri predmetu na vajah', 'Projekt', 2);
