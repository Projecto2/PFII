/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagCategoriaExame;
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
public class DiagCategoriaExameFacade extends AbstractFacade<DiagCategoriaExame>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagCategoriaExameFacade()
    {
        super(DiagCategoriaExame.class);
    }

    @Override
    public List<DiagCategoriaExame> findAll()
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagCategoriaExame d ORDER BY d.descricaoCategoria", DiagCategoriaExame.class);
        return typedQuery.getResultList();
    }
    
    public List<DiagCategoriaExame> findPesquisa(DiagCategoriaExame categoriaExamePesquisar)
    {
        String query = constroiQuery(categoriaExamePesquisar);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (categoriaExamePesquisar.getPkIdCategoria() != null)
        {
            qry.setParameter("pkIdCategoriaExame", categoriaExamePesquisar.getPkIdCategoria());
        }

        if (categoriaExamePesquisar.getDescricaoCategoria() != null && !categoriaExamePesquisar.getDescricaoCategoria().trim().isEmpty())
        {
            qry.setParameter("descricaoCategoria", categoriaExamePesquisar.getDescricaoCategoria() + "%");
        }

        if (categoriaExamePesquisar.getFkIdSector().getPkIdSectorExame() != null)
        {
            qry.setParameter("pkIdSectorExame", categoriaExamePesquisar.getFkIdSector().getPkIdSectorExame());
        }

        List<DiagCategoriaExame> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagCategoriaExame categoriaExamePesquisar)
    {
        String query = "Select d FROM DiagCategoriaExame d WHERE :pesquisar = :pesquisar";

        if (categoriaExamePesquisar.getPkIdCategoria() != null)
        {
            query += " AND d.pkIdCategoriaExame = :pkIdCategoriaExame";
        }

        if (categoriaExamePesquisar.getDescricaoCategoria() != null && !categoriaExamePesquisar.getDescricaoCategoria().trim().isEmpty())
        {
            query += " AND d.descricaoCategoria LIKE :descricaoCategoria";
        }

        if (categoriaExamePesquisar.getFkIdSector().getPkIdSectorExame() != null)
        {
            query += " AND d.fkIdSector.pkIdSectorExame = :pkIdSectorExame";
        }

        query += " ORDER BY d.descricaoCategoria";

        return query;
    }
    
    public boolean isCategoriaExameTabelaEmpty()
    {
        List<DiagCategoriaExame> listCategoriasExame = this.findAll();
        return (listCategoriasExame == null || listCategoriasExame.isEmpty());
    }
}
