/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagControloSemanalMaterial;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagControloSemanalMaterialFacade extends AbstractFacade<DiagControloSemanalMaterial>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagControloSemanalMaterialFacade()
    {
        super(DiagControloSemanalMaterial.class);
    }

    public List<DiagControloSemanalMaterial> findPesquisaHistorico(DiagControloSemanalMaterial controloSemanalMaterial, Date dataInicioCadastro, Date dataFimCadastro, int numeroRegistos)
    {
        String query = constroiQuery(controloSemanalMaterial, dataInicioCadastro, dataFimCadastro);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (controloSemanalMaterial.getPkIdControloSemanalMaterial() != null)
        {
            qry.setParameter("pkIdControloSemanalMaterial", controloSemanalMaterial.getPkIdControloSemanalMaterial());
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome() + "%");
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getSobreNome() + "%");
        }

        if (dataFimCadastro != null)
        {
            Calendar cal = new GregorianCalendar();

            dataFimCadastro.setHours(cal.getMaximum(Calendar.HOUR_OF_DAY));
            dataFimCadastro.setMinutes(cal.getMaximum(Calendar.MINUTE));
            dataFimCadastro.setSeconds(cal.getMaximum(Calendar.SECOND));
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

        System.out.println("" + query.toString());

        qry.setMaxResults(numeroRegistos);
        List<DiagControloSemanalMaterial> result = qry.getResultList();

        return result;
    }

    public String constroiQuery(DiagControloSemanalMaterial controloSemanalMaterial, Date dataInicioCadastro, Date dataFimCadastro)
    {
        String query = "SELECT d FROM DiagControloSemanalMaterial d WHERE :pesquisar = :pesquisar";

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.pkIdCandidatoDador = :pkIdDador";
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getSobreNome() != null && !controloSemanalMaterial.getFkIdFuncionario().getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (dataInicioCadastro != null && dataFimCadastro != null)
        {
            query += " AND d.data >= :dataInicioCadastro and d.data <= :dataFimCadastro";
        }

        if (dataInicioCadastro != null && dataFimCadastro == null)
        {
            query += " AND d.data >= :dataInicioCadastro";
        }

        if (dataInicioCadastro == null && dataFimCadastro != null)
        {
            query += " AND d.data <= :dataFimCadastro";
        }

        query += " ORDER BY d.data DESC";

        return query;
    }
}
