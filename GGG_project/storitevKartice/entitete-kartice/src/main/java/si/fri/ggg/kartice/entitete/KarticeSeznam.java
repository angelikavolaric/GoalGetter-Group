package si.fri.ggg.kartice.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "karticeSeznam")
@NamedQueries(value = {
        @NamedQuery(name = "KarticeSeznam.getAll", query = "SELECT n FROM KarticeSeznam n"),
        /*@NamedQuery(name = "KarticeSeznam.getAllForUser",
                query = "SELECT n FROM KarticeSeznam n WHERE n.uporabnik = :uporabnik")*/
})
public class KarticeSeznam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "predmet")
    private String predmet;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen",nullable = false, updatable = false)
    private Timestamp ustvarjen;

    /*@ManyToOne
    @JoinColumn(name = "uporabnikId", referencedColumnName = "id", nullable = false)
    private Uporabnik uporabnik;*/

    @Column(name = "uporabnikId")
    private Integer uporabnikId;

    // One-to-Many relationship: A KarticeSeznam has many Kartica entities
    @JsonbTransient /////////////////////7
    @OneToMany(mappedBy = "karticeSeznam", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Kartica> kartice;

    @PrePersist
    private void prePersist() {
        if (ustvarjen == null) {
            ustvarjen = Timestamp.from(Instant.now());  // Set to current time if not provided
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Timestamp getUstvarjen() {
        return ustvarjen;
    }

    // Getter and setter for kartice
    public List<Kartica> getKartice() {
        if (kartice == null) {
            kartice = new ArrayList<>();
        }
        return kartice;
    }

    public List<Kartica> putKartice(Kartica novaKartica) {
        if (kartice == null) {
            kartice = new ArrayList<>();
        }
        novaKartica.setKarticeSeznam(this); // Ensure the Kartica is linked to this KarticeSeznam
        kartice.add(novaKartica);
        return kartice;
    }




    public void setKartice(List<Kartica> kartice) {
        this.kartice = kartice;
    }

}

/*package si.fri.ggg.kartice.entitete;
//package si.fri.ggg.uporabniki.entitete;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kartice-seznam")
@NamedQueries(value =
        {
                @NamedQuery(name = "KarticeSeznam.getAll", query = "SELECT n FROM KarticeSeznam n"),
                @NamedQuery(name = "KarticeSeznam.getAllForUser",
                        query = "SELECT n FROM KarticeSeznam n WHERE n.uporabnik = :uporabnik")
        })
public class KarticeSeznam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "predmet")
    private String predmet;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

   /* @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik; *

    @OneToMany(mappedBy = "karticeSeznam", cascade = CascadeType.ALL)
    private List<Kartica> kartice;

   /* @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })*

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Instant getUstvaren() {
        return ustvarjen;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    /*public Uporabnik getUporabnik() {
        return uporabnik;
    }*/

    /*public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }*
    public List<Kartica> getKartica() {
        if (kartice == null){
            kartice = new ArrayList<>();
        }
        return kartice;
    }

    public void setKartice(List<Kartica> kartice) {
        this.kartice = kartice;
    }

}
*/
