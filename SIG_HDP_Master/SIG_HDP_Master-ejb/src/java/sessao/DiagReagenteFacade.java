/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.DiagReagente;
import entidade.DiagTipoReagente;
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
public class DiagReagenteFacade extends AbstractFacade<DiagReagente>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagReagenteFacade()
    {
        super(DiagReagente.class);
    }
    
    public DiagReagente findReagenteById(int idReagente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagReagente d WHERE d.pkIdReagente = :idReagente", DiagReagente.class).setParameter("idReagente", idReagente);

        return (DiagReagente) typedQuery.getResultList().get(0);
    }

    public List<DiagReagente> findReagenteByTipo(DiagTipoReagente tipoReagente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagReagente d WHERE d.fkIdTipoReagente.pkIdTipoReagente = :idTipoReagente", DiagReagente.class).setParameter("idTipoReagente", tipoReagente.getPkIdTipoReagente());

        return typedQuery.getResultList();
    }

    public List<DiagReagente> findPesquisa(DiagReagente reagentePesquisar, Date dataInicioCadastro, Date dataFimCadastro, Date dataInicioValidade, Date dataFimValidade)
    {
        String query = constroiQuery(reagentePesquisar, dataInicioCadastro, dataFimCadastro, dataInicioValidade, dataFimValidade);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (reagentePesquisar.getPkIdReagente() != null)
        {
            qry.setParameter("pkIdReagente", reagentePesquisar.getPkIdReagente());
        }

        if (reagentePesquisar.getNumeroLote() != null && !reagentePesquisar.getNumeroLote().trim().isEmpty())
        {
            qry.setParameter("numeroLote", reagentePesquisar.getNumeroLote() + "%");
        }

        if (reagentePesquisar.getFkIdTipoReagente().getPkIdTipoReagente() != null)
        {
            qry.setParameter("pkIdTipoReagente", reagentePesquisar.getFkIdTipoReagente().getPkIdTipoReagente());
        }

        if (reagentePesquisar.getQuantidade() != null)
        {
            qry.setParameter("quantidade", reagentePesquisar.getQuantidade());
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

        if (dataInicioValidade != null && dataFimValidade != null)
        {
            qry.setParameter("dataInicioValidade", dataFimValidade);
            qry.setParameter("dataFimValidade", dataFimValidade);
        }

        if (dataInicioValidade != null && dataFimValidade == null)
        {
            qry.setParameter("dataInicioValidade", dataInicioValidade);
        }

        if (dataInicioValidade == null && dataFimValidade != null)
        {
            qry.setParameter("dataFimValidade", dataFimValidade);
        }

        List<DiagReagente> resultado = qry.getResultList();

        return resultado;
    }

    public String constroiQuery(DiagReagente reagentePesquisar, Date dataInicioCadastro, Date dataFimCadastro, Date dataInicioValidade, Date dataFimValidade)
    {
        String query = "Select d FROM DiagReagente d WHERE :pesquisar = :pesquisar";

        if (reagentePesquisar.getPkIdReagente() != null)
        {
            query += " AND d.pkIdReagente = :pkIdReagente";
        }

        if (reagentePesquisar.getNumeroLote() != null && !reagentePesquisar.getNumeroLote().trim().isEmpty())
        {
            query += " AND d.numeroLote LIKE :numeroLote";
        }

        if (reagentePesquisar.getFkIdTipoReagente().getPkIdTipoReagente() != null)
        {
            query += " AND d.fkIdTipoReagente.pkIdTipoReagente = :pkIdTipoReagente";
        }

        if (reagentePesquisar.getQuantidade() != null)
        {
            query += " AND d.quantidade = :quantidade";
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

        if (dataInicioValidade != null && dataFimValidade != null)
        {
            query += " AND d.dataValidade >= :dataInicioValidade and d.dataValidade <= :dataFimValidade";
        }

        if (dataInicioValidade != null && dataFimValidade == null)
        {
            query += " AND d.dataValidade >= :dataInicioValidade";
        }

        if (dataInicioValidade == null && dataFimValidade != null)
        {
            query += " AND d.dataValidade <= :dataFimValidade";
        }

        query += " ORDER BY d.numeroLote";

        return query;
    }
}
