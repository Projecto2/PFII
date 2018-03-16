/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagBolsaSangue;
import entidade.DiagCandidatoDador;
import entidade.DiagGrupoSanguineo;
import entidade.GrlPessoa;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagBolsaSangueFacade extends AbstractFacade<DiagBolsaSangue>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagBolsaSangueFacade()
    {
        super(DiagBolsaSangue.class);
    }

    //Pegar as bolsas de sangue pelo codigo do dador
    public List<DiagBolsaSangue> findBolsasDeSangueDador(GrlPessoa pessoaAux)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagBolsaSangue d WHERE d.fkIdCandidatoDador.fkIdPessoa.pkIdPessoa = :idPessoa", DiagBolsaSangue.class).setParameter("idPessoa", pessoaAux.getPkIdPessoa());

        return typedQuery.getResultList();
    }

    //Pegar as bolsas de sangue cuja data de expiração ainda não chegou
    public List<DiagBolsaSangue> findBolsasDeSangueDadorValidas(GrlPessoa pessoaAux)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagBolsaSangue d WHERE d.fkIdCandidatoDador.fkIdPessoa.pkIdPessoa = :idPessoa AND d.dataExpiracao > CURRENT_DATE AND d.quantidadeColhida > 0", DiagBolsaSangue.class).setParameter("idPessoa", pessoaAux.getPkIdPessoa());

        return typedQuery.getResultList();
    }

    public Date findDataUltimaBolsa(DiagCandidatoDador candidatoDador)
    {
        TypedQuery typedQuery = em.createQuery("SELECT MAX(d.dataCadastro) FROM DiagBolsaSangue d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador = :idCandidatoDador", Date.class).setParameter("idCandidatoDador", candidatoDador.getPkIdCandidatoDador());

        return (Date) typedQuery.getSingleResult();
    }

    public List<DiagBolsaSangue> findBolsasSangue(DiagBolsaSangue bolsaSangueAux, DiagGrupoSanguineo grupoSanguineoAux, Date dataInicioCadastro, Date dataFimCadastro, Date dataInicioExpiracao, Date dataFimExpiracao, int numeroRegistos)
    {
        String query = constroiQuery(bolsaSangueAux, grupoSanguineoAux, dataInicioCadastro, dataFimCadastro, dataInicioExpiracao, dataFimExpiracao);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (bolsaSangueAux.getCodigoColheita() != null && !bolsaSangueAux.getCodigoColheita().trim().isEmpty())
        {
            qry.setParameter("codigoColheita", bolsaSangueAux.getCodigoColheita());
        }

        if (bolsaSangueAux.getQuantidadeColhida() != null)
        {
            qry.setParameter("quantidade", bolsaSangueAux.getQuantidadeColhida());
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNome() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNome() + "%");
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNomeDoMeio() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getSobreNome() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getSobreNome() + "%");
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        if (grupoSanguineoAux.getPkIdGrupoSanguineo() != null)
        {
            qry.setParameter("pkIdGrupoSanguineo", grupoSanguineoAux.getPkIdGrupoSanguineo());
        }

        if (dataInicioCadastro != null && dataFimCadastro != null)
        {
            qry.setParameter("dataInicioCadastro", dataInicioCadastro);
            qry.setParameter("dataFimCadastro", dataFimCadastro);
        }

        if (dataInicioCadastro != null && dataFimCadastro == null)
        {
            qry.setParameter("dataInicioCadastro", dataInicioCadastro);
        }

        if (dataInicioCadastro == null && dataFimCadastro != null)
        {
            qry.setParameter("dataFimCadastro", dataFimCadastro);
        }

        if (dataInicioExpiracao != null && dataFimExpiracao != null)
        {
            qry.setParameter("dataInicioExpiracao", dataInicioExpiracao);
            qry.setParameter("dataFimExpiracao", dataFimExpiracao);
        }

        if (dataInicioExpiracao != null && dataFimExpiracao == null)
        {
            qry.setParameter("dataInicioExpiracao", dataInicioExpiracao);
        }

        if (dataInicioExpiracao == null && dataFimExpiracao != null)
        {
            qry.setParameter("dataFimExpiracao", dataFimExpiracao);
        }

        qry.setMaxResults(numeroRegistos);
        List<DiagBolsaSangue> bolsas = qry.getResultList();

        return bolsas;
    }

    public String constroiQuery(DiagBolsaSangue bolsaSangueAux, DiagGrupoSanguineo grupoSanguineoAux, Date dataInicioCadastro, Date dataFimCadastro, Date dataInicioExpiracao, Date dataFimExpiracao)
    {
        String query = "SELECT d FROM DiagBolsaSangue d WHERE :pesquisar = :pesquisar";

        if (bolsaSangueAux.getCodigoColheita() != null && !bolsaSangueAux.getCodigoColheita().trim().isEmpty())
        {
            query += " AND d.codigoColheita LIKE :codigoColheita";
        }

        if (bolsaSangueAux.getQuantidadeColhida() != null)
        {
            query += " AND d.quantidadeColhida = :quantidade";
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNome() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdCandidatoDador.fkIdPessoa.nome LIKE :nome";
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNomeDoMeio() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND d.fkIdCandidatoDador.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getSobreNome() != null && !bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdCandidatoDador.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND d.fkIdCandidatoDador.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (bolsaSangueAux.getFkIdCandidatoDador().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND d.fkIdCandidatoDador.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        if (dataInicioCadastro != null && dataFimCadastro != null)
        {
            query += " AND d.dataCadastro >= :dataInicioCadastro and d.dataCadastro <= :dataFimCadastro";
        }

        if (dataInicioCadastro != null && dataFimCadastro == null)
        {
            query += " AND d.dataCadastro >= :dataInicioCadastro";
        }

        if (dataInicioCadastro == null && dataFimCadastro != null)
        {
            query += " AND d.dataCadastro <= :dataFimCadastro";
        }

        if (dataInicioExpiracao != null && dataFimExpiracao != null)
        {
            query += " AND d.dataExpiracao >= :dataInicioExpiracao and d.data <= :dataFimExpiracao";
        }

        if (dataInicioExpiracao != null && dataFimExpiracao == null)
        {
            query += " AND d.dataExpiracao >= :dataInicioExpiracao";
        }

        if (dataInicioExpiracao == null && dataFimExpiracao != null)
        {
            query += " AND d.dataExpiracao <= :dataFimExpiracao";
        }

        if (grupoSanguineoAux.getPkIdGrupoSanguineo() != null)
        {
            query += " AND :pkIdGrupoSanguineo IN (SELECT dd.conclusao.pkIdGrupoSanguineo FROM DiagTipagemDador dd WHERE "
                    + "dd.fkIdCandidatoDador.pkIdCandidatoDador = d.fkIdCandidatoDador.pkIdCandidatoDador)";
        }

        query += " AND d.quantidadeColhida > 0.0 ORDER BY d.codigoColheita";

        return query;
    }
}
