/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.movimentos;

import entidade.FarmLocalArmazenamento;
import entidade.FarmMovimento;
import entidade.FarmMovimentoHasProduto;
import entidade.FarmPedido;
import entidade.FarmProduto;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessao.FarmMovimentoHasProdutoFacade;
import sessao.FarmPedidoHasProdutoFacade;
import util.Constantes;
import util.RelatorioJasper;
import util.relatorio.ConexaoPostgresSQL;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmVisualizarItensPedidoRealizadoBean implements Serializable
{

   @EJB
   private FarmMovimentoHasProdutoFacade movimentoHasProdutoFacade;
   @EJB
   private FarmPedidoHasProdutoFacade pedidoHasProdutoFacade;
   private FarmMovimento movimentoRealizadoPesquisa;
   private List<FarmMovimentoHasProduto> produtosMovimento;
   private ConexaoPostgresSQL conexaoPostgresSQL;

   /**
    * Creates a new instance of FarmMovimentosVisualizarBean
    */
   public FarmVisualizarItensPedidoRealizadoBean()
   {
      
   }

   public FarmMovimento getInstanciaMovimentoRealizado()
   {
      setMovimentoRealizadoPesquisa(new FarmMovimento());

      FarmPedido pedido = new FarmPedido();

      pedido.setFkIdFuncionarioSolicitou(new RhFuncionario());
      pedido.setFkIdFuncionarioAtendeu(new RhFuncionario());
      pedido.setFkLocalDestinoPedido(new FarmLocalArmazenamento());
      pedido.setFkLocalOrigemPedido(new FarmLocalArmazenamento());
      getMovimentoRealizadoPesquisa().setFkIdPedido(pedido);

      return getMovimentoRealizadoPesquisa();
   }

   public void pesquisarProdutosMovimento()
   {
      setProdutosMovimento(movimentoHasProdutoFacade.findProdutosMovimento(movimentoRealizadoPesquisa));
   }

   public int getQuantidadePedida(FarmPedido pedido, FarmProduto produto)
   {
      return pedidoHasProdutoFacade.findProdutoPedidoAux(pedido, produto).get(0).getQuantidade();
   }

   public String irParaVisualizarItensPedido()
   {
      return "pedidoRealizadoVisualizarItensFarm.xhtml?faces-redirect=true";
   }

   /**
    * @return the movimentoRealizadoPesquisa
    */
   public FarmMovimento getMovimentoRealizadoPesquisa()
   {
      if(movimentoRealizadoPesquisa == null)
         movimentoRealizadoPesquisa = getInstanciaMovimentoRealizado();
      
      return movimentoRealizadoPesquisa;
   }

   /**
    * @param movimentoRealizadoPesquisa the movimentoRealizadoPesquisa to set
    */
   public void setMovimentoRealizadoPesquisa(FarmMovimento movimentoRealizadoPesquisa)
   {
      this.movimentoRealizadoPesquisa = movimentoRealizadoPesquisa;
   }

   /**
    * @return the produtosMovimento
    */
   public List<FarmMovimentoHasProduto> getProdutosMovimento()
   {
      pesquisarProdutosMovimento();
      return produtosMovimento;
   }

   /**
    * @param produtosMovimento the produtosMovimento to set
    */
   public void setProdutosMovimento(List<FarmMovimentoHasProduto> produtosMovimento)
   {
      this.produtosMovimento = produtosMovimento;
   }

   public void imprimirRelatorio()
   {
      conexaoPostgresSQL = new ConexaoPostgresSQL();
      Connection conn = conexaoPostgresSQL.getConnection();
      HashMap<String, Object> parametrosMap = new HashMap<>();

      parametrosMap.put("idPedido", movimentoRealizadoPesquisa.getFkIdPedido().getPkIdPedido());
      parametrosMap.put("origemDoPedido", movimentoRealizadoPesquisa.getFkIdPedido().getFkLocalOrigemPedido().getDescricao());
      parametrosMap.put("destinoDoPedido", movimentoRealizadoPesquisa.getFkIdPedido().getFkLocalDestinoPedido().getDescricao());
      parametrosMap.put("dataDoMovimento", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(movimentoRealizadoPesquisa.getDataMovimento()));
      parametrosMap.put("funcionarioAtendeu", movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioAtendeu().getFkIdPessoa().getNome()
              + " " + movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioAtendeu().getFkIdPessoa().getNomeDoMeio()
              + " " + movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioAtendeu().getFkIdPessoa().getSobreNome());
      
      parametrosMap.put("funcionarioSolicitou", movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioSolicitou().getFkIdPessoa().getNome()
              + " " + movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioSolicitou().getFkIdPessoa().getNomeDoMeio()
              + " " + movimentoRealizadoPesquisa.getFkIdPedido().getFkIdFuncionarioSolicitou().getFkIdPessoa().getSobreNome());
      
      parametrosMap.put("REPORT_CONNECTION", conn);
      parametrosMap.put("SUBREPORT_DIR", "/home/" + System.getProperty("user.name") + Constantes.FARM_CAMINHO_DO_SUBREPORT);
      RelatorioJasper.exportPDF("farm/modeloDePedido.jasper", parametrosMap, produtosMovimento);
   }
}
