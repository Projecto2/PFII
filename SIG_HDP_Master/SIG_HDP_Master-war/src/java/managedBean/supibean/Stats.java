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
 
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessao.SupiEscalaFacade;
import sessao.SupiTurnoFacade;



public class Stats implements Serializable {

    private String season;

    private int win;

    private int loss;
    

    private String id;
    //
    private RhFuncionario funcionario;
    private double cargaHoraria;
    private Integer[] turnos = new Integer[31];
    private List turno = new ArrayList();

    public Stats() {
    }

    public Stats(RhFuncionario funcionario, Integer[] turno, double cargaHoraria) {
        this.funcionario = funcionario;
        this.turnos = turno;
        this.cargaHoraria = cargaHoraria;
    }

    public Integer[] getTurnos() {
        return turnos;
    }

    public void setTurnos(Integer[] turnos) {
        this.turnos = turnos;
    }

    public double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RhFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(RhFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public List getTurno() {
        return turno;
    }

    public void setTurno(List turno) {
        this.turno = turno;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Stats(String season, int win, int loss) {
        this.season = season;
        this.win = win;
        this.loss = loss;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
