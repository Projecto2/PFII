/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.grlbean;

import entidade.GrlComuna;
import entidade.GrlContacto;
import entidade.GrlDistrito;
import entidade.GrlEndereco;
import entidade.GrlFornecedor;
import entidade.GrlInstituicao;
import entidade.GrlMunicipio;
import java.io.Serializable;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.GrlContactoFacade;
import sessao.GrlEnderecoFacade;
import sessao.GrlFornecedorFacade;
import sessao.GrlInstituicaoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class GrlFornecedorNovoBean implements Serializable
{
   @Resource
   private UserTransaction userTransaction;
   
   @EJB
   private GrlFornecedorFacade fornecedorFacade;
   @EJB
   private GrlInstituicaoFacade instituicaoFacade;
   @EJB
   private GrlEnderecoFacade enderecoFacade;
   @EJB
   private GrlContactoFacade contactoFacade;
   
   private GrlFornecedor fornecedor;

   /**
    * Creates a new instance of GrlFornecedorNovoBean
    */
   public GrlFornecedorNovoBean()
   {
      fornecedor = getInstanciaFornecedor();
   }
   
   public static GrlFornecedorNovoBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      GrlFornecedorNovoBean grlFornecedorNovoBean
              = (GrlFornecedorNovoBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                      null, "grlFornecedorNovoBean");

      return grlFornecedorNovoBean;
   }
   public GrlFornecedor getInstanciaFornecedor()
   {
      GrlFornecedor item = new GrlFornecedor();

        item.setFkIdInstituicao(new GrlInstituicao());
        item.getFkIdInstituicao().setFkIdContacto(new GrlContacto());
        item.getFkIdInstituicao().setFkIdEndereco(new GrlEndereco());
        item.getFkIdInstituicao().getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
        item.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(new GrlComuna());
        item.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());

        return item;
   }
   
   public void criar()
   {
      try
        {
            userTransaction.begin();
            enderecoFacade.create(fornecedor.getFkIdInstituicao().getFkIdEndereco());
            contactoFacade.create(fornecedor.getFkIdInstituicao().getFkIdContacto());
            
            if (fornecedor.getFkIdInstituicao().getFkIdEndereco().getFkIdComuna().getPkIdComuna() == null)
            {
               System.out.println("comuna eh null");
                fornecedor.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(null);
            }
            if (fornecedor.getFkIdInstituicao().getFkIdEndereco().getFkIdDistrito().getPkIdDistrito() == null)
            {
               System.out.println("distrito eh null");
                fornecedor.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(null);
            }
            
            instituicaoFacade.create(fornecedor.getFkIdInstituicao());
            fornecedorFacade.create(fornecedor);
            userTransaction.commit();
            Mensagem.sucessoMsg("Fornecedor cadastrado com sucesso!");
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        setFornecedor(getInstanciaFornecedor());
   }
   /**
    * @return the fornecedor
    */
   public GrlFornecedor getFornecedor()
   {
      if(fornecedor == null)
         fornecedor = getInstanciaFornecedor();
      return fornecedor;
   }

   /**
    * @param fornecedor the fornecedor to set
    */
   public void setFornecedor(GrlFornecedor fornecedor)
   {
      this.fornecedor = fornecedor;
   }
}
