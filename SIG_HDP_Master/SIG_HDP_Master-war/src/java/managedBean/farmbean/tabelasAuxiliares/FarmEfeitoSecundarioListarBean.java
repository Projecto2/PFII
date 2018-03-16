/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedBean.farmbean.tabelasAuxiliares;

import entidade.FarmEfeitoSecundario;
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
import sessao.FarmEfeitoSecundarioFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmEfeitoSecundarioListarBean implements Serializable
{

   @Resource
   private UserTransaction userTransaction;
   
   @EJB
   FarmEfeitoSecundarioFacade efeitoSecundarioFacade;   
   private FarmEfeitoSecundario efeitoSecundarioPesquisa;
   private FarmEfeitoSecundario efeitoSecundarioEditar;
   private FarmEfeitoSecundario efeitoSecundarioEliminar;
   private FarmEfeitoSecundario efeitoSecundarioNovo;

   private List<FarmEfeitoSecundario> efeitoSecundariosPesquisados;
   /**
    * Creates a new instance of FarmEfeitoSecundarioListarBean
    */
   public FarmEfeitoSecundarioListarBean()
   {
      efeitoSecundarioEditar = new FarmEfeitoSecundario();
      efeitoSecundarioEliminar = new FarmEfeitoSecundario();
      efeitoSecundarioPesquisa = new FarmEfeitoSecundario();
      efeitoSecundarioNovo = new FarmEfeitoSecundario();
   }

   public void pesquisarEfeitosSecundarios()
   {
       System.out.println("antes de cahamar do metodo findEfeitoSecundario" + efeitoSecundarioPesquisa);
   
       System.out.println("descricao :" + efeitoSecundarioPesquisa.getDescricaoEfeitoSecundario());
      
       setEfeitoSecundariosPesquisados(efeitoSecundarioFacade.findEfeitoSecundario(efeitoSecundarioPesquisa));
      if (getEfeitoSecundariosPesquisados().isEmpty())
         Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
   }

   public String criar ()
    {
        try
        {
            userTransaction.begin();
            efeitoSecundarioFacade.create(getEfeitoSecundarioNovo());
            userTransaction.commit();
            Mensagem.sucessoMsg("Efeito Secundário cadastrado com sucesso!");
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

        setEfeitoSecundarioNovo(null);

        return null;
    }
   
   public String editar()
    {
        try
        {
            userTransaction.begin();
            if (efeitoSecundarioEditar.getPkIdEfeitoSecundario() == null)
            {
                throw new NullPointerException("PK -> NULL");
            }
            efeitoSecundarioFacade.edit(efeitoSecundarioEditar);
            userTransaction.commit();
            Mensagem.sucessoMsg("Efeito Secundário editado com sucesso!");
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

        efeitoSecundarioEditar = null;
        return null;
    }
   /**
    * @return the efeitoSecundarioPesquisa
    */
   public FarmEfeitoSecundario getEfeitoSecundarioPesquisa()
   {
      return efeitoSecundarioPesquisa;
   }

   /**
    * @param efeitoSecundarioPesquisa the efeitoSecundarioPesquisa to set
    */
   public void setEfeitoSecundarioPesquisa(FarmEfeitoSecundario efeitoSecundarioPesquisa)
   {
      this.efeitoSecundarioPesquisa = efeitoSecundarioPesquisa;
   }

   /**
    * @return the efeitoSecundariosPesquisados
    */
   public List<FarmEfeitoSecundario> getEfeitoSecundariosPesquisados()
   {
      return efeitoSecundariosPesquisados;
   }

   /**
    * @param efeitoSecundariosPesquisados the efeitoSecundariosPesquisados to set
    */
   public void setEfeitoSecundariosPesquisados(List<FarmEfeitoSecundario> efeitoSecundariosPesquisados)
   {
      this.efeitoSecundariosPesquisados = efeitoSecundariosPesquisados;
   }

   /**
    * @return the efeitoSecundarioEditar
    */
   public FarmEfeitoSecundario getEfeitoSecundarioEditar()
   {
      return efeitoSecundarioEditar;
   }

   /**
    * @param efeitoSecundario the efeitoSecundarioEditar to set
    */
   public void setEfeitoSecundarioEditar(FarmEfeitoSecundario efeitoSecundario)
   {
      System.out.println("edittando "+efeitoSecundario);
      this.efeitoSecundarioEditar = efeitoSecundario;
   }

   /**
    * @return the efeitoSecundarioEliminar
    */
   public FarmEfeitoSecundario getEfeitoSecundarioEliminar()
   {
      return efeitoSecundarioEliminar;
   }

   /**
    * @param efeitoSecundarioEliminar the efeitoSecundarioEliminar to set
    */
   public void setEfeitoSecundarioEliminar(FarmEfeitoSecundario efeitoSecundarioEliminar)
   {
      this.efeitoSecundarioEliminar = efeitoSecundarioEliminar;
   }

   /**
    * @return the efeitoSecundarioNovo
    */
   public FarmEfeitoSecundario getEfeitoSecundarioNovo()
   {
      return efeitoSecundarioNovo;
   }

   /**
    * @param efeitoSecundarioNovo the efeitoSecundarioNovo to set
    */
   public void setEfeitoSecundarioNovo(FarmEfeitoSecundario efeitoSecundarioNovo)
   {
      this.efeitoSecundarioNovo = efeitoSecundarioNovo;
   }
   
   
}
