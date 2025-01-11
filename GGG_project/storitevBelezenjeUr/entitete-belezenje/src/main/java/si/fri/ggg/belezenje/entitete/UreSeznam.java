package si.fri.ggg.belezenje.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ureSeznam")
@NamedQueries(value = {
        @NamedQuery(name = "UreSeznam.getAll", query = "SELECT n FROM UreSeznam n"),

})
public class UreSeznam {

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

    // One-to-Many relationship:
    @JsonbTransient /////////////////////7
    @OneToMany(mappedBy = "ureSeznam", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ure> ure;

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
    public List<Ure> getUre() {
        if (ure == null) {
            ure = new ArrayList<>();
        }
        return ure;
    }

    public void setUre(List<Ure> ure) {this.ure = ure;}

    public List<Ure> putUre(Ure noveUre) {
        if (ure == null) {
            ure = new ArrayList<>();
        }
        noveUre.setUreSeznam(this);
        ure.add(noveUre);
        return ure;
    }





}

