package si.fri.ggg.kartice.dtos;

import java.security.Timestamp;

public class KarticeSeznamDto {
    //private Kartica kartica;

    private String opis;
    private String predmet;
    private Timestamp ustvarjen;

    public KarticeSeznamDto(String opis, String predmet, Timestamp ustvarjen) {
        this.opis = opis;
        this.predmet = predmet;
        this.ustvarjen = ustvarjen;
    }


    public String getOpis() {
        return this.opis;
    }

    public String getPredmet() {
        return this.predmet;
    }

    public Timestamp getUstvarjen() {
        return this.ustvarjen;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }
}
