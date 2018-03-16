/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsAgendamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsAgendamentoFacade extends AbstractFacade<AdmsAgendamento>
{
    @EJB
    private AdmsEstadoAgendamentoFacade admsEstadoAgendamentoFacade;
    
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;
    
    

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsAgendamentoFacade()
    {
        super(AdmsAgendamento.class);
    }
    
    public AdmsAgendamento findAgendamentoByServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        Query query = em.createQuery("SELECT agendamento from AdmsAgendamento agendamento WHERE agendamento.fkIdServicoSolicitado.pkIdServicoSolicitado = :idServicoSolicitado AND agendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento <> 'Cancelado' ORDER BY agendamento.pkIdAgendamento DESC");
        
        query.setParameter("idServicoSolicitado", servicoSolicitado.getPkIdServicoSolicitado());
        
        query.setMaxResults(1);
        
        List<AdmsAgendamento> agendamento = query.getResultList();
        if(agendamento.isEmpty()) return null;
        return agendamento.get(0);
    }
    
    
    public List<AdmsAgendamento> findAgendamentoDoMedicoByDiaDaSemana(RhFuncionario medico, int diaDaSemana, Date data)
    {
        Query query = em.createNativeQuery("SELECT pk_id_agendamento, fk_id_servico_solicitado, fk_id_estado_agendamento, data_hora_inicio, data_hora_fim, data_hora_check_in\n" +
        " FROM adms_agendamento "
        + "INNER JOIN adms_estado_agendamento ON adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento "
        + "INNER JOIN adms_agendamento_medico ON adms_agendamento.pk_id_agendamento = adms_agendamento_medico.fk_id_agendamento "
        + "WHERE EXTRACT(DOW from data_hora_inicio) = ?1 AND (adms_estado_agendamento.descricao_estado_agendamento LIKE 'Agendado' OR adms_estado_agendamento.descricao_estado_agendamento LIKE 'Chegou') "
        + "AND adms_agendamento_medico.fk_id_medico = ?2 AND adms_agendamento.data_hora_inicio >= ?3", AdmsAgendamento.class);
        
        query.setParameter(1, diaDaSemana);
        
        query.setParameter(2, medico.getPkIdFuncionario());
        
        query.setParameter(3, data);
        
//        query.setMaxResults(1);
        
        List<AdmsAgendamento> agendamentos = query.getResultList();
        if(agendamentos.isEmpty()) return null;
        return agendamentos;
    }
    
    
    
    public AdmsAgendamento findUltimoAgendamentoServico(AdmsServicoSolicitado servicoSolicitado)
    {
        Query query = em.createQuery("SELECT agendamento from AdmsAgendamento agendamento WHERE agendamento.fkIdServicoSolicitado.pkIdServicoSolicitado = :idServicoSolicitado ORDER BY agendamento.pkIdAgendamento DESC");
        
        query.setParameter("idServicoSolicitado", servicoSolicitado.getPkIdServicoSolicitado());
        
        query.setMaxResults(1);
        
        List<AdmsAgendamento> agendamento = query.getResultList();
        if(agendamento.isEmpty()) return null;
        return agendamento.get(0);
    }
    
    public List<AdmsAgendamento> findAgendamentosComEsseServico(int idServico, Date data)
    {
//        System.out.println("servico "+idServico+" "+data);
        
        Query query = em.createNativeQuery("SELECT pk_id_agendamento, fk_id_servico_solicitado, fk_id_estado_agendamento, data_hora_inicio, data_hora_fim, data_hora_check_in "
            + "FROM adms_agendamento INNER JOIN adms_servico_solicitado on adms_agendamento.fk_id_servico_solicitado = adms_servico_solicitado.pk_id_servico_solicitado"
            + " INNER JOIN adms_estado_agendamento on adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento"
            + " WHERE adms_servico_solicitado.fk_id_servico = ?1 AND adms_estado_agendamento.descricao_estado_agendamento <> 'Cancelado' AND (DATE(adms_agendamento.data_hora_inicio) >= ?2) ORDER BY adms_agendamento.pk_id_agendamento DESC", AdmsAgendamento.class);
        
        query.setParameter(1, idServico);
        query.setParameter(2, data);
        
        List<AdmsAgendamento> agendamentos = query.getResultList();
        
        if(agendamentos.isEmpty()) return null;
        return agendamentos;
    }
    
    
    public void mudarEstadoParaEfetuado(AdmsServicoSolicitado servicoSolicitado)
    {
        AdmsAgendamento ultimoAgendamento = findUltimoAgendamentoServico(servicoSolicitado);
        if(ultimoAgendamento != null)
        {
            ultimoAgendamento.setFkIdEstadoAgendamento(admsEstadoAgendamentoFacade.getEstadoAgendamentoEfetuado());
            edit(ultimoAgendamento);
        }
    }
    
    
    public int findNumeroAgendamentosMedicoServicoSolicitado(int idServico, Integer pkIdMedico, Date dataInicio, Date dataFim)
    {
        System.out.println("id do medico associado "+pkIdMedico);
        String consultasMarcadas;
        if(pkIdMedico != null)
        {
            consultasMarcadas = "AND agendamento.pkIdAgendamento IN(SELECT agendamentoMedico.fkIdAgendamento.pkIdAgendamento "
            + "FROM AdmsAgendamentoMedico agendamentoMedico WHERE agendamentoMedico.fkIdMedico.pkIdFuncionario = :idMedico)";
        }
        else
        {
            consultasMarcadas = "AND agendamento.pkIdAgendamento NOT IN(SELECT agendamentoMedico.fkIdAgendamento.pkIdAgendamento "
            + "FROM AdmsAgendamentoMedico agendamentoMedico)";
        }
        
        Query query = em.createQuery("SELECT COUNT(agendamento.pkIdAgendamento) from AdmsAgendamento agendamento WHERE "
            + "agendamento.fkIdServicoSolicitado.fkIdServico.pkIdServico = :idServico "
            + "AND (agendamento.dataHoraInicio >= :dataInicio AND agendamento.dataHoraInicio <= :dataFim) "
            + "AND agendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento <> 'Cancelado' "
            + "AND agendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento <> 'Não Apareceu' "
            + ""+consultasMarcadas);
        
        query.setParameter("idServico", idServico);
        
        if(pkIdMedico != null)
            query.setParameter("idMedico", pkIdMedico);
        
        query.setParameter("dataInicio", dataInicio);
        
        query.setParameter("dataFim", dataFim);
        
        query.setMaxResults(1);
        
        List<Integer> numerAgendamentos = query.getResultList();
        System.out.println("agendamentos "+numerAgendamentos.get(0));
        return Integer.parseInt(""+numerAgendamentos.get(0)) ;
    }
    
    
    
    public List<AdmsAgendamento> findAgendamentosQueChocamComEstasDatas(AdmsPaciente paciente, Date dataHoraInicio, Date dataHoraFim)
    {
        System.out.println("ola ");
        String query = constroiQueryFindAgendamentosQueChocamComEstasDatas(paciente, dataHoraInicio, dataHoraFim);
        
        Query qry = em.createQuery(query);
        
        qry.setParameter("idPaciente", paciente.getPkIdPaciente());
        
        qry.setParameter("dataHoraInicio", dataHoraInicio);
        
//        if(dataHoraFim != null)
        qry.setParameter("dataHoraFim", dataHoraFim);
        
        List<AdmsAgendamento> agendamentos = qry.getResultList();
        
        if(agendamentos.isEmpty()) return null;
        return agendamentos;
    }
    
    
    public String constroiQueryFindAgendamentosQueChocamComEstasDatas(AdmsPaciente paciente, Date dataHoraInicio, Date dataHoraFim)
    {   
        String query = "SELECT agendamento FROM AdmsAgendamento as agendamento "
            + "WHERE agendamento.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.pkIdPaciente = :idPaciente "
            + "AND (agendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento NOT LIKE 'Cancelado' AND agendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento NOT LIKE 'Não Apareceu') "
            + "AND (agendamento.dataHoraFim IS NOT NULL) "
            + "AND ( (agendamento.dataHoraInicio >= :dataHoraInicio AND agendamento.dataHoraInicio <= :dataHoraFim) OR (agendamento.dataHoraFim >= :dataHoraInicio AND agendamento.dataHoraFim <= :dataHoraFim) )";

//        
//        if (dataHoraFim != null)
           query += "AND ( (agendamento.dataHoraInicio >= :dataHoraInicio AND agendamento.dataHoraInicio <= :dataHoraFim) OR (agendamento.dataHoraFim >= :dataHoraInicio AND agendamento.dataHoraFim <= :dataHoraFim) )";
        
//        if (servicoSolicitado.getFkIdServico().getPkIdServico() != null)
//        {
//            fromQuery += " INNER JOIN adms_servico ON adms_servico_solicitado.fk_id_servico = adms_servico.pk_id_servico"; 
//            whereQuery += " AND adms_servico.pk_id_servico = ?3";
//        }
           
           System.out.println(""+query);

        return query;
    }
    
}
