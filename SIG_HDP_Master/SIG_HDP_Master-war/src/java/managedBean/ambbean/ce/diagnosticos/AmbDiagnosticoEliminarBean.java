/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.ambbean.ce.diagnosticos;

import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.AmbDiagnosticoFacade;
import sessao.AmbDiagnosticoHasDoencaFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author ivandro-colombo
 */
@ManagedBean
@SessionScoped
public class AmbDiagnosticoEliminarBean implements Serializable
{
    @Resource
    private UserTransaction userTransaction;
 
    @EJB
    private AmbDiagnosticoFacade ambDiagnosticoFacade;    
    @EJB
    private AmbDiagnosticoHasDoencaFacade ambDiagnosticoHasDoencaFacade;    
    
    
    private AmbDiagnostico ambDiagnosticoEliminar; 
    private AmbDiagnosticoHasDoenca ambDiagnosticoHasDoencaEliminar;
    
    /**
     * Creates a new instance of AmbDiagnosticoEliminarBean
     */
    public AmbDiagnosticoEliminarBean()
    {
    }
    
    /******************Início dos métodos setters e getters********************/
    
    public AmbDiagnostico getAmbDiagnosticoEliminar()
    {
        if (ambDiagnosticoEliminar == null)
        {
            ambDiagnosticoEliminar = AmbDiagnosticoCriarBean.getInstanciaAmbDiagnostico();
        }
        return ambDiagnosticoEliminar;
    }

    public void setAmbDiagnosticoEliminar(AmbDiagnostico ambDiagnosticoEliminar)
    {
        this.ambDiagnosticoEliminar = ambDiagnosticoEliminar;
    }

//    public AmbDiagnosticoHasDoenca getAmbDiagnosticoHasDoencaEliminar()
//    {
//        if (ambDiagnosticoHasDoencaEliminar == null)
//        {
//            ambDiagnosticoHasDoencaEliminar = AmbDiagnosticoCriarBean.getInstanciaAmbDiagnosticoHasDoenca();
//        }
//        return ambDiagnosticoHasDoencaEliminar;
//    }
//
//    public void setAmbDiagnosticoHasDoencaEliminar(AmbDiagnosticoHasDoenca ambDiagnosticoHasDoencaEliminar)
//    {
//        this.ambDiagnosticoHasDoencaEliminar = ambDiagnosticoHasDoencaEliminar;
//    }
    
    /*********************Fim dos métodos setters e getters********************/    
    
    public static AmbDiagnosticoEliminarBean getInstanciaBean()
    {
        return (AmbDiagnosticoEliminarBean) GeradorCodigo.getInstanciaBean("ambDiagnosticoEliminarBean");
    }
    
    public void EliminarDiagnostico(AmbDiagnostico reg) throws SystemException
    {
        try
        {
            this.userTransaction.begin();
            
            for (AmbDiagnosticoHasDoenca adhd : ambDiagnosticoHasDoencaFacade.findAll())
                if (adhd.getFkIdDiagnostico().getPkIdDiagnostico().equals(reg.getPkIdDiagnostico()))
                    ambDiagnosticoHasDoencaFacade.remove(adhd);
            
            ambDiagnosticoFacade.remove(reg);           

            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Diagnostico removido com sucesso!");
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
    
    public void removerDoencaDiagnostico(AmbDiagnosticoHasDoenca adhd) throws SystemException 
    {
        try
        {
            this.userTransaction.begin();
            
            ambDiagnosticoHasDoencaFacade.remove(adhd);
            
            this.userTransaction.commit();
            
            Mensagem.sucessoMsg("Doença removida com sucesso!");
            
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
