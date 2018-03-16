/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbImpressoesGerais;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbImpressoesGeraisFacade extends AbstractFacade<AmbImpressoesGerais>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbImpressoesGeraisFacade()
    {
        super(AmbImpressoesGerais.class);
    }
    
    public boolean isImpressoesGeraisTabelaEmpty()
    {
        List<AmbImpressoesGerais> listAmbImpressoesGerais = this.findAll();
        return (listAmbImpressoesGerais == null || listAmbImpressoesGerais.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdImpressoesGerais)
    {
        AmbImpressoesGerais reg = this.find(pkIdImpressoesGerais);
        return reg != null;
    }
}
