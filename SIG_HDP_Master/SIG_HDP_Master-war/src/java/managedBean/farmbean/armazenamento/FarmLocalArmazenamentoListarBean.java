/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.farmbean.armazenamento;

import entidade.FarmLocalArmazenamento;
import entidade.FarmTipoLocalArmazenamento;
import entidade.GrlInstituicao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import managedBean.farmbean.configuracoes.FarmConfiguracoesBean;
import sessao.FarmLocalArmazenamentoFacade;
import util.Constantes;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmLocalArmazenamentoListarBean implements Serializable
{

   @EJB
   private FarmLocalArmazenamentoFacade localFacde;
   private FarmLocalArmazenamento local, localEditar, localNovo;
   private List<FarmLocalArmazenamento> locaisPesquisados;

   /**
    * Creates a new instance of localArmazenamentoListarBean
    */
   public FarmLocalArmazenamentoListarBean()
   {
   }

   public static FarmLocalArmazenamento getInstancia()
   {
      FarmLocalArmazenamento item = new FarmLocalArmazenamento();
      item.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(FarmConfiguracoesBean.TIPO_CADASTRO_LOCAL));
      item.setFkIdInstituicao(new GrlInstituicao(FarmConfiguracoesBean.INSTITUICAO_CADASTRO_LOCAL));
      return item;
   }
   
   public void pesquisarLocais()
   {
      setLocaisPesquisados(localFacde.findLocalArmazenamento(local));
      if (getLocaisPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum local de armazenamento encontrado na pesquisa.");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getLocaisPesquisados().size() + " registo(s) retornado(s).");
   }

   public void editar()
   {
      int tipo = localEditar.getFkIdTipoLocalArmazenamento().getPkIdTipoLocalArmazenamento();
      int instituicao = localEditar.getFkIdInstituicao().getPkIdInstituicao();

      localEditar.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(tipo));
      localEditar.setFkIdInstituicao(new GrlInstituicao(instituicao));

      localFacde.edit(localEditar);
      pesquisarLocais();
      Mensagem.sucessoMsg("Local de Armazenamento editado com sucesso.");
   }

   public void criar()
   {
      localFacde.create(localNovo);
      pesquisarLocais();
      Mensagem.sucessoMsg("Local de Armazenamento criado com sucesso.");

      localNovo = new FarmLocalArmazenamento();
      localNovo.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(FarmConfiguracoesBean.TIPO_CADASTRO_LOCAL));
      localNovo.setFkIdInstituicao(new GrlInstituicao(FarmConfiguracoesBean.INSTITUICAO_CADASTRO_LOCAL));
   }

   public void eliminar()
   {
      try
      {
         localFacde.remove(localEditar);
         Mensagem.sucessoMsg("Local Removido com sucesso");
      }
      catch (Exception ex)
      {
         Mensagem.warnMsg("O local não pode ser removido porque está a ser utilizado.");
      }
   }

   public boolean renderizarMenuItemVerProdutos(FarmLocalArmazenamento local)
   {
      return local.getFkIdTipoLocalArmazenamento().getPkIdTipoLocalArmazenamento() != Constantes.FARM_TIPO_LOCAL_AREA_EXTERNA;
   }

   /**
    * @return the local
    */
   public FarmLocalArmazenamento getLocal()
   {
      if (local == null)
      {
         local = new FarmLocalArmazenamento();
         local.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
         local.setFkIdInstituicao(new GrlInstituicao());
      }
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
    * @return the localNovo
    */
   public FarmLocalArmazenamento getLocalNovo()
   {
      if (localNovo == null)
      {

         localNovo = new FarmLocalArmazenamento();
         localNovo.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento(FarmConfiguracoesBean.TIPO_CADASTRO_LOCAL));
         localNovo.setFkIdInstituicao(new GrlInstituicao(FarmConfiguracoesBean.INSTITUICAO_CADASTRO_LOCAL));
      }

      return localNovo;
   }

   /**
    * @param localNovo the localNovo to set
    */
   public void setLocalNovo(FarmLocalArmazenamento localNovo)
   {
      this.localNovo = localNovo;
   }

   /**
    * @return the locaisPesquisados
    */
   public List<FarmLocalArmazenamento> getLocaisPesquisados()
   {
      return locaisPesquisados;
   }

   /**
    * @param locaisPesquisados the locaisPesquisados to set
    */
   public void setLocaisPesquisados(List<FarmLocalArmazenamento> locaisPesquisados)
   {
      this.locaisPesquisados = locaisPesquisados;
   }

   /**
    * @return the localEditar
    */
   public FarmLocalArmazenamento getLocalEditar()
   {
      if (localEditar == null)
      {

         localEditar = new FarmLocalArmazenamento();
         localEditar.setFkIdTipoLocalArmazenamento(new FarmTipoLocalArmazenamento());
         localEditar.setFkIdInstituicao(new GrlInstituicao());
      }
      return localEditar;
   }

   /**
    * @param localEditar the localEditar to set
    */
   public void setLocalEditar(FarmLocalArmazenamento localEditar)
   {
      this.localEditar = localEditar;
   }
}
