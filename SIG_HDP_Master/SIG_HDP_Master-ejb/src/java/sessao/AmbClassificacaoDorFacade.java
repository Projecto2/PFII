/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbClassificacaoDor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbClassificacaoDorFacade extends AbstractFacade<AmbClassificacaoDor>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbClassificacaoDorFacade()
    {
        super(AmbClassificacaoDor.class);
    }
    
    public boolean isClassificacaoDorTabelaEmpty()
    {
        List<AmbClassificacaoDor> listClassificacaoDor = this.findAll();
        return (listClassificacaoDor == null || listClassificacaoDor.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdClassificacaoDor)
    {
        AmbClassificacaoDor reg = this.find(pkIdClassificacaoDor);
        return reg != null;
    }
}
