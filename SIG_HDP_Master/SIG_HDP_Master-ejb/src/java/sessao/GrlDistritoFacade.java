/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlDistrito;
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
public class GrlDistritoFacade extends AbstractFacade<GrlDistrito>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlDistritoFacade ()
    {
        super(GrlDistrito.class);
    }
    
    public List<GrlDistrito> pesquisaPorMunicipio (Integer idMunicipio)
    {
        TypedQuery<GrlDistrito> t = this.em.createQuery("SELECT d FROM GrlDistrito d WHERE d.fkIdMunicipio.pkIdMunicipio = :idMunicipio", GrlDistrito.class).setParameter("idMunicipio", idMunicipio);

        List<GrlDistrito> resultado = t.getResultList();

        return resultado;
    }

    public List<GrlDistrito> findDistrito (GrlDistrito distrito)
    {
        String query = constroiQuery(distrito);

        TypedQuery<GrlDistrito> t = em.createQuery(query, GrlDistrito.class);

        if (distrito.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            t.setParameter("pais", distrito.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais());
        }

        if (distrito.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia() != null)
        {
            t.setParameter("provincia", distrito.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia());
        }

        if (distrito.getFkIdMunicipio().getPkIdMunicipio() != null)
        {
            t.setParameter("municipio", distrito.getFkIdMunicipio().getPkIdMunicipio());
        }

        if (distrito.getDescricao() != null && !distrito.getDescricao().trim().isEmpty())
        {
            t.setParameter("descricao", distrito.getDescricao() + "%");
        }

        List<GrlDistrito> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlDistrito distrito)
    {
        String query = "SELECT d FROM GrlDistrito d WHERE d.pkIdDistrito = d.pkIdDistrito";

        if (distrito.getFkIdMunicipio().getFkIdProvincia().getFkIdPais().getPkIdPais() != null)
        {
            query += " AND d.fkIdMunicipio.fkIdProvincia.fkIdPais.pkIdPais = :pais";
        }

        if (distrito.getFkIdMunicipio().getFkIdProvincia().getPkIdProvincia() != null)
        {
            query += " AND d.fkIdMunicipio.fkIdProvincia.pkIdProvincia = :provincia";
        }

        if (distrito.getFkIdMunicipio().getPkIdMunicipio() != null)
        {
            query += " AND d.fkIdMunicipio.pkIdMunicipio = :municipio";
        }

        if (distrito.getDescricao() != null && !distrito.getDescricao().trim().isEmpty())
        {
            query += " AND d.descricao LIKE :descricao";
        }

        query += " ORDER BY d.fkIdMunicipio.fkIdProvincia.fkIdPais.nomePais, d.fkIdMunicipio.fkIdProvincia.nomeProvincia, d.fkIdMunicipio.nomeMunicipio, d.descricao";

        return query;
    }
    
    
    public boolean isDistritoTabelaEmpty()
    {
        List<GrlDistrito> listDistrito = this.findAll();
        return (listDistrito == null || listDistrito.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdDistrito)
    {
        GrlDistrito reg = this.find(pkIdDistrito);
        return reg != null;
    }
}
