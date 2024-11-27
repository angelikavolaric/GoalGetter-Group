package si.fri.uporabniki.dtos;

public class UporabnikDto {
    private String ime;
    private String priimek;
    private String email;

    public UporabnikDto() {
    }

    public UporabnikDto(String vprasanje) {
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
