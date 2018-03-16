/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhFuncionario;
import entidade.RhFuncionarioHasRhTipoFalta;
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
public class RhFuncionarioHasRhTipoFaltaFacade extends AbstractFacade<RhFuncionarioHasRhTipoFalta>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhFuncionarioHasRhTipoFaltaFacade ()
    {
        super(RhFuncionarioHasRhTipoFalta.class);
    }

    /**
     * Pesquisa todas as faltas de um funcionário
     *
     * @param idFuncionario
     * @return
     */
    public List<RhFuncionarioHasRhTipoFalta> findFaltas (Integer idFuncionario)
    {
        TypedQuery<RhFuncionarioHasRhTipoFalta> query;
        query = em.createQuery("SELECT f FROM RhFuncionarioHasRhTipoFalta f "
                               + "WHERE f.fkIdFuncionario.pkIdFuncionario = :idFuncionario "
                               + "ORDER BY f.data", RhFuncionarioHasRhTipoFalta.class).setParameter("idFuncionario", idFuncionario);

        return query.getResultList();
    }

    /**
     * Pesquisa todas as faltas de um funcionário num determinado
     * intervalo de datas 
     * 
     * @param idFuncionario
     * @param dataInf
     * @param dataSup
     * @return
     */
    public List<RhFuncionarioHasRhTipoFalta> findFaltas (Integer idFuncionario, Date dataInf, Date dataSup)
    {
        TypedQuery<RhFuncionarioHasRhTipoFalta> query;
        query = em.createQuery("SELECT f FROM RhFuncionarioHasRhTipoFalta f "
                               + "WHERE f.fkIdFuncionario.pkIdFuncionario = :idFuncionario "
                               + "AND f.data BETWEEN :dataInf AND :dataSup "
                               + "ORDER BY f.data", RhFuncionarioHasRhTipoFalta.class)
                .setParameter("idFuncionario", idFuncionario).setParameter("dataInf", dataInf).setParameter("dataSup", dataSup);;

        return query.getResultList();
    }

    /**
     * Pesquisa todas as faltas de todos os funcionários num determinado
     * intervalo de datas
     *
     * @param dataInf
     * @param dataSup
     * @return
     */
    public List<RhFuncionarioHasRhTipoFalta> findFaltas (Date dataInf, Date dataSup)
    {
        TypedQuery<RhFuncionarioHasRhTipoFalta> query;
        query = em.createQuery("SELECT f FROM RhFuncionarioHasRhTipoFalta f "
                               + "WHERE f.data BETWEEN :dataInf AND :dataSup "
                               + "ORDER BY f.data", RhFuncionarioHasRhTipoFalta.class).setParameter("dataInf", dataInf).setParameter("dataSup", dataSup);

        return query.getResultList();
    }

    /**
     * Pesquisa todas as faltas não justificadas de um funcionário num
     * determinado intervalo de datas
     *
     * @param idFuncionario
     * @param dataInferior
     * @param dataSuperior
     * @return
     */
    public List<RhFuncionarioHasRhTipoFalta> findFaltasNaoJustificadas (Integer idFuncionario, Date dataInferior, Date dataSuperior)
    {
        TypedQuery<RhFuncionarioHasRhTipoFalta> query;
        query = em.createQuery("SELECT f FROM RhFuncionarioHasRhTipoFalta f "
                               + "WHERE f.fkIdTipoFalta.descricao LIKE :naoJustificada AND "
                               + "f.fkIdFuncionario.pkIdFuncionario = :idFuncionario AND f.data BETWEEN :dataInferior AND :dataSuperior "
                               + "ORDER BY f.data", RhFuncionarioHasRhTipoFalta.class).setParameter("idFuncionario", idFuncionario)
                .setParameter("dataInferior", dataInferior).setParameter("dataSuperior", dataSuperior)
                .setParameter("naoJustificada", "Não Justificada");

        return query.getResultList();
    }

}
