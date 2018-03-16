/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlEspecialidade;
import entidade.RhProfissao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlEspecialidadeExcelBean;
import managedBean.rhbean.carregamentoExcel.RhProfissaoExcelBean;
import sessao.GrlEspecialidadeFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 * @author Garcia Paulo
 */
@ManagedBean
@SessionScoped
public class GrlEspecialidadeBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   private GrlEspecialidadeFacade especialidadeFacade;

   /**
    *
    * Entidades
    */
   private GrlEspecialidade especialidade, especialidadePesquisa;
   private List<GrlEspecialidade> especialidadesPesquisadasList;

   /**
    * Creates a new instance of EspecialidadeBean
    */
   public GrlEspecialidadeBean()
   {
   }

   public GrlEspecialidade getInstancia()
   {
      GrlEspecialidade cat = new GrlEspecialidade();
      cat.setFkIdProfissao(new RhProfissao());

      return cat;
   }

   public GrlEspecialidade getEspecialidade()
   {
      if (this.especialidade == null)
      {
         especialidade = getInstancia();
      }

      return especialidade;
   }

   public void setEspecialidade(GrlEspecialidade especialidade)
   {
      this.especialidade = especialidade;
   }

   public GrlEspecialidade getEspecialidadePesquisa()
   {
      if (especialidadePesquisa == null)
      {
         especialidadePesquisa = getInstancia();
      }
      return especialidadePesquisa;
   }

   public void setEspecialidadePesquisa(GrlEspecialidade especialidadePesquisa)
   {
      this.especialidadePesquisa = especialidadePesquisa;
   }

   public void setEspecialidadesPesquisadasList(List<GrlEspecialidade> especialidadesPesquisadasList)
   {
      this.especialidadesPesquisadasList = especialidadesPesquisadasList;
   }

   public List<GrlEspecialidade> getEspecialidadesPesquisadasList()
   {
      return especialidadesPesquisadasList;
   }

   public void pesquisarEspecialidades()
   {
      especialidadesPesquisadasList = especialidadeFacade.findEspecialidade(especialidadePesquisa);

      if (especialidadesPesquisadasList.isEmpty())
      {
         Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
      }
   }

   public String create()
   {
      try
      {
         userTransaction.begin();
         especialidadeFacade.create(especialidade);
         userTransaction.commit();
         Mensagem.sucessoMsg("Especialidade guardada com sucesso!");
      }
      catch (Exception e)
      {
         try
         {
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }

      especialidade = null;

      return null;
   }

   public String edit()
   {
      try
      {
         userTransaction.begin();
         if (especialidade.getPkIdEspecialidade() == null)
         {
            throw new NullPointerException("PK -> NULL");
         }
         int espec = especialidade.getFkIdProfissao().getPkIdProfissao();
         especialidade.setFkIdProfissao(new RhProfissao(espec));
         especialidadeFacade.edit(especialidade);
         userTransaction.commit();

         Mensagem.sucessoMsg("Especialidade editada com sucesso! ");
         pesquisarEspecialidades();
      }
      catch (Exception e)
      {
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

      especialidade = null;

      return null;
   }

   public List<GrlEspecialidade> pesquisaPorEspecialidadeMedica()
   {
      return especialidadeFacade.pesquisaPorEspecialidadeMedica();
   }

   public void carregarExcel()
   {
      especialidade = getInstancia();
      especialidadePesquisa = getInstancia();
      especialidadesPesquisadasList = new ArrayList<>();

      RhProfissaoExcelBean.getInstanciaBean().carregarProfissaoTabela();
      GrlEspecialidadeExcelBean.getInstanciaBean().carregarEspecialidadeTabela();
   }

   public List<GrlEspecialidade> findAllOrderByDescricao(Integer pkIdProfissao)
   {
      System.out.println("0: EspecialidadeBean.findAllOrderByDescricao()\tpkIdProfissao: " + pkIdProfissao);
      return this.especialidadeFacade.findAllOrderByDescricao(pkIdProfissao);
   }

   public String limpar()
   {
      especialidade = null;
      return "especialidadeGrl?faces-redirect=true";
   }
}
