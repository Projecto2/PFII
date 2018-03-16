/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmUpdates;
import entidade.SupiUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author helga
 */
@Stateless
public class SupiUpdatesFacade extends AbstractFacade<SupiUpdates>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SupiUpdatesFacade()
    {
        super(SupiUpdates.class);
    }
    
    public SupiUpdates obterRegistoUpdates()
   {
      List<SupiUpdates> list = this.findAll();
      if (list == null || list.isEmpty())
      {
         return null;
      }
      return list.get(0);
   }
    
    public Date dataTurno()
   {
      SupiUpdates reg = obterRegistoUpdates();
      return reg == null ? null : reg.getTurno();
   }
    
     public void escreverDataActualizacaoTurnoTabela()
   {
      SupiUpdates reg = obterRegistoUpdates();

      if (reg == null)
      {
         reg = new SupiUpdates();
         reg.setTurno(new Date());
         this.create(reg);
      }
      else
      {
         reg.setTurno(new Date());
         this.edit(reg);
      }
   }
    
}
