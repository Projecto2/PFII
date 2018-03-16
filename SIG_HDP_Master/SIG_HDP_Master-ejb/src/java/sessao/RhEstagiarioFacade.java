/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhEstagiario;
import java.util.Date;
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
public class RhEstagiarioFacade extends AbstractFacade<RhEstagiario>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhEstagiarioFacade ()
    {
        super(RhEstagiario.class);
    }

    /**
     * Encontra um estagiário de acordo o seu id de pessoa
     * 
     * @param idPessoa
     * @return
     */
    public List<RhEstagiario> findPorIdPessoa (Integer idPessoa)
    {
        TypedQuery<RhEstagiario> query = em.createQuery("SELECT e FROM RhEstagiario e WHERE e.fkIdPessoa.pkIdPessoa = :idPessoa", RhEstagiario.class).setParameter("idPessoa", idPessoa);

        return query.getResultList();
    }

    /**
     * Encontra um estagiário de acordo o seu número do BI
     * 
     * @param numBI 
     * @return
     */
    public List<RhEstagiario> findPorNumeroBI (String numBI)
    {
        TypedQuery<RhEstagiario> query = em.createQuery("SELECT e FROM RhEstagiario e WHERE e.numeroBi = :numBI", RhEstagiario.class).setParameter("numBI", numBI);

        return query.getResultList();
    }

    /**
     *
     * Procura todos os estagiários que têm de iniciar o estágio antes de uma
     * determinada data mas ainda encontram-se no estado aprovado
     *
     * @param data
     * @return
     */
    public List<RhEstagiario> procurarAprovadosComInicioAntesDe (Date data)
    {
        TypedQuery<RhEstagiario> query;
        query = em.createQuery("SELECT e FROM RhEstagiario e WHERE e.dataInicioEstagio <= :data "
                               + "AND e.fkIdEstadoEstagiario.descricao = 'Aprovado'", RhEstagiario.class).setParameter("data", data);

        return query.getResultList();
    }

    /**
     *
     * Procura todos os estagiários que têm de termiar o estágio antes de uma
     * determinada data mas ainda encontram-se no estado activo
     *
     * @param data
     * @return
     */
    public List<RhEstagiario> procurarActivosComTerminoAntesDe (Date data)
    {
        TypedQuery<RhEstagiario> query;
        query = em.createQuery("SELECT e FROM RhEstagiario e WHERE e.dataFimEstagio <= :data "
                               + "AND e.fkIdEstadoEstagiario.descricao = 'Activo'", RhEstagiario.class).setParameter("data", data);

        return query.getResultList();
    }

    /**
     * Pesquisa todos os estagiários de acordo com todos os campos que 
     * tiverem dados no objecto estagiario, ou seja, procura de acordo os critérios
     * introduzidos
     *
     * @param estagiario
     * @return
     */
    public List<RhEstagiario> findEstagiario (RhEstagiario estagiario)
    {
        String query = constroiQuery(estagiario);

        TypedQuery<RhEstagiario> t = em.createQuery(query, RhEstagiario.class);

        if (estagiario.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario() != null)
        {
            t.setParameter("estadoEstagiario", estagiario.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario());
        }

        if (estagiario.getFkIdPessoa().getNome() != null && !estagiario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            t.setParameter("nome", estagiario.getFkIdPessoa().getNome() + "%");
        }

        if (estagiario.getFkIdPessoa().getNomeDoMeio() != null && !estagiario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            t.setParameter("nomeDoMeio", estagiario.getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (estagiario.getFkIdPessoa().getSobreNome() != null && !estagiario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            t.setParameter("sobreNome", estagiario.getFkIdPessoa().getSobreNome() + "%");
        }

        if (estagiario.getNumeroBi() != null && !estagiario.getNumeroBi().trim().isEmpty())
        {
            t.setParameter("numBi", estagiario.getNumeroBi());
        }

        if (estagiario.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            t.setParameter("sexo", estagiario.getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        t.setMaxResults(100);

        List<RhEstagiario> resultado = t.getResultList();

        return resultado;
    }

    /**
     * Constrói uma query JPQL com todos os campos que 
     * tiverem dados no objecto estagiario
     * 
     * @param estagiario
     * @return
     */
    public String constroiQuery (RhEstagiario estagiario)
    {
        String query = "SELECT e FROM RhEstagiario e WHERE e.pkIdEstagiario = e.pkIdEstagiario";

        if (estagiario.getFkIdEstadoEstagiario().getPkIdEstadoEstagiario() != null)
        {
            query += " AND e.fkIdEstadoEstagiario.pkIdEstadoEstagiario = :estadoEstagiario";
        }

        if (estagiario.getFkIdPessoa().getNome() != null && !estagiario.getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND LOWER(e.fkIdPessoa.nome) LIKE LOWER(:nome)";
        }

        if (estagiario.getFkIdPessoa().getNomeDoMeio() != null && !estagiario.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND LOWER(e.fkIdPessoa.nomeDoMeio) LIKE LOWER(:nomeDoMeio)";
        }

        if (estagiario.getFkIdPessoa().getSobreNome() != null && !estagiario.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND LOWER(e.fkIdPessoa.sobreNome) LIKE LOWER(:sobreNome)";
        }

        if (estagiario.getNumeroBi() != null && !estagiario.getNumeroBi().trim().isEmpty())
        {
            query += " AND e.numeroBi LIKE :numBi";
        }

        if (estagiario.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND e.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " ORDER BY e.fkIdPessoa.nome, e.fkIdPessoa.nomeDoMeio, e.fkIdPessoa.sobreNome";

        return query;
    }

}
