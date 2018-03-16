/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsEstadoPagamento;
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
public class AdmsEstadoPagamentoFacade extends AbstractFacade<AdmsEstadoPagamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsEstadoPagamentoFacade()
    {
        super(AdmsEstadoPagamento.class);
    }
    
    public AdmsEstadoPagamento findPagamentoPendente()
    {
        Query qry = em.createQuery("SELECT estadoPagamento FROM AdmsEstadoPagamento as estadoPagamento WHERE estadoPagamento.descricaoEstadoPagamento LIKE 'Pendente'");

        List<AdmsEstadoPagamento> estado = qry.getResultList();
        
        if(estado.isEmpty()) return null;
        
        return estado.get(0);
    }
    
    public AdmsEstadoPagamento findEstadoPagamentoPago()
    {
        Query qry = em.createQuery("SELECT estadoPagamento FROM AdmsEstadoPagamento as estadoPagamento WHERE estadoPagamento.descricaoEstadoPagamento LIKE 'Pago'");

        List<AdmsEstadoPagamento> estado = qry.getResultList();
        
        if(estado.isEmpty()) return null;
        
        return estado.get(0);
    }
    
    
    public boolean isEstadoPagamentoTabelaEmpty()
    {
        List<AdmsEstadoPagamento> listEstadoPagamento = this.findAll();
        return (listEstadoPagamento == null || listEstadoPagamento.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEstadoPagamento)
    {
        AdmsEstadoPagamento reg = this.find(pkIdEstadoPagamento);
        return reg != null;
    }
    
}
