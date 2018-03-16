/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.armazenamento;

import entidade.FarmLocalArmazenamento;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmLocalArmazenamentoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmLocalArmazenamentoListarTodosBean implements Serializable
{

   /**
    * Creates a new instance of FarmLocalArmazenamentoListarTodosBean
    */
   @EJB
   private FarmLocalArmazenamentoFacade localFacade;
   private FarmLocalArmazenamento local;
   
   private List<FarmLocalArmazenamento> todosLocaisPesquisados;
   
   public FarmLocalArmazenamentoListarTodosBean()
   {
      local = getInstanciaLocal();
   }
   
   public FarmLocalArmazenamento getInstanciaLocal()
   {
      setLocal(new FarmLocalArmazenamento());
      getLocal().setFkIdInstituicao(new GrlInstituicao());
      getLocal().setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      
      return getLocal();
   }
   
   public void pesquisarTodoslocais()
   {
      setTodosLocaisPesquisados(localFacade.findAll());
   }

   /**
    * @return the local
    */
   public FarmLocalArmazenamento getLocal()
   {
      return local;
   }

   /**
    * @param local the local to set
    */
   public void setLocal(FarmLocalArmazenamento local)
   {
      this.local = local;
   }

   /**
    * @return the todosLocaisPesquisados
    */
   public List<FarmLocalArmazenamento> getTodosLocaisPesquisados()
   {
      return todosLocaisPesquisados;
   }

   /**
    * @param todosLocaisPesquisados the todosLocaisPesquisados to set
    */
   public void setTodosLocaisPesquisados(List<FarmLocalArmazenamento> todosLocaisPesquisados)
   {
      this.todosLocaisPesquisados = todosLocaisPesquisados;
   }
   
}
