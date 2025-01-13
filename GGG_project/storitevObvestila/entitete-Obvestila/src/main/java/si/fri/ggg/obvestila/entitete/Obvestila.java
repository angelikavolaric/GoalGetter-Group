package si.fri.ggg.obvestila.entitete;

public class Obvestila {

    private String zadeva; // Subject or topic of the notification
    private String sporocilo; // Message or content of the notification

    // Constructor to initialize the Obvestila object with subject and message
    public Obvestila(String zadeva, String sporocilo) {
        this.zadeva = zadeva;
        this.sporocilo = sporocilo;
    }

    // Getter and setter for zadeva (subject)
    public String getZadeva() {
        return zadeva;
    }

    public void setZadeva(String zadeva) {
        this.zadeva = zadeva;
    }

    // Getter and setter for sporocilo (message)
    public String getSporocilo() {
        return sporocilo;
    }

    public void setSporocilo(String sporocilo) {
        this.sporocilo = sporocilo;
    }

    // Method to display the information about the Obvestila object
    @Override
    public String toString() {
        return "Obvestila{" +
                "zadeva='" + zadeva + '\'' +
                ", sporocilo='" + sporocilo + '\'' +
                '}';
    }
}
