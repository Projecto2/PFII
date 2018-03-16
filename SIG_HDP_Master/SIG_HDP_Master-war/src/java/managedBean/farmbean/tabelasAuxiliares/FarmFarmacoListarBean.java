/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmFarmaco;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.FarmFarmacoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmFarmacoListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   
   @EJB
   FarmFarmacoFacade farmacoFacade;   
   private FarmFarmaco farmacoPesquisa;
   private FarmFarmaco farmacoEditar;
   private FarmFarmaco farmacoEliminar;
   private FarmFarmaco farmacoNovo;

   private List<FarmFarmaco> farmacosPesquisados;
   /**
    * Creates a new instance of FarmFarmacoListarBean
    */
   public FarmFarmacoListarBean()
   {
      farmacoEditar = new FarmFarmaco();
      farmacoEliminar = new FarmFarmaco();
      farmacoPesquisa= new FarmFarmaco();
      farmacoNovo = new FarmFarmaco();
   }

   public void pesquisarFarmacos()
   {
      setFarmacosPesquisados(farmacoFacade.findFarmaco(getFarmacoPesquisa()));
      if (getFarmacosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
      else
         Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getFarmacosPesquisados().size() + " registo(s) retornado(s).");
   }

   public String criar ()
    {
        try
        {
            userTransaction.begin();
            farmacoFacade.create(getFarmacoNovo());
            userTransaction.commit();
            Mensagem.sucessoMsg("Fármaco cadastrado com sucesso!");
        }
        catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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

        setFarmacoNovo(null);

        return null;
    }
   
   public String editar()
    {
        try
        {
            userTransaction.begin();
            if (farmacoEditar.getPkIdFarmaco() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            farmacoFacade.edit(farmacoEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Fármaco editado com sucesso!");
        }
        catch (IllegalStateException | NullPointerException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
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

        farmacoEditar = null;
        return null;
    }
   /**
    * @return the farmacoPesquisa
    */
   public FarmFarmaco getFarmacoPesquisa()
   {
      return farmacoPesquisa;
   }

   /**
    * @param farmacoPesquisa the farmacoPesquisa to set
    */
   public void setFarmacoPesquisa(FarmFarmaco farmacoPesquisa)
   {
      this.farmacoPesquisa = farmacoPesquisa;
   }

   /**
    * @return the farmacosPesquisados
    */
   public List<FarmFarmaco> getFarmacosPesquisados()
   {
      return farmacosPesquisados;
   }

   /**
    * @param farmacosPesquisados the farmacosPesquisados to set
    */
   public void setFarmacosPesquisados(List<FarmFarmaco> farmacosPesquisados)
   {
      this.farmacosPesquisados = farmacosPesquisados;
   }

   /**
    * @return the farmacoEditar
    */
   public FarmFarmaco getFarmacoEditar()
   {
      return farmacoEditar;
   }

   /**
    * @param farmaco the farmacoEditar to set
    */
   public void setFarmacoEditar(FarmFarmaco farmaco)
   {
      this.farmacoEditar = farmaco;
   }

   /**
    * @return the farmacoEliminar
    */
   public FarmFarmaco getFarmacoEliminar()
   {
      return farmacoEliminar;
   }

   /**
    * @param farmacoEliminar the farmacoEliminar to set
    */
   public void setFarmacoEliminar(FarmFarmaco farmacoEliminar)
   {
      this.farmacoEliminar = farmacoEliminar;
   }

   /**
    * @return the farmacoNovo
    */
   public FarmFarmaco getFarmacoNovo()
   {
      return farmacoNovo;
   }

   /**
    * @param farmacoNovo the farmacoNovo to set
    */
   public void setFarmacoNovo(FarmFarmaco farmacoNovo)
   {
      this.farmacoNovo = farmacoNovo;
   }
   
   
}
