/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlAreaInterna;
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
public class GrlAreaInternaFacade extends AbstractFacade<GrlAreaInterna>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlAreaInternaFacade()
    {
        super(GrlAreaInterna.class);
    }
    
    public GrlAreaInterna findByDescricao(String area)
    {

      Query qry = em.createQuery("SELECT areaInterna FROM GrlAreaInterna as areaInterna WHERE areaInterna.descricaoAreaInterna = 'Admissões'");

      List<GrlAreaInterna> areaInterna = qry.getResultList();

      return areaInterna.get(0);
    }

    /**
     *
     * @param areaInterna
     * @return
     */
    public List<GrlAreaInterna> findAreaInterna(GrlAreaInterna areaInterna)
    {
        String query = constroiQuery(areaInterna);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (areaInterna.getDescricaoAreaInterna() != null && !areaInterna.getDescricaoAreaInterna().trim().isEmpty())
        {
            qry.setParameter("descricaoAreaInterna", areaInterna.getDescricaoAreaInterna() + "%");
        }

        if (areaInterna.getCodigoAreaInterna() != null && !areaInterna.getCodigoAreaInterna().trim().isEmpty())
        {
            qry.setParameter("codigoAreaInterna", areaInterna.getCodigoAreaInterna() + "%");
        }

        List<GrlAreaInterna> areaInternas = qry.getResultList();

        return areaInternas;
    }

    public String constroiQuery(GrlAreaInterna areaInterna)
    {
        String query = "SELECT areaInterna FROM GrlAreaInterna areaInterna WHERE :pesquisar = :pesquisar";

        if (areaInterna.getDescricaoAreaInterna() != null && !areaInterna.getDescricaoAreaInterna().trim().isEmpty())
        {
            query += " AND areaInterna.descricaoAreaInterna LIKE :descricaoAreaInterna";
        }

        if (areaInterna.getCodigoAreaInterna() != null && !areaInterna.getCodigoAreaInterna().trim().isEmpty())
        {
            query += " AND areaInterna.codigoAreaInterna LIKE :codigoAreaInterna";
        }

        query += " ORDER BY areaInterna.descricaoAreaInterna";

      //query += " ORDER BY areaInterna.getFkIdPessoa().pkIdPessoa";
        return query;
    }
    
    
    
    public boolean isAreaInternaTabelaEmpty()
    {
        List<GrlAreaInterna> listAreaInterna = this.findAll();
        return (listAreaInterna == null || listAreaInterna.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdAreaInterna)
    {
        GrlAreaInterna reg = this.find(pkIdAreaInterna);
        return reg != null;
    }
    
    
}
