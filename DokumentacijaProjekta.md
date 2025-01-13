
# Dokumentacija za Projekt

---
## Kazalo

1. [Uvod](#uvod)
2. [Arhitektura Aplikacije](#arhitektura-aplikacije)
3. [Tehnologije](#tehnologije)
4. [Navodila za Namestitev](#navodiola-za-namestitev)
5. [Uporaba Aplikacije](#uporaba-aplikacije)


---
## Uvod
Dokumentacija opisuje aplikacijo Goal getter group, ki temelji na arhitekturi mikrostoritev uporabljene 
za obdelovanje in streženje podatkov. Aplikacija uporablja **KumuluzEE**, PostgreSQL, Docker.........

### Namen aplikacije

.............................
Aplikacija za spodbudo k učenju, preko katere uporabnik izdeluje učne kartice,
se iz njih uči, beleži napredek in ga deli s prijatelji ter z njimi tekmuje. 
---
## Arhitektura Aplikacije
### Mikrostoritve
Vsaka naslednjih mikrostoritev opravlja specifično nalogo. Mikrostoritve komunicirajo preko API HTTP klicev 
in uporabljajo JPA za shranjevanje podatkov v PostgreSQL podatkovni bazi.

Mikrostoritev 1: **Beleženje**
* storitev upravlja s podatki o urah učenja.
* omogoča ustvarjanje, urejanje, brisanje in pridobivanje posameznih vnosov ur učenja.
* prav tako lahko uporabnik ustvarja sezname ter z njimi podobno manipulira.
\

Mikrostoritev 2: **Deljenje**
* storitev upravlja s podatki o ciljih in napredku učenja.
* uporabnik lahko izve ali je dosegel svoj cilj ali ne

\
Mikrostoritev 3: **Kartice**
* storitev upravlja s podatki o karticah.
* omogoča ustvarjanje, urejanje, brisanje in pridobivanje posameznih kartic za učenje.
* prav tako lahko uporabnik ustvarja sezname ter z njimi podobno manipulira.
\

Mikrostoritev 4: **Uporabnik**
* ta storitev obravnava podatke o uporabnikih.
* omogoča registracijo uporabnikov, pridobivanje podatkov o uporabniku in 
poizvedovanje o uporabnikovih vnosih.
\

Mikrostoritev 5: **Sporočila**
* mikrostoritev kliče zunanji api in hkrati shranjuje podatke na bazo aplikacije
* omogoča ustvarjanje, brisanje in pridobivanje posameznih obvestil.

\
Mikrostoritev 6: **Časovnik**
* storitev upravlja s podatki o časovnikih.
* omogoča ustvarjanje, brisanje in pridobivanje posameznih časovnikov.

### Tehnologije
* Maven: Orodje za upravljanje odvisnosti in gradnjo projekta.
* KumuluzEE: Razvojni okvir za mikrostoritve
* JPA (Java Persistence API): Omogoča objektno-relacijsko preslikavo 
in obvladovanje podatkov v relacijskih bazah.
* EclipseLink: JPA implementacija, ki se uporablja za povezovanje s podatkovnimi bazami.
* PostgreSQL: Open-source elacijska podatkovna baza, ki hrani podatke aplikacije.
* Reast API: Mikrostoritve komunicirajo med seboj in z uporabnikom preko HTTP zahtev (GET, POST, PUT, DELETE).




---

## Navodila za Namestitev
Za zagon potrebujete Maven JDK 11+, PostgreSQL in Docker 
1. Prenesite Kodo:
Klonirajte repozitorij iz GitHub z ukazom:

``` git clone https://github.com/angelikavolaric/GoalGetter-Group.git ``` 
2. Zaženite ukaz:
``` mvn clean package ```
3. Ter zgradite in zaženite vse docker vsebnike:
\
``` cd <poljubnaStoritev> ```
\
   ``` docker-compose build ```
\
   ```  docker-compose up```



---
## Uporaba Aplikacije
Po zagonu aplikacije bodo mikrostoritve na voljo za uporabo preko HTTP klicev.
Porti za posamezne storitve so navedeni v [README datoteki](README.md). 
\
Podrobnejšo dokumentacijo API-jev si lahko ogledate tukaj:

|  [Beleženje](ApiDokumentacije/storitevBelezenje.yml)  |     [Deljenje](ApiDokumentacije/storitevDeljenje.yml)     |   [Sporočila](ApiDokumentacije/storitevSporocila.yml)   |
|:-----------------------------------------------------:|:---------------------------------------------------------:|:-------------------------------------------------------:|
|  **[Kartice](ApiDokumentacije/storitevKartice.yml)**  |  **[Uporabnik](ApiDokumentacije/storitevUporabnik.yml)**  |  **[Časovnik](ApiDokumentacije/storitevCasovnik.yml)**  |

---




