/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.InterCamaInternamento;
import entidade.InterDoencaInternamentoPaciente;
import entidade.InterEstadoCama;
import entidade.InterRegistoInternamento;
import entidade.InterRegistoSaida;
import entidade.InterResultadoDoenca;
import entidade.InterTipoAlta;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.RowEditEvent;
import sessao.InterRegistoInternamentoFacade;
import sessao.InterRegistoSaidaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterRegistoSaidaNovoBean implements Serializable
{

    @EJB
    private InterRegistoSaidaFacade interRegistoSaidaFacade;

    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;

    private InterRegistoInternamento registoInternamento;

    private InterRegistoSaida interRegistoSaida;

    private final Calendar dataCorrente = Calendar.getInstance();

    private int fkIdResultado;

    private String informacaoAdicional;

    /**
     * Creates a new instance of InterRegistoSaidaNovoBean
     */
    public InterRegistoSaidaNovoBean()
    {
    }

    public void init()
    {

    }

    public static InterRegistoSaidaNovoBean getInstanciaBean()
    {
        return (InterRegistoSaidaNovoBean) GeradorCodigo.getInstanciaBean("interRegistoSaidaNovoBean");
    }

    public static InterRegistoSaida getInstancia()
    {
        InterRegistoSaida s = new InterRegistoSaida();
        s.setFkIdFuncionario(new RhFuncionario());
        s.setFkIdRegistoInternamento(new InterRegistoInternamento());
        s.setFkIdTipoAlta(new InterTipoAlta());

        return s;
    }

    public InterRegistoSaidaFacade getInterRegistoSaidaFacade()
    {
        return interRegistoSaidaFacade;
    }

    public void setInterRegistoSaidaFacade(InterRegistoSaidaFacade interRegistoSaidaFacade)
    {
        this.interRegistoSaidaFacade = interRegistoSaidaFacade;
    }

    public InterRegistoInternamento getRegistoInternamento()
    {
        return registoInternamento;
    }

    public void setRegistoInternamento(InterRegistoInternamento registoInternamento)
    {
        this.registoInternamento = registoInternamento;
    }

    public InterRegistoSaida getInterRegistoSaida()
    {
        if (interRegistoSaida == null)
        {
            interRegistoSaida = getInstancia();
            interRegistoSaida.setDataRegistoSaida(dataCorrente.getTime());
        }
        else
        {
            interRegistoSaida.setDataRegistoSaida(dataCorrente.getTime());
        }

        return interRegistoSaida;
    }

    public Date getDataCorrente()
    {
        return dataCorrente.getTime();
    }

    public void setInterRegistoSaida(InterRegistoSaida interRegistoSaida)
    {
        this.interRegistoSaida = interRegistoSaida;
    }

    public String getInformacaoAdicional()
    {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional)
    {
        this.informacaoAdicional = informacaoAdicional;
    }

    public int getFkIdResultado()
    {
        return fkIdResultado;
    }

    public void setFkIdResultado(int fkIdResultado)
    {
        this.fkIdResultado = fkIdResultado;
    }

    public InterRegistoInternamentoFacade getInterRegistoInternamentoFacade()
    {
        return interRegistoInternamentoFacade;
    }

    public void setInterRegistoInternamentoFacade(InterRegistoInternamentoFacade interRegistoInternamentoFacade)
    {
        this.interRegistoInternamentoFacade = interRegistoInternamentoFacade;
    }

    public int findTotalDiasHospitalizacao(Date dataInternamento)
    {
        long dt1 = interRegistoSaida.getDataRegistoSaida().getTime();
        long dt2 = dataInternamento.getTime();
        int diferenca = (int) ((dt1 - dt2) / (24 * 60 * 60 * 1000));

        return diferenca;
    }

    public void onRowEdit(RowEditEvent event)
    {
        try
        {
            userTransaction.begin();

            InterDoencaInternamentoPaciente doenca = InterDoencaInternamentoPacienteBean.getInstanciaBean().getInterDoencaInternamentoPacienteFacade().find(((InterDoencaInternamentoPaciente) event.getObject()).getPkIdDoencaInternamentoPaciente());

            doenca.setFkIdResultadoDoenca(new InterResultadoDoenca(fkIdResultado));
            doenca.setInformacaoAdicional(informacaoAdicional);

            InterDoencaInternamentoPacienteBean.getInstanciaBean().getInterDoencaInternamentoPacienteFacade().edit(doenca);

            userTransaction.commit();
            Mensagem.sucessoMsg("Resultado da doença gravado com sucesso.");

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
                Mensagem.erroMsg("Por favor digite um valor maior de zero!");

            }
        }
    }

    public String salvar()
    {
        InterDoencaInternamentoPacienteBean.getInstanciaBean().pesquisar(registoInternamento.getPkIdRegistoInternamento(), 2);

        boolean flag = false;

        for (int i = 0; i < InterDoencaInternamentoPacienteBean.getInstanciaBean().getListaDoencasPaciente().size() && !flag; i++)
        {
            if (InterDoencaInternamentoPacienteBean.getInstanciaBean().getListaDoencasPaciente().get(i).getFkIdResultadoDoenca() == null)
            {
                flag = true;
            }
        }

        if (flag)
        {
            Mensagem.warnMsg("O resultado de todas as doenças é obrigatório.");
        }
        else
        {
            try
            {
                userTransaction.begin();

                interRegistoSaida.getFkIdFuncionario().setPkIdFuncionario(SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getPkIdFuncionario());
                interRegistoSaida.getFkIdRegistoInternamento().setPkIdRegistoInternamento(registoInternamento.getPkIdRegistoInternamento());

                if (interRegistoSaida.getFkIdTipoAlta().getPkIdTipoAlta() == 2)
                {
                    InterGuiaTransferenciaDoentesNovoBean.getInstanciaBean().setRegistoInternamento(registoInternamento);

                    String tipoServico = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao().getFkIdFuncionario().getFkIdSeccaoTrabalho().getDescricao();

                    if (tipoServico.equals("Internamento Medicina"))
                    {
                        return "guiaTransferenciaDoentesAdultosNovoInter.xhtml?faces-redirect=true";
                    }
                    else
                    {
                        return "guiaTransferenciaDoentesCriancasNovoInter.xhtml?faces-redirect=true";
                    }
                }
                else
                {
                    interRegistoSaida.setData(dataCorrente.getTime());
                    
                    interRegistoSaidaFacade.create(interRegistoSaida);

                    registoInternamento.setAtivo(false);
                    interRegistoInternamentoFacade.edit(registoInternamento);

                    InterCamaInternamento camaInstancia = InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().find(registoInternamento.getFkIdCamaInternamento().getPkIdCamaInternamento());
                    camaInstancia.setFkIdEstadoCama(new InterEstadoCama(InterEstadoCamaListarBean.getInstanciaBean().getInterEstadoCamaFacade().findByDescricao("Livre Não Pronta").get(0).getPkIdEstadoCama()));

                    InterCamaListarBean.getInstanciaBean().getInterCamaInternamentoFacade().edit(camaInstancia);                   
                    
                    userTransaction.commit();
                    Mensagem.sucessoMsg("Saída gravada com sucesso.");
                    Mensagem.warnMsg("A cama "+camaInstancia.getDescricaoCamaInternamento()+" foi atualizada para Livre");

                    InterRegistoInternamentoBean.getInstanciaBean().pesquisar();
                    
                    interRegistoSaida = null;

                    return "/faces/interVisao/interInternamento/internamentoListar/registoInternamentoListarInter.xhtml?faces-redirect=true";
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
                }
            }
        }

        return null;
    }

    public void limparCampos()
    {
        fkIdResultado = 0;
        informacaoAdicional = null;
    }

    public String novaSaida(InterRegistoInternamento registoInter)
    {
        registoInternamento = registoInter;

        return "/faces/interVisao/interInternamento/internamentoCadastrar/registoSaidaNovoInter.xhtml?faces-redirect=true";
    }
}
