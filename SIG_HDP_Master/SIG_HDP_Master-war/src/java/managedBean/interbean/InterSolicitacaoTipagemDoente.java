/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.DiagSolicitacaoTipagemDoente;
import entidade.DiagTipagemDoente;
import entidade.InterRegistoInternamento;
import entidade.RhFuncionario;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.diagbean.DiagTipagemDoenteBean;
import managedBean.segbean.SegLoginBean;
import sessao.DiagSolicitacaoTipagemDoenteFacade;
import sessao.DiagTipagemDoenteFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterSolicitacaoTipagemDoente
{

    @EJB
    private DiagTipagemDoenteFacade diagTipagemDoenteFacade;

    @EJB
    private DiagSolicitacaoTipagemDoenteFacade diagSolicitacaoTipagemDoenteFacade;

    @Resource
    private UserTransaction userTransaction;

    private final Calendar dataCorrente = Calendar.getInstance();

    /**
     * Creates a new instance of InterSolicitacaoTipagemDoente
     */
    public InterSolicitacaoTipagemDoente()
    {
    }

    public static InterSolicitacaoTipagemDoente getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterSolicitacaoTipagemDoente) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interSolicitacaoTipagemDoente");
    }

    public DiagSolicitacaoTipagemDoente getInstancia()
    {
        DiagSolicitacaoTipagemDoente d = new DiagSolicitacaoTipagemDoente();
        d.setFkIdRegistoInternamento(InterRegistoInternamentoBean.getInstanciaBean().getInstancia());
        d.setFkIdFuncionario(new RhFuncionario());

        return d;
    }

    public void solicitarTipagemDoente(InterRegistoInternamento interRegistoInternamento)
    {
        try
        {
            userTransaction.begin();

            DiagSolicitacaoTipagemDoente diag = getInstancia();

            diag.setFkIdRegistoInternamento(interRegistoInternamento);
            diag.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
            diag.setDataSolicitacao(dataCorrente.getTime());

            diagSolicitacaoTipagemDoenteFacade.create(diag);

            userTransaction.commit();
            Mensagem.sucessoMsg("Soicitação tipagem gravada com sucesso!");
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

    public boolean verificarTipagemDoente(InterRegistoInternamento interRegistoInternamento)
    {
        DiagTipagemDoente diagTipagem = DiagTipagemDoenteBean.getInstancia();

        if (interRegistoInternamento.getPkIdRegistoInternamento() != null)
        {
//            System.out.println("RG: " + interRegistoInternamento.getPkIdRegistoInternamento() + "PC: " + interRegistoInternamento.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente());

            diagTipagem.setFkIdPaciente(interRegistoInternamento.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente());

            if (diagTipagemDoenteFacade.findTipagem(diagTipagem, null, null, 1).isEmpty() || diagTipagemDoenteFacade.findTipagem(diagTipagem, null, null, 1) == null)
            {
                return false;
            }
        }
        return true;
    }
}
