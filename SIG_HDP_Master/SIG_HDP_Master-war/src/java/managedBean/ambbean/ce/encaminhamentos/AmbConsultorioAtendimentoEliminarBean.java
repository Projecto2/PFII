/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.encaminhamentos;

import entidade.AmbConsultorioAtendimento;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.encaminhamentos.AmbConsultorioAtendimentoCriarBean.getInstanciaAmbConsultorioAtendimento;
import sessao.AmbConsultorioAtendimentoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbConsultorioAtendimentoEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
 
    @EJB
    private AmbConsultorioAtendimentoFacade ambConsultorioAtendimentoFacade;
    
    private AmbConsultorioAtendimento ambConsultorioAtendimentoEliminar;
    
    /**
     * Creates a new instance of AmbConsultorioAtendimentoEliminarBean
     */
    public AmbConsultorioAtendimentoEliminarBean()
    {
    }

    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbConsultorioAtendimentoEliminarBean getInstanciaBean()
    {
        return (AmbConsultorioAtendimentoEliminarBean) GeradorCodigo.getInstanciaBean("ambConsultorioAtendimentoEliminarBean");
    }

    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/
    
    /******************Início dos métodos setters e getters********************/
    
    public AmbConsultorioAtendimento getAmbConsultorioAtendimentoEliminar()
    {
        return ambConsultorioAtendimentoEliminar;
    }

    public void setAmbConsultorioAtendimentoEliminar(AmbConsultorioAtendimento ambConsultorioAtendimentoEliminar)
    {
        this.ambConsultorioAtendimentoEliminar = ambConsultorioAtendimentoEliminar;
    }
    
    /*********************Fim dos métodos setters e getters********************/
    
    public void removerRegisto(AmbConsultorioAtendimento reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambConsultorioAtendimentoFacade.remove(reg);
            this.userTransaction.commit();
            Mensagem.sucessoMsg("Dados do Encaminhamento removidos com sucesso!");
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
