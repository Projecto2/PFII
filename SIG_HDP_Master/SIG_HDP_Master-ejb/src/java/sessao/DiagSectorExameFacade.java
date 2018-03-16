/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagSectorExame;
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
public class DiagSectorExameFacade extends AbstractFacade<DiagSectorExame>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagSectorExameFacade()
    {
        super(DiagSectorExame.class);
    }
    
    public List<DiagSectorExame> findPesquisa(DiagSectorExame sectorExamePesquisar)
    {
        String query = constroiQuery(sectorExamePesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (sectorExamePesquisar.getPkIdSectorExame() != null)
        {
            qry.setParameter("pkIdSectorExame", sectorExamePesquisar.getPkIdSectorExame());
        }

        if (sectorExamePesquisar.getDescricaoSector() != null && !sectorExamePesquisar.getDescricaoSector().trim().isEmpty())
        {
            qry.setParameter("descricaoSector", sectorExamePesquisar.getDescricaoSector() + "%");
        }

        List<DiagSectorExame> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagSectorExame sectorExamePesquisar)
    {
        String query = "Select d FROM DiagSectorExame d WHERE :pesquisar = :pesquisar";

        if (sectorExamePesquisar.getPkIdSectorExame() != null)
        {
            query += " AND d.pkIdSectorExame = :pkIdSectorExame";
        }

        if (sectorExamePesquisar.getDescricaoSector() != null && !sectorExamePesquisar.getDescricaoSector().trim().isEmpty())
        {
            query += " AND d.descricaoSector LIKE :descricaoSector";
        }

        query += " ORDER BY d.descricaoSector";

        return query;
    }
    
    public boolean isSectorExameTabelaEmpty()
    {
        List<DiagSectorExame> list = this.findAll();
        return (list == null || list.isEmpty());
    }
}
