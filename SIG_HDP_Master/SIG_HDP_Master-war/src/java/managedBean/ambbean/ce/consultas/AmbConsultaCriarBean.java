/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.consultas;

import entidade.AdmsAgendamento;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsTipoSolicitacaoServico;
import entidade.AmbAderencia;
import entidade.AmbCidCategorias;
import entidade.AmbCidSubcategorias;
import entidade.AmbClassificacaoDor;
import entidade.AmbColoracao;
import entidade.AmbConfirmacao;
import entidade.AmbConsulta;
import entidade.AmbConsultaHasColoracao;
import entidade.AmbConsultaHasCor;
import entidade.AmbConsultaHasEspessura;
import entidade.AmbConsultaHasImpressoesGerais;
import entidade.AmbConsultaHasTextura;
import entidade.AmbConsultaHasTurgorPele;
import entidade.AmbConsultaHasTurgorTecido;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbCor;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbDiagnosticoHipotese;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import entidade.AmbEspessura;
import entidade.AmbEstado;
import entidade.AmbEstadoHidratacao;
import entidade.AmbImpressoesGerais;
import entidade.AmbObservacoesMedicas;
import entidade.AmbTextura;
import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
import entidade.AmbTurgor;
import entidade.FarmCategoriaMedicamento;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmProduto;
import entidade.FarmTipoProduto;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlCentroHospitalar;
import entidade.GrlEndereco;
import entidade.GrlEspecialidade;
import entidade.GrlInstituicao;
import entidade.GrlPessoa;
import entidade.GrlTamanho;
import entidade.RhFuncionario;
import entidade.RhProfissao;
import entidade.SegConta;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaAmbDiagnostico;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean.getInstanciaAmbDiagnosticoHasDoenca;
import managedBean.ambbean.ce.diagnosticos.AmbHipoteseDiagnosticoBean;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaSupiMedicoHasEscala;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAdmsAgendamento;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagemHasSinal;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsPacienteFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AdmsTipoSolicitacaoServicoFacade;
import sessao.AmbAderenciaFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbClassificacaoDorFacade;
import sessao.AmbColoracaoFacade;
import sessao.AmbConfirmacaoFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultaHasColoracaoFacade;
import sessao.AmbConsultaHasCorFacade;
import sessao.AmbConsultaHasEspessuraFacade;
import sessao.AmbConsultaHasImpressoesGeraisFacade;
import sessao.AmbConsultaHasTexturaFacade;
import sessao.AmbConsultaHasTurgorPeleFacade;
import sessao.AmbConsultaHasTurgorTecidoFacade;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbCorFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbDiagnosticoHipoteseHasDoencaFacade;
import sessao.AmbEspessuraFacade;
import sessao.AmbEstadoFacade;
import sessao.AmbEstadoHidratacaoFacade;
import sessao.AmbImpressoesGeraisFacade;
import sessao.AmbObservacoesMedicasFacade;
import sessao.AmbSinalFacade;
import sessao.AmbTexturaFacade;
import sessao.AmbTriagemFacade;
import sessao.AmbTriagemHasSinalFacade;
import sessao.AmbTurgorFacade;
import sessao.FarmProdutoFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.GrlTamanhoFacade;
import sessao.RhFuncionarioFacade;
import util.GeradorCodigo;
import util.Mensagem;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultaCriarBean implements Serializable
{    
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    @EJB
    private AdmsTipoSolicitacaoServicoFacade admsTipoSolicitacaoServicoFacade;
    @EJB
    private AmbAderenciaFacade ambAderenciaFacade;
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;
    @EJB
    private AmbClassificacaoDorFacade ambClassificacaoDorFacade;
    @EJB
    private AmbColoracaoFacade ambColoracaoFacade;
    @EJB
    private AmbConsultaFacade ambConsultaFacade;  
    @EJB
    private AmbConfirmacaoFacade ambConfirmacaoFacade;     
    @EJB
    private AmbConsultaHasColoracaoFacade ambConsultaHasColoracaoFacade;
    @EJB
    private AmbConsultaHasCorFacade ambConsultaHasCorFacade;
    @EJB
    private AmbConsultaHasEspessuraFacade ambConsultaHasEspessuraFacade;  
    @EJB
    private AmbConsultaHasImpressoesGeraisFacade ambConsultaHasImpressoesGeraisFacade;
    @EJB
    private AmbConsultaHasTexturaFacade ambConsultaHasTexturaFacade;
    @EJB
    private AmbConsultaHasTurgorPeleFacade ambConsultaHasTurgorPeleFacade;
    @EJB
    private AmbConsultaHasTurgorTecidoFacade ambConsultaHasTurgorTecidoFacade;
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    @EJB
    private AmbCorFacade ambCorFacade;
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;
    @EJB
    private AmbDiagnosticoHipoteseHasDoencaFacade ambDiagnosticoHipoteseHasDoencaFacade;
    @EJB
    private AmbEspessuraFacade ambEspessuraFacade;
    @EJB
    private AmbEstadoFacade ambEstadoFacade;
    @EJB
    private AmbEstadoHidratacaoFacade ambEstadoHidratacaoFacade;
    @EJB
    private AmbImpressoesGeraisFacade ambImpressoesGeraisFacade;
    @EJB
    private AmbObservacoesMedicasFacade ambObservacoesMedicasFacade;
    @EJB
    private AmbSinalFacade ambSinalFacade;
    @EJB
    private AmbTexturaFacade ambTexturaFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private AmbTriagemHasSinalFacade ambTriagemHasSinalFacade;
    @EJB
    private AmbTurgorFacade ambTurgorFacade;
    @EJB
    private FarmProdutoFacade farmProdutoFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;
    @EJB
    private GrlTamanhoFacade grlTamanhoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    
    
    private AdmsAgendamento admsAgendamento;
    private AdmsEstadoPagamento admsEstadoPagamento;
    private AdmsServicoEfetuado admsServicoEfetuado;
    private AdmsPaciente admsPacienteConsulta, admsPacienteReconsulta;
    private AdmsTipoSolicitacaoServico admsTipoSolicitacaoServico;
    private AmbAderencia ambAderencia;
    private AmbCidSubcategorias ambCidSubcategorias;
    private AmbClassificacaoDor ambClassificacaoDor;
    private AmbColoracao ambColoracao;
    private AmbConfirmacao ambConfirmacao; 
    private AmbConsulta ambConsulta
                      , acAux;
    private AmbConsultaHasColoracao ambConsultaHasColoracao;
    private AmbConsultaHasCor ambConsultaHasCor;
    private AmbConsultaHasEspessura ambConsultaHasEspessura;
    private AmbConsultaHasImpressoesGerais ambConsultaHasImpressoesGerais;
    private AmbConsultaHasTextura ambConsultaHasTextura;
    private AmbConsultaHasTurgorPele ambConsultaHasTurgorPele;
    private AmbConsultaHasTurgorTecido ambConsultaHasTurgorTecido;
    private AmbConsultorioAtendimento ambConsultorioAtendimento;
    private AmbCor ambCor;
    private AmbDiagnostico ambDiagnostico;
    private AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca;
    private AmbDiagnosticoHipotese ambDiagnosticoHipotese;
    private AmbDiagnosticoHipoteseHasDoenca ambDiagnosticoHipoteseHasDoenca;
    private AmbEspessura ambEspessura;
    private AmbEstado ambEstado;
    private AmbEstadoHidratacao ambEstadoHidratacao;
    private AmbImpressoesGerais ambImpressoesGerais;
    private AmbObservacoesMedicas ambObservacoesMedicas;
    private AmbTextura ambTextura;
    private AmbTriagem ambTriagem;
    private AmbTriagemHasSinal ambTriagemHasSinal;
    private AmbTurgor ambTurgor;
    private Date dataInicio
               , dataFim;
    private FarmProduto farmProduto;
    private GrlCentroHospitalar grlCentroHospitalar;
    private GrlEspecialidade grlEspecialidade;
    private GrlTamanho grlTamanho;
    private RhFuncionario rhFuncionario;
    
    private boolean aderencia
                  , classe
                  , dor
                  , gravou = false
                  , localizacao
                  , ma
                  , presencaDePregas
                  , selecione
                  , tamanho
                  , turgor;
    
    private int codigoAderencia
              , codigoClassificacaoDor
//              , codigoConfirmacao
//              , codigoEspecialidade
              , codigoEstadoHidratacao
              , codigoObservacoesMedicas
              , codigoTamanho
              , fkIdViaAdministracao
              , fkIdFormaFarmaceutica
              , quantidade;
    
    private long codigoPaciente
               , codigoTriagem;
    
    private String dosagem
                 , historiaClinica
                 , unidadeMedida;
    
//    private int[]    listaImpressoes
//                   , listaMucosasColoracao
//                   , listaCorPele
//                   , listaTexturaPele
//                   , listaTurgorPele
//                   , listaEspessuraTecido
//                   , listaTurgorTecido;
    
//    private String[] listaDoencas;

    /**
     * Creates a new instance of AmbConsultaBean
     */
    public AmbConsultaCriarBean()
    {
        inicializaBooleanos();
    }
    
    private void inicializaBooleanos()
    {
        aderencia = true;
        classe = true;
        dor = true;
        localizacao = true;
        ma =true ;
        presencaDePregas = true;
        selecione = true;
        tamanho = true;
        turgor = true;
    }
    
    public static AmbConsultaCriarBean getInstanciaBean()
    {
        return (AmbConsultaCriarBean) GeradorCodigo.getInstanciaBean("ambConsultaCriarBean");
    }
    
    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }
    
    
    
    public AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        GeradorCodigo g = new GeradorCodigo();
        return (AmbTriagemCriarBean) g.getInstanciaBean("ambTriagemCriarBean");
    }  
    
    public static AdmsPaciente getInstanciaAdmsPaciente()
    {
        AdmsPaciente admsPaciente = new AdmsPaciente();
        
        admsPaciente.setFkIdEnderecoTemporario(new GrlEndereco());
        admsPaciente.setFkIdPessoa(new GrlPessoa());
        
        return admsPaciente;
    }

    public static AdmsServicoEfetuado getInstanciaAdmsServicoEfetuado()
    {
        AdmsServicoEfetuado admsServicoEfetuado = new AdmsServicoEfetuado();
        
        admsServicoEfetuado.setFkIdServicoSolicitado(new AdmsServicoSolicitado());
        
        return admsServicoEfetuado;
    }
    
    public static GrlCentroHospitalar getInstanciaGrlCentroHospitalar()
    {
        GrlCentroHospitalar grlCentroHospitalar = new GrlCentroHospitalar();
        
        grlCentroHospitalar.setFkIdInstituicao(new GrlInstituicao());
        
        return grlCentroHospitalar;
    }
    
    public static AmbCidSubcategorias getInstanciaAmbCidSubcategorias()
    {
        AmbCidSubcategorias ambCidSubcategorias = new AmbCidSubcategorias();
        
        ambCidSubcategorias.setFkIdCategorias(new AmbCidCategorias());
        
        return ambCidSubcategorias;
    }
    
    public static AmbConsulta getInstanciaAmbConsulta()
    {
        AmbConsulta ambConsulta = new AmbConsulta();
        
        ambConsulta.setFkIdEstado(new AmbEstado());
        ambConsulta.setFkIdEspecialidade(getInstanciaGrlEspecialidade());
        ambConsulta.setFkIdFuncionario(getInstanciaRhFuncionario());
        ambConsulta.setFkIdObservacoesMedicas(new AmbObservacoesMedicas());
        ambConsulta.setFkIdConsultorioAtendimento(getInstanciaAmbConsultorioAtendimento());
        
        return ambConsulta;
    }
    
    public static AmbConsultaHasColoracao getInstanciaAmbConsultaHasColoracao()
    {
        AmbConsultaHasColoracao ambConsultaHasColoracao = new AmbConsultaHasColoracao();
        
        ambConsultaHasColoracao.setFkIdColoracao(new AmbColoracao());
        ambConsultaHasColoracao.setFkIdConsulta(getInstanciaAmbConsulta());
        
        return ambConsultaHasColoracao;
    }
    
    public static AmbConsultaHasCor getInstanciaAmbConsultaHasCor()
    {
        AmbConsultaHasCor ambConsultaHasCor = new AmbConsultaHasCor();
        
        ambConsultaHasCor.setFkIdCor(new AmbCor());
        ambConsultaHasCor.setFkIdConsulta(getInstanciaAmbConsulta());
        
        return ambConsultaHasCor;
    }
    
    public static AmbConsultaHasEspessura getInstanciaAmbConsultaHasEspessura()
    {
        AmbConsultaHasEspessura ambConsultaHasEspessura = new AmbConsultaHasEspessura();
        
        ambConsultaHasEspessura.setFkIdEspessura(new AmbEspessura());
        ambConsultaHasEspessura.setFkIdConsulta(getInstanciaAmbConsulta());
        
        return ambConsultaHasEspessura;
    }

    public static AmbConsultaHasImpressoesGerais getInstanciaAmbConsultaHasImpressoesGerais()
    {
        AmbConsultaHasImpressoesGerais ambConsultaHasImpressoesGerais = new AmbConsultaHasImpressoesGerais();
        
        ambConsultaHasImpressoesGerais.setFkIdConsulta(getInstanciaAmbConsulta());
        ambConsultaHasImpressoesGerais.setFkIdImpressoesGerais(new AmbImpressoesGerais());
        
        return ambConsultaHasImpressoesGerais;
    }

    public static AmbConsultaHasTextura getInstanciaAmbConsultaHasTextura()
    {
        AmbConsultaHasTextura ambConsultaHasTextura = new AmbConsultaHasTextura();
        
        ambConsultaHasTextura.setFkIdConsulta(getInstanciaAmbConsulta());
        ambConsultaHasTextura.setFkIdTextura(new AmbTextura());
        
        return ambConsultaHasTextura;
    }
    
    public static AmbConsultaHasTurgorPele getInstanciaAmbConsultaHasTurgorPele()
    {
        AmbConsultaHasTurgorPele ambConsultaHasTurgorPele = new AmbConsultaHasTurgorPele();
        
        ambConsultaHasTurgorPele.setFkIdConsulta(getInstanciaAmbConsulta());
        ambConsultaHasTurgorPele.setFkIdTurgor(new AmbTurgor());
        
        return ambConsultaHasTurgorPele;
    }
    
    public static AmbConsultaHasTurgorTecido getInstanciaAmbConsultaHasTurgorTecido()
    {
        AmbConsultaHasTurgorTecido ambConsultaHasTurgorTecido = new AmbConsultaHasTurgorTecido();
        
        ambConsultaHasTurgorTecido.setFkIdConsulta(getInstanciaAmbConsulta());
        ambConsultaHasTurgorTecido.setFkIdTurgor(new AmbTurgor());
        
        return ambConsultaHasTurgorTecido;
    }
    
    public static AmbConsultorioAtendimento getInstanciaAmbConsultorioAtendimento()
    {
        AmbConsultorioAtendimento ambConsultorioAtendimento = new AmbConsultorioAtendimento();
        
        ambConsultorioAtendimento.setFkIdFuncionario(getInstanciaRhFuncionario());
        ambConsultorioAtendimento.setFkIdMedicoHasEscala(getInstanciaSupiMedicoHasEscala());
        ambConsultorioAtendimento.setFkIdTriagem(getInstanciaAmbTriagem());
        
        return ambConsultorioAtendimento;
    }
    
    public static AmbDiagnosticoHipotese getInstanciaAmbDiagnosticoHipotese()
    {
        AmbDiagnosticoHipotese ambDiagnosticoHipotese = new AmbDiagnosticoHipotese();
        
        ambDiagnosticoHipotese.setFkIdConsulta(getInstanciaAmbConsulta());
        
        return ambDiagnosticoHipotese;
    }
    
    public static AmbDiagnosticoHipoteseHasDoenca getInstanciaAmbDiagnosticoHipoteseHasDoenca()
    {
        AmbDiagnosticoHipoteseHasDoenca ambDiagnosticoHipoteseAsDoenca = new AmbDiagnosticoHipoteseHasDoenca();
        
        ambDiagnosticoHipoteseAsDoenca.setFkIdDiagnosticoHipotese(getInstanciaAmbDiagnosticoHipotese());
        ambDiagnosticoHipoteseAsDoenca.setFkIdSubcategorias(getInstanciaAmbCidSubcategorias());
        
        return ambDiagnosticoHipoteseAsDoenca;
    }
    
    public static FarmProduto getInstanciaFarmProduto()
    {
        FarmProduto farmProduto = new FarmProduto();
        
        farmProduto.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
        farmProduto.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica());
        farmProduto.setFkIdFuncionarioCadastrou(getInstanciaRhFuncionario());
        farmProduto.setFkIdTipoProduto(new FarmTipoProduto());
        farmProduto.setFkIdUnidadeMedida(new FarmUnidadeMedida());
        farmProduto.setFkIdViaAdministracao(new FarmViaAdministracao());
        
        return farmProduto;
    }
    
    public static GrlEspecialidade getInstanciaGrlEspecialidade()
    {
        GrlEspecialidade grlEspecialidade = new GrlEspecialidade();
        
        grlEspecialidade.setFkIdProfissao(new RhProfissao());
        
        return grlEspecialidade;
    }

    public AdmsAgendamento getAdmsAgendamento()
    {
        if (admsAgendamento == null)
        {
            admsAgendamento = getInstanciaAdmsAgendamento();
        }
        return admsAgendamento;
    }

    public void setAdmsAgendamento(AdmsAgendamento admsAgendamento)
    {
        this.admsAgendamento = admsAgendamento;
    }

    public AdmsEstadoPagamento getAdmsEstadoPagamento()
    {
        if (admsEstadoPagamento == null)
        {
            admsEstadoPagamento = new AdmsEstadoPagamento();
        }
        return admsEstadoPagamento;
    }

    public void setAdmsEstadoPagamento(AdmsEstadoPagamento admsEstadoPagamento)
    {
        this.admsEstadoPagamento = admsEstadoPagamento;
    }

    public AdmsPaciente getAdmsPacienteConsulta()
    {
        if (admsPacienteConsulta == null)
        {
            admsPacienteConsulta = getInstanciaAdmsPaciente();
        }
        return admsPacienteConsulta;
    }

    public void setAdmsPacienteConsulta(AdmsPaciente admsPacienteConsulta)
    {
        this.admsPacienteConsulta = admsPacienteConsulta;
    }

    public AdmsPaciente getAdmsPacienteReconsulta()
    {
        if (admsPacienteReconsulta == null)
        {
            admsPacienteReconsulta = getInstanciaAdmsPaciente();
        }
        return admsPacienteReconsulta;
    }

    public void setAdmsPacienteReconsulta(AdmsPaciente admsPacienteReconsulta)
    {
        this.admsPacienteReconsulta = admsPacienteReconsulta;
    }

    public AdmsServicoEfetuado getAdmsServicoEfetuado()
    {
        if (admsServicoEfetuado == null)
        {
            admsServicoEfetuado = getInstanciaAdmsServicoEfetuado();
        }
        return admsServicoEfetuado;
    }

    public void setAdmsServicoEfetuado(AdmsServicoEfetuado admsServicoEfetuado)
    {
        this.admsServicoEfetuado = admsServicoEfetuado;
    }
    
    public AdmsTipoSolicitacaoServico getAdmsTipoSolicitacaoServico()
    {
        if (admsTipoSolicitacaoServico == null)
        {
            admsTipoSolicitacaoServico = new AdmsTipoSolicitacaoServico();
        }
        return admsTipoSolicitacaoServico;
    }

    public void setAdmsTipoSolicitacaoServico(AdmsTipoSolicitacaoServico admsTipoSolicitacaoServico)
    {
        this.admsTipoSolicitacaoServico = admsTipoSolicitacaoServico;
    }

    public AmbAderencia getAmbAderencia()
    {
        if (ambAderencia == null)
        {
            ambAderencia = new AmbAderencia();
        }
        return ambAderencia;
    }

    public void setAmbAderencia(AmbAderencia ambAderencia)
    {
        this.ambAderencia = ambAderencia;
    }

    public AmbCidSubcategorias getAmbCidSubcategorias()
    {
        if (ambCidSubcategorias == null)
        {
            ambCidSubcategorias = getInstanciaAmbCidSubcategorias();
        }
        return ambCidSubcategorias;
    }

    public void setAmbCidSubcategorias(AmbCidSubcategorias ambCidSubcategorias)
    {
        this.ambCidSubcategorias = ambCidSubcategorias;
    }

    public AmbClassificacaoDor getAmbClassificacaoDor()
    {
        if (ambClassificacaoDor == null)
        {
            ambClassificacaoDor = new AmbClassificacaoDor();
        }
        return ambClassificacaoDor;
    }

    public void setAmbClassificacaoDor(AmbClassificacaoDor ambClassificacaoDor)
    {
        this.ambClassificacaoDor = ambClassificacaoDor;
    }

    public AmbColoracao getAmbColoracao()
    {
        if (ambColoracao == null)
        {
            ambColoracao = new AmbColoracao();
        }
        return ambColoracao;
    }

    public void setAmbColoracao(AmbColoracao ambColoracao)
    {
        this.ambColoracao = ambColoracao;
    }

    public AmbConfirmacao getAmbConfirmacao()
    {
        if (ambConfirmacao == null)
        {
            ambConfirmacao = new AmbConfirmacao();
        }        
        return ambConfirmacao;
    }

    public void setAmbConfirmacao(AmbConfirmacao ambConfirmacao)
    {
        this.ambConfirmacao = ambConfirmacao;
    }

    public AmbConsulta getAmbConsulta()
    {
        if (ambConsulta == null)
        {
            ambConsulta = getInstanciaAmbConsulta();
        }
        return ambConsulta;
    }

    public void setAmbConsulta(AmbConsulta ambConsulta)
    {
        this.ambConsulta = ambConsulta;
    }

    public AmbConsulta getACAux()
    {
        if (acAux == null)
        {
            acAux = getInstanciaAmbConsulta();
        }
        return acAux;
    }

    public void setACAux(AmbConsulta ambConsulta)
    {
        this.acAux = ambConsulta;
    }

    public AmbConsultaHasColoracao getAmbConsultaHasColoracao()
    {
        if (ambConsultaHasColoracao == null)
        {
            ambConsultaHasColoracao = getInstanciaAmbConsultaHasColoracao();
        }
        return ambConsultaHasColoracao;
    }

    public void setAmbConsultaHasColoracao(AmbConsultaHasColoracao ambConsultaHasColoracao)
    {
        this.ambConsultaHasColoracao = ambConsultaHasColoracao;
    }

    public AmbConsultaHasCor getAmbConsultaHasCor()
    {
        if (ambConsultaHasCor == null)
        {
            ambConsultaHasCor = getInstanciaAmbConsultaHasCor();
        }
        return ambConsultaHasCor;
    }

    public void setAmbConsultaHasCor(AmbConsultaHasCor ambConsultaHasCor)
    {
        this.ambConsultaHasCor = ambConsultaHasCor;
    }

    public AmbConsultaHasEspessura getAmbConsultaHasEspessura()
    {
        if (ambConsultaHasEspessura == null)
        {
            ambConsultaHasEspessura = getInstanciaAmbConsultaHasEspessura();
        }
        return ambConsultaHasEspessura;
    }

    public void setAmbConsultaHasEspessura(AmbConsultaHasEspessura ambConsultaHasEspessura)
    {
        this.ambConsultaHasEspessura = ambConsultaHasEspessura;
    }

    public AmbConsultaHasImpressoesGerais getAmbConsultaHasImpressoesGerais()
    {
        if (ambConsultaHasImpressoesGerais == null)
        {
            ambConsultaHasImpressoesGerais = getInstanciaAmbConsultaHasImpressoesGerais();
        }
        return ambConsultaHasImpressoesGerais;
    }

    public void setAmbConsultaHasImpressoesGerais(AmbConsultaHasImpressoesGerais ambConsultaHasImpressoesGerais)
    {
        this.ambConsultaHasImpressoesGerais = ambConsultaHasImpressoesGerais;
    }

    public AmbConsultaHasTextura getAmbConsultaHasTextura()
    {
        if (ambConsultaHasTextura == null)
        {
            ambConsultaHasTextura = getInstanciaAmbConsultaHasTextura();
        }        
        return ambConsultaHasTextura;
    }

    public void setAmbConsultaHasTextura(AmbConsultaHasTextura ambConsultaHasTextura)
    {
        this.ambConsultaHasTextura = ambConsultaHasTextura;
    }

    public AmbConsultaHasTurgorPele getAmbConsultaHasTurgorPele()
    {
        if (ambConsultaHasTurgorPele == null)
        {
            ambConsultaHasTurgorPele = getInstanciaAmbConsultaHasTurgorPele();
        } 
        return ambConsultaHasTurgorPele;
    }

    public void setAmbConsultaHasTurgorPele(AmbConsultaHasTurgorPele ambConsultaHasTurgorPele)
    {
        this.ambConsultaHasTurgorPele = ambConsultaHasTurgorPele;
    }

    public AmbConsultaHasTurgorTecido getAmbConsultaHasTurgorTecido()
    {
        if (ambConsultaHasTurgorTecido == null)
        {
            ambConsultaHasTurgorTecido = getInstanciaAmbConsultaHasTurgorTecido();
        } 
        return ambConsultaHasTurgorTecido;
    }

    public void setAmbConsultaHasTurgorTecido(AmbConsultaHasTurgorTecido ambConsultaHasTurgorTecido)
    {
        this.ambConsultaHasTurgorTecido = ambConsultaHasTurgorTecido;
    }

    public AmbConsultorioAtendimento getAmbConsultorioAtendimento()
    {
        if (ambConsultorioAtendimento == null)
        {
            ambConsultorioAtendimento = getInstanciaAmbConsultorioAtendimento();
        }
        return ambConsultorioAtendimento;
    }

    public void setAmbConsultorioAtendimento(AmbConsultorioAtendimento ambConsultorioAtendimento)
    {
        this.ambConsultorioAtendimento = ambConsultorioAtendimento;
    }
    
    public AmbCor getAmbCor()
    {
        if (ambCor == null)
        {
            ambCor = new AmbCor();
        }
        return ambCor;
    }

    public void setAmbCor(AmbCor ambCor)
    {
        this.ambCor = ambCor;
    }

    public AmbDiagnostico getAmbDiagnostico()
    {
        if (ambDiagnostico == null)
        {
            ambDiagnostico = getInstanciaAmbDiagnostico();
        }
        return ambDiagnostico;
    }

    public void setAmbDiagnostico(AmbDiagnostico ambDiagnostico)
    {
        this.ambDiagnostico = ambDiagnostico;
    }

    public AmbDiagnosticoHasDoenca getAmbDiagnosticoHasDoenca()
    {
        if (ambDiagnosticoHasDoenca == null)
        {
            ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
        }
        return ambDiagnosticoHasDoenca;
    }

    public void setAmbDiagnosticoHasDoenca(AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca)
    {
        this.ambDiagnosticoHasDoenca = ambDiagnosticoHasDoenca;
    }

    public AmbDiagnosticoHipotese getAmbDiagnosticoHipotese()
    {
        if (ambDiagnosticoHipotese == null)
        {
            ambDiagnosticoHipotese = getInstanciaAmbDiagnosticoHipotese();
        }
        return ambDiagnosticoHipotese;
    }

    public void setAmbDiagnosticoHipotese(AmbDiagnosticoHipotese ambDiagnosticoHipotese)
    {
        this.ambDiagnosticoHipotese = ambDiagnosticoHipotese;
    }

    public AmbDiagnosticoHipoteseHasDoenca getAmbDiagnosticoHipoteseHasDoenca()
    {
        if (ambDiagnosticoHipoteseHasDoenca == null)
        {
            ambDiagnosticoHipoteseHasDoenca = getInstanciaAmbDiagnosticoHipoteseHasDoenca();
        }
        return ambDiagnosticoHipoteseHasDoenca;
    }

    public void setAmbDiagnosticoHipoteseHasDoenca(AmbDiagnosticoHipoteseHasDoenca ambDiagnosticoHipoteseHasDoenca)
    {
        this.ambDiagnosticoHipoteseHasDoenca = ambDiagnosticoHipoteseHasDoenca;
    }

    public AmbEspessura getAmbEspessura()
    {
        if (ambEspessura == null)
        {
            ambEspessura = new AmbEspessura();
        }
        return ambEspessura;
    }

    public void setAmbEspessura(AmbEspessura ambEspessura)
    {
        this.ambEspessura = ambEspessura;
    }

    public AmbEstado getAmbEstado()
    {
        if (ambEstado == null)
        {
            ambEstado = new AmbEstado();
        }
        return ambEstado;
    }

    public void setAmbEstado(AmbEstado ambEstado)
    {
        this.ambEstado = ambEstado;
    }

    public AmbEstadoHidratacao getAmbEstadoHidratacao()
    {
        if (ambEstadoHidratacao == null)
        {
            ambEstadoHidratacao = new AmbEstadoHidratacao();
        }
        return ambEstadoHidratacao;
    }

    public void setAmbEstadoHidratacao(AmbEstadoHidratacao ambEstadoHidratacao)
    {
        this.ambEstadoHidratacao = ambEstadoHidratacao;
    }

    public AmbImpressoesGerais getAmbImpressoesGerais()
    {
        if (ambImpressoesGerais == null)
        {
            ambImpressoesGerais = new AmbImpressoesGerais();
        }
        return ambImpressoesGerais;
    }

    public void setAmbImpressoesGerais(AmbImpressoesGerais ambImpressoesGerais)
    {
        this.ambImpressoesGerais = ambImpressoesGerais;
    }

    public AmbObservacoesMedicas getAmbObservacoesMedicas()
    {
        if (ambObservacoesMedicas == null)
        {
            ambObservacoesMedicas = new AmbObservacoesMedicas();
        }
        return ambObservacoesMedicas;
    }

    public void setAmbObservacoesMedicas(AmbObservacoesMedicas ambObservacoesMedicas)
    {
        this.ambObservacoesMedicas = ambObservacoesMedicas;
    }

    public AmbTextura getAmbTextura()
    {
        if (ambTextura == null)
        {
            ambTextura = new AmbTextura();
        }
        return ambTextura;
    }

    public void setAmbTextura(AmbTextura ambTextura)
    {
        this.ambTextura = ambTextura;
    }
    
    public AmbTriagem getAmbTriagem()
    {
        if (ambTriagem == null)
        {
            ambTriagem = getInstanciaAmbTriagem();
        }
        return ambTriagem;
    }

    public void setAmbTriagem(AmbTriagem ambTriagem)
    {
        this.ambTriagem = ambTriagem;
    }

    public AmbTriagemHasSinal getAmbTriagemHasSinal()
    {
        if (ambTriagemHasSinal == null)
        {
            ambTriagemHasSinal = getInstanciaAmbTriagemHasSinal();
        }
        return ambTriagemHasSinal;
    }

    public void setAmbTriagemHasSinal(AmbTriagemHasSinal ambTriagemHasSinal)
    {
        this.ambTriagemHasSinal = ambTriagemHasSinal;
    }
    
    public AmbTurgor getAmbTurgor()
    {
        if (ambTurgor == null)
        {
            ambTurgor = new AmbTurgor();
        }        
        return ambTurgor;
    }

    public void setAmbTurgor(AmbTurgor ambTurgor)
    {
        this.ambTurgor = ambTurgor;
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
    
    public FarmProduto getFarmProduto()
    {
        if (farmProduto == null)
        {
            farmProduto = getInstanciaFarmProduto();
        }
        return farmProduto;
    }

    public void setFarmProduto(FarmProduto farmProduto)
    {
        this.farmProduto = farmProduto;
    }

    public GrlCentroHospitalar getGrlCentroHospitalar()
    {
        if (grlCentroHospitalar == null)
        {
            grlCentroHospitalar = getInstanciaGrlCentroHospitalar();
        }
        return grlCentroHospitalar;
    }

    public void setGrlCentroHospitalar(GrlCentroHospitalar grlCentroHospitalar)
    {
        this.grlCentroHospitalar = grlCentroHospitalar;
    }

    public GrlEspecialidade getGrlEspecialidade()
    {
        if (grlEspecialidade == null)
        {
            grlEspecialidade = getInstanciaGrlEspecialidade();
        }
        return grlEspecialidade;
    }

    public void setGrlEspecialidade(GrlEspecialidade grlEspecialidade)
    {
        this.grlEspecialidade = grlEspecialidade;
    }

    public GrlTamanho getGrlTamanho()
    {
        if (grlTamanho == null)
        {
            grlTamanho = new GrlTamanho();
        }
        return grlTamanho;
    }

    public void setGrlTamanho(GrlTamanho grlTamanho)
    {
        this.grlTamanho = grlTamanho;
    }

    public RhFuncionario getRhFuncionario()
    {
        if (rhFuncionario == null)
        {
            rhFuncionario = getInstanciaRhFuncionario();
        }
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }

    public boolean isAderencia()
    {
        return aderencia;
    }

    public void setAderencia(boolean aderencia)
    {
        this.aderencia = aderencia;
    }

    public boolean isClasse()
    {
        return classe;
    }

    public void setClasse(boolean classe)
    {
        this.classe = classe;
    }

    public boolean isDor()
    {
        return dor;
    }

    public void setDor(boolean dor)
    {
        this.dor = dor;
    }

    public boolean isGravou()
    {
        return gravou;
    }

    public void setGravou(boolean gravou)
    {
        this.gravou = gravou;
    }

    public boolean isLocalizacao()
    {
        return localizacao;
    }

    public void setLocalizacao(boolean localizacao)
    {
        this.localizacao = localizacao;
    }

    public boolean isMa()
    {
        return ma;
    }

    public void setMa(boolean ma)
    {
        this.ma = ma;
    }

    public boolean isPresencaDePregas()
    {
        return presencaDePregas;
    }

    public void setPresencaDePregas(boolean presencaDePregas)
    {
        this.presencaDePregas = presencaDePregas;
    }
    
    public boolean isSelecione()
    {
        return selecione;
    }

    public void setSelecione(boolean selecione)
    {
        this.selecione = selecione;
    }

    public boolean isTamanho()
    {
        return tamanho;
    }
    
    public boolean isTurgor()
    {
        return turgor;
    }

    public void setTurgor(boolean turgor)
    {
        this.turgor = turgor;
    }
    
    public void setTamanho(boolean tamanho)
    {
        this.tamanho = tamanho;
    }

    public FarmProdutoFacade getFarmProdutoFacade()
    {
        return farmProdutoFacade;
    }

    public void setFarmProdutoFacade(FarmProdutoFacade farmProdutoFacade)
    {
        this.farmProdutoFacade = farmProdutoFacade;
    }

    public int getCodigoAderencia()
    {
        return codigoAderencia;
    }

    public void setCodigoAderencia(int codigoAderencia)
    {
        this.codigoAderencia = codigoAderencia;
    }

    public int getCodigoClassificacaoDor()
    {
        return codigoClassificacaoDor;
    }

    public void setCodigoClassificacaoDor(int codigoClassificacaoDor)
    {
        this.codigoClassificacaoDor = codigoClassificacaoDor;
    }

//    public int getCodigoConfirmacao()
//    {
//        return codigoConfirmacao;
//    }
//
//    public void setCodigoConfirmacao(int codigoConfirmacao)
//    {
//        this.codigoConfirmacao = codigoConfirmacao;
//    }

    public int getCodigoEstadoHidratacao()
    {
        return codigoEstadoHidratacao;
    }

    public void setCodigoEstadoHidratacao(int codigoEstadoHidratacao)
    {
        this.codigoEstadoHidratacao = codigoEstadoHidratacao;
    }

    public int getCodigoTamanho()
    {
        return codigoTamanho;
    }

    public void setCodigoTamanho(int codigoTamanho)
    {
        this.codigoTamanho = codigoTamanho;
    }

    public int getCodigoObservacoesMedicas()
    {
        return codigoObservacoesMedicas;
    }

    public void setCodigoObservacoesMedicas(int codigoObservacoesMedicas)
    {
        this.codigoObservacoesMedicas = codigoObservacoesMedicas;
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

    public int getQuantidade()
    {
        return quantidade;
    }

    public void setQuantidade(int quantidade)
    {
        this.quantidade = quantidade;
    }

//    public int getCodigoEspecialidade()
//    {
//        return codigoEspecialidade;
//    }
//
//    public void setCodigoEspecialidade(int codigoEspecialidade)
//    {
//        this.codigoEspecialidade = codigoEspecialidade;
//    }

    public long getCodigoPaciente()
    {
        return codigoPaciente;
    }

    public void setCodigoPaciente(long codigoPaciente)
    {
        this.codigoPaciente = codigoPaciente;
    }

    public long getCodigoTriagem()
    {
        return codigoTriagem;
    }

    public void setCodigoTriagem(long codigoTriagem)
    {
        this.codigoTriagem = codigoTriagem;
    }

    public String getDosagem()
    {
        return dosagem;
    }

    public void setDose(String dosagem)
    {
        this.dosagem = dosagem;
    }

    public String getHistoriaClinica()
    {
        return historiaClinica;
    }

    public void setHistoriaClinica(String historiaClinica)
    {
        this.historiaClinica = historiaClinica;
    }

    public String getUnidadeMedida()
    {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida)
    {
        this.unidadeMedida = unidadeMedida;
    }
   
    public String gravarConsulta() 
    {
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        }
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        } 
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() > 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        }        
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas > 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                }
            }
        }

        if (AmbConsultaListarBean.getInstanciaBean().getCodigoEncaminhamento() == 0)
            Mensagem.warnMsg("Tem de seleccionar um Paciente na lista. Não pode ser nulo!");        
        
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() > 0)
            {
                if (codigoObservacoesMedicas > 0)
                {
                    try
                    {
                        this.userTransaction.begin();

                        admsServicoEfetuado = getInstanciaAdmsServicoEfetuado();
                        ambConsultaHasColoracao = getInstanciaAmbConsultaHasColoracao();
                        ambConsultaHasCor = getInstanciaAmbConsultaHasCor();
                        ambConsultaHasEspessura = getInstanciaAmbConsultaHasEspessura();
                        ambConsultaHasImpressoesGerais = getInstanciaAmbConsultaHasImpressoesGerais();
                        ambConsultaHasTextura = getInstanciaAmbConsultaHasTextura();
                        ambConsultaHasTurgorPele = getInstanciaAmbConsultaHasTurgorPele();
                        ambConsultaHasTurgorTecido = getInstanciaAmbConsultaHasTurgorTecido();
                        ambDiagnosticoHipotese = getInstanciaAmbDiagnosticoHipotese();
                        ambDiagnosticoHipoteseHasDoenca = getInstanciaAmbDiagnosticoHipoteseHasDoenca();
                        ambDiagnostico = getInstanciaAmbDiagnostico();
                        ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
            
                        if (!gravou)
                        {
                            for (int i = 0; i < admsTipoSolicitacaoServicoFacade.findAll().size(); i++)
                            {
                                if (admsTipoSolicitacaoServicoFacade.findAll().get(i).getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_PRIMEIRA))
                                {
                                    admsTipoSolicitacaoServico = admsTipoSolicitacaoServicoFacade.findAll().get(i);
//System.err.print("Teste 0    : admsTipoSolicitacaoServico: " + admsTipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico());
                                    ambConsulta.setFkIdTipoSolicitacaoServico(admsTipoSolicitacaoServico);
//System.err.print("Teste 0.0  : ambConsulta: " + ambConsulta.getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico());
                                }
                            }
            

                            grlCentroHospitalar = grlCentroHospitalarFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar());
//System.err.print("Teste 0.1  : ambConsulta: " + grlCentroHospitalar.getFkIdInstituicao().getDescricao());
                            ambConsulta.setFkIdCentro(grlCentroHospitalar);
//System.err.print("Teste 0.2  : ambConsulta: " + ambConsulta.getFkIdCentro().getFkIdInstituicao().getDescricao());                        

                
                            ambEstado = ambEstadoFacade.findByDescricao(Defs.ESTADO_ESPERA);
//System.err.print("Teste 1    : ambEstado: " + ambEstado.getDescricao());
                            ambConsulta.setFkIdEstado(ambEstado);
//System.err.print("Teste 1.0  : ambEstado: " + ambEstado.getDescricao());
                            ambConfirmacao = ambConfirmacaoFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoConfirmacao());
//System.err.print("Teste 1.1  : ambConfirmacao: " + ambConfirmacao.getDescricao());                            
                            ambObservacoesMedicas = ambObservacoesMedicasFacade.find(codigoObservacoesMedicas);
//System.err.print("Teste 2    : ambObservacoesMedicas: " + ambObservacoesMedicas.getDescricao());                        
                            grlEspecialidade = grlEspecialidadeFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade());
