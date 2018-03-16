/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsPaciente;
import entidade.FarmProduto;
import entidade.InterEnfermaria;
import entidade.InterMedicacaoHasFarmProduto;
import entidade.InterNotificacao;
import entidade.InterParametroVital;
import entidade.InterRegistoInternamento;
import entidade.InterTipoNotificacao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.InterNotificacaoFacade;
import util.Constantes;
import util.GeradorCodigo;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterNotificacaoBean implements Serializable
{

    @EJB
    private InterNotificacaoFacade interNotificacaoFacade;

    @Resource
    private UserTransaction userTransaction;

    private List<InterNotificacao> listaNotificacoes;

    private InterNotificacao interNotificacao;

    private int fkIdTipoNotificacao;

    private final Calendar dataCorrente = Calendar.getInstance();

    private SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();

    private String tipoServico = segConta.getFkIdFuncionario().getFkIdSeccaoTrabalho().getDescricao();
    
    
    /**
     * Creates a new instance of InterGeradorNotificacoes
     */
    public InterNotificacaoBean()
    {
    }

    public static InterNotificacaoBean getInstanciaBean()
    {
        return (InterNotificacaoBean) GeradorCodigo.getInstanciaBean("interNotificacaoBean");
    }

    public InterNotificacao getInstancia()
    {
        InterNotificacao n = new InterNotificacao();
        n.setFkIdPaciente(null);
        n.setFkIdTipoNotificacao(new InterTipoNotificacao());
        n.setFkIdParametroVital(null);
        n.setFkIdProduto(null);
        n.setFkIdEnfermaria(new InterEnfermaria());

        return n;
    }

    public InterNotificacao getInterNotificacao()
    {
        if (interNotificacao == null)
        {
            interNotificacao = getInstancia();
        }
        return interNotificacao;
    }

    public void setInterNotificacao(InterNotificacao interNotificacao)
    {
        this.interNotificacao = interNotificacao;
    }

    public List<InterNotificacao> getListaNotificacoes()
    {
        if (listaNotificacoes == null)
        {
            listaNotificacoes = new ArrayList();
        }
        return listaNotificacoes;
    }

    public void setListaNotificacoes(List<InterNotificacao> listaNotificacoes)
    {
        this.listaNotificacoes = listaNotificacoes;
    }

    public int getFkIdTipoNotificacao()
    {
        return fkIdTipoNotificacao;
    }

    public void setFkIdTipoNotificacao(int fkIdTipoNotificacao)
    {
        this.fkIdTipoNotificacao = fkIdTipoNotificacao;
    }

    public void gerarNotificacoes()
    {
        listaNotificacoes = new ArrayList();

        //Por todas as possíveis listas que geram notificaçẽs e percorrer uma por uma        
        criarNotificaoesDePedidosInternamento();
        criarNotificaoesDeParametrosVitais();
        criarNotificaoesDeMedicacoes();
        criarNotificaoesDeExamesSolicitados();
        criarNotificaoesDeMedicamentosSolicitados();

        findAll();
    }

    public void criarNotificaoesDePedidosInternamento()
    {
        try
        {
            userTransaction.begin();

            InterSolicitacoesInterBean.getInstanciaBean().findAllByServico();

            for (int i = 0; i < InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().size(); i++)
            {
                if (interNotificacaoFacade.findBy(tipoServico, 1, null, InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, 0).isEmpty() || interNotificacaoFacade.findBy(tipoServico, fkIdTipoNotificacao, null, InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, 0) == null)
                {
                    getInterNotificacao().setDescricao(Constantes.NOTIFICACAO_PEDIDOS_DESCRICAO + "" + InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome() + " " + InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + " " + InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome());
                    getInterNotificacao().setDataDeNotificacao(InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getData());
                    getInterNotificacao().setFkIdPaciente(new AdmsPaciente(InterSolicitacoesInterBean.getInstanciaBean().getListaSolicitacoesAgendadas().get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()));
                    getInterNotificacao().getFkIdTipoNotificacao().setPkIdTipoNotificacao(1);
                    getInterNotificacao().getFkIdEnfermaria().setPkIdEnfermaria(InterEnfermariaListarBean.getInstanciaBean().getInterEnfermariaFacade().getEnfermariaFuncionario(tipoServico).getPkIdEnfermaria());

                    interNotificacaoFacade.create(getInterNotificacao());

                    limparCampos();
                }
            }

            userTransaction.commit();
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

    public void criarNotificaoesDeParametrosVitais()
    {
        InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().pesquisar(null, 0, 2);
    }

    public void criarNotificaoesDeMedicacoes()
    {
        try
        {
            userTransaction.begin();

            List<InterMedicacaoHasFarmProduto> listaAux = InterMedicacaoListarBean.getInstanciaBean().getInterMedicacaoHasFarmProdutoFacade().pesquisarRegisto(tipoServico, null, null, null, null, null, 0, 0, 0, null);

            for (int i = 0; i < listaAux.size(); i++)
            {
                if (!InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getPkIdRegistoInternamento(), 0, dataCorrente.getTime()).equals("xxx---stop") && !InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getPkIdRegistoInternamento(), 0, dataCorrente.getTime()).equals("X"))
                {
                    if (InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario6_6h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario8_8h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario12_12h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)))
                    {
                        if (interNotificacaoFacade.findBy(tipoServico, 3, null, listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, listaAux.get(i).getFkIdProduto().getPkIdProduto()).isEmpty() || interNotificacaoFacade.findBy(tipoServico, 3, null, listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, listaAux.get(i).getFkIdProduto().getPkIdProduto()) == null)
                        {
                            getInterNotificacao().setDescricao(Constantes.NOTIFICACAO_MEDICACAO_DESCRICAO);
                            getInterNotificacao().setDataDeNotificacao(dataCorrente.getTime());
                            getInterNotificacao().setFkIdPaciente(new AdmsPaciente(listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()));
                            getInterNotificacao().getFkIdTipoNotificacao().setPkIdTipoNotificacao(3);
                            getInterNotificacao().getFkIdEnfermaria().setPkIdEnfermaria(InterEnfermariaListarBean.getInstanciaBean().getInterEnfermariaFacade().getEnfermariaFuncionario(tipoServico).getPkIdEnfermaria());
                            getInterNotificacao().setFkIdProduto(new FarmProduto(listaAux.get(i).getFkIdProduto().getPkIdProduto()));
                            getInterNotificacao().setAssunto("Precisa-se dar o medicamento " + listaAux.get(i).getFkIdProduto().getDescricao() + " " + listaAux.get(i).getFkIdProduto().getFkIdFormaFarmaceutica().getDescricao() + " " + listaAux.get(i).getFkIdHoraMedicacao().getDescricao());

                            interNotificacaoFacade.create(getInterNotificacao());
                           
                            limparCampos();
                        }
                    }
                }
            }

            for (int i = 0; i < listaAux.size(); i++)
            {
                if (!InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getPkIdRegistoInternamento(), 0, dataCorrente.getTime()).equals("xxx---stop") || !InterRealizarMedicacaoListarBean.getInstanciaBean().descricaoOpcaoMedicacao(listaAux.get(i).getPkIdInterMedicacaoHasFarmProduto(), listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getPkIdRegistoInternamento(), 0, dataCorrente.getTime()).equals("X"))
                {
                    if (!InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horarioPadrao(listaAux.get(i).getFkIdHoraMedicacao().getDescricao()))
                    {
                        if (InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario6_6h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario8_8h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)) || InterRegistoInternamentoParametroVitalListarBean.getInstanciaBean().horario12_12h(listaAux.get(i).getFkIdHoraMedicacao().getDescricao(), dataCorrente.get(Calendar.HOUR_OF_DAY)))
                        {
                            if (interNotificacaoFacade.findBy(tipoServico, 3, null, listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, listaAux.get(i).getFkIdProduto().getPkIdProduto()).isEmpty() || interNotificacaoFacade.findBy(tipoServico, 3, null, listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), 0, listaAux.get(i).getFkIdProduto().getPkIdProduto()) == null)
                            {
                                getInterNotificacao().setDescricao(Constantes.NOTIFICACAO_MEDICACAO_DESCRICAO);
                                getInterNotificacao().setDataDeNotificacao(dataCorrente.getTime());
                                getInterNotificacao().setFkIdPaciente(new AdmsPaciente(listaAux.get(i).getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()));
                                getInterNotificacao().getFkIdTipoNotificacao().setPkIdTipoNotificacao(3);
                                getInterNotificacao().getFkIdEnfermaria().setPkIdEnfermaria(InterEnfermariaListarBean.getInstanciaBean().getInterEnfermariaFacade().getEnfermariaFuncionario(tipoServico).getPkIdEnfermaria());
                                getInterNotificacao().setFkIdProduto(new FarmProduto(listaAux.get(i).getFkIdProduto().getPkIdProduto()));
                                getInterNotificacao().setAssunto("Precisa dar " + listaAux.get(i).getFkIdProduto().getDescricao() + " " + listaAux.get(i).getFkIdProduto().getFkIdFormaFarmaceutica().getDescricao() + " " + listaAux.get(i).getFkIdHoraMedicacao().getDescricao() + "no paciente");
                                
                                interNotificacaoFacade.create(getInterNotificacao());

                                limparCampos();
                            }
                        }
                    }
                }
            }

            userTransaction.commit();
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

    public void criarNotificaoesDeExamesSolicitados()
    {

    }

    public void criarNotificaoesDeMedicamentosSolicitados()
    {

    }

    public void findAll()
    {
        listaNotificacoes = interNotificacaoFacade.findBy(tipoServico, fkIdTipoNotificacao, null, null, 0, 0);
    }

    public int size()
    {
        return interNotificacaoFacade.findAll().size();
    }

    public String getColorSize()
    {
        if (fkIdTipoNotificacao == 0)
        {
            fkIdTipoNotificacao = 1;
        }
        
        gerarNotificacoes();
        
        if (listaNotificacoes.isEmpty())
        {
            return "WHITE";
        }
        return "YELLOW";
    }

    public String getColorTitle(InterNotificacao notificacao)
    {
        if (notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == 1)
        {
            return Constantes.COR_NOTIFICACOES_PEDIDO;
        }
        else if (notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == 2)
        {
            return Constantes.COR_NOTIFICACOES_PARAMETRO;
        }
        else if (notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == 3)
        {
            return Constantes.COR_NOTIFICACOES_MEDICACAO;
        }
        else if (notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == 4)
        {
            return Constantes.COR_NOTIFICACOES_EXAMES;
        }
        else
        {
            return Constantes.COR_NOTIFICACOES_MEDICAMENTOS;
        }
    }

    public void limparCampos()
    {
        interNotificacao = null;
    }

    public InterNotificacaoFacade getInterNotificacaoFacade()
    {
        return interNotificacaoFacade;
    }

    public void setInterNotificacaoFacade(InterNotificacaoFacade interNotificacaoFacade)
    {
        this.interNotificacaoFacade = interNotificacaoFacade;
    }
}
