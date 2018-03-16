/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsGrupoServico;
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
public class AdmsGrupoServicoFacade extends AbstractFacade<AdmsGrupoServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsGrupoServicoFacade()
    {
        super(AdmsGrupoServico.class);
    }
    
    
    public List<AdmsGrupoServico> findGrupoServico(AdmsGrupoServico grupoServico)
    {
        String query = constroiQuery(grupoServico);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (grupoServico.getDescricaoGrupoServico() != null && !grupoServico.getDescricaoGrupoServico().trim().isEmpty())
        {
            qry.setParameter("descricaoGrupoServico", grupoServico.getDescricaoGrupoServico() + "%");
        }

        if (grupoServico.getFkIdGrupoServicoPai().getPkIdGrupoServico()!= null)
        {
            qry.setParameter("fkIdGrupoServicoPai", grupoServico.getFkIdGrupoServicoPai());
        }
        
        System.out.println(""+query);

        List<AdmsGrupoServico> grupoServicos = qry.getResultList();

        return grupoServicos;
    }

    public String constroiQuery(AdmsGrupoServico grupoServico)
    {
        String query = "SELECT grupoServico FROM AdmsGrupoServico grupoServico WHERE :pesquisar = :pesquisar";

        if (grupoServico.getDescricaoGrupoServico() != null && !grupoServico.getDescricaoGrupoServico().trim().isEmpty())
        {
            query += " AND LOWER(grupoServico.descricaoGrupoServico) LIKE LOWER(:descricaoGrupoServico)";
        }

        if (grupoServico.getFkIdGrupoServicoPai().getPkIdGrupoServico() != null)
        {
            query += " AND grupoServico.fkIdGrupoServicoPai = :fkIdGrupoServicoPai";
        }

        query += " ORDER BY grupoServico.descricaoGrupoServico";

        return query;
    }
    
    
    public boolean isGrupoServicoTabelaEmpty()
    {
        List<AdmsGrupoServico> listGrupos = this.findAll();
        return (listGrupos == null || listGrupos.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdGrupoServico)
    {
        AdmsGrupoServico reg = this.find(pkIdGrupoServico);
        return reg != null;
    }
    
}
