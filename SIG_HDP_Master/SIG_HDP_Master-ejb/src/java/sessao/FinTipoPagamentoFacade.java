/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinTipoPagamento;
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
public class FinTipoPagamentoFacade extends AbstractFacade<FinTipoPagamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinTipoPagamentoFacade()
    {
        super(FinTipoPagamento.class);
    }
    
    public List<FinTipoPagamento> findTipoPagamento(FinTipoPagamento tipoPagamento)
    {
        String query = constroiQuery(tipoPagamento);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (tipoPagamento.getDescricaoTipoPagamento() != null && !tipoPagamento.getDescricaoTipoPagamento().trim().isEmpty())
            qry.setParameter("descricaoTipoPagamento", tipoPagamento.getDescricaoTipoPagamento()+"%");

        List<FinTipoPagamento> tipoPagamentos = qry.getResultList();

        return tipoPagamentos;
    }


    public String constroiQuery(FinTipoPagamento tipoPagamento)
    {        
        String query = "SELECT tipoPagamento FROM FinTipoPagamento tipoPagamento WHERE :pesquisar = :pesquisar";

        if (tipoPagamento.getDescricaoTipoPagamento() != null && !tipoPagamento.getDescricaoTipoPagamento().trim().isEmpty())
           query += " AND tipoPagamento.descricaoTipoPagamento LIKE :descricaoTipoPagamento";

        //query += " ORDER BY tipoPagamento.descricaoTipoPagamento";

        return query;
    }
    
    
//    public List<FinTipoPagamento> findTipoPagamentoPreco1(FinTipoPagamento tipoPagamento)
//    {
//        String query = constroiQuery(tipoPagamento);
//
//        Query qry = em.createQuery(query);
//
//        qry.setParameter("pesquisar", true);
//
//        if (tipoPagamento.getDescricaoTipoPagamento() != null && !tipoPagamento.getDescricaoTipoPagamento().trim().isEmpty())
//            qry.setParameter("descricaoTipoPagamento", tipoPagamento.getDescricaoTipoPagamento()+"%");
//
//        List<FinTipoPagamento> tipoPagamentos = qry.getResultList();
//
//        return tipoPagamentos;
//    }
    
}
