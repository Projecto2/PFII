/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.encaminhamentos;

import entidade.AmbConsultorio;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbTriagem;
import entidade.RhFuncionario;
import entidade.SegConta;
import entidade.SupiAtividadeMedico;
import entidade.SupiEscala;
import entidade.SupiEscalaMedico;
import entidade.SupiLocalConsulta;
import entidade.SupiMedicoHasEscala;
import entidade.SupiSeccao;
import entidade.SupiSupervisorHasEscala;
import entidade.SupiTipoEscala;
import entidade.SupiTurno;
import entidade.SupiTurnoMedico;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaRhFuncionario;
import managedBean.segbean.SegLoginBean;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbConsultorioFacade;
import sessao.AmbTriagemFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiEscalaFacade;
import sessao.SupiMedicoHasEscalaFacade;
import sessao.SupiSupervisorHasEscalaFacade;
import sessao.SupiTurnoFacade;
import util.DataUtils;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultorioAtendimentoCriarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;

    @EJB
    private AmbConsultorioFacade ambConsultorioFacade;
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private SupiEscalaFacade supiEscalaFacade;
    @EJB
    private SupiMedicoHasEscalaFacade supiMedicoHasEscalaFacade;
    @EJB
    private SupiSupervisorHasEscalaFacade supiSupervisorHasEscalaFacade;
    @EJB
    private SupiTurnoFacade supiTurnoFacade;

    private AmbConsultorioAtendimento ambConsultorioAtendimento
                                    , ambConsultorioAtendimentoAux;

    private AmbConsultorio ambConsultorio;
    private AmbTriagem ambTriagem;
    private RhFuncionario rhFuncionario;
    private SupiEscala supiEscala;
    private SupiMedicoHasEscala supiMedicoHasEscala;
    private SupiSupervisorHasEscala supiSupervisorHasEscala;
    private SupiTurno supiTurno;
    
    private int codigoMedico
              , codigoSupervisorHasEscala;
    
    private long codigoTriagem;
    
    /**
     * Creates a new instance of AmbConsultorioAtendimentoBean
     */
    public AmbConsultorioAtendimentoCriarBean()
    {
    }
    
    public static AmbConsultorioAtendimentoCriarBean getInstanciaBean()
    {
        return (AmbConsultorioAtendimentoCriarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoBean");
    }
    
    public static AmbConsultorioAtendimentoListarBean getInstanciaAmbConsultorioAtendimentoListarBean()
    {
        return (AmbConsultorioAtendimentoListarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoListarBean");
    }
    
    public AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }  
    
    public static AmbConsultorioAtendimento getInstanciaAmbConsultorioAtendimento()
    {
        AmbConsultorioAtendimento ambConsultorioAtendimento = new AmbConsultorioAtendimento();

        ambConsultorioAtendimento.setFkIdFuncionario(getInstanciaRhFuncionario());
        ambConsultorioAtendimento.setFkIdMedicoHasEscala(getInstanciaSupiMedicoHasEscala());
        ambConsultorioAtendimento.setFkIdTriagem(getInstanciaAmbTriagem());
        
        return ambConsultorioAtendimento;
    }
    
//    public static SegLoginBean obterSeLoginBean()
//    {
//        return (SegLoginBean) GeradorCodigo.getInstanciaBean("segLoginBean");
//    }
    
    public static SupiEscala getInstanciaSupiEscala()
    {
        SupiEscala supiEscala = new SupiEscala();

        supiEscala.setFkIdSeccao(new SupiSeccao());
        supiEscala.setFkIdTipoEscala(new SupiTipoEscala());
        
        return supiEscala;
    }
    
    public static SupiSupervisorHasEscala getInstanciaSupiSupervisorHasEscala()
    {
        SupiSupervisorHasEscala supiSupervisorHasEscala = new SupiSupervisorHasEscala();

        supiSupervisorHasEscala.setFkIdEscala(getInstanciaSupiEscala());
        supiSupervisorHasEscala.setFkIdFuncionario(getInstanciaRhFuncionario());
        supiSupervisorHasEscala.setFkIdTurno(new SupiTurno());

        return supiSupervisorHasEscala;
    }
    
    public static SupiMedicoHasEscala getInstanciaSupiMedicoHasEscala()
    {
        SupiMedicoHasEscala supiMedicoHasEscala = new SupiMedicoHasEscala();

        supiMedicoHasEscala.setFkIdAtividadeMedico(new SupiAtividadeMedico());
        supiMedicoHasEscala.setFkIdConsultorio(new AmbConsultorio());
        supiMedicoHasEscala.setFkIdEscalaMedico(new SupiEscalaMedico());
        supiMedicoHasEscala.setFkIdLocalConsulta(new SupiLocalConsulta());
        supiMedicoHasEscala.setFkIdMedico(getInstanciaRhFuncionario());
        supiMedicoHasEscala.setFkIdTurnoMedico(new SupiTurnoMedico());

        return supiMedicoHasEscala;
    }
    
    public AmbConsultorioAtendimento getAmbConsultorioAtendimento()
    {
        if (this.ambConsultorioAtendimento == null)
        {
            this.ambConsultorioAtendimento = getInstanciaAmbConsultorioAtendimento();
        }
        return ambConsultorioAtendimento;
    }

    public void setAmbConsultorioAtendimento(AmbConsultorioAtendimento ambConsultorioAtendimento)
    {
        this.ambConsultorioAtendimento = ambConsultorioAtendimento;
    }

    public AmbConsultorioAtendimento getAmbConsultorioAtendimentoAux()
    {
        if (this.ambConsultorioAtendimentoAux == null)
        {
            this.ambConsultorioAtendimentoAux = getInstanciaAmbConsultorioAtendimento();
        }
        return ambConsultorioAtendimentoAux;
    }

    public void setAmbConsultorioAtendimentoAux(AmbConsultorioAtendimento ambConsultorioAtendimentoAux)
    {
        this.ambConsultorioAtendimentoAux = ambConsultorioAtendimentoAux;
    }

    public AmbConsultorio getAmbConsultorio()
    {
        if (this.ambConsultorio == null)
        {
            this.ambConsultorio = new AmbConsultorio();
        }
        return ambConsultorio;
    }

    public void setAmbConsultorio(AmbConsultorio ambConsultorio)
    {
        this.ambConsultorio = ambConsultorio;
    }

    public AmbTriagem getAmbTriagem()
    {
        if (this.ambTriagem == null)
        {
            this.ambTriagem = getInstanciaAmbTriagem();
        }
        return ambTriagem;
    }

    public void setAmbTriagem(AmbTriagem ambTriagem)
    {
        this.ambTriagem = ambTriagem;
    }

    public RhFuncionario getRhFuncionario()
    {
        if (this.rhFuncionario == null)
        {
            this.rhFuncionario = getInstanciaRhFuncionario();
        }
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }

    public int getCodigoSupervisorHasEscala()
    {
        return codigoSupervisorHasEscala;
    }

    public void setCodigoSupervisorHasEscala(int codigoSupervisorHasEscala)
    {
        this.codigoSupervisorHasEscala = codigoSupervisorHasEscala;
    }

    public SupiEscala getSupiEscala()
    {
        if (supiEscala == null)
        {
            supiEscala = getInstanciaSupiEscala();
        }
        return supiEscala;
    }

    public void setSupiEscala(SupiEscala supiEscala)
    {
        this.supiEscala = supiEscala;
    }

    public SupiMedicoHasEscala getSupiMedicoHasEscala()
    {
        if (supiMedicoHasEscala == null)
        {
            supiMedicoHasEscala = getInstanciaSupiMedicoHasEscala();
        }
        return supiMedicoHasEscala;
    }

    public void setSupiMedicoHasEscala(SupiMedicoHasEscala supiMedicoHasEscala)
    {
        this.supiMedicoHasEscala = supiMedicoHasEscala;
    }

    public SupiSupervisorHasEscala getSupiSupervisorHasEscala()
    {
        if (supiSupervisorHasEscala == null)
        {
            supiSupervisorHasEscala = getInstanciaSupiSupervisorHasEscala();
        }
        return supiSupervisorHasEscala;
    }

    public void setSupiSupervisorHasEscala(SupiSupervisorHasEscala supiSupervisorHasEscala)
    {
        this.supiSupervisorHasEscala = supiSupervisorHasEscala;
    }

    public SupiTurno getSupiTurno()
    {
        if (supiTurno == null)
        {
            supiTurno = new SupiTurno();
        }
        return supiTurno;
    }

    public void setSupiTurno(SupiTurno supiTurno)
    {
        this.supiTurno = supiTurno;
    }

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
    
    public void criarRegisto() 
    {
        try
        {
            this.userTransaction.begin();
            
            ambConsultorioAtendimento = getInstanciaAmbConsultorioAtendimento();
            
            ambTriagem = ambTriagemFacade.find(getInstanciaAmbConsultorioAtendimentoListarBean().getCodigoTriagem());
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 0.0 : " + codigoTriagem);            
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 0   : " + ambTriagem.getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome());
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 1   : " + ambTriagem.getFkIdFuncionario().getFkIdPessoa().getNome());

            supiMedicoHasEscala = supiMedicoHasEscalaFacade.find(getInstanciaAmbConsultorioAtendimentoListarBean().getCodigoMedico());
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 2   : " + supiMedicoHasEscala.getFkIdMedico().getFkIdPessoa().getNome());
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 2.1 : " + supiMedicoHasEscala.getFkIdConsultorio().getNome());
        
            ambConsultorioAtendimento.setFkIdMedicoHasEscala(supiMedicoHasEscala);
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 3   : " + ambConsultorioAtendimento.getFkIdSupiSupervisorEscalaConsultorio().getFkIdConsultorio().getNome());
            ambConsultorioAtendimento.setFkIdTriagem(ambTriagem);
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 4   : " + ambConsultorioAtendimento.getFkIdTriagem().getFkIdAgendamento().getFkIdServicoSolicitado().getFkIdSolicitacao().getFkIdPaciente().getFkIdPessoa().getNome()); 
            ambConsultorioAtendimento.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 5   : " + ambConsultorioAtendimento.getFkIdFuncionario().getFkIdPessoa().getNome()); 
            ambConsultorioAtendimento.setDataHoraCadastro(DataUtils.stringToDate(getInstanciaAmbConsultorioAtendimentoListarBean().dataSistema()));
//System.out.print("ambConsultorioAtendimentoBean.criarRegisto(): Teste 6   : " + ambConsultorioAtendimento.getDataHoraCadastro());            
            
            ambConsultorioAtendimentoFacade.create(ambConsultorioAtendimento);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Paciente encaminhado com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
//                Mensagem.erroMsg(e.toString());
                this.userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }

    /// Dados referentes à Tela de Atendimento
    
    public AmbConsultorioAtendimento anterior()
    {
        AmbConsultorioAtendimento ambConsultorioAtendimento = new AmbConsultorioAtendimento();
        
        return ambConsultorioAtendimento;
    }
    
    public AmbConsultorioAtendimento proximo()
    {
        AmbConsultorioAtendimento ambConsultorioAtendimento = new AmbConsultorioAtendimento();
        
        return ambConsultorioAtendimento;
    }

    public String fechar()
    {
        return "/faces/ambVisao/ambEncaminhamentos/consultorioAtendimentoCriarAmb.xhtml?faces-redirect=true";
    }
    
    public String home()
    {
        return "/faces/ambVisao/homeAmb.xhtml?faces-redirect=true";
    }
}
