/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.SegConta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adalberto
 */
@Stateless
public class SegContaFacade extends AbstractFacade<SegConta>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public SegContaFacade()
    {
        super(SegConta.class);
    }

    public List<SegConta> findContas(SegConta conta)
    {
        String query = constroiQueryUtilizador(conta);

        TypedQuery<SegConta> t = em.createQuery(query, SegConta.class);

        if (conta.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !conta.getFkIdFuncionario().getFkIdPessoa().getNome().isEmpty())
        {
            t.setParameter("nome", "%" + conta.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome().isEmpty())
        {
            t.setParameter("sobreNome", "%" + conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (conta.getNomeUtilizador() != null && !conta.getNomeUtilizador().isEmpty())
        {
            t.setParameter("nomeUtilizador", conta.getNomeUtilizador());
        }

        List<SegConta> resultado = t.getResultList();

        return resultado;
    }

    public SegConta findConta(SegConta conta)
    {
        String query = constroiQueryLogin(conta);

        TypedQuery<SegConta> t = em.createQuery(query, SegConta.class);

        if (conta.getNomeUtilizador() != null && !conta.getNomeUtilizador().isEmpty())
        {
            t.setParameter("nomeUtilizador", conta.getNomeUtilizador());
        }

        if (conta.getPalavraPasse() != null && !conta.getPalavraPasse().isEmpty())
        {
            t.setParameter("palavraPasse", conta.getPalavraPasse());
        }

        List<SegConta> resultado = t.getResultList();

        if (!resultado.isEmpty())
        {
            return resultado.get(0);
        }

        return null;
    }

    public String constroiQueryLogin(SegConta conta)
    {
        String query = "SELECT f FROM SegConta f WHERE f.pkIdConta = f.pkIdConta";

        if (conta.getNomeUtilizador() != null && !conta.getNomeUtilizador().isEmpty())
        {
            query += " AND f.nomeUtilizador LIKE :nomeUtilizador";
        }

        if (conta.getPalavraPasse() != null && !conta.getPalavraPasse().isEmpty())
        {
            query += " AND f.palavraPasse LIKE :palavraPasse";
        }

        return query;
    }

    public String constroiQueryUtilizador(SegConta conta)
    {
        String query = "SELECT f FROM SegConta f WHERE f.pkIdConta = f.pkIdConta";

        if (conta.getNomeUtilizador() != null && !conta.getNomeUtilizador().isEmpty())
        {
            query += " AND f.nomeUtilizador LIKE :nomeUtilizador";
        }

        if (conta.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !conta.getFkIdFuncionario().getFkIdPessoa().getNome().isEmpty())
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
        }

        if (conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !conta.getFkIdFuncionario().getFkIdPessoa().getSobreNome().isEmpty())
        {
            query += " AND f.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        return query;
    }

   
    public SegConta getUtilizadorBypalavraPasseAndnomeUtilizador(String username, String password)
    {
        TypedQuery<SegConta> query;
        query = em.createQuery("SELECT scu FROM SegConta scu WHERE scu.palavraPasse = :password AND scu.nomeUtilizador = :username", SegConta.class);

        query.setParameter("username", username)
            .setParameter("password", password);

        List<SegConta> results = query.getResultList();

        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results.get(0);
        }

    }

    /**
     * Verificar se o utilizador ja existe
     *
     * @param nomeUtilizador
     * @return
     */
    public SegConta existeContaUtilizador(String nomeUtilizador)
    {
        TypedQuery<SegConta> query;
        query = em.createQuery("SELECT scu FROM SegConta scu WHERE scu.nomeUtilizador = :nomeUtilizador", SegConta.class);

        query.setParameter("nomeUtilizador", nomeUtilizador);

        List<SegConta> results = query.getResultList();

        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results.get(0);
        }

    }

    /**
     * Verificar se o utilizador ja existe
     *
     * @param nomeUtilizador
     * @return
     */
    public SegConta existeContaUtilizadorEdit(String nomeUtilizador)
    {
        TypedQuery<SegConta> query;
        query = em.createQuery("SELECT scu FROM SegConta scu WHERE scu.nomeUtilizador <> nomeUtilizador", SegConta.class);

        query.setParameter("username", nomeUtilizador);

        List<SegConta> results = query.getResultList();

        if (results.isEmpty())
        {
            return null;
        }
        else
        {
            return results.get(0);
        }

    }
}
