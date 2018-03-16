/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbTipoDoente;
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
public class TbTipoDoenteFacade extends AbstractFacade<TbTipoDoente>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbTipoDoenteFacade()
    {
        super(TbTipoDoente.class);
    }

    public boolean isTipoDoenteTabelaEmpty()
    {
        return (this.findAll() == null || this.findAll().isEmpty());
    }

    public boolean existeRegisto(int pkTipoDoente)
    {
        TbTipoDoente reg = this.find(pkTipoDoente);
        return reg != null;
    }

    public List<TbTipoDoente> findTipoDoente(TbTipoDoente tipoDoente)
    {
        String query = constroiQuery(tipoDoente);

        TypedQuery<TbTipoDoente> t = em.createQuery(query, TbTipoDoente.class);

        if (tipoDoente.getDescricao() != null && !(tipoDoente.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", tipoDoente.getDescricao() + "%");
        }

        List<TbTipoDoente> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbTipoDoente tipoDoente)
    {
        String query = "SELECT a FROM TbTipoDoente a WHERE a.pkTipoDoente = a.pkTipoDoente";

        if (tipoDoente.getDescricao() != null && !(tipoDoente.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
