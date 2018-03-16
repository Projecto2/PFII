/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsSolicitacao;
import entidade.RhFuncionario;
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
public class AdmsSolicitacaoFacade extends AbstractFacade<AdmsSolicitacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsSolicitacaoFacade()
    {
        super(AdmsSolicitacao.class);
    }
    
    
    public List<AdmsSolicitacao> findSolicitacao(AdmsSolicitacao solicitacao, Date dataInicial, 
        Date dataFinal, int solicitacaoMedica, Integer quantidadeRegistos, RhFuncionario medicoSolicitante)
    {
        String query = constroiQuery(solicitacao, dataInicial, dataFinal, solicitacaoMedica, medicoSolicitante);

        Query qry = em.createQuery(query);

        if (dataInicial != null)
            qry.setParameter("dataInicial", dataInicial);
        
        if (dataFinal != null)
            qry.setParameter("dataFinal", dataFinal);
        
//        Integer numeroproc = solicitacao.getFkIdSubprocesso().getNumeroSubprocesso();
//        
        if(solicitacao.getFkIdSubprocesso().getNumeroSubprocesso() != 0)
            qry.setParameter("numeroSubprocesso", solicitacao.getFkIdSubprocesso().getNumeroSubprocesso());
        
        if(solicitacao.getFkIdPaciente() != null && solicitacao.getFkIdPaciente().getPkIdPaciente() != null)
            qry.setParameter("idPaciente", solicitacao.getFkIdPaciente().getPkIdPaciente());
        
        if(medicoSolicitante != null && medicoSolicitante.getPkIdFuncionario() != null)
            qry.setParameter("idMedicoSolicitante", medicoSolicitante.getPkIdFuncionario());
        
        if (quantidadeRegistos != null && quantidadeRegistos > 0)
        {
            qry.setMaxResults(quantidadeRegistos);
        }
        List<AdmsSolicitacao> solicitacaos = qry.getResultList();

        return solicitacaos;
    }


    public String constroiQuery(AdmsSolicitacao solicitacao, Date dataInicial, Date dataFinal, int solicitacaoMedica, RhFuncionario medicoSolicitante)
    {        
        String query = "SELECT solicitacao FROM AdmsSolicitacao solicitacao WHERE TRUE = TRUE";

        if (dataInicial != null)
           query += " AND (solicitacao.data >= :dataInicial)";
        
        if (dataFinal != null)
           query += " AND (solicitacao.data <= :dataFinal)";
        
//        Integer numeroproc = solicitacao.getFkIdSubprocesso().getNumeroSubprocesso();
//        
        if(solicitacao.getFkIdSubprocesso().getNumeroSubprocesso() != 0)
            query += " AND solicitacao.fkIdSubprocesso.numeroSubprocesso = :numeroSubprocesso";
        
        if(solicitacao.getFkIdPaciente() != null && solicitacao.getFkIdPaciente().getPkIdPaciente() != null)
            query += " AND solicitacao.fkIdPaciente.pkIdPaciente = :idPaciente";
        
        if(medicoSolicitante != null && medicoSolicitante.getPkIdFuncionario() != null)
            query += " AND solicitacao.fkIdFuncionarioSolicitante.pkIdFuncionario = :idMedicoSolicitante";
        
        if(solicitacaoMedica != 1)
        {
            if(solicitacaoMedica == 2)
                query += " AND (solicitacao.fkIdFuncionarioSolicitante.fkIdProfissao.descricao = 'Médico' OR "
                    + "solicitacao.fkIdFuncionarioSolicitante.fkIdProfissao.descricao = 'Enfermeiro')";
            else query += " AND ( solicitacao.fkIdFuncionarioSolicitante.fkIdProfissao.descricao <> 'Médico' AND "
                    + "solicitacao.fkIdFuncionarioSolicitante.fkIdProfissao.descricao <> 'Enfermeiro')";
                
        }

        query += " ORDER BY solicitacao.data asc";
        
        System.out.println(" "+query);

        return query;
    }
    
    public AdmsSolicitacao findUltimaSolicitacaoBySubProcesso(Integer subProcesso, long idPaciente)
    {
        Query query = em.createQuery("SELECT DISTINCT solicitacao FROM AdmsSolicitacao solicitacao WHERE solicitacao.fkIdSubprocesso.numeroSubprocesso = :subProcesso AND solicitacao.fkIdPaciente.pkIdPaciente = :idPaciente  ORDER BY solicitacao.pkIdSolicitacao DESC");
        
        query.setParameter("subProcesso", subProcesso.longValue());
        query.setParameter("idPaciente", idPaciente);
        
        query.setMaxResults(1);
        
        List<AdmsSolicitacao> solicitacao = query.getResultList();
        if(solicitacao.isEmpty()) return null;
        return solicitacao.get(0);
    }

//    public EntityManager getEm()
//    {
//        return em;
//    }
//
//    public void setEm(EntityManager em)
//    {
//        this.em = em;
//    }
    
}
