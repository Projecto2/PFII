/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagCategoriaExame;
import entidade.DiagSubcategoriaExame;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagSubcategoriaExameFacade extends AbstractFacade<DiagSubcategoriaExame>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagSubcategoriaExameFacade()
    {
        super(DiagSubcategoriaExame.class);
    }

    @Override
    public List<DiagSubcategoriaExame> findAll()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagSubcategoriaExame d ORDER BY d.descricaoSubcategoria", DiagSubcategoriaExame.class);
        return typedQuery.getResultList();
    }
    
    public List<DiagSubcategoriaExame> findPesquisa(DiagSubcategoriaExame subcategoriaExame)
    {
        String query = constroiQuery(subcategoriaExame);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (subcategoriaExame.getPkIdSubcategoriaExame() != null)
        {
            qry.setParameter("pkIdSubcategoriaExame", subcategoriaExame.getPkIdSubcategoriaExame());
        }

        if (subcategoriaExame.getDescricaoSubcategoria() != null && !subcategoriaExame.getDescricaoSubcategoria().trim().isEmpty())
        {
            qry.setParameter("descricaoSubcategoria", subcategoriaExame.getDescricaoSubcategoria() + "%");
        }

        if (subcategoriaExame.getFkIdCategoria().getPkIdCategoria() != null)
        {
            qry.setParameter("pkIdCategoria", subcategoriaExame.getFkIdCategoria());
        }

        List<DiagSubcategoriaExame> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagSubcategoriaExame subcategoriaExame)
    {
        String query = "Select d FROM DiagSubcategoriaExame d WHERE :pesquisar = :pesquisar";

        if (subcategoriaExame.getPkIdSubcategoriaExame() != null)
        {
            query += " AND d.pkIdSubcategoriaExame = :pkIdSubcategoriaExame";
        }

        if (subcategoriaExame.getDescricaoSubcategoria() != null && !subcategoriaExame.getDescricaoSubcategoria().trim().isEmpty())
        {
            query += " AND d.descricaoSubcategoria LIKE :descricaoSubcategoria";
        }

        if (subcategoriaExame.getFkIdCategoria().getPkIdCategoria() != null)
        {
            query += " AND d.fkIdCategoria = :pkIdCategoria";
        }

        query += " ORDER BY d.descricaoSubcategoria";

        return query;
    }
    
    public boolean isSubcategoriaExameTabelaEmpty()
    {
        List<DiagSubcategoriaExame> listSubcategoriaExame = this.findAll();
        return (listSubcategoriaExame == null || listSubcategoriaExame.isEmpty());
    }
}
