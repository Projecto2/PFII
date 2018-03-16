/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.armazenamento;

import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import sessao.FarmFichaStockFacade;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmVisualizarFichaStockBean implements Serializable
{
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   private FarmLocalArmazenamento local;
   private List<FarmFichaStock> listaItems;
   private FarmProduto produto;
   private ConexaoPostgresSQL conexaoPostgresSQL;

   /**
    * Creates a new instance of FarmVisualizarFichaStockBean
    */
   public FarmVisualizarFichaStockBean()
   {
   }
     
   public static FarmVisualizarFichaStockBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      return (FarmVisualizarFichaStockBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmVisualizarFichaStockBean");
   }
   
   public void pesquisarFichaStock()
   {
      listaItems = fichaStockFacade.findFichaStock(local, produto);
   }
   
   public void definirParametros(FarmProduto produtoPesquisa, FarmLocalArmazenamento localPesquisa)
   {
      setLocal(localPesquisa);
      setProduto(produtoPesquisa);
      listaItems = new ArrayList<>();
      pesquisarFichaStock();
   }
   
   public FarmLocalArmazenamento getInstanciaLocal()
   {
      FarmLocalArmazenamento localAux = new FarmLocalArmazenamento();
      localAux.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
      localAux.setFkIdInstituicao(new GrlInstituicao());
      return localAux;
   }

   /**
    * @return the local
    */
   public FarmLocalArmazenamento getLocal()
   {
      if(local == null)
         local = getInstanciaLocal();
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
    * @return the listaItems
    */
   public List<FarmFichaStock> getListaItems()
   {
      if(listaItems == null)
         listaItems = new ArrayList<>();
      return listaItems;
   }

   /**
    * @param listaItems the listaItems to set
    */
   public void setListaItems(List<FarmFichaStock> listaItems)
   {
      this.listaItems = listaItems;
   }

   /**
    * @return the produto
    */
   public FarmProduto getProduto()
   {
      if(produto == null)
         produto = FarmProdutoNovoBean.getInstancia();
      return produto;
   }

   /**
    * @param produto the produto to set
    */
   public void setProduto(FarmProduto produto)
   {
      System.out.println("fazendo set do produto... "+produto);
      this.produto = produto;
   }
   
   public void imprimirRelatorio()
   {
      conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();

      parametrosMap.put("estruturaSanitaria", local.getFkIdInstituicao().getCodigoInstituicao());
      parametrosMap.put("municipio", local.getFkIdInstituicao().getFkIdEndereco().getFkIdMunicipio().getNomeMunicipio().toUpperCase());
      parametrosMap.put("provincia", local.getFkIdInstituicao().getFkIdEndereco().getFkIdMunicipio().getFkIdProvincia().getNomeProvincia().toUpperCase());
      parametrosMap.put("localDeArmazenamento", local.getDescricao() + " (" + local.getAbreviatura() + ")");
      parametrosMap.put("descricaoProduto", produto.getDescricao() + " " 
              + produto.getDosagem() + " " 
              + produto.getFkIdUnidadeMedida().getAbreviatura());
      
      RelatorioJasper.exportPDF("farm/fichaDeStock.jasper", parametrosMap, listaItems);
   }
}
