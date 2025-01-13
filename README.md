# GoalGetter-Group

## O projektu
Aplikacija za spodbudo k učenju, preko katere uporabnik izdeluje učne kartice,
se iz njih uči, beleži napredek in ga deli s prijatelji ter z njimi tekmuje. 


## Docker vrata
**storitevKartice:** \
&emsp; karticeContainer: *8081* \
&emsp; karticeDatabase: *5432*

**storitevUporabniki:** \
&emsp; uporabnikiContainer: *8082* \
&emsp; uporabnikiDatabase: *5434*

**storitevBelezenje:** \
&emsp; uporabnikiContainer: *8083* \
&emsp; uporabnikiDatabase: *5435*

**storitevDeljenje:** \
&emsp; uporabnikiContainer: *8087* \
&emsp; uporabnikiDatabase: *5439*

**storitevCasovnik:** \
&emsp; uporabnikiContainer: *8088* \
&emsp; uporabnikiDatabase: *5440*

**storitevObvestila:** \
&emsp; uporabnikiContainer: *8090* \
&emsp; uporabnikiDatabase: *5442*

---
mogoče se za zagon dockerjev rabi za images:

docker build -t ggg_project-storitevkartice:latest . \
docker build -t ggg_project-storitevuporabniki:latest .
