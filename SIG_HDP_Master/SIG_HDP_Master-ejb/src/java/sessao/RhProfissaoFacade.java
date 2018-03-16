/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhProfissao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhProfissaoFacade extends AbstractFacade<RhProfissao>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhProfissaoFacade ()
    {
        super(RhProfissao.class);
    }

    @Override
    public List<RhProfissao> findAll ()
    {
        Query t = em.createQuery("SELECT p FROM RhProfissao p ORDER BY p.descricao", RhProfissao.class);

        return t.getResultList();
    }

    /**
     * Pesquisa a profissão com uma descrição<br/>
     *
     * @param descricao
     * @return
     */
    public List<RhProfissao> pesquisaPorDescricao (String descricao)
    {
        TypedQuery<RhProfissao> t = em.createQuery("SELECT pf FROM RhProfissao pf "
                                                   + "WHERE pf.descricao = :descricao", RhProfissao.class).setParameter("descricao", descricao);

        List<RhProfissao> resultado = t.getResultList();

        return resultado;
    }

    public RhProfissao findByDescricao (String descricao)
    {
        List<RhProfissao> resultado = pesquisaPorDescricao(descricao);

        return (resultado == null || resultado.isEmpty()) ? null : resultado.get(0);
    }
}
