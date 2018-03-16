/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.transferencia;

import entidade.AmbConsultorioAtendimento;
import entidade.AmbTransferencia;
import entidade.GrlContacto;
import entidade.GrlEndereco;
import entidade.GrlInstituicao;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.consultas.AmbConsultaCriarBean.getInstanciaAmbConsultorioAtendimento;
import static managedBean.ambbean.ce.diagnosticos.AmbDiagnosticoListarBean.getInstanciaAmbDiagnosticoHasDoenca;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbTransferenciaFacade;
import sessao.GrlInstituicaoFacade;
import sessao.RhFuncionarioFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTransferenciaCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction; 
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;     
    @EJB
    private AmbTransferenciaFacade ambTransferenciaFacade;
    @EJB
    private GrlInstituicaoFacade grlInstituicaoFacade; 
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;    
    
    private AmbConsultorioAtendimento ambConsultorioAtendimento; 
    private AmbTransferencia ambTransferencia;
    private GrlInstituicao grlInstituicao; 

    /**
     * Creates a new instance of AmbTransferenciaCriarBean
     */
    public AmbTransferenciaCriarBean()
    {
    }
    
    public static AmbTransferenciaCriarBean getInstanciaBean()
    {
        return (AmbTransferenciaCriarBean) GeradorCodigo.getInstanciaBean("ambTransferenciaCriarBean");
    }
    
    public static AmbTransferenciaListarBean getInstanciaAmbTransferenciaListarBean()
    {
        return (AmbTransferenciaListarBean) GeradorCodigo.getInstanciaBean("ambTransferenciaListarBean");
    }
    
    public AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    } 
   
    public static AmbTransferencia getInstanciaAmbTransferencia()
    {
        AmbTransferencia ambAmbTransferencia = new AmbTransferencia();

        ambAmbTransferencia.setFkIdConsultorioAtendimento(getInstanciaAmbConsultorioAtendimento());
        ambAmbTransferencia.setFkIdFuncionario(getInstanciaRhFuncionario());
        ambAmbTransferencia.setFkIdInstituicao(getInstanciaGrlInstituicao());
        
        return ambAmbTransferencia;
    }    
    
    public static GrlInstituicao getInstanciaGrlInstituicao()
    {
        GrlInstituicao grlInstituicao = new GrlInstituicao();

        grlInstituicao.setFkIdContacto(new GrlContacto());
        grlInstituicao.setFkIdEndereco(new GrlEndereco());
        
        return grlInstituicao;
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

    public GrlInstituicao getGrlInstituicao()
    {
        if (grlInstituicao == null)
        {
            grlInstituicao = new GrlInstituicao();
        }
        return grlInstituicao;
    }

    public void setGrlInstituicao(GrlInstituicao grlInstituicao)
    {
        this.grlInstituicao = grlInstituicao;
    }
    
    public void criarRegisto() 
    {
        try
        {
            this.userTransaction.begin();
//            
            ambTransferencia = getInstanciaAmbTransferencia();
//            
            ambConsultorioAtendimento = ambConsultorioAtendimentoFacade.find(getInstanciaAmbTransferenciaListarBean().getCodigoPaciente());
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 0.0 : " + codigoTriagem);            
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 0   : " + ambTriagem.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 1   : " + ambTriagem.getFkIdFuncionario().getFkIdPessoa().getNome());
//
            grlInstituicao = grlInstituicaoFacade.find(getInstanciaAmbTransferenciaListarBean().getCodigoInstituicao());
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 2   : " + supiMedicoHasEscala.getFkIdMedico().getFkIdPessoa().getNome());
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 2.1 : " + supiMedicoHasEscala.getFkIdConsultorio().getNome());
//        
            ambTransferencia.setFkIdConsultorioAtendimento(ambConsultorioAtendimento);
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 3   : " + ambConsultorioAtendimento.getFkIdSupiSupervisorEscalaConsultorio().getFkIdConsultorio().getNome());
            ambTransferencia.setFkIdInstituicao(grlInstituicao);
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 4   : " + ambConsultorioAtendimento.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()); 
            ambTransferencia.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 5   : " + ambConsultorioAtendimento.getFkIdFuncionario().getFkIdPessoa().getNome()); 
            ambTransferencia.setDataTransferencia(DataUtils.stringToDate(getInstanciaAmbTransferenciaListarBean().dataSistema()));
////System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 6   : " + ambConsultorioAtendimento.getDataHoraCadastro());            
//            
            ambTransferenciaFacade.create(ambTransferencia);
//            
            this.userTransaction.commit();
//            
            Mensagem.sucessoMsg("Paciente transferido com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                this.userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    public String fechar()
    {
        return "/faces/ambVisao/ambTransferencia/TransferenciaCriarAmb.xhtml?faces-redirect=true";
    }    
}
