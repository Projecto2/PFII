/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterMedicacaoHasFarmProduto;
import entidade.InterNotificacao;
import entidade.InterOpcaoMedicacao;
import entidade.InterRealizarMedicacao;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.RowEditEvent;
import sessao.InterRealizarMedicacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRealizarMedicacaoNovoBean implements Serializable
{

    @EJB
    private InterRealizarMedicacaoFacade interRealizarMedicacaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterRealizarMedicacao entregaMedicamentos;

    private final Calendar dataCorrente = Calendar.getInstance();

    private int opcaoMedicacao;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
    
    /**
     * Creates a new instance of InterRealizarMedicacaoNovoBean
     */
    public InterRealizarMedicacaoNovoBean()
    {
    }

    public static InterRealizarMedicacaoNovoBean getInstanciaBean()
    {
        return (InterRealizarMedicacaoNovoBean) GeradorCodigo.getInstanciaBean("interRealizarMedicacaoNovoBean");
    }

    public InterRealizarMedicacao getInstancia()
    {
        InterRealizarMedicacao md = new InterRealizarMedicacao();
        md.setFkIdMedicacaoHasFarmProduto(new InterMedicacaoHasFarmProduto());
        md.setFkIdOpcaoMedicacao(new InterOpcaoMedicacao());
        md.setFkIdFuncionario(new RhFuncionario());

        return md;
    }

    public InterRealizarMedicacao getEntregaMedicamentos()
    {
        if (entregaMedicamentos == null)
        {
            entregaMedicamentos = getInstancia();
        }
        return entregaMedicamentos;
    }

    public void setEntregaMedicamentos(InterRealizarMedicacao entregaMedicamentos)
    {
        this.entregaMedicamentos = entregaMedicamentos;
    }

    public int getOpcaoMedicacao()
    {
        return opcaoMedicacao;
    }

    public void setOpcaoMedicacao(int opcaoMedicacao)
    {
        this.opcaoMedicacao = opcaoMedicacao;
    }

    public void onRowEdit(RowEditEvent event)
    {

        try
        {
            userTransaction.begin();

            getEntregaMedicamentos().getFkIdMedicacaoHasFarmProduto().setPkIdInterMedicacaoHasFarmProduto(((InterMedicacaoHasFarmProduto) event.getObject()).getPkIdInterMedicacaoHasFarmProduto());
            getEntregaMedicamentos().getFkIdOpcaoMedicacao().setPkIdOpcaoMedicacao(opcaoMedicacao);
            getEntregaMedicamentos().getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
            getEntregaMedicamentos().setDataHora(dataCorrente.getTime());
            getEntregaMedicamentos().setHora(dataCorrente.get(Calendar.HOUR_OF_DAY));
            getEntregaMedicamentos().setDataRegistadaNoPaciente(dataCorrente.getTime());

            interRealizarMedicacaoFacade.create(getEntregaMedicamentos());

            if (!InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 3, null, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(),  0, InterMedicacaoListarBean.getInstanciaBean().getInterMedicacaoHasFarmProdutoFacade().find(getEntregaMedicamentos().getFkIdMedicacaoHasFarmProduto().getPkIdInterMedicacaoHasFarmProduto()).getFkIdProduto().getPkIdProduto()).isEmpty())
            {
                InterNotificacao notificacao = InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 3, null, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(),  0, InterMedicacaoListarBean.getInstanciaBean().getInterMedicacaoHasFarmProdutoFacade().find(getEntregaMedicamentos().getFkIdMedicacaoHasFarmProduto().getPkIdInterMedicacaoHasFarmProduto()).getFkIdProduto().getPkIdProduto()).get(0);

                if (notificacao != null)            
                    InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().remove(notificacao);
            }
            
            userTransaction.commit();
            Mensagem.sucessoMsg("Medicação gravada com sucesso.");

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

    public void onRowCancel(RowEditEvent event)
    {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean verificarHora(int hora)
    {
        if (dataCorrente.get(Calendar.HOUR_OF_DAY) != hora)
        {
            return true;
        }
        return false;
    }

    public boolean verificarHora2(int hora)
    {
        if (dataCorrente.get(Calendar.HOUR_OF_DAY) == hora)
        {
            return true;
        }
        return false;
    }

    public boolean horario6_6h(String horario, int hora)
    {
        if (horario.equals("6/6h") && verificarHora2(hora))
        {
            return false;
        }
        return true;
    }

    public boolean horario8_8h(String horario, int hora)
    {
        if (horario.equals("8/8h") && verificarHora2(hora))
        {
            return false;
        }
        return true;
    }

    public boolean horario12_12h(String horario, int hora)
    {
        if ((!horario.equals("12/12h") && !horario.equals("6/6h")) && verificarHora2(hora))
        {
            return false;
        }
        return true;
    }

    public void limparCampos()
    {
        entregaMedicamentos = null;
        opcaoMedicacao = 0;
    }
}
