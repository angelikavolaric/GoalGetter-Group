package si.fri.ggg.deljenje.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "cilj")
@NamedQueries(value = {
        @NamedQuery(name = "Cilj.getAll", query = "SELECT a FROM Cilj a")
})
public class Cilj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "opis")
    private String opis;
    @Column(name = "cilj ur")
    private Integer ciljUr;
    @Column(name = "cilj min")
    private Integer ciljMin;
    @Column(name = "ustvarjen",nullable = false, updatable = false)
    private Timestamp ustvarjen;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getOpis() {return opis;}
    public void setOpis(String opis) {this.opis = opis;}

    public Integer getCiljUr() {return ciljUr;}
    public void setCiljUr(Integer ciljUr) {this.ciljUr = ciljUr;}

    public Integer getCiljMin() {return ciljMin;}
    public void setCiljMin(Integer ciljMin){this.ciljMin = ciljMin;}

    @PrePersist
    private void prePersist() {
        if (ustvarjen == null) {
            ustvarjen = Timestamp.from(Instant.now());  // Set to current time if not provided
        }
    }
    public Timestamp getUstvarjen() {return ustvarjen;}

}
