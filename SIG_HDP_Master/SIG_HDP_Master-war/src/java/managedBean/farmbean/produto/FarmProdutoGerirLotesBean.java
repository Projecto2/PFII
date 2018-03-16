/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.produto;

import entidade.FarmLoteProduto;
import entidade.FarmProduto;
import entidade.GrlMarcaProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import managedBean.segbean.SegLoginBean;
import sessao.FarmLoteProdutoFacade;
import sessao.FarmProdutoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmProdutoGerirLotesBean implements Serializable
{
   @Resource
   private UserTransaction userTransaction;

   @EJB
   FarmProdutoFacade produtoFacade;
   private FarmProduto produto;

   @EJB
   FarmLoteProdutoFacade loteFacade;
   private FarmLoteProduto loteProduto;
   private FarmLoteProduto LoteProdutoPesquisa;

   private List<FarmLoteProduto> lotesDeProdutosPesquisados;
   
   private Date dataFabricoInicio;
   private Date dataFabricoFim;
   
   private Date dataValidadeInicio;
   private Date dataValidadeFim;
   /**
    * Creates a new instance of FarmProdutoEditar
    */
   public FarmProdutoGerirLotesBean()
   {
   }

   public String voltar()
   {
      return "produtosListar.xhtml?faces-redirect=true";
   }
    
   public static FarmProdutoGerirLotesBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      return (FarmProdutoGerirLotesBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmProdutoGerirLotesBean");
   }
      
   public FarmLoteProduto getInstanciaLoteProduto()
   {
      FarmLoteProduto loteProdutoAux = new FarmLoteProduto();
      loteProdutoAux.setFkIdProduto(FarmProdutoNovoBean.getInstancia());
      loteProdutoAux.setFkIdMarca(new GrlMarcaProduto(FarmConfiguracoesBean.MARCA_LABORATORIO));
      loteProdutoAux.setFkIdFuncionarioCadastrou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      loteProdutoAux.setDataCadastro(new Date());
      
      return loteProdutoAux;
   }
   
   public List<FarmLoteProduto> getLoteByProduto()
   {
      setLotesDeProdutosPesquisados(loteFacade.findLotePorProduto(getLoteProdutoPesquisa(), produto));
      
      return getLotesDeProdutosPesquisados();
   }
   
   public List<FarmLoteProduto> getLoteByProduto(FarmProduto item)
   {
      return loteFacade.findLotePorProduto(getLoteProdutoPesquisa(), item);
   }
   
   public void pesquisarLotesProdutos()
   {
      setLotesDeProdutosPesquisados(loteFacade.findLotePorProduto(getLoteProdutoPesquisa(), produto));
   }
   
   public String criar ()
    {
        try
        {
            userTransaction.begin();
            
            loteProduto.setFkIdProduto(produto);
            
            if(loteProduto.getFkIdMarca() == null)
               loteProduto.setFkIdMarca(null);
            
            loteFacade.create(loteProduto);
            userTransaction.commit();
            Mensagem.sucessoMsg("Lote cadastrado com sucesso!");
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
           Mensagem.sucessoMsg("Ocorreu um erro ao cadastrar o lote, tente novamente.");
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
        loteProduto = getInstanciaLoteProduto();
        
        return null;
    }
    
   public void eliminar()
   {
      try
      {
         loteFacade.remove(LoteProdutoPesquisa);
         LoteProdutoPesquisa = getInstanciaLoteProduto();
         pesquisarLotesProdutos();
         Mensagem.sucessoMsg("O Lote foi eliminado com Sucesso.");
      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("O Lote não poder ser eliminado, porque está a ser utilizado.");
      }

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
      this.produto = produto;
   }

   /**
    * @return the loteProduto
    */
   public FarmLoteProduto getLoteProduto()
   {
      if(loteProduto == null)
         loteProduto = getInstanciaLoteProduto();
      return loteProduto;
   }

   /**
    * @param loteProduto the loteProduto to set
    */
   public void setLoteProduto(FarmLoteProduto loteProduto)
   {
      this.loteProduto = loteProduto;
   }

   /**
    * @return the LoteProdutoPesquisa
    */
   public FarmLoteProduto getLoteProdutoPesquisa()
   {
      if(LoteProdutoPesquisa == null)
         LoteProdutoPesquisa = getInstanciaLoteProduto();
      return LoteProdutoPesquisa;
   }

   /**
    * @param LoteProdutoPesquisa the LoteProdutoPesquisa to set
    */
   public void setLoteProdutoPesquisa(FarmLoteProduto LoteProdutoPesquisa)
   {
      this.LoteProdutoPesquisa = LoteProdutoPesquisa;
   }

   /**
    * @return the lotesDeProdutosPesquisados
    */
   public List<FarmLoteProduto> getLotesDeProdutosPesquisados()
   {
      if(lotesDeProdutosPesquisados == null)
         lotesDeProdutosPesquisados = new ArrayList<>();
      return lotesDeProdutosPesquisados;
   }

   /**
    * @param lotesDeProdutosPesquisados the lotesDeProdutosPesquisados to set
    */
   public void setLotesDeProdutosPesquisados(List<FarmLoteProduto> lotesDeProdutosPesquisados)
   {
      this.lotesDeProdutosPesquisados = lotesDeProdutosPesquisados;
   }

   /**
    * @return the dataFabricoInicio
    */
   public Date getDataFabricoInicio()
   {
      return dataFabricoInicio;
   }

   /**
    * @param dataFabricoInicio the dataFabricoInicio to set
    */
   public void setDataFabricoInicio(Date dataFabricoInicio)
   {
      this.dataFabricoInicio = dataFabricoInicio;
   }

   /**
    * @return the dataFabricoFim
    */
   public Date getDataFabricoFim()
   {
      return dataFabricoFim;
   }

   /**
    * @param dataFabricoFim the dataFabricoFim to set
    */
   public void setDataFabricoFim(Date dataFabricoFim)
   {
      this.dataFabricoFim = dataFabricoFim;
   }

   /**
    * @return the dataValidadeInicio
    */
   public Date getDataValidadeInicio()
   {
      return dataValidadeInicio;
   }

   /**
    * @param dataValidadeInicio the dataValidadeInicio to set
    */
   public void setDataValidadeInicio(Date dataValidadeInicio)
   {
      this.dataValidadeInicio = dataValidadeInicio;
   }

   /**
    * @return the dataValidadeFim
    */
   public Date getDataValidadeFim()
   {
      return dataValidadeFim;
   }

   /**
    * @param dataValidadeFim the dataValidadeFim to set
    */
   public void setDataValidadeFim(Date dataValidadeFim)
   {
      this.dataValidadeFim = dataValidadeFim;
   }
  

}
