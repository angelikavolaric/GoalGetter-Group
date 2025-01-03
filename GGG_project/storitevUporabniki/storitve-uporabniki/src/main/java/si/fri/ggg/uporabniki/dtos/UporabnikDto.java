package si.fri.ggg.uporabniki.dtos;

public class UporabnikDto {
    private String ime;
    private String priimek;
    private String uporabniskoIme;
    private String email;

    public UporabnikDto() {}

    public UporabnikDto(String ime, String priimek, String uporabniskoIme, String email) {
        this.ime = ime;
        this.priimek = priimek;
        this.uporabniskoIme = uporabniskoIme;
        this.email = email;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
