package si.fri.ggg.belezenje.entitete;


import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "ure")
@NamedQueries(value = {
        @NamedQuery(name = "Ure.getAll", query = "SELECT a FROM Ure a")
})


public class Ure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vnosi ur")
    private Integer vnosiUr;
    @Column(name = "vnosi min")
    private Integer vnosiMin;
    @Column(name = "ustvarjen",nullable = false, updatable = false)
    private Timestamp ustvarjen;

    @JsonbTransient  /////////////////////7
    @ManyToOne
    @JoinColumn(name = "ureSeznamId", referencedColumnName = "id", nullable = false)  // Foreign key to KarticeSeznam
    private UreSeznam ureSeznam;

    @Column(name = "uporabnikId", nullable = false)  // Foreign key to KarticeSeznam
    private Integer uporabnikId;




    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVnosiUr() {
        return vnosiUr;
    }
    public void setVnosiUr(Integer vnosi) {this.vnosiUr = vnosi;}

    public Integer getVnosiMin() {return vnosiMin;}
    public void setVnosiMin(Integer vnosi) {this.vnosiMin = vnosi;}



    @PrePersist
    private void prePersist() {
        if (ustvarjen == null) {
            ustvarjen = Timestamp.from(Instant.now());  // Set to current time if not provided
        }
    }

    public Timestamp getUstvarjen() {
        return ustvarjen;
    }

    public void setUreSeznam(UreSeznam ureSeznam) {
        this.ureSeznam = ureSeznam;
    }
    public UreSeznam getUreSeznam() {return ureSeznam;}
}
