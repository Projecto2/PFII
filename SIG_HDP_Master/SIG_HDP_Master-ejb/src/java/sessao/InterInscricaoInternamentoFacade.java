/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterInscricaoInternamento;
import entidade.InterRegistoInternamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class InterInscricaoInternamentoFacade extends AbstractFacade<InterInscricaoInternamento> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterInscricaoInternamentoFacade() {
        super(InterInscricaoInternamento.class);
    }

    public int getUltimaInscricao(String tipoServico)
    {
         Query qrs = em.createQuery("SELECT o FROM InterRegistoInternamento o WHERE o.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :servico");
         qrs.setParameter("servico", tipoServico);
        
         List<InterRegistoInternamento> listaRegistos = qrs.getResultList();
         
         if (qrs.getResultList().isEmpty())
             return 1;
         return listaRegistos.size()+1;
    }
        
}
