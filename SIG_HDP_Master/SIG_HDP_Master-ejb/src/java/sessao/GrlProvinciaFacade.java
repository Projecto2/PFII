/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlProvincia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlProvinciaFacade extends AbstractFacade<GrlProvincia>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlProvinciaFacade ()
    {
        super(GrlProvincia.class);
    }

    public List<GrlProvincia> pesquisaPorPais (Integer idPais)
    {
        TypedQuery<GrlProvincia> t = this.em.createQuery("SELECT p FROM GrlProvincia p WHERE p.fkIdPais.pkIdPais = :idPais", GrlProvincia.class).setParameter("idPais", idPais);

        List<GrlProvincia> resultado = t.getResultList();

        return resultado;
    }

    public List<GrlProvincia> findProvincia (GrlProvincia provincia)
    {
        String query = constroiQuery(provincia);

        TypedQuery<GrlProvincia> t = em.createQuery(query, GrlProvincia.class);

        if (provincia.getFkIdPais().getPkIdPais() != null)
        {
            t.setParameter("pais", provincia.getFkIdPais().getPkIdPais());
        }

        if (provincia.getNomeProvincia() != null && !provincia.getNomeProvincia().trim().isEmpty())
        {
            t.setParameter("nomeProvincia", provincia.getNomeProvincia() + "%");
        }

        List<GrlProvincia> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlProvincia provincia)
    {
        String query = "SELECT p FROM GrlProvincia p WHERE p.pkIdProvincia = p.pkIdProvincia";

        if (provincia.getFkIdPais().getPkIdPais() != null)
        {
            query += " AND p.fkIdPais.pkIdPais = :pais";
        }

        if (provincia.getNomeProvincia() != null && !provincia.getNomeProvincia().trim().isEmpty())
        {
            query += " AND p.nomeProvincia LIKE :nomeProvincia";
        }

        query += " ORDER BY p.fkIdPais.nomePais, p.nomeProvincia";

        return query;
    }
    
    
    public boolean isProvinciaTabelaEmpty()
    {
        List<GrlProvincia> listProvincia = this.findAll();
        return (listProvincia == null || listProvincia.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdProvincia)
    {
        GrlProvincia reg = this.find(pkIdProvincia);
        return reg != null;
    }
    
}
