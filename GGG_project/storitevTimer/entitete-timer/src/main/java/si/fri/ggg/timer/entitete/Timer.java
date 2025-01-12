package si.fri.ggg.timer.entitete;

import java.sql.Timestamp;
import java.time.Instant;

public class Timer {


    private String opis;
    private Integer timerUr;
    private Integer timerMin;
    private Integer timerSek;


    public Timer(String opis, Integer timerUr, Integer timerMin, Integer timerSek) {
        this.timerUr = timerUr;
        this.timerMin = timerMin;
        this.timerSek = timerSek;


    }

    public Integer convertToSec(Integer timerUr, Integer timerMin, Integer timerSek){
        return (timerUr*3600)+(timerMin*60)+timerSek;
    }

    /*public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Integer getTimerUr() {return timerUr;}
    public void setTimerUr(Integer vnos) {
        this.timerUr = vnos;
        this.duration = convertToSec(vnos,this.timerMin,this.timerSek);
    }

    public Integer getTimerMin() {return timerMin;}
    public void setTimerMin(Integer vnos) {
        this.timerMin = vnos;
        this.duration = convertToSec(this.timerUr,vnos,this.timerSek);
    }

    public Integer getTimerSek() {return timerSek;}
    public void setTimerSek(Integer vnos) {this.timerSek = vnos;
        this.duration = convertToSec(this.timerUr,this.timerMin,vnos);
    }

    public Integer getDuration() {return duration;}
    public void setDuration(Integer vnos) {this.duration = vnos;}

*/
}
