package si.fri.ggg.belezenje.dtos;

import java.time.Instant;

public class UreDto {
    private String vnosiUr;
    private String vnosiMin;


    private Instant dodan;

    public UreDto() {}

    public UreDto(String vnosi, String vnosiMin, Instant dodan) {
        this.vnosiUr = vnosi;
        this.vnosiMin = vnosiMin;
        this.dodan = dodan;
    }

    public String getVnosiUr() {
        return vnosiUr;
    }
    public void setVnosiUr(String vnosi) {
        this.vnosiUr = vnosi;
    }

    public Instant getDodan() {
        return dodan;
    }
    public void setDodan(Instant dodan) {
        this.dodan = dodan;
    }

    public String getVnosiMin() {return vnosiMin;}
    public void setVnosiMin(String vnosiMin) {this.vnosiMin = vnosiMin;}



}
