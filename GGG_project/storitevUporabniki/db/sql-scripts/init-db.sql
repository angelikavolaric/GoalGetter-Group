CREATE TABLE IF NOT EXISTS uporabnik (id SERIAL PRIMARY KEY, ime VARCHAR(255) NOT NULL, priimek TEXT NOT NULL,uporabnisko_ime VARCHAR(255) NOT NULL,email VARCHAR(255) NOT NULL);


INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Petra', 'Kos', 'petrakos', 'petra.kos@hotmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Miha', 'Novak', 'mihanovak', 'miha.novak@gmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Klara', 'Kos', 'klaraakos', 'klara.kos@hotmail.com');
INSERT INTO uporabnik (ime, priimek, uporabnisko_ime, email) VALUES ('Marko', 'Novak', 'markonovak', 'marko.novak@gmail.com');