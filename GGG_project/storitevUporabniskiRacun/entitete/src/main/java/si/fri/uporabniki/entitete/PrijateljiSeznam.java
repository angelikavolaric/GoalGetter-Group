package si.fri.uporabniki.entitete;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prijatelji-seznam")
@NamedQueries(value =
        {
                @NamedQuery(name = "UporabnikiSeznam.getAll", query = "SELECT n FROM UporabnikiSeznam n"),
                @NamedQuery(name = "UporabnikiSeznam.getAllForUser",
                        query = "SELECT n FROM UporabnikiSeznam n WHERE n.uporabnik = :uporabnik")
        })
public class PrijateljiSeznam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ustvarjen")
    private Instant ustvarjen;

    @ManyToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @OneToMany(mappedBy = "prijateljiSeznam", cascade = CascadeType.ALL)
    private List<Uporabnik> prijatelji;

   /* @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getUstvaren() {
        return ustvarjen;
    }

    public void setUstvarjen(Instant ustvarjen) {
        this.ustvarjen = ustvarjen;
    }

    public si.fri.uporabniki.entitete.Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public List<Uporabnik> getPrijatelj() {
        if (prijatelj == null){
            prijatelj= new ArrayList<>();
        }
        return uporabniki;
    }

    public void setUporabniki(List<Uporabnik> prijatelji) {
        this.prijatelji = prijatelji;
    }

}

