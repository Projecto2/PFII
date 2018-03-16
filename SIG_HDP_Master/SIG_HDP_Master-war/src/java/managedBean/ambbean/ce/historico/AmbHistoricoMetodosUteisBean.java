/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.historico;

import entidade.AdmsClassificacaoServicoSolicitado;
import entidade.AdmsEstadoPagamento;
import entidade.AdmsPaciente;
import entidade.AdmsServico;
import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
import entidade.AdmsSolicitacao;
import entidade.AdmsTipoSolicitacaoServico;
import entidade.AmbCidSubcategorias;
import entidade.AmbConsulta;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbDiagnosticoHipotese;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import entidade.AmbPrescricaoMedica;
import entidade.AmbPrescricaoMedicaHasProduto;
import entidade.AmbTriagem;
import entidade.DiagExameRealizado;
import entidade.FarmProduto;
import entidade.FinPrecoCategoriaServico;
import entidade.InterDoencaInternamentoPaciente;
import entidade.InterMedicacao;
import entidade.InterMedicacaoHasFarmProduto;
import entidade.InterRegistoInternamento;
import entidade.InterResultadoDoenca;
import entidade.InterTipoAlta;
import entidade.InterTipoDoencaInternamento;
import entidade.InterTituloAlta;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAdmsServicoEfetuado;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultorioAtendimento;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbDiagnosticoHipotese;
import managedBean.ambbean.ce.consultas.AmbConsultaListarBean;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaAmbDiagnostico;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaDiagExameRealizado;
import static managedBean.ambbean.ce.receitas.AmbPrescricaoMedicaCriarBean.getInstanciaAmbPrescricaoMedica;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import managedBean.ambbean.ce.triagem.AmbTriagemListarBean;
import managedBean.interbean.InterMedicacaoNovoBean;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AdmsServicoSolicitadoFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbDiagnosticoHipoteseHasDoencaFacade;
import sessao.AmbPrescricaoMedicaFacade;
import sessao.AmbPrescricaoMedicaHasProdutoFacade;
import sessao.AmbTriagemFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.InterDoencaInternamentoPacienteFacade;
import sessao.InterMedicacaoFacade;
import sessao.InterMedicacaoHasFarmProdutoFacade;
import sessao.InterTituloAltaFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbHistoricoMetodosUteisBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsServicoSolicitadoFacade admsServicoSolicitadoFacade;    
    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;    
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade; 
    @EJB
    private AmbConsultaFacade ambConsultaFacade;   
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;      
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade; 
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;     
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;   
    @EJB
    private AmbDiagnosticoHipoteseHasDoencaFacade ambDiagnosticoHipoteseHasDoencaFacade;
    @EJB
    private AmbPrescricaoMedicaFacade ambPrescricaoMedicaFacade;    
    @EJB
    private AmbPrescricaoMedicaHasProdutoFacade ambPrescricaoMedicaHasProdutoFacade;  
    @EJB
    private AmbTriagemFacade ambTriagemFacade;    
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;//para fazer o Histórico do Diagnóstico
    @EJB
    private InterDoencaInternamentoPacienteFacade interDoencaInternamentoPacienteFacade;    
    @EJB
    private InterMedicacaoFacade interMedicacaoFacade;//para fazer o Histórico do Internamento
    @EJB
    private InterMedicacaoHasFarmProdutoFacade interMedicacaoHasFarmProdutoFacade;
    @EJB
    private InterTituloAltaFacade interTituloAltaFacade;     
    
    private AdmsServicoEfetuado admsServicoEfetuado;
    private AmbDiagnostico ambDiagnostico;
    private InterMedicacao imAux;
    
    
    /**
     * Creates a new instance of AmbHistoricoMetodosUteisBean
     */
    public AmbHistoricoMetodosUteisBean()
    {
    }

    public static AmbHistoricoMetodosUteisBean getInstanciaAmbHistoricoMetodosUteisBean()
    {
        return (AmbHistoricoMetodosUteisBean) GeradorCodigo.getInstanciaBean("ambHistoricoMetodosUteisBean");
    }     
    
    public static InterMedicacaoNovoBean getInstanciaInterMedicacaoNovoBean()
    {
        return (InterMedicacaoNovoBean) GeradorCodigo.getInstanciaBean("interMedicacaoNovoBean");
    }     
    
    public static AmbConsultaListarBean getInstanciaAmbConsultaListarBean()
    {
        return (AmbConsultaListarBean) GeradorCodigo.getInstanciaBean("ambConsultaListarBean");
    }     
    
    public static AdmsServicoSolicitado getInstanciaAdmsServicoSolicitado()
    {
        AdmsServicoSolicitado ass = new AdmsServicoSolicitado();
        
        ass.setFkIdClassificacaoServicoSolicitado(new AdmsClassificacaoServicoSolicitado());
        ass.setFkIdEstadoPagamento(new AdmsEstadoPagamento());
        ass.setFkIdPrecoCategoriaServico(new FinPrecoCategoriaServico());
        ass.setFkIdRecepcionista(getInstanciaRhFuncionario());
        ass.setFkIdServico(new AdmsServico());
        ass.setFkIdSolicitacao(new AdmsSolicitacao());
        ass.setFkIdTipoSolicitacao(new AdmsTipoSolicitacaoServico());
        
        return ass;
    }     
    
    public static InterDoencaInternamentoPaciente getInstanciaInterDoencaInternamentoPaciente()
    {
        InterDoencaInternamentoPaciente interDoencaInternamentoPaciente = new InterDoencaInternamentoPaciente();
        
        interDoencaInternamentoPaciente.setFkIdCidSubcategorias(new AmbCidSubcategorias());
        interDoencaInternamentoPaciente.setFkIdFuncionario(new RhFuncionario());
        interDoencaInternamentoPaciente.setFkIdRegistoInternamento(new InterRegistoInternamento());
        interDoencaInternamentoPaciente.setFkIdResultadoDoenca(new InterResultadoDoenca());
        interDoencaInternamentoPaciente.setFkIdTipoDoencaIntenamento(new InterTipoDoencaInternamento());
        

        return interDoencaInternamentoPaciente;
    }     
    
    public static InterMedicacao getInstanciaInterMedicacao()
    {
        InterMedicacao md = new InterMedicacao();
        
        md.setFkIdRegistoInternamento(new InterRegistoInternamento());
        md.setFkIdFuncionario(new RhFuncionario());

        return md;
    }        
    
    public static InterTituloAlta getInstanciaInterTituloAlta()
    {
        InterTituloAlta ita = new InterTituloAlta();
        
        ita.setFkIdFuncionario(new RhFuncionario());
        ita.setFkIdRegistoInternamento(new InterRegistoInternamento());        
        ita.setFkIdTipoAlta(new InterTipoAlta());

        return ita;
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
    
    public InterMedicacao getImAux()
    {
        if (imAux == null)
        {
            imAux = getInstanciaInterMedicacaoNovoBean().getInstancia();
        }
        return imAux;
    }

    public void setImAux(InterMedicacao imAux)
    {
        this.imAux = imAux;
    }    
    
    public List<InterMedicacao> listarDadosInternamentoGeral(String numeroProcesso)
    {
        List<InterMedicacao> resultado = new ArrayList<>();
        
        if (!interMedicacaoFacade.findAll().isEmpty())
            for (InterMedicacao im : interMedicacaoFacade.findAll())
                 if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(numeroProcesso))
                     resultado.add(im);
         
        return resultado;
    }    
    
    public InterMedicacao listarDadosInterMedicacao(int numeroSubprocesso)
    {
        InterMedicacao resultado = getInstanciaInterMedicacao();
        
        if (!interMedicacaoFacade.findAll().isEmpty())
            for (InterMedicacao im : interMedicacaoFacade.findAll())
                 if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                     resultado = im;
         
        return resultado;
    }
    
    public InterDoencaInternamentoPaciente listarDadosInterDoencaInternamentoPacienteSubprocesso(int numeroSubprocesso)
    {
        InterDoencaInternamentoPaciente resultado = getInstanciaInterDoencaInternamentoPaciente();
        
        if (!interDoencaInternamentoPacienteFacade.findAll().isEmpty())
            for (InterDoencaInternamentoPaciente idip : interDoencaInternamentoPacienteFacade.findAll())
                 if (idip.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                     resultado = idip;
         
        return resultado;
    }       
    
    public List<DiagExameRealizado> listarDadosExameSubprocesso(int numeroSubprocesso)
    {
        List<DiagExameRealizado> resultado = new ArrayList<>();
        
        if (!diagExameRealizadoFacade.findAll().isEmpty())
            for (DiagExameRealizado der : diagExameRealizadoFacade.findAll())
                 if (der.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                     resultado.add(der);
         
        return resultado;
    }

    public List<AmbCidSubcategorias> devolverPreDiagnostico(long idDiagnosticoHipotese, int numeroSubprocesso)
    {
        List<AmbCidSubcategorias> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHipoteseHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
                 if (adhhd.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese().equals(idDiagnosticoHipotese))
                     if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         if (adhhd.getFkIdSubcategorias() != null)
                             resultado.add(adhhd.getFkIdSubcategorias());
         
        return resultado;
    }    

    public StringBuilder lerPreDiagnostico(long idDiagnosticoHipotese, int numeroSubprocesso) 
    {
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < devolverPreDiagnostico(idDiagnosticoHipotese, numeroSubprocesso).size(); i++)
             resultado.append(devolverPreDiagnostico(idDiagnosticoHipotese, numeroSubprocesso).get(i).getNome()).append(". ");
        
        return resultado;
    } 
    
    public List<String> devolverPreDiagnosticoOutros(long idDiagnosticoHipotese, int numeroSubprocesso)
    {
        List<String> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHipoteseHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
                 if (adhhd.getFkIdDiagnosticoHipotese().getPkIdDiagnosticoHipotese().equals(idDiagnosticoHipotese))
                     if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         if (adhhd.getOutros() != null)
                             resultado.add(adhhd.getOutros());
         
        return resultado;
    }    

    public StringBuilder lerPreDiagnosticoOutros(long idDiagnosticoHipotese, int numeroSubprocesso) 
    {
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < devolverPreDiagnosticoOutros(idDiagnosticoHipotese, numeroSubprocesso).size(); i++)
             resultado.append(devolverPreDiagnosticoOutros(idDiagnosticoHipotese, numeroSubprocesso).get(i)).append(". ");
        
        return resultado;
    }    
    
    public List<AmbCidSubcategorias> devolverDiagnostico(long idDiagnostico, int numeroSubprocesso)
    {
        List<AmbCidSubcategorias> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 if (adhd.getFkIdDiagnostico().getPkIdDiagnostico().equals(idDiagnostico))
                     if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         if (adhd.getFkIdSubcategorias() != null)
                             resultado.add(adhd.getFkIdSubcategorias());
         
        return resultado;
    } 
    
    public StringBuilder lerDiagnostico(long idDiagnostico, int numeroSubprocesso) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverDiagnostico(idDiagnostico, numeroSubprocesso).size(); i++)
             resultado.append(devolverDiagnostico(idDiagnostico, numeroSubprocesso).get(i).getNome()).append(". ");
        
        return resultado;
    } 
    
    public List<String> devolverDiagnosticoOutros(long idDiagnostico, int numeroSubprocesso)
    {
        List<String> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 if (adhd.getFkIdDiagnostico().getPkIdDiagnostico().equals(idDiagnostico))
                     if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         if (adhd.getOutros() != null)
                             resultado.add(adhd.getOutros());
         
        return resultado;
    } 
    
    public StringBuilder lerDiagnosticoOutros(long idDiagnostico, int numeroSubprocesso) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverDiagnosticoOutros(idDiagnostico, numeroSubprocesso).size(); i++)
             resultado.append(devolverDiagnosticoOutros(idDiagnostico, numeroSubprocesso).get(i)).append(". ");
        
        return resultado;
    }     
    
    public List<AmbCidSubcategorias> devolverDoencaInternamento(long idRegistoInternamento)
    {
        List<AmbCidSubcategorias> resultado = new ArrayList<>();
        
        if (!interDoencaInternamentoPacienteFacade.findAll().isEmpty())
            for (InterDoencaInternamentoPaciente idip : interDoencaInternamentoPacienteFacade.findAll())
                 if (idip.getFkIdRegistoInternamento().getPkIdRegistoInternamento().equals(idRegistoInternamento))
                     if (idip.getFkIdCidSubcategorias() != null)
                         resultado.add(idip.getFkIdCidSubcategorias());
         
        return resultado;
    } 
    
    public StringBuilder lerDoencaInternamento(long idRegistoInternamento) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverDoencaInternamento(idRegistoInternamento).size(); i++)
             resultado.append(devolverDoencaInternamento(idRegistoInternamento).get(i).getNome()).append(". ");
        
        return resultado;
    }
    
    public List<FarmProduto> devolverRemedio(long idConsulta, int numeroSubprocesso)
    {
        List<FarmProduto> resultado = new ArrayList<>();
        
        if (!ambPrescricaoMedicaHasProdutoFacade.findAll().isEmpty())
            for (AmbPrescricaoMedicaHasProduto apmhfp : ambPrescricaoMedicaHasProdutoFacade.findAll())
                 if (apmhfp.getFkIdPrescricaoMedica().getFkIdConsulta().getPkIdConsulta().equals(idConsulta))
                     if (apmhfp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         if (apmhfp.getFkIdFarmProduto() != null)
                             resultado.add(apmhfp.getFkIdFarmProduto());
         
        return resultado;
    } 
    
    public StringBuilder lerRemedio(long idConsulta, int numeroSubprocesso) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverRemedio(idConsulta, numeroSubprocesso).size(); i++)
             resultado.append(devolverRemedio(idConsulta, numeroSubprocesso).get(i).getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedio(idConsulta, numeroSubprocesso).get(i).getDosagem())
                      .append(Defs.STRING_VAZIA).append(devolverRemedio(idConsulta, numeroSubprocesso).get(i).getFkIdUnidadeMedida().getAbreviatura()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedio(idConsulta, numeroSubprocesso).get(i).getFkIdFormaFarmaceutica().getDescricao()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedio(idConsulta, numeroSubprocesso).get(i).getFkIdViaAdministracao().getDescricao()).append(". ").append(Defs.STRING_VAZIA);
        
        return resultado;
    } 
    
    public List<InterMedicacaoHasFarmProduto> devolverRemedioInternamentoAmbulatorio(int idMedicacao)
    {
        List<InterMedicacaoHasFarmProduto> resultado = new ArrayList<>();
        
        if (!interMedicacaoHasFarmProdutoFacade.findAll().isEmpty())
            for (InterMedicacaoHasFarmProduto imhfp : interMedicacaoHasFarmProdutoFacade.findAll())
                 if (imhfp.getFkIdMedicacao().getPkIdMedicacao().equals(idMedicacao))
                     if (imhfp.getFkIdProduto() != null)    
                         resultado.add(imhfp);
         
        return resultado;
    } 
    
    public StringBuilder lerRemedioInternamentoAmbulatorio(int idMedicacao) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverRemedioInternamentoAmbulatorio(idMedicacao).size(); i++)
             resultado.append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdProduto().getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdProduto().getDosagem())
                      .append(Defs.STRING_VAZIA).append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdProduto().getFkIdFormaFarmaceutica().getDescricao()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdProduto().getFkIdViaAdministracao().getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getFkIdHoraMedicacao().getDescricao())
                      .append(Defs.STRING_VAZIA).append(devolverRemedioInternamentoAmbulatorio(idMedicacao).get(i).getDose()).append(". ").append(Defs.STRING_VAZIA);
        
        return resultado;
    } 
    
    public InterTituloAlta listarDadosInterTituloAltaSubprocesso(int numeroSubprocesso)
    {
        InterTituloAlta resultado = getInstanciaInterTituloAlta();
        
        if (!interTituloAltaFacade.findAll().isEmpty())
            for (InterTituloAlta ita : interTituloAltaFacade.findAll())
                 if (ita.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                     resultado = ita;
         
        return resultado;
    }    
    
    public List<InterTituloAlta> devolverDadosAlta(long idRegistoInternamento)
    {
        List<InterTituloAlta> resultado = new ArrayList<>();
        
        if (!interTituloAltaFacade.findAll().isEmpty())
            for (InterTituloAlta ita : interTituloAltaFacade.findAll())
                 if (ita.getFkIdRegistoInternamento().getPkIdRegistoInternamento().equals(idRegistoInternamento))
                     resultado.add(ita);
         
        return resultado;
    } 
    
    public StringBuilder lerDadosAlta(long idRegistoInternamento) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverDadosAlta(idRegistoInternamento).size(); i++)
             resultado.append(AmbTriagemListarBean.getInstanciaBean().dateToString(devolverDadosAlta(idRegistoInternamento).get(i).getDataAlta())).append(Defs.STRING_VAZIA)
                      .append(Defs.STRING_VAZIA).append(devolverDadosAlta(idRegistoInternamento).get(i).getResumoClinico())
                      .append(Defs.STRING_VAZIA).append(devolverDadosAlta(idRegistoInternamento).get(i).getDiagnosticoAlta()).append(Defs.STRING_VAZIA)
                      .append(devolverDadosAlta(idRegistoInternamento).get(i).getObs()).append(Defs.STRING_VAZIA)
                      .append(devolverDadosAlta(idRegistoInternamento).get(i).getNumeroTituloAlta()).append(". ").append(Defs.STRING_VAZIA)
                      .append(devolverDadosAlta(idRegistoInternamento).get(i).getFkIdTipoAlta().getDescricao()).append(Defs.STRING_VAZIA);
        
        return resultado;
    }     
    
    public InterMedicacao listarDadosInterMedicacaoAmbulatorio(long idServicoSolicitado, int numeroSubprocesso, Date d)
    {
        InterMedicacao resultado = getInstanciaInterMedicacao();
        
        if (!interMedicacaoFacade.findAll().isEmpty())
        {
            for (InterMedicacao im : interMedicacaoFacade.findAll())
            {
                 if (im.getFkIdRegistoInternamento() != null)
                 {  
                     if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     {
                         resultado = im;
                     }
                     else
                     {
                          if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                          {
                              if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getDataAtendimento() != null)
                              {
                                   if (d != null)
                                   {
                                       if (DataUtils.toString(im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getDataAtendimento()).equals(DataUtils.toString(d)))
                                       {
                                           resultado = im;
                                       }
                                   }
                              }
                          }
                     }
                 }
            }
        }
        return resultado;
    }    
    
    /*******Metodos responsaveis por listar os Dados Gerais do Paciente*******/ 
    
    public AdmsServicoSolicitado listarDadosAdmsServicoSolicitado(long idServicoSolicitado)
    {
        AdmsServicoSolicitado resultado = getInstanciaAdmsServicoSolicitado();

        if (!admsServicoSolicitadoFacade.findAll().isEmpty())
            for (AdmsServicoSolicitado ass : admsServicoSolicitadoFacade.findAll())
                 if (ass.getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = ass;
         
        return resultado;
    }    
    
    public AmbTriagem listarDadosAmbTriagem(long idServicoSolicitado)
    {
        AmbTriagem resultado = getInstanciaAmbTriagem();
        
        if (!ambTriagemFacade.findAll().isEmpty())
            for (AmbTriagem ac : ambTriagemFacade.findAll())
                 if (ac.getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = ac;
         
        return resultado;
    }      
    
    public AmbConsultorioAtendimento listarDadosAmbConsultorioAtendimento(long idServicoSolicitado)
    {
        AmbConsultorioAtendimento resultado = getInstanciaAmbConsultorioAtendimento();
        
        if (!ambConsultorioAtendimentoFacade.findAll().isEmpty())
            for (AmbConsultorioAtendimento aca : ambConsultorioAtendimentoFacade.findAll())
                 if (aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = aca;
         
        return resultado;
    }     
    
    public AmbConsulta listarDadosAmbConsulta(long idServicoSolicitado)
    {
        AmbConsulta resultado = getInstanciaAmbConsulta();
        
        if (!ambConsultaFacade.findAll().isEmpty())
            for (AmbConsulta ac : ambConsultaFacade.findAll())
                 if (ac.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = ac;
         
        return resultado;
    }  
  
    public DiagExameRealizado listarDadosDiagExameRealizado(long idServicoSolicitado)
    {
        DiagExameRealizado resultado = getInstanciaDiagExameRealizado();
        
        if (!diagExameRealizadoFacade.findAll().isEmpty())
            for (DiagExameRealizado der : diagExameRealizadoFacade.findAll())
                 if (der.getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = der;
         
        return resultado;
    }    
    
    public AmbDiagnosticoHipotese listarDadosAmbDiagnosticoHipotese(long idServicoSolicitado)
    {
        AmbDiagnosticoHipotese resultado = getInstanciaAmbDiagnosticoHipotese();
        
        if (!ambDiagnosticoHipoteseFacade.findAll().isEmpty())
            for (AmbDiagnosticoHipotese adh : ambDiagnosticoHipoteseFacade.findAll())
                 if (adh.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = adh;
         
        return resultado;
    }  
    
    public AmbDiagnostico listarDadosAmbDiagnostico(long idServicoSolicitado)
    {
        AmbDiagnostico resultado = getInstanciaAmbDiagnostico();
        
        if (!ambDiagnosticoFacade.findAll().isEmpty())
            for (AmbDiagnostico ad : ambDiagnosticoFacade.findAll())
                 if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = ad;
         
        return resultado;
    }     
    
    public AmbPrescricaoMedica listarDadosAmbPrescricaoMedica(long idServicoSolicitado)
    {
        AmbPrescricaoMedica resultado = getInstanciaAmbPrescricaoMedica();
        
        if (!ambPrescricaoMedicaFacade.findAll().isEmpty())
            for (AmbPrescricaoMedica apm : ambPrescricaoMedicaFacade.findAll())
                 if (apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = apm;
         
        return resultado;
    }     
    
    public InterMedicacao listarDadosInterMedicacaoHistoricoGeral(long idServicoSolicitado)
    {
        InterMedicacao resultado = getInstanciaInterMedicacao();
        
        if (!interMedicacaoFacade.findAll().isEmpty())
            for (InterMedicacao im : interMedicacaoFacade.findAll())
                 if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = im;
         
        return resultado;
    }    
    
    public InterTituloAlta listarDadosInterTituloAltaHistoricoGeral(long idServicoSolicitado)
    {
        InterTituloAlta resultado = getInstanciaInterTituloAlta();
        
        if (!interTituloAltaFacade.findAll().isEmpty())
            for (InterTituloAlta ita : interTituloAltaFacade.findAll())
                 if (ita.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     resultado = ita;
         
        return resultado;
    }     
    
    public List<AmbCidSubcategorias> devolverPreDiagnostico(long idServicoSolicitado)
    {
        List<AmbCidSubcategorias> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHipoteseHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
                 if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     if (adhhd.getFkIdSubcategorias() != null) 
                         resultado.add(adhhd.getFkIdSubcategorias());
         
        return resultado;
    }    

    public StringBuilder lerPreDiagnostico(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < devolverPreDiagnostico(idServicoSolicitado).size(); i++)
             resultado.append(devolverPreDiagnostico(idServicoSolicitado).get(i).getNome()).append(". ");
        
        return resultado;
    }     
    
    public List<String> devolverPreDiagnosticoOutros(long idServicoSolicitado)
    {
        List<String> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHipoteseHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHipoteseHasDoenca adhhd : ambDiagnosticoHipoteseHasDoencaFacade.findAll())
                 if (adhhd.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     if (adhhd.getOutros() != null)
                         resultado.add(adhhd.getOutros());
         
        return resultado;
    } 
    
    public StringBuilder lerPreDiagnosticoOutros(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverPreDiagnosticoOutros(idServicoSolicitado).size(); i++)
             resultado.append(devolverPreDiagnosticoOutros(idServicoSolicitado).get(i)).append(". ");
        
        return resultado;
    }    
    
    public List<AmbCidSubcategorias> devolverDiagnostico(long idServicoSolicitado)
    {
        List<AmbCidSubcategorias> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     if (adhd.getFkIdSubcategorias() != null) 
                         resultado.add(adhd.getFkIdSubcategorias());
         
        return resultado;
    }    

    public StringBuilder lerDiagnostico(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < devolverDiagnostico(idServicoSolicitado).size(); i++)
             resultado.append(devolverDiagnostico(idServicoSolicitado).get(i).getNome()).append(". ");
        
        return resultado;
    } 
    
    public List<String> devolverDiagnosticoOutros(long idServicoSolicitado)
    {
        List<String> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoHasDoencaFacade.findAll().isEmpty())
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                 if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     if (adhd.getOutros() != null)
                         resultado.add(adhd.getOutros());
         
        return resultado;
    } 
    
    public StringBuilder lerDiagnosticoOutros(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverDiagnosticoOutros(idServicoSolicitado).size(); i++)
             resultado.append(devolverDiagnosticoOutros(idServicoSolicitado).get(i)).append(". ");
        
        return resultado;
    }  
    
    public List<AmbPrescricaoMedicaHasProduto> devolverRemedio(long idServicoSolicitado)
    {
        List<AmbPrescricaoMedicaHasProduto> resultado = new ArrayList<>();
        
        if (!ambPrescricaoMedicaHasProdutoFacade.findAll().isEmpty())
            for (AmbPrescricaoMedicaHasProduto apmhfp : ambPrescricaoMedicaHasProdutoFacade.findAll())
                 if (apmhfp.getFkIdPrescricaoMedica().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     if (apmhfp.getFkIdFarmProduto() != null)
                         resultado.add(apmhfp);
         
        return resultado;
    }     
    
    public StringBuilder lerRemedio(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverRemedio(idServicoSolicitado).size(); i++)
             resultado.append(devolverRemedio(idServicoSolicitado).get(i).getFkIdFarmProduto().getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedio(idServicoSolicitado).get(i).getFkIdFarmProduto().getDosagem())
                      .append(Defs.STRING_VAZIA).append(devolverRemedio(idServicoSolicitado).get(i).getFkIdFarmProduto().getFkIdUnidadeMedida().getAbreviatura()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedio(idServicoSolicitado).get(i).getFkIdFarmProduto().getFkIdFormaFarmaceutica().getDescricao()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedio(idServicoSolicitado).get(i).getFkIdFarmProduto().getFkIdViaAdministracao().getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedio(idServicoSolicitado).get(i).getFkIdHoraMedicacao().getDescricao())
                      .append(Defs.STRING_VAZIA).append(devolverRemedio(idServicoSolicitado).get(i).getQuantidade()).append(".").append(Defs.STRING_VAZIA);
        
        return resultado;
    }
  
    public DiagExameRealizado listarDadosDiagExameRealizadoAmbulatorio(long idServicoSolicitado, int numeroSubprocesso, Date d)
    {
        DiagExameRealizado resultado = getInstanciaDiagExameRealizado();
        
        if (!diagExameRealizadoFacade.findAll().isEmpty())
        {
            for (DiagExameRealizado der : diagExameRealizadoFacade.findAll())
            {
                 if (der.getFkIdServicoSolicitado() != null)
                 {  
                     if (der.getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     {
                         resultado = der;
                     }
                     else
                     {
                          if (der.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                          {
                              if (der.getFkIdServicoSolicitado().getDataAtendimento() != null)
                              {
                                   if (d != null)
                                   {
                                       if (DataUtils.toString(der.getFkIdServicoSolicitado().getDataAtendimento()).equals(DataUtils.toString(d)))
                                       {
                                           resultado = der;
                                       }
                                   }
                              }
                          }
                     }
                 }
            }
        }
        return resultado;
    }    
    
    public InterMedicacao listarDadosInterMedicacao(String numeroProcesso, int numeroSubprocesso)
    {
        InterMedicacao resultado = getInstanciaInterMedicacao();
        
        if (!interMedicacaoFacade.findAll().isEmpty())
            for (InterMedicacao im : interMedicacaoFacade.findAll())
                 if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(numeroProcesso))
                     if (im.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         resultado = im;
                         
        return resultado;
    }    
    
    public List<FarmProduto> devolverRemedioInternamento(long idServicoSolicitado)
    {
        List<FarmProduto> resultado = new ArrayList<>();
        
        if (!interMedicacaoHasFarmProdutoFacade.findAll().isEmpty())
            for (InterMedicacaoHasFarmProduto imhp : interMedicacaoHasFarmProdutoFacade.findAll())
                if (imhp.getFkIdMedicacao().getFkIdRegistoInternamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                    if (imhp.getFkIdProduto() != null)
                        resultado.add(imhp.getFkIdProduto());
         
        return resultado;
    } 
    
    public StringBuilder lerRemedioInternamento(long idServicoSolicitado) 
    {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < devolverRemedioInternamento(idServicoSolicitado).size(); i++)
             resultado.append(devolverRemedioInternamento(idServicoSolicitado).get(i).getDescricao()).append(Defs.STRING_VAZIA).append(devolverRemedioInternamento(idServicoSolicitado).get(i).getDosagem())
                      .append(Defs.STRING_VAZIA).append(devolverRemedioInternamento(idServicoSolicitado).get(i).getFkIdUnidadeMedida().getAbreviatura()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedioInternamento(idServicoSolicitado).get(i).getFkIdFormaFarmaceutica().getDescricao()).append(Defs.STRING_VAZIA)
                      .append(devolverRemedioInternamento(idServicoSolicitado).get(i).getFkIdViaAdministracao().getDescricao()).append(".").append(Defs.STRING_VAZIA);
        
        return resultado;
    } 
    
    public InterTituloAlta listarDadosInterTituloAlta(String numeroProcesso, int numeroSubprocesso/*, Date d*/)
    {
        InterTituloAlta resultado = getInstanciaInterTituloAlta();
        
        if (!interTituloAltaFacade.findAll().isEmpty())
            for (InterTituloAlta ita : interTituloAltaFacade.findAll())
                 if (ita.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(numeroProcesso))
                     if (ita.getFkIdRegistoInternamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         resultado = ita;

        return resultado;
    }   
    
    public AmbPrescricaoMedica listarDadosAmbPrescricaoMedica(long idServicoSolicitado, int numeroSubprocesso, Date d)
    {
        AmbPrescricaoMedica resultado = getInstanciaAmbPrescricaoMedica();
        
        if (!ambPrescricaoMedicaFacade.findAll().isEmpty())
        {
            for (AmbPrescricaoMedica apm : ambPrescricaoMedicaFacade.findAll())
            {
                 if (apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado() != null)
                 {  
                     if (apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getPkIdServicoSolicitado().equals(idServicoSolicitado))
                     {
                         resultado = apm;
                     }
                     else
                     {
                          if (apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                          {
                              if (apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getDataAtendimento() != null)
                              {
                                   if (d != null)
                                   {
                                       if (DataUtils.toString(apm.getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getDataAtendimento()).equals(DataUtils.toString(d)))
                                       {
                                           resultado = apm;
                                       }
                                   }
                              }
                          }
                     }
                 }
            }
        }
        return resultado;
    }    
    
    /* Métodos responsáveis por capturar os valores de determinados Objectos */
    
    public AmbDiagnostico seleccionarDetalhesConsulta(AmbDiagnostico ad)
    {
        ambDiagnostico = ad;
System.out.print("1:AmbHistoricoMetodosUteisBean.seleccionarDetalhesConsulta()\nId Triagem   : " + ad.getPkIdDiagnostico());
System.out.print("2:AmbHistoricoMetodosUteisBean.seleccionarDetalhesConsulta()\nNome         : " + ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
System.out.print("3:AmbHistoricoMetodosUteisBean.seleccionarDetalhesConsulta()\nData         : " + ad.getDataHoraDiagnostico());
        return ambDiagnostico;
    }
    
    public AdmsServicoEfetuado seleccionarDetalhesServicoEfetuado(AdmsServicoEfetuado ase)
    {
        admsServicoEfetuado = ase;
System.out.print("1:AmbHistoricoMetodosUteisBean.seleccionarDetalhesServicoEfetuado()\nId Triagem   : " + ase.getPkIdServicoEfetuado());
System.out.print("2:AmbHistoricoMetodosUteisBean.seleccionarDetalhesServicoEfetuado()\nNome         : " + ase.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
System.out.print("3:AmbHistoricoMetodosUteisBean.seleccionarDetalhesServicoEfetuado()\nData         : " + ase.getDataEfetuada());
        return admsServicoEfetuado;
    }    
}

