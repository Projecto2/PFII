/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbGrupoRisco;
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
public class TbGrupoRiscoFacade extends AbstractFacade<TbGrupoRisco>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbGrupoRiscoFacade()
    {
        super(TbGrupoRisco.class);
    }

    public boolean isGrupoRiscoTabelaEmpty()
    {
        List<TbGrupoRisco> listTbGrupoRisco = this.findAll();
        return (listTbGrupoRisco == null || listTbGrupoRisco.isEmpty());
    }

    public boolean existeRegisto(int pkGrupoRisco)
    {
        TbGrupoRisco reg = this.find(pkGrupoRisco);
        return reg != null;
    }

    public List<TbGrupoRisco> findGrupoRisco(TbGrupoRisco grupoRisco)
    {
        String query = constroiQuery(grupoRisco);

        TypedQuery<TbGrupoRisco> t = em.createQuery(query, TbGrupoRisco.class);

        if (grupoRisco.getDescricao() != null && !(grupoRisco.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", grupoRisco.getDescricao() + "%");
        }

        List<TbGrupoRisco> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbGrupoRisco grupoRisco)
    {
        String query = "SELECT a FROM TbGrupoRisco a WHERE a.pkGrupoRisco = a.pkGrupoRisco";

        if (grupoRisco.getDescricao() != null && !(grupoRisco.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
