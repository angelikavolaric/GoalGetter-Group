package si.fri.ggg.timer.entitete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "timers")
@NamedQueries(value = {
        @NamedQuery(name = "Timer.getAll", query = "SELECT a FROM TimerEnt a")
})

public class TimerEnt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "outerAPIid")
    private String outerAPIid;

    @Column(name = "opis")
    private String opis;
    @Column(name = "zacetek")
    private String zacetek;
    @Column(name = "konec")
    private String konec;


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
    public String getOpis() {return opis;}
    public void setOpis(String opis) {
        this.opis = opis;
    }
    public String getZacetek() {return zacetek;}
    public void setZacetek(String zacetek) {
        this.zacetek = zacetek;
    }
    public String getKonec() {return konec;}
    public void setKonec(String konec) {
        this.konec = konec;
    }



}