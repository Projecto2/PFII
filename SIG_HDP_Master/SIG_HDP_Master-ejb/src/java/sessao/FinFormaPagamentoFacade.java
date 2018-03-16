/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FinFormaPagamento;
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
public class FinFormaPagamentoFacade extends AbstractFacade<FinFormaPagamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinFormaPagamentoFacade()
    {
        super(FinFormaPagamento.class);
    }
    
    
    public List<FinFormaPagamento> findFormaPagamento(FinFormaPagamento formaPagamento)
    {
        String query = constroiQuery(formaPagamento);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (formaPagamento.getDescricaoFormaPagamento() != null && !formaPagamento.getDescricaoFormaPagamento().trim().isEmpty())
            qry.setParameter("descricaoFormaPagamento", formaPagamento.getDescricaoFormaPagamento()+"%");

        List<FinFormaPagamento> formaPagamentos = qry.getResultList();

        return formaPagamentos;
    }


    public String constroiQuery(FinFormaPagamento formaPagamento)
    {        
        String query = "SELECT formaPagamento FROM FinFormaPagamento formaPagamento WHERE :pesquisar = :pesquisar";

        if (formaPagamento.getDescricaoFormaPagamento() != null && !formaPagamento.getDescricaoFormaPagamento().trim().isEmpty())
           query += " AND formaPagamento.descricaoFormaPagamento LIKE :descricaoFormaPagamento";

        query += " ORDER BY formaPagamento.descricaoFormaPagamento";

        return query;
    }
    
}
