package si.fri.kartice.dtos;

public class KarticaDto {
    private String vprasanje;
    private String odgovor;

    public KarticaDto() {
    }

    public KarticaDto(String vprasanje) {
        this.vprasanje = vprasanje;
    } //konstruktor

    public String getVprasanje() {

        return vprasanje;
    }

    public void setVprasanje(String vprasanje) {

        this.vprasanje = vprasanje;
    }

    public String getOdgovor() {

        return odgovor;
    }

    public void setOdgovor(String odgovor) {

        this.odgovor = odgovor;
    }
}
