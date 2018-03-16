/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmTipoLocalArmazenamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author elisangela
 */
@Stateless
public class FarmTipoLocalArmazenamentoFacade extends AbstractFacade<FarmTipoLocalArmazenamento>
{
   @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager()
   {
      return em;
   }

   public FarmTipoLocalArmazenamentoFacade()
   {
      super(FarmTipoLocalArmazenamento.class);
   }
   
   public boolean isTipoLocalArmazenamentoTabelaEmpty()
   {
      List<FarmTipoLocalArmazenamento> listTiposLocalArmazenamento = this.findAll();
      return (listTiposLocalArmazenamento == null || listTiposLocalArmazenamento.isEmpty());
   }

   public boolean existeRegisto(int pkIdTipoLocalArmazenamento)
   {
      FarmTipoLocalArmazenamento reg = this.find(pkIdTipoLocalArmazenamento);
      return reg != null;
   }
}
