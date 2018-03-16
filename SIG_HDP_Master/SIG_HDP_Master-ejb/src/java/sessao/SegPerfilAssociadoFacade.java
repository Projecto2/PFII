/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.SegPerfil;
import entidade.SegPerfilAssociado;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegPerfilAssociadoFacade extends AbstractFacade<SegPerfilAssociado>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegPerfilAssociadoFacade()
    {
        super(SegPerfilAssociado.class);
    }
    
     public List<SegPerfil> getPerfilByPerfil(Integer idperfil) {
        Query query;
        query = em.createQuery("SELECT rp.segPerfil FROM SegPerfilAssociado rp WHERE rp.segPerfilAssociadoPK.idPerfilFilho = :idperfil");

        query.setParameter("idperfil", idperfil);

        List<SegPerfil> results = (List<SegPerfil>) query.getResultList();

        //System.out.println("query: " + results);
        if (results.isEmpty()) {
            return new ArrayList<SegPerfil>();
        } else {
            return results;
        }

    }
    
}
