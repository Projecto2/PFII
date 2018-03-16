/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.receitas;

import entidade.AmbPrescricaoMedica;
import entidade.AmbPrescricaoMedicaHasProduto;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import static managedBean.ambbean.ce.receitas.AmbPrescricaoMedicaCriarBean.getInstanciaAmbPrescricaoMedica;
import static managedBean.ambbean.ce.receitas.AmbPrescricaoMedicaCriarBean.getInstanciaAmbPrescricaoMedicaHasProduto;
import sessao.AmbPrescricaoMedicaFacade;
import sessao.AmbPrescricaoMedicaHasProdutoFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbPrescricaoMedicaEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
  
    @EJB
    private AmbPrescricaoMedicaFacade ambPrescricaoMedicaFacade;
    @EJB
    private AmbPrescricaoMedicaHasProdutoFacade ambPrescricaoMedicaHasProdutoFacade;  
    
    private AmbPrescricaoMedica ambPrescricaoMedicaEliminar; 
//    private AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProdutoEliminar;
    
    /**
     * Creates a new instance of AmbPrescricaoMedicaEliminarBean
     */
    public AmbPrescricaoMedicaEliminarBean()
    {
    }
    
    public static AmbPrescricaoMedicaEliminarBean getInstanciaBean()
    {
        return (AmbPrescricaoMedicaEliminarBean) GeradorCodigo.getInstanciaBean("ambPrescricaoMedicaEliminarBean");
    }
    
    /******************Início dos métodos setters e getters********************/
    
    public AmbPrescricaoMedica getAmbPrescricaoMedicaEliminar()
    {
        if (ambPrescricaoMedicaEliminar == null)
        {
            ambPrescricaoMedicaEliminar = getInstanciaAmbPrescricaoMedica();
        }
        return ambPrescricaoMedicaEliminar;
    }

    public void setAmbPrescricaoMedicaEliminar(AmbPrescricaoMedica ambPrescricaoMedicaEliminar)
    {
        this.ambPrescricaoMedicaEliminar = ambPrescricaoMedicaEliminar;
    }    
    
//    public AmbPrescricaoMedicaHasProduto getAmbPrescricaoMedicaHasProdutoEliminar()
//    {
//        if (ambPrescricaoMedicaHasProdutoEliminar == null)
//        {
//            ambPrescricaoMedicaHasProdutoEliminar = getInstanciaAmbPrescricaoMedicaHasProduto();
//        }
//        return ambPrescricaoMedicaHasProdutoEliminar;
//    }
//
//    public void setAmbPrescricaoMedicaHasProdutoEliminar(AmbPrescricaoMedicaHasProduto ambPrescricaoMedicaHasProdutoEliminar)
//    {
//        this.ambPrescricaoMedicaHasProdutoEliminar = ambPrescricaoMedicaHasProdutoEliminar;
//    }
    
    /*********************Fim dos métodos setters e getters********************/    
    
    public void eliminarReceita(AmbPrescricaoMedica apm) throws SystemException 
    {
        try
        {
            this.userTransaction.begin();
            
           for (AmbPrescricaoMedicaHasProduto apmhp : ambPrescricaoMedicaHasProdutoFacade.findAll())
                if (apmhp.getFkIdPrescricaoMedica().getPkIdPrescricaoMedica().equals(apm.getPkIdPrescricaoMedica()))
                    ambPrescricaoMedicaHasProdutoFacade.remove(apmhp);
            
            ambPrescricaoMedicaFacade.remove(apm);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Receita eliminada com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg("Impossível remover!");
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
    
    public void removerRemedioReceita(AmbPrescricaoMedicaHasProduto apmhp) throws SystemException 
    {
        try
        {
            this.userTransaction.begin();
            
//System.out.print("ambPrescricaoMedicaEliminarBean.removerRemedioReceita(): Teste 0.0 : " + apmhp.getPkIdPrescricaoMedicaProduto());            
            ambPrescricaoMedicaHasProdutoFacade.remove(apmhp);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Remédio removido da Receita com sucesso!");
            
        } catch (Exception e)
        {
            try
            {
                Mensagem.erroMsg("Impossível remover!");
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
}
