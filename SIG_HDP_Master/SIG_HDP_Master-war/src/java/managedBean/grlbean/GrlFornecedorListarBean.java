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
import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import sessao.GrlMunicipioFacade;
import util.ItensAjaxBean;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class GrlFornecedorListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   
    @EJB
    private GrlMunicipioFacade municipioFacade;
   @EJB
   private GrlFornecedorFacade fornecedorFacade;
   @EJB
   private GrlContactoFacade contactoFacade;
   @EJB
   private GrlEnderecoFacade enderecoFacade;
   @EJB
   private GrlInstituicaoFacade instituicaoFacade;
   
   private GrlFornecedor fornecedor;
   private GrlFornecedor fornecedorEditar;
   private List<GrlFornecedor> listaFornecedores;
   
   /**
    * Creates a new instance of GrlFornecedorListarBean
    */
   public GrlFornecedorListarBean()
   {
      limparCampos();
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
   
   public GrlFornecedor getInstanciaFornecedorEditar()
   {
      fornecedorEditar = new GrlFornecedor();
      GrlEndereco endereco = new GrlEndereco();
      GrlComuna comuna = new GrlComuna();
      GrlMunicipio municipio = new GrlMunicipio();
      GrlProvincia provincia = new GrlProvincia();
      GrlPais pais = new GrlPais();      
      GrlInstituicao instituicao = new GrlInstituicao();
      GrlContacto contacto = new GrlContacto();
      
      provincia.setFkIdPais(pais);
      municipio.setFkIdProvincia(provincia);
      comuna.setFkIdMunicipio(municipio);
      endereco.setFkIdComuna(comuna);
      instituicao.setFkIdEndereco(endereco);
      instituicao.setFkIdContacto(contacto);
      getFornecedorEditar().setFkIdInstituicao(instituicao);
      return getFornecedorEditar();
   }
   
   public void pesquisarFornecedores()
   {
      listaFornecedores = fornecedorFacade.findFornecedor(fornecedor);
      if(listaFornecedores.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaFornecedores().size() + " registo(s) retornado(s).");
   }
   
   public void limparCampos()
   {
      fornecedorEditar = getInstanciaFornecedorEditar();
      fornecedor = getInstanciaFornecedor();
      listaFornecedores = new ArrayList<>();
   }

   public String editar()
    {
        try
        {
            userTransaction.begin();
            if (getFornecedorEditar().getPkIdFornecedor() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
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
            
            //contacto
            contactoFacade.edit(fornecedorEditar.getFkIdInstituicao().getFkIdContacto());
            
            //endereco
            enderecoFacade.edit(fornecedorEditar.getFkIdInstituicao().getFkIdEndereco());
            //instituicao
            instituicaoFacade.edit(fornecedorEditar.getFkIdInstituicao());
            
            fornecedorFacade.edit(fornecedorEditar);
            userTransaction.commit();
            pesquisarFornecedores();
            Mensagem.sucessoMsg("Fornecedor editado com sucesso!");
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg("Fornecedor editado com sucesso!");
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        setFornecedorEditar(getInstanciaFornecedorEditar());
        return null;
    }
   
   public void eliminar()
   {
      try
      {
         fornecedorFacade.remove(fornecedorEditar);
         Mensagem.sucessoMsg("Fornecedor eliminado com sucesso.");
      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("O fornecedor não pode ser eliminado porque está a ser utilizado.");
      }

   }
   
   /**
    * @return the fornecedor
    */
   public GrlFornecedor getFornecedor()
   {
      return fornecedor;
   }

   /**
    * @param fornecedor the fornecedor to set
    */
   public void setFornecedor(GrlFornecedor fornecedor)
   {
      this.fornecedor = fornecedor;
   }

   /**
    * @return the listaFornecedores
    */
   public List<GrlFornecedor> getListaFornecedores()
   {
      return listaFornecedores;
   }

   /**
    * @param listaFornecedores the listaFornecedores to set
    */
   public void setListaFornecedores(List<GrlFornecedor> listaFornecedores)
   {
      this.listaFornecedores = listaFornecedores;
   }

   /**
    * @return the fornecedorEditar
    */
   public GrlFornecedor getFornecedorEditar()
   {
      if(fornecedorEditar == null)
         fornecedorEditar = getInstanciaFornecedor();
      return fornecedorEditar;
   }

   /**
    * @param fornecedorEditar the fornecedorEditar to set
    */
   public void setFornecedorEditar(GrlFornecedor fornecedorEditar)
   {
      if (fornecedorEditar != null)
        {
            if (fornecedorEditar.getFkIdInstituicao().getFkIdEndereco().getFkIdComuna() == null)
            {
                fornecedorEditar.getFkIdInstituicao().getFkIdEndereco().setFkIdComuna(new GrlComuna());
            }
            if (fornecedorEditar.getFkIdInstituicao().getFkIdEndereco().getFkIdDistrito() == null)
            {
                fornecedorEditar.getFkIdInstituicao().getFkIdEndereco().setFkIdDistrito(new GrlDistrito());
            }

            ItensAjaxBean itensAjaxBean = obterItensAjaxBean();

            GrlMunicipio mn = municipioFacade.find(fornecedorEditar.getFkIdInstituicao().getFkIdEndereco().getFkIdMunicipio().getPkIdMunicipio());

            itensAjaxBean.setPais(mn.getFkIdProvincia().getFkIdPais().getPkIdPais());
            itensAjaxBean.setProvincia(mn.getFkIdProvincia().getPkIdProvincia());
            itensAjaxBean.setMunicipio(mn.getPkIdMunicipio());
        }
      this.fornecedorEditar = fornecedorEditar;
   }
   
   private ItensAjaxBean obterItensAjaxBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (ItensAjaxBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "itensAjaxBean");
    }
}
