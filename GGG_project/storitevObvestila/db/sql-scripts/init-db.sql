CREATE TABLE IF NOT EXISTS obvestila (id SERIAL PRIMARY KEY, zadeva VARCHAR(255) NOT NULL, sporocilo TEXT NOT NULL);
                                --#FOREIGN KEY (uporabnikId) REFERENCES uporabnik (id)





INSERT INTO obvestila (zadeva, sporocilo) VALUES ('6783be8c5be34b0f9660d627', 'Test obvestila');
