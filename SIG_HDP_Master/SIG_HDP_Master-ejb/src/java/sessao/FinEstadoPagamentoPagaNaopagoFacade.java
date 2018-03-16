/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinEstadoPagamentoPagaNaopago;
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
public class FinEstadoPagamentoPagaNaopagoFacade extends AbstractFacade<FinEstadoPagamentoPagaNaopago>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinEstadoPagamentoPagaNaopagoFacade()
    {
        super(FinEstadoPagamentoPagaNaopago.class);
    }
    
    public FinEstadoPagamentoPagaNaopago findEstadoPagamentoPago()
    {
        Query qry = em.createQuery("SELECT estadoPagamento FROM FinEstadoPagamentoPagaNaopago as estadoPagamento WHERE estadoPagamento.descricao LIKE 'Pago'");

        List<FinEstadoPagamentoPagaNaopago> estado = qry.getResultList();
        
        if(estado.isEmpty()) return null;
        
        return estado.get(0);
    }
    
    public FinEstadoPagamentoPagaNaopago findEstadoPagamentoNaoPago()
    {
        Query qry = em.createQuery("SELECT estadoPagamento FROM FinEstadoPagamentoPagaNaopago as estadoPagamento WHERE estadoPagamento.descricao LIKE 'Não Pago'");

        List<FinEstadoPagamentoPagaNaopago> estado = qry.getResultList();
        
        if(estado.isEmpty()) return null;
        
        return estado.get(0);
    }
    
}
