/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.ambbean.ce.triagem;

import entidade.AdmsAgendamento;
import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AmbClassificacaoDor;
import entidade.AmbSinal;
import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.ambbean.ce.notificacoes.AmbNotificacoesCriarBean;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAdmsAgendamento;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagemHasSinal;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsClassificacaoServicoSolicitadoFacade;
import sessao.AmbClassificacaoDorFacade;
import sessao.AmbSinalFacade;
import sessao.AmbTriagemFacade;
import sessao.AmbTriagemHasSinalFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTriagemListarBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsClassificacaoServicoSolicitadoFacade admsClassificacaoServicoSolicitadoFacade;    
    @EJB
    private AmbClassificacaoDorFacade ambClassificacaoDorFacade;
    @EJB
    private AmbSinalFacade ambSinalFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private AmbTriagemHasSinalFacade ambTriagemHasSinalFacade;

    private List<AmbTriagem> listaTriagem;
    
    private AdmsAgendamento admsAgendamento;
    private AmbClassificacaoDor ambClassificacaoDor;
    private AmbSinal ambSinal;
    private AmbTriagem ambTriagem
                     , atAux;
    private AmbTriagemHasSinal ambTriagemHasSinal;
    private Date dataInicio
               , dataFinal;
    
    private int   listaSinais []
                , limite = Defs.LIMITE;
    
    private long codigoPaciente;
    
    /**
     * Creates a new instance of AmbTriagemListarBean
     */
    public AmbTriagemListarBean()
    {        
    }

    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbTriagemListarBean getInstanciaBean()
    {
        return (AmbTriagemListarBean) GeradorCodigo.getInstanciaBean("ambTriagemListarBean");
    }

