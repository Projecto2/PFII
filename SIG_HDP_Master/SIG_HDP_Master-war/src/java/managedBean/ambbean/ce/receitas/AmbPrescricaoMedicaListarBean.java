/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.receitas;

import entidade.AdmsPaciente;
import entidade.AmbConsulta;
import entidade.AmbPrescricaoMedica;
import entidade.AmbPrescricaoMedicaHasProduto;
import entidade.FarmProduto;
import entidade.InterHoraMedicacao;
import entidade.SegConta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.UserTransaction;
import managedBean.interbean.InterObjetosSessaoBean;
import managedBean.segbean.SegLoginBean;
import sessao.AmbConsultaFacade;
import sessao.AmbPrescricaoMedicaFacade;
import sessao.AmbPrescricaoMedicaHasProdutoFacade;
import sessao.InterHoraMedicacaoFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbPrescricaoMedicaListarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;    

    @EJB
    private AmbConsultaFacade ambConsultaFacade;    
    @EJB
    private AmbPrescricaoMedicaFacade ambPrescricaoMedicaFacade;    
    @EJB
    private AmbPrescricaoMedicaHasProdutoFacade ambPrescricaoMedicaHasProdutoFacade;
    @EJB
    private InterHoraMedicacaoFacade interHoraMedicacaoFacade;
    
    private final Calendar dataCorrente = Calendar.getInstance();

    private Date dataInicio 
               , dataFim
               , dataRegistoPesq;
    
    private SegConta segConta = SegLoginBean.obterSegLoginBean().obterContaDaCorrenteSessao();

    private List<AmbPrescricaoMedica> listaPrescricao;
    private List<AmbPrescricaoMedicaHasProduto> listaPrescricaoProduto, listaPrescricaoAux;
    
    private AdmsPaciente admsPaciente;
    private AmbPrescricaoMedica apmAux;
    private AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProduto;
    
