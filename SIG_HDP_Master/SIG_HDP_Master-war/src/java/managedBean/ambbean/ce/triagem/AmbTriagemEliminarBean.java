/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.triagem;

import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.triagem.AmbTriagemCriarBean.getInstanciaAmbTriagem;
import sessao.AmbTriagemFacade;
import sessao.AmbTriagemHasSinalFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTriagemEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    
    @EJB
    private AmbTriagemFacade ambTriagemFacade;
    @EJB
    private AmbTriagemHasSinalFacade ambTriagemHasSinalFacade;
    
    private AmbTriagem ambTriagemEliminar;
    
    /**
     * Creates a new instance of AmbTriagemEliminarBean
     */
    public AmbTriagemEliminarBean()
    {
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbTriagemEliminarBean getInstanciaBean()
    {
        return (AmbTriagemEliminarBean) GeradorCodigo.getInstanciaBean("ambTriagemEliminarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/
    
    public AmbTriagem getAmbTriagemEliminar()
    {
        if (ambTriagemEliminar == null)
        {
            ambTriagemEliminar = getInstanciaAmbTriagem();
        }
        return ambTriagemEliminar;
    }

    public void setAmbTriagemEliminar(AmbTriagem ambTriagemEliminar)
    {
        this.ambTriagemEliminar = ambTriagemEliminar;
    }
    
    /*********************Fim dos métodos setters e getters********************/
    
    public void removerRegisto(AmbTriagem reg) throws SystemException
    {
        try
        {
            this.userTransaction.begin();
            
            for (AmbTriagemHasSinal ats : ambTriagemHasSinalFacade.findAll())
                if (ats.getFkIdTriagem().getPkIdTriagem().equals(reg.getPkIdTriagem()))
                    this.ambTriagemHasSinalFacade.remove(ats);
            
            this.ambTriagemFacade.remove(reg);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Triagem removida com sucesso!");
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg("Impossível remover!");
                e.printStackTrace();
                this.userTransaction.rollback();
//                Mensagem.erroMsg(e.toString());
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
//                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }
    }
}
