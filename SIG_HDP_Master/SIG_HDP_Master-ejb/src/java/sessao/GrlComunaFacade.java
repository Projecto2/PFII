/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlComuna;
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
public class GrlComunaFacade extends AbstractFacade<GrlComuna>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlComunaFacade ()
    {
        super(GrlComuna.class);
    }
    
    public List<GrlComuna> pesquisaPorMunicipio (Integer idMunicipio)
    {
        TypedQuery<GrlComuna> t = this.em.createQuery("SELECT c FROM GrlComuna c WHERE c.fkIdMunicipio.pkIdMunicipio = :idMunicipio", GrlComuna.class).setParameter("idMunicipio", idMunicipio);

        List<GrlComuna> resultado = t.getResultList();

        return resultado;
    }

    public List<GrlComuna> findComuna (GrlComuna comuna)
    {
        String query = constroiQuery(comuna);

        TypedQuery<GrlComuna> t = em.createQuery(query, GrlComuna.class);

        if (comuna.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            t.setParameter("pais", comuna.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
        }

        if (comuna.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia() != null)
        {
            t.setParameter("provincia", comuna.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());
        }

        if (comuna.getFkIdMunicipio().getPkIdMunicipio() != null)
        {
            t.setParameter("municipio", comuna.getFkIdMunicipio().getPkIdMunicipio());
        }

        if (comuna.getDescricaoComuna() != null && !comuna.getDescricaoComuna().trim().isEmpty())
        {
            t.setParameter("descricaoComuna", comuna.getDescricaoComuna() + "%");
        }

        List<GrlComuna> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlComuna comuna)
    {
        String query = "SELECT c FROM GrlComuna c WHERE c.pkIdComuna = c.pkIdComuna";

        if (comuna.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            query += " AND c.fkIdMunicipio.fkIdProvincia.fkIdPais.pkIdPais = :pais";
        }

        if (comuna.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia() != null)
        {
            query += " AND c.fkIdMunicipio.fkIdProvincia.pkIdProvincia = :provincia";
        }

        if (comuna.getFkIdMunicipio().getPkIdMunicipio() != null)
        {
            query += " AND c.fkIdMunicipio.pkIdMunicipio = :municipio";
        }

        if (comuna.getDescricaoComuna() != null && !comuna.getDescricaoComuna().trim().isEmpty())
        {
            query += " AND c.descricaoComuna LIKE :descricaoComuna";
        }

        query += " ORDER BY c.fkIdMunicipio.fkIdProvincia.fkIdPais.nomePais, c.fkIdMunicipio.fkIdProvincia.nomeProvincia, c.fkIdMunicipio.nomeMunicipio, c.descricaoComuna";

        return query;
    }
    
    
    public boolean isComunaTabelaEmpty()
    {
        List<GrlComuna> listComuna = this.findAll();
        return (listComuna == null || listComuna.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdComuna)
    {
        GrlComuna reg = this.find(pkIdComuna);
        return reg != null;
    }
    

}
