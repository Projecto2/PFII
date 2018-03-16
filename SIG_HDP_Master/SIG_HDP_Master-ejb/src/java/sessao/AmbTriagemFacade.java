/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import Utils.DataUtils;
import entidade.AdmsAgendamento;
import entidade.AmbSinal;
import entidade.AmbTriagem;
import entidade.AmbTriagemHasSinal;
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
public class AmbTriagemFacade extends AbstractFacade<AmbTriagem>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbTriagemFacade()
    {
        super(AmbTriagem.class);
    }
    
    public AmbSinal findByDescricao(String nome)
    {
        TypedQuery<AmbSinal> q = em.createQuery("SELECT a FROM AmbSinal a WHERE a.descricao = :nome",
                AmbSinal.class).setParameter("nome", nome);
        List<AmbSinal> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
    public List<AmbTriagem> findAllOrderByDataDecrescente(String descricao)
    {
        return em.createQuery("SELECT a FROM AmbTriagem a WHERE a.fkIdEstado.descricao = :descricao ORDER BY a.dataHoraTriagem DESC",
                AmbTriagem.class).setParameter("descricao", descricao).getResultList();
    }

//    public String constroiQuery(AmbTriagem item, Date dataInicio, Date dataFim)
//    {
//        String query = "Select a FROM AmbTriagem a WHERE :pesquisar = :pesquisar";
//
//        if (dataInicio != null)
//        {
//            query += " AND a.dataHoraTriagem >= :dataInicio";
//        }
//
//        if (dataFim != null)
//        {
//            query += " AND a.dataHoraTriagem <= :dataFim";
//        }
//
//        query += " ORDER BY a.dataHoraTriagem DESC";
//
//        return query;
//    }

//    public List<AmbTriagem> findTriagem(AmbTriagem item, Date dataInicio, Date dataFim)
//    {
//        String query = constroiQuery(item, dataInicio, dataFim);
//
//        Query t = em.createQuery(query);
//
//        t.setParameter("pesquisar", true);
//
//        if (dataInicio != null)
//        {
//            t.setParameter("dataInicio", dataInicio);
//        }
//
//        if (dataFim != null)
//        {
//            t.setParameter("dataFim", dataFim);
//        }
//
//        List<AmbTriagem> resultado = t.getResultList();
//
//        return resultado;
//    }

    public String constroiQuery(AmbTriagemHasSinal item, Date dataInicio, Date dataFim)
    {
        String query = "Select a FROM AmbTriagemHasSinal a WHERE :pesquisar = :pesquisar";

        if (dataInicio != null)
        {
            query += " AND a.fkIdTriagem.dataHoraTriagem >= :dataInicio";
        }

        if (dataFim != null)
        {
            query += " AND a.fkIdTriagem.dataHoraTriagem <= :dataFim";
        }

        query += " ORDER BY a.fkIdTriagem.dataHoraTriagem DESC";

        return query;
    }

    public List<AmbTriagemHasSinal> findTriagem(AmbTriagemHasSinal item, Date dataInicio, Date dataFim)
    {
        String query = constroiQuery(item, dataInicio, dataFim);

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

        List<AmbTriagemHasSinal> resultado = t.getResultList();

        return resultado;
    }    
    
    public List<AdmsAgendamento> findSolicitacoesAmb()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.dataHoraCheckIn >= :dataOntem AND a.dataHoraCheckIn <= :dataHoje AND a.fkIdEstadoAgendamento.descricaoEstadoAgendamento = 'Chegou' AND "
                + "a.fkIdServicoSolicitado.fkIdServico.fkIdTipoServico.descricaoTipoServico LIKE 'Consulta' AND "
                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT at.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbTriagem at) "
                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AdmsAgendamento.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }
   
//    public List<AdmsAgendamento> findSolicitacoesAmb()
//    {
//        Date dataHoje = new Date();
//        Date dataOntem = DataUtils.addDias(dataHoje, -1);
//
//        TypedQuery typedQuery = em.createQuery("SELECT a FROM AdmsAgendamento a WHERE a.fkIdEstadoAgendamento.descricaoEstadoAgendamento = 'Chegou' AND "
//                + "a.fkIdServicoSolicitado.fkIdServico.fkIdTipoServico.descricaoTipoServico LIKE 'Consulta' AND "
//                + "a.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT at.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbTriagem at) "
//                + "ORDER BY a.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AdmsAgendamento.class);
//
////        typedQuery.setParameter("dataHoje", dataHoje);
////        typedQuery.setParameter("dataOntem", dataOntem);
//
//        return typedQuery.getResultList();
//    }    
    
    public List<AmbTriagem> listarDadosTriagens()
    {
        Date dataHoje = new Date();
        Date dataOntem = DataUtils.addDias(dataHoje, -1);

        TypedQuery typedQuery = em.createQuery("SELECT a FROM AmbTriagem a WHERE a.dataHoraTriagem >= :dataOntem AND a.dataHoraTriagem <= :dataHoje AND "
                + "a.fkIdEstado.descricao LIKE 'Em espera' AND "
                + "a.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado NOT IN (SELECT aca.fkIdTriagem.fkIdAgendamento.fkIdServicoSolicitado.pkIdServicoSolicitado FROM AmbConsultorioAtendimento aca) "
                + "ORDER BY a.fkIdAgendamento.fkIdServicoSolicitado.fkIdSolicitacao.data ASC", AmbTriagem.class);

        typedQuery.setParameter("dataHoje", dataHoje);
        typedQuery.setParameter("dataOntem", dataOntem);

        return typedQuery.getResultList();
    }    
}
