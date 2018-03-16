/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterAnotacaoEnfermagem;
import entidade.InterRegistoInternamento;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterAnotacaoEnfermagemFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterAnotacaoEnfermagemNovoBean implements Serializable
{

    @EJB
    private InterAnotacaoEnfermagemFacade interAnotacaoEnfermagemFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterAnotacaoEnfermagem notaEnfermagem;

    private final Calendar dataCorrente = Calendar.getInstance();

    /**
     * Creates a new instance of InterAnotacaoEnfermagemNovoBean
     */
    public InterAnotacaoEnfermagemNovoBean()
    {
    }

    public static InterAnotacaoEnfermagemNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterAnotacaoEnfermagemNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interAnotacaoEnfermagemNovoBean");
    }

    public InterAnotacaoEnfermagem getInstancia()
    {
        InterAnotacaoEnfermagem nt = new InterAnotacaoEnfermagem();
        nt.setFkIdRegistoInternamento(new InterRegistoInternamento());
        nt.setFkIdFuncionario(new RhFuncionario());

        return nt;
    }

    public InterAnotacaoEnfermagem getNotaEnfermagem()
    {
        if (notaEnfermagem == null)
            notaEnfermagem = getInstancia();
        return notaEnfermagem;
    }

    public void setNotaEnfermagem(InterAnotacaoEnfermagem notaEnfermagem)
    {
        this.notaEnfermagem = notaEnfermagem;
    }

    public void salvar()
    {

        try
        {
            userTransaction.begin();

            notaEnfermagem.setDataHora(dataCorrente.getTime());
            notaEnfermagem.setData(dataCorrente.getTime());
            notaEnfermagem.getFkIdRegistoInternamento().setPkIdRegistoInternamento(InterAnotacaoEnfermagemListarBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento());
            notaEnfermagem.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());

            interAnotacaoEnfermagemFacade.create(notaEnfermagem);

            userTransaction.commit();
            Mensagem.sucessoMsg("Anotação de enfermagem gravada com sucesso.");

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
        notaEnfermagem = null;
    }
}
