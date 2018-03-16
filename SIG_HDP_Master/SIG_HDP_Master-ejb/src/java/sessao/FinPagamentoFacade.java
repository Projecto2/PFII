/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsPaciente;
import entidade.FinPagamento;
import entidade.FinPagamentoConvenio;
import entidade.FinPagamentoViaBanco;
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
public class FinPagamentoFacade extends AbstractFacade<FinPagamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinPagamentoFacade()
    {
        super(FinPagamento.class);
    }
    
    
    public List<FinPagamento> findPagamento(FinPagamento finPagamentoPesquisar, Date dataInicial, Date dataFinal, 
        FinPagamentoViaBanco pagamentoViaBanco, FinPagamentoConvenio pagamentoConvenio, 
        AdmsPaciente paciente, Integer quantidadeRegistos)
    {
        String query = constroiQuery(finPagamentoPesquisar, dataInicial, dataFinal, pagamentoViaBanco, pagamentoConvenio, paciente);

        Query qry = em.createQuery(query);
        
        qry.setParameter("pesquisar", true);

        if (dataInicial != null)
            qry.setParameter("dataInicial", dataInicial);
        
        if (dataFinal != null)
            qry.setParameter("dataFinal", dataFinal);
//        
//        if (finPagamentoPesquisar.getFkIdTipoPagamentoSolicitacao().getFkIdTipoPagamento().getPkIdTipoPagamento() != null)
//        {
//            qry.setParameter("idTipoPagamento", finPagamentoPesquisar.getFkIdTipoPagamentoSolicitacao().getFkIdTipoPagamento().getPkIdTipoPagamento());
//        }
        
        if (finPagamentoPesquisar.getFkIdFormaPagamento().getPkIdFormaPagamento() != null)
        {
            qry.setParameter("idFormaDePagamento", finPagamentoPesquisar.getFkIdFormaPagamento().getPkIdFormaPagamento());
        }
        
        if (pagamentoViaBanco.getFkIdContaBancaria().getFkIdBanco().getPkIdBanco() != null)
        {
            qry.setParameter("idBanco", pagamentoViaBanco.getFkIdContaBancaria().getFkIdBanco().getPkIdBanco());
        }
        
        if (pagamentoViaBanco.getFkIdContaBancaria().getPkIdContaBancaria() != null)
        {
            qry.setParameter("idContaBancaria", pagamentoViaBanco.getFkIdContaBancaria().getPkIdContaBancaria());
        }
        
        if (pagamentoConvenio.getFkIdConvenio().getPkIdConvenio() != null)
        {
            System.out.println("");
            qry.setParameter("idConvenio", pagamentoConvenio.getFkIdConvenio().getPkIdConvenio());
        }
        
        if (pagamentoConvenio.getFkIdProjetoConvenio().getPkIdProjetoConvenio() != null)
        {
            qry.setParameter("idProjetoConvenio", pagamentoConvenio.getFkIdProjetoConvenio().getPkIdProjetoConvenio());
        }
        
        if(paciente != null && paciente.getPkIdPaciente() != null)
        {
            qry.setParameter("idPaciente", paciente.getPkIdPaciente());
        }
        
        if (quantidadeRegistos != null && quantidadeRegistos > 0)
        {
            qry.setMaxResults(quantidadeRegistos);
        }
        
        System.out.println(" "+query);

        List<FinPagamento> finPagamentos = qry.getResultList();

        return finPagamentos;
    }
    
    
    public String constroiQuery(FinPagamento finPagamentoPesquisar, Date dataInicial, Date dataFinal,
        FinPagamentoViaBanco pagamentoViaBanco, FinPagamentoConvenio pagamentoConvenio, AdmsPaciente paciente)
    {        
        String queryContaBancaria = "";
        String queryProjetoConvenio = "";
        String query = "SELECT finPagamento FROM FinPagamento finPagamento WHERE :pesquisar = :pesquisar";

        if (dataInicial != null)
           query += " AND (finPagamento.dataPagamento >= :dataInicial)";
        
        if (dataFinal != null)
           query += " AND (finPagamento.dataPagamento <= :dataFinal)";
        
//        if (finPagamentoPesquisar.getFkIdTipoPagamentoSolicitacao().getFkIdTipoPagamento().getPkIdTipoPagamento() != null)
//            query += " AND finPagamento.fkIdTipoPagamentoSolicitacao.fkIdTipoPagamento.pkIdTipoPagamento = :idTipoPagamento";
        
        if (finPagamentoPesquisar.getFkIdFormaPagamento().getPkIdFormaPagamento() != null)
        {
            query += " AND finPagamento.fkIdFormaPagamento.pkIdFormaPagamento = :idFormaDePagamento";
        }
        
        if (pagamentoViaBanco.getFkIdContaBancaria().getPkIdContaBancaria() != null)
        {
            queryContaBancaria = " AND pagamentoBancario.fkIdContaBancaria.pkIdContaBancaria = :idContaBancaria";
        }
        
        if (pagamentoViaBanco.getFkIdContaBancaria().getFkIdBanco().getPkIdBanco() != null)
        {
            query += " AND finPagamento.pkIdPagamento IN(SELECT pagamentoBancario.fkIdPagamento.pkIdPagamento "
                + "FROM FinPagamentoViaBanco pagamentoBancario WHERE pagamentoBancario.fkIdContaBancaria.fkIdBanco.pkIdBanco = :idBanco"+queryContaBancaria+")";
        }
        
        if (pagamentoConvenio.getFkIdProjetoConvenio().getPkIdProjetoConvenio() != null)
        {
            queryProjetoConvenio = " OR pagamentoConvenio.fkIdProjetoConvenio.pkIdProjetoConvenio = :idProjetoConvenio";
        }
        
//OR pagamentoConvenio.fkIdProjetoConvenio.fkIdConvenio.pkIdConvenio = :idConvenio
        
        if (pagamentoConvenio.getFkIdConvenio().getPkIdConvenio() != null)
        {
            query += " AND finPagamento.pkIdPagamento IN(SELECT pagamentoConvenio.fkIdPagamento.pkIdPagamento "
                + "FROM FinPagamentoConvenio pagamentoConvenio WHERE "
                + "pagamentoConvenio.fkIdConvenio.pkIdConvenio = :idConvenio "+queryProjetoConvenio+")";
        }

        if (paciente != null && paciente.getPkIdPaciente() != null)
            query += " AND finPagamento.pkIdPagamento IN(SELECT pagamentoEncargo.fkIdPagamento.pkIdPagamento FROM FinPagamentoEncargoDevido pagamentoEncargo WHERE pagamentoEncargo.fkIdEncargoDevido.fkIdSubprocesso.fkIdPaciente.pkIdPaciente = :idPaciente)";
        
        query += " ORDER BY finPagamento.dataPagamento ASC";
        
        return query;
    }
    
}
