/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiTurno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author helga
 */
@Stateless
public class SupiTurnoFacade extends AbstractFacade<SupiTurno> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiTurnoFacade() {
        super(SupiTurno.class);
    }
    
    public boolean existeRegisto(int pkIdTurno)
   {
      SupiTurno reg = this.find(pkIdTurno);
      return reg != null;
   }
    
    
    
    public boolean isTurnoTabelaEmpty()
   {
      List<SupiTurno> listTurnos = this.findAll();
      return (listTurnos == null || listTurnos.isEmpty());
   }
    
}
