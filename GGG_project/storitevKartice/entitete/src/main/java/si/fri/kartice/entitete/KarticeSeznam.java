package si.fri.kartice.entitete;

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

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @OneToMany(mappedBy = "karticeSeznam", cascade = CascadeType.ALL)
    private List<Kartica> kartice;

    /*@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

   /* public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
*/
    public Instant getUstvarjen() {
        return ustvarjen;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public List<Kartica> getKartica() {
        if (kartice == null){
            kartice = new ArrayList<>();
        }
        return kartice;
    }

    public void setArtikli(List<Kartica> kartice) {
        this.kartice = kartice;
    }

}

