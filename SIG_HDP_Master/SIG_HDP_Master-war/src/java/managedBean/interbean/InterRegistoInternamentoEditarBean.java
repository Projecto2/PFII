/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterRegistoInternamento;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.InterRegistoInternamentoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoInternamentoEditarBean
{

    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterRegistoInternamento registo;

    /**
     * Creates a new instance of InterRegistoInternamentoEditarBean
     */
    public InterRegistoInternamentoEditarBean()
    {
    }

    public static InterRegistoInternamentoEditarBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterRegistoInternamentoEditarBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interRegistoInternamentoEditarBean");
    }

    public InterRegistoInternamento getRegisto()
    {
        if (registo == null)
        {
            registo = InterRegistoInternamentoBean.getInstanciaBean().getInstancia();//new InterRegistoInternamento();
        }
        return registo;
    }

    public void setRegisto(InterRegistoInternamento registo)
    {
        this.registo = registo;
    }

    public void editar()
    {
        try
        {
            userTransaction.begin();

            interRegistoInternamentoFacade.edit(registo);

            userTransaction.commit();
            Mensagem.sucessoMsg("O internamento foi editado com sucesso.");
        }
        catch (Exception e)
        {
            try
            {
                userTransaction.rollback();
                System.out.println(e.getMessage());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Está solicitação já foi atendida");
                System.out.println("Rollback: " + ex.toString());
            }
        }
    }

    public void limparCampos()
    {
        registo = null;
    }
}
