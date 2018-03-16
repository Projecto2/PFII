/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.diagnosticos;

import entidade.AmbCidSubcategorias;
import entidade.AmbConsulta;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.AmbObservacoesMedicas;
import entidade.DiagExameRealizado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsulta;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaAmbDiagnostico;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoCriarBean.getInstanciaDiagExameRealizado;
import sessao.AmbCidAgrupamentosFacade;
import sessao.AmbCidCapitulosFacade;
import sessao.AmbCidCategoriasFacade;
import sessao.AmbCidConfiguracoesFacade;
import sessao.AmbCidDoencasPrioridadesFacade;
import sessao.AmbCidPerfisFacade;
import sessao.AmbCidSubcategoriasFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.AmbObservacoesMedicasFacade;
import sessao.AmbTriagemFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.GrlEspecialidadeFacade;
import sessao.RhProfissaoFacade;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbDiagnosticoListarBean implements Serializable
{
    @EJB
    private AmbCidAgrupamentosFacade ambCidAgrupamentosFacade;
    @EJB
    private AmbCidCapitulosFacade ambCidCapitulosFacade;
    @EJB
    private AmbCidCategoriasFacade ambCidCategoriasFacade;
    @EJB
    private AmbCidConfiguracoesFacade ambCidConfiguracoesFacade;
    @EJB
    private AmbConsultaFacade ambConsultaFacade;  
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;      
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;    
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;    
    @EJB
    private AmbCidDoencasPrioridadesFacade ambCidDoencasPrioridadesFacade;
    @EJB
    private AmbCidPerfisFacade ambCidPerfisFacade;    
    @EJB
    private AmbCidSubcategoriasFacade ambCidSubcategoriasFacade;
    @EJB
    private AmbObservacoesMedicasFacade ambObservacoesMedicasFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;    
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;    
    @EJB
    private GrlEspecialidadeFacade grlEspecialidadeFacade;    
    @EJB
    private RhProfissaoFacade rhProfissaoFacade;    
   
//    private AdmsPaciente admsPaciente;    
    private AmbConsulta acAux;
    private AmbDiagnostico ambDiagnostico; 
    private AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca;
    private Date dataInicio
               , dataFinal;
    private DiagExameRealizado diagExameRealizado;
    
    private int codigoCentroHospitalar
              , codigoExameRealizado 
              , codigoObservacoesMedicas;
    
    /**
     * Creates a new instance of AmbDiagnosticoListarBean
     */
    public AmbDiagnosticoListarBean()
    {
    }
    
    public static AmbDiagnosticoListarBean getInstanciaBean()
    {
        return (AmbDiagnosticoListarBean) GeradorCodigo.getInstanciaBean("ambDiagnosticoListarBean");
    }
    
    public static AmbDiagnosticoHasDoenca getInstanciaAmbDiagnosticoHasDoenca()
    {
        AmbDiagnosticoHasDoenca ambDiagnosticoHasDoenca = new AmbDiagnosticoHasDoenca();
        
        ambDiagnosticoHasDoenca.setFkIdDiagnostico(getInstanciaAmbDiagnostico());
        ambDiagnosticoHasDoenca.setFkIdSubcategorias(new AmbCidSubcategorias());

        return ambDiagnosticoHasDoenca;
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
    
    public AmbDiagnostico getAmbDiagnostico()
    {
        if (ambDiagnostico == null)
        {
            ambDiagnostico = AmbDiagnosticoCriarBean.getInstanciaAmbDiagnostico();
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
    
    public Date getDataInicio()
    {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
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
    
    public DiagExameRealizado getDiagExameRealizado()
    {
        if (diagExameRealizado == null)
        {
            diagExameRealizado = AmbDiagnosticoCriarBean.getInstanciaDiagExameRealizado();
        }
        return diagExameRealizado;
    }

    public void setDiagExameRealizado(DiagExameRealizado diagExameRealizado)
    {
        this.diagExameRealizado = diagExameRealizado;
    }
    
    public int getCodigoCentroHospitalar()
    {
        return codigoCentroHospitalar;
    }

    public void setCodigoCentroHospitalar(int codigoCentroHospitalar)
    {
        this.codigoCentroHospitalar = codigoCentroHospitalar;
    }    
    
    public int getCodigoExameRealizado()
    {
        return codigoExameRealizado;
    }

    public void setCodigoExameRealizado(int codigoExameRealizado)
    {
        this.codigoExameRealizado = codigoExameRealizado;
    }

    public int getCodigoObservacoesMedicas()
    {
        return codigoObservacoesMedicas;
    }

    public void setCodigoObservacoesMedicas(int codigoObservacoesMedicas)
    {
        this.codigoObservacoesMedicas = codigoObservacoesMedicas;
    }
    
    public List<AmbObservacoesMedicas> listarObservacoesMedicas()
    {
        return ambObservacoesMedicasFacade.findAll();
    }
    
    public List<AmbDiagnosticoHasDoenca> findPacientesDiagnosticados()
    {
        return ambDiagnosticoFacade.findPacientesDiagnosticados();
    }    
    
    public List<DiagExameRealizado> findConsultasExame()
    {
        return ambDiagnosticoFacade.findConsultasExame();
    }
    
    public List<AmbDiagnosticoHasDoenca> pesquisarDiagnosticos()
    {
        return ambDiagnosticoFacade.findDiagnosticos(ambDiagnosticoHasDoenca, dataInicio, dataFinal);
    }
    
    public DiagExameRealizado atualizarDadosPacientesConsulta() 
    {
        DiagExameRealizado diagExame = getInstanciaDiagExameRealizado();
        
        for (DiagExameRealizado der : diagExameRealizadoFacade.findAll()) 
        {
            if (der != null)
            {
                if (der.getPkIdExameRealizado() == codigoExameRealizado) 
                {
                    diagExameRealizado = der;
                    diagExameRealizado.setPkIdExameRealizado(codigoExameRealizado);
                    diagExame = diagExameRealizado;
                }
            }
        }
        return diagExame;
    }
    
    public List<AmbDiagnosticoHasDoenca> pesquisaDePacienteDiagnosticado(String np)
    {
        List<AmbDiagnosticoHasDoenca> resultado = new ArrayList<>();

        for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
            if (adhd != null)
                if (adhd.getFkIdDiagnostico().getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                    resultado.add(adhd);
        return resultado;
    }    
    
    public AmbConsulta seleccionarDetalhesConsulta(AmbConsulta ac)
    {
        acAux = ac;
        return acAux;
    }
    
    public AmbDiagnostico seleccionarDetalhesDiagnostico(AmbDiagnostico ad)
    {
        ambDiagnostico = ad;
        return ambDiagnostico;
    }    
    
    public List<AmbDiagnosticoHasDoenca> retornaDoencas(String doenca)
    {
        ambDiagnosticoHasDoenca = getInstanciaAmbDiagnosticoHasDoenca();

        List<AmbDiagnosticoHasDoenca> resultado = new ArrayList();

        for (AmbCidSubcategorias acs : ambCidSubcategoriasFacade.findAll())
        {
            if (acs != null)
            {
                if (acs.getPkIdSubcategorias().equals(doenca))
                {
                    ambDiagnosticoHasDoenca.setFkIdSubcategorias(acs);
                    resultado.add(ambDiagnosticoHasDoenca);
                }
            }
        }
        return resultado;
    }

    public List<AmbDiagnosticoHasDoenca> devolverDoencas()
    {
        List<AmbDiagnosticoHasDoenca> resultado = new ArrayList();

        String cd = null;

        List<String> listaPkIdSubcategoriasDasDoencasSeleccionadas = AmbDiagnosticoBean.getInstanciaBean().getListaPkIdSubcategoriasDasDoencasSeleccionadas();

        if (listaPkIdSubcategoriasDasDoencasSeleccionadas != null)
        {
            for (int i = 0; i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size(); i++)
            {
                for (AmbDiagnosticoHasDoenca adhd : retornaDoencas(listaPkIdSubcategoriasDasDoencasSeleccionadas.get(i)))
                {
                    if (adhd.getFkIdSubcategorias().getPkIdSubcategorias().equals(cd))
                    {
                        ambDiagnosticoHasDoenca = adhd;
                    }
                    resultado.add(adhd);
                }

                if (i < listaPkIdSubcategoriasDasDoencasSeleccionadas.size() - 1)
                {
                    resultado.get(i).getFkIdSubcategorias().getNome();
                }
            }
        }
        return resultado;
    }    
}
