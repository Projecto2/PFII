/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.triagem;


import entidade.AdmsAgendamento;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoAgendamento;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.AmbClassificacaoDor;
import entidade.AmbEstado;
import entidade.AmbSinal;
import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.GrlPessoa;
import entidade.RhCategoriaProfissional;
import entidade.RhEstadoFuncionario;
import entidade.RhFuncao;
import entidade.RhFuncionario;
import entidade.RhHorarioGeralDeTrabalho;
import entidade.RhNivelAcademico;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhTipoDeHorarioTrabalho;
import entidade.RhTipoFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.admsbean.paciente.AdmsPacienteAgendamentoPesquisarBean;
import managedBean.ambbean.ce.notificacoes.AmbNotificacoesCriarBean;
import managedBean.segbean.SegLoginBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AdmsEstadoPagamentoFacade;
import sessao.AdmsPacienteFacade;
import sessao.AmbClassificacaoDorFacade;
import sessao.AmbEstadoFacade;
import sessao.AmbSinalFacade;
import sessao.AmbTriagemFacade;
import sessao.AmbTriagemHasSinalFacade;
import sessao.RhFuncionarioFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTriagemCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;
    @EJB
    private AdmsEstadoPagamentoFacade admsEstadoPagamentoFacade;
    @EJB
    private AdmsPacienteFacade admsPacienteFacade;
    @EJB
    private AmbClassificacaoDorFacade ambClassificacaoDorFacade;
    @EJB
    private AmbEstadoFacade ambEstadoFacade;
    @EJB
    private AmbSinalFacade ambSinalFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private AmbTriagemHasSinalFacade ambTriagemHasSinalFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    private AdmsAgendamento admsAgendamento;
    private AdmsClassificacaoServicoSolicitado admsClassificacaoServicoSolicitado;
    private AdmsEstadoPagamento admsEstadoPagamento;
    private AdmsPaciente admsPaciente;
    private AmbClassificacaoDor ambClassificacaoDor;
    private AmbEstado ambEstado;
    private AmbSinal ambSinal;
    private AmbTriagem ambTriagem
                     , atAux;
    private AmbTriagemHasSinal ambTriagemHasSinal;
    private RhFuncionario rhFuncionario;

//    private Date dataNascimentoInicial, dataNascimentoFinal;

    
    private List<AdmsPaciente> pacientes;
//    private List<AmbTriagem> listaAmbTriagem;
    
    
    private boolean classificacaoDaDor
                  , gravou = false
                  , localDaDor
                  , seleccionar
                  , sinais;
    
    private int codigoClassificacaoDor
              , codigoEstado
              , codigoEstadoPaciente
              , codigoFrequenciaCardiaca
              , codigoFrequenciaRespiratoria
              , codigoTensaoArterialMaxima
              , codigoTensaoArterialMinima;
    
