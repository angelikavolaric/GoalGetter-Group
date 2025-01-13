CREATE TABLE IF NOT EXISTS obvestila
s (id SERIAL PRIMARY KEY, outerAPIid VARCHAR(255) NOT NULL, opis TEXT NOT NULL, zacetek TEXT NOT NULL, konec TEXT NOT NULL);
                                --#FOREIGN KEY (uporabnikId) REFERENCES uporabnik (id)




INSERT INTO obvestila
s (outerAPIid, opis, zacetek, konec) VALUES ('6783be8c5be34b0f9660d627', 'Test Obvestila
', '2025-01-12T14:00:00Z', '2025-01-12T15:00:00Z' );
