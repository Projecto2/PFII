/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinTipoEncargo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class FinTipoEncargoFacade extends AbstractFacade<FinTipoEncargo>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinTipoEncargoFacade()
    {
        super(FinTipoEncargo.class);
    }
    
    public FinTipoEncargo findTipoEncargoServico()
    {
        Query qry = em.createQuery("SELECT tipoEncargo FROM FinTipoEncargo as tipoEncargo WHERE tipoEncargo.descricaoTipoEncargo LIKE 'Serviço'");

        List<FinTipoEncargo> tipo = qry.getResultList();
        
        if(tipo.isEmpty()) return null;
        
        return tipo.get(0);
    }
    
    public FinTipoEncargo findTipoEncargoProduto()
    {
        Query qry = em.createQuery("SELECT tipoEncargo FROM FinTipoEncargo as tipoEncargo WHERE tipoEncargo.descricaoTipoEncargo LIKE 'Produto'");

        List<FinTipoEncargo> tipo = qry.getResultList();
        
        if(tipo.isEmpty()) return null;
        
        return tipo.get(0);
    }
    
}
