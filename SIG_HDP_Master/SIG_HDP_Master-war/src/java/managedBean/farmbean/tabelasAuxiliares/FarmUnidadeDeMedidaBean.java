/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sessao.FarmUnidadeMedidaFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmUnidadeDeMedidaBean implements Serializable
{

   @EJB
   private FarmUnidadeMedidaFacade farmUnidadeMedidaFacade;
   private FarmUnidadeMedida unidadeMedidaEditar, unidadeMedidaPesquisa;

   private List<FarmUnidadeMedida> unidadesPesquisadas;

   /**
    * Creates a new instance of UnidadeDeMedidaBean
    */
   public FarmUnidadeDeMedidaBean()
   {
   }

   public static FarmUnidadeDeMedidaBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmUnidadeDeMedidaBean farmUnidadeDeMedidaBean = (FarmUnidadeDeMedidaBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmUnidadeDeMedidaBean");

      return farmUnidadeDeMedidaBean;
   }
   
   public FarmUnidadeMedida getInstanciaUnidade()
   {
      FarmUnidadeMedida item = new FarmUnidadeMedida();
      item.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
      return item;
   }

   public void pesquisarUnidadesDeMedida()
   {
      setUnidadesPesquisadas(farmUnidadeMedidaFacade.findUnidadeMedida(unidadeMedidaPesquisa));
      if (getUnidadesPesquisadas().isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa Efectuada com sucesso. " + getUnidadesPesquisadas().size() + "registos retornados.");
   }

   public void criar()
   {
      try
      {
         farmUnidadeMedidaFacade.create(unidadeMedidaEditar);
         Mensagem.sucessoMsg("Unidade de Medida cadastrada com sucesso!");
         unidadeMedidaEditar = getInstanciaUnidade();

      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("Já existe essa unidade de medida");
      }
   }

   public void editar()
   {
      farmUnidadeMedidaFacade.edit(unidadeMedidaEditar);
      Mensagem.sucessoMsg("Unidade de Medida editada com sucesso!");
      unidadeMedidaEditar = getInstanciaUnidade();
   }

   public void eliminar()
   {
      try
      {
         farmUnidadeMedidaFacade.remove(unidadeMedidaEditar);
         Mensagem.sucessoMsg("Unidade de Medida eliminada com sucesso.");
      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("A unidade de medida não pode ser eliminada porque está a ser utilizada.");
      }
   }

   /**
    * @return the farmUnidadeMedidaFacade
    */
   public FarmUnidadeMedidaFacade getFarmUnidadeMedidaFacade()
   {
      return farmUnidadeMedidaFacade;
   }

   /**
    * @param farmUnidadeMedidaFacade the farmUnidadeMedidaFacade to set
    */
   public void setFarmUnidadeMedidaFacade(FarmUnidadeMedidaFacade farmUnidadeMedidaFacade)
   {
      this.farmUnidadeMedidaFacade = farmUnidadeMedidaFacade;
   }

   /**
    * @return the unidadeMedidaEditar
    */
   public FarmUnidadeMedida getUnidadeMedidaEditar()
   {
      if (unidadeMedidaEditar == null)
         unidadeMedidaEditar = getInstanciaUnidade();

      return unidadeMedidaEditar;
   }

   /**
    * @param unidadeMedidaEditar the unidadeMedidaEditar to set
    */
   public void setUnidadeMedidaEditar(FarmUnidadeMedida unidadeMedidaEditar)
   {
      this.unidadeMedidaEditar = unidadeMedidaEditar;
   }

   /**
    * @return the unidadesPesquisadas
    */
   public List<FarmUnidadeMedida> getUnidadesPesquisadas()
   {
      return unidadesPesquisadas;
   }

   /**
    * @param unidadesPesquisadas the unidadesPesquisadas to set
    */
   public void setUnidadesPesquisadas(List<FarmUnidadeMedida> unidadesPesquisadas)
   {
      this.unidadesPesquisadas = unidadesPesquisadas;
   }

   /**
    * @return the unidadeMedidaPesquisa
    */
   public FarmUnidadeMedida getUnidadeMedidaPesquisa()
   {
      if (unidadeMedidaPesquisa == null)
         unidadeMedidaPesquisa = getInstanciaUnidade();
      return unidadeMedidaPesquisa;
   }

   /**
    * @param unidadeMedidaPesquisa the unidadeMedidaPesquisa to set
    */
   public void setUnidadeMedidaPesquisa(FarmUnidadeMedida unidadeMedidaPesquisa)
   {
      this.unidadeMedidaPesquisa = unidadeMedidaPesquisa;
   }
   
   
   //Obtem as unidades de medida usadas no internamento
   public List<FarmUnidadeMedida> findUnidadesMedidasInternamento()
   {
        return farmUnidadeMedidaFacade.lerUnidadesInternamento();
   }
}
