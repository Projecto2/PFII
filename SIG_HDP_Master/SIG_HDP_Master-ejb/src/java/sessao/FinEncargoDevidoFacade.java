/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AdmsPaciente;
import entidade.AdmsServicoSolicitado;
import entidade.FinEncargoDevido;
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
public class FinEncargoDevidoFacade extends AbstractFacade<FinEncargoDevido>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinEncargoDevidoFacade()
    {
        super(FinEncargoDevido.class);
    }

    public FinEncargoDevido findEncargoDoServico(AdmsServicoSolicitado servicoSolicitado)
    {
        System.out.println("aaa "+servicoSolicitado.getPkIdServicoSolicitado());
        Query qry = em.createQuery("SELECT encargoDevido FROM FinEncargoDevido encargoDevido WHERE encargoDevido.fkIdServicoSolicitado.pkIdServicoSolicitado = :idServicoSolicitado");

        qry.setParameter("idServicoSolicitado", servicoSolicitado.getPkIdServicoSolicitado());

        List<FinEncargoDevido> encargo = qry.getResultList();

        if (encargo.isEmpty())
        {
            return null;
        }

        return encargo.get(0);
    }

    public List<FinEncargoDevido> findEncargosDevidos(FinEncargoDevido encargoDevido, Integer idSubProcesso, Date dataInicial, Date dataFinal, AdmsPaciente paciente, Integer quantidadeRegistos)
    {
        System.out.println("ola");
        String query = constroiQuery(encargoDevido, idSubProcesso, dataInicial, dataFinal, paciente);

        Query qry = em.createQuery(query);
//        Query qry = em.createQuery("SELECT encargo FROM FinEncargoDevido as encargo WHERE TRUE = TRUE");

        if (dataInicial != null)
        {
            qry.setParameter("dataInicial", dataInicial);
        }

        if (dataFinal != null)
        {
            qry.setParameter("dataFinal", dataFinal);
        }

        if (idSubProcesso != null && idSubProcesso > 0)
        {
            qry.setParameter("subProcesso", idSubProcesso);
        }

        if (encargoDevido.getFkIdTipoEncargo().getPkIdTipoEncargo() != null)
        {
            qry.setParameter("tipoEncargo", encargoDevido.getFkIdTipoEncargo().getPkIdTipoEncargo());
        }

        if (encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().getPkIdServico() != null)
        {
            qry.setParameter("idServico", encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().getPkIdServico());
        }

//      if (!nomeCompleto.trim().isEmpty())
//          qry.setParameter("nome", "%"+nomeCompleto+"%");
        if (encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getPkIdCategoriaServico() != null)
        {
            qry.setParameter("idCategoriaServico", encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getPkIdCategoriaServico());
        }
            
        if (paciente != null && paciente.getPkIdPaciente() != null)
        {
            qry.setParameter("idPaciente", paciente.getPkIdPaciente());
        }

        if (quantidadeRegistos != null && quantidadeRegistos > 0)
        {
            qry.setMaxResults(quantidadeRegistos);
        }

        List<FinEncargoDevido> encargoDevidos = qry.getResultList();

        return encargoDevidos;
    }

    public String constroiQuery(FinEncargoDevido encargoDevido, Integer idSubProcesso, Date dataInicial, Date dataFinal, AdmsPaciente paciente)
    {
        String query = "SELECT encargo FROM FinEncargoDevido as encargo WHERE TRUE = TRUE AND encargo.fkIdEstadoPagamentoPagoNaopago.descricao LIKE 'Não Pago'";
//      String query = "SELECT p FROM FinEncargoDevido as p WHERE true = true";

        if (dataInicial != null)
        {
            query += " AND (encargo.data >= :dataInicial)";
        }

        if (dataFinal != null)
        {
            query += " AND (encargo.data <= :dataFinal)";
        }

        if (idSubProcesso != null && idSubProcesso > 0)
        {

            query += " AND encargo.fkIdServicoSolicitado.fkIdSolicitacao.fkIdSubprocesso.numeroSubprocesso = :subProcesso";
        }

        if (encargoDevido.getFkIdTipoEncargo().getPkIdTipoEncargo() != null)

        {
            query += " AND encargo.fkIdTipoEncargo.pkIdTipoEncargo = :tipoEncargo";
        }

        if (encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getFkIdServico().getPkIdServico() != null)
        {
            query += " AND encargo.fkIdPrecoCategoriaServico.fkIdCategoriaServico.fkIdServico.pkIdServico = :idServico";
        }

//      if (!nomeCompleto.trim().isEmpty())
//          qry.setParameter("nome", "%"+nomeCompleto+"%");
        if (encargoDevido.getFkIdPrecoCategoriaServico().getFkIdCategoriaServico().getPkIdCategoriaServico() != null)
        {
            query += " AND encargo.fkIdPrecoCategoriaServico.fkIdCategoriaServico.pkIdCategoriaServico = :idCategoriaServico";
        }
        
        if (paciente != null && paciente.getPkIdPaciente() != null)
        {
            query += " AND encargo.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.pkIdPaciente = :idPaciente";
        }

//      if (!nomeCompleto.trim().isEmpty())
        query += " ORDER BY encargo.data ASC";
        
        System.out.println(""+query);

        return query;
    }

}
