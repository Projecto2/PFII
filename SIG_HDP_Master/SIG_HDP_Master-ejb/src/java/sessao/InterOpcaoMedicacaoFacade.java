/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterOpcaoMedicacao;
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
public class InterOpcaoMedicacaoFacade extends AbstractFacade<InterOpcaoMedicacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterOpcaoMedicacaoFacade()
    {
        super(InterOpcaoMedicacao.class);
    }

    public List<InterOpcaoMedicacao> findByDescricao()
    {
         Query qrs = em.createQuery("SELECT o FROM InterOpcaoMedicacao o WHERE o.codigo != :valor1");
         qrs.setParameter("valor1", "MF");
        
         return qrs.getResultList();
    }
    
    public boolean isOpcaoMedicacaoTabelaEmpty()
    {
        List<InterOpcaoMedicacao> listOpcaoMedicacao = this.findAll();
        return (listOpcaoMedicacao == null || listOpcaoMedicacao.isEmpty());
    }

    public List<InterOpcaoMedicacao> findAllByDescricao(String descricaoPesq, String cod)
    {
        Query qrs = em.createQuery("SELECT o FROM InterOpcaoMedicacao o WHERE o.codigo LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+cod+"%");

        return qrs.getResultList();   
    }
}
