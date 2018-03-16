/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.notificacoes;

import entidade.FarmEstado;
import entidade.FarmEstadoNotificacao;
import entidade.FarmLocalArmazenamento;
import entidade.FarmLoteProduto;
import entidade.FarmLoteProdutoHasLocalArmazenamento;
import entidade.FarmNotificacao;
import entidade.FarmProduto;
import entidade.FarmTipoNotificacao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;
import managedBean.farmbean.tabelasAuxiliares.FarmStockRupturaBean;
import sessao.FarmEstadoNotificacaoFacade;
import sessao.FarmLocalArmazenamentoFacade;
import sessao.FarmLoteProdutoHasLocalArmazenamentoFacade;
import sessao.FarmNotificacaoFacade;
import sessao.FarmProdutoFacade;
import util.Constantes;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmNotificacaoBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   private FarmProdutoFacade produtoFacade;
   @EJB
   private FarmNotificacaoFacade notificacaoFacade;
   @EJB
   private FarmEstadoNotificacaoFacade estadoNotificacaoFacade;
   @EJB
   private FarmLocalArmazenamentoFacade localFacade;
   @EJB
   private FarmLoteProdutoHasLocalArmazenamentoFacade loteProdutoHasLocalArmazenamentoFacade;

   private FarmNotificacao notificacao;
   private List<FarmNotificacao> listaNotificacoes;

   FacesContext context = FacesContext.getCurrentInstance();
   FarmStockRupturaBean farmStockRupturaBean = (FarmStockRupturaBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmStockRupturaBean");

   /**
    * Creates a new instance of FarmNotificacaoBean
    */
   public FarmNotificacaoBean()
   {
   }

   public static FarmNotificacaoBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmNotificacaoBean farmNotificacaoBean
              = (FarmNotificacaoBean) context.getELContext().
              getELResolver().getValue(FacesContext.getCurrentInstance().
                      getELContext(), null, "farmNotificacaoBean");

      return farmNotificacaoBean;
   }

   public FarmNotificacao getInstanciaNotificacao()
   {
      FarmNotificacao notificacaoAux = new FarmNotificacao();
      notificacaoAux.setFkIdEstadoNotificacao(new FarmEstadoNotificacao());
      notificacaoAux.setFkIdLoteProduto(new FarmLoteProduto());
      notificacaoAux.setFkIdProduto(new FarmProduto());
      notificacaoAux.setFkIdTipoNotificacao(new FarmTipoNotificacao());
      return notificacaoAux;
   }

   public void pesquisarNotificacoes()
   {
      System.out.println("a pesquisar notificacoes...");
      eliminarNotificacoes();
      gerarNotificacoes();
      listaNotificacoes = notificacaoFacade.findNotificacaoOrderByTipo();
      
      System.out.println("notificacacoes geradas: " + listaNotificacoes.size());
   }

   public void gerarNotificacoes()
   {
      gerarNotificacoesDoTipoValidade();
      gerarNotificacoesDoTipoRuptura();
   }

   public void eliminarNotificacoes()
   {
      List<FarmNotificacao> listaNotif = notificacaoFacade.findAll();
      List<FarmEstadoNotificacao> listaEstado = estadoNotificacaoFacade.findAll();
      for (FarmNotificacao notif : listaNotif)
      {
         notificacaoFacade.remove(notif);
      }

      for (FarmEstadoNotificacao estado : listaEstado)
      {
         estadoNotificacaoFacade.remove(estado);
      }
   }

   public void pesquisarNotificacoesDoDia()
   {
      if (!getListaNotificacoes().isEmpty())
      {
         String dataUltimaNotificacao = new SimpleDateFormat("dd-MM-yyyy").format(listaNotificacoes.get(0).getDataCadastro());
         String dataHoje = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

         System.out.println("data ultima notificacao: " + dataUltimaNotificacao);
         System.out.println("data hoje : " + dataHoje);

         if (!dataUltimaNotificacao.equals(dataHoje))
         {
            pesquisarNotificacoes();
         }
      }
      System.out.println("pesquisarNotificacoesDoDia esta vazia");
   }

   public String getColorSize()
   {
      pesquisarNotificacoesDoDia();
      if (listaNotificacoes.isEmpty())
         return " WHITE";
      return "YELLOW";
   }

   public String getColorTitle(FarmNotificacao notificacao)
   {
      if (notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == Constantes.FARM_TIPO_NOTIF_LOTE_EXPIRADO
              || notificacao.getFkIdTipoNotificacao().getPkIdTipoNotificacao() == Constantes.FARM_TIPO_NOTIF_POS_RUPTURA)
         return " RED";

      return " ORANGE";
   }

   public void notificarLoteExpirado(FarmLoteProdutoHasLocalArmazenamento localHasProduto)
   {
      try
      {
         userTransaction.begin();
         notificacao = getInstanciaNotificacao();

         notificacao.setTitulo("Produto com lote " + localHasProduto.getFkIdLoteProduto().getNumeroLote() + " expirado no(a) " + localHasProduto.getFkIdLocalArmazenamento().getDescricao() + ".");
         notificacao.setCorpo("O produto "
                 + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getDescricao()
                 + " " + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getDosagem()
                 + " " + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura()
                 + " expirou a " + new SimpleDateFormat("dd-MM-yyyy").format(localHasProduto.getFkIdLoteProduto().getDataValidade())
                 + ". Tem stock de " + localHasProduto.getQuantidadeStock() + " unidades no(a) " + localHasProduto.getFkIdLocalArmazenamento().getDescricao()
                 + ".");

         notificacao.setFkIdLoteProduto(localHasProduto.getFkIdLoteProduto());
         notificacao.setFkIdTipoNotificacao(new FarmTipoNotificacao(Constantes.FARM_TIPO_NOTIF_LOTE_EXPIRADO));

         boolean jaExiste = false;
         for (FarmNotificacao notif : notificacaoFacade.findNotificacao(notificacao))
         {

            if (notif.getTitulo().equals(notificacao.getTitulo())
                    && notif.getFkIdEstadoNotificacao().getDataAgendamento() == null
                    && notif.getFkIdEstadoNotificacao().getDataResolucao() == null)
               jaExiste = true;
         }
         if (!jaExiste)
         {
            FarmEstadoNotificacao estadoNotificacao = new FarmEstadoNotificacao();
            estadoNotificacao.setDataResolucao(null);
            estadoNotificacao.setFkIdEstado(new FarmEstado(Constantes.FARM_ESTADO_NOTIF_PENDENTE));
            estadoNotificacao.setFkIdFuncionario(null);
            estadoNotificacao.setDataAgendamento(null);
            estadoNotificacaoFacade.create(estadoNotificacao);

            notificacao.setFkIdEstadoNotificacao(estadoNotificacao);
            notificacao.setDataCadastro(new Date());
            notificacao.setFkIdProduto(null);
            notificacaoFacade.create(notificacao);
            userTransaction.commit();
         }
      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {
            //System.out.println(ex.toString());
         }
      }
   }

   public void notificarLoteQuaseExpirado(FarmLoteProdutoHasLocalArmazenamento localHasProduto)
   {
      try
      {
         userTransaction.begin();
         notificacao = getInstanciaNotificacao();

         notificacao.setTitulo("Produto com lote " + localHasProduto.getFkIdLoteProduto().getNumeroLote() + " com data de validade próxima no(a) " + localHasProduto.getFkIdLocalArmazenamento().getDescricao() + ".");
         notificacao.setCorpo("O produto "
                 + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getDescricao()
                 + " " + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getDosagem()
                 + " " + localHasProduto.getFkIdLoteProduto().getFkIdProduto().getFkIdUnidadeMedida().getAbreviatura()
                 + " vai expirar a " + new SimpleDateFormat("dd-MM-yyyy").format(localHasProduto.getFkIdLoteProduto().getDataValidade())
                 + ". Tem stock de " + localHasProduto.getQuantidadeStock() + " unidades no(a) " + localHasProduto.getFkIdLocalArmazenamento().getDescricao()
                 + ".");

         notificacao.setFkIdLoteProduto(localHasProduto.getFkIdLoteProduto());
         notificacao.setFkIdTipoNotificacao(new FarmTipoNotificacao(Constantes.FARM_TIPO_NOTIF_LOTE_QUASE_EXPIRADO));

         boolean jaExiste = false;
         for (FarmNotificacao notif : notificacaoFacade.findNotificacao(notificacao))
         {

            if (notif.getTitulo().equals(notificacao.getTitulo())
                    && notif.getFkIdEstadoNotificacao().getDataAgendamento() == null
                    && notif.getFkIdEstadoNotificacao().getDataResolucao() == null)
               jaExiste = true;
         }
         if (!jaExiste)
         {
            FarmEstadoNotificacao estadoNotificacao = new FarmEstadoNotificacao();
            estadoNotificacao.setDataResolucao(null);
            estadoNotificacao.setFkIdEstado(new FarmEstado(Constantes.FARM_ESTADO_NOTIF_PENDENTE));
            estadoNotificacao.setFkIdFuncionario(null);
            estadoNotificacao.setDataAgendamento(null);
            estadoNotificacaoFacade.create(estadoNotificacao);

            notificacao.setFkIdEstadoNotificacao(estadoNotificacao);
            notificacao.setDataCadastro(new Date());
            notificacao.setFkIdProduto(null);
            notificacaoFacade.create(notificacao);
            userTransaction.commit();
         }

      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {
            //System.out.println(ex.toString());
         }
      }
   }

   public void notificarProdutoEmPreRuptura(FarmProduto produto, FarmLocalArmazenamento local, int quantidadeActual, int quantidadeMinimaPermitida)
   {
      try
      {
         userTransaction.begin();
         notificacao = getInstanciaNotificacao();

         notificacao.setTitulo("Produto " + produto.getDescricao() + " em Pré-ruptura de stock no(a)" + local.getDescricao() + ".");
         notificacao.setCorpo("O produto "
                 + produto.getDescricao()
                 + " " + produto.getDosagem()
                 + " " + produto.getFkIdUnidadeMedida().getAbreviatura()
                 + " encontra-se em  Pré-ruptura de stock no(a) " + local.getDescricao()
                 + ". Com quantidade actual de " + quantidadeActual + " unidades. "
                 + "A quantidade mínima permitida é de " + quantidadeMinimaPermitida + ".");

         notificacao.setFkIdProduto(produto);
         notificacao.setFkIdTipoNotificacao(new FarmTipoNotificacao(Constantes.FARM_TIPO_NOTIF_PRE_RUPTURA));

         boolean jaExiste = false;
         for (FarmNotificacao notif : notificacaoFacade.findNotificacao(notificacao))
         {

            if (notif.getTitulo().equals(notificacao.getTitulo())
                    && notif.getFkIdEstadoNotificacao().getDataAgendamento() == null
                    && notif.getFkIdEstadoNotificacao().getDataResolucao() == null)
               jaExiste = true;
         }
         if (!jaExiste)
         {
            FarmEstadoNotificacao estadoNotificacao = new FarmEstadoNotificacao();
            estadoNotificacao.setDataResolucao(null);
            estadoNotificacao.setFkIdEstado(new FarmEstado(Constantes.FARM_ESTADO_NOTIF_PENDENTE));
            estadoNotificacao.setFkIdFuncionario(null);
            estadoNotificacao.setDataAgendamento(null);
            estadoNotificacaoFacade.create(estadoNotificacao);

            notificacao.setFkIdEstadoNotificacao(estadoNotificacao);
            notificacao.setDataCadastro(new Date());
            notificacao.setFkIdLoteProduto(null);
            notificacaoFacade.create(notificacao);
            userTransaction.commit();
         }

      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {
            //System.out.println(ex.toString());
         }
      }
   }

   public void notificarProdutoEmPosRuptura(FarmProduto produto, FarmLocalArmazenamento local, int quantidadeActual, int quantidadeMinimaPermitida)
   {
      try
      {
         userTransaction.begin();
         notificacao = getInstanciaNotificacao();

         notificacao.setTitulo("Produto " + produto.getDescricao() + " em Pós-ruptura de stock no(a)" + local.getDescricao() + ".");
         notificacao.setCorpo("O produto "
                 + produto.getDescricao()
                 + " " + produto.getDosagem()
                 + " " + produto.getFkIdUnidadeMedida().getAbreviatura()
                 + " encontra-se em  Pós-ruptura de stock no(a) " + local.getDescricao()
                 + ". Com quantidade actual de " + quantidadeActual + " unidades. "
                 + "A quantidade mínima permitida é de " + quantidadeMinimaPermitida + ".");

         notificacao.setFkIdProduto(produto);
         notificacao.setFkIdTipoNotificacao(new FarmTipoNotificacao(Constantes.FARM_TIPO_NOTIF_POS_RUPTURA));

         boolean jaExiste = false;
         for (FarmNotificacao notif : notificacaoFacade.findNotificacao(notificacao))
         {
            if (notif.getTitulo().equals(notificacao.getTitulo())
                    && notif.getFkIdEstadoNotificacao().getDataAgendamento() == null
                    && notif.getFkIdEstadoNotificacao().getDataResolucao() == null)
               jaExiste = true;
         }
         if (!jaExiste)
         {
            FarmEstadoNotificacao estadoNotificacao = new FarmEstadoNotificacao();
            estadoNotificacao.setDataResolucao(null);
            estadoNotificacao.setFkIdEstado(new FarmEstado(Constantes.FARM_ESTADO_NOTIF_PENDENTE));
            estadoNotificacao.setFkIdFuncionario(null);
            estadoNotificacao.setDataAgendamento(null);
            estadoNotificacaoFacade.create(estadoNotificacao);

            notificacao.setFkIdEstadoNotificacao(estadoNotificacao);
            notificacao.setDataCadastro(new Date());
            notificacao.setFkIdLoteProduto(null);
            notificacaoFacade.create(notificacao);
            userTransaction.commit();
         }

      }
      catch (Exception e)
      {
         try
         {
            userTransaction.rollback();
         }
         catch (Exception ex)
         {
            //System.out.println(ex.toString());
         }
      }
   }

   public void gerarNotificacoesDoTipoValidade()
   {
      for (FarmLoteProdutoHasLocalArmazenamento localHasProduto : loteProdutoHasLocalArmazenamentoFacade.findAll())
      {
         Date dataHoje = new Date();
         GregorianCalendar addDate = new GregorianCalendar();
         addDate.add(Calendar.MONTH, Constantes.FARM_MESES_DE_ANTECEDENCIA);
         Date dateIntermediaria = addDate.getTime();

         if (localHasProduto.getQuantidadeStock() > 0)
         {
            if (localHasProduto.getFkIdLoteProduto().getDataValidade().before(dataHoje))
            {
               notificarLoteExpirado(localHasProduto);
            }
            else if (localHasProduto.getFkIdLoteProduto().getDataValidade().after(dataHoje)
                    && localHasProduto.getFkIdLoteProduto().getDataValidade().before(dateIntermediaria))
            {
               notificarLoteQuaseExpirado(localHasProduto);
            }
         }
      }
   }

   public int getQuantidadeItem(List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto)
   {
      int qtd = 0;
      for (FarmLoteProdutoHasLocalArmazenamento aux : listaLotesPorProduto)
      {
         qtd += aux.getQuantidadeStock();
      }
      return qtd;
   }

   public int getQuantidadeMinimaPermitida(List<FarmLoteProdutoHasLocalArmazenamento> listaLotesPorProduto)
   {
      if (listaLotesPorProduto.isEmpty() || listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida() == null)
         return 0;
      return listaLotesPorProduto.get(0).getQuantidadeMinimaPermitida();
   }

   public void gerarNotificacoesDoTipoRuptura()
   {
      for (FarmProduto produto : produtoFacade.findAll())
      {
         for (FarmLocalArmazenamento local : localFacade.findAll())
         {
            List<FarmLoteProdutoHasLocalArmazenamento> lotesNoLocal = loteProdutoHasLocalArmazenamentoFacade.findProdutoNoLocal(local, produto);
            if (!lotesNoLocal.isEmpty())
            {
               int qtdActual = getQuantidadeItem(lotesNoLocal);
               int qtdMin = getQuantidadeMinimaPermitida(lotesNoLocal);

               if (qtdActual <= qtdMin * .5)
                  notificarProdutoEmPosRuptura(produto, local, qtdActual, qtdMin);
               else if (qtdActual <= qtdMin * .25)
                  notificarProdutoEmPreRuptura(produto, local, qtdActual, qtdMin);
            }
         }
      }
   }

   public FarmNotificacao getNotificacao()
   {
      if (notificacao == null)
         notificacao = getInstanciaNotificacao();
      return notificacao;
   }

   public void setNotificacao(FarmNotificacao notificacao)
   {
      this.notificacao = notificacao;
   }

   public List<FarmNotificacao> getListaNotificacoes()
   {
      if (listaNotificacoes == null)
         listaNotificacoes = notificacaoFacade.findAll();
      return listaNotificacoes;
   }

   public void setListaNotificacoes(List<FarmNotificacao> listaNotificacoes)
   {
      this.listaNotificacoes = listaNotificacoes;
   }

}
