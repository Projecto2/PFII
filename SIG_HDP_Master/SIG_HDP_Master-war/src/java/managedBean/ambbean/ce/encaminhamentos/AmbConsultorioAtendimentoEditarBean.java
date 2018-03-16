/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.encaminhamentos;

import entidade.AmbConsultorioAtendimento;
import entidade.SupiEscala;
import entidade.SupiMedicoHasEscala;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaAmbConsultorioAtendimento;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaAmbConsultorioAtendimentoListarBean;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaSupiMedicoHasEscala;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import static managedBean.ambbean.ce.triagem.AmbTriagemEditarBean.getInstanciaAmbTriagemCriarBean;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.RhFuncionarioFacade;
import sessao.SupiMedicoHasEscalaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultorioAtendimentoEditarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private SupiMedicoHasEscalaFacade supiMedicoHasEscalaFacade;
    
    private SupiMedicoHasEscala supiMedicoHasEscala;
    
    /**
     * Creates a new instance of AmbConsultorioAtendimentoEditarBean
     */
    public AmbConsultorioAtendimentoEditarBean()
    {
    }
 
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbConsultorioAtendimentoEditarBean getInstanciaBean()
    {
        return (AmbConsultorioAtendimentoEditarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoEditarBean");
    }

    public static AmbConsultorioAtendimentoCriarBean getInstanciaAmbConsultorioAtendimentoCriarBean()
    {
        return (AmbConsultorioAtendimentoCriarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoCriarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/

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
    
    /*********************Fim dos métodos setters e getters********************/
    
    public void editarRegisto(AmbConsultorioAtendimento aca)
    {
        try
        {
            this.userTransaction.begin();
            
            if (aca.getPkIdConsultorioAtendimento() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            
            supiMedicoHasEscala = supiMedicoHasEscalaFacade.find(getInstanciaAmbConsultorioAtendimentoListarBean().getCodigoMedico());
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 0: : " + ambConsultorio.getNome());
//            ambConsultorioAtendimentoAux.setFkIdSupiSupervisorEscalaConsultorio(supiSupervisorHasEscalaHasConsultorio);
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 1: " + ambConsultorioAtendimentoAux.getFkIdSupiSupervisorEscalaConsultorio().getFkIdConsultorio().getNome());
            aca.setFkIdMedicoHasEscala(supiMedicoHasEscala);
            aca.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.out.print("ambConsultorioAtendimentoBeaneditarRegisto(): Teste 2: " + aca.getFkIdFuncionario().getFkIdPessoa().getNome()); 

            this.ambConsultorioAtendimentoFacade.edit(aca);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Dados editados com sucesso!");
            
//            aca = getInstanciaAmbConsultorioAtendimento();
            
        } catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                this.userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
}
