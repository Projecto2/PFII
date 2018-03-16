/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import Utils.DataUtils;
import entidade.AmbDiagnostico;
import entidade.AmbDiagnosticoHasDoenca;
import entidade.DiagExameRealizado;
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
public class AmbDiagnosticoFacade extends AbstractFacade<AmbDiagnostico>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbDiagnosticoFacade()
    {
        super(AmbDiagnostico.class);
    }

//    public List<DiagExameRealizado> findConsultasExame()
//    {
//        Date dataHoje = new Date();
//        Date dataOntem = DataUtils.addDias(dataHoje, -1);
//        
//        TypedQuery typedQuery = em.createQuery("SELECT der FROM DiagExameRealizado der WHERE der.data >= :dataOntem AND der.data <= :dataHoje AND "
//                + "der.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso FROM AmbConsulta ac) AND "
//                + "der.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso NOT IN (SELECT ad.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso FROM AmbDiagnostico ad) "
//                + "ORDER BY der.data ASC", DiagExameRealizado.class);
//
//        typedQuery.setParameter("dataHoje", dataHoje);
//        typedQuery.setParameter("dataOntem", dataOntem);
//
//        return typedQuery.getResultList();
//    }
    
    public List<DiagExameRealizado> findConsultasExame()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);
        
        TypedQuery typedQuery = em.createQuery("SELECT der FROM DiagExameRealizado der WHERE der.data >= :dataOntem AND der.data <= :dataHoje AND "
                + "der.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso FROM AmbConsulta ac) AND "
                + "der.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT ad.fkIdExameRealizado.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbDiagnostico ad) "
                + "ORDER BY der.data ASC", DiagExameRealizado.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }
    
    public List<AmbDiagnosticoHasDoenca> findPacientesDiagnosticados()
    {
        return em.createQuery("SELECT a FROM AmbDiagnosticoHasDoenca a ORDER BY a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome"
                            + ", a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio"
                            + ", a.fkIdDiagnostico.fkIdDiagnosticoHipotese.fkIdConsulta.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome").getResultList();
    }        
    
//    public List<DiagExameRealizado> findConsultasExame()
//    {
//        
//        TypedQuery typedQuery = em.createQuery("SELECT der FROM DiagExameRealizado der WHERE "
//                + "der.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso IN (SELECT ac.fkIdConsultorioAtendimento.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.pkIdSubprocesso FROM AmbConsulta ac) "
//                + "ORDER BY der.data ASC", DiagExameRealizado.class);
//
//        return typedQuery.getResultList();
//    }    
    
    public String constroiQueryDiagnosticos(AmbDiagnosticoHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = "SELECT a FROM AmbDiagnosticoHasDoenca a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.fkIdDiagnostico.dataHoraDiagnostico >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.fkIdDiagnostico.dataHoraDiagnostico <= :dataFim";
        }

        query += " ORDER BY a.fkIdDiagnostico.dataHoraDiagnostico DESC";

        return query;
    }
    public List<AmbDiagnosticoHasDoenca> findDiagnosticos(AmbDiagnosticoHasDoenca item, Date dataInicio, Date dataFim)
    {
        String query = constroiQueryDiagnosticos(item, dataInicio, dataFim);

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

        List<AmbDiagnosticoHasDoenca> resultado = t.getResultList();

        return resultado;
    }
}
