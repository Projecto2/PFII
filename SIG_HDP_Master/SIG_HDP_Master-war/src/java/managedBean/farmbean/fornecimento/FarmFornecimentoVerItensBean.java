/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.fornecimento;

import entidade.FarmFornecimento;
import entidade.FarmFornecimentoHasProduto;
import entidade.FarmTipoFornecimento;
import entidade.GrlFornecedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.segbean.SegLoginBean;
import sessao.FarmFornecimentoFacade;
import sessao.FarmFornecimentoHasProdutoFacade;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFornecimentoVerItensBean implements Serializable
{
   @EJB
   private FarmFornecimentoFacade fornecimentoFacade;
   @EJB
   private FarmFornecimentoHasProdutoFacade fornecimentoHasProdutoFacade;
   
   private List<FarmFornecimentoHasProduto> itensFornecimento;
   private FarmFornecimento fornecimento;

   /**
    * Creates a new instance of FarmFornecimentoVerItens
    */
   public FarmFornecimentoVerItensBean()
   {
      itensFornecimento = new ArrayList<>();
      fornecimento = getInstanciaFornecimento();
   }
   
   public FarmFornecimento getInstanciaFornecimento()
   {
      setFornecimento(new FarmFornecimento());
//      getFornecimento().setFkIdArmazemDestino(new FarmLocalArmazenamento());
      getFornecimento().setFkIdFornecedor(new GrlFornecedor());
      getFornecimento().setFkIdTipoFornecimento(new FarmTipoFornecimento());
      getFornecimento().setFkIdFuncionarioCadastrou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      getFornecimento().setCustoTotal(0.0);
      return getFornecimento();
   }
   
   public void pesquisarItens()
   {
      System.out.println("pesquisando item para o forneciento "+fornecimento);
      itensFornecimento = fornecimentoHasProdutoFacade.findItensFornecimento(fornecimento);
   }

   /**
    * @return the itensFornecimento
    */
   public List<FarmFornecimentoHasProduto> getItensFornecimento()
   {
      pesquisarItens();
      return itensFornecimento;
   }

   /**
    * @return the fornecimento
    */
   public FarmFornecimento getFornecimento()
   {
      return fornecimento;
   }

   /**
    * @param itensFornecimento the itensFornecimento to set
    */
   public void setItensFornecimento(List<FarmFornecimentoHasProduto> itensFornecimento)
   {
      this.itensFornecimento = itensFornecimento;
   }

   /**
    * @param fornecimento the fornecimento to set
    */
   public void setFornecimento(FarmFornecimento fornecimento)
   {
      System.out.println("fazendo set do fornecimento: "+fornecimento);
      this.fornecimento = fornecimento;
   }
   
   
}
