/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.fornecimento;

import entidade.FarmCategoriaMedicamento;
import entidade.FarmFichaStock;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmFornecimento;
import entidade.FarmFornecimentoHasProduto;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmProduto;
import entidade.FarmTipoFornecimento;
import entidade.FarmTipoProduto;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlFornecedor;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.UserTransaction;
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import managedBean.segbean.SegLoginBean;
import sessao.FarmFichaStockFacade;
import sessao.FarmFornecimentoFacade;
import sessao.FarmFornecimentoHasProdutoFacade;
import sessao.FarmLoteProdutoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.GrlFornecedorFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFornecimentoNovoBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   private FarmFornecimentoFacade fornecimentoFacade;
   @EJB
   private GrlFornecedorFacade fornecedorFacade;
   @EJB
   private FarmLoteProdutoFacade loteProdutoFacade;
   @EJB
   private FarmFornecimentoHasProdutoFacade fornecimentoHasProdutoFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteLocalFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;

   private FarmFornecimento fornecimento;
   private FarmFornecimentoHasProduto fornecimentoHasProduto;
   private FarmLoteProduto loteProdutoPesquisa;

   private List<FarmLoteProduto> disponiveis;
   private List<FarmFornecimentoHasProduto> seleccionados;

   /**
    * Creates a new instance of FarmFornecimentoNovo
    */
   public FarmFornecimentoNovoBean()
   {
      limparCampos();
   }

   public FarmFornecimentoHasProduto getInstanciaFornecimentoHasProduto()
   {
      fornecimentoHasProduto = new FarmFornecimentoHasProduto();
      fornecimentoHasProduto.setFkIdFornecimento(fornecimento);
      fornecimentoHasProduto.setFkIdLoteProduto(new FarmLoteProduto());
      fornecimentoHasProduto.setFkIdLocalArmazenamento(new FarmLocalArmazenamento());
      return fornecimentoHasProduto;
   }

   public FarmFornecimento getInstanciaFornecimento()
   {
      fornecimento = new FarmFornecimento();
      fornecimento.setFkIdFornecedor(new GrlFornecedor(FarmConfiguracoesBean.FORNECEDOR));
      fornecimento.setFkIdTipoFornecimento(new FarmTipoFornecimento(FarmConfiguracoesBean.TIPO_FORNECIMENTO));
      fornecimento.setFkIdFuncionarioCadastrou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      fornecimento.setCustoTotal(0.0);
      return fornecimento;
   }

   public FarmLoteProduto getInstanciaLoteProduto()
   {
      loteProdutoPesquisa = new FarmLoteProduto();
      loteProdutoPesquisa.setFkIdFuncionarioCadastrou(new RhFuncionario());
      FarmProduto produto = new FarmProduto();
      produto.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
      produto.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica());
      produto.setFkIdFuncionarioCadastrou(new RhFuncionario());
      produto.setFkIdTipoProduto(new FarmTipoProduto());
      produto.setFkIdUnidadeMedida(new FarmUnidadeMedida());
      produto.setFkIdViaAdministracao(new FarmViaAdministracao());
      loteProdutoPesquisa.setFkIdProduto(produto);
      return loteProdutoPesquisa;

   }

   public void calcularCustoTotal()
   {
      double custo = 0.0;
      for (FarmFornecimentoHasProduto aux : seleccionados)
      {
         aux.setCustoTotal(aux.getCustoPorUnidade() * aux.getQuantidade());
         custo += aux.getCustoTotal();
      }

      fornecimento.setCustoTotal(custo);
   }

   public void adicionar()
   {
      fornecimentoHasProduto = getInstanciaFornecimentoHasProduto();
      fornecimentoHasProduto.setFkIdLoteProduto(loteProdutoPesquisa);
      fornecimentoHasProduto.setCustoPorUnidade(0.0);
      fornecimentoHasProduto.setCustoTotal(0.0);
      fornecimentoHasProduto.setQuantidade(1);
      seleccionados.add(fornecimentoHasProduto);
      eliminarDuplicados();
      loteProdutoPesquisa = getInstanciaLoteProduto();
   }

   public void eliminarDuplicados()
   {
      for (FarmFornecimentoHasProduto aux : seleccionados)
      {
         if (disponiveis.contains(aux.getFkIdLoteProduto()))
            disponiveis.remove(aux.getFkIdLoteProduto());
      }
   }

   public void remover(FarmFornecimentoHasProduto item)
   {
      seleccionados.remove(item);
      pesquisarLotesProduto();
      eliminarDuplicados();
      calcularCustoTotal();
      loteProdutoPesquisa = getInstanciaLoteProduto();
   }

   public void pesquisarLotesProduto()
   {
      disponiveis = loteProdutoFacade.findProdutoNaoExpirado(loteProdutoPesquisa);
      if (disponiveis.isEmpty())
         Mensagem.warnMsg("Nenhum Lote de Produto encontrado.");

      eliminarDuplicados();
   }

   public String criar()
   {
      fornecimento.setDataFornecimento(new Date());
      calcularCustoTotal();
      if (fornecimento.getFkIdFornecedor().getPkIdFornecedor() == null)
         Mensagem.warnMsg("Defina o Fornecedor.");
      else if (fornecimento.getFkIdTipoFornecimento().getPkIdTipoFornecimento() == null)
         Mensagem.warnMsg("Defina o tipo de Fornecimento.");
      else if (seleccionados.isEmpty())
         Mensagem.warnMsg("Não seleccionou nenhum  produto");
      else
      {
         try
         {
            userTransaction.begin();

            fornecimentoFacade.create(fornecimento);
            System.out.println("dentro do try");
            for (FarmFornecimentoHasProduto aux : seleccionados)
            {
               FarmLoteProdutoHasLocalArmazenamento itemNoLocal = loteLocalFacade.findLoteProdutoNoLocal(aux.getFkIdLocalArmazenamento(), aux.getFkIdLoteProduto());
               if (itemNoLocal == null)
               {
                  System.out.println("itemNoLocal == null");
                  itemNoLocal = new FarmLoteProdutoHasLocalArmazenamento();
                  itemNoLocal.setFkIdLoteProduto(aux.getFkIdLoteProduto());
                  itemNoLocal.setFkIdLocalArmazenamento(aux.getFkIdLocalArmazenamento());
                  loteLocalFacade.create(itemNoLocal);
               }

               itemNoLocal.setQuantidadeStock(itemNoLocal.getQuantidadeStock() + aux.getQuantidade());
               System.out.println("setQuantidadeStock");
               loteLocalFacade.edit(itemNoLocal);
               fornecimentoHasProdutoFacade.create(aux);
               FarmFichaStock fichaStock = new FarmFichaStock();
               fichaStock.setDataMovimento(new Date());
               fornecimento.setFkIdFornecedor(fornecedorFacade.find(fornecimento.getFkIdFornecedor().getPkIdFornecedor()));

               System.out.println("fornecimentoHasProdutoFacade.create(aux);");
               fichaStock.setOrigemOuDestino(fornecimento.getFkIdFornecedor().getFkIdInstituicao().getDescricao());
               fichaStock.setFkIdLoteProduto(itemNoLocal.getFkIdLoteProduto());
               fichaStock.setFkIdLocalArmazenamento(aux.getFkIdLocalArmazenamento());
               fichaStock.setEntradas(aux.getQuantidade());
               fichaStock.setSaidas(0);
               fichaStock.setQuantidadeRestante(itemNoLocal.getQuantidadeStock());
               fichaStock.setFkIdFuncionario(fornecimento.getFkIdFuncionarioCadastrou());
               fichaStockFacade.create(fichaStock);
               System.out.println("fichaStockFacade.create(fichaStock);");
            }
//            FarmNotificacaoBean.getInstanciaBean().pesquisarNotificacoes();
//            FarmGraficosBean.getInstanciaBean().actualizarGraficos();
            Mensagem.sucessoMsg("Fornecimento efectuado com sucesso.");
            limparCampos();
            userTransaction.commit();
         }
         catch (Exception e)
         {
            Mensagem.warnMsg("Ocorreu um erro ao processar o fornecimento. Tente novamente."+e.toString());
            try
            {
               userTransaction.rollback();
               Mensagem.erroMsg(e.toString());
            }
            catch (Exception ex)
            {
//               Mensagem.erroMsg("Rollback: " + ex.toString());
            }
         }
      }

      return null;
   }

   public void limparCampos()
   {
      disponiveis = new ArrayList<>();
      seleccionados = new ArrayList<>();
      fornecimento = getInstanciaFornecimento();
      loteProdutoPesquisa = getInstanciaLoteProduto();
      fornecimentoHasProduto = getInstanciaFornecimentoHasProduto();
   }

   /**
    * @return the fornecimento
    */
   public FarmFornecimento getFornecimento()
   {
      if (fornecimento == null)
         fornecimento = getInstanciaFornecimento();

      return fornecimento;
   }

   /**
    * @param fornecimento the fornecimento to set
    */
   public void setFornecimento(FarmFornecimento fornecimento)
   {
      this.fornecimento = fornecimento;
   }

   /**
    * @return the fornecimentoHasProduto
    */
   public FarmFornecimentoHasProduto getFornecimentoHasProduto()
   {
      if (fornecimentoHasProduto == null)
         fornecimentoHasProduto = getInstanciaFornecimentoHasProduto();
      return fornecimentoHasProduto;
   }

   /**
    * @param fornecimentoHasProduto the fornecimentoHasProduto to set
    */
   public void setFornecimentoHasProduto(FarmFornecimentoHasProduto fornecimentoHasProduto)
   {
      this.fornecimentoHasProduto = fornecimentoHasProduto;
   }

   /**
    * @return the loteProdutoPesquisa
    */
   public FarmLoteProduto getLoteProdutoPesquisa()
   {
      if (loteProdutoPesquisa == null)
         loteProdutoPesquisa = getInstanciaLoteProduto();

      return loteProdutoPesquisa;
   }

   /**
    * @param loteProdutoPesquisa the loteProdutoPesquisa to set
    */
   public void setLoteProdutoPesquisa(FarmLoteProduto loteProdutoPesquisa)
   {
      this.loteProdutoPesquisa = loteProdutoPesquisa;
   }

   /**
    * @return the disponiveis
    */
   public List<FarmLoteProduto> getDisponiveis()
   {
      if (disponiveis == null)
         disponiveis = new ArrayList<>();
      return disponiveis;
   }

   /**
    * @param disponiveis the disponiveis to set
    */
   public void setDisponiveis(List<FarmLoteProduto> disponiveis)
   {
      this.disponiveis = disponiveis;
   }

   /**
    * @return the seleccionados
    */
   public List<FarmFornecimentoHasProduto> getSeleccionados()
   {
      if (seleccionados == null)
         seleccionados = new ArrayList<>();
      return seleccionados;
   }

   /**
    * @param seleccionados the seleccionados to set
    */
   public void setSeleccionados(List<FarmFornecimentoHasProduto> seleccionados)
   {
      this.seleccionados = seleccionados;
   }

}
