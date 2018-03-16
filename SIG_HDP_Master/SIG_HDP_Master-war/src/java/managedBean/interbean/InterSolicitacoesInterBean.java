/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.AdmsAgendamento;
import entidade.AdmsEstadoAgendamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.GrlPessoa;
import entidade.InterRegistoInternamento;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.GrlSexoFacade;
import sessao.InterRegistoInternamentoFacade;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterSolicitacoesInterBean implements Serializable
{    
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    
    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;
    
    @EJB
    private InterRegistoInternamentoFacade interRegistoInternamentoFacade;

    @Resource
    private UserTransaction userTransaction;
    
    private AdmsServicoSolicitado servicoSolicitado, servicoSolicitadoPesquisa, servicoSolicitadoVisualizar;

    private List<AdmsServicoSolicitado> listaSolicitacoes;

    private List<AdmsAgendamento> listaSolicitacoesAgendadas;

    private Long pk_id_paciente, solicitacao;

    private Date dataSolicitacao1, dataSolicitacao2;

    private String numeroProcessoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq;
    private String sexoPesq, areaSolicitantePesq, estadoSolicitacaoPesq, tipoServico;

    private AdmsAgendamento agendamento;
    
    /**
     * Creates a new instance of SolicitacoesInterBean
     */
    public InterSolicitacoesInterBean()
    {

    }

    public static InterSolicitacoesInterBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (InterSolicitacoesInterBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "interSolicitacoesInterBean");
    }

    public String getTipoServico()
    {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico)
    {
        this.tipoServico = tipoServico;
    }    
    
    public List<AdmsServicoSolicitado> listarTodos(String tipoServico)
    {
        return interRegistoInternamentoFacade.listarServicosSolicitados(tipoServico);
    }

    public List<AdmsAgendamento> getListaSolicitacoesAgendadas()
    {
        return listaSolicitacoesAgendadas;
    }

    public void setListaSolicitacoesAgendadas(List<AdmsAgendamento> listaSolicitacoesAgendadas)
    {
        this.listaSolicitacoesAgendadas = listaSolicitacoesAgendadas;
    }

    public String pesquisar()
    {
        List<AdmsAgendamento> listaSolicitacoesTemp = interRegistoInternamentoFacade.findAllAgendamentosParaInternamento(tipoServico, numeroProcessoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPesq, dataSolicitacao1, dataSolicitacao2, areaSolicitantePesq, estadoSolicitacaoPesq);

        listaSolicitacoesAgendadas = new ArrayList();

        for (int i = 0; i < listaSolicitacoesTemp.size(); i++)
        {
            List<InterRegistoInternamento> listaAtivos = interRegistoInternamentoFacade.pesquisarRegisto(listaSolicitacoesTemp.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), tipoServico, null, null, null, null, null, null, 0, 0, 0, null, null, null);

            if (listaAtivos.isEmpty())
            {
                if (verificarItem(listaSolicitacoesAgendadas, listaSolicitacoesTemp.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()) == false)
                {
                    listaSolicitacoesAgendadas.add(listaSolicitacoesTemp.get(i));
                }
                System.out.println("TM:" + listaSolicitacoesAgendadas.size());
            }
        }

        if (listaSolicitacoesAgendadas.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaSolicitacoesAgendadas.size() + " registo(s) retornado(s).");
        }

        return "/faces/interVisao/interEnfermagem/solicitacaoListarInter.xhtml?faces-redirect=true";
    }

    public List<AdmsAgendamento> findAllSolicitacoesAgendadasInter()
    {
        tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter();

        List<AdmsAgendamento> listaSolicitacoesTemp = interRegistoInternamentoFacade.findAllAgendamentosParaInternamento(tipoServico, numeroProcessoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPesq, dataSolicitacao1, dataSolicitacao2, areaSolicitantePesq, estadoSolicitacaoPesq);

        listaSolicitacoesAgendadas = new ArrayList();

        for (int i = 0; i < listaSolicitacoesTemp.size(); i++)
        {
            List<InterRegistoInternamento> listaAtivos = interRegistoInternamentoFacade.pesquisarRegisto(listaSolicitacoesTemp.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), tipoServico, null, null, null, null, null, null, 0, 0, 0, null, null, null);

            if (listaAtivos.isEmpty())
            {
                if (verificarItem(listaSolicitacoesAgendadas, listaSolicitacoesTemp.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente()) == false)
                {
                    listaSolicitacoesAgendadas.add(listaSolicitacoesTemp.get(i));
                }
            }
        }

        return listaSolicitacoesAgendadas;
    }

    public void findAllByServico()
    {
        SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();
        tipoServico = segConta.getFkIdFuncionario().getFkIdSeccaoTrabalho().getDescricao();

        List<AdmsAgendamento> listaSolicitacoesTemp = interRegistoInternamentoFacade.findAllAgendamentosParaInternamento(tipoServico, numeroProcessoPesq, nomePacientePesq, nomeMeioPacientePesq, sobreNomePacientePesq, sexoPesq, dataSolicitacao1, dataSolicitacao2, areaSolicitantePesq, estadoSolicitacaoPesq);

        listaSolicitacoesAgendadas = new ArrayList();

        for (int i = 0; i < listaSolicitacoesTemp.size(); i++)
        {
            List<InterRegistoInternamento> listaAtivos = interRegistoInternamentoFacade.pesquisarRegisto(listaSolicitacoesTemp.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente(), tipoServico, null, null, null, null, null, null, 0, 0, 0, null, null, null);

            if (listaAtivos.isEmpty())
            {
                listaSolicitacoesAgendadas.add(listaSolicitacoesTemp.get(i));
            }
        }
    }

    public InterRegistoInternamentoFacade getInterRegistoInternamentoFacade()
    {
        return interRegistoInternamentoFacade;
    }

    public void setInterRegistoInternamentoFacade(InterRegistoInternamentoFacade interRegistoInternamentoFacade)
    {
        this.interRegistoInternamentoFacade = interRegistoInternamentoFacade;
    }

    public String imprimir(int pk_id_solicitacao)
    {
        return null;
    }

    public String novoInternamento(Long pk_id_servicoSolicitado)
    {
        solicitacao = pk_id_servicoSolicitado;

        return "registarInternamento";
    }

    public AdmsServicoSolicitado getInstanciaAdmsServicoSolicitado()
    {
        AdmsServicoSolicitado s = new AdmsServicoSolicitado();
        s.setFkIdSolicitacao(new AdmsSolicitacao());
        s.getFkIdSolicitacao().setFkIdPaciente(new AdmsPaciente());
        s.getFkIdSolicitacao().getFkIdPaciente().setFkIdPessoa(new GrlPessoa());

        return s;
    }

    public AdmsServicoSolicitado getServicoSolicitado()
    {
        if (servicoSolicitado == null)
        {
            servicoSolicitado = getInstanciaAdmsServicoSolicitado();
        }
        return servicoSolicitado;
    }

    public AdmsServicoSolicitado getServicoSolicitadoPesquisa()
    {
        if (servicoSolicitadoPesquisa == null)
        {
            servicoSolicitadoPesquisa = getInstanciaAdmsServicoSolicitado();
        }
        return servicoSolicitadoPesquisa;
    }

    public void setServicoSolicitadoPesquisa(AdmsServicoSolicitado servicoSolicitadoPesquisa)
    {
        this.servicoSolicitadoPesquisa = servicoSolicitadoPesquisa;
    }

    public AdmsServicoSolicitado getServicoSolicitadoVisualizar()
    {
        return servicoSolicitadoVisualizar;
    }

    public void setServicoSolicitadoVisualizar(AdmsServicoSolicitado servicoSolicitadoVisualizar)
    {
        this.servicoSolicitadoVisualizar = servicoSolicitadoVisualizar;
    }

    public void setServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        this.servicoSolicitado = servicoSolicitado;
    }

    public AdmsAgendamento getAgendamento()
    {
        return agendamento;
    }

    public void setAgendamento(AdmsAgendamento agendamento)
    {
        this.agendamento = agendamento;
    }

    public List<AdmsServicoSolicitado> getListaSolicitacoes()
    {
        return listaSolicitacoes;
    }

    public void setListaSolicitacoes(List<AdmsServicoSolicitado> listaSolicitacoes)
    {
        this.listaSolicitacoes = listaSolicitacoes;
    }

    public Long getPk_id_paciente()
    {
        return pk_id_paciente;
    }

    public void setPk_id_paciente(Long pk_id_paciente)
    {
        this.pk_id_paciente = pk_id_paciente;
    }

    public Date getDataSolicitacao1()
    {
        return dataSolicitacao1;
    }

    public void setDataSolicitacao1(Date dataSolicitacao1)
    {
        this.dataSolicitacao1 = dataSolicitacao1;
    }

    public Date getDataSolicitacao2()
    {
        return dataSolicitacao2;
    }

    public void setDataSolicitacao2(Date dataSolicitacao2)
    {
        this.dataSolicitacao2 = dataSolicitacao2;
    }

    public Long getSolicitacao()
    {
        return solicitacao;
    }

    public void setSolicitacao(Long solicitacao)
    {
        this.solicitacao = solicitacao;
    }

    public String getNumeroProcessoPesq()
    {
        return numeroProcessoPesq;
    }

    public void setNumeroProcessoPesq(String numeroProcessoPesq)
    {
        this.numeroProcessoPesq = numeroProcessoPesq;
    }

    public String getNomePacientePesq()
    {
        return nomePacientePesq;
    }

    public void setNomePacientePesq(String nomePacientePesq)
    {
        this.nomePacientePesq = nomePacientePesq;
    }

    public String getNomeMeioPacientePesq()
    {
        return nomeMeioPacientePesq;
    }

    public void setNomeMeioPacientePesq(String nomeMeioPacientePesq)
    {
        this.nomeMeioPacientePesq = nomeMeioPacientePesq;
    }

    public String getSobreNomePacientePesq()
    {
        return sobreNomePacientePesq;
    }

    public void setSobreNomePacientePesq(String sobreNomePacientePesq)
    {
        this.sobreNomePacientePesq = sobreNomePacientePesq;
    }

    public String getSexoPesq()
    {
        return sexoPesq;
    }

    public void setSexoPesq(String sexoPesq)
    {
        this.sexoPesq = sexoPesq;
    }

    public String getAreaSolicitantePesq()
    {
        return areaSolicitantePesq;
    }

    public void setAreaSolicitantePesq(String areaSolicitantePesq)
    {
        this.areaSolicitantePesq = areaSolicitantePesq;
    }

    public String getEstadoSolicitacaoPesq()
    {
        return estadoSolicitacaoPesq;
    }

    public void setEstadoSolicitacaoPesq(String estadoSolicitacaoPesq)
    {
        this.estadoSolicitacaoPesq = estadoSolicitacaoPesq;
    }

    public void visualizarSolicitcao(AdmsServicoSolicitado solicitacaoInter)
    {
        servicoSolicitado = solicitacaoInter;
    }

    public void limparPesquisa()
    {
        numeroProcessoPesq = null;
        nomePacientePesq = null;
        sexoPesq = null;
        dataSolicitacao1 = null;
        dataSolicitacao2 = null;
    }

    public boolean verificarItem(List<AdmsAgendamento> lista, Long pkIdPaciente)
    {
        for (int i = 0; i < lista.size(); i++)
        {
            if (lista.get(i).getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente() == pkIdPaciente)
            {
                return true;
            }
        }
        return false;
    }

    public String cancelarPedido()
    {
        try
        {
            userTransaction.begin();

            //Cancelar : 1. Editar serviço ESoficitado  2. Alterar Estado de Agendamento
            admsServicoSolicitadoFacade.edit(servicoSolicitado);
            
            agendamento.setFkIdEstadoAgendamento(new AdmsEstadoAgendamento(3));
            
            admsAgendamentoFacade.edit(agendamento);
            
            userTransaction.commit();

            servicoSolicitado = null;
            agendamento = null;
            
            Mensagem.sucessoMsg("Solicitação cancelada com sucesso!");            
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
        
        return "solicitacaoListarInter.xhtml?faces-redirect=true";
    }
    
    public String naoAparceu(AdmsAgendamento admsAg)
    {
        try
        {
            userTransaction.begin();
            
            admsAg.setFkIdEstadoAgendamento(new AdmsEstadoAgendamento(4));
            
            admsAgendamentoFacade.edit(admsAg);
            
            userTransaction.commit();            
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
        
        return "solicitacaoListarInter.xhtml?faces-redirect=true";
    }

    public String listarPedidosInternamento(String nomeServico)
    {
        tipoServico = nomeServico;
        InterObjetosSessaoBean.getInstanciaBean().setServicoInter(nomeServico);
        return "/faces/interVisao/interEnfermagem/solicitacaoListarInter.xhtml?faces-redirect=true";
    }

}
