/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCeConfiguracoes;
import entidade.GrlCentroHospitalar;
import entidade.GrlEspecialidade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbCeConfiguracoesFacade extends AbstractFacade<AmbCeConfiguracoes>
{
    @EJB
    private GrlCentroHospitalarFacade grlCentroHospitalarFacade;
    
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCeConfiguracoesFacade()
    {
        super(AmbCeConfiguracoes.class);
    }
    
    public AmbCeConfiguracoes findAllByIdConta(int idConta)
    {
        Query q = em.createQuery("SELECT a FROM AmbCeConfiguracoes a WHERE a.idConta = :idConta");
        q.setParameter("idConta", idConta);
        List<AmbCeConfiguracoes> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }  
    
    public AmbCeConfiguracoes loadAmbCeConfiguracoes(int idConta)
    {
        return this.findAllByIdConta(idConta);
    } 

    public List<GrlCentroHospitalar> findAllOrderBYCentro(int idCentro)
    {
        return em.createQuery("SELECT g FROM GrlCentroHospitalar g ORDER BY g.pkIdCentro")
                 .setParameter("idCentro", idCentro).getResultList();
    }    
    
    public List<GrlCentroHospitalar> findAllOrderBYPkId()
    {
        Query q = em.createQuery("SELECT g FROM GrlCentroHospitalar g ORDER BY g.pkIdCentro");
        return q.getResultList();
    }

    public List<GrlEspecialidade> findAllByIdEspecialidade(int idEspecialidade)
    {
        return em.createQuery("SELECT g FROM GrlEspecialidade g WHERE g.pkIdEspecialidade = :idEspecialidade")
                 .setParameter("idEspecialidade", idEspecialidade).getResultList();
    }      
}
