/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmProduto;
import entidade.InterMedicacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author armindo
 */
@Stateless
public class InterMedicacaoFacade extends AbstractFacade<InterMedicacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterMedicacaoFacade()
    {
        super(InterMedicacao.class);
    }

    public FarmProduto pesquisarMedicamentoPorNome(String nomeMedicamento)
    {
         Query qrs = em.createQuery("SELECT o FROM FarmProduto o WHERE o.decricao = :medicamento");
         qrs.setParameter("medicamento", nomeMedicamento);
        
         List<FarmProduto> listaMediamentos = qrs.getResultList();
         
         return listaMediamentos.get(0);
    }
    
}
