/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagEstadoClinico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagEstadoClinicoFacade extends AbstractFacade<DiagEstadoClinico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagEstadoClinicoFacade()
    {
        super(DiagEstadoClinico.class);
    }
    
    public boolean isEstadoClinicoTabelaEmpty()
    {
        List<DiagEstadoClinico> list = this.findAll();
        return (list == null || list.isEmpty());
    }
    
    public List<DiagEstadoClinico> findPesquisa(DiagEstadoClinico estadoClinicoPesquisar)
    {
        String query = constroiQuery(estadoClinicoPesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (estadoClinicoPesquisar.getPkIdEstadoClinico() != null)
        {
            qry.setParameter("pkIdEstadoClinico", estadoClinicoPesquisar.getPkIdEstadoClinico());
        }

        if (estadoClinicoPesquisar.getDescricaoEstadoClinico()!= null && !estadoClinicoPesquisar.getDescricaoEstadoClinico().trim().isEmpty())
        {
            qry.setParameter("descricaoEstadoClinico", estadoClinicoPesquisar.getDescricaoEstadoClinico()+ "%");
        }

        List<DiagEstadoClinico> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagEstadoClinico estadoClinicoPesquisar)
    {
        String query = "Select d FROM DiagEstadoClinico d WHERE :pesquisar = :pesquisar";

        if (estadoClinicoPesquisar.getPkIdEstadoClinico() != null)
        {
            query += " AND d.pkIdEstadoClinico = :pkIdEstadoClinico";
        }

        if (estadoClinicoPesquisar.getDescricaoEstadoClinico()!= null && !estadoClinicoPesquisar.getDescricaoEstadoClinico().trim().isEmpty())
        {
            query += " AND d.descricaoEstadoClinico LIKE :descricaoEstadoClinico";
        }

        query += " ORDER BY d.descricaoEstadoClinico";

        return query;
    }
    
}
