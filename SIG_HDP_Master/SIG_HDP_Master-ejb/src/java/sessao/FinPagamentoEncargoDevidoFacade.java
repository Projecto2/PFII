/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinEncargoDevido;
import entidade.FinPagamento;
import entidade.FinPagamentoEncargoDevido;
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
public class FinPagamentoEncargoDevidoFacade extends AbstractFacade<FinPagamentoEncargoDevido>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinPagamentoEncargoDevidoFacade()
    {
        super(FinPagamentoEncargoDevido.class);
    }
    
    public FinPagamentoEncargoDevido findPagamentoEncargoByEncargo(FinEncargoDevido encargo)
    {
        Query qry = em.createQuery("SELECT pagamentoEncargo FROM FinPagamentoEncargoDevido pagamentoEncargo WHERE pagamentoEncargo.fkIdEncargoDevido.pkIdEncargoDevido = :idEncargo");

        qry.setParameter("idEncargo", encargo.getPkIdEncargoDevido());
        
        List<FinPagamentoEncargoDevido> pagamentoEncargo = qry.getResultList();
        
        if(pagamentoEncargo.isEmpty()) return null;
        
        return pagamentoEncargo.get(0);
    }
    
    public List<FinPagamentoEncargoDevido> findPagamentoEncargoListaByPagamento(FinPagamento pagamento)
    {
        Query qry = em.createQuery("SELECT pagamentoEncargo FROM FinPagamentoEncargoDevido pagamentoEncargo WHERE pagamentoEncargo.fkIdPagamento.pkIdPagamento = :idPagamento");

        qry.setParameter("idPagamento", pagamento.getPkIdPagamento());
        
        List<FinPagamentoEncargoDevido> pagamentoEncargo = qry.getResultList();
        
        if(pagamentoEncargo.isEmpty()) return null;
        
        return pagamentoEncargo;
    }
    
}
