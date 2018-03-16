/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlCentroHospitalar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class GrlCentroHospitalarFacade extends AbstractFacade<GrlCentroHospitalar>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public GrlCentroHospitalarFacade ()
    {
        super(GrlCentroHospitalar.class);
    }
    
    public GrlCentroHospitalar findCentroHospitalarDivina ()
    {
        Query t = em.createQuery("SELECT c FROM GrlCentroHospitalar c WHERE c.codigoCentro = 'HDP' OR c.fkIdInstituicao.codigoInstituicao = 'HDP'", GrlCentroHospitalar.class);

        List<GrlCentroHospitalar> resultado = t.getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<GrlCentroHospitalar> findCentroHospitalar (GrlCentroHospitalar centro)
    {
        String query = constroiQuery(centro);

        Query t = em.createQuery(query, GrlCentroHospitalar.class);


        if (centro.getFkIdInstituicao().getCodigoInstituicao() != null && !centro.getFkIdInstituicao().getCodigoInstituicao().trim().isEmpty())
        {
            t.setParameter("sigla", centro.getFkIdInstituicao().getCodigoInstituicao() + "%");
        }

        if (centro.getFkIdInstituicao().getDescricao() != null && !centro.getFkIdInstituicao().getDescricao().trim().isEmpty())
        {
            t.setParameter("descricao", centro.getFkIdInstituicao().getDescricao()+ "%");
        }

        List<GrlCentroHospitalar> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery (GrlCentroHospitalar centro)
    {
        String query = "SELECT c FROM GrlCentroHospitalar c WHERE c.pkIdCentro = c.pkIdCentro";

        if (centro.getFkIdInstituicao().getCodigoInstituicao() != null && !centro.getFkIdInstituicao().getCodigoInstituicao().trim().isEmpty())
        {
            query += " AND c.fkIdInstituicao.codigoInstituicao LIKE :sigla";
        }

        if (centro.getFkIdInstituicao().getDescricao() != null && !centro.getFkIdInstituicao().getDescricao().trim().isEmpty())
        {
            query += " AND c.fkIdInstituicao.descricao LIKE :descricao";
        }

        query += " ORDER BY c.fkIdInstituicao.descricao";

        return query;
    }
}
