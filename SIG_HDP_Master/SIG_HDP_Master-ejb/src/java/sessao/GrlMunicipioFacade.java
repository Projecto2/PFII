/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlMunicipio;
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
public class GrlMunicipioFacade extends AbstractFacade<GrlMunicipio>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlMunicipioFacade ()
    {
        super(GrlMunicipio.class);
    }

    public List<GrlMunicipio> pesquisaPorProvincia (Integer idProvincia)
    {
        TypedQuery<GrlMunicipio> t = this.em.createQuery("SELECT m FROM GrlMunicipio m WHERE m.fkIdProvincia.pkIdProvincia = :idProvincia", GrlMunicipio.class).setParameter("idProvincia", idProvincia);

        List<GrlMunicipio> resultado = t.getResultList();

        return resultado;
    }

    public List<GrlMunicipio> findMunicipio (GrlMunicipio municipio)
    {
        String query = constroiQuery(municipio);

        TypedQuery<GrlMunicipio> t = em.createQuery(query, GrlMunicipio.class);

        if (municipio.getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            t.setParameter("pais", municipio.getFkIdProvincia().getFkIdPais().getPkIdPais());
        }

        if (municipio.getFkIdProvincia().getPkIdProvincia() != null)
        {
            t.setParameter("provincia", municipio.getFkIdProvincia().getPkIdProvincia());
        }

        if (municipio.getNomeMunicipio() != null && !municipio.getNomeMunicipio().trim().isEmpty())
        {
            t.setParameter("nomeMunicipio", municipio.getNomeMunicipio() + "%");
        }

        List<GrlMunicipio> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlMunicipio municipio)
    {
        String query = "SELECT m FROM GrlMunicipio m WHERE m.pkIdMunicipio = m.pkIdMunicipio";

        if (municipio.getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            query += " AND m.fkIdProvincia.fkIdPais.pkIdPais = :pais";
        }

        if (municipio.getFkIdProvincia().getPkIdProvincia() != null)
        {
            query += " AND m.fkIdProvincia.pkIdProvincia = :provincia";
        }

        if (municipio.getNomeMunicipio() != null && !municipio.getNomeMunicipio().trim().isEmpty())
        {
            query += " AND m.nomeMunicipio LIKE :nomeMunicipio";
        }

        query += " ORDER BY m.fkIdProvincia.fkIdPais.nomePais, m.fkIdProvincia.nomeProvincia, m.nomeMunicipio";

        return query;
    }
    
    
    public boolean isMunicipioTabelaEmpty()
    {
        List<GrlMunicipio> listMunicipio = this.findAll();
        return (listMunicipio == null || listMunicipio.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdMunicipio)
    {
        GrlMunicipio reg = this.find(pkIdMunicipio);
        return reg != null;
    }

}
