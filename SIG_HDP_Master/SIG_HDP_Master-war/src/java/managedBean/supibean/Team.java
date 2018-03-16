/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.supibean;

/**
 *
 * @author helga
 */
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
public class Team implements Serializable {
 
    private String name;
     
    private List<Stats> stats;
    private List<Stats> escalaStats;
     
    public Team() {
        stats = new ArrayList<Stats>();
        escalaStats = new ArrayList<Stats>();
    }
    
    
     
    public Team(String name) {
        this.name = name;
        stats = new ArrayList<Stats>();
        escalaStats = new ArrayList<Stats>();
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public List<Stats> getStats() {
        return stats;
    }
 
    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<Stats> getEscalaStats() {
        return escalaStats;
    }

    public void setEscalaStats(List<Stats> escalaStats) {
        this.escalaStats = escalaStats;
    }
    
    
     
    public int getAllWins() {
        int sum = 0;
         
        for(Stats s : escalaStats) {
            sum += s.getWin();
        }
         
        return sum;
    }
     
    public int getAllLosses() {
        int sum = 0;
         
        for(Stats s : escalaStats) {
            sum += s.getLoss();
        }
         
        return sum;
    }
}
