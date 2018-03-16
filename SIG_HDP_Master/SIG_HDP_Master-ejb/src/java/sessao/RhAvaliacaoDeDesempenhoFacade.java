/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhAvaliacaoDeDesempenho;
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
public class RhAvaliacaoDeDesempenhoFacade extends AbstractFacade<RhAvaliacaoDeDesempenho>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhAvaliacaoDeDesempenhoFacade ()
    {
        super(RhAvaliacaoDeDesempenho.class);
    }

    public List<RhAvaliacaoDeDesempenho> pesquisarPorAno (Integer ano)
    {
        TypedQuery<RhAvaliacaoDeDesempenho> t = em.createQuery("SELECT av FROM RhAvaliacaoDeDesempenho av "
                + "WHERE av.ano = :ano ORDER BY av.classificacao DESC", RhAvaliacaoDeDesempenho.class).setParameter("ano", ano);

        return t.getResultList();
    }
    
    public List<RhAvaliacaoDeDesempenho> pesquisarAvaliacao (Integer idFuncionario, Integer ano)
    {
        TypedQuery<RhAvaliacaoDeDesempenho> t = em.createQuery("SELECT a FROM RhAvaliacaoDeDesempenho a "
                 + "WHERE a.fkIdFuncionario.pkIdFuncionario = :idFuncionario AND a.ano = :ano", RhAvaliacaoDeDesempenho.class)
                 .setParameter("idFuncionario", idFuncionario).setParameter("ano", ano);

        return t.getResultList();
    }
    
    public boolean temAvaliacao (Integer idFuncionario, Integer ano)
    {
        return ! pesquisarAvaliacao(idFuncionario, ano).isEmpty();
    }
}
