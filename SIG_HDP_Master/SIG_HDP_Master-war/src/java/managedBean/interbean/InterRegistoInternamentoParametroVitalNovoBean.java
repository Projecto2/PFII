/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.interbean;

import entidade.InterHoraMedicacao;
import entidade.InterParametroVital;
import entidade.InterRegistoInternamento;
import entidade.InterRegistoInternamentoHasParametroVital;
import entidade.RhFuncionario;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterRegistoInternamentoHasParametroVitalFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoInternamentoParametroVitalNovoBean
{
    @EJB
    private InterRegistoInternamentoHasParametroVitalFacade interRegistoInternamentoHasParametroVitalFacade;

    @Resource
    private UserTransaction userTransaction;

    private final Calendar dataCorrente = Calendar.getInstance();
    
    private InterRegistoInternamentoHasParametroVital interRegistoInternamentoHasParametroVital;
    
    /**
     * Creates a new instance of InterRegistoInternamentoParametroVitalNovoBean
     */
    public InterRegistoInternamentoParametroVitalNovoBean()
    {
    }
    
    public static InterRegistoInternamentoParametroVitalNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRegistoInternamentoParametroVitalNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRegistoInternamentoParametroVitalNovoBean");
    }

    public InterRegistoInternamentoHasParametroVital getInstancia()
    {
        InterRegistoInternamentoHasParametroVital rp = new InterRegistoInternamentoHasParametroVital();
        rp.setFkIdParametroVital(new InterParametroVital());
        rp.setFkIdRegistoInternamento(new InterRegistoInternamento());
        rp.setFkIdFuncionario(new RhFuncionario());
        rp.setFkIdHoraMedicacao(new InterHoraMedicacao());

        return rp;
    }

    public InterRegistoInternamentoHasParametroVital getInterRegistoInternamentoHasParametroVital()
    {
        if (interRegistoInternamentoHasParametroVital == null)
        {
            interRegistoInternamentoHasParametroVital = getInstancia();
        }
        
        return interRegistoInternamentoHasParametroVital;
    }

    public void setInterRegistoInternamentoHasParametroVital(InterRegistoInternamentoHasParametroVital interRegistoInternamentoHasParametroVital)
    {
        this.interRegistoInternamentoHasParametroVital = interRegistoInternamentoHasParametroVital;
    }
    
    public void salvar()
    {
        try
        {
            userTransaction.begin();

            InterRegistoInternamento registo = InterControloDiarioBean.getInstanciaBean().getRegistoInternamento();

            interRegistoInternamentoHasParametroVital.getFkIdRegistoInternamento().setPkIdRegistoInternamento(registo.getPkIdRegistoInternamento());
            interRegistoInternamentoHasParametroVital.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
            interRegistoInternamentoHasParametroVital.setDataHora(dataCorrente.getTime());
            
            interRegistoInternamentoHasParametroVitalFacade.create(interRegistoInternamentoHasParametroVital);
            
            InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().pesquisarTodos(registo.getPkIdRegistoInternamento(), 0);
            InterParametroVitalListarBean.getInstanciaBean().findById(registo.getPkIdRegistoInternamento());
            
            userTransaction.commit();
            Mensagem.sucessoMsg("Parametro adicionado com sucesso.");

            limparCampos();
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.toString());

            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                System.out.println("Roolback: " + ex.toString());
            }
        }
    }

    public void limparCampos()
    {
        interRegistoInternamentoHasParametroVital = null;
    }
}
