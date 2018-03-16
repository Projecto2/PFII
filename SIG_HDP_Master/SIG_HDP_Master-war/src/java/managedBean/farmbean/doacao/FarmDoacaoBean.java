/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.doacao;

import entidade.FarmDoacao;
import entidade.FarmDoacaoHasLoteProduto;
import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.FarmDoacaoFacade;
import sessao.FarmDoacaoHasLoteProdutoFacade;
import sessao.FarmFichaStockFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.GrlInstituicaoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmDoacaoBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmDoacaoFacade doacaoFacade;
   @EJB
   private FarmDoacaoHasLoteProdutoFacade doacaoHasProdutoFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteHaslocalFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   @EJB
   private GrlInstituicaoFacade instituicaoFacade;

   private FarmDoacao doacao;
   private FarmDoacaoHasLoteProduto doacaoHasProduto;

   /**
    * Creates a new instance of FarmDoacaoBean
    */
   public FarmDoacaoBean()
   {
   }

   public FarmDoacao getInstanciaDoacao()
   {
      FarmDoacao doacaoAux = new FarmDoacao();
      doacaoAux.setFkIdFuncionario(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      doacaoAux.setFkIdInstituicao(new GrlInstituicao());
      doacaoAux.setFkIdLocalArmazenamento(new FarmLocalArmazenamento());
      return doacaoAux;
   }

   public FarmDoacaoHasLoteProduto getInstanciaDoacaoHasLoteProduto()
   {
      FarmDoacaoHasLoteProduto aux = new FarmDoacaoHasLoteProduto();
      aux.setFkIdDoacao(new FarmDoacao());
      aux.setFkIdLoteProduto(new FarmLoteProduto());
      aux.setQuantidade(1);
      return aux;
   }

   public void definirLote(FarmLoteProduto lote)
   {
      doacaoHasProduto.setFkIdLoteProduto(lote);
   }

   public void validarQuantidade()
   {
      if (doacaoHasProduto.getQuantidade() < 1)
         Mensagem.sucessoMsg("A quantidade a Doar não é valida. Verifique.");
   }

   public void criar(FarmLocalArmazenamento localOrigem)
   {
      try
      {
         if (doacaoHasProduto.getQuantidade() < 1)
            Mensagem.warnMsg("A quantidade a Doar não é valida. Verifique.");
         else
         {
            userTransaction.begin();
            doacao.setDataCadastro(new Date());
            doacao.setFkIdLocalArmazenamento(localOrigem);

            doacao.setFkIdFuncionario(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
            doacaoFacade.create(doacao);

            doacaoHasProduto.setFkIdDoacao(doacao);
            doacaoHasProduto.setFkIdLoteProduto(doacaoHasProduto.getFkIdLoteProduto());

            //decrementar
            FarmLoteProdutoHasLocalArmazenamento itemNoLocal_Origem = loteHaslocalFacade.findLoteProdutoNoLocal(doacao.getFkIdLocalArmazenamento(), doacaoHasProduto.getFkIdLoteProduto());

            itemNoLocal_Origem.setQuantidadeStock(itemNoLocal_Origem.getQuantidadeStock() - doacaoHasProduto.getQuantidade());
            loteHaslocalFacade.edit(itemNoLocal_Origem);

            doacaoHasProdutoFacade.create(doacaoHasProduto);
            //ficha de stock
            FarmFichaStock fichaParaOrigem = new FarmFichaStock();

            fichaParaOrigem.setDataMovimento(new Date());
            doacao.setFkIdInstituicao(instituicaoFacade.find(doacao.getFkIdInstituicao().getPkIdInstituicao()));
            fichaParaOrigem.setOrigemOuDestino("Doação para " + doacao.getFkIdInstituicao().getDescricao());

            fichaParaOrigem.setFkIdLoteProduto(doacaoHasProduto.getFkIdLoteProduto());
            fichaParaOrigem.setFkIdLocalArmazenamento(doacao.getFkIdLocalArmazenamento());
            fichaParaOrigem.setEntradas(0);
            fichaParaOrigem.setSaidas(doacaoHasProduto.getQuantidade());
            fichaParaOrigem.setQuantidadeRestante(itemNoLocal_Origem.getQuantidadeStock());
            fichaParaOrigem.setFkIdFuncionario(doacao.getFkIdFuncionario());
            fichaStockFacade.create(fichaParaOrigem);

            userTransaction.commit();
//            FarmNotificacaoBean.getInstanciaBean().pesquisarNotificacoes();
//            FarmGraficosBean.getInstanciaBean().actualizarGraficos();
            Mensagem.sucessoMsg("Produto doado com sucesso.");
         }

      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um Erro ao Doar o Produto. Tente novamente.");
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {

         }
      }
   }

   public FarmDoacao getDoacao()
   {
      if (doacao == null)
         doacao = getInstanciaDoacao();
      return doacao;
   }

   public void setDoacao(FarmDoacao doacao)
   {
      this.doacao = doacao;
   }

   public FarmDoacaoHasLoteProduto getDoacaoHasProduto()
   {
      if (doacaoHasProduto == null)
         doacaoHasProduto = getInstanciaDoacaoHasLoteProduto();
      return doacaoHasProduto;
   }

   public void setDoacaoHasProduto(FarmDoacaoHasLoteProduto doacaoHasProduto)
   {
      this.doacaoHasProduto = doacaoHasProduto;
   }

}
