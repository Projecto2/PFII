/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.SupiTurno;
import entidade.SupiTurnoMedico;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.SupiTurnoFacade;
import sessao.SupiTurnoMedicoFacade;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiTurnoMedicoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private SupiTurnoMedicoFacade supiTurnoMedicoFacade;

    private SupiTurnoMedico supiTurno1, supiTurnoRemover;

    SupiTurno turno = new SupiTurno();
    private int idTurno;
    String mostrarHora1;
    String mostrarHora;
    ManipulaData manipulaData;

    /**
     * Creates a new instance of TurnoSupiBean
     */
    public SupiTurnoMedicoBean()
    {
    }

    public SupiTurnoMedico getSupiTurno1()
    {
        if (supiTurno1 == null)
        {
            supiTurno1 = new SupiTurnoMedico();
        }

        return supiTurno1;
    }

    public void setSupiTurno1(SupiTurnoMedico supiTurno1)
    {
        this.supiTurno1 = supiTurno1;
    }

    public String getMostrarHora1()
    {
        return mostrarHora1;
    }

    public void setMostrarHora1(String mostrarHora1)
    {
        this.mostrarHora1 = mostrarHora1;
    }

    public String getMostrarHora()
    {
        return mostrarHora;
    }

    public void setMostrarHora(String mostrarHora)
    {
        this.mostrarHora = mostrarHora;
    }
    
    

    public SupiTurnoMedico getSupiTurnoRemover()
    {
        return supiTurnoRemover;
    }

    public void setSupiTurnoRemover(SupiTurnoMedico supiTurnoRemover)
    {
        this.supiTurnoRemover = supiTurnoRemover;
    }

    public SupiTurno getTurno()
    {
        return turno;
    }
    
    public String mendagemDuracaoTurno1()
    {

        //supiTurno1.setCargaHoraria((double)manipulaData.diferencaHoras(supiTurno1. .getHoraEntrada(), supiTurno1.getHoraSaida())); 
        double horas = supiTurno1.getCargaHoraria();

        if (horas >= 0)
        {
            mostrarHora1 = "" + horas + " Horas ";
        } else
        {
            mostrarHora1 = "Hora Invalida";
        }
        return mostrarHora1;
    }
    
     public String create () throws SystemException
     {
          try
          {
               userTransaction.begin();
               supiTurnoMedicoFacade.create(supiTurno1);
               userTransaction.commit();
               Mensagem.sucessoMsg("Turno guardado com sucesso!");
          }
          catch (Exception e)
          {
               try
               {
                    userTransaction.rollback();
                    Mensagem.erroMsg(e.toString());
               }
               catch (IllegalStateException | SecurityException | SystemException ex)
               {
                    Mensagem.erroMsg("Rollback: " + ex.toString());
               }
          }

          supiTurno1 = null;

          return null;
     }
     
     public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (supiTurno1.getPkIdTurnoMedico()== null)
               {
                    throw new NullPointerException("PK -> NULL");
               }
               supiTurnoMedicoFacade.edit(supiTurno1);
               userTransaction.commit();
               Mensagem.sucessoMsg("Turno editado com sucesso!");
          }
          catch (Exception e)
          {
               try
               {
                    userTransaction.rollback();
                    Mensagem.erroMsg(e.toString());
               }
               catch (IllegalStateException | SecurityException | SystemException ex)
               {
                    Mensagem.erroMsg("Rollback: " + ex.toString());
               }
          }

          supiTurno1 = null;

          return null;
     }
     
     public void eliminarTurno(int idTurno)
    {

        FacesContext fc = FacesContext.getCurrentInstance();

        try
        {

            supiTurno1 = supiTurnoMedicoFacade.find(idTurno);

            if (supiTurno1.getSupiMedicoHasEscalaList() != null)
            {

                supiTurnoMedicoFacade.remove(supiTurno1);

                fc.addMessage(null, new FacesMessage("Turno Removido com sucesso!"));

                supiTurno1 = new SupiTurnoMedico();

            } else
            {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar o Turno " + ""));

            }

        } catch (Exception ex)
        {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
    }

    public void setTurno(SupiTurno turno)
    {
        this.turno = turno;
    }

    public int getIdTurno()
    {
        return idTurno;
    }

    public void setIdTurno(int idTurno)
    {
        this.idTurno = idTurno;
    }
//
//    public void create()
//    {
//        supiTurnoFacade.create(turno);
//    }

    public SupiTurno getSupiTurno()
    {
        if (turno == null)
        {
            turno = new SupiTurno();

        }
        return turno;

    }

    public List<SupiTurnoMedico> findall()
    {
        return supiTurnoMedicoFacade.findAll();
    }

    public void changeTurno(ValueChangeEvent eve)
    {
        int turno = Integer.parseInt(eve.getNewValue().toString());

        //Faz com que apareça o horário do turno selecionado      
        try
        {
            // escala.getFkIdTurno().setCargaHoraria(supiTurnoFacade.find(turno).getCargaHoraria());
        } catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println("Trocou o turno - " + turno);

    }

}
