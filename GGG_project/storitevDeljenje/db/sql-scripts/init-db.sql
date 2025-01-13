CREATE TABLE IF NOT EXISTS cilj (id SERIAL PRIMARY KEY,
                                 opis VARCHAR(255) NOT NULL,
    "cilj ur" INT NOT NULL,
    "cilj min" INT NOT NULL,
    ustvarjen TIMESTAMP NOT NULL,
    uporabnikId INT NOT NULL);



INSERT INTO cilj (opis, "cilj ur",  "cilj min", ustvarjen, uporabnikId) VALUES ('cilj1',3, 0, '2020-09-26T10:30:54.937', 1);
INSERT INTO cilj (opis,"cilj ur",  "cilj min", ustvarjen, uporabnikId) VALUES ( 'Moj prvi cilj je:', 1, 30, '2020-09-26T10:30:54.937', 3);
INSERT INTO cilj (opis, "cilj ur",  "cilj min", ustvarjen, uporabnikId) VALUES ('test,', 4, 30, '2020-09-26T10:30:54.937', 2);


