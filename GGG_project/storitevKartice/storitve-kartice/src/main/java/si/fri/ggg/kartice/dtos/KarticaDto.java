package si.fri.ggg.kartice.dtos;

public class KarticaDto {
    private String vprasanje;
    private String odgovor;
    private int karticaSeznamId;

    /*public KarticaDto() {
    }*/
    public KarticaDto() {}

    public KarticaDto(String odgovor, String vprasanje, int karticaSeznamId) {
        this.odgovor = odgovor;
        this.vprasanje = vprasanje;
        this.karticaSeznamId = karticaSeznamId;
    }

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

    public int getKarticaSeznamId() {

        return karticaSeznamId;
    }

    public void setKarticaSeznamId(int karticaSeznamId) {
        this.karticaSeznamId = karticaSeznamId;
    }
}
