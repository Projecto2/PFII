/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.grlbean;

import entidade.GrlPais;
import entidade.GrlProvincia;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.grlbean.carregamentoExcel.GrlProvinciaExcelBean;
import sessao.GrlProvinciaFacade;
import util.Mensagem;
import util.RelatorioJasper;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class GrlProvinciaBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;

   @EJB
   private GrlProvinciaFacade provinciaFacade;

   /**
    *
    * Entidades
    */
   private GrlProvincia provincia,

   /**
    * Entidades
    */
   provinciaPesquisa,

   /**
    * Entidades
    */
   provinciaRemover;
   private List<GrlProvincia> provinciasPesquisadasList;

   /**
    * Creates a new instance of ProvinciaBean
    */
   public GrlProvinciaBean()
   {
   }

   public GrlProvincia getInstanciaProvincia()
   {
      GrlProvincia sec = new GrlProvincia();
      sec.setFkIdPais(new GrlPais());

      return sec;
   }

   public GrlProvincia getProvincia()
   {
      if (this.provincia == null)
      {
         provincia = getInstanciaProvincia();
      }

      return provincia;
   }

   public void setProvincia(GrlProvincia provincia)
   {
      this.provincia = provincia;
   }

   public GrlProvincia getProvinciaPesquisa()
   {
      if (provinciaPesquisa == null)
      {
         provinciaPesquisa = getInstanciaProvincia();
      }
      return provinciaPesquisa;
   }

   public void setProvinciaPesquisa(GrlProvincia provinciaPesquisa)
   {
      this.provinciaPesquisa = provinciaPesquisa;
   }

   public GrlProvincia getProvinciaRemover()
   {
      return provinciaRemover;
   }

   public void setProvinciaRemover(GrlProvincia provinciaRemover)
   {
      this.provinciaRemover = provinciaRemover;
   }

   public List<GrlProvincia> getProvinciasPesquisadasList()
   {
      return provinciasPesquisadasList;
   }

   public void pesquisarProvincias()
   {
      provinciasPesquisadasList = provinciaFacade.findProvincia(provinciaPesquisa);

      if (provinciasPesquisadasList.isEmpty())
      {
         Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
      }
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + provinciasPesquisadasList.size() + " registo(s) retornado(s).");
   }

   public String create()
   {
      try
      {
         userTransaction.begin();
         provinciaFacade.create(provincia);
         userTransaction.commit();
         Mensagem.sucessoMsg("Província guardada com sucesso!");
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

      provincia = null;

      return null;
   }

   public String edit()
   {
      try
      {
         userTransaction.begin();
         if (provincia.getPkIdProvincia() == null)
         {
            throw new NullPointerException("PK -> NULL");
         }
         int pais = provincia.getFkIdPais().getPkIdPais();
         provincia.setFkIdPais(new GrlPais(pais));
         provinciaFacade.edit(provincia);
         userTransaction.commit();

         Mensagem.sucessoMsg("Província editada com sucesso! ");
         pesquisarProvincias();
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

      provincia = null;

      return null;
   }

   public String remove()
   {
      try
      {
         userTransaction.begin();

         provinciaFacade.remove(provinciaRemover);

         userTransaction.commit();

         Mensagem.sucessoMsg("Província removida com sucesso!");
         provinciaRemover = null;
         pesquisarProvincias();
      }
      catch (Exception e)
      {
         try
         {
            e.printStackTrace();
            Mensagem.erroMsg("Esta província possui registro de actividades, impossível remover !");
            Mensagem.erroMsg(e.toString());
            userTransaction.rollback();
         }
         catch (IllegalStateException | SecurityException | SystemException ex)
         {
            e.printStackTrace();
            Mensagem.erroMsg("Rollback: " + ex.toString());
         }
      }

      return null;
   }

   public String limpar()
   {
      provincia = null;
      return "provinciaGrl?faces-redirect=true";
   }

   public void exportPDF(ActionEvent evt)
   {
      HashMap<String, Object> parametrosMap = new HashMap<>();
      RelatorioJasper.exportPDF("grl/relatorioTeste.jasper", parametrosMap, provinciasPesquisadasList);
   }

   public void carregar()
   {
      GrlProvinciaExcelBean.getInstanciaBean().carregarProvinciaTabela();

      Mensagem.sucessoMsg("Dados Carregados Com Sucesso!");

      pesquisarProvincias();
      Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + provinciasPesquisadasList.size() + " registo(s) retornado(s).");
   }
}