//    private long codigoPrescricaoProduto; 
    
    private int codigoHoraMedicacao
              , codigoCentroHospitalar
              , codigoPrescricaoProduto
              , fkIdViaAdministracao 
              , fkIdFormaFarmaceutica
              , formaFarmaceuticaPesq
              , opcaoMedicacaoPesq
              , quantidade; 

    
    private String horarioPesq
                 , medicamentoPesq
                 , nomeMedico
                 , nomeDoMeioMedico
                 , observacoes
                 , sobreNomeMedico
                 , tipoServico = InterObjetosSessaoBean.getInstanciaBean().getServicoInter()
                 , viaPesq;
    
    /**
     * Creates a new instance of AmbPrescricaoMedicaListarBean
     */
    public AmbPrescricaoMedicaListarBean()
    {
        quantidade += 1;
    }
    
    public static AmbPrescricaoMedicaCriarBean getInstanciaAmbPrescricaoMedicaCriarBean()
    {
        return (AmbPrescricaoMedicaCriarBean) GeradorCodigo.getInstanciaBean("ambPrescricaoMedicaCriarBean");
    }
    
    public static AmbPrescricaoMedicaListarBean getInstanciaBean()
    {
        return (AmbPrescricaoMedicaListarBean) GeradorCodigo.getInstanciaBean("ambPrescricaoMedicaListarBean");
    }

    public List<AmbPrescricaoMedica> getListaPrescricao()
    {
        return listaPrescricao;
    }

    public void setListaPrescricao(List<AmbPrescricaoMedica> listaPrescricao)
    {
        this.listaPrescricao = listaPrescricao;
    }

    public List<AmbPrescricaoMedicaHasProduto> getListaPrescricaoProduto()
    {
        return listaPrescricaoProduto;
    }

    public void setListaPrescricaoProduto(List<AmbPrescricaoMedicaHasProduto> listaPrescricaoProduto)
    {
        this.listaPrescricaoProduto = listaPrescricaoProduto;
    }

    public AmbPrescricaoMedica getApmAux()
    {
        if (apmAux == null)
        {
            apmAux = getInstanciaAmbPrescricaoMedicaCriarBean().getAmbPrescricaoMedica();
        }
        return apmAux;
    }

    public void setApmAux(AmbPrescricaoMedica apmAux)
    {
        this.apmAux = apmAux;
    }

    public AmbPrescricaoMedicaHasProduto getAmbPrescricaoMedicaHasProduto()
    {
        if (ambPrescricaoMedicaHasProduto == null)
        {
            ambPrescricaoMedicaHasProduto = getInstanciaAmbPrescricaoMedicaCriarBean().getAmbPrescricaoMedicaHasProduto();
        }
        return ambPrescricaoMedicaHasProduto;
    }

    public void setAmbPrescricaoMedicaHasProduto(AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProduto)
    {
        this.ambPrescricaoMedicaHasProduto = ambPrescricaoMedicaHasProduto;
    }

    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim()
    {
        if (dataFim == null)
        {
            dataFim = new Date();
        }
        return dataFim;
    }

    public void setDataFim(Date dataFim)
    {
        this.dataFim = dataFim;
    }

    public int getCodigoCentroHospitalar()
    {
        return codigoCentroHospitalar;
    }

    public void setCodigoCentroHospitalar(int codigoCentroHospitalar)
    {
        this.codigoCentroHospitalar = codigoCentroHospitalar;
    }
    
    public String getNomeMedico()
    {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico)
    {
        this.nomeMedico = nomeMedico;
    }

    public String getNomeDoMeioMedico()
    {
        return nomeDoMeioMedico;
    }

    public void setNomeDoMeioMedico(String nomeDoMeioMedico)
    {
        this.nomeDoMeioMedico = nomeDoMeioMedico;
    }

    public String getSobreNomeMedico()
    {
        return sobreNomeMedico;
    }

    public void setSobreNomeMedico(String sobreNomeMedico)
    {
        this.sobreNomeMedico = sobreNomeMedico;
    }
    
    public String getMedicamentoPesq()
    {
        return medicamentoPesq;
    }

    public void setMedicamentoPesq(String medicamentoPesq)
    {
        this.medicamentoPesq = medicamentoPesq;
    }

    public String getObservacoes()
    {
        return observacoes;
    }

    public void setObservacoes(String observacoes)
    {
        this.observacoes = observacoes;
    }

    public String getViaPesq()
    {
        return viaPesq;
    }

    public void setViaPesq(String viaPesq)
    {
        this.viaPesq = viaPesq;
    }
    
    public int getFkIdViaAdministracao()
    {
        return fkIdViaAdministracao;
    }

    public void setFkIdViaAdministracao(int fkIdViaAdministracao)
    {
        this.fkIdViaAdministracao = fkIdViaAdministracao;
    }

    public int getFkIdFormaFarmaceutica()
    {
        return fkIdFormaFarmaceutica;
    }

    public void setFkIdFormaFarmaceutica(int fkIdFormaFarmaceutica)
    {
        this.fkIdFormaFarmaceutica = fkIdFormaFarmaceutica;
    }
    
    public int getFormaFarmaceuticaPesq()
    {
        return formaFarmaceuticaPesq;
    }

    public void setFormaFarmaceuticaPesq(int formaFarmaceuticaPesq)
    {
        this.formaFarmaceuticaPesq = formaFarmaceuticaPesq;
    }

    public int getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }

    public String getHorarioPesq()
    {
        return horarioPesq;
    }

    public void setHorarioPesq(String horarioPesq)
    {
        this.horarioPesq = horarioPesq;
    }

    public Date getDataRegistoPesq()
    {
        return dataRegistoPesq;
    }

    public void setDataRegistoPesq(Date dataRegistoPesq)
    {
        this.dataRegistoPesq = dataRegistoPesq;
    }

    public int getCodigoPrescricaoProduto()
    {
        return codigoPrescricaoProduto;
    }

    public void setCodigoPrescricaoProduto(int codigoPrescricaoProduto)
    {
        this.codigoPrescricaoProduto = codigoPrescricaoProduto;
    }

    public int getCodigoHoraMedicacao()
    {
        return codigoHoraMedicacao;
    }

    public void setCodigoHoraMedicacao(int codigoHoraMedicacao)
    {
        this.codigoHoraMedicacao = codigoHoraMedicacao;
    }

    public int getOpcaoMedicacaoPesq()
    {
        return opcaoMedicacaoPesq;
    }

    public void setOpcaoMedicacaoPesq(int opcaoMedicacaoPesq)
    {
        this.opcaoMedicacaoPesq = opcaoMedicacaoPesq;
    }
    
    public boolean verifica(long idConsulta)
    {
        if (!ambPrescricaoMedicaFacade.findAll().isEmpty())
            for (AmbPrescricaoMedica apm : ambPrescricaoMedicaFacade.findAll())
                 if (apm.getFkIdConsulta().getPkIdConsulta().equals(idConsulta))
                     return true;
        return false;
    }   
    
    public boolean verificaReceita(long idConsulta)
    {
        if (!ambPrescricaoMedicaFacade.findAll().isEmpty())
            for (AmbPrescricaoMedica apm : ambPrescricaoMedicaFacade.findAll())
                 if (apm.getFkIdConsulta().getPkIdConsulta().equals(idConsulta))
                     return true;
        return false;
    }     
    
    public void pesquisar(int pk_id_medicacao)
    {
        listaPrescricaoProduto = ambPrescricaoMedicaHasProdutoFacade.pesquisarRegisto(tipoServico, nomeMedico, nomeDoMeioMedico, sobreNomeMedico, dataRegistoPesq, AmbPrescricaoMedicaCriarBean.getInstanciaBean().getAmbConsultaAux().getPkIdConsulta(), 0, 0, 0, null);

        if (listaPrescricaoProduto.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaPrescricaoProduto.size() + " registo(s) retornado(s).");
        }
    }
    
    public List<AmbPrescricaoMedicaHasProduto> pesquisarPrescricoes()
    {
        return ambPrescricaoMedicaHasProdutoFacade.findPrescricao(ambPrescricaoMedicaHasProduto, dataInicio, dataFim);
    }
    
    public List<AmbPrescricaoMedicaHasProduto> pesquisarPorViaAdms(int pkIdMedicamento)
    {
        List<AmbPrescricaoMedicaHasProduto> listaAux = ambPrescricaoMedicaHasProdutoFacade.pesquisarRegisto(tipoServico, nomeMedico, nomeDoMeioMedico, sobreNomeMedico, dataRegistoPesq, AmbPrescricaoMedicaCriarBean.getInstanciaBean().getAmbConsultaAux().getPkIdConsulta(), 0, 0, pkIdMedicamento, null);
        
        return listaAux;
    }
    
    public List<AmbPrescricaoMedica> findAll()
    {
        return ambPrescricaoMedicaFacade.findAll();
    }
    
    public List<AmbPrescricaoMedicaHasProduto> findAllPrescricoes()
    {
        return ambPrescricaoMedicaHasProdutoFacade.findAll();
    }
    
    public List<InterHoraMedicacao> listarTodosHorarios()
    {
        return interHoraMedicacaoFacade.findAll();
    }
    
    public List<FarmProduto> lerTodosMedicamentosPorViaAdmissao()
    {
        int categoria;

        if (AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel1().getPkIdCategoriaMedicamento() == null)
        {
            categoria = 0;
        }

        else if (AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel2().getPkIdCategoriaMedicamento() == null)
        {
            categoria = AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel1().getPkIdCategoriaMedicamento();
        }

        else if (AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel3().getPkIdCategoriaMedicamento() == null)
        {
            categoria = AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel2().getPkIdCategoriaMedicamento();
        }

        else if (AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel4().getPkIdCategoriaMedicamento() == null)
        {
            categoria = AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel3().getPkIdCategoriaMedicamento();
        }
        else
        {
            categoria = AmbFarmProdutoListarBean.getInstanciaBean().getCategoriaNivel4().getPkIdCategoriaMedicamento();
        }

        List<FarmProduto> lista = ambPrescricaoMedicaHasProdutoFacade.produtosPorViaAdmissao(fkIdViaAdministracao, fkIdFormaFarmaceutica, categoria);
        
        List<FarmProduto> listaAux = new ArrayList();

        for (int i = 0; i < lista.size(); i++)
        {
            if (pesquisarPorViaAdms(lista.get(i).getPkIdProduto()).isEmpty())
            {
                listaAux.add(lista.get(i));
            }
        }

        return listaAux;
    }
    
    public List<AmbPrescricaoMedicaHasProduto> pesquisaDeReceitaConsulta(String np)
    {
        List<AmbPrescricaoMedicaHasProduto> resultado = new ArrayList<>();

        if (ambPrescricaoMedicaHasProdutoFacade.findAll() != null)
            for (AmbPrescricaoMedicaHasProduto apmhp : ambPrescricaoMedicaHasProdutoFacade.findAll())
                 if (apmhp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np))
                     if (apmhp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_PRIMEIRA))
                         resultado.add(apmhp);
        return resultado;
    }     
    
    public List<AmbPrescricaoMedicaHasProduto> pesquisaDeReceitaReconsulta(String np)
    {
        List<AmbPrescricaoMedicaHasProduto> resultado = new ArrayList<>();

        if (ambPrescricaoMedicaHasProdutoFacade.findAll() != null)
            for (AmbPrescricaoMedicaHasProduto apmhp : ambPrescricaoMedicaHasProdutoFacade.findAll())
                 if (apmhp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np))
                     if (apmhp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_RETORNO))
                         resultado.add(apmhp);
        return resultado;
    }   
    
    public List<AmbPrescricaoMedicaHasProduto> lerPrescricoesConsulta(AmbConsulta ac) 
    {
        List<AmbPrescricaoMedicaHasProduto> aphp = new ArrayList<>();

        if (findAllPrescricoes() != null)
            for (int i = 0; i < findAllPrescricoes().size(); i++)
                if (findAllPrescricoes().get(i).getFkIdPrescricaoMedica().getFkIdConsulta() != null)
                    if (findAllPrescricoes().get(i).getFkIdPrescricaoMedica().getFkIdConsulta().getPkIdConsulta().equals(ac.getPkIdConsulta()))
                        if (util.DataUtils.toString(findAllPrescricoes().get(i).getFkIdPrescricaoMedica().getFkIdConsulta().getDataHoraConsulta()).equals(util.DataUtils.toString(ac.getDataHoraConsulta())))
                            aphp.add(findAllPrescricoes().get(i));
        
        return aphp;
    } 
    
    public AmbPrescricaoMedica seleccionarDetalhesAmbPrescricaoMedica(AmbPrescricaoMedica apm)
    {
        apmAux = apm;
        return apmAux;
    }    
}
