/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbSintoma;
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
public class TbSintomaFacade extends AbstractFacade<TbSintoma>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbSintomaFacade()
    {
        super(TbSintoma.class);
    }

    public boolean isSintomaTabelaEmpty()
    {
        return (this.findAll() == null || this.findAll().isEmpty());
    }

    public boolean existeRegisto(int pkSintoma)
    {
        TbSintoma reg = this.find(pkSintoma);
        return reg != null;
    }

    public List<TbSintoma> findSintoma(TbSintoma sintoma)
    {
        String query = constroiQuery(sintoma);

        TypedQuery<TbSintoma> t = em.createQuery(query, TbSintoma.class);

        if (sintoma.getDescricao() != null && !(sintoma.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", sintoma.getDescricao() + "%");
        }

        List<TbSintoma> sintomas = t.getResultList();

        return sintomas;
    }

    public String constroiQuery(TbSintoma sintoma)
    {
        String query = "SELECT a FROM TbSintoma a WHERE a.pkSintoma = a.pkSintoma";

        if (sintoma.getDescricao() != null && !(sintoma.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
