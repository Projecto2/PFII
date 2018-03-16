/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbClassificacaoDoenca;
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
public class TbClassificacaoDoencaFacade extends AbstractFacade<TbClassificacaoDoenca>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbClassificacaoDoencaFacade()
    {
        super(TbClassificacaoDoenca.class);
    }

    public boolean isClassificacaoDoencaTabelaEmpty()
    {
        List<TbClassificacaoDoenca> listTbClassificacaoDoenca = this.findAll();
        return (listTbClassificacaoDoenca == null || listTbClassificacaoDoenca.isEmpty());
    }

    public boolean existeRegisto(int pkClassificacaoDoenca)
    {
        TbClassificacaoDoenca reg = this.find(pkClassificacaoDoenca);
        return reg != null;
    }

    public List<TbClassificacaoDoenca> findClassificacaoDoenca(TbClassificacaoDoenca classificacaoDoenca)
    {
        String query = constroiQuery(classificacaoDoenca);

        TypedQuery<TbClassificacaoDoenca> t = em.createQuery(query, TbClassificacaoDoenca.class);

        if (classificacaoDoenca.getDescricao() != null && !(classificacaoDoenca.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", classificacaoDoenca.getDescricao() + "%");
        }

        List<TbClassificacaoDoenca> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbClassificacaoDoenca classificacaoDoenca)
    {
        String query = "SELECT a FROM TbClassificacaoDoenca a WHERE a.pkClassificacaoDoenca = a.pkClassificacaoDoenca";

        if (classificacaoDoenca.getDescricao() != null && !(classificacaoDoenca.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }
}
