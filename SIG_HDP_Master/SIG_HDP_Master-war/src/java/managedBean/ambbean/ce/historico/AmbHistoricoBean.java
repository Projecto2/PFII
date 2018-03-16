/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.historico;

import entidade.AdmsPaciente;
import entidade.AdmsServicoEfetuado;
import entidade.AmbDiagnostico;
import entidade.GrlEndereco;
import entidade.GrlPessoa;
import entidade.InterMedicacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAdmsPaciente;
import sessao.AdmsAgendamentoFacade;
import sessao.AdmsServicoEfetuadoFacade;
import sessao.AmbConsultaFacade;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHipoteseFacade;
import sessao.DiagExameRealizadoFacade;
import sessao.InterMedicacaoFacade;
import sessao.InterRegistoInternamentoFacade;
import util.GeradorCodigo;
import util.amb.Defs;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbHistoricoBean implements Serializable
{
    @EJB
    private AdmsAgendamentoFacade admsAgendamentoFacade;
    @EJB
    private AdmsServicoEfetuadoFacade admsServicoEfetuadoFacade;     
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade; 
    @EJB
    private AmbDiagnosticoHipoteseFacade ambDiagnosticoHipoteseFacade;
    @EJB
    private AmbConsultaFacade ambConsultaFacade;    
    @EJB
    private DiagExameRealizadoFacade diagExameRealizadoFacade;//para fazer o Histórico do Diagnóstico
    @EJB
    private InterMedicacaoFacade interMedicacaoFacade;//para fazer o Histórico do Internamento

    
    private AdmsPaciente admsPacienteHistoricoGeral
                       , admsPacienteHistoricoNome
                       , admsPacienteHistoricoNomeSubprocesso
                       , admsPacienteHistoricoProcessoSubprocesso;
    
    private InterMedicacao interMedicacao
                         , imAux;
    
    private int numeroSubprocesso
              , numeroSubprocessoNome;
    
    private String nome
                 , nomeSubprocesso
                 , nomeDoMeio
                 , nomeDoMeioSubprocesso
                 , sobreNome
                 , sobreNomeSubprocesso;
    
    /**
     * Creates a new instance of AmbHistoricoBean
     */
    public AmbHistoricoBean()
    {
    }
    
    public static AmbHistoricoBean getInstanciaBean()
    {
        return (AmbHistoricoBean) GeradorCodigo.getInstanciaBean("ambHistoricoBean");
    } 
    
    public static AdmsPaciente getInstanciaAdmsPaciente()
    {
        AdmsPaciente admsPaciente = new AdmsPaciente();
        
        admsPaciente.setFkIdEnderecoTemporario(new GrlEndereco());
        admsPaciente.setFkIdPessoa(new GrlPessoa());
        
        return admsPaciente;
    }    
    
    public AdmsPaciente getAdmsPacienteHistoricoGeral()
    {
        if (admsPacienteHistoricoGeral == null)
        {
            admsPacienteHistoricoGeral = getInstanciaAdmsPaciente();
        }
        return admsPacienteHistoricoGeral;
    }

    public void setAdmsPacienteHistoricoGeral(AdmsPaciente admsPacienteHistoricoGeral)
    {
        this.admsPacienteHistoricoGeral = admsPacienteHistoricoGeral;
    }    

    public AdmsPaciente getAdmsPacienteHistoricoNome()
    {
        if (admsPacienteHistoricoNome == null)
        {
            admsPacienteHistoricoNome = getInstanciaAdmsPaciente();
        }        
        return admsPacienteHistoricoNome;
    }

    public void setAdmsPacienteHistoricoNome(AdmsPaciente admsPacienteHistoricoNome)
    {
        this.admsPacienteHistoricoNome = admsPacienteHistoricoNome;
    }

    public AdmsPaciente getAdmsPacienteHistoricoNomeSubprocesso()
    {
        if (admsPacienteHistoricoNomeSubprocesso == null)
        {
            admsPacienteHistoricoNomeSubprocesso = getInstanciaAdmsPaciente();
        }        
        return admsPacienteHistoricoNomeSubprocesso;
    }

    public void setAdmsPacienteHistoricoNomeSubprocesso(AdmsPaciente admsPacienteHistoricoNomeSubprocesso)
    {
        this.admsPacienteHistoricoNomeSubprocesso = admsPacienteHistoricoNomeSubprocesso;
    }
    
    public AdmsPaciente getAdmsPacienteHistoricoProcessoSubprocesso()
    {
        if (admsPacienteHistoricoProcessoSubprocesso == null)
        {
            admsPacienteHistoricoProcessoSubprocesso = getInstanciaAdmsPaciente();
        }
        return admsPacienteHistoricoProcessoSubprocesso;
    }

    public void setAdmsPacienteHistoricoProcessoSubprocesso(AdmsPaciente admsPacienteHistoricoProcessoSubprocesso)
    {
        this.admsPacienteHistoricoProcessoSubprocesso = admsPacienteHistoricoProcessoSubprocesso;
    }    

    public int getNumeroSubprocesso()
    {
        return numeroSubprocesso;
    }

    public void setNumeroSubprocesso(int numeroSubprocesso)
    {
        this.numeroSubprocesso = numeroSubprocesso;
    }

    public int getNumeroSubprocessoNome()
    {
        return numeroSubprocessoNome;
    }

    public void setNumeroSubprocessoNome(int numeroSubprocessoNome)
    {
        this.numeroSubprocessoNome = numeroSubprocessoNome;
    }

    public String getNome()
    {
        if (nome == null)
        {
            nome = new String();
        }
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getNomeSubprocesso()
    {
        if (nomeSubprocesso == null)
        {
            nomeSubprocesso = new String();
        }
        return nomeSubprocesso;
    }

    public void setNomeSubprocesso(String nomeSubprocesso)
    {
        this.nomeSubprocesso = nomeSubprocesso;
    }

    public String getNomeDoMeio()
    {
        if (nomeDoMeio == null)
        {
            nomeDoMeio = new String();
        }
        return nomeDoMeio;
    }

    public void setNomeDoMeio(String nomeDoMeio)
    {
        this.nomeDoMeio = nomeDoMeio;
    }

    public String getNomeDoMeioSubprocesso()
    {
        if (nomeDoMeioSubprocesso == null)
        {
            nomeDoMeioSubprocesso = new String();
        }
        return nomeDoMeioSubprocesso;
    }

    public void setNomeDoMeioSubprocesso(String nomeDoMeioSubprocesso)
    {
        this.nomeDoMeioSubprocesso = nomeDoMeioSubprocesso;
    }

    public String getSobreNome()
    {
        if (sobreNome == null)
        {
            sobreNome = new String();
        }
        return sobreNome;
    }

    public void setSobreNome(String sobreNome)
    {
        this.sobreNome = sobreNome;
    }

    public String getSobreNomeSubprocesso()
    {
        if (sobreNomeSubprocesso == null)
        {
            sobreNomeSubprocesso = new String();
        }
        return sobreNomeSubprocesso;
    }

    public void setSobreNomeSubprocesso(String sobreNomeSubprocesso)
    {
        this.sobreNomeSubprocesso = sobreNomeSubprocesso;
    }
    
    public boolean verificaAmbulatorio(String valor)
    {
        return !valor.equals("Consulta");
    }
    
    public boolean verificaDiagnostico(String valor)
    {
        return !valor.equals("Exame");
    }    
    
    public boolean verificaInternamento(String valor)
    {
        return !valor.equals("Internamento");
    }     
    
    public List<AdmsServicoEfetuado> listar(String np)
    {
        List<AdmsServicoEfetuado> resultado = new ArrayList<>();

        if (!admsServicoEfetuadoFacade.findAll().isEmpty())
            for (AdmsServicoEfetuado ase : admsServicoEfetuadoFacade.findAll())
                 if (ase.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                     resultado.add(ase);

        return resultado;
    }       
    
    public List<AdmsServicoEfetuado> listarNome(String nome, String nomeDoMeio, String sobreNome)
    {
        List<AdmsServicoEfetuado> resultado = new ArrayList<>();

        if (!admsServicoEfetuadoFacade.findAll().isEmpty())
            for (AdmsServicoEfetuado ase : admsServicoEfetuadoFacade.findAll())
                 if (ase.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().equals(nome.trim()))
                     if (ase.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().equals(nomeDoMeio.trim()))
                         if (ase.getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().equals(sobreNome.trim()))
                             resultado.add(ase);
        
        return resultado;
    }      
    
    public List<AmbDiagnostico> listarProcessoSubprocesso(String np, int numeroSubprocesso)
    {
        List<AmbDiagnostico> resultado = new ArrayList<>();

        if (!ambDiagnosticoFacade.findAll().isEmpty())
            for (AmbDiagnostico ad : ambDiagnosticoFacade.findAll())
                 if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                       .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                     if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                           .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                         resultado.add(ad);
        
        return resultado;
    }
    
    public List<AmbDiagnostico> listarNomeSubprocesso(String nome, String nomeDoMeio, String sobreNome, int numeroSubprocesso)
    {
        List<AmbDiagnostico> resultado = new ArrayList<>();
        
        if (!ambDiagnosticoFacade.findAll().isEmpty())
            for (AmbDiagnostico ad : ambDiagnosticoFacade.findAll())
                 if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                       .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome().equals(nome.trim()))
                     if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                           .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().equals(nomeDoMeio.trim()))
                         if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                               .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getSobreNome().equals(sobreNome.trim()))
                             if (ad.getFkIdDiagnosticoHipotese().getFkIdConsulta().getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento()
                                   .getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdSubprocesso().getNumeroSubprocesso().equals(numeroSubprocesso))
                                 resultado.add(ad);
        
        return resultado;
    }     
}