//System.err.print("Teste 3    : grlEspecialidade: " + grlEspecialidade.getDescricao());
                            ambEstadoHidratacao = ambEstadoHidratacaoFacade.find(codigoEstadoHidratacao);
//System.err.print("Teste 4    : ambEstadoHidratacao: " + ambEstadoHidratacao.getDescricao());
                            grlTamanho = grlTamanhoFacade.find(codigoTamanho);
//System.err.print("Teste 5    : grlTamanho: " + grlTamanho.getDescricao());
                            ambClassificacaoDor = ambClassificacaoDorFacade.find(codigoClassificacaoDor);
//System.err.print("Teste 6    : ambClassificacaoDor: " + ambClassificacaoDor.getDescricao()); 
                            ambAderencia = ambAderenciaFacade.find(codigoAderencia);
//System.err.print("Teste 7    : ambAderencia: " + ambAderencia.getDescricao());
//System.err.print("Teste 7.1  : Cód. Enc.: " + getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento());
                            ambConsultorioAtendimento = ambConsultorioAtendimentoFacade.find(getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento());
//System.err.print("Teste 8    : ambTriagem: " + ambConsultorioAtendimento.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());


                            ambConsulta.setFkIdEspecialidade(grlEspecialidade);
//System.err.print("Teste 9    : ambConsulta: " + ambConsulta.getFkIdEspecialidade().getDescricao());
                            ambConsulta.setFkIdConfirmacao(ambConfirmacao);
