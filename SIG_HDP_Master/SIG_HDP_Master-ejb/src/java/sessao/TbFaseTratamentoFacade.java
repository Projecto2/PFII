/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbFaseTratamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adelino
 */
@Stateless
public class TbFaseTratamentoFacade extends AbstractFacade<TbFaseTratamento>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbFaseTratamentoFacade()
    {
        super(TbFaseTratamento.class);
    }

    public boolean isFaseTratamentoTabelaEmpty()
    {
        List<TbFaseTratamento> listTbFaseTratamento = this.findAll();
        return (listTbFaseTratamento == null || listTbFaseTratamento.isEmpty());
    }

    public boolean existeRegisto(int pkFaseTratamento)
    {
        TbFaseTratamento reg = this.find(pkFaseTratamento);
        return reg != null;
    }

    public List<TbFaseTratamento> findFaseTratamento(TbFaseTratamento faseTratamento)
    {
        String query = constroiQuery(faseTratamento);

        TypedQuery<TbFaseTratamento> t = em.createQuery(query, TbFaseTratamento.class);

        if (faseTratamento.getDescricao() != null && !(faseTratamento.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", faseTratamento.getDescricao() + "%");
        }

        List<TbFaseTratamento> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbFaseTratamento faseTratamento)
    {
        String query = "SELECT a FROM TbFaseTratamento a WHERE a.pkFaseTratamento = a.pkFaseTratamento";

        if (faseTratamento.getDescricao() != null && !(faseTratamento.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
