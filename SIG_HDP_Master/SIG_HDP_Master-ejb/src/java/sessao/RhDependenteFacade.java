/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlProvincia;
import entidade.RhDependente;
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
public class RhDependenteFacade extends AbstractFacade<RhDependente>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhDependenteFacade ()
    {
        super(RhDependente.class);
    }
    
    /**
     * Pesquisa todos os dependentes de um funcionários
     * @param idFuncionario
     * @return
     */
    public List<RhDependente> pesquisaPorFuncionario (Integer idFuncionario)
    {
        TypedQuery<RhDependente> t = this.em.createQuery("SELECT d FROM RhDependente d WHERE d.fkIdFuncionario.pkIdFuncionario = :idFuncionario", RhDependente.class).setParameter("idFuncionario", idFuncionario);

        List<RhDependente> resultado = t.getResultList();

        return resultado;
    }
}
