/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.horario;

import entidade.RhHorarioGeralDeTrabalho;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.validacao.RhHorarioGeralDeTrabalhoValidarBean;
import sessao.RhHorarioGeralDeTrabalhoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhHorarioGeralDeTrabalhoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhHorarioGeralDeTrabalhoFacade horarioGeralDeTrabalhoFacade;

    /**
     *
     * Entidades
     */
    private RhHorarioGeralDeTrabalho horarioGeralDeTrabalho, horarioGeralDeTrabalhoRemover;

    /**
     * Creates a new instance of RhHorarioGeralDeTrabalhoBean
     */
    public RhHorarioGeralDeTrabalhoBean ()
    {
    }

    public RhHorarioGeralDeTrabalho getHorarioGeralDeTrabalho ()
    {
        if (this.horarioGeralDeTrabalho == null)
        {
            this.horarioGeralDeTrabalho = new RhHorarioGeralDeTrabalho();
        }

        return horarioGeralDeTrabalho;
    }

    public void setHorarioGeralDeTrabalho (RhHorarioGeralDeTrabalho horarioGeralDeTrabalho)
    {
        this.horarioGeralDeTrabalho = horarioGeralDeTrabalho;
    }

    public RhHorarioGeralDeTrabalho getHorarioGeralDeTrabalhoRemover ()
    {
        return horarioGeralDeTrabalhoRemover;
    }

    public void setHorarioGeralDeTrabalhoRemover (RhHorarioGeralDeTrabalho horarioGeralDeTrabalhoRemover)
    {
        this.horarioGeralDeTrabalhoRemover = horarioGeralDeTrabalhoRemover;
    }

    public String create ()
    {
        try
        {
            userTransaction.begin();
            
            RhHorarioGeralDeTrabalhoValidarBean horarioValidar = RhHorarioGeralDeTrabalhoValidarBean.getInstanciaBean();

            if(!horarioValidar.validarNovo(horarioGeralDeTrabalho))
                return null;
            
            horarioGeralDeTrabalho.setDataDaUltimaAlteracao(new Date());
            
            horarioGeralDeTrabalhoFacade.create(horarioGeralDeTrabalho);
            userTransaction.commit();
            Mensagem.sucessoMsg("Horário geral de trabalho guardado com sucesso!");
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

        horarioGeralDeTrabalho = null;

        return null;
    }

    public String edit ()
    {
        try
        {
            userTransaction.begin();
            
            RhHorarioGeralDeTrabalhoValidarBean horarioValidar = RhHorarioGeralDeTrabalhoValidarBean.getInstanciaBean();
            
            if(!horarioValidar.validarNovo(horarioGeralDeTrabalho))
                return null;
            
            if (horarioGeralDeTrabalho.getPkIdHorarioGeralDeTrabalho() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            
            horarioGeralDeTrabalho.setDataDaUltimaAlteracao(new Date());

            horarioGeralDeTrabalhoFacade.edit(horarioGeralDeTrabalho);
            userTransaction.commit();
            Mensagem.sucessoMsg("Horário geral de trabalho editado com sucesso!");
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

        horarioGeralDeTrabalho = null;

        return null;
    }

    public String remove ()
    {
        try
        {
            userTransaction.begin();

            horarioGeralDeTrabalhoFacade.remove(horarioGeralDeTrabalhoRemover);

            userTransaction.commit();

            Mensagem.sucessoMsg("Horário geral de trabalho removido com sucesso!");
            horarioGeralDeTrabalhoRemover = null;
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Este horário geral de trabalho possui registro de actividades, impossível remover !");
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return null;
    }

    public RhHorarioGeralDeTrabalho getHorarioDefinido ()
    {
        List<RhHorarioGeralDeTrabalho> horarioList = horarioGeralDeTrabalhoFacade.findAll();
        
        if(!horarioList.isEmpty())
        {
            return horarioList.get(0);
        }

        return new RhHorarioGeralDeTrabalho();
    }
    
    public String limpar ()
    {
        horarioGeralDeTrabalho = null;
        
        return "horarioGeralDeTrabalhoRh?faces-redirect=true";
    }

}
