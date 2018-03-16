/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterControloParametrosVitais;
import entidade.InterNotificacao;
import entidade.InterPulsoUnidade;
import entidade.InterRegistoInternamentoHasParametroVital;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.RowEditEvent;
import sessao.InterControloParametrosVitaisFacade;
import sessao.InterRegistoInternamentoHasParametroVitalFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterControloParametrosVitaisNovoBean implements Serializable
{
    @EJB
    private InterRegistoInternamentoHasParametroVitalFacade interRegistoInternamentoHasParametroVitalFacade;

    @EJB
    private InterControloParametrosVitaisFacade interControloParametrosVitaisFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterControloParametrosVitais interControloParametros;

    private final Calendar dataCorrente = Calendar.getInstance();

    private float valor, peso, temperatura, pulso;

    private int ta1, ta2, saturacao, frequenciaResp, diuresi, gicemia;

    private Date dataRegistada;

    private String outros;

    private String tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();
    
    /**
     * Creates a new instance of InterControloParametrosVitaisNovoBean
     */
    public InterControloParametrosVitaisNovoBean()
    {
    }

    public InterControloParametrosVitais getInterControloParametros()
    {
        if (interControloParametros == null)
            interControloParametros = getInstancia();
        return interControloParametros;
    }

    public void setInterControloParametros(InterControloParametrosVitais interControloParametros)
    {
        this.interControloParametros = interControloParametros;
    }

    public static InterControloParametrosVitaisNovoBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterControloParametrosVitaisNovoBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interControloParametrosVitaisNovoBean");
    }

    public static InterControloParametrosVitais getInstancia()
    {
        InterControloParametrosVitais pv = new InterControloParametrosVitais();
        pv.setFkIdRegistoInternamentoHasParametroVital(new InterRegistoInternamentoHasParametroVital());
        pv.setFkIdFuncionario(new RhFuncionario());
        pv.setFkIdPulsoUnidade(new InterPulsoUnidade());

        return pv;
    }

    public float getValor()
    {
        return valor;
    }

    public void setValor(float valor)
    {
        this.valor = valor;
    }

    public Integer getTa1()
    {
        return ta1;
    }

    public void setTa1(Integer ta1)
    {
        this.ta1 = ta1;
    }

    public Integer getTa2()
    {
        return ta2;
    }

    public void setTa2(Integer ta2)
    {
        this.ta2 = ta2;
    }

    public Date getDataCorrente()
    {
        return dataCorrente.getTime();
    }

    public Date getDataRegistada()
    {
        if (dataRegistada == null)
        {
            return dataCorrente.getTime();
        }
        return dataRegistada;
    }

    public void setDataRegistada(Date dataRegistada)
    {
        this.dataRegistada = dataRegistada;
    }

    public String getOutros()
    {
        return outros;
    }

    public void setOutros(String outros)
    {
        this.outros = outros;
    }

    public void onRowEdit(RowEditEvent event)
    {
        try
        {
            interControloParametros.setDataRegistadaNoPaciente(dataRegistada);

            if (interControloParametros.getDataRegistadaNoPaciente().compareTo(dataCorrente.getTime()) > 0)
            {
                Mensagem.warnMsg("A data no paciente deve ser menor ou igual a data corrente");
            }
            else if (interControloParametros.getValor() == 0 && ta1 == 0 && ta2 == 0)
            {
                Mensagem.warnMsg("Por favor digite um valor maior de zero!");
            }
            else if ((ta1 == 0 || ta2 == 0) && interControloParametros.getValor() == 0)
            {
                Mensagem.warnMsg("A Tensão Arterial deve ser maior de zero. Digite um valor válido para a Tensão Arterial.");
            }
            else if (ta1 < ta2)
            {
                Mensagem.warnMsg("A Tensão Alta deve ser maior que a Tensão Baixa. Digite um valor válido para a Tensão Alta.");
            }
            else
            {
                userTransaction.begin();

                if (interControloParametros.getFkIdPulsoUnidade().getPkIdPulsoUnidade() == null)
                {
                    interControloParametros.setFkIdPulsoUnidade(null);
                }

                if (ta1 != 0 && ta2 != 0)
                {
                    interControloParametros.setTaValor1(ta1);
                    interControloParametros.setTaValor2(ta2);

                }

                interControloParametros.getFkIdRegistoInternamentoHasParametroVital().setPkIdRegistoInternamentoHasParametroVital(((InterRegistoInternamentoHasParametroVital) event.getObject()).getPkIdRegistoInternamentoHasParametroVital());
                interControloParametros.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
                interControloParametros.setDataHoraGravadaNoSistema(dataCorrente.getTime());
                interControloParametros.setHora(dataCorrente.get(Calendar.HOUR_OF_DAY));

                interControloParametrosVitaisFacade.create(interControloParametros);

                if (!InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 2, null, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), interRegistoInternamentoHasParametroVitalFacade.find(interControloParametros.getFkIdRegistoInternamentoHasParametroVital().getPkIdRegistoInternamentoHasParametroVital()).getFkIdParametroVital().getPkIdParametroVital(), 0).isEmpty())
                {
                    InterNotificacao notificacao = InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().findBy(tipoServico, 2, null, InterControloDiarioBean.getInstanciaBean().getRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), interRegistoInternamentoHasParametroVitalFacade.find(interControloParametros.getFkIdRegistoInternamentoHasParametroVital().getPkIdRegistoInternamentoHasParametroVital()).getFkIdParametroVital().getPkIdParametroVital(), 0).get(0);

                    if (notificacao != null)                
                        InterNotificacaoBean.getInstanciaBean().getInterNotificacaoFacade().remove(notificacao);
                }   
                
                userTransaction.commit();
                Mensagem.sucessoMsg("Parametro gravado com sucesso.");

                limparCampos();
            }

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
                Mensagem.erroMsg("Por favor digite um valor maior de zero!");

            }
        }
    }

    public void limparCampos()
    {
        interControloParametros = null;
        ta1 = 0;
        ta2 = 0;
        dataRegistada = dataCorrente.getTime();
    }

    public int hora()
    {
        return dataCorrente.get(Calendar.HOUR_OF_DAY);
    }

    public String voltar()
    {
        return "/faces/interVisao/interInternamento/internamentoListar/parametrosVitaisListarInter.xhtml?faces-redirect=true";
    }
}
