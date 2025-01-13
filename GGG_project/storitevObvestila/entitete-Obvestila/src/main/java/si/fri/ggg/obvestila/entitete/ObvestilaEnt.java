package si.fri.ggg.obvestila.entitete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "obvestila")
@NamedQueries(value = { @NamedQuery(name = "Obvestila.getAll", query = "SELECT a FROM ObvestilaEnt a") })

public class ObvestilaEnt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "outerAPIid")
    private String outerAPIid;

    @Column(name = "zadeva")
    private String zadeva;
    @Column(name = "sporocilo")
    private String sporocilo;



    public Integer getId() {
            return id;
        }
    public void setId(Integer id) {
            this.id = id;
    }

    public String getOuterAPIid() {return outerAPIid;}
    public void setOuterAPIid(String outerAPIid) {
        this.outerAPIid = outerAPIid;
    }
    public String getZadeva() {return zadeva;}
    public void setZadeva(String ozadeva) {
        this.zadeva = zadeva;
    }
    public String getSporocilo() {return sporocilo;}
    public void setSporocilo(String sporocilo) {
        this.sporocilo = sporocilo;
    }

}