//System.err.print("Teste 9.0  : ambConsulta: " + ambConsulta.getFkIdConfirmacao().getDescricao());                            
                            ambConsulta.setFkIdConsultorioAtendimento(ambConsultorioAtendimento);
//System.err.print("Teste 10   : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getPkIdAgendamento());
//System.err.print("Teste 10.1 : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());  
                            ambConsulta.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.err.print("Teste 11   : ambConsulta: " + ambConsulta.getFkIdFuncionario().getFkIdPessoa().getNome());
//System.err.print("Teste 12   : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdFuncionario().getFkIdPessoa().getNome());
                            ambConsulta.setDataHoraConsulta(new Date());
//System.err.print("Teste 13   : ambConsulta: " + ambConsulta.getDataHoraConsulta());
                            ambConsulta.setFkIdAderencia(ambAderencia);
//System.err.print("Teste 14   : ambConsulta: " + ambConsulta.getFkIdAderencia().getDescricao());
                            ambConsulta.setFkIdClassificacaoDor(ambClassificacaoDor);
//System.err.print("Teste 15   : ambConsulta: " + ambConsulta.getFkIdClassificacaoDor().getDescricao());
                            ambConsulta.setFkIdEstadoHidratacao(ambEstadoHidratacao);
//System.err.print("Teste 16   : ambConsulta: " + ambConsulta.getFkIdEstadoHidratacao().getDescricao());  
                            ambConsulta.setFkIdTamanho(grlTamanho);
