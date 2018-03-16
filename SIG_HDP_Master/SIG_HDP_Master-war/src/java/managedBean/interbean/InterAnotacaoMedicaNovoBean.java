/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterAnotacaoMedica;
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
import sessao.InterAnotacaoMedicaFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterAnotacaoMedicaNovoBean implements Serializable
{

    @EJB
    private InterAnotacaoMedicaFacade interAnotacaoMedicaFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterAnotacaoMedica notaMedica;

    private final Calendar dataCorrente = Calendar.getInstance();

    /**
     * Creates a new instance of InterAnotacaoMedicaNovoBean
     */
    public InterAnotacaoMedicaNovoBean()
    {
    }

    public static InterAnotacaoMedicaNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterAnotacaoMedicaNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interAnotacaoMedicaNovoBean");
    }

    public InterAnotacaoMedica getInstancia()
    {
        InterAnotacaoMedica nt = new InterAnotacaoMedica();
        nt.setFkIdRegistoInternamento(new InterRegistoInternamento());
        nt.setFkIdFuncionario(new RhFuncionario());

        return nt;
    }

    public InterAnotacaoMedica getNotaMedica()
    {
        if (notaMedica == null)
            notaMedica = getInstancia();
        return notaMedica;
    }

    public void setNotaMedica(InterAnotacaoMedica notaMedica)
    {
        this.notaMedica = notaMedica;
    }

    public void salvar()
    {

        try
        {
            userTransaction.begin();

            notaMedica.setDataHora(dataCorrente.getTime());
            notaMedica.setData(dataCorrente.getTime());
            notaMedica.getFkIdRegistoInternamento().setPkIdRegistoInternamento(InterAnotacaoMedicaListarBean.getInstanciaBean().getRegistoInternamento().getPkIdRegistoInternamento());
            notaMedica.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());

            interAnotacaoMedicaFacade.create(notaMedica);

            userTransaction.commit();
            Mensagem.sucessoMsg("Anotação médica gravada com sucesso.");

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
        notaMedica = null;
    }
}
