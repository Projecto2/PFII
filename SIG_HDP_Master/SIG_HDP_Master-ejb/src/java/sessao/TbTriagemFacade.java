/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbConsulta;
import entidade.TbTriagem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author adelino
 */
@Stateless
public class TbTriagemFacade extends AbstractFacade<TbTriagem>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbTriagemFacade()
    {
        super(TbTriagem.class);
    }
    
    public List<AmbConsulta> findPacientes()
    {
        Query query = em.createQuery("SELECT p FROM AmbConsulta p");
        
        return query.getResultList();
    }
    
    public List<TbTriagem> findTriagem(TbTriagem triagem)
    {
        String query = constroiQuery(triagem);

        TypedQuery<TbTriagem> t = em.createQuery(query, TbTriagem.class);

        List<TbTriagem> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(TbTriagem triagem)
    {
        String query = "SELECT a FROM TbTriagem a WHERE a.pkTriagem = a.pkTriagem";

        if (triagem.getPkTriagem() != null)
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }
    
}
