/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbObservacoesMedicas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbObservacoesMedicasFacade extends AbstractFacade<AmbObservacoesMedicas>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbObservacoesMedicasFacade()
    {
        super(AmbObservacoesMedicas.class);
    }
    
    public List<AmbObservacoesMedicas> findAllOrderByNome()
    {
        return em.createQuery("SELECT a FROM AmbObservacoesMedicas a ORDER BY a.nome").getResultList();
    }
    
    public boolean isObservacoesMedicasTabelaEmpty()
    {
        List<AmbObservacoesMedicas> listAmbObservacoesMedicas = this.findAll();
        return (listAmbObservacoesMedicas == null || listAmbObservacoesMedicas.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdIntervalo)
    {
        AmbObservacoesMedicas reg = this.find(pkIdIntervalo);
        return reg != null;
    }
}
