/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinPagamento;
import entidade.FinPagamentoViaBanco;
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
public class FinPagamentoViaBancoFacade extends AbstractFacade<FinPagamentoViaBanco>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinPagamentoViaBancoFacade()
    {
        super(FinPagamentoViaBanco.class);
    }
    
    
    public FinPagamentoViaBanco findPagamentoViaBancoPorPagamento(FinPagamento pagamento)
    {
        Query query = em.createQuery("SELECT pagamentoViaBanco from FinPagamentoViaBanco pagamentoViaBanco WHERE pagamentoViaBanco.fkIdPagamento.pkIdPagamento = :idPagamento");
    
        query.setParameter("idPagamento", pagamento.getPkIdPagamento());
        
        List<FinPagamentoViaBanco> lista = query.getResultList();
        if(lista.isEmpty())
        {
            return null;
        }
        return lista.get(0);
    }
    
}
