/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmFormaFarmaceutica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmFormaFarmaceuticaFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFormaFarmaceuticaListarBean implements Serializable
{
   @EJB
   private FarmFormaFarmaceuticaFacade formaFarmaceuticaFacade;

   private FarmFormaFarmaceutica formaFarmaceuticaPesquisa, formaFarmaceuticaEditar;
   private List<FarmFormaFarmaceutica> formasFarmaceuticasPesquisadas;

   public FarmFormaFarmaceuticaListarBean()
   {
   }

   /**
    * Creates a new instance of FarmFormaFarmaceuticaListarBean
    * @return 
    */
   
   public FarmFormaFarmaceutica getInstanciaFormaFarmaceutica()
   {
      FarmFormaFarmaceutica item = new FarmFormaFarmaceutica();
      return item;
   }

   public void pesquisarFormaFarmaceuticas()
   {
      System.out.println("pesq forma");
      formasFarmaceuticasPesquisadas = formaFarmaceuticaFacade.findFormaFarmaceutica(formaFarmaceuticaPesquisa);
      if(formasFarmaceuticasPesquisadas.isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + formasFarmaceuticasPesquisadas.size() + " registo(s) retornado(s).");
   }

   public void criar()
   {
      formaFarmaceuticaFacade.create(formaFarmaceuticaEditar);
      Mensagem.sucessoMsg("FormaFarmaceutica cadastrada com sucesso.");
   }

   public void editar()
   {
      formaFarmaceuticaFacade.edit(formaFarmaceuticaEditar);
      Mensagem.sucessoMsg("FormaFarmaceutica editada com sucesso.");
   }

   public void eliminar()
   {
      try
      {
         formaFarmaceuticaFacade.remove(formaFarmaceuticaEditar);
         Mensagem.sucessoMsg("FormaFarmaceutica eliminada com sucesso.");
      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("O formaFarmaceutica não pode ser eliminado porque está a ser utilizado.");
      }

   }

   public void limparCampos()
   {
      formaFarmaceuticaEditar = getInstanciaFormaFarmaceutica();
      formaFarmaceuticaPesquisa = getInstanciaFormaFarmaceutica();
      formasFarmaceuticasPesquisadas = new ArrayList<>();
   }

   /**
    * @return the formaFarmaceuticaPesquisa
    */
   public FarmFormaFarmaceutica getFormaFarmaceuticaPesquisa()
   {
      if (formaFarmaceuticaPesquisa == null)
         formaFarmaceuticaPesquisa = getInstanciaFormaFarmaceutica();
      return formaFarmaceuticaPesquisa;
   }

   /**
    * @param formaFarmaceuticaPesquisa the formaFarmaceuticaPesquisa to set
    */
   public void setFormaFarmaceuticaPesquisa(FarmFormaFarmaceutica formaFarmaceuticaPesquisa)
   {
      this.formaFarmaceuticaPesquisa = formaFarmaceuticaPesquisa;
   }

   /**
    * @return the formaFarmaceuticaEditar
    */
   public FarmFormaFarmaceutica getFormaFarmaceuticaEditar()
   {
      if (formaFarmaceuticaEditar == null)
         formaFarmaceuticaEditar = getInstanciaFormaFarmaceutica();

      return formaFarmaceuticaEditar;
   }

   /**
    * @param formaFarmaceuticaEditar the formaFarmaceuticaEditar to set
    */
   public void setFormaFarmaceuticaEditar(FarmFormaFarmaceutica formaFarmaceuticaEditar)
   {
      this.formaFarmaceuticaEditar = formaFarmaceuticaEditar;
   }

   /**
    * @return the formasFarmaceuticasPesquisadas
    */
   public List<FarmFormaFarmaceutica> getFormaFarmaceuticasPesquisadas()
   {
      if (formasFarmaceuticasPesquisadas == null)
         formasFarmaceuticasPesquisadas = new ArrayList<>();
      return formasFarmaceuticasPesquisadas;
   }

   /**
    * @param formasFarmaceuticasPesquisadas the formasFarmaceuticasPesquisadas to set
    */
   public void setFormaFarmaceuticasPesquisadas(List<FarmFormaFarmaceutica> formasFarmaceuticasPesquisadas)
   {
      this.formasFarmaceuticasPesquisadas = formasFarmaceuticasPesquisadas;
   }
}

