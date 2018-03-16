/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsEstadoAgendamento;
import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Garcia Paulo
 * @author Duneth Silva
 */
@Stateless
public class AdmsServicoSolicitadoFacade extends AbstractFacade<AdmsServicoSolicitado> implements Serializable
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsServicoSolicitadoFacade()
    {
        super(AdmsServicoSolicitado.class);
    }
    
    public List<AdmsServicoSolicitado> listaPacienteTriagem()
    {
        TypedQuery<AdmsServicoSolicitado> query = em.createQuery("select servico from AdmsServicoSolicitado servico where servico.pkIdServicoSolicitado NOT IN(select triagem.fkIdServicoSolicitado.pkIdServicoSolicitado from AmbTriagem triagem) and servico.fkIdEstadoPagamento.descricaoEstadoPagamento='Pago' and servico.fkIdServico.fkIdTipoServico.descricaoTipoServico='Consulta' and (servico.fkIdServico.fkIdEspecialidade.descricao='Pediatria' OR servico.fkIdServico.fkIdEspecialidade.descricao='Clinica Geral')",AdmsServicoSolicitado.class);
    
        return query.getResultList();
    }
    
    public List<AdmsServicoSolicitado> findServicosSolicitadosNaoPagos(long codSolicitacao)
    {
        Query query = em.createQuery("SELECT servico from AdmsServicoSolicitado servico WHERE servico.fkIdEstadoPagamento.descricaoEstadoPagamento = 'Pendente' AND servico.fkIdSolicitacao.pkIdSolicitacao = :codSolicitacao");
    
        query.setParameter("codSolicitacao", codSolicitacao);
        return query.getResultList();
    }
    
    public List<AdmsServicoSolicitado> findServicosSolicitadosBySolicitacao(long codSolicitacao)
    {
        Query query = em.createQuery("SELECT servico from AdmsServicoSolicitado servico WHERE servico.fkIdSolicitacao.pkIdSolicitacao = :codSolicitacao");
    
        query.setParameter("codSolicitacao", codSolicitacao);
        return query.getResultList();
    }
    
    
    public List<AdmsServicoSolicitado> findServicoSolicitado(AdmsServicoSolicitado servicoSolicitado, 
        AdmsEstadoAgendamento estadoAgendamento, RhFuncionario funcionario, Date dataInicial, Date dataFinal, Date dataAgendadaInicial, Date dataAgendadaFinal, 
        AdmsPaciente paciente, Integer quantidadeRegistos)
    {
        String query = constroiQuery(servicoSolicitado, estadoAgendamento, funcionario, dataInicial, dataFinal, dataAgendadaInicial, dataAgendadaFinal, paciente);

        Query qry = em.createNativeQuery(query, AdmsServicoSolicitado.class);
        
        if (dataInicial != null)
            qry.setParameter(1, dataInicial);
        
        if (dataFinal != null)
            qry.setParameter(2, dataFinal);
        
        if (dataAgendadaInicial != null || dataAgendadaFinal != null)
        {
            if(estadoAgendamento.getPkIdEstadoAgendamento() == null || estadoAgendamento.getPkIdEstadoAgendamento() != -1)
            {
                if(dataAgendadaInicial != null)
                    qry.setParameter(7, dataAgendadaInicial);
                if(dataAgendadaFinal != null)
                    qry.setParameter(11, dataAgendadaFinal);
                
            }
//            else {
//                if(estadoAgendamento.getPkIdEstadoAgendamento() != -1)
//                    qry.setParameter(7, dataAgendada);
//            }
        }
        
        if (servicoSolicitado.getFkIdServico().getPkIdServico() != null)
            qry.setParameter(3, servicoSolicitado.getFkIdServico().getPkIdServico());
//        
        if (servicoSolicitado.getFkIdTipoSolicitacao().getPkIdTipoSolicitacao() != null)
            qry.setParameter(4, servicoSolicitado.getFkIdTipoSolicitacao().getPkIdTipoSolicitacao());
//        
        if (servicoSolicitado.getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
            qry.setParameter(5, servicoSolicitado.getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado());
//        
        if (servicoSolicitado.getFkIdEstadoPagamento().getPkIdEstadoPagamento() != null)
            qry.setParameter(6, servicoSolicitado.getFkIdEstadoPagamento().getPkIdEstadoPagamento());
        
        if(estadoAgendamento.getPkIdEstadoAgendamento() != null)
        {
            if(estadoAgendamento.getPkIdEstadoAgendamento() != -1)
            {
                qry.setParameter(8, estadoAgendamento.getPkIdEstadoAgendamento());
            }
        }
//        
        if(funcionario.getPkIdFuncionario() != null)
        {
            qry.setParameter(10, funcionario.getPkIdFuncionario());
        }
        
        if(paciente != null && paciente.getPkIdPaciente() != null)
        {
            qry.setParameter(9, paciente.getPkIdPaciente());
        }
        
        if (quantidadeRegistos != null && quantidadeRegistos > 0)
        {
            qry.setMaxResults(quantidadeRegistos);
        }
        
        System.out.println(" "+query);

        List<AdmsServicoSolicitado> servicosSolicitados = qry.getResultList();

        return servicosSolicitados;
    }


    public String constroiQuery(AdmsServicoSolicitado servicoSolicitado, AdmsEstadoAgendamento estadoAgendamento, 
        RhFuncionario funcionario, Date dataInicial, Date dataFinal, Date dataAgendadaInicial, Date dataAgendadaFinal, AdmsPaciente paciente)
    {        
//        String funcionarioAdicionado = "", 
        String    estadoAgendamentoQuery = "", 
            dataAgendadaQuery = "", 
//            naoApareceuQuery = "",
            innerJoinMedico = "",
            medicoQuery = "";
//        boolean dataAgendadaDoServico = false;
            
            
        String  fromQuery = " FROM adms_servico_solicitado INNER JOIN adms_solicitacao ON adms_servico_solicitado.fk_id_solicitacao = adms_solicitacao.pk_id_solicitacao";
        
        String  whereQuery = " WHERE TRUE = TRUE";

        if (dataInicial != null)
        {
           whereQuery += " AND (adms_solicitacao.data >= ?1)";
        }
//        
        if (dataFinal != null)
           whereQuery += " AND (adms_solicitacao.data <= ?2)";
        
        if (servicoSolicitado.getFkIdServico().getPkIdServico() != null)
        {
            fromQuery += " INNER JOIN adms_servico ON adms_servico_solicitado.fk_id_servico = adms_servico.pk_id_servico"; 
            whereQuery += " AND adms_servico.pk_id_servico = ?3";
        }
//        
        if (servicoSolicitado.getFkIdTipoSolicitacao().getPkIdTipoSolicitacao() != null)
        {
            fromQuery += " INNER JOIN adms_tipo_solicitacao_servico ON adms_servico_solicitado.fk_id_tipo_solicitacao = adms_tipo_solicitacao_servico.pk_id_tipo_solicitacao";
            whereQuery += " AND adms_tipo_solicitacao_servico.pk_id_tipo_solicitacao = ?4";
        }
        
        if (servicoSolicitado.getFkIdClassificacaoServicoSolicitado().getPkIdClassificacaoServicoSolicitado() != null)
        {
            fromQuery += " INNER JOIN adms_classificacao_servico_solicitado ON adms_servico_solicitado.fk_id_classificacao_servico_solicitado = adms_classificacao_servico_solicitado.pk_id_classificacao_servico_solicitado";
            whereQuery += " AND adms_classificacao_servico_solicitado.pk_id_classificacao_servico_solicitado = ?5";
        }
        
        if (servicoSolicitado.getFkIdEstadoPagamento().getPkIdEstadoPagamento() != null)
        {
            fromQuery += " INNER JOIN adms_estado_pagamento ON adms_servico_solicitado.fk_id_estado_pagamento = adms_estado_pagamento.pk_id_estado_pagamento";
            whereQuery += " AND adms_estado_pagamento.pk_id_estado_pagamento = ?6";
        }
        
        if(funcionario.getPkIdFuncionario() != null)
        {
            innerJoinMedico = " INNER JOIN adms_agendamento_medico ON adms_agendamento.pk_id_agendamento = adms_agendamento_medico.fk_id_agendamento";
            medicoQuery = " AND adms_agendamento_medico.fk_id_medico = ?10";
        }
        

        if(dataAgendadaInicial != null)
        {
            dataAgendadaQuery += " AND (adms_agendamento.data_hora_inicio::date >= ?7";
            if(dataAgendadaFinal != null)
                dataAgendadaQuery += " AND adms_agendamento.data_hora_inicio::date <= ?11)";
            else dataAgendadaQuery += ")";
        }
        else if(dataAgendadaFinal != null)
        {
            dataAgendadaQuery += " AND (adms_agendamento.data_hora_inicio::date <= ?11)";
        }
        
        
        if(estadoAgendamento.getPkIdEstadoAgendamento() != null)
        {
            
            if(estadoAgendamento.getPkIdEstadoAgendamento() != -1)
            {
                if(estadoAgendamento.getDescricaoEstadoAgendamento().equalsIgnoreCase("Não Apareceu"))
                    estadoAgendamentoQuery = " (adms_estado_agendamento.pk_id_estado_agendamento = ?8 OR (adms_estado_agendamento.descricao_estado_agendamento = 'Agendado' AND ( (adms_agendamento.data_hora_fim IS NOT NULL AND adms_agendamento.data_hora_fim < CURRENT_TIMESTAMP) OR (AGE(adms_agendamento.data_hora_inicio) >= '12 hour') )))";
                else
                    estadoAgendamentoQuery = " adms_estado_agendamento.pk_id_estado_agendamento = ?8";

                whereQuery += " AND adms_servico_solicitado.pk_id_servico_solicitado IN(SELECT DISTINCT adms_agendamento.fk_id_servico_solicitado FROM adms_agendamento INNER JOIN adms_estado_agendamento ON adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento"+innerJoinMedico+" WHERE "+estadoAgendamentoQuery+""+dataAgendadaQuery+""+medicoQuery+")";
            }
            else{
                whereQuery += " AND adms_servico_solicitado.pk_id_servico_solicitado NOT IN(SELECT DISTINCT adms_agendamento.fk_id_servico_solicitado FROM adms_agendamento INNER JOIN adms_estado_agendamento ON adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento WHERE adms_estado_agendamento.descricao_estado_agendamento <> 'Cancelado')";
            }
        }

        if( (dataAgendadaInicial != null || dataAgendadaFinal != null) && estadoAgendamento.getPkIdEstadoAgendamento() == null)
        {
            whereQuery += " AND adms_servico_solicitado.pk_id_servico_solicitado IN(SELECT adms_agendamento.fk_id_servico_solicitado FROM adms_agendamento INNER JOIN adms_estado_agendamento ON adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento"+innerJoinMedico+" WHERE TRUE = TRUE "+dataAgendadaQuery+" AND adms_estado_agendamento.descricao_estado_agendamento <> 'Cancelado'"+medicoQuery+")";
        }
        
        if( (dataAgendadaInicial == null && dataAgendadaFinal == null) && estadoAgendamento.getPkIdEstadoAgendamento() == null && funcionario.getPkIdFuncionario() != null)
        {
            whereQuery += " AND adms_servico_solicitado.pk_id_servico_solicitado IN(SELECT adms_agendamento.fk_id_servico_solicitado FROM adms_agendamento INNER JOIN adms_estado_agendamento ON adms_agendamento.fk_id_estado_agendamento = adms_estado_agendamento.pk_id_estado_agendamento"+innerJoinMedico+" WHERE adms_estado_agendamento.descricao_estado_agendamento <> 'Cancelado'"+medicoQuery+")";
        }
//        
        if(paciente != null && paciente.getPkIdPaciente() != null)
        {
            whereQuery += " AND adms_solicitacao.fk_id_paciente = ?9";
        }
        
        String query = "SELECT adms_servico_solicitado.pk_id_servico_solicitado, adms_servico_solicitado.fk_id_solicitacao, adms_servico_solicitado.fk_id_servico, fk_id_estado_pagamento, \n" +
                        "  adms_servico_solicitado.observacao, adms_servico_solicitado.fk_id_classificacao_servico_solicitado, adms_servico_solicitado.fk_id_tipo_solicitacao, \n" +
                        "  adms_servico_solicitado.fk_id_recepcionista, adms_servico_solicitado.data_atendimento, adms_servico_solicitado.fk_id_preco_categoria_servico "+fromQuery+""+whereQuery+"";
        
        return query;
    }

}
