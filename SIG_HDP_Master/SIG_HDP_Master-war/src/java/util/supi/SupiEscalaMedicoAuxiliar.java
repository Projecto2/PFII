/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.supi;

import entidade.RhFuncionario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessao.SupiEscalaFacade;
import sessao.SupiTurnoFacade;
import sessao.SupiTurnoMedicoFacade;

/**
 *
 * @author helga
 */
public class SupiEscalaMedicoAuxiliar 
{
    private final SupiTurnoMedicoFacade supiTurnoMedicoFacade = lookupSupiTurnoMedicoFacadeBean();
    private SupiEscalaFacade supiEscalaFacade = lookupSupiEscalaFacadeBean();
    private String id;
    //
    private RhFuncionario medico;
    private double cargaHorariaMedico;
    private Integer [] turnos = new Integer [31];
    private List turno = new ArrayList();
    
    public SupiEscalaMedicoAuxiliar()
    {
    }

    
    public SupiEscalaMedicoAuxiliar(RhFuncionario medico, Integer [] turno, double cargaHorariaMedico)
    {
        this.medico = medico;
        this.turnos = turno;
        this.cargaHorariaMedico = cargaHorariaMedico;
    }
    
    public Integer [] getTurnos()
    {
        return turnos;
    }

    public void setTurnos(Integer [] turnos)
    {
        this.turnos = turnos;
    }
    
    public double getCargaHorariaMedico()
    {
        return cargaHorariaMedico;
    }

    public void setCargaHorariaMedico(double cargaHorariaMedico)
    {
        this.cargaHorariaMedico = cargaHorariaMedico;
    }
    
    

   
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public List getTurno()
    {
        return turno;
    }

    public void setTurno(List turno)
    {
        this.turno = turno;
    }

    public RhFuncionario getMedico()
    {
        return medico;
    }

    public void setMedico(RhFuncionario medico)
    {
        this.medico = medico;
    }
    
    
    
    

    public List<SupiEscalaMedicoAuxiliar> prepararEscala()
    {
         int size = getFuncionariosMedicos().size();
        System.out.println("Size:" + size);
        List<SupiEscalaMedicoAuxiliar> list = new ArrayList<SupiEscalaMedicoAuxiliar>();
        for (int i = 0; i < size; i++)
        {
            int j = 0;
            //list.add(new SupiEscalaAuxiliarBean(getFuncionarios().get(i), dia1, dia2, dia3, dia4, dia5, dia6, dia7, dia8, dia9, dia10, dia11, dia12, dia13, dia14, dia15, dia16, dia17, dia18, dia19, dia20, dia21, dia22, dia23, dia24, dia25, dia26, dia27, dia28, dia29, dia30, dia31, cargaHoraria));
            list.add(new SupiEscalaMedicoAuxiliar(getFuncionariosMedicos().get(i), turnos ,cargaHorariaMedico));
        }

        return list;
    }

   
    
    
    //listar todos os medicos
    private List<RhFuncionario> getFuncionariosMedicos()
    {
        System.out.println("supiEscalaFacade.findMedicos():"+supiEscalaFacade.findMedicos());
        return supiEscalaFacade.findMedicos();
    }

    private SupiEscalaFacade lookupSupiEscalaFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (SupiEscalaFacade) c.lookup("java:global/SIG_HDP_Master/SIG_HDP_Master-ejb/SupiEscalaFacade!sessao.SupiEscalaFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private SupiTurnoMedicoFacade lookupSupiTurnoMedicoFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (SupiTurnoMedicoFacade) c.lookup("java:global/SIG_HDP_Master/SIG_HDP_Master-ejb/SupiTurnoMedicoFacade!sessao.SupiTurnoMedicoFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}

