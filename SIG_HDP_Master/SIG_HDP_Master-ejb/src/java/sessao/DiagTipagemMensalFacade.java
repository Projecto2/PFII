/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagTipagemMensal;
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
public class DiagTipagemMensalFacade extends AbstractFacade<DiagTipagemMensal>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipagemMensalFacade()
    {
        super(DiagTipagemMensal.class);
    }
    
    public List<DiagTipagemMensal> findTipagem(DiagTipagemMensal tipagemAux)
    {
        TypedQuery typedQuery;

        if ( tipagemAux.getAno() == null || (tipagemAux.getMes() == null || tipagemAux.getMes().isEmpty()))
        {
            typedQuery = em.createQuery("SELECT d FROM DiagTipagemMensal d", String.class);
        } 
        else
        {
            typedQuery = em.createQuery("SELECT d FROM DiagTipagemMensal d WHERE d.mes LIKE :mes AND d.ano = :ano", DiagTipagemMensal.class);

            typedQuery.setParameter("ano", tipagemAux.getAno());
            typedQuery.setParameter("mes", tipagemAux.getMes());
        }
        return typedQuery.getResultList();
    }

    public List<String> findAnosComTipagem()
    {
        TypedQuery typedQuery = em.createQuery("SELECT DISTINCT d.ano FROM DiagTipagemMensal d", String.class);

        return typedQuery.getResultList();
    }
}
