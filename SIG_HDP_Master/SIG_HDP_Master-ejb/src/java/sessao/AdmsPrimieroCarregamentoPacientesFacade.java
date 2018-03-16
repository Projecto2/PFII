/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsCategoriaServico;
import entidade.AdmsPrimieroCarregamentoPacientes;
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
public class AdmsPrimieroCarregamentoPacientesFacade extends AbstractFacade<AdmsPrimieroCarregamentoPacientes>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsPrimieroCarregamentoPacientesFacade()
    {
        super(AdmsPrimieroCarregamentoPacientes.class);
    }
    
    public boolean jaExistiuCarregamento()
    {
        Query qry = em.createQuery("SELECT p FROM AdmsPrimieroCarregamentoPacientes as p");
        
        List<AdmsPrimieroCarregamentoPacientes> primeiroCarregamento = qry.getResultList();
        return ( (primeiroCarregamento != null) && (!primeiroCarregamento.isEmpty()) );
    } 
    
}
