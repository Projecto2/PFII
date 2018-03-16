/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.diagnosticos;

import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.AmbCidCategorias;
import entidade.AmbCidSubcategorias;
import entidade.AmbConsulta;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbDiagnosticoHipotese;
import entidade.AmbObservacoesMedicas;
import entidade.DiagAmostra;
import entidade.DiagExame;
import entidade.DiagExameRealizado;
import entidade.GrlCentroHospitalar;
import entidade.RhFuncionario;
import entidade.SegConta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.consultas.AmbConsultaCriarBean;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAdmsPaciente;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipotese;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaGrlCentroHospitalar;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import managedBean.segbean.SegLoginBean;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbObservacoesMedicasFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.GrlCentroHospitalarFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhProfissaoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbDiagnosticoCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;
    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;
    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;
    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;
    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;
    @EJB
    private AmbConsultaFacade ambConsultaFacade;
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;    
    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;
    @EJB
    private AmbObservacoesMedicasFacade ambObservacoesMedicasFacade;
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;    
    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;

    private AdmsPaciente admsPaciente
                       , admsPacienteHistorico;
    private AmbCidSubcategorias ambCidSubcategorias;
//    private AmbConsulta ambConsulta;
    private AmbDiagnostico ambDiagnostico;
    private AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca;
    private AmbDiagnosticoHipotese ambDiagnosticoHipotese;
    private AmbObservacoesMedicas ambObservacoesMedicas;
    private DiagExameRealizado diagExameRealizado;
    private GrlCentroHospitalar grlCentroHospitalar;
    private RhFuncionario rhFuncionario;
    
        private boolean gravou = false;
    
//    private int codigoExameRealizado 
//              , codigoObservacoesMedicas
//              , limite = Defs.LIMITE;
    private long codigoConsulta;

    /**
     * Creates a new instance of AmbCeDiagnosticoBean
     */
    public AmbDiagnosticoCriarBean()
    {
    }

    public static AmbDiagnosticoCriarBean getInstanciaBean()
    {
        return (AmbDiagnosticoCriarBean) GeradorCodigo.getInstanciaBean("ambDiagnosticoCriarBean");
    }
    
    public AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }      