//System.err.print("Teste 17   : ambConsulta: " + ambConsulta.getFkIdTamanho().getDescricao());
                            ambConsulta.setFkIdObservacoesMedicas(ambObservacoesMedicas);
//System.err.print("Teste 18   : ambConsulta: " + ambConsulta.getFkIdObservacoesMedicas().getDescricao());                
                
                            ambConsulta.setAmbConsultaHasColoracaoList(getInstanciaAmbConsultaListarBean().devolverColoracao());
                            ambConsulta.setAmbConsultaHasCorList(getInstanciaAmbConsultaListarBean().devolverCor());
                            ambConsulta.setAmbConsultaHasEspessuraList(getInstanciaAmbConsultaListarBean().devolverEspessura());
                            ambConsulta.setAmbConsultaHasImpressoesGeraisList(getInstanciaAmbConsultaListarBean().devolverImpressoesGerais());
                            ambConsulta.setAmbConsultaHasTexturaList(getInstanciaAmbConsultaListarBean().devolverTextura());
                            ambConsulta.setAmbConsultaHasTurgorPeleList(getInstanciaAmbConsultaListarBean().devolverTurgorPele());
                            ambConsulta.setAmbConsultaHasTurgorTecidoList(getInstanciaAmbConsultaListarBean().devolverTurgorTecido());

                            ambConsultaFacade.create(ambConsulta);

                            if (!ambConsulta.getAmbConsultaHasImpressoesGeraisList().isEmpty())
                            {
                                for (AmbConsultaHasImpressoesGerais aci : ambConsulta.getAmbConsultaHasImpressoesGeraisList())
                                {
                                    aci.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.0  : ambConsultaHasImpressoesGerais: " + aci.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasImpressoesGeraisFacade.create(aci);
                                }    
                            }
            
                            if (!ambConsulta.getAmbConsultaHasColoracaoList().isEmpty())
                            {
                                for (AmbConsultaHasColoracao acc : ambConsulta.getAmbConsultaHasColoracaoList())
                                {
                                    acc.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.1  : ambConsultaHasColoracao: " + acc.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasColoracaoFacade.create(acc);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasCorList().isEmpty())
                            {
                                for (AmbConsultaHasCor ac : ambConsulta.getAmbConsultaHasCorList())
                                {   
                                    ac.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.2  : ambConsultaHasCor: " + ac.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasCorFacade.create(ac);
                                }
                            }

                            if (!ambConsulta.getAmbConsultaHasTexturaList().isEmpty())
                            {
                                for (AmbConsultaHasTextura act : ambConsulta.getAmbConsultaHasTexturaList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.3  : ambConsultaHasTextura: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTexturaFacade.create(act);
                                } 
                            }

                            if (!ambConsulta.getAmbConsultaHasTurgorPeleList().isEmpty())
                            {
                                for (AmbConsultaHasTurgorPele act : ambConsulta.getAmbConsultaHasTurgorPeleList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.4  : AmbConsultaHasTurgorPele: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTurgorPeleFacade.create(act);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasEspessuraList().isEmpty())
                            {
                                for (AmbConsultaHasEspessura ace : ambConsulta.getAmbConsultaHasEspessuraList())
                                {
                                    ace.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.5  : ambConsultaHasEspessura: " + ace.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasEspessuraFacade.create(ace);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasTurgorTecidoList().isEmpty())
                            {
                                for (AmbConsultaHasTurgorTecido act : ambConsulta.getAmbConsultaHasTurgorTecidoList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.6  : AmbConsultaHasTurgorTecido: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTurgorTecidoFacade.create(act);
                                }
                            }

                            ambDiagnosticoHipotese.setFkIdConsulta(ambConsulta);
//System.err.print("Teste 33   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdConsulta().getPkIdConsulta());
//System.err.print("Teste 33.1 : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());           
                            ambDiagnosticoHipotese.setDataHoraHipoteseDiagnostico(new Date());
//System.err.print("Teste 33.2   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getDataHoraHipoteseDiagnostico()); 

                            ambDiagnosticoHipotese.setFkIdFuncionario(ambConsulta.getFkIdFuncionario());
//System.err.print("Teste 33.3   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdFuncionario().getFkIdPessoa().getNome()); 
                            ambDiagnosticoHipotese.setAmbDiagnosticoHipoteseHasDoencaList(getInstanciaAmbConsultaListarBean().devolverDoencas());
//System.err.print("\n\n");
//System.err.print("Teste 33.4   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList());

                            ambDiagnosticoHipoteseFacade.create(ambDiagnosticoHipotese);
                 
                            if (ambObservacoesMedicas.getDescricao().equals(Defs.SOLICITACAO_OUTROS_SERVICOS))
                            {
                                if (!ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList().isEmpty())
                                {
                                    for (AmbDiagnosticoHipoteseHasDoenca adhd : ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList())
                                    {
                                         adhd.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 34   : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese());

                                         ambDiagnosticoHipoteseHasDoencaFacade.create(adhd);
                                    }
                                }
                                else
                                {
                                    ambDiagnosticoHipoteseHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35   : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getOutros());
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 35.0 : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipotese.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());                                    
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
//System.err.print("Teste 35.1 : ambDiagnosticoHipoteseHasDoenca: " + ambCidSubcategorias.getNome());                                    
                                
                                    ambDiagnosticoHipoteseHasDoencaFacade.create(ambDiagnosticoHipoteseHasDoenca);
                                }
                            }
                            else
                            {
                                ambDiagnostico.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
                                ambDiagnostico.setDataHoraDiagnostico(new Date());

                                ambDiagnostico.setFkIdFuncionario(ambDiagnosticoHipotese.getFkIdFuncionario());
//System.err.print("Teste 35.2 : ambDiagnostico: " + ambDiagnostico.getFkIdFuncionario().getFkIdPessoa().getNome());                                
                                ambDiagnostico.setFkIdExameRealizado(null);
                                ambDiagnostico.setFkIdCentro(grlCentroHospitalar);
                                ambDiagnostico.setObservacoes(ambDiagnosticoHipotese.getFkIdConsulta().getObservacoes());
                                ambDiagnostico.setFkIdObservacoesMedicas(ambObservacoesMedicas);
//System.err.print("Teste 35.3 : ambDiagnostico: " + ambDiagnostico.getFkIdObservacoesMedicas().getDescricao());                                

                                ambDiagnosticoFacade.create(ambDiagnostico);
                                    //Garavar aqui o amb_diagnostico
                                if (!ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList().isEmpty())
                                {
                                    for (AmbDiagnosticoHipoteseHasDoenca adhd : ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList())
                                    {
                                         adhd.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 35.4  : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese());

                                         ambDiagnosticoHipoteseHasDoencaFacade.create(adhd);
                                        
                                         ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
//System.err.print("Teste 35.5 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdDiagnostico().getPkIdDiagnostico());                                         
                                         ambDiagnosticoHasDoenca.setFkIdSubcategorias(adhd.getFkIdSubcategorias());
//System.err.print("Teste 35.6 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdSubcategorias().getNome());                                
                                         
                                         ambDiagnosticoHasDoencaFacade.create(ambDiagnosticoHasDoenca);
                                    }
                                }
                                else
                                {
                                    ambDiagnosticoHipoteseHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35.7  : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getOutros());
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
                                
                                    ambDiagnosticoHipoteseHasDoencaFacade.create(ambDiagnosticoHipoteseHasDoenca);
                                    
                                    ambDiagnosticoHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35.8 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getOutros());
//                                    ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
                                    ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
//System.err.print("Teste 35.9 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdDiagnostico().getPkIdDiagnostico());                                    
                                    ambDiagnosticoHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
//System.err.print("Teste 36   : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdSubcategorias().getNome());                                    
                                
                                    ambDiagnosticoHasDoencaFacade.create(ambDiagnosticoHasDoenca);
                                }
                            }

                            for (AmbConsultorioAtendimento aca : ambConsultorioAtendimentoFacade.findAll())
                            {
                                if (aca.getPkIdConsultorioAtendimento().equals(getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento()))
                                {
                                    for (AmbTriagem at : ambTriagemFacade.findAll())
                                    {
                                        if (aca.getFkIdTriagem().getPkIdTriagem().equals(at.getPkIdTriagem()))
                                        {
                                            for (AmbEstado ae : ambEstadoFacade.findAll())
                                            {
                                                if (ae.getDescricao().equals(Defs.ESTADO_ATENDIDO))
                                                {
//System.err.print("Teste   : Cód. Enc.   : " + aca.getPkIdConsultorioAtendimento()); 
//System.err.print("Teste   : Cód. Triagem: " + at.getPkIdTriagem()); 
//System.err.print("Teste   : Cód. Estado : " + ae.getPkIdEstado()); 
                                                    aca.getFkIdTriagem().setFkIdEstado(new AmbEstado(ae.getPkIdEstado()));
                                                    ambConsulta.setFkIdEstado(aca.getFkIdTriagem().getFkIdEstado());
//System.err.print("Teste 37   : ambConsulta: " + ambConsulta.getFkIdEstado().getDescricao()); 
//System.err.print("Teste 38   : ambTriagem: " + at.getFkIdEstado().getDescricao()); 

                                                    ambTriagemFacade.edit(aca.getFkIdTriagem());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
            
                        admsServicoEfetuado.setFkIdServicoSolicitado(ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado());
//System.err.print("Teste 39   : admsServicoEfetuado: " + admsServicoEfetuado.getFkIdServicoSolicitado().getPkIdServicoSolicitado());
                        admsServicoEfetuado.setDescricaoTabelaBusca(Defs.DESCRICAO_TABELA_BUSCA);
//System.err.print("Teste 40   : admsServicoEfetuado: " + admsServicoEfetuado.getDescricaoTabelaBusca());           
                        admsServicoEfetuado.setCodigoTabelaBusca(BigInteger.valueOf(ambConsulta.getPkIdConsulta()));
//System.err.print("Teste 41   : admsServicoEfetuado: " + admsServicoEfetuado.getCodigoTabelaBusca());
                        admsServicoEfetuado.setDataEfetuada(new Date());
//System.err.print("Teste 42   : admsServicoEfetuado: " + admsServicoEfetuado.getDataEfetuada());

                        admsServicoEfetuadoFacade.create(admsServicoEfetuado);

                        this.userTransaction.commit();

                        gravou = true;
                        
                        }
            
                        Mensagem.sucessoMsg("Consulta gravada com sucesso!");
            
                        ambConsulta = getInstanciaAmbConsulta();
            
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        try
                        {
                            Mensagem.erroMsg("Operação não concluída. Tente novamente!");
                            this.userTransaction.rollback();
                        } catch (IllegalStateException | SecurityException | SystemException ex)
                        {
//                            Mensagem.erroMsg("Rollback: " + ex.toString());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        
        if (gravou == true)
            if (ambObservacoesMedicas.getDescricao().equals("Solicitação outros Serviços"))
                return solicitarServicos();

        return null;
    }
   
    public String gravarReconsulta() 
    {
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        }
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        } 
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() > 0)
            {
                if (codigoObservacoesMedicas == 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
                }
            }
        }        
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() == 0)
            {
                if (codigoObservacoesMedicas > 0)
                {
                    Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                    Mensagem.warnMsg("O campo 'Especialidade' é obrigatório!");
                }
            }
        }

        if (AmbConsultaListarBean.getInstanciaBean().getCodigoEncaminhamento() == 0)
            Mensagem.warnMsg("Tem de seleccionar um Paciente na lista. Não pode ser nulo!");        
        
        if (AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {
            if (AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade() > 0)
            {
                if (codigoObservacoesMedicas > 0)
                {
                    try
                    {
                        this.userTransaction.begin();

                        admsServicoEfetuado = getInstanciaAdmsServicoEfetuado();
                        ambConsultaHasColoracao = getInstanciaAmbConsultaHasColoracao();
                        ambConsultaHasCor = getInstanciaAmbConsultaHasCor();
                        ambConsultaHasEspessura = getInstanciaAmbConsultaHasEspessura();
                        ambConsultaHasImpressoesGerais = getInstanciaAmbConsultaHasImpressoesGerais();
                        ambConsultaHasTextura = getInstanciaAmbConsultaHasTextura();
                        ambConsultaHasTurgorPele = getInstanciaAmbConsultaHasTurgorPele();
                        ambConsultaHasTurgorTecido = getInstanciaAmbConsultaHasTurgorTecido();
                        ambDiagnosticoHipotese = getInstanciaAmbDiagnosticoHipotese();
                        ambDiagnosticoHipoteseHasDoenca = getInstanciaAmbDiagnosticoHipoteseHasDoenca();
                        ambDiagnostico = getInstanciaAmbDiagnostico();
                        ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
            
                        if (!gravou)
                        {
                            for (int i = 0; i < admsTipoSolicitacaoServicoFacade.findAll().size(); i++)
                            {
                                if (admsTipoSolicitacaoServicoFacade.findAll().get(i).getDescricaoTipoSolicitacaoServico().equals(Defs.TIPO_SOLICITACAO_RETORNO))
                                {
                                    admsTipoSolicitacaoServico = admsTipoSolicitacaoServicoFacade.findAll().get(i);
//System.err.print("Teste 0    : admsTipoSolicitacaoServico: " + admsTipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico());
                                    ambConsulta.setFkIdTipoSolicitacaoServico(admsTipoSolicitacaoServico);
//System.err.print("Teste 0.0  : ambConsulta: " + ambConsulta.getFkIdTipoSolicitacaoServico().getDescricaoTipoSolicitacaoServico());
                                }
                            }
            

                            grlCentroHospitalar = grlCentroHospitalarFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoCentroHospitalar());
//System.err.print("Teste 0.1  : ambConsulta: " + grlCentroHospitalar.getFkIdInstituicao().getDescricao());
                            ambConsulta.setFkIdCentro(grlCentroHospitalar);
//System.err.print("Teste 0.2  : ambConsulta: " + ambConsulta.getFkIdCentro().getFkIdInstituicao().getDescricao());                        

                
                            ambEstado = ambEstadoFacade.findByDescricao(Defs.ESTADO_ESPERA);
//System.err.print("Teste 1    : ambEstado: " + ambEstado.getDescricao());
                            ambConsulta.setFkIdEstado(ambEstado);
//System.err.print("Teste 1.0  : ambEstado: " + ambEstado.getDescricao());
                            ambConfirmacao = ambConfirmacaoFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoConfirmacao());
//System.err.print("Teste 1.1  : ambConfirmacao: " + ambConfirmacao.getDescricao());                            
                            ambObservacoesMedicas = ambObservacoesMedicasFacade.find(codigoObservacoesMedicas);
//System.err.print("Teste 2    : ambObservacoesMedicas: " + ambObservacoesMedicas.getDescricao());                        
                            grlEspecialidade = grlEspecialidadeFacade.find(AmbConsultaListarBean.getInstanciaBean().getCodigoEspecialidade());
//System.err.print("Teste 3    : grlEspecialidade: " + grlEspecialidade.getDescricao());
                            ambEstadoHidratacao = ambEstadoHidratacaoFacade.find(codigoEstadoHidratacao);
//System.err.print("Teste 4    : ambEstadoHidratacao: " + ambEstadoHidratacao.getDescricao());
                            grlTamanho = grlTamanhoFacade.find(codigoTamanho);
//System.err.print("Teste 5    : grlTamanho: " + grlTamanho.getDescricao());
                            ambClassificacaoDor = ambClassificacaoDorFacade.find(codigoClassificacaoDor);
//System.err.print("Teste 6    : ambClassificacaoDor: " + ambClassificacaoDor.getDescricao()); 
                            ambAderencia = ambAderenciaFacade.find(codigoAderencia);
//System.err.print("Teste 7    : ambAderencia: " + ambAderencia.getDescricao());
//System.err.print("Teste 7.1  : Cód. Enc.: " + getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento());
                            ambConsultorioAtendimento = ambConsultorioAtendimentoFacade.find(getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento());
//System.err.print("Teste 8    : ambTriagem: " + ambConsultorioAtendimento.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());


                            ambConsulta.setFkIdEspecialidade(grlEspecialidade);
//System.err.print("Teste 9    : ambConsulta: " + ambConsulta.getFkIdEspecialidade().getDescricao());
                            ambConsulta.setFkIdConfirmacao(ambConfirmacao);
//System.err.print("Teste 9.0  : ambConsulta: " + ambConsulta.getFkIdConfirmacao().getDescricao());                            
                            ambConsulta.setFkIdConsultorioAtendimento(ambConsultorioAtendimento);
//System.err.print("Teste 10   : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getPkIdAgendamento());
//System.err.print("Teste 10.1 : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());  
                            ambConsulta.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.err.print("Teste 11   : ambConsulta: " + ambConsulta.getFkIdFuncionario().getFkIdPessoa().getNome());
//System.err.print("Teste 12   : ambConsulta: " + ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdFuncionario().getFkIdPessoa().getNome());
                            ambConsulta.setDataHoraConsulta(new Date());
//System.err.print("Teste 13   : ambConsulta: " + ambConsulta.getDataHoraConsulta());
                            ambConsulta.setFkIdAderencia(ambAderencia);
//System.err.print("Teste 14   : ambConsulta: " + ambConsulta.getFkIdAderencia().getDescricao());
                            ambConsulta.setFkIdClassificacaoDor(ambClassificacaoDor);
//System.err.print("Teste 15   : ambConsulta: " + ambConsulta.getFkIdClassificacaoDor().getDescricao());
                            ambConsulta.setFkIdEstadoHidratacao(ambEstadoHidratacao);
//System.err.print("Teste 16   : ambConsulta: " + ambConsulta.getFkIdEstadoHidratacao().getDescricao());  
                            ambConsulta.setFkIdTamanho(grlTamanho);
//System.err.print("Teste 17   : ambConsulta: " + ambConsulta.getFkIdTamanho().getDescricao());
                            ambConsulta.setFkIdObservacoesMedicas(ambObservacoesMedicas);
//System.err.print("Teste 18   : ambConsulta: " + ambConsulta.getFkIdObservacoesMedicas().getDescricao());                
                
                            ambConsulta.setAmbConsultaHasColoracaoList(getInstanciaAmbConsultaListarBean().devolverColoracao());
                            ambConsulta.setAmbConsultaHasCorList(getInstanciaAmbConsultaListarBean().devolverCor());
                            ambConsulta.setAmbConsultaHasEspessuraList(getInstanciaAmbConsultaListarBean().devolverEspessura());
                            ambConsulta.setAmbConsultaHasImpressoesGeraisList(getInstanciaAmbConsultaListarBean().devolverImpressoesGerais());
                            ambConsulta.setAmbConsultaHasTexturaList(getInstanciaAmbConsultaListarBean().devolverTextura());
                            ambConsulta.setAmbConsultaHasTurgorPeleList(getInstanciaAmbConsultaListarBean().devolverTurgorPele());
                            ambConsulta.setAmbConsultaHasTurgorTecidoList(getInstanciaAmbConsultaListarBean().devolverTurgorTecido());

                            ambConsultaFacade.create(ambConsulta);

                            if (!ambConsulta.getAmbConsultaHasImpressoesGeraisList().isEmpty())
                            {
                                for (AmbConsultaHasImpressoesGerais aci : ambConsulta.getAmbConsultaHasImpressoesGeraisList())
                                {
                                    aci.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.0  : ambConsultaHasImpressoesGerais: " + aci.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasImpressoesGeraisFacade.create(aci);
                                }    
                            }
            
                            if (!ambConsulta.getAmbConsultaHasColoracaoList().isEmpty())
                            {
                                for (AmbConsultaHasColoracao acc : ambConsulta.getAmbConsultaHasColoracaoList())
                                {
                                    acc.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.1  : ambConsultaHasColoracao: " + acc.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasColoracaoFacade.create(acc);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasCorList().isEmpty())
                            {
                                for (AmbConsultaHasCor ac : ambConsulta.getAmbConsultaHasCorList())
                                {   
                                    ac.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.2  : ambConsultaHasCor: " + ac.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasCorFacade.create(ac);
                                }
                            }

                            if (!ambConsulta.getAmbConsultaHasTexturaList().isEmpty())
                            {
                                for (AmbConsultaHasTextura act : ambConsulta.getAmbConsultaHasTexturaList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.3  : ambConsultaHasTextura: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTexturaFacade.create(act);
                                } 
                            }

                            if (!ambConsulta.getAmbConsultaHasTurgorPeleList().isEmpty())
                            {
                                for (AmbConsultaHasTurgorPele act : ambConsulta.getAmbConsultaHasTurgorPeleList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.4  : AmbConsultaHasTurgorPele: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTurgorPeleFacade.create(act);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasEspessuraList().isEmpty())
                            {
                                for (AmbConsultaHasEspessura ace : ambConsulta.getAmbConsultaHasEspessuraList())
                                {
                                    ace.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.5  : ambConsultaHasEspessura: " + ace.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasEspessuraFacade.create(ace);
                                } 
                            }
            
                            if (!ambConsulta.getAmbConsultaHasTurgorTecidoList().isEmpty())
                            {
                                for (AmbConsultaHasTurgorTecido act : ambConsulta.getAmbConsultaHasTurgorTecidoList())
                                {
                                    act.setFkIdConsulta(ambConsulta);
//System.out.print("Teste 0.6  : AmbConsultaHasTurgorTecido: " + act.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            

                                    ambConsultaHasTurgorTecidoFacade.create(act);
                                }
                            }

                            ambDiagnosticoHipotese.setFkIdConsulta(ambConsulta);
//System.err.print("Teste 33   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdConsulta().getPkIdConsulta());
//System.err.print("Teste 33.1 : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());           
                            ambDiagnosticoHipotese.setDataHoraHipoteseDiagnostico(new Date());
//System.err.print("Teste 33.2   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getDataHoraHipoteseDiagnostico()); 

                            ambDiagnosticoHipotese.setFkIdFuncionario(ambConsulta.getFkIdFuncionario());
//System.err.print("Teste 33.3   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getFkIdFuncionario().getFkIdPessoa().getNome()); 
                            ambDiagnosticoHipotese.setAmbDiagnosticoHipoteseHasDoencaList(getInstanciaAmbConsultaListarBean().devolverDoencas());
//System.err.print("\n\n");
//System.err.print("Teste 33.4   : ambDiagnosticoHipotese: " + ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList());

                            ambDiagnosticoHipoteseFacade.create(ambDiagnosticoHipotese);
                 
                            if (ambObservacoesMedicas.getDescricao().equals(Defs.SOLICITACAO_OUTROS_SERVICOS))
                            {
                                if (!ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList().isEmpty())
                                {
                                    for (AmbDiagnosticoHipoteseHasDoenca adhd : ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList())
                                    {
                                         adhd.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 34   : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese());

                                         ambDiagnosticoHipoteseHasDoencaFacade.create(adhd);
                                    }
                                }
                                else
                                {
                                    ambDiagnosticoHipoteseHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35   : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getOutros());
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 35.0 : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipotese.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());                                    
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
//System.err.print("Teste 35.1 : ambDiagnosticoHipoteseHasDoenca: " + ambCidSubcategorias.getNome());                                    
                                
                                    ambDiagnosticoHipoteseHasDoencaFacade.create(ambDiagnosticoHipoteseHasDoenca);
                                }
                            }
                            else
                            {
                                ambDiagnostico.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
                                ambDiagnostico.setDataHoraDiagnostico(new Date());

                                ambDiagnostico.setFkIdFuncionario(ambDiagnosticoHipotese.getFkIdFuncionario());
//System.err.print("Teste 35.2 : ambDiagnostico: " + ambDiagnostico.getFkIdFuncionario().getFkIdPessoa().getNome());                                
                                ambDiagnostico.setFkIdExameRealizado(null);
                                ambDiagnostico.setFkIdCentro(grlCentroHospitalar);
                                ambDiagnostico.setObservacoes(ambDiagnosticoHipotese.getFkIdConsulta().getObservacoes());
                                ambDiagnostico.setFkIdObservacoesMedicas(ambObservacoesMedicas);
//System.err.print("Teste 35.3 : ambDiagnostico: " + ambDiagnostico.getFkIdObservacoesMedicas().getDescricao());                                

                                ambDiagnosticoFacade.create(ambDiagnostico);
                                    //Garavar aqui o amb_diagnostico
                                if (!ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList().isEmpty())
                                {
                                    for (AmbDiagnosticoHipoteseHasDoenca adhd : ambDiagnosticoHipotese.getAmbDiagnosticoHipoteseHasDoencaList())
                                    {
                                         adhd.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
//System.err.print("Teste 35.4  : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese());

                                         ambDiagnosticoHipoteseHasDoencaFacade.create(adhd);
                                        
                                         ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
//System.err.print("Teste 35.5 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdDiagnostico().getPkIdDiagnostico());                                         
                                         ambDiagnosticoHasDoenca.setFkIdSubcategorias(adhd.getFkIdSubcategorias());
//System.err.print("Teste 35.6 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdSubcategorias().getNome());                                
                                         
                                         ambDiagnosticoHasDoencaFacade.create(ambDiagnosticoHasDoenca);
                                    }
                                }
                                else
                                {
                                    ambDiagnosticoHipoteseHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35.7  : ambDiagnosticoHipoteseHasDoenca: " + ambDiagnosticoHipoteseHasDoenca.getOutros());
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdDiagnosticoHipotese(ambDiagnosticoHipotese);
                                    ambDiagnosticoHipoteseHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
                                
                                    ambDiagnosticoHipoteseHasDoencaFacade.create(ambDiagnosticoHipoteseHasDoenca);
                                    
                                    ambDiagnosticoHasDoenca.setOutros(getInstanciaAmbConsultaListarBean().getAmbDiagnosticoHipoteseHasDoenca().getOutros());
//System.err.print("Teste 35.8 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getOutros());
//                                    ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
                                    ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
//System.err.print("Teste 35.9 : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdDiagnostico().getPkIdDiagnostico());                                    
                                    ambDiagnosticoHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
//System.err.print("Teste 36   : ambDiagnosticoHasDoenca: " + ambDiagnosticoHasDoenca.getFkIdSubcategorias().getNome());                                    
                                
                                    ambDiagnosticoHasDoencaFacade.create(ambDiagnosticoHasDoenca);
                                }
                            }

                            for (AmbConsultorioAtendimento aca : ambConsultorioAtendimentoFacade.findAll())
                            {
                                if (aca.getPkIdConsultorioAtendimento().equals(getInstanciaAmbConsultaListarBean().getCodigoEncaminhamento()))
                                {
                                    for (AmbTriagem at : ambTriagemFacade.findAll())
                                    {
                                        if (aca.getFkIdTriagem().getPkIdTriagem().equals(at.getPkIdTriagem()))
                                        {
                                            for (AmbEstado ae : ambEstadoFacade.findAll())
                                            {
                                                if (ae.getDescricao().equals(Defs.ESTADO_ATENDIDO))
                                                {
//System.err.print("Teste   : Cód. Enc.   : " + aca.getPkIdConsultorioAtendimento()); 
//System.err.print("Teste   : Cód. Triagem: " + at.getPkIdTriagem()); 
//System.err.print("Teste   : Cód. Estado : " + ae.getPkIdEstado()); 
                                                    aca.getFkIdTriagem().setFkIdEstado(new AmbEstado(ae.getPkIdEstado()));
                                                    ambConsulta.setFkIdEstado(aca.getFkIdTriagem().getFkIdEstado());
//System.err.print("Teste 37   : ambConsulta: " + ambConsulta.getFkIdEstado().getDescricao()); 
//System.err.print("Teste 38   : ambTriagem: " + at.getFkIdEstado().getDescricao()); 

                                                    ambTriagemFacade.edit(aca.getFkIdTriagem());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
            
                        admsServicoEfetuado.setFkIdServicoSolicitado(ambConsulta.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado());
//System.err.print("Teste 39   : admsServicoEfetuado: " + admsServicoEfetuado.getFkIdServicoSolicitado().getPkIdServicoSolicitado());
                        admsServicoEfetuado.setDescricaoTabelaBusca(Defs.DESCRICAO_TABELA_BUSCA);
//System.err.print("Teste 40   : admsServicoEfetuado: " + admsServicoEfetuado.getDescricaoTabelaBusca());           
                        admsServicoEfetuado.setCodigoTabelaBusca(BigInteger.valueOf(ambConsulta.getPkIdConsulta()));
//System.err.print("Teste 41   : admsServicoEfetuado: " + admsServicoEfetuado.getCodigoTabelaBusca());
                        admsServicoEfetuado.setDataEfetuada(new Date());
//System.err.print("Teste 42   : admsServicoEfetuado: " + admsServicoEfetuado.getDataEfetuada());

                        admsServicoEfetuadoFacade.create(admsServicoEfetuado);

                        this.userTransaction.commit();

                        gravou = true;
                        
                        }
            
                        Mensagem.sucessoMsg("Reconsulta gravada com sucesso!");
            
                        ambConsulta = getInstanciaAmbConsulta();
            
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        try
                        {
                            Mensagem.erroMsg("Operação não concluída. Tente novamente!");
                            this.userTransaction.rollback();
                        } catch (IllegalStateException | SecurityException | SystemException ex)
                        {
//                            Mensagem.erroMsg("Rollback: " + ex.toString());
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        
        if (gravou == true)
            if (ambObservacoesMedicas.getDescricao().equals("Solicitação outros Serviços"))
                return solicitarServicos();

        return null;
    }
    
    ///Métodos de Pesquisa
    
    public String fecharConsulta()
    {
        getInstanciaAmbConsulta();
        return "/ambVisao/ambConsultas/consultaCriarAmb.xhtml?faces-redirect=true";
    }
    
    public String fecharReconsulta()
    {
        getInstanciaAmbConsulta();
        return "/ambVisao/ambConsultas/reconsultaCriarAmb.xhtml?faces-redirect=true";
    }    
    
    public String solicitarServicos()
    {
        return "/ambVisao/ambAdms/solicitacoesAmb.xhtml?faces-redirect=true";
    }
  
    public String solicitarTransferencia()
    {
        return "/ambVisao/ambTranferencia/transferenciaAmb.xhtml?faces-redirect=true";
    }    
    
    public boolean verifica()
    {
 
        if (gravou == true)
            gravou = true;
        else
            gravou = false;
        
        return true;
    }
    
    public boolean seleccionar()
    {
        if (selecione == true)
        {
            aderencia = true; 
            classe = true;
            dor = true;
            localizacao = true;
            ma = true;
            presencaDePregas = true;
            tamanho = true;
            turgor = true;
        } else
        {
            aderencia = false;
            classe = false;
            dor = false;
            localizacao = false;
            ma = false;
            presencaDePregas = false;
            tamanho = false;
            turgor = false;
        }
        return true;
    }
}
