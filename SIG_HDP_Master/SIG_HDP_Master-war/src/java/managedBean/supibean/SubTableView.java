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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import managedBean.supibean.Stats;
import managedBean.supibean.Team;

@ManagedBean(name = "dtSubTableView")
public class SubTableView {

    private List<Team> teams;
     private List<Team> escalaMensal;
    
     

    @PostConstruct
    public void init() {
        teams = new ArrayList<Team>();
        escalaMensal = new ArrayList<Team>();
        Team lakers = new Team();
        lakers.getEscalaStats().add(new Stats("2005-2006", 50, 32));
        escalaMensal.add(lakers);

        /*Team celtics = new Team("Boston Celtics");
        celtics.getStats().add(new Stats("2005-2006", 46, 36));
        celtics.getStats().add(new Stats("2006-2007", 50, 32));
        celtics.getStats().add(new Stats("2007-2008", 41, 41));
        celtics.getStats().add(new Stats("2008-2009", 45, 37));
        celtics.getStats().add(new Stats("2009-2010", 38, 44));
        celtics.getStats().add(new Stats("2010-2011", 35, 47));
        teams.add(celtics);
                */
    }
    
    

    public List<Team> getEscalaMensal() {
        return escalaMensal;
    }

    public void setEscalaMensal(List<Team> escalaMensal) {
        this.escalaMensal = escalaMensal;
    }

    
    
    public List<Team> getTeams() {
        return teams;
    }
}
