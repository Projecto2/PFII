/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.RhFerias;
import entidade.RhSeccaoTrabalho;
import java.util.Date;
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
public class RhFeriasFacade extends AbstractFacade<RhFerias>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhFeriasFacade ()
    {
        super(RhFerias.class);
    }

    /**
     * Procura por funcionário as férias activas ou marcadas num determinado
     * intevalo de tempo
     *
     * @param idFuncionario
     * @param dataInf
     * @param dataSup
     * @return
     */
    public List<RhFerias> findFeriasActivasOuMarcadasFuncionario (Integer idFuncionario, Date dataInf, Date dataSup)
    {
        TypedQuery<RhFerias> t = em.createQuery("SELECT f FROM RhFerias f "
                                                + "WHERE (f.fkIdEstadoFerias.descricao = 'Activo' OR f.fkIdEstadoFerias.descricao = 'Marcado') "
                                                + "AND f.fkIdFuncionario.pkIdFuncionario = :idFuncionario "
                                                + "AND f.dataInicio BETWEEN :dataInf AND :dataSup", RhFerias.class)
                .setParameter("dataInf", dataInf).setParameter("dataSup", dataSup)
                .setParameter("idFuncionario", idFuncionario);

        return t.getResultList();
    }

    /**
     * Procura por todas as férias independente do estado, num determinado
     * intervalo de datas
     *
     * @param dataInf
     * @param dataSup
     * @return
     */
    public List<RhFerias> findFerias (Date dataInf, Date dataSup)
    {
        TypedQuery<RhFerias> t = em.createQuery("SELECT f FROM RhFerias f "
                 + "WHERE f.dataInicio BETWEEN :dataInf AND :dataSup "
                 + "ORDER BY f.fkIdFuncionario.fkIdPessoa.nome, "
                 + "f.fkIdFuncionario.fkIdPessoa.nomeDoMeio, f.fkIdFuncionario.fkIdPessoa.sobreNome, f.dataInicio", RhFerias.class)
                .setParameter("dataInf", dataInf).setParameter("dataSup", dataSup);

        return t.getResultList();
    }

    /**
     * Procura por todas as férias independente do estado, num determinado
     * intervalo de datas e determinada area de trabalho
     *
     * @param dataInf
     * @param dataSup
     * @param seccaoTrabalho
     * @return
     */
    public List<RhFerias> findFeriasSeccaoTrabalho (Date dataInf, Date dataSup, RhSeccaoTrabalho seccaoTrabalho)
    {
        System.err.println("///////////////");
        String query = "SELECT f FROM RhFerias f "
                 + "WHERE f.dataInicio BETWEEN :dataInf AND :dataSup ";
        
        if (seccaoTrabalho.getPkIdSeccaoTrabalho() != null)
            query += "AND f.fkIdFuncionario.fkIdSeccaoTrabalho.pkIdSeccaoTrabalho = :seccao ";
        
        else if (seccaoTrabalho.getFkIdDepartamento().getPkIdDepartamento() != null)
            query += "AND f.fkIdFuncionario.fkIdSeccaoTrabalho.fkIdDepartamento.pkIdDepartamento = :departamento ";
        
        query += "ORDER BY f.fkIdFuncionario.fkIdPessoa.nome, "
                 + "f.fkIdFuncionario.fkIdPessoa.nomeDoMeio, f.fkIdFuncionario.fkIdPessoa.sobreNome, f.dataInicio";
                 
        Query q = em.createQuery(query,RhFerias.class).setParameter("dataInf", dataInf).setParameter("dataSup", dataSup);
        
        if (seccaoTrabalho.getPkIdSeccaoTrabalho() != null)
            q.setParameter("seccao", seccaoTrabalho.getPkIdSeccaoTrabalho());
        
        else if (seccaoTrabalho.getFkIdDepartamento().getPkIdDepartamento() != null)
            q.setParameter("departamento", seccaoTrabalho.getFkIdDepartamento().getPkIdDepartamento());

        return q.getResultList();
    }

    /**
     *
     * Procura todas as férias no estado marcado com data de início antes 
     * determinada data 
     *
     * @param data
     * @return
     */
    public List<RhFerias> procurarMarcadasComInicioAntesDe (Date data)
    {
        TypedQuery<RhFerias> query;
        query = em.createQuery("SELECT f FROM RhFerias f WHERE f.dataInicio <= :data "
                               + "AND f.fkIdEstadoFerias.descricao = 'Marcada'", RhFerias.class).setParameter("data", data);

        return query.getResultList();
    }
    
    /**
     *
     * Procura todas as férias no estado activo com data de término antes de uma
     * determinada data
     *
     * @param data
     * @return
     */
    public List<RhFerias> procurarActivasComTerminoAntesDe (Date data)
    {
        TypedQuery<RhFerias> query;
        query = em.createQuery("SELECT f FROM RhFerias f WHERE f.dataTermino <= :data "
                               + "AND f.fkIdEstadoFerias.descricao = 'Activo' ", RhFerias.class).setParameter("data", data);

        return query.getResultList();
    }

}
