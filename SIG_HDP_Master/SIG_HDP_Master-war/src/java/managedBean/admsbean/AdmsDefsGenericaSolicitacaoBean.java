/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.admsbean;

import entidade.AdmsAgendamento;
import entidade.AdmsAgendamentoMedico;
import entidade.AdmsServico;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.FinEncargoDevido;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.transaction.UserTransaction;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsAgendamentoMedicoFacade;
import sessao.AdmsEstadoAgendamentoFacade;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.AdmsSolicitacaoFacade;
import sessao.FinEncargoDevidoFacade;
import sessao.FinEstadoPagamentoPagaNaopagoFacade;
import sessao.FinPagamentoEncargoDevidoFacade;
import sessao.FinTipoEncargoFacade;
import sessao.RhFuncionarioFacade;
import util.Mensagem;
import util.adms.ConstantesAdms;

/**
 *
 * @author gemix
 */
@ManagedBean
//@SessionScoped
public abstract class AdmsDefsGenericaSolicitacaoBean implements Serializable
{
    @EJB
    protected AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    protected FinPagamentoEncargoDevidoFacade finPagamentoEncargoDevidoFacade;
    @EJB
    protected FinTipoEncargoFacade finTipoEncargoFacade;
    @EJB
    protected FinEstadoPagamentoPagaNaopagoFacade finEstadoPagamentoPagaNaopagoFacade;
    @EJB
    protected FinEncargoDevidoFacade finEncargoDevidoFacade;
    @EJB
    protected AdmsAgendamentoFacade admsAgendamentoFacade;
    
    

    /**
     * Creates a new instance of AdmsDefsGenericaSolicitacaoBean
     */

    
    @EJB
    protected AdmsAgendamentoMedicoFacade admsAgendamentoMedicoFacade;
    @EJB
    protected RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    protected AdmsEstadoAgendamentoFacade admsEstadoAgendamentoFacade;
    @EJB
    protected AdmsSolicitacaoFacade admsSolicitacaoFacade;
    @EJB
    protected AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;
    @EJB
    protected AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;
    
    
    
    
    protected AdmsServicoSolicitado servicoSolicitado, servicoPorProcurarAgendamento;
    
    protected AdmsServico servicoPesquisa;
    
    protected boolean solicitacaoMedica = false, /*pesquisar = false,*/
        remocaoPersistente = false, agendamentoMedico = false, podeAgendarParaMedico = false, carregarAgenda = false;
    
    protected List<AdmsServico> servicoLista;
    
    protected AdmsSolicitacao solicitacao;
    
    protected List<AdmsAgendamento> agendamentos;
    
    protected List<AdmsAgendamentoMedico> agendamentosMedicos;
    
    protected AdmsAgendamento agendamento, agendamentoMarcado;
    
    protected AdmsAgendamentoMedico agendamentoMedicoM, agendamentoMedicoMarcado;
    
    protected RhFuncionario medico;
    
    protected int numeroServicosNovosAdicionados = 0;
    
    
    @Resource
    protected UserTransaction userTransaction;
    
