
CREATE TABLE IF NOT EXISTS ureSeznam (id SERIAL PRIMARY KEY,
                        opis VARCHAR(255) NOT NULL,
                        ustvarjen TIMESTAMP NOT NULL,
                        predmet VARCHAR(255) DEFAULT 'undefined',
                        uporabnikId INT NOT NULL);


CREATE TABLE IF NOT EXISTS ure (id SERIAL PRIMARY KEY,
                  "vnosi ur" INT NOT NULL,
                  "vnosi min" INT NOT NULL,
                  ureSeznamId INT NOT NULL,
                  ustvarjen TIMESTAMP NOT NULL,
                  uporabnikId INT NOT NULL
                  FOREIGN KEY (ureSeznamId) REFERENCES ureSeznam (id));
--#FOREIGN KEY (uporabnikId) REFERENCES uporabnik (id)

INSERT INTO ureSeznam (opis, ustvarjen, predmet, uporabnikId) VALUES ('A', '2020-09-26T10:30:54.937', 'ars', 0);
INSERT INTO ureSeznam (opis, ustvarjen, predmet, uporabnikId) VALUES ('Učenje pri enem predmetu', '2020-09-26T10:30:54.937', 'DS', 1);
INSERT INTO ureSeznam (opis, ustvarjen, predmet, uporabnikId) VALUES ('Učenje pri vseh predmetih', '2020-09-26T10:30:54.937', '', 2);


INSERT INTO ure ("vnosi ur", "vnosi min", ureSeznamId, ustvarjen, uporabnikId) VALUES (1, 12, 2, '2024-02-03T17:39:44.937', 0);
INSERT INTO ure ("vnosi ur", "vnosi min", ureSeznamId, ustvarjen, uporabnikId) VALUES (0, 30, 1, '2024-02-03T17:39:44.937', 1);
INSERT INTO ure ("vnosi ur", "vnosi min", ureSeznamId, ustvarjen, uporabnikId) VALUES (2, 0,1,'2024-02-03T17:39:44.937', 1);
INSERT INTO ure ("vnosi ur", "vnosi min", ureSeznamId, ustvarjen, uporabnikId) VALUES (1, 30, 2,'2024-02-03T17:39:44.937', 2);
INSERT INTO ure ("vnosi ur", "vnosi min", ureSeznamId, ustvarjen, uporabnikId) VALUES (0, 45,2,'2024-02-03T17:39:44.937', 2);