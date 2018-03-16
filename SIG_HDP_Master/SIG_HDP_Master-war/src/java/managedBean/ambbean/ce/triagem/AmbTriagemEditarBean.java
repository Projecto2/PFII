/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.triagem;

import entidade.AmbTriagem;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import sessao.AmbTriagemFacade;
import sessao.RhFuncionarioFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTriagemEditarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    
    /**
     * Creates a new instance of AmbTriagemEditarBean
     */
    public AmbTriagemEditarBean()
    {
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbTriagemEditarBean getInstanciaBean()
    {
        return (AmbTriagemEditarBean) GeradorCodigo.getInstanciaBean("ambTriagemEditarBean");
    }

    public static AmbTriagemCriarBean getInstanciaAmbTriagemCriarBean()
    {
        return (AmbTriagemCriarBean) GeradorCodigo.getInstanciaBean("ambTriagemCriarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/

 
    /*********************Fim dos métodos setters e getters********************/
    
    public void editarRegisto(AmbTriagem at)
    {
        try
        {
            this.userTransaction.begin();           
            
            if (at.getPkIdTriagem() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }

            at.setFkIdFuncionario(rhFuncionarioFacade.find(getInstanciaAmbTriagemCriarBean().obterFuncionario().getPkIdFuncionario()));
//System.out.print("Teste 1  : " + at.toString());
            this.ambTriagemFacade.edit(at);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Triagem editada com sucesso!");
            
            limpar(at);
            
        } catch (Exception e)
        {
            try
            {
                this.userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
    
    public void limpar(AmbTriagem ambTriagem)
    {
        ambTriagem.setAltura(null);
        ambTriagem.setGlicemia(null);
        ambTriagem.setLocalDaDor(null);
        ambTriagem.setObservacoes(null);
        ambTriagem.setPeso(null);
        ambTriagem.setPulso(null);
        ambTriagem.setTemperatura(null);
    }
}
