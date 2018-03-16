/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.produto;

import entidade.FarmAviso;
import entidade.FarmCategoriaMedicamento;
import entidade.FarmContraIndicacao;
import entidade.FarmEfeitoSecundario;
import entidade.FarmFarmaco;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmIndicacao;
import entidade.FarmObservacao;
import entidade.FarmOutroComponente;
import entidade.FarmProduto;
import entidade.FarmProdutoHasAviso;
import entidade.FarmProdutoHasContraIndicacao;
import entidade.FarmProdutoHasEfeitoSecundario;
import entidade.FarmProdutoHasFarmaco;
import entidade.FarmProdutoHasIndicacao;
import entidade.FarmProdutoHasObservacao;
import entidade.FarmProdutoHasOutroComponente;
import entidade.FarmTipoProduto;
import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import managedBean.segbean.SegLoginBean;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import sessao.FarmProdutoFacade;
import sessao.FarmProdutoHasAvisoFacade;
import sessao.FarmProdutoHasContraIndicacaoFacade;
import sessao.FarmProdutoHasEfeitoSecundarioFacade;
import sessao.FarmProdutoHasFarmacoFacade;
import sessao.FarmProdutoHasIndicacaoFacade;
import sessao.FarmProdutoHasObservacaoFacade;
import sessao.FarmProdutoHasOutroComponenteFacade;
import sessao.FarmUnidadeMedidaFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmProdutoNovoBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   @EJB
   private FarmUnidadeMedidaFacade unidadeMedidaFacade;
   @EJB
   FarmProdutoFacade produtoFacade;
   private FarmProduto produto, produtoMG, produtoMGV;
   private TreeNode categoriaTreeNode;

   @EJB
   private FarmProdutoHasEfeitoSecundarioFacade efeitoSecundarioFacade;
   private List<Integer> listaEfeitosSecundarios, listaEfeitosSecundariosMG, listaEfeitosSecundariosMGV;

   @EJB
   private FarmProdutoHasFarmacoFacade farmacoFacade;
   private List<Integer> listaFarmacos, listaFarmacosMGV;

   @EJB
   private FarmProdutoHasOutroComponenteFacade outroComponenteFacade;
   private List<Integer> listaOutrosComponentes, listaOutrosComponentesMG, listaOutrosComponentesMGV;

   @EJB
   private FarmProdutoHasAvisoFacade avisoFacade;
   private List<Integer> listaAviso, listaAvisoMGV, listaAvisoMG;

   @EJB
   private FarmProdutoHasContraIndicacaoFacade contraIndicacaoFacade;
   private List<Integer> listaContraIndicacao, listaContraIndicacaoMGV, listaContraIndicacaoMG;

   @EJB
   private FarmProdutoHasIndicacaoFacade indicacaoFacade;
   private List<Integer> listaIndicacao, listaIndicacaoMGV, listaIndicacaoMG;

   @EJB
   private FarmProdutoHasObservacaoFacade observacaoFacade;
   private List<Integer> listaObservacao, listaObservacaoMGV, listaObservacaoMG;
   /**
    * Creates a new instance of FarmProdutoNovoBean
    */
   public FarmProdutoNovoBean()
   {
   }

   public static FarmProdutoNovoBean getInstanciaBean()
   {
      FacesContext context = FacesContext.getCurrentInstance();
      FarmProdutoNovoBean farmProdutoNovoBean = (FarmProdutoNovoBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "farmProdutoNovoBean");

      return farmProdutoNovoBean;
   }
   
   public void criarProdutoM()
   {
      try
      {
         userTransaction.begin();
         produto.setFkIdCategoriaMedicamento((FarmCategoriaMedicamento) categoriaTreeNode.getData());

         produtoFacade.create(produto);

         for (Object idFarmaco : getListaFarmacos())
         {
            FarmProdutoHasFarmaco farmacoAux = new FarmProdutoHasFarmaco();
            farmacoAux.setFkIdFarmaco(new FarmFarmaco(Integer.parseInt("" + idFarmaco)));
            farmacoAux.setFkIdProduto(produto);
            farmacoFacade.create(farmacoAux);
         }

         for (Object idComponente : getListaOutrosComponentes())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produto);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundarios())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produto);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAviso())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produto);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacao())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produto);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacao())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produto);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacao())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produto);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produto = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Produto guardado com sucesso!");
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }

   public void criarProdutoMG()
   {
      try
      {
         userTransaction.begin();
         produtoMG.setFkIdCategoriaMedicamento(null);
         produtoMG.setFkIdTipoProduto(new FarmTipoProduto(Constantes.FARM_TIPO_PRODUTO_MATERIAL_GASTAVEL));
         produtoMG.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(Constantes.FARM_N_A));
         produtoMG.setFkIdViaAdministracao(new FarmViaAdministracao(Constantes.FARM_N_A));
         produtoFacade.create(produtoMG);

         for (Object idComponente : getListaOutrosComponentesMG())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produtoMG);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundariosMG())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produtoMG);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAvisoMG())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produtoMG);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacaoMG())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produtoMG);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacaoMG())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produtoMG);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacaoMG())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produtoMG);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produtoMG = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Produto guardado com sucesso!");
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }

   public void criarProdutoMGV()
   {
      try
      {
         produtoMGV.setFkIdCategoriaMedicamento((FarmCategoriaMedicamento) categoriaTreeNode.getData());
         produtoMGV.setFkIdTipoProduto(new FarmTipoProduto(Constantes.FARM_TIPO_PRODUTO_MEDICAMENTO_GRANDE_VOLUME));
         produtoMGV.setFkIdViaAdministracao(new FarmViaAdministracao(Constantes.FARM_N_A));
         userTransaction.begin();
         produtoFacade.create(produtoMGV);

         for (Object idFarmaco : getListaFarmacosMGV())
         {
            FarmProdutoHasFarmaco farmacoAux = new FarmProdutoHasFarmaco();
            farmacoAux.setFkIdFarmaco(new FarmFarmaco(Integer.parseInt("" + idFarmaco)));
            farmacoAux.setFkIdProduto(produtoMGV);
            farmacoFacade.create(farmacoAux);
         }

         for (Object idComponente : getListaOutrosComponentesMGV())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produtoMGV);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundariosMGV())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produtoMGV);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAvisoMGV())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produtoMGV);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacaoMGV())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produtoMGV);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacaoMGV())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produtoMGV);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacaoMGV())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produtoMGV);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produtoMGV = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Produto guardado com sucesso!");
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }

   public boolean renderizarPainelTipoM()
   {
      if (produto.getFkIdTipoProduto().getPkIdTipoProduto() == null)
         return false;

      return produto.getFkIdTipoProduto().getPkIdTipoProduto() == Constantes.FARM_TIPO_PRODUTOMEDICAMENTO;
   }

   public boolean renderizarPainelTipoMG()
   {
      if (produto.getFkIdTipoProduto().getPkIdTipoProduto() == null)
         return false;
      return produto.getFkIdTipoProduto().getPkIdTipoProduto() == Constantes.FARM_TIPO_PRODUTO_MATERIAL_GASTAVEL;
   }

   public boolean renderizarPainelTipoMGV()
   {
      if (produto.getFkIdTipoProduto().getPkIdTipoProduto() == null)
         return false;
      return produto.getFkIdTipoProduto().getPkIdTipoProduto() == Constantes.FARM_TIPO_PRODUTO_MEDICAMENTO_GRANDE_VOLUME;
   }

   public void onNodeSelect(NodeSelectEvent event)
   {
      String node = event.getTreeNode().getData().toString();
      if (categoriaTreeNode.getChildCount() > 0)
         Mensagem.warnMsg("Nao pode seleccionar uma categoria raiz.");
   }

   public FarmProduto getInstanciaProduto()
   {
      FarmProduto produtoAux = new FarmProduto();
      produtoAux.setFkIdTipoProduto(new FarmTipoProduto(Constantes.FARM_TIPO_PRODUTOMEDICAMENTO));
      produtoAux.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(FarmConfiguracoesBean.FORMA_FARMACEUTICA));
      produtoAux.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento(FarmConfiguracoesBean.CATEGORIA_MEDICAMENTO));
      produtoAux.setFkIdViaAdministracao(new FarmViaAdministracao(FarmConfiguracoesBean.VIA_ADMINISTRACAO));
      produtoAux.setNomeGenerico("Não especificado");
      produtoAux.setAvisos("Não especificados");
      produtoAux.setObservacoes("Não especificadas");
      produtoAux.setIndicacoes("Não especificadas");
      produtoAux.setContraIndicacoes("Não especificadas");
      produtoAux.setNomeGenerico("Não especificado");
      produtoAux.setNomeGenerico("Não especificado");
      produtoAux.setNomeGenerico("Não especificado");
      FarmUnidadeMedida unidade = new FarmUnidadeMedida(FarmConfiguracoesBean.UNIDADE_MEDIDA);
      unidade.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida(FarmConfiguracoesBean.TIPO_UNIDADE_MEDIDA));
      produtoAux.setFkIdUnidadeMedida(unidade);
      produtoAux.setDataHoraCadastro(new Date());
      produtoAux.setFkIdFuncionarioCadastrou(SegLoginBean.getInstanciaBean().getSessaoActual().getFkIdFuncionario());
      return produtoAux;
   }
   
   public static FarmProduto getInstancia()
   {
      FarmProduto produtoAux = new FarmProduto();
      produtoAux.setFkIdTipoProduto(new FarmTipoProduto());
      produtoAux.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica());
      produtoAux.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
      produtoAux.setFkIdViaAdministracao(new FarmViaAdministracao());
      produtoAux.setFkIdUnidadeMedida(new FarmUnidadeMedida());
      produtoAux.setFkIdFuncionarioCadastrou(new RhFuncionario());
      return produtoAux;
   }
   
   /**
    * @return the produto
    */
   public FarmProduto getProduto()
   {
      if (produto == null)
         produto = getInstanciaProduto();
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
    * @return the categoriaTreeNode
    */
   public TreeNode getCategoriaTreeNode()
   {
      return categoriaTreeNode;
   }

   /**
    * @param categoriaTreeNode the categoriaTreeNode to set
    */
   public void setCategoriaTreeNode(TreeNode categoriaTreeNode)
   {
      this.categoriaTreeNode = categoriaTreeNode;
   }

   public List<Integer> getListaEfeitosSecundarios()
   {
      return listaEfeitosSecundarios;
   }

   public void setListaEfeitosSecundarios(List<Integer> listaEfeitosSecundarios)
   {
      this.listaEfeitosSecundarios = listaEfeitosSecundarios;
   }

   /**
    * @return the listaFarmacos
    */
   public List<Integer> getListaFarmacos()
   {
      return listaFarmacos;
   }

   /**
    * @param listaFarmacos the listaFarmacos to set
    */
   public void setListaFarmacos(List<Integer> listaFarmacos)
   {
      this.listaFarmacos = listaFarmacos;
   }

   /**
    * @return the outroComponenteFacade
    */
   public FarmProdutoHasOutroComponenteFacade getOutroComponenteFacade()
   {
      return outroComponenteFacade;
   }

   /**
    * @param outroComponenteFacade the outroComponenteFacade to set
    */
   public void setOutroComponenteFacade(FarmProdutoHasOutroComponenteFacade outroComponenteFacade)
   {
      this.outroComponenteFacade = outroComponenteFacade;
   }

   /**
    * @return the listaOutrosComponentes
    */
   public List<Integer> getListaOutrosComponentes()
   {
      return listaOutrosComponentes;
   }

   /**
    * @param listaOutrosComponentes the listaOutrosComponentes to set
    */
   public void setListaOutrosComponentes(List<Integer> listaOutrosComponentes)
   {
      this.listaOutrosComponentes = listaOutrosComponentes;
   }

   public FarmProduto getProdutoMG()
   {
      if (produtoMG == null)
         produtoMG = getInstanciaProduto();
      return produtoMG;
   }

   public void setProdutoMG(FarmProduto produtoMG)
   {
      this.produtoMG = produtoMG;
   }

   public FarmProduto getProdutoMGV()
   {
      if (produtoMGV == null)
         produtoMGV = getInstanciaProduto();
      return produtoMGV;
   }

   public void setProdutoMGV(FarmProduto produtoMGV)
   {
      this.produtoMGV = produtoMGV;
   }

   public List<Integer> getListaEfeitosSecundariosMG()
   {
      return listaEfeitosSecundariosMG;
   }

   public void setListaEfeitosSecundariosMG(List<Integer> listaEfeitosSecundariosMG)
   {
      this.listaEfeitosSecundariosMG = listaEfeitosSecundariosMG;
   }

   public List<Integer> getListaEfeitosSecundariosMGV()
   {
      return listaEfeitosSecundariosMGV;
   }

   public void setListaEfeitosSecundariosMGV(List<Integer> listaEfeitosSecundariosMGV)
   {
      this.listaEfeitosSecundariosMGV = listaEfeitosSecundariosMGV;
   }

   public List<Integer> getListaFarmacosMGV()
   {
      return listaFarmacosMGV;
   }

   public void setListaFarmacosMGV(List<Integer> listaFarmacosMGV)
   {
      this.listaFarmacosMGV = listaFarmacosMGV;
   }

   public List<Integer> getListaOutrosComponentesMG()
   {
      return listaOutrosComponentesMG;
   }

   public void setListaOutrosComponentesMG(List<Integer> listaOutrosComponentesMG)
   {
      this.listaOutrosComponentesMG = listaOutrosComponentesMG;
   }

   public List<Integer> getListaOutrosComponentesMGV()
   {
      return listaOutrosComponentesMGV;
   }

   public void setListaOutrosComponentesMGV(List<Integer> listaOutrosComponentesMGV)
   {
      this.listaOutrosComponentesMGV = listaOutrosComponentesMGV;
   }

   public void setProdutoEditar(FarmProduto produtoEditar)
   {
      produto = produtoEditar;
      produtoMG = produtoEditar;
      produtoMGV = produtoEditar;
   }

   public List<Integer> getListaAviso()
   {
      return listaAviso;
   }

   public void setListaAviso(List<Integer> listaAviso)
   {
      this.listaAviso = listaAviso;
   }

   public List<Integer> getListaAvisoMGV()
   {
      return listaAvisoMGV;
   }

   public void setListaAvisoMGV(List<Integer> listaAvisoMGV)
   {
      this.listaAvisoMGV = listaAvisoMGV;
   }

   public List<Integer> getListaAvisoMG()
   {
      return listaAvisoMG;
   }

   public void setListaAvisoMG(List<Integer> listaAvisoMG)
   {
      this.listaAvisoMG = listaAvisoMG;
   }

   public List<Integer> getListaContraIndicacao()
   {
      return listaContraIndicacao;
   }

   public void setListaContraIndicacao(List<Integer> listaContraIndicacao)
   {
      this.listaContraIndicacao = listaContraIndicacao;
   }

   public List<Integer> getListaContraIndicacaoMGV()
   {
      return listaContraIndicacaoMGV;
   }

   public void setListaContraIndicacaoMGV(List<Integer> listaContraIndicacaoMGV)
   {
      this.listaContraIndicacaoMGV = listaContraIndicacaoMGV;
   }

   public List<Integer> getListaContraIndicacaoMG()
   {
      return listaContraIndicacaoMG;
   }

   public void setListaContraIndicacaoMG(List<Integer> listaContraIndicacaoMG)
   {
      this.listaContraIndicacaoMG = listaContraIndicacaoMG;
   }

   public List<Integer> getListaIndicacao()
   {
      return listaIndicacao;
   }

   public void setListaIndicacao(List<Integer> listaIndicacao)
   {
      this.listaIndicacao = listaIndicacao;
   }

   public List<Integer> getListaIndicacaoMGV()
   {
      return listaIndicacaoMGV;
   }

   public void setListaIndicacaoMGV(List<Integer> listaIndicacaoMGV)
   {
      this.listaIndicacaoMGV = listaIndicacaoMGV;
   }

   public List<Integer> getListaIndicacaoMG()
   {
      return listaIndicacaoMG;
   }

   public void setListaIndicacaoMG(List<Integer> listaIndicacaoMG)
   {
      this.listaIndicacaoMG = listaIndicacaoMG;
   }

   public List<Integer> getListaObservacao()
   {
      return listaObservacao;
   }

   public void setListaObservacao(List<Integer> listaObservacao)
   {
      this.listaObservacao = listaObservacao;
   }

   public List<Integer> getListaObservacaoMGV()
   {
      return listaObservacaoMGV;
   }

   public void setListaObservacaoMGV(List<Integer> listaObservacaoMGV)
   {
      this.listaObservacaoMGV = listaObservacaoMGV;
   }

   public List<Integer> getListaObservacaoMG()
   {
      return listaObservacaoMG;
   }

   public void setListaObservacaoMG(List<Integer> listaObservacaoMG)
   {
      this.listaObservacaoMG = listaObservacaoMG;
   }

   public void editarProdutoM()
   {
      try
      {
         userTransaction.begin();
         produto.setFkIdCategoriaMedicamento((FarmCategoriaMedicamento) categoriaTreeNode.getData());

         int categoria = produto.getFkIdCategoriaMedicamento().getPkIdCategoriaMedicamento();
         produto.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento(categoria));

         int tipo = produto.getFkIdTipoProduto().getPkIdTipoProduto();
         produto.setFkIdTipoProduto(new FarmTipoProduto(tipo));

         int forma = produto.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica();
         produto.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(forma));

         int via = produto.getFkIdViaAdministracao().getPkIdViaAdministracao();
         produto.setFkIdViaAdministracao(new FarmViaAdministracao(via));

         int unidade = produto.getFkIdUnidadeMedida().getPkIdUnidadeMedida();
         produto.setFkIdUnidadeMedida(new FarmUnidadeMedida(unidade));

         produtoFacade.edit(produto);
         eliminarRelaccionamentos(produto);
         for (Object idFarmaco : getListaFarmacos())
         {
            FarmProdutoHasFarmaco farmacoAux = new FarmProdutoHasFarmaco();
            farmacoAux.setFkIdFarmaco(new FarmFarmaco(Integer.parseInt("" + idFarmaco)));
            farmacoAux.setFkIdProduto(produto);
            farmacoFacade.create(farmacoAux);
         }

         for (Object idComponente : getListaOutrosComponentes())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produto);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundarios())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produto);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAviso())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produto);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacao())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produto);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacao())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produto);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacao())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produto);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produto = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Dados do produto editados com sucesso!");
         FarmProdutoListarBean.getInstanciaBean().pesquisarProdutos();
         FarmProdutoListarBean.getInstanciaBean().setProdutoPesquisa(getInstancia());
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }

   public void editarProdutoMG()
   {
      try
      {
         userTransaction.begin();
         produtoMG.setFkIdCategoriaMedicamento(null);
         produtoMG.setFkIdTipoProduto(new FarmTipoProduto(Constantes.FARM_TIPO_PRODUTO_MATERIAL_GASTAVEL));
         produtoMG.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(Constantes.FARM_N_A));
         produtoMG.setFkIdViaAdministracao(new FarmViaAdministracao(Constantes.FARM_N_A));
         
         int unidade = produto.getFkIdUnidadeMedida().getPkIdUnidadeMedida();
         produto.setFkIdUnidadeMedida(new FarmUnidadeMedida(unidade));
         produtoFacade.edit(produtoMG);

         eliminarRelaccionamentos(produtoMG);
         for (Object idComponente : getListaOutrosComponentesMG())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produtoMG);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundariosMG())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produtoMG);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAvisoMG())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produtoMG);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacaoMG())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produtoMG);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacaoMG())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produtoMG);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacaoMG())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produtoMG);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produtoMG = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Dados do produto editados com sucesso!");
         FarmProdutoListarBean.getInstanciaBean().pesquisarProdutos();
         FarmProdutoListarBean.getInstanciaBean().setProdutoPesquisa(getInstanciaProduto());
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         FarmProdutoListarBean.getInstanciaBean().pesquisarProdutos();
         FarmProdutoListarBean.getInstanciaBean().setProdutoPesquisa(getInstanciaProduto());
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }
   
   public void editarProdutoMGV()
   {
      try
      {
         produtoMGV.setFkIdCategoriaMedicamento((FarmCategoriaMedicamento) categoriaTreeNode.getData());
         produtoMGV.setFkIdTipoProduto(new FarmTipoProduto(Constantes.FARM_TIPO_PRODUTO_MEDICAMENTO_GRANDE_VOLUME));
         produtoMGV.setFkIdViaAdministracao(new FarmViaAdministracao(Constantes.FARM_N_A));
         
         int categoria = ((FarmCategoriaMedicamento) categoriaTreeNode.getData()).getPkIdCategoriaMedicamento();
         produtoMGV.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento(categoria));


         int forma = produtoMGV.getFkIdFormaFarmaceutica().getPkIdFormaFarmaceutica();
         produtoMGV.setFkIdFormaFarmaceutica(new FarmFormaFarmaceutica(forma));

         int unidade = produtoMGV.getFkIdUnidadeMedida().getPkIdUnidadeMedida();
         produtoMGV.setFkIdUnidadeMedida(new FarmUnidadeMedida(unidade));

         userTransaction.begin();
         produtoFacade.edit(produtoMGV);
         eliminarRelaccionamentos(produtoMGV);
         for (Object idFarmaco : getListaFarmacosMGV())
         {
            FarmProdutoHasFarmaco farmacoAux = new FarmProdutoHasFarmaco();
            farmacoAux.setFkIdFarmaco(new FarmFarmaco(Integer.parseInt("" + idFarmaco)));
            farmacoAux.setFkIdProduto(produtoMGV);
            farmacoFacade.create(farmacoAux);
         }

         for (Object idComponente : getListaOutrosComponentesMGV())
         {
            FarmProdutoHasOutroComponente componenteAux = new FarmProdutoHasOutroComponente();
            componenteAux.setFkIdOutroComponente(new FarmOutroComponente(Integer.parseInt("" + idComponente)));
            componenteAux.setFkIdProduto(produtoMGV);
            outroComponenteFacade.create(componenteAux);
         }

         for (Object idEfeito : getListaEfeitosSecundariosMGV())
         {
            FarmProdutoHasEfeitoSecundario efeitoAux = new FarmProdutoHasEfeitoSecundario();
            efeitoAux.setFkIdEfeitoSecundario(new FarmEfeitoSecundario(Integer.parseInt("" + idEfeito)));
            efeitoAux.setFkIdProduto(produtoMGV);
            efeitoSecundarioFacade.create(efeitoAux);
         }

         for (Object idAviso : getListaAvisoMGV())
         {
            FarmProdutoHasAviso avisoAux = new FarmProdutoHasAviso();
            avisoAux.setFkIdAviso(new FarmAviso(Integer.parseInt("" + idAviso)));
            avisoAux.setFkIdProduto(produtoMGV);
            avisoFacade.create(avisoAux);
         }

         for (Object idContraIndicacao : getListaContraIndicacaoMGV())
         {
            FarmProdutoHasContraIndicacao contraIndicacaoAux = new FarmProdutoHasContraIndicacao();
            contraIndicacaoAux.setFkIdContraIndicacao(new FarmContraIndicacao(Integer.parseInt("" + idContraIndicacao)));
            contraIndicacaoAux.setFkIdProduto(produtoMGV);
            contraIndicacaoFacade.create(contraIndicacaoAux);
         }

         for (Object idIndicacao : getListaIndicacaoMGV())
         {
            FarmProdutoHasIndicacao indicacaoAux = new FarmProdutoHasIndicacao();
            indicacaoAux.setFkIdIndicacao(new FarmIndicacao(Integer.parseInt("" + idIndicacao)));
            indicacaoAux.setFkIdProduto(produtoMGV);
            indicacaoFacade.create(indicacaoAux);
         }


         for (Object idObservacao : getListaObservacaoMGV())
         {
            FarmProdutoHasObservacao observacaoAux = new FarmProdutoHasObservacao();
            observacaoAux.setFkIdObservacao(new FarmObservacao(Integer.parseInt("" + idObservacao)));
            observacaoAux.setFkIdProduto(produtoMGV);
            observacaoFacade.create(observacaoAux);
         }

         userTransaction.commit();
         produtoMGV = getInstanciaProduto();
         setListaEfeitosSecundarios(null);
         Mensagem.sucessoMsg("Dados do produto editados com sucesso!");
         
      }
      catch (Exception e)
      {
         Mensagem.warnMsg("Ocorreu um erro ao gravar os dados. Tente novamente.");
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }
   }
   
   public void eliminarRelaccionamentos(FarmProduto item)
   {
      List<FarmProdutoHasAviso> listaAvisos = avisoFacade.findByIdProduto(item);
      for(FarmProdutoHasAviso aux : listaAvisos)
      {
         avisoFacade.remove(aux);
      }
      
      List<FarmProdutoHasContraIndicacao> listaContraIndicacoes = contraIndicacaoFacade.findByIdProduto(item);
      for(FarmProdutoHasContraIndicacao aux : listaContraIndicacoes)
      {
         contraIndicacaoFacade.remove(aux);
      }
      
      List<FarmProdutoHasIndicacao> listaIndicacoes = indicacaoFacade.findByIdProduto(item);
      for(FarmProdutoHasIndicacao aux : listaIndicacoes)
      {
         indicacaoFacade.remove(aux);
      }
      
      List<FarmProdutoHasObservacao> listaObservacoes = observacaoFacade.findByIdProduto(item);
      for(FarmProdutoHasObservacao aux : listaObservacoes)
      {
         observacaoFacade.remove(aux);
      }
      
      List<FarmProdutoHasFarmaco> listaFarmaco = farmacoFacade.findByIdProduto(item);
      for(FarmProdutoHasFarmaco aux : listaFarmaco)
      {
         farmacoFacade.remove(aux);
      }
      
      List<FarmProdutoHasEfeitoSecundario> listaEfeitoSecundarios = efeitoSecundarioFacade.findByIdProduto(item);
      for(FarmProdutoHasEfeitoSecundario aux : listaEfeitoSecundarios)
      {
         efeitoSecundarioFacade.remove(aux);
      }
      
      List<FarmProdutoHasOutroComponente> listaOutroComponente = outroComponenteFacade.findByIdProduto(item);
      for(FarmProdutoHasOutroComponente aux : listaOutroComponente)
      {
         outroComponenteFacade.remove(aux);
      }
      
   }
   /**
    *
    * @param produto
    * @return
    */
   public ArrayList<SelectItem> getUnidadeesMedida(FarmProduto produto)
   {
      ArrayList<SelectItem> itens = new ArrayList<>();

      for (FarmUnidadeMedida e : unidadeMedidaFacade.findUnidadeMedida(produto.getFkIdUnidadeMedida()))
      {
         itens.add(new SelectItem(e.getPkIdUnidadeMedida(), e.getDescricao()));
      }
      return itens;
   }
}