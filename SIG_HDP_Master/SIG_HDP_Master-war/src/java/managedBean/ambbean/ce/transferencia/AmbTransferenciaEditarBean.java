/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.transferencia;

import entidade.AmbTransferencia;
import entidade.GrlInstituicao;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.transferencia.AmbTransferenciaCriarBean.getInstanciaGrlInstituicao;
import managedBean.ambbean.ce.triagem.AmbTriagemCriarBean;
import sessao.AmbConsultorioAtendimentoFacade;
import sessao.AmbTransferenciaFacade;
import sessao.GrlInstituicaoFacade;
import sessao.RhFuncionarioFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTransferenciaEditarBean
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

    private GrlInstituicao grlInstituicao; 
    
    /**
     * Creates a new instance of AmbTransferenciaEditarBean
     */
    public AmbTransferenciaEditarBean()
    {
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbTransferenciaEditarBean getInstanciaBean()
    {
        return (AmbTransferenciaEditarBean) GeradorCodigo.getInstanciaBean("ambTransferenciaEditarBean");
    }

    public static AmbTransferenciaListarBean getInstanciaAmbTransferenciaListarBean()
    {
        return (AmbTransferenciaListarBean) GeradorCodigo.getInstanciaBean("ambTransferenciaListarBean");
    }    
    
    public static AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/
    
    public GrlInstituicao getGrlInstituicao()
    {
        if (grlInstituicao == null)
        {
            grlInstituicao = getInstanciaGrlInstituicao();
        }
        return grlInstituicao;
    }

    public void setGrlInstituicao(GrlInstituicao grlInstituicao)
    {
        this.grlInstituicao = grlInstituicao;
    }
    
    /*********************Fim dos métodos setters e getters********************/    
    
    public void editarRegisto(AmbTransferencia at)
    {
        try
        {
            this.userTransaction.begin();
            
            if (at.getPkIdTransferencia() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            
            grlInstituicao = grlInstituicaoFacade.find(getInstanciaAmbTransferenciaListarBean().getCodigoInstituicao());

            at.setFkIdInstituicao(grlInstituicao);
            at.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.out.print("ambTransferencia.editarRegisto(): Teste 1: " + aca.getFkIdFuncionario().getFkIdPessoa().getNome()); 

            this.ambTransferenciaFacade.edit(at);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Dados editados com sucesso!");
            
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