//    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
//    {
//        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
//    }
//    
//    public static AmbDiagnosticoListarBean getInstanciaAmbDiagnosticoListarBean()
//    {
//        return (AmbDiagnosticoListarBean) GeradorCodigo.getInstanciaBean("ambDiagnosticoListarBean");
//    }
//    
//    public static AmbNotificacoesCriarBean getInstanciaAmbNotificacoesCriarBean()
//    {
//        return (AmbNotificacoesCriarBean) GeradorCodigo.getInstanciaBean("ambNotificacoesCriarBean");
//    }



    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/
    public List<AmbTriagem> getListaTriagem()
    {
        if (listaTriagem != null)
        {
            listaTriagem = new ArrayList<>();
        }
        return listaTriagem;
    }

    public void setListaTriagem(List<AmbTriagem> listaTriagem)
    {
        this.listaTriagem = listaTriagem;
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
    
    public AmbSinal getAmbSinal()
    {
        if (ambSinal == null)
        {
            ambSinal = new AmbSinal();
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
    
    public Date getDataFinal()
    {
        if (dataFinal == null)
        {
            dataFinal = new Date();
        }
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal)
    {
        this.dataFinal = dataFinal;
    }
    
    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }
    
    public int[] getListaSinais()
    {
        return listaSinais;
    }

    public void setListaSinais(int[] listaSinais)
    {
        this.listaSinais = listaSinais;
    }

    public int getLimite()
    {
        return limite;
    }
    
    public long getCodigoPaciente()
    {
        return codigoPaciente;
    }

    public void setCodigoPaciente(long codigoPaciente)
    {
        this.codigoPaciente = codigoPaciente;
    }    
    
    /*********************Fim dos métodos setters e getters********************/
    
    /*********************Início dos métodos para pesquisas********************/
    
    public List<AdmsAgendamento> findSolicitacoes()
    {        
        return ambTriagemFacade.findSolicitacoesAmb();
    }

    public List<AdmsAgendamento> pesquisarPacientes()
    {
        List<AdmsAgendamento> resultado = new ArrayList<>();
        
        resultado.clear();

        for (AdmsAgendamento admsA : admsAgendamentoFacade.findAll())//admsAgendamentoFacade.findAll()
        {
            if (admsA != null)
            {
                if (admsA.getFkIdServicoSolicitado().getFkIdServico().getFkIdTipoServico().getDescricaoTipoServico().equals("Consulta"))
                { 
                    if (admsA.getFkIdEstadoAgendamento().getDescricaoEstadoAgendamento().equals("Chegou"))
                    {                      
                       if (admsA.getDataHoraCheckIn() != null)
                       {
//                           if (DataUtils.toString(admsA.getDataHoraCheckIn()).equals(DataUtils.dataTimeAgora()))
//                           {
//                         if (admsA.getFkIdServicoSolicitado().getFkIdEstadoPagamento().getDescricaoEstadoPagamento().equals("Pago"))
//                         {
                                resultado.add(admsA);
//System.out.print("Teste 0  : ambTriagemBean.pesquisarPacientes() : " + resultado.size()); 
//                         }
//                           }

//System.out.println("0:AdmsAgendamento.pesquisarPacientes() : " + DataUtils.dateToString(admsA.getDataHoraCheckIn()));
                      }
                  }
               }
           }
        }
        return resultado;
    }    
    
    public AmbTriagem seleccionarDetalhesTriagem(AmbTriagem at)
    {
        atAux = at;

//System.out.print("1:AmbTriagem.seleccionarDetalhesTriagem()\nId Triagem   : " + at.getPkIdTriagem());
//System.out.print("2:AmbTriagem.seleccionarDetalhesTriagem()\nNome         : " + at.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
//System.out.print("3:AmbTriagem.seleccionarDetalhesTriagem()\nData         : " + at.getDataHoraTriagem());
        return atAux;
    }

    public AdmsAgendamento listarTodosAgendamentos()
    {
        long codP = 0L;

        for (AdmsAgendamento admsA : admsAgendamentoFacade.findAll())
        {
            if (admsA.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente() == codigoPaciente)
            {
                codP = admsA.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getPkIdPaciente();
                admsAgendamento = admsA;
//System.out.println("0:listarTodosAgendamentos.admsAgendamento: " + admsAgendamento.toString());
            }
        }
        return admsAgendamento;
    }
   
    public List<AmbTriagem> pesquisaPaciente(String np)
    {
        List<AmbTriagem> resultado = new ArrayList<>();
        
        if (!ambTriagemFacade.findAll().isEmpty())
            for (AmbTriagem ambt : ambTriagemFacade.findAll())
                 if (ambt.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                    resultado.add(ambt);
        return resultado;
    }
    
    public List<AmbTriagemHasSinal> pesquisaPacienteTriagem(String np)
    {
        List<AmbTriagemHasSinal> resultado = new ArrayList<>();
        
        resultado.clear();
        
        if (!ambTriagemHasSinalFacade.findAll().isEmpty())
            for (AmbTriagemHasSinal ats : ambTriagemHasSinalFacade.findAll())
                 if (ats.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                     resultado.add(ats);
        
        return resultado;
    }
    
//    public List<AmbTriagem> pesquisarTriagens()
//    {
//        return ambTriagemFacade.findTriagem(ambTriagem, dataInicio, dataFinal);
//    }
    
    public List<AmbTriagemHasSinal> pesquisarTriagens()
    {
        return ambTriagemFacade.findTriagem(ambTriagemHasSinal, dataInicio, dataFinal);
    }
    
    public List<AmbTriagemHasSinal> devolveSinaisPacientesTriagem() 
    {

        List<AmbTriagemHasSinal> resultado = new ArrayList();
        
        
        if (!ambTriagemHasSinalFacade.findAll().isEmpty())
        {
            for (AmbTriagemHasSinal ats : ambTriagemHasSinalFacade.findAll()) 
            {
                for (AmbTriagem at : ambTriagemFacade.findAll()) 
                {
                    if (at.getPkIdTriagem() == ats.getFkIdTriagem().getPkIdTriagem()) 
                    {
                        for (AmbSinal as : ambSinalFacade.findAll()) 
                        {
                            if (as.getDescricao().equals(ats.getFkIdSinal().getDescricao())) 
                            {
                                ats.setFkIdSinal(as);
                                ats.setFkIdTriagem(at);
                                resultado.add(ats);
                                   
                            }
                        }
                    }
                }
            }
        }
        return resultado;
    }
    
    public List<AmbTriagemHasSinal> retornaSinais(int sinal)
    {
        ambTriagemHasSinal = new AmbTriagemHasSinal();

        List<AmbTriagemHasSinal> resultado = new ArrayList();
        
        if (!ambSinalFacade.findAll().isEmpty())
        {
            for (AmbSinal sinais : ambSinalFacade.findAll())
            {
                if (sinais.getPkIdSinal() == sinal)
                {
                    ambTriagemHasSinal.setFkIdSinal(sinais);
                    resultado.add(ambTriagemHasSinal);
                }
            }
        }
        return resultado;
    }
    
    public List<AmbTriagemHasSinal> devolverSinais()
    {
        List<AmbTriagemHasSinal> resultadoSinais = new ArrayList();
        
        int cs = 0;
        
        if (listaSinais != null)
        {
            for (int i = 0; i < listaSinais.length; i++)
            {
                for (AmbTriagemHasSinal ambTAS : retornaSinais(listaSinais[i]))
                {
                    if (ambTAS.getFkIdSinal().getPkIdSinal() == cs)
                    {
                        ambTriagemHasSinal = ambTAS;
                    }
                    resultadoSinais.add(ambTAS);
                }

                if (i < listaSinais.length - 1)
                {
                    resultadoSinais.get(i).getFkIdSinal().getDescricao();
                }
            }
        }
        return resultadoSinais;
    }
    
    public StringBuilder lerSinaisPacientesTriagem(long idTriagem) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolveSinaisPacientesTriagem().size(); i++)
             if (devolveSinaisPacientesTriagem().get(i).getFkIdTriagem().getPkIdTriagem().equals(idTriagem))
                resultado.append(devolveSinaisPacientesTriagem().get(i).getFkIdSinal().getDescricao()).append(". ");
        return resultado;
    }    
    
    public List<AmbClassificacaoDor> listarAmbClassificacaoDor()
    {
        return ambClassificacaoDorFacade.findAll();
    }
    
    public List<AdmsClassificacaoServicoSolicitado> listarEstadosPaciente()
    {
        return admsClassificacaoServicoSolicitadoFacade.findAll();
    }
    
    public List<AmbSinal> listarSinais()
    {
        return ambSinalFacade.findAll();
    }
    
//    public String getColorNotificacoes()
//    {
//        findSolicitacoes();
//        getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosConsulta();
//        getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosReconsulta();
//        getInstanciaAmbNotificacoesCriarBean().criarAmbNotificacao();
//        getInstanciaAmbDiagnosticoListarBean().findConsultasExame();
//
//        if (findSolicitacoes().isEmpty() 
//            && 
//            getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosConsulta().isEmpty()
//            && 
//            getInstanciaAmbConsultaListarBean().pesquisarPacientesEncaminhadosReconsulta().isEmpty()
//            &&
//            getInstanciaAmbDiagnosticoListarBean().findConsultasExame().isEmpty())
//        {
//            return " WHITE";
//        }
//        return "YELLOW";
//    }
    
    /*********************Fim dos métodos para pesquisas***********************/
    
    public String dataSistema()
    {
        return DataUtils.dataTimeAgoraFull();
    }
    
    public String dateToString(Date data)
    {
        if (data != null)
            return DataUtils.dateToString(data);
        return null;
    }
}
