/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhSalarioFuncionario;
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
public class RhSalarioFuncionarioFacade extends AbstractFacade<RhSalarioFuncionario>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhSalarioFuncionarioFacade ()
    {
        super(RhSalarioFuncionario.class);
    }

    /**
     * Pesquisa os salários de um funcionário
     * 
     * @param idFuncionario 
     * @return
     */
    public List<RhSalarioFuncionario> findPorIdFuncionario (Integer idFuncionario)
    {
        TypedQuery<RhSalarioFuncionario> query = em.createQuery("SELECT s FROM RhSalarioFuncionario s "
                                                                + "WHERE s.fkIdFuncionario.pkIdFuncionario = :idFuncionario", RhSalarioFuncionario.class).setParameter("idPessoa", idFuncionario);
        return query.getResultList();
    }

    /**
     * Pesquisa o último salário de um funcionário
     * 
     * @param idFuncionario 
     * @return
     */
    public List<RhSalarioFuncionario> findActualPorIdFuncionario (Integer idFuncionario)
    {
        TypedQuery<RhSalarioFuncionario> query = em.createQuery("SELECT s FROM RhSalarioFuncionario s "
                                                                + "WHERE s.fkIdFuncionario.pkIdFuncionario = :idFuncionario "
                                                                + "ORDER BY s.dataCadastro DESC", RhSalarioFuncionario.class).setParameter("idFuncionario", idFuncionario);
        return query.getResultList();
    }
}