    protected String estadoAgendamentoString = "", corAgendamento;
    
    
    public AdmsDefsGenericaSolicitacaoBean()
    {
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
//    */
    public AdmsServicoSolicitado getServicoPorProcurarAgendamento()
    {
        return servicoPorProcurarAgendamento;
    }
    

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void setServicoLista(List<AdmsServico> servicoLista)
    {
        this.servicoLista = servicoLista;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
     */

    public boolean isAgendamentoMedico()
    {
        return agendamentoMedico;
    }

    public void setAgendamentoMedico(boolean agendamentoMedico)
    {
        this.agendamentoMedico = agendamentoMedico;
    }

    public boolean isPodeAgendarParaMedico()
    {
        return podeAgendarParaMedico;
    }

    public void setPodeAgendarParaMedico(boolean podeAgendarParaMedico)
    {
        this.podeAgendarParaMedico = podeAgendarParaMedico;
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
     */
    public RhFuncionario getMedico()
    {
        if(medico == null)
        {
            medico = new RhFuncionario();
        }
        return medico;
    }

    public void setMedico(RhFuncionario medico)
    {
        this.medico = medico;
    }
    
    /*
    
    
    gggggggggggggggggggggggggggggggggggggggggggggg
     */
    public List<AdmsAgendamentoMedico> getAgendamentosMedicos()
    {
        if(agendamentosMedicos == null)
        {
            agendamentosMedicos = new ArrayList<>();
        }
        return agendamentosMedicos;
    }

    public void setAgendamentosMedicos(List<AdmsAgendamentoMedico> agendamentosMedicos)
    {
        this.agendamentosMedicos = agendamentosMedicos;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public List<AdmsAgendamento> getAgendamentos()
    {
        if(agendamentos == null)
        {
            agendamentos = new ArrayList<>();
        }
        return agendamentos;
    }
    
    
    //***********************************************************
    public void gravarAgendamentos()
    {
        if(agendamentos == null || agendamentos.isEmpty()) return;
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPkIdAgendamento() == null)
            {
                if(podeAgendar(agendamentos.get(i).getFkIdServicoSolicitado()))
                {
                    admsAgendamentoFacade.create(agendamentos.get(i));
//                    criarEncargoParaOServicoAgendado(agendamentos.get(i).getFkIdServicoSolicitado());
                }
                else
                {
                    eliminarAgendamentoMedico(agendamentos.get(i));
                    Mensagem.warnMsg("O Serviço "+agendamentos.get(i).getFkIdServicoSolicitado().getFkIdServico().getNomeServico()+" Não Pode Ser Agendado");
                }
            }
        }
    }
    
    
    public void criarEncargoParaOServicoAgendado(AdmsServicoSolicitado servicoSolicitado)
    {
        System.out.println("bbbb "+servicoSolicitado.getPkIdServicoSolicitado());
        if(servicoSolicitado.getPkIdServicoSolicitado() == null || (finEncargoDevidoFacade.findEncargoDoServico(servicoSolicitado) == null))
        {
            System.out.println("entrou ");
            FinEncargoDevido encargo = new FinEncargoDevido();
            encargo.setDescricaoEncargoDevido(servicoSolicitado.getFkIdServico().getNomeServico());
            encargo.setQuantidade(1);
            encargo.setFkIdEstadoPagamentoPagoNaopago(finEstadoPagamentoPagaNaopagoFacade.findEstadoPagamentoNaoPago());
//            encargo.setValor(servicoSolicitado.getFkIdPrecoCategoriaServico().getValor());
            definirValorASerPagoPeloTipo(encargo, servicoSolicitado);
            encargo.setFkIdTipoEncargo(finTipoEncargoFacade.findTipoEncargoServico());
            encargo.setFkIdPrecoCategoriaServico(servicoSolicitado.getFkIdPrecoCategoriaServico());
            encargo.setFkIdServicoSolicitado(servicoSolicitado);
            encargo.setData(new Date());
            encargo.setFkIdSubprocesso(servicoSolicitado.getFkIdSolicitacao().getFkIdSubprocesso());
//            encargo.setFkIdEstadoEncargoDevido(fin);
            finEncargoDevidoFacade.create(encargo);
        }
    }
    
    
public abstract void definirValorASerPagoPeloTipo(FinEncargoDevido encargo, AdmsServicoSolicitado servicoSolicitado);
    
    
    
    
    
    public void gravarAgendamentosMedicos()
    {
        if(agendamentosMedicos == null || agendamentosMedicos.isEmpty()) return;
        for(int i = 0; i < agendamentosMedicos.size(); i++)
        {
            if(agendamentosMedicos.get(i).getPkIdAgendamentoMedico() == null)
            {
                admsAgendamentoMedicoFacade.create(agendamentosMedicos.get(i));
            }
        }
    }
    
    
    public void eliminarAgendamentoMedico(AdmsAgendamento agendamento)
    {
        for(int i = 0; i < agendamentosMedicos.size(); i++)
        {
            if(agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado() == agendamento.getFkIdServicoSolicitado().getPkIdServicoSolicitado())
            {
                agendamentosMedicos.remove(i);
                break;
            }
        }
    }
    
    
    public boolean podeAgendar(AdmsServicoSolicitado servicoSolicitadoPorProcurar)
    {
        AdmsAgendamento ultimoAgendamento = admsAgendamentoFacade.findUltimoAgendamentoServico(servicoSolicitadoPorProcurar);
        return AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().podeAgendar(ultimoAgendamento);
    }

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void setAgendamentos(List<AdmsAgendamento> agendamentos)
    {
        this.agendamentos = agendamentos;
    }
    

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public String getEstadoAgendamentoString()
    {
        return estadoAgendamentoString;
    }

    public void setEstadoAgendamentoString(String estadoAgendamentoString)
    {
        this.estadoAgendamentoString = estadoAgendamentoString;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void limparDatas()
    {
        estadoAgendamentoString = "";
        agendamentoMarcado = null;
        agendamentoMedicoMarcado = null;
        agendamento = null;
    }

    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
     */
    public AdmsAgendamento getAgendamentoMarcado()
    {
        if(agendamentoMarcado == null)
        {
            agendamentoMarcado = new AdmsAgendamento();
        }
        return agendamentoMarcado;
    }

    public void setAgendamentoMarcado(AdmsAgendamento agendamentoMarcado)
    {
        this.agendamentoMarcado = agendamentoMarcado;
    }
    
    
    

    public AdmsAgendamentoMedico getAgendamentoMedicoMarcado()
    {
        if(agendamentoMedicoMarcado == null)
        {
            agendamentoMedicoMarcado = new AdmsAgendamentoMedico();
        }
        return agendamentoMedicoMarcado;
    }

    public void setAgendamentoMedicoMarcado(AdmsAgendamentoMedico agendamentoMedicoMarcado)
    {
        this.agendamentoMedicoMarcado = agendamentoMedicoMarcado;
    }
    
    
    
    public void definirDataHoraAgendada(AdmsServicoSolicitado servicoSolicitado)
    {
        //verifica se este agendamento ainda n\ao esta no array
        AdmsAgendamento ultimoAgendamento = admsAgendamentoFacade.findAgendamentoByServicoSolicitado(servicoSolicitado);
        int posicao = getPosicaoAgendamento(servicoSolicitado);

        if(posicao > -1)
        {
            if(!(ultimoAgendamento == null))
            {
                if(!AdmsDefinicoesParaClassesComAgendamentoBean.getInstanciaBean().verificarSeAgendamnetosSaoIguais(ultimoAgendamento, agendamentos.get(posicao)))
                {
                    agendamentos.set(posicao, ultimoAgendamento);
                    int posicaoMedica = getPosicaoAgendamentoMedico(agendamentos.get(posicao));
                    if(posicaoMedica > -1) agendamentosMedicos.remove(posicaoMedica);
                }
            }
            agendamentoMarcado = agendamentos.get(posicao);
            if(agendamentoMarcado.getPkIdAgendamento() == null)
                estadoAgendamentoString = "***"+agendamentoMarcado.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento()+"***";
            else estadoAgendamentoString = ""+agendamentoMarcado.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento();
            definirMedicoAgendado(agendamentoMarcado);
            transferirDadosParaVariavelAgendamento();
        }
        
        else
        {
            agendamentoMarcado = ultimoAgendamento;
            if(agendamentoMarcado == null)
            {
                agendamento = null;
                if(!verificarSePossuiServicoAnteriorCancelado(servicoSolicitado))
                {
                    agendamentoMedicoMarcado = null;
                    corAgendamento = ConstantesAdms.PRETO;
                    estadoAgendamentoString = "Sem Agendamento";
                }
            }
            else
            {
                definirMedicoAgendado(agendamentoMarcado);
                estadoAgendamentoString = ""+agendamentoMarcado.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento();
                agendamentos.add(agendamentoMarcado);
                transferirDadosParaVariavelAgendamento();
            }
        }
        definirCorEstadoAgendamento(estadoAgendamentoString);
    }
    
    public void definirMedicoAgendado(AdmsAgendamento agendamento)
    {
        //verifica se este agendamento ainda n\ao esta no array
        int posicao = getPosicaoAgendamentoMedico(agendamento);
        if(posicao > -1)
        {
            agendamentoMedicoMarcado = agendamentosMedicos.get(posicao);
        }
        else
        {
            agendamentoMedicoMarcado = admsAgendamentoMedicoFacade.getAgendamentoMedico(agendamento);
            if(!(agendamentoMedicoMarcado == null))
            {
                agendamentosMedicos.add(agendamentoMedicoMarcado);
            }
        }
    }
    
    
    public void transferirDadosParaVariavelAgendamento()
    {
        if(agendamento == null) agendamento = new AdmsAgendamento();
        agendamento.setPkIdAgendamento(agendamentoMarcado.getPkIdAgendamento());
        agendamento.setFkIdEstadoAgendamento(agendamentoMarcado.getFkIdEstadoAgendamento());
        agendamento.setFkIdServicoSolicitado(agendamentoMarcado.getFkIdServicoSolicitado());
//        agendamento.setDataAgendada(agendamentoMarcado.getDataAgendada());
//        agendamento.setHoraAgendada(agendamentoMarcado.getHoraAgendada());
        agendamento.setDataHoraInicio(agendamentoMarcado.getDataHoraInicio());
    }
    
    public boolean verificarSePossuiServicoAnteriorCancelado(AdmsServicoSolicitado servicoSolicitado)
    {
        if(servicoSolicitado.getPkIdServicoSolicitado() == null) return false;
        agendamentoMarcado = admsAgendamentoFacade.findUltimoAgendamentoServico(servicoSolicitado);
        if(agendamentoMarcado == null) return false;
        estadoAgendamentoString = agendamentoMarcado.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento();
        return true;
    }
    

    
    public void definirCorEstadoAgendamento(String estado)
    {
        if(estado.equalsIgnoreCase(""))
            corAgendamento = ConstantesAdms.PRETO;
        else if(estado.equalsIgnoreCase("Chegou"))
            corAgendamento = ConstantesAdms.VERDE;
        else if(estado.equalsIgnoreCase("Agendado"))
            corAgendamento = ConstantesAdms.LARANJA;
        else if(estado.equalsIgnoreCase("***Agendado***"))
            corAgendamento = ConstantesAdms.AGENDADONAOGRAVADO;
        else if(estado.equalsIgnoreCase("***Chegou***"))
            corAgendamento = ConstantesAdms.AGENDADONAOGRAVADO;
        else if(estado.equalsIgnoreCase("Cancelado"))
            corAgendamento = ConstantesAdms.VERMELHO;
        else if(estado.equalsIgnoreCase("Não Apareceu"))
            corAgendamento = ConstantesAdms.CINZA;
        else if(estado.equalsIgnoreCase("Efetuado"))
            corAgendamento = ConstantesAdms.AZUL;
    }
    
    


    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoDoServicoCasoExista(AdmsServicoSolicitado servicoRemover)
    {
        if(agendamentos == null || agendamentos.isEmpty()) return;
        int posicao = getPosicaoAgendamento(servicoRemover);
        if(posicao >= 0)
        {
            if(!(agendamentosMedicos == null || agendamentosMedicos.isEmpty()))
                removerAgendamentoMedico(agendamentos.get(posicao));
            if(!(agendamentos.get(posicao).getPkIdAgendamento() == null))
                removerAgendamentoDeManeiraPersistente(agendamentos.get(posicao));
            agendamentos.remove(posicao);
        }
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoDoPorCancelamento(AdmsServicoSolicitado servicoRemover)
    {
        if(agendamentos == null || agendamentos.isEmpty()) return;
        int posicao = getPosicaoAgendamento(servicoRemover);
        if(posicao >= 0)
        {
            if(!(agendamentosMedicos == null || agendamentosMedicos.isEmpty()))
                removerAgendamentoMedico(agendamentos.get(posicao));
            removerEncargoDoServico(agendamentos.get(posicao).getFkIdServicoSolicitado());
            agendamentos.remove(posicao);
            
        }
    }
    
    
    public void removerEncargoDoServico(AdmsServicoSolicitado servicoSolicitadoLocal)
    {
        FinEncargoDevido encargo = finEncargoDevidoFacade.findEncargoDoServico(servicoSolicitadoLocal);
        if(encargo != null && finPagamentoEncargoDevidoFacade.findPagamentoEncargoByEncargo(encargo) == null)
        {
//            FinPagamentoEncargoDevido pagamentoEncargo = finPagamentoEncargoDevidoFacade.findUltimoPagamentoAtivoDoEncargo(encargo);
//            if(pagamentoEncargo == null)
//            {
                finEncargoDevidoFacade.remove(encargo);
                servicoSolicitadoLocal.setFkIdEstadoPagamento(admsEstadoPagamentoFacade.findPagamentoPendente());
                admsServicoSolicitadoFacade.edit(servicoSolicitadoLocal);
//            }
        }
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoMedico(AdmsAgendamento agendamento)
    {
        if(agendamentosMedicos == null || agendamentosMedicos.isEmpty()) return;
        int posicao = getPosicaoAgendamentoMedico(agendamento);
        if(posicao >= 0)
        {
            if(!(agendamentosMedicos.get(posicao).getPkIdAgendamentoMedico()== null))
            {
                removerAgendamentoMedicoDeManeiraPersistente(agendamentosMedicos.get(posicao));
            }
            agendamentosMedicos.remove(posicao);
        }
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public int getPosicaoAgendamentoMedico(AdmsAgendamento agendamento)
    {
        for(int i = 0; i < agendamentosMedicos.size(); i ++)
        {
            if(agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdServico().getPkIdServico() == agendamento.getFkIdServicoSolicitado().getFkIdServico().getPkIdServico()
                 && agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico() == agendamento.getFkIdServicoSolicitado().getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico())
            {
                if(agendamento.getFkIdServicoSolicitado().getPkIdServicoSolicitado() == null && agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado() == null)
                    return i;
                if(agendamento.getFkIdServicoSolicitado().getPkIdServicoSolicitado() != null && agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado() != null)
                {
                    if(agendamentosMedicos.get(i).getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado() == agendamento.getFkIdServicoSolicitado().getPkIdServicoSolicitado())
                    {
                        return i;
                    }  
                }
            }
        } 
        return -1;   
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoMedicoDeManeiraPersistente(AdmsAgendamentoMedico agendamentoMedico)
    {
        admsAgendamentoMedicoFacade.remove(agendamentoMedico);
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoDeManeiraPersistente(AdmsAgendamento agendamento)
    {
        admsAgendamentoFacade.remove(agendamento);
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public int getPosicaoAgendamento(AdmsServicoSolicitado admsServicoSolicitado)
    {
        
        for(int i = 0; i < agendamentos.size(); i ++)
        {            
            if(agendamentos.get(i).getFkIdServicoSolicitado().getFkIdServico().getPkIdServico() == admsServicoSolicitado.getFkIdServico().getPkIdServico()
                 && agendamentos.get(i).getFkIdServicoSolicitado().getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico() == admsServicoSolicitado.getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico())
            {
                if(admsServicoSolicitado.getPkIdServicoSolicitado() == null && agendamentos.get(i).getFkIdServicoSolicitado().getPkIdServicoSolicitado() == null)
                    return i;
                if(admsServicoSolicitado.getPkIdServicoSolicitado() != null && agendamentos.get(i).getFkIdServicoSolicitado().getPkIdServicoSolicitado() != null)
                {
                    if(agendamentos.get(i).getFkIdServicoSolicitado().getPkIdServicoSolicitado() == admsServicoSolicitado.getPkIdServicoSolicitado())
                    {
                        return i;
                    }  
                }
            }
        } 
        return -1;
    }

    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public int pegarPosicaoServicoSolicitado(AdmsServicoSolicitado servicoSolicitado, AdmsSolicitacao solicitacao)
    {
//        System.out.println("veio pegar a posicao ");
        for(int i = 0; i <= solicitacao.getAdmsServicoSolicitadoList().size(); i++)
        {
            if(solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdServico().getPkIdServico() == servicoSolicitado.getFkIdServico().getPkIdServico()
                && solicitacao.getAdmsServicoSolicitadoList().get(i).getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico() == servicoSolicitado.getFkIdPrecoCategoriaServico().getPkIdPrecoCategoriaServico())
            {
                return i;
            }
        }
        return -1;
    }
    
    
    
    public void removerServicoSolicitadoESeuAgendamentoPersistenteMente(AdmsServicoSolicitado servicoSolicitadoRemover)
    {
        removerAgendamentoDoServicoCasoExista(servicoSolicitadoRemover);
        removerServicoSolicitadoDeManeiraPersistente(servicoSolicitadoRemover);
        
    }
    
    public void removerServicoSolicitadoPelaPosicao(int posicao, AdmsSolicitacao solicitacaoServicoSolicitado)
    {
        if(solicitacaoServicoSolicitado.getAdmsServicoSolicitadoList().get(posicao).getPkIdServicoSolicitado() != null)
        {
            removerServicoSolicitadoDeManeiraPersistente(solicitacaoServicoSolicitado.getAdmsServicoSolicitadoList().get(posicao));
            System.out.println("removeu persistente");
        }
        
        solicitacaoServicoSolicitado.getAdmsServicoSolicitadoList().remove(posicao);
        System.out.println("removeu");
        
//        AdmsSolicitacao solicitacaoTemp = new AdmsSolicitacao();
//        solicitacaoTemp.setData(solicitacaoServicoSolicitado.getData());
//        solicitacaoTemp.setFkIdCentro(solicitacaoServicoSolicitado.getFkIdCentro());
//        solicitacaoTemp.setFkIdFuncionarioSolicitante(solicitacaoServicoSolicitado.getFkIdFuncionarioSolicitante());
//        solicitacaoTemp.setFkIdPaciente(solicitacaoServicoSolicitado.getFkIdPaciente());
//        solicitacaoTemp.setFkIdResponsavelPaciente(solicitacaoServicoSolicitado.getFkIdResponsavelPaciente());
//        solicitacaoTemp.setFkIdServicoEfetuado(solicitacaoServicoSolicitado.getFkIdServicoEfetuado());
//        solicitacaoTemp.setFkIdSubprocesso(solicitacaoServicoSolicitado.getFkIdSubprocesso());
//        solicitacaoTemp.setObservacao(solicitacaoServicoSolicitado.getObservacao());
//        solicitacaoTemp.setAdmsServicoSolicitadoList(solicitacaoServicoSolicitado.getAdmsServicoSolicitadoList());
//        solicitacaoTemp.setPkIdSolicitacao(solicitacaoServicoSolicitado.getPkIdSolicitacao());
        

        
//        solicitacaoServicoSolicitado.getAdmsServicoSolicitadoList().clear();
        
//        solicitacaoServicoSolicitado = null;
//        
//        solicitacaoServicoSolicitado = solicitacaoTemp;
        
//        solicitacaoTemp.setData(solicitacao.getData());
//        solicitacaoTemp.setFkIdCentro(solicitacao.getFkIdCentro());
//        solicitacaoTemp.setFkIdFuncionarioSolicitante(solicitacao.getFkIdFuncionarioSolicitante());
//        solicitacaoTemp.setFkIdPaciente(solicitacao.getFkIdPaciente());
//        solicitacaoTemp.setFkIdResponsavelPaciente(solicitacao.getFkIdResponsavelPaciente());
//        solicitacaoTemp.setFkIdServicoEfetuado(solicitacao.getFkIdServicoEfetuado());
//        solicitacaoTemp.setFkIdSubprocesso(solicitacao.getFkIdSubprocesso());
//        solicitacaoTemp.setObservacao(solicitacao.getObservacao());
//        solicitacaoTemp.setAdmsServicoSolicitadoList(solicitacao.getAdmsServicoSolicitadoList());
//        solicitacaoTemp.setPkIdSolicitacao(solicitacao.getPkIdSolicitacao());
    }
    
    
    
//    public void removerEncargoDoServicoCasoNaoPago(AdmsServicoSolicitado servicoSolicitadoLocal)
//    {
//        FinEncargoDevido encargoDevidoLocal = finEncargoDevidoFacade.findEncargoDoServico(servicoSolicitado);
//        if(encargoDevidoLocal != null)
//        {
//            if(encargoDevidoLocal.getFkIdEstadoPagamentoPagoNaopago().getDescricao().equalsIgnoreCase("Pago"))
//            {
//                new Exception("O Serviço Já Se Encontra Pago, Não Ser Eliminado, Tenta Cancelar o Pagamento Primeiro!");
//            }
//        }
//    }
    

       
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerServicoSolicitadoDeManeiraPersistente(AdmsServicoSolicitado servicoSolicitado)
    {
        admsServicoSolicitadoFacade.remove(servicoSolicitado);
        remocaoPersistente = true;
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    
    public boolean podeRemover(AdmsServicoSolicitado servicoRemover)
    {
        if(servicoRemover.getPkIdServicoSolicitado() == null) return true;
        return !foiEfetuado(servicoRemover) && !temPagamento(servicoRemover);
    }
    
    public boolean renderizaRemover(AdmsServicoSolicitado servicoRemover, boolean removerJaGravado)
    {
        boolean podeRemover = podeRemover(servicoRemover);
        
        if(!podeRemover) return false;
        
        if((servicoRemover.getPkIdServicoSolicitado() == null) && !removerJaGravado){
            return true;
        }
        if(!(servicoRemover.getPkIdServicoSolicitado() == null) && !removerJaGravado){
            return false;
        }
        
        if(!(servicoRemover.getPkIdServicoSolicitado() == null) && removerJaGravado){
            return true;
        }
        
        if((servicoRemover.getPkIdServicoSolicitado() == null) && removerJaGravado){
            return false;
        }
        
        return false;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    
    public boolean temPagamento(AdmsServicoSolicitado servicoSolicitado)
    {
        return servicoSolicitado.getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equalsIgnoreCase("Pago");
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public boolean foiEfetuado(AdmsServicoSolicitado servicoSolicitado)
    {
        AdmsServicoEfetuado servicoEfetuado = admsServicoEfetuadoFacade.findServicoEfetuadoPorServicoSolicitado(servicoSolicitado);
        return !(servicoEfetuado == null);
    }

    /******************************************************************* Dados da agenda
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    
    public boolean isCarregarAgenda()
    {
        return carregarAgenda;
    }

    public void setCarregarAgenda(boolean carregarAgenda)
    {
        this.carregarAgenda = carregarAgenda;
    }
    
    
    
    public void definirAgendamento(Date dataAgendamentoInicio, Date dataAgendamentoFim)
    {
//        Date data = new Date();
        
        if(agendamentos == null)
        {
            agendamentos = new ArrayList<>();
        }
        removerAgendamentoDoServicoCasoExista(servicoPorProcurarAgendamento);
        
        AdmsAgendamento agendamentoAdd = new AdmsAgendamento();
//        agendamentoAdd.setDataAgendada(dataAgendamentoInicio);
        agendamentoAdd.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoAgendado());
//        agendamentoAdd.setHoraAgendada(dataAgendamentoInicio);
        //8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        agendamentoAdd.setDataHoraInicio(dataAgendamentoInicio);
        agendamentoAdd.setDataHoraFim(dataAgendamentoFim);
        agendamentoAdd.setFkIdServicoSolicitado(servicoPorProcurarAgendamento);

        agendamentos.add(agendamentoAdd);
        adicionarAgendamentoMedico();
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void adicionarAgendamentoMedico()
    {
        if(agendamentoMedico)
        {
            if(!(medico.getPkIdFuncionario() == null))
            {
                AdmsAgendamentoMedico agendamentoMedicoAdicionar = new AdmsAgendamentoMedico();
                agendamentoMedicoAdicionar.setFkIdAgendamento(agendamentos.get(agendamentos.size() - 1));
                RhFuncionario medicoParaAgenda = rhFuncionarioFacade.find(medico.getPkIdFuncionario());
                agendamentoMedicoAdicionar.setFkIdMedico(medicoParaAgenda);
                
                if(agendamentosMedicos == null) getAgendamentosMedicos();
                agendamentosMedicos.add(agendamentoMedicoAdicionar);
            }
        }
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void naoPermitirAgendamentoMedico()
    {
        agendamentoMedico = false;
        podeAgendarParaMedico = false;
    }
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void removerAgendamentoServicoPorProcurar()
    {
        removerAgendamentoDoServicoCasoExista(servicoPorProcurarAgendamento);
    }
    
    
    /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void inicializarCamposAgendamentoMedico()
    {
//        agendamentoMedico = false;
        podeAgendarParaMedico = servicoPorProcurarAgendamento.getFkIdServico().getPodeTerMedico();
        medico = null;
    }
    
        /*
    gggggggggggggggggggggggggggggggggggggggggggggg
    
    
    
    */
    public void setServicoPorProcurarAgendamento(AdmsServicoSolicitado servicoPorProcurarAgendamento)
    {
        if(servicoPorProcurarAgendamento == null) getServicoPorProcurarAgendamento();
        this.servicoPorProcurarAgendamento = servicoPorProcurarAgendamento;
        agendamentoMedico = false;
        inicializarCamposAgendamentoMedico();
        carregarAgenda = true;
    }

    public AdmsAgendamento getAgendamento()
    {
        return agendamento;
    }

    public void setAgendamento(AdmsAgendamento agendamento)
    {
        this.agendamento = agendamento;
    }
    
    
    public boolean possuiAgendamentosNaoGravados()
    {
        if(agendamentos == null) return false;
        for(int i = 0; i < agendamentos.size(); i++)
        {
            if(agendamentos.get(i).getPkIdAgendamento() == null)
                return true;
        }
        if(agendamentosMedicos == null) return false;
        for(int i = 0; i < agendamentosMedicos.size(); i++)
        {
            if(agendamentosMedicos.get(i).getPkIdAgendamentoMedico() == null)
                return true;
        }
        return false;
    }
    
    
    
    public String getCorAgendamento()
    {
        return corAgendamento;
    }

    public void setCorAgendamento(String corAgendamento)
    {
        this.corAgendamento = corAgendamento;
    }
    
}
