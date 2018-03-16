/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoNotificacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoNotificacaoFacade extends AbstractFacade<FarmTipoNotificacao>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoNotificacaoFacade()
   {
      super(FarmTipoNotificacao.class);
   }
   
   public boolean isTipoNotificacaoTabelaEmpty()
   {
      List<FarmTipoNotificacao> listTiposNotificacao = this.findAll();
      return (listTiposNotificacao == null || listTiposNotificacao.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoNotificacao)
   {
      FarmTipoNotificacao reg = this.find(pkIdTipoNotificacao);
      return reg != null;
   }
}
