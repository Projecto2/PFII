/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SupiFormacaoFormando;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author helga
 */
@Stateless
public class SupiFormacaoFormandoFacade extends AbstractFacade<SupiFormacaoFormando> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupiFormacaoFormandoFacade() {
        super(SupiFormacaoFormando.class);
    }
    
}
