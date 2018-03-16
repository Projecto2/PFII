/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhDepartamento;
import entidade.RhSeccaoTrabalho;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.carregamentoExcel.RhDepartamentoExcelBean;
import managedBean.rhbean.carregamentoExcel.RhSeccaoTrabalhoExcelBean;
import sessao.RhSeccaoTrabalhoFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhSeccaoTrabalhoBean implements Serializable
{

     @Resource
     private UserTransaction userTransaction;

     @EJB
     private RhSeccaoTrabalhoFacade seccaoTrabalhoFacade;

     /**
      *
      * Entidades
      */
     private RhSeccaoTrabalho seccaoTrabalho, seccaoTrabalhoPesquisa, seccaoTrabalhoRemover;
     private List<RhSeccaoTrabalho> seccoesTrabalhoPesquisadasList;

     /**
      * Creates a new instance of RhSeccaoBean
      */
     public RhSeccaoTrabalhoBean ()
     {
     }

    public static RhSeccaoTrabalhoBean getInstanciaBean()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        RhSeccaoTrabalhoBean rhSeccaoTrabalhoBean = 
            (RhSeccaoTrabalhoBean) context.getELContext().
            getELResolver().getValue(FacesContext.getCurrentInstance().
            getELContext(), null, "rhSeccaoTrabalhoBean");
        
        return rhSeccaoTrabalhoBean;
    }
    
     public RhSeccaoTrabalho getInstanciaSeccaoTrabalho ()
     {
          RhSeccaoTrabalho sec = new RhSeccaoTrabalho();
          sec.setFkIdDepartamento(new RhDepartamento());

          return sec;
     }

     public RhSeccaoTrabalho getSeccaoTrabalho ()
     {
          if (this.seccaoTrabalho == null)
          {
               seccaoTrabalho = getInstanciaSeccaoTrabalho();
          }

          return seccaoTrabalho;
     }

     public void setSeccaoTrabalho (RhSeccaoTrabalho seccaoTrabalho)
     {
          this.seccaoTrabalho = seccaoTrabalho;
     }

     public RhSeccaoTrabalho getSeccaoTrabalhoPesquisa ()
     {
          if (seccaoTrabalhoPesquisa == null)
          {
               seccaoTrabalhoPesquisa = getInstanciaSeccaoTrabalho();
          }
          return seccaoTrabalhoPesquisa;
     }

     public void setSeccaoTrabalhoPesquisa (RhSeccaoTrabalho seccaoTrabalhoPesquisa)
     {
          this.seccaoTrabalhoPesquisa = seccaoTrabalhoPesquisa;
     }

     public RhSeccaoTrabalho getSeccaoTrabalhoRemover ()
     {
          return seccaoTrabalhoRemover;
     }

     public void setSeccaoTrabalhoRemover (RhSeccaoTrabalho seccaoTrabalhoRemover)
     {
          this.seccaoTrabalhoRemover = seccaoTrabalhoRemover;
     }

     public List<RhSeccaoTrabalho> getSeccoesTrabalhoPesquisadasList ()
     {
          return seccoesTrabalhoPesquisadasList;
     }

     public void pesquisarSeccoesTrabalho ()
     {
          seccoesTrabalhoPesquisadasList = seccaoTrabalhoFacade.findSeccaoTrabalho(seccaoTrabalhoPesquisa);

          if (seccoesTrabalhoPesquisadasList.isEmpty())
          {
               Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
          }
          else
          {
               Mensagem.sucessoMsg("Número de registros encontrados ("+seccoesTrabalhoPesquisadasList.size()+")");
          }
     }

     public String create ()
     {
          try
          {
               userTransaction.begin();
               seccaoTrabalhoFacade.create(seccaoTrabalho);
               userTransaction.commit();
               Mensagem.sucessoMsg("Secção guardada com sucesso!");
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

          seccaoTrabalho = null;

          return null;
     }

     public String edit ()
     {
          try
          {
               userTransaction.begin();
               if (seccaoTrabalho.getPkIdSeccaoTrabalho() == null)
               {
                    throw new NullPointerException("PK -> NULL");
               }
               int espec = seccaoTrabalho.getFkIdDepartamento().getPkIdDepartamento();
               seccaoTrabalho.setFkIdDepartamento(new RhDepartamento(espec));
               seccaoTrabalhoFacade.edit(seccaoTrabalho);
               userTransaction.commit();
               
               Mensagem.sucessoMsg("Secção editada com sucesso! ");
               pesquisarSeccoesTrabalho();
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

          seccaoTrabalho = null;

          return null;
     }

     public String remove ()
     {
          try
          {
               userTransaction.begin();

               seccaoTrabalhoFacade.remove(seccaoTrabalhoRemover);

               userTransaction.commit();

               Mensagem.sucessoMsg("Secção removida com sucesso!");
               seccaoTrabalhoRemover = null;
               pesquisarSeccoesTrabalho();
          }
          catch (Exception e)
          {
               try
               {
                    e.printStackTrace();
                    Mensagem.erroMsg("Esta secção possui registro de actividades, impossível remover !");
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

     public void carregarExcel ()
     {
         RhDepartamentoExcelBean.getInstanciaBean().carregarDepartamentoTabela();
         RhSeccaoTrabalhoExcelBean.getInstanciaBean().carregarSeccaoTrabalhoTabela();
     }
     
     public String limpar ()
     {
          seccaoTrabalho = null;
          return "seccaoTrabalhoRh?faces-redirect=true";
     }
     
     public List<RhSeccaoTrabalho> findAllPorOrdemDeDepartamento()
     {
         return seccaoTrabalhoFacade.findAllPorOrdemDeDepartamento();
     }
     
}

