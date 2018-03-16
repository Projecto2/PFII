/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.quarentena;

import entidade.FarmFichaStock;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmQuarentena;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import managedBean.segbean.SegLoginBean;
import sessao.FarmFichaStockFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmQuarentenaFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmQuarentenaBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmQuarentenaFacade quarentenaFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteHaslocalFacade;
   @EJB
   private FarmFichaStockFacade fichaStockFacade;
   private FarmQuarentena quarentena;

   FacesContext context = FacesContext.getCurrentInstance();
   HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
   HttpSession sessao = request.getSession();

   private final String destinoFichaDeStock;

   /**
    * Creates a new instance of FarmQuarentenaBean
    */
   public FarmQuarentenaBean()
   {
      this.destinoFichaDeStock = "Quarentena";
   }

   public FarmQuarentena getInstanciaQuarentena()
   {
      FarmQuarentena quarentenaAux = new FarmQuarentena();

      sessao = request.getSession();

      quarentenaAux.setFkIdFuncionario(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());

      quarentenaAux.setFkIdLocalOrigem(new FarmLocalArmazenamento());
      quarentenaAux.setFkIdLoteProduto(new FarmLoteProduto());
      quarentenaAux.setMotivo("Lote fora de Prazo");
      return quarentenaAux;
   }

   public void definirLote(FarmLoteProduto lote)
   {
      quarentena.setFkIdLoteProduto(lote);
   }

   public void criar(int quantidade, FarmLocalArmazenamento localOrigem)
   {
      try
      {
         userTransaction.begin();
         quarentena.setDataCadastro(new Date());
         quarentena.setQuantidade(quantidade);
         quarentena.setFkIdLocalOrigem(localOrigem);

         //decrementar
         FarmLoteProdutoHasLocalArmazenamento itemNoLocal_Origem = loteHaslocalFacade.findLoteProdutoNoLocal(localOrigem, quarentena.getFkIdLoteProduto());

         itemNoLocal_Origem.setQuantidadeStock(itemNoLocal_Origem.getQuantidadeStock() - quantidade);
         loteHaslocalFacade.edit(itemNoLocal_Origem);

         quarentenaFacade.create(quarentena);
         //ficha de stock
         FarmFichaStock fichaParaOrigem = new FarmFichaStock();

         fichaParaOrigem.setDataMovimento(new Date());
         fichaParaOrigem.setOrigemOuDestino(destinoFichaDeStock);
         fichaParaOrigem.setFkIdLoteProduto(quarentena.getFkIdLoteProduto());
         fichaParaOrigem.setFkIdLocalArmazenamento(localOrigem);
         fichaParaOrigem.setEntradas(0);
         fichaParaOrigem.setSaidas(quantidade);
         fichaParaOrigem.setQuantidadeRestante(itemNoLocal_Origem.getQuantidadeStock());
         fichaParaOrigem.setFkIdFuncionario(quarentena.getFkIdFuncionario());

         fichaStockFacade.create(fichaParaOrigem);

         userTransaction.commit();
//         FarmNotificacaoBean.getInstanciaBean().pesquisarNotificacoes();
//         FarmGraficosBean.getInstanciaBean().actualizarGraficos();
         Mensagem.sucessoMsg("Item movido com sucesso para a Quarentena.");
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um Erro ao Mover o Produto para a Quarentena. Tente novamente.");
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {

         }
      }
   }

   public FarmQuarentena getQuarentena()
   {
      if (quarentena == null)
         quarentena = getInstanciaQuarentena();
      return quarentena;
   }

   public void setQuarentena(FarmQuarentena quarentena)
   {
      this.quarentena = quarentena;
   }

}
