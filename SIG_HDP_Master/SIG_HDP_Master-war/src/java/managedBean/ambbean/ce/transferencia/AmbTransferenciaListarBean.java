/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.transferencia;

import entidade.AmbConsultorioAtendimento;
import entidade.AmbTransferencia;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultorioAtendimento;
import managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoListarBean;
import static managedBean.ambbean.ce.transferencia.AmbTransferenciaCriarBean.getInstanciaAmbTransferencia;
import sessao.AmbConsultaFacade;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbTransferenciaFacade;
import sessao.GrlInstituicaoFacade;
import util.DataUtils;
import util.GeradorCodigo;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTransferenciaListarBean implements Serializable
{
    @EJB
    private AmbConsultaFacade ambConsultaFacade;    
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    @EJB
    private AmbTransferenciaFacade ambTransferenciaFacade;
    @EJB
    private GrlInstituicaoFacade grlInstituicaoFacade;
    
    private AmbConsultorioAtendimento ambConsultorioAtendimento;
    
    private AmbTransferencia ambTransferencia
                           , ambTransferenciaAux;
    
    private Date dataInicio
               , dataFinal;
    
    private int codigoInstituicao; 
    private long codigoPaciente;
    
    private String numeroPaciente;
    
    /**
     * Creates a new instance of AmbTransferenciaListarBean
     */
    public AmbTransferenciaListarBean()
    {
    }

    public static AmbConsultorioAtendimentoListarBean getInstanciaAmbConsultorioAtendimentoListarBean()
    {
        return (AmbConsultorioAtendimentoListarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoListarBean");
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

    public AmbTransferencia getAmbTransferencia()
    {
        if (ambTransferencia == null)
        {
            ambTransferencia = getInstanciaAmbTransferencia();
        }
        return ambTransferencia;
    }

    public void setAmbTransferencia(AmbTransferencia ambTransferencia)
    {
        this.ambTransferencia = ambTransferencia;
    }

    public AmbTransferencia getAmbTransferenciaAux()
    {
        if (ambTransferenciaAux == null)
        {
            ambTransferenciaAux = getInstanciaAmbTransferencia();
        }
        return ambTransferenciaAux;
    }

    public void setAmbTransferenciaAux(AmbTransferencia ambTransferenciaAux)
    {
        this.ambTransferenciaAux = ambTransferenciaAux;
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
    
    public long getCodigoPaciente()
    {
        return codigoPaciente;
    }

    public void setCodigoPaciente(long codigoPaciente)
    {
        this.codigoPaciente = codigoPaciente;
    }

    public int getCodigoInstituicao()
    {
        return codigoInstituicao;
    }

    public void setCodigoInstituicao(int codigoInstituicao)
    {
        this.codigoInstituicao = codigoInstituicao;
    }
    
    public String getNumeroPaciente()
    {
        if (numeroPaciente == null)
        {
            numeroPaciente = new String();
        }
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente)
    {
        this.numeroPaciente = numeroPaciente;
    }    
    
    public List<AmbTransferencia> findAll()
    {
        return ambTransferenciaFacade.findAll();
    }    
    
    public List<AmbTransferencia> findTransferencias()
    {
        return ambTransferenciaFacade.findTransferencias(ambTransferencia, dataInicio, dataFinal);
    }      
    
    public List<AmbTransferencia> pesquisarPacienteTransferido(String np)
    {
        List<AmbTransferencia> resultado = new ArrayList<>();
        
        resultado.clear();
        
        if (!ambTransferenciaFacade.findAll().isEmpty())
            for (AmbTransferencia at : ambTransferenciaFacade.findAll())
                 if (at.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getNumeroPaciente().equals(np.trim()))
                     resultado.add(at);
        
        return resultado;
    }    
    
    public List<GrlInstituicao> findInstituicoes()
    {
        return grlInstituicaoFacade.findAll();
    } 
    
    public List<AmbConsultorioAtendimento> findPacientesEncaminhados()
    {
        return ambConsultaFacade.findPacientesEncaminhados();
    }    
    
    public void seleccionar(AmbTransferencia aca)
    {
        ambTransferenciaAux = aca;
    }    
    
//    public AmbConsultorioAtendimento seleccionarDetalhesTransferencia(AmbConsultorioAtendimento aca)
//    {
//        ambConsultorioAtendimento = aca;
//
//System.out.print("1:AmbTransferencia.seleccionarDetalhesTransferencia()\nId Encaminhamento  : " + aca.getPkIdConsultorioAtendimento());
//System.out.print("2:AmbTransferencia.seleccionarDetalhesTransferencia()\nNome               : " + aca.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
//System.out.print("3:AmbTransferencia.seleccionarDetalhesTransferencia()\nData               : " + aca.getDataHoraCadastro());
//        return ambConsultorioAtendimento;
//    }    
    
    public AmbTransferencia seleccionarDetalhesTransferencia(AmbTransferencia at)
    {
        ambTransferenciaAux = at;

System.out.print("1:AmbTransferencia.seleccionarDetalhesTransferencia()\nId Transferência  : " + at.getPkIdTransferencia());
System.out.print("2:AmbTransferencia.seleccionarDetalhesTransferencia()\nNome              : " + at.getFkIdConsultorioAtendimento().getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
System.out.print("3:AmbTransferencia.seleccionarDetalhesTransferencia()\nData              : " + at.getDataTransferencia());
        return ambTransferenciaAux;
    }     
    
    public String dataSistema()
    {
        return DataUtils.dataTimeAgoraFull();
    }    
}
