/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.carregamentosexcel;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.grlbean.carregamentoExcel.GrlMarcaProdutoExcelBean;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmCarregarTabelasBean implements Serializable
{

   /**
    * Creates a new instance of FarmCarregarTabelasBean
    */
   public FarmCarregarTabelasBean()
   {
   }

   public static void actualizarFarmaciaTabelas()
   {
      FarmAvisoExcelBean.getInstanciaBean().carregarAvisoTabela();
      FarmCategoriaDeMedicamentoExcelBean.getInstanciaBean().carregarCategoriaDeMedicamentoTabela();
      FarmContraIndicacaoExcelBean.getInstanciaBean().carregarContraIndicacaoTabela();
      FarmEfeitoSecundarioExcelBean.getInstanciaBean().carregarEfeitoSecundarioTabela();
      FarmTurnoExcelBean.getInstanciaBean().carregarTurnoTabela();
      FarmObservacaoExcelBean.getInstanciaBean().carregarObservacaoTabela();
      FarmTipoUnidadeMedidaExcelBean.getInstanciaBean().carregarTipoUnidadeMedidaTabela();
      FarmUnidadeDeMedidaExcelBean.getInstanciaBean().carregarUnidadeDeMedidaTabela();
      FarmTipoFornecimentoExcelBean.getInstanciaBean().carregarTipoFornecimentoTabela();
      FarmTipoLocalArmazenamentoExcelBean.getInstanciaBean().carregarTipoLocalArmazenamentoTabela();
      FarmLocalArmazenamentoExcelBean.getInstanciaBean().carregarLocalArmazenamentoTabela();
      FarmTipoNotificacaoExcelBean.getInstanciaBean().carregarTipoNotificacaoTabela();
      FarmTipoProdutoExcelBean.getInstanciaBean().carregarTipoProdutoTabela();
      FarmTipoQuantidadeExcelBean.getInstanciaBean().carregarTipoQuantidadeTabela();
      FarmEstadoExcelBean.getInstanciaBean().carregarEstadoTabela();
      FarmEstadoDePedidoExcelBean.getInstanciaBean().carregarEstadoDePedidoTabela();
      FarmFarmacoExcelBean.getInstanciaBean().carregarFarmacoTabela();
      FarmFormaFarmaceuticaExcelBean.getInstanciaBean().carregarFormaFarmaceuticaTabela();
      FarmIndicacaoExcelBean.getInstanciaBean().carregarIndicacaoTabela();
      FarmOutroComponenteExcelBean.getInstanciaBean().carregarOutroComponenteTabela();
      FarmViaAdministracaoExcelBean.getInstanciaBean().carregarViaAdministracaoTabela();
      
      GrlMarcaProdutoExcelBean.getInstanciaBean().carregarMarcaProdutoTabela();
   }
}