//    private int[] listaSinais;

    private long codigoAdmsAgendamento
               , codigoPaciente;
    
    private String numeroPaciente;
    
    /**
     * Creates a new instance of AmbTriagemBean
     */
    public AmbTriagemCriarBean()
    {
        classificacaoDaDor = true;
        localDaDor = true;
        seleccionar = true;
        sinais = true;
        inicializaCampos();
    }

    private void inicializaCampos()
    {
        ambTriagem = getInstanciaAmbTriagem();
        
        ambTriagem.setFrequenciaCardiaca(Defs.VALOR_FREQUENCIA_CARDIACA_PULSO);
        ambTriagem.setFrequenciaRespiratoria(Defs.VALOR_FREQUENCIA_RESPIRATRIA);
        ambTriagem.setGlicemia(Defs.VALOR_GLICEMIA);
        ambTriagem.setPulso(Defs.VALOR_PULSO);
        ambTriagem.setTensaoArterialMaxima(Defs.VALOR_TENSAO_ARTERIAL_MAXIMA);
        ambTriagem.setTensaoArterialMinima(Defs.VALOR_TENSAO_ARTERIAL_MINIMA);
        ambTriagem.setTemperatura(Defs.VALOR_TEMPERATURA);
       
//        AmbNotificacoesCriarBean.getInstanciaBean().criarAmbNotificacaoPrimeira();
    }
    
    public static AdmsPacienteAgendamentoPesquisarBean obterAdmsPacienteAgendamentoPesquisarBean()
    {
        return (AdmsPacienteAgendamentoPesquisarBean) GeradorCodigo.getInstanciaBean("admsPacienteAgendamentoPesquisarBean");
    }
    
    public static AmbTriagemCriarBean getInstanciaBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemBean");
    }
    
    public static AmbTriagemListarBean getInstanciaAmbTriagemListarBean()
    {
        return (AmbTriagemListarBean) GeradorCodigo.getInstanciaBean("ambTriagemListarBean");
    }
    
    public static SegLoginBean getInstanciaSeLoginBean()
    {
        return (SegLoginBean) GeradorCodigo.getInstanciaBean("segLoginBean");
    }
    
    public static AdmsAgendamento getInstanciaAdmsAgendamento()
    {
        AdmsAgendamento admsAgendamento = new AdmsAgendamento();
        admsAgendamento.setFkIdEstadoAgendamento(new AdmsEstadoAgendamento());
        admsAgendamento.setFkIdServicoSolicitado(new AdmsServicoSolicitado());
        
        return admsAgendamento;
    }
    
    public static AdmsClassificacaoServicoSolicitado getInstanciaAdmsClassificacaoServicoSolicitado()
    {
        AdmsClassificacaoServicoSolicitado admsClassificacaoServicoSolicitado = new AdmsClassificacaoServicoSolicitado();

        return admsClassificacaoServicoSolicitado;
    }
    
    public static AdmsPaciente getInstanciaAdmsPaciente()
    {
        AdmsPaciente admsPaciente = new AdmsPaciente();
        admsPaciente.setFkIdPessoa(new GrlPessoa());

        return admsPaciente;
    }
    
    public static AmbClassificacaoDor getInstanciaAmbClassificacaoDor()
    {
        AmbClassificacaoDor ambClassificacaoDor = new AmbClassificacaoDor();
        
        return ambClassificacaoDor;
    }
    
    public static AmbEstado getInstanciaAmbEstado()
    {
        AmbEstado ambEstado = new AmbEstado();
        
        return ambEstado;
    }
    
    public static AmbSinal getInstanciaAmbSinal()
    {
        AmbSinal ambSinal = new AmbSinal();

        return ambSinal;
    }
        
    public static AmbTriagem getInstanciaAmbTriagem()
    {
        AmbTriagem ambtriagem = new AmbTriagem();
        ambtriagem.setFkIdAgendamento(getInstanciaAdmsAgendamento());
        ambtriagem.setFkIdClassificacaoDor(getInstanciaAmbClassificacaoDor());
        ambtriagem.setFkIdClassificacaoServicoSolicitado(getInstanciaAdmsClassificacaoServicoSolicitado());
        ambtriagem.setFkIdEstado(getInstanciaAmbEstado());
        
        return ambtriagem;
    }
    
    public static AmbTriagemHasSinal getInstanciaAmbTriagemHasSinal()
    {
        AmbTriagemHasSinal ambTriagemHasSinal = new AmbTriagemHasSinal();
        ambTriagemHasSinal.setFkIdSinal(new AmbSinal());
        ambTriagemHasSinal.setFkIdTriagem(getInstanciaAmbTriagem());

        return ambTriagemHasSinal;
    }

    public static RhFuncionario getInstanciaRhFuncionario()
    {
        RhFuncionario rhFuncionario = new RhFuncionario();
        rhFuncionario.setFkIdAnexoGuiaTransferencia(new GrlFicheiroAnexado());
        rhFuncionario.setFkIdCategoria(new RhCategoriaProfissional());
        rhFuncionario.setFkIdCentroHospitalar(new GrlCentroHospitalar());
        rhFuncionario.setFkIdEspecialidade1(new GrlEspecialidade());
        rhFuncionario.setFkIdEspecialidade2(new GrlEspecialidade());
        rhFuncionario.setFkIdEstadoFuncionario(new RhEstadoFuncionario());
        rhFuncionario.setFkIdFuncao(new RhFuncao());
        rhFuncionario.setFkIdHorarioGeralDeTrabalho(new RhHorarioGeralDeTrabalho());
        rhFuncionario.setFkIdNivelAcademico(new RhNivelAcademico());
        rhFuncionario.setFkIdPessoa(new GrlPessoa());
        rhFuncionario.setFkIdProfissao(new RhProfissao());
        rhFuncionario.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        rhFuncionario.setFkIdTipoDeHorarioTrabalho(new RhTipoDeHorarioTrabalho());
        rhFuncionario.setFkIdTipoFuncionario(new RhTipoFuncionario());
        
        return rhFuncionario;
    }

    public List<AdmsPaciente> getPacientes()
    {
        return pacientes;
    }

    public void setPacientes(List<AdmsPaciente> pacientes)
    {
        this.pacientes = pacientes;
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
    
    public AdmsClassificacaoServicoSolicitado getAdmsClassificacaoServicoSolicitado()
    {
        if (admsClassificacaoServicoSolicitado == null)
        {
            admsClassificacaoServicoSolicitado = getInstanciaAdmsClassificacaoServicoSolicitado();
        }
        
        return admsClassificacaoServicoSolicitado;
    }
    
    public void setAdmsClassificacaoServicoSolicitado(AdmsClassificacaoServicoSolicitado admsClassificacaoServicoSolicitado)
    {
        this.admsClassificacaoServicoSolicitado = admsClassificacaoServicoSolicitado;
    }

    public AdmsPaciente getAdmsPaciente()
    {
        if (admsPaciente == null)
        {
            admsPaciente = getInstanciaAdmsPaciente();
        }
        return admsPaciente;
    }

    public void setAdmsPaciente(AdmsPaciente admsPaciente)
    {
        this.admsPaciente = admsPaciente;
    }

    public AmbClassificacaoDor getAmbClassificacaoDor()
    {
        if (ambClassificacaoDor == null)
        {
            ambClassificacaoDor = getInstanciaAmbClassificacaoDor();
        }
        return ambClassificacaoDor;
    }

    public void setAmbClassificacaoDor(AmbClassificacaoDor ambClassificacaoDor)
    {
        this.ambClassificacaoDor = ambClassificacaoDor;
    }

    public AmbEstado getAmbEstado()
    {
        if (ambEstado == null)
        {
            ambEstado = getInstanciaAmbEstado();
        }
        return ambEstado;
    }

    public void setAmbEstado(AmbEstado ambEstado)
    {
        this.ambEstado = ambEstado;
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

    public AmbSinal getAmbSinal()
    {
        if (ambSinal == null)
        {
            ambSinal = getInstanciaAmbSinal();
        }
        return ambSinal;
    }

    public void setAmbSinal(AmbSinal ambSinal)
    {
        this.ambSinal = ambSinal;
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

    public AmbTriagem getAtAux()
    {
        if (atAux == null)
        {
            atAux = getInstanciaAmbTriagem();
        }
        return atAux;
    }

    public void setAtAux(AmbTriagem atAux)
    {
        this.atAux = atAux;
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

    public int getCodigoClassificacaoDor()
    {
        return codigoClassificacaoDor;
    }
    
    public void setCodigoClassificacaoDor(int codigoClassificacaoDor)
    {
        this.codigoClassificacaoDor = codigoClassificacaoDor;
    }

    public int getCodigoEstado()
    {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado)
    {
        this.codigoEstado = codigoEstado;
    }

    public int getCodigoEstadoPaciente()
    {
        return codigoEstadoPaciente;
    }

    public void setCodigoEstadoPaciente(int codigoEstadoPaciente)
    {
        this.codigoEstadoPaciente = codigoEstadoPaciente;
    }

    public int getCodigoFrequenciaCardiaca()
    {
        return codigoFrequenciaCardiaca;
    }

    public void setCodigoFrequenciaCardiaca(int codigoFrequenciaCardiaca)
    {
        this.codigoFrequenciaCardiaca = codigoFrequenciaCardiaca;
    }

    public int getCodigoFrequenciaRespiratoria()
    {
        return codigoFrequenciaRespiratoria;
    }

    public void setCodigoFrequenciaRespiratoria(int codigoFrequenciaRespiratoria)
    {
        this.codigoFrequenciaRespiratoria = codigoFrequenciaRespiratoria;
    }

    public int getCodigoTensaoArterialMaxima()
    {
        return codigoTensaoArterialMaxima;
    }

    public void setCodigoTensaoArterialMaxima(int codigoTensaoArterialMaxima)
    {
        this.codigoTensaoArterialMaxima = codigoTensaoArterialMaxima;
    }

    public int getCodigoTensaoArterialMinima()
    {
        return codigoTensaoArterialMinima;
    }

    public void setCodigoTensaoArterialMinima(int codigoTensaoArterialMinima)
    {
        this.codigoTensaoArterialMinima = codigoTensaoArterialMinima;
    }
    
    public long getCodigoAdmsAgendamento()
    {
        return codigoAdmsAgendamento;
    }

    public void setCodigoAdmsAgendamento(long codigoAdmsAgendamento)
    {
        this.codigoAdmsAgendamento = codigoAdmsAgendamento;
    }

    public String getNumeroPaciente()
    {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente)
    {
        this.numeroPaciente = numeroPaciente;
    }
        
    public boolean isClassificacaoDaDor()
    {
        return classificacaoDaDor;
    }
        
    public void setClassificacaoDaDor(boolean classificacaoDaDor)
    {
        this.classificacaoDaDor = classificacaoDaDor;
    }

    public boolean isGravou()
    {
        return gravou;
    }

    public void setGravou(boolean gravou)
    {
        this.gravou = gravou;
    }
        
    public boolean isLocalDaDor()
    {
        return localDaDor;
    }

    public void setLocalDaDor(boolean localDaDor)
    {
        this.localDaDor = localDaDor;
    }
    
    public boolean isSeleccionar()
    {
        return seleccionar;
    }

    public void setSeleccionar(boolean seleccionar)
    {
        this.seleccionar = seleccionar;
    }

    public boolean isSinais()
    {
        return sinais;
    }

    public void setSinais(boolean sinais)
    {
        this.sinais = sinais;
    }
    
    public SegConta segContaObterSegLoginBean()
    {
        SegConta segConta;// = new SegConta();
        segConta = getInstanciaSeLoginBean().obterContaDaCorrenteSessao();

        return segConta;
    }
    
    public RhFuncionario obterFuncionario()
    {
        return segContaObterSegLoginBean().getFkIdFuncionario();
    }
    
    public static double validaPeso(double peso)
    {
        if (!(peso >= 4 && peso <= 250))
           Mensagem.erroMsg("Verificar o valor!");
        return peso;
    }
    
    public String criarAmbTriagem()
    {
        if (AmbTriagemListarBean.getInstanciaBean().getCodigoPaciente() == 0)
            Mensagem.warnMsg("Tem de seleccionar um Paciente na lista. Não pode ser nulo!");
        
        try
        {
            this.userTransaction.begin();
            
            ambTriagemHasSinal = getInstanciaAmbTriagemHasSinal();
            
//            if (!gravou)
//            {
                
            admsAgendamento = admsAgendamentoFacade.find(getInstanciaAmbTriagemListarBean().listarTodosAgendamentos().getPkIdAgendamento());            
//System.out.print("Teste 0   : admsAgendamento: " + admsAgendamento.getPkIdAgendamento() + " Nome: " + admsAgendamento.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
            admsClassificacaoServicoSolicitado = admsClassificacaoServicoSolicitadoFacade.find(codigoEstadoPaciente);
//System.out.print("Teste 1   : admsClassificacaoServicoSolicitado: " + admsClassificacaoServicoSolicitado.getDescricaoClassificacaoServicoSolicitado());
            ambClassificacaoDor = ambClassificacaoDorFacade.find(codigoClassificacaoDor);
//System.out.print("Teste 2   : ambClassificacaoDor: " + ambClassificacaoDor.getDescricao());
            ambEstado = ambEstadoFacade.findByDescricao(Defs.ESTADO_ESPERA);
//System.out.print("Teste 3   : ambEstado: " + ambEstado.getDescricao());

            ambTriagem.setFkIdAgendamento(admsAgendamento);
//System.out.print("Teste 4   : Agendamento: " + ambTriagem.getFkIdAgendamento());
            ambTriagem.setFkIdClassificacaoDor(ambClassificacaoDor);
//System.out.print("Teste 5   : ClassificacaoDor: " + ambTriagem.getFkIdClassificacaoDor());
            ambTriagem.setFkIdClassificacaoServicoSolicitado(admsClassificacaoServicoSolicitado);
//System.out.print("Teste 6   : ClassificacaoServicoSolicitado: " + ambTriagem.getFkIdClassificacaoServicoSolicitado());                
            ambTriagem.setFkIdEstado(ambEstado);
//System.out.print("Teste 7   : ambEstado: " + ambEstado.getDescricao());                
            ambTriagem.setFrequenciaCardiaca(ambTriagem.getFrequenciaCardiaca() + " " + Defs.UNIDADE_FREQUENCIA_CARDIACA_PULSO);
//System.out.print("Teste 8   : FrequenciaCardiaca: " + ambTriagem.getFrequenciaCardiaca());                
            ambTriagem.setFrequenciaRespiratoria(ambTriagem.getFrequenciaRespiratoria() + " " + Defs.UNIDADE_FREQUENCIA_RESPIRATORIA);
//System.out.print("Teste 9   : FrequenciaRespiratoria: " + ambTriagem.getFrequenciaRespiratoria());
//                ambTriagem.setPeso(peso(ambTriagem.getPeso()));
            ambTriagem.setFkIdFuncionario(rhFuncionarioFacade.find(obterFuncionario().getPkIdFuncionario()));
//System.out.print("Teste 10  : Funcionario: " + ambTriagem.getFkIdFuncionario());                
            ambTriagem.setGlicemia(ambTriagem.getGlicemia() + " " + Defs.UNIDADE_GLICEMIA);
//System.out.print("Teste 11  : Glicemia: " + ambTriagem.getGlicemia());                
            ambTriagem.setPulso(ambTriagem.getPulso() + " " + Defs.UNIDADE_FREQUENCIA_CARDIACA_PULSO);
//System.out.print("Teste 12  : Pulso: " + ambTriagem.getPulso());
//System.out.print("Teste 8   : ambTriagem: " + ambTriagem.getFkIdFuncionario().getFkIdPessoa().getNome());
            ambTriagem.setTensaoArterialMaxima(ambTriagem.getTensaoArterialMaxima() + " " + Defs.UNIDADE_TENSAO_ARTERIAL);
//System.out.print("Teste 13  : TensaoArterialMaxima: " + ambTriagem.getTensaoArterialMaxima());
            ambTriagem.setTensaoArterialMinima(ambTriagem.getTensaoArterialMinima() + " " + Defs.UNIDADE_TENSAO_ARTERIAL);
//System.out.print("Teste 14  : TensaoArterialMinima: " + ambTriagem.getTensaoArterialMinima());
                
            ambTriagem.setAmbTriagemHasSinalList(getInstanciaAmbTriagemListarBean().devolverSinais());
//System.out.print("Teste 15  : " + ambTriagem.getAmbTriagemHasSinalList().toString());                
                
            ambTriagem.setDataHoraTriagem(DataUtils.stringToDate(getInstanciaAmbTriagemListarBean().dataSistema()));
//System.out.print("Teste 16  : DataHoraTriagem: " + ambTriagem.getDataHoraTriagem());

            ambTriagemFacade.create(ambTriagem);

            if (!ambTriagem.getAmbTriagemHasSinalList().isEmpty())
            {
                for (AmbTriagemHasSinal ats : ambTriagem.getAmbTriagemHasSinalList())
                {
                     ats.setFkIdTriagem(ambTriagem);
//System.out.print("Teste17   : ambTriagem: " + ats.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            
//System.out.print("Teste17.0 : ambTriagem: " + ats.getFkIdSinal().getDescricao());
                     ambTriagemHasSinalFacade.create(ats);
                }
            }
            else
            {

                ambSinal = ambTriagemFacade.findByDescricao(Defs.VALOR_PADRAO);
                             
                ambTriagemHasSinal.setFkIdTriagem(ambTriagem);
                ambTriagemHasSinal.setFkIdSinal(ambSinal);
                             
//System.out.print("Teste17.1 : ambTriagem: " + ambTriagemHasSinal.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            
//System.out.print("Teste17.2 : ambTriagem: " + ambTriagemHasSinal.getFkIdSinal().getDescricao());
                ambTriagemHasSinalFacade.create(ambTriagemHasSinal);
                    
            }

            userTransaction.commit();

//                gravou = true;
//            }
            
            Mensagem.sucessoMsg("Triagem guardada com sucesso!");
            
            limpar();

        } catch (Exception e)
        {
//            Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg("Operação não concluída. Tente novamente!");
//                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
        
//        if (gravou == true)
//        {
//           return encaminhar();
//        }
        
        return null;
    }
    
    public String encaminhar()
    {
        return "/ambVisao/ambEncaminhamentos/consultorioAtendimentoAmb.xhtml?faces-redirect=true";
    }
    
    public String fechar()
    {
        getInstanciaAmbTriagem();
        return "/ambVisao/ambTriagem/triagemAmb.xhtml?faces-redirect=true";
    }
    
    public void limpar()
    {
        ambTriagem.setLocalDaDor(null);
        ambTriagem.setAltura(null);
        ambTriagem.setPeso(null);
        ambTriagem.setObservacoes(null);
        inicializaCampos();
        atAux = getInstanciaAmbTriagem();
        ambTriagemHasSinal = getInstanciaAmbTriagemHasSinal();
    }

    public boolean seleccionarSinais()
    {
        if (seleccionar == true)
        {
            classificacaoDaDor = true;
            localDaDor = true;
            sinais = true;
        } else
        {
            classificacaoDaDor = false;
            localDaDor = false;
            sinais = false;
        }
        return true;
    }
    
    //
}