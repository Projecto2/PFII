/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsAgendamento;
import entidade.AdmsAgendamentoMedico;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsAgendamentoMedicoFacade extends AbstractFacade<AdmsAgendamentoMedico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsAgendamentoMedicoFacade()
    {
        super(AdmsAgendamentoMedico.class);
    }
    
    public AdmsAgendamentoMedico getAgendamentoMedico(AdmsAgendamento agendamento)
    {
        Query query = em.createQuery("SELECT agendamentoMedico from AdmsAgendamentoMedico agendamentoMedico WHERE agendamentoMedico.fkIdAgendamento.pkIdAgendamento = :idAgendamento ORDER BY agendamentoMedico.pkIdAgendamentoMedico DESC");
        
        query.setParameter("idAgendamento", agendamento.getPkIdAgendamento());
        
        List<AdmsAgendamentoMedico> agendamentoMedico = query.getResultList();
        if(agendamentoMedico.isEmpty()) return null;
        return agendamentoMedico.get(0);
    }
    
    
    public List<AdmsAgendamentoMedico> findAgendamentosMedicosQueChocamComEstasDatas(Integer idMedico, Date dataHoraInicio, Date dataHoraFim)
    {
        System.out.println("ola ");
        String query = constroiQueryFindAgendamentosMedicosQueChocamComEstasDatas(idMedico, dataHoraInicio, dataHoraFim);
        
        Query qry = em.createQuery(query);
        
        qry.setParameter("idMedico", idMedico);
        
        qry.setParameter("dataHoraInicio", dataHoraInicio);
        
//        if(dataHoraFim != null)
        qry.setParameter("dataHoraFim", dataHoraFim);
        
        List<AdmsAgendamentoMedico> agendamentosMedicos = qry.getResultList();
        
        if(agendamentosMedicos.isEmpty()) return null;
        return agendamentosMedicos;
    }
    
    
    public String constroiQueryFindAgendamentosMedicosQueChocamComEstasDatas(Integer idMedico, Date dataHoraInicio, Date dataHoraFim)
    {   
        String query = "SELECT agendamentoMedico FROM AdmsAgendamentoMedico as agendamentoMedico "
            + "WHERE agendamentoMedico.fkIdMedico.pkIdFuncionario = :idMedico AND (agendamentoMedico.fkIdAgendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento NOT LIKE 'Cancelado' AND agendamentoMedico.fkIdAgendamento.fkIdEstadoAgendamento.descricaoEstadoAgendamento NOT LIKE 'Não Apareceu') "
            + "AND (agendamentoMedico.fkIdAgendamento.dataHoraFim IS NOT NULL) "
            + "AND ( (agendamentoMedico.fkIdAgendamento.dataHoraInicio >= :dataHoraInicio AND agendamentoMedico.fkIdAgendamento.dataHoraInicio <= :dataHoraFim) OR (agendamentoMedico.fkIdAgendamento.dataHoraFim >= :dataHoraInicio AND agendamentoMedico.fkIdAgendamento.dataHoraFim <= :dataHoraFim) )";

//        
//        if (dataHoraFim != null)
           query += "AND ( (agendamentoMedico.fkIdAgendamento.dataHoraInicio >= :dataHoraInicio AND agendamentoMedico.fkIdAgendamento.dataHoraInicio <= :dataHoraFim) OR (agendamentoMedico.fkIdAgendamento.dataHoraFim >= :dataHoraInicio AND agendamentoMedico.fkIdAgendamento.dataHoraFim <= :dataHoraFim) )";
        
//        if (servicoSolicitado.getFkIdServico().getPkIdServico() != null)
//        {
//            fromQuery += " INNER JOIN adms_servico ON adms_servico_solicitado.fk_id_servico = adms_servico.pk_id_servico"; 
//            whereQuery += " AND adms_servico.pk_id_servico = ?3";
//        }
           
           System.out.println(""+query);

        return query;
    }

    
}