//    public static SegLoginBean obterSeLoginBean()
//    {
//        return (SegLoginBean) GeradorCodigo.getInstanciaBean("segLoginBean");
//    }
    
    public static AmbCidSubcategorias getInstanciaAmbCidSubcategorias()
    {
        AmbCidSubcategorias ambCidSubcategorias = new AmbCidSubcategorias();
        
        ambCidSubcategorias.setFkIdCategorias(new AmbCidCategorias());

        return ambCidSubcategorias;
    }
    
    public static AmbDiagnostico getInstanciaAmbDiagnostico()
    {
        AmbDiagnostico ambDiagnostico = new AmbDiagnostico();
        
        ambDiagnostico.setFkIdDiagnosticoHipotese(getInstanciaAmbDiagnosticoHipotese());
        ambDiagnostico.setFkIdExameRealizado(getInstanciaDiagExameRealizado());
        ambDiagnostico.setFkIdFuncionario(getInstanciaRhFuncionario());
        ambDiagnostico.setFkIdObservacoesMedicas(new AmbObservacoesMedicas());

        return ambDiagnostico;
    }

    public static AmbDiagnosticoHasDoenca getInstanciaAmbDiagnosticoHasDoenca()
    {
        AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca = new AmbDiagnosticoHasDoenca();

        ambDiagnosticoHasDoenca.setFkIdDiagnostico(getInstanciaAmbDiagnostico());
        ambDiagnosticoHasDoenca.setFkIdSubcategorias(new AmbCidSubcategorias());

        return ambDiagnosticoHasDoenca;
    }
    
    public static DiagExameRealizado getInstanciaDiagExameRealizado()
    {
        DiagExameRealizado diagExameRealizado = new DiagExameRealizado();
        
        diagExameRealizado.setFkIdAmostra(new DiagAmostra());
        diagExameRealizado.setFkIdExame(new DiagExame());
        diagExameRealizado.setFkIdFuncionario(getInstanciaRhFuncionario());
        diagExameRealizado.setFkIdServicoSolicitado(new AdmsServicoSolicitado());

        return diagExameRealizado;
    }

    public List<String> getListaDoencasDiagnosticadas()
    {
        return getListaDoencasDiagnosticadas();
    }

    public void setListaDoencasDiagnosticadas(List<String> listaDoencasDiagnosticadas)
    {
        this.setListaDoencasDiagnosticadas(listaDoencasDiagnosticadas);
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
    
    public AdmsPaciente getAdmsPacienteHistorico()
    {
        if (admsPacienteHistorico == null)
        {
            admsPacienteHistorico = getInstanciaAdmsPaciente();
        }
        return admsPacienteHistorico;
    }

    public void setAdmsPacienteHistorico(AdmsPaciente admsPacienteHistorico)
    {
        this.admsPacienteHistorico = admsPacienteHistorico;
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

//    public AmbConsulta getAmbConsulta()
//    {
//        if (ambConsulta == null)
//        {
//            ambConsulta = getInstanciaAmbConsulta();
//        }
//        return ambConsulta;
//    }

//    public void setAmbConsulta(AmbConsulta ambConsulta)
//    {
//        this.ambConsulta = ambConsulta;
//    }

//    public AmbDiagnosticoHasDoenca getAmbDiagnosticoHasDoenca()
//    {
//        if (ambDiagnosticoHasDoenca == null)
//        {
//            ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
//        }
//        return ambDiagnosticoHasDoenca;
//    }

//    public void setAmbDiagnosticoHasDoenca(AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca)
//    {
//        this.ambDiagnosticoHasDoenca = ambDiagnosticoHasDoenca;
//    }

//    public AmbDiagnostico getAmbDiagnostico()
//    {
//        if (ambDiagnostico == null)
//        {
//            ambDiagnostico = getInstanciaAmbDiagnostico();
//        }
//        return ambDiagnostico;
//    }

//    public void setAmbDiagnostico(AmbDiagnostico ambDiagnostico)
//    {
//        this.ambDiagnostico = ambDiagnostico;
//    }

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

    public DiagExameRealizado getDiagExameRealizado()
    {
        if (diagExameRealizado == null)
        {
            diagExameRealizado = getInstanciaDiagExameRealizado();
        }
        return diagExameRealizado;
    }

    public void setDiagExameRealizado(DiagExameRealizado diagExameRealizado)
    {
        this.diagExameRealizado = diagExameRealizado;
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
    
    public boolean isGravou()
    {
        return gravou;
    }

    public void setGravou(boolean gravou)
    {
        this.gravou = gravou;
    }    
   
    public long getCodigoConsulta()
    {
        return codigoConsulta;
    }

    public void setCodigoConsulta(long codigoConsulta)
    {
        this.codigoConsulta = codigoConsulta;
    }

    ///CRUD
    
    public String criarRegisto() 
    {
        if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoObservacoesMedicas() == 0)
            {
                Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");
                Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");
            }
        }
        if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoCentroHospitalar() == 0)
        {
            if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoObservacoesMedicas() > 0)
                Mensagem.warnMsg("O 'Centro Hospitalar de Proveniência' é obrigatório!");     
        } 
        if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {
            if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoObservacoesMedicas() == 0)
                Mensagem.warnMsg("O campo 'Alta/Internado?' é obrigatório!");     
        }
        
        if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoCentroHospitalar() > 0)
        {   
            if (AmbDiagnosticoListarBean.getInstanciaBean().getCodigoObservacoesMedicas() > 0)
            {
                try
                {
                    this.userTransaction.begin();
            
                    ambCidSubcategorias = getInstanciaAmbCidSubcategorias();
                    ambDiagnostico = getInstanciaAmbDiagnostico();
                    ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();
            
                    if (!gravou)
                    {
                        grlCentroHospitalar = grlCentroHospitalarFacade.find(AmbDiagnosticoListarBean.getInstanciaBean().getCodigoCentroHospitalar());
System.err.print("Teste 0.1  : ambConsulta: " + grlCentroHospitalar.getFkIdInstituicao().getDescricao());
                        ambDiagnostico.setFkIdCentro(grlCentroHospitalar);
System.err.print("Teste 0.2  : ambConsulta: " + ambDiagnostico.getFkIdCentro().getFkIdInstituicao().getDescricao());

                        diagExameRealizado = diagExameRealizadoFacade.find(AmbDiagnosticoListarBean.getInstanciaBean().getCodigoExameRealizado());
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 0 : " + diagExameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());             
            
                        for (AmbDiagnosticoHipotese adh : ambDiagnosticoHipoteseFacade.findAll())
                        {
                             if (adh.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getPkIdSubprocesso()
                                 .equals(diagExameRealizado.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getPkIdSubprocesso()))
                             {
                                 ambDiagnostico.setFkIdDiagnosticoHipotese(adh);
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 1 : " + adh.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdFuncionarioSolicitante()
        .getFkIdPessoa().getNome());  
                             }
                        }                    
            
                        ambObservacoesMedicas = ambObservacoesMedicasFacade.find(AmbDiagnosticoListarBean.getInstanciaBean().getCodigoObservacoesMedicas());
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 2 : " + ambObservacoesMedicas.getDescricao());          
            
                        ambDiagnostico.setDataHoraDiagnostico(new Date());
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 3 : " + ambDiagnostico.getDataHoraDiagnostico());             

                        ambDiagnostico.setFkIdExameRealizado(diagExameRealizado);
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 5 : " + ambDiagnostico.getFkIdExameRealizado().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());            
                        ambDiagnostico.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 6 : " + ambDiagnostico.getFkIdFuncionario().getFkIdPessoa().getNome());             
                        ambDiagnostico.setFkIdObservacoesMedicas(ambObservacoesMedicas);
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 7 : " + ambDiagnostico.getFkIdObservacoesMedicas().getDescricao());

                        if (AmbDiagnosticoListarBean.getInstanciaBean().getAmbDiagnostico().getObservacoes() != null)
                            ambDiagnostico.setObservacoes(AmbDiagnosticoListarBean.getInstanciaBean().getAmbDiagnostico().getObservacoes());
                        
                        ambDiagnostico.setAmbDiagnosticoHasDoencaList(AmbDiagnosticoListarBean.getInstanciaBean().devolverDoencas());
                        
                        ambDiagnosticoFacade.create(ambDiagnostico);
                        
                        if (!ambDiagnostico.getAmbDiagnosticoHasDoencaList().isEmpty())
                        {
                            for (AmbDiagnosticoHasDoenca adhd : ambDiagnostico.getAmbDiagnosticoHasDoencaList())
                            {
                                 adhd.setFkIdDiagnostico(ambDiagnostico);
System.err.print("Teste 8   : ambDiagnosticoHasDoenca: " + ambDiagnostico.getFkIdDiagnosticoHipotese().getFkIdConsulta()
                 .getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());

                                ambDiagnosticoHasDoencaFacade.create(adhd);
                           }
                       }
                       else
                       {
                           ambDiagnosticoHasDoenca.setOutros(AmbDiagnosticoListarBean.getInstanciaBean().getAmbDiagnosticoHasDoenca().getOutros());
System.err.print("ambDiagnosticoCriarBean.criarRegisto(): Teste 11: " + ambDiagnosticoHasDoenca.getOutros()); 
                           ambDiagnosticoHasDoenca.setFkIdDiagnostico(ambDiagnostico);
                           ambDiagnosticoHasDoenca.setFkIdSubcategorias(ambCidSubcategorias);
                                
                           ambDiagnosticoHasDoencaFacade.create(ambDiagnosticoHasDoenca);
                       } 

                        this.userTransaction.commit();
            
                        gravou = true;
                        
                        }
                    
                        Mensagem.sucessoMsg("Paciente diagnosticado com sucesso!");
            
                }  catch (Exception e)
                {
                    Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
                    try
                    {
                        e.printStackTrace();
//                        Mensagem.erroMsg(e.toString());
                        userTransaction.rollback();
                    } catch (IllegalStateException | SecurityException | SystemException | EJBException ex)
                    {
                        e.printStackTrace();
//                        Mensagem.erroMsg("Rollback: " + ex.toString());
                    }
                }
            }
        }
        
        if (gravou == true)
            if (ambObservacoesMedicas.getDescricao().equals("Solicitação outros Serviços"))
                return AmbConsultaCriarBean.getInstanciaBean().solicitarServicos();
        
        return null;
    }
    
    ///CRUD
    
    
    ///
    
//    public SegConta segContaObterSegLoginBean()
//    {
//        SegConta segConta = new SegConta();
//        segConta = obterSeLoginBean().obterContaDaCorrenteSessao();
//
//        return segConta;
//    }
    
//    public RhFuncionario obterFuncionario()
//    {
//        return segContaObterSegLoginBean().getFkIdFuncionario();
//    }

    public String fechar()
    {
        getInstanciaAmbDiagnostico();
        return "ambVisao/ambDiagnosticos/diagnosticosCriarAmb.xhtml?faces-redirect=true";
    }
    
    ///
}
