/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagControloSemanalMaterial;
import entidade.DiagControloSemanalMaterialHasMaterial;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagControloSemanalMaterialHasMaterialFacade extends AbstractFacade<DiagControloSemanalMaterialHasMaterial>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagControloSemanalMaterialHasMaterialFacade()
    {
        super(DiagControloSemanalMaterialHasMaterial.class);
    }
    
    public List<DiagControloSemanalMaterialHasMaterial> findProdutosByRegisto(DiagControloSemanalMaterial registo)
    {
        if (registo == null)
        {
            return new ArrayList<>();
        }
        
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagControloSemanalMaterialHasMaterial d WHERE d.fkIdControloSemanalMaterial.pkIdControloSemanalMaterial = :idRegisto", DiagControloSemanalMaterialHasMaterial.class).setParameter("idRegisto", registo.getPkIdControloSemanalMaterial());

        return typedQuery.getResultList();
    }
}
