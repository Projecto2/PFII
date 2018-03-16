/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhTipoFuncionario;
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
public class RhTipoFuncionarioFacade extends AbstractFacade<RhTipoFuncionario>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhTipoFuncionarioFacade ()
    {
        super(RhTipoFuncionario.class);
    }
    
    @Override
    public List<RhTipoFuncionario> findAll()
    {
          Query t = em.createQuery("SELECT t FROM RhTipoFuncionario t ORDER BY t.descricao", RhTipoFuncionario.class);

          return t.getResultList();
    }
    
    /**
     * Pesquisa um tipo de funcionário com uma determinada descrição<br/>
     * Não retorna o funcionário, mas sim o tipo
     * 
     * @param descricao
     * @return
     */
    public List<RhTipoFuncionario> pesquisaPorDescricao(String descricao)
    {
        TypedQuery<RhTipoFuncionario> t = this.em.createQuery("SELECT tf FROM RhTipoFuncionario tf WHERE tf.descricao = :descricao", RhTipoFuncionario.class)
                .setParameter("descricao", descricao);

        List<RhTipoFuncionario> resultado = t.getResultList();

        return resultado;
    }
}
