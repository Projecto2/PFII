/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import Utils.DataUtils;
import entidade.AmbConsulta;
import entidade.AmbConsultorioAtendimento;
import entidade.AmbDiagnosticoHipoteseHasDoenca;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbConsultaFacade extends AbstractFacade<AmbConsulta>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbConsultaFacade()
    {
        super(AmbConsulta.class);
    }
    
    public String constroiQueryConsulta(AmbDiagnosticoHipoteseHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbDiagnosticoHipoteseHasDoenca a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico <= :dataFim";
        }

        query += " AND a.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdTipoSolicitacaoServico.descricaoTipoSolicitacaoServico LIKE 'Primeira Vez' ORDER BY a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico DESC";

        return query;
    }    
    
    public List<AmbDiagnosticoHipoteseHasDoenca> findConsulta(AmbDiagnosticoHipoteseHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = constroiQueryConsulta(item, dataInicio, dataFim);

        Query t = em.createQuery(query);

        t.setParameter("pesquisar", true);

        if (dataInicio != null)
        {
            t.setParameter("dataInicio", dataInicio);
        }

        if (dataFim != null)
        {
            t.setParameter("dataFim", dataFim);
        }

        List<AmbDiagnosticoHipoteseHasDoenca> resultado = t.getResultList();

        return resultado;
    }    
 
    public String constroiQueryReconsulta(AmbDiagnosticoHipoteseHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbDiagnosticoHipoteseHasDoenca a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico <= :dataFim";
        }

        query += " AND a.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdTipoSolicitacaoServico.descricaoTipoSolicitacaoServico LIKE 'Retorno' ORDER BY a.fkIdDiagnosticoHipotese.dataHoraHipoteseDiagnostico DESC";

        return query;
    }    
    
    public List<AmbDiagnosticoHipoteseHasDoenca> findReconsulta(AmbDiagnosticoHipoteseHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = constroiQueryReconsulta(item, dataInicio, dataFim);

        Query t = em.createQuery(query);

        t.setParameter("pesquisar", true);

        if (dataInicio != null)
        {
            t.setParameter("dataInicio", dataInicio);
        }

        if (dataFim != null)
        {
            t.setParameter("dataFim", dataFim);
        }

        List<AmbDiagnosticoHipoteseHasDoenca> resultado = t.getResultList();

        return resultado;
    }        
    
    public List<AmbDiagnosticoHipoteseHasDoenca> findPacientesConsultados()
    {
        return em.createQuery("SELECT a FROM AmbDiagnosticoHipoteseHasDoenca a ORDER BY a.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome"
                            + ", a.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio"
                            + ", a.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome").getResultList();
    }    
    
    public List<AmbConsultorioAtendimento> findPacientesEncaminhados()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        return em.createQuery("SELECT a FROM AmbConsultorioAtendimento a WHERE a.dataHoraCadastro >= :dataOntem AND a.dataHoraCadastro <= :dataHoje AND "
                + "a.fkIdTriagem.fkIdEstado.descricao LIKE 'Em espera' AND "
                + "a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbConsulta ac) "
                + "ORDER BY a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AmbConsultorioAtendimento.class).setParameter("dataHoje", dataHoje).setParameter("dataOntem", dataOntem).getResultList();
    }    
    
    public List<AmbConsultorioAtendimento> findPacientesEncaminhadosConsulta()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AmbConsultorioAtendimento a WHERE a.dataHoraCadastro >= :dataOntem AND a.dataHoraCadastro <= :dataHoje AND "
                + "a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdTipoSolicitacao.descricaoTipoSolicitacaoServico LIKE 'Primeira Vez' AND "
                + "a.fkIdTriagem.fkIdEstado.descricao LIKE 'Em espera' AND "
                + "a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbConsulta ac) "
                + "ORDER BY a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AmbConsultorioAtendimento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }

    public List<AmbConsultorioAtendimento> findPacientesEncaminhadosReconsulta()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AmbConsultorioAtendimento a WHERE a.dataHoraCadastro >= :dataOntem AND a.dataHoraCadastro <= :dataHoje AND "
                + "a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdTipoSolicitacao.descricaoTipoSolicitacaoServico LIKE 'Retorno' AND "
                + "a.fkIdTriagem.fkIdEstado.descricao LIKE 'Em espera' AND "
                + "a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbConsulta ac) "
                + "ORDER BY a.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AmbConsultorioAtendimento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }
}
