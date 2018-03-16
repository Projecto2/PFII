/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterTipoDoencaInternamento;
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
public class InterTipoDoencaInternamentoFacade extends AbstractFacade<InterTipoDoencaInternamento>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterTipoDoencaInternamentoFacade()
    {
        super(InterTipoDoencaInternamento.class);
    }

    public List<InterTipoDoencaInternamento> findByDescricao(String descricao)
    {
        Query qrs = em.createQuery("SELECT o FROM InterTipoDoencaInternamento o WHERE o.descricao LIKE :valor ORDER BY o.descricao");
        qrs.setParameter("valor", "%"+descricao+"%");

        return qrs.getResultList();
    }

    public boolean isTipoDoencaInternamentoTabelaEmpty()
    {
        List<InterTipoDoencaInternamento> listTipoDoencaInternamento = this.findAll();
        return (listTipoDoencaInternamento == null || listTipoDoencaInternamento.isEmpty());
    }
}
