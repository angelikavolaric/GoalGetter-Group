package si.fri.ggg.kartice.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name = "kartica")
@NamedQueries(value = {
        @NamedQuery(name = "Kartica.getAll", query = "SELECT a FROM Kartica a")
})
public class Kartica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vprasanje")
    private String vprasanje;

    @Column(name = "odgovor")
    private String odgovor;

    // Many-to-One relationship: A Kartica belongs to one KarticeSeznam
    @JsonbTransient  /////////////////////7
    @ManyToOne
    @JoinColumn(name = "karticeSeznamId", referencedColumnName = "id", nullable = false)  // Foreign key to KarticeSeznam
    private KarticeSeznam karticeSeznam;

    // Getters and setters
    public Integer getId() {
        return id;
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

    public KarticeSeznam getKarticeSeznam() {
        return karticeSeznam;
    }

    public void setKarticeSeznam(KarticeSeznam karticeSeznam) {
        this.karticeSeznam = karticeSeznam;
    }

}

/*package si.fri.ggg.kartice.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "kartica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kartica.getAll", query = "SELECT a FROM Kartica a")
        })
public class Kartica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vprasanje")
    private String vprasanje;

    @Column(name = "odgovor")
    private String odgovor;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "kartica_id")
    private Kartica kartica;

    public Integer getId() {
        return id;
    }

   // public void setId(Integer id) {
    //    this.id = id;
    //}

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

    //public Kartica pridobiKartico() {
     //   return kartica;
    //}

    //public void setKartica(Kartica kartice) {
    //    this.kartica = kartice;
   // }
}*/
