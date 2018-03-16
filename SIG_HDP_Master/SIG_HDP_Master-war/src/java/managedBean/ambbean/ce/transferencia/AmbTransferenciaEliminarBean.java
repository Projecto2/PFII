/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.transferencia;

import entidade.AmbTransferencia;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AmbTransferenciaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbTransferenciaEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
    @EJB
    private AmbTransferenciaFacade ambTransferenciaFacade;
    
    private AmbTransferencia AmbTransferenciaEliminar;    
    
    
    /**
     * Creates a new instance of AmbTransferenciaBean
     */
    public AmbTransferenciaEliminarBean()
    {
    }
    
    /*****Início dos métodos resposáveis por chamar uma Bean pelo contexto*****/
    
    public static AmbTransferenciaEliminarBean getInstanciaBean()
    {
        return (AmbTransferenciaEliminarBean) GeradorCodigo.getInstanciaBean("ambTransferenciaEliminarBean");
    }
    
    /*******Fim dos métodos resposáveis por chamar uma Bean pelo contexto******/

    /******************Início dos métodos setters e getters********************/
    
    public AmbTransferencia getAmbTransferenciaEliminar()
    {
        return AmbTransferenciaEliminar;
    }

    public void setAmbTransferenciaEliminar(AmbTransferencia AmbTransferenciaEliminar)
    {
        this.AmbTransferenciaEliminar = AmbTransferenciaEliminar;
    }
    
    /*********************Fim dos métodos setters e getters********************/
    
    public void removerRegisto(AmbTransferencia reg)
    {
        try
        {
            this.userTransaction.begin();
            this.ambTransferenciaFacade.remove(reg);
            this.userTransaction.commit();
            Mensagem.sucessoMsg("Dados da Tranferência removidos com sucesso!");
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg("Impossível Remover.");
                e.printStackTrace();
                this.userTransaction.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex)
            {
                ex.printStackTrace();
                Mensagem.erroMsg("Impossível Remover." );//+ ex.toString()
            }
        }
    }    
    
}
