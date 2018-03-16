/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AdmsPaciente;
import entidade.DiagTipagemDoente;
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
public class DiagTipagemDoenteFacade extends AbstractFacade<DiagTipagemDoente>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTipagemDoenteFacade()
    {
        super(DiagTipagemDoente.class);
    }

    public DiagTipagemDoente findTipagemDoente(AdmsPaciente paciente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTipagemDoente d WHERE d.fkIdPaciente.pkIdPaciente = :idPaciente", DiagTipagemDoente.class).setParameter("idPaciente", paciente.getPkIdPaciente());

        if ( typedQuery.getResultList().isEmpty() )
        {
            return null;
        }

        return (DiagTipagemDoente) typedQuery.getResultList().get(0);
    }

    public List<DiagTipagemDoente> findTipagem(DiagTipagemDoente tipagemDoente, Date dataInicio, Date dataFim, int numeroRegistos)
    {
        String query = constroiQueryTipagem(tipagemDoente, dataInicio, dataFim);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null )
        {
            qry.setParameter("pkIdPessoa", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getPkIdPessoa());
        }

        if ( tipagemDoente.getFkIdPaciente().getNumeroPaciente() != null && !tipagemDoente.getFkIdPaciente().getNumeroPaciente().trim().isEmpty() )
        {
            qry.setParameter("numProcesso", tipagemDoente.getFkIdPaciente().getNumeroPaciente() + "%");
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNome() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            qry.setParameter("nome", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNome() + "%");
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().trim().isEmpty() )
        {
            qry.setParameter("nomeDoMeio", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            qry.setParameter("sobreNome", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getSobreNome() + "%");
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null )
        {
            qry.setParameter("nacionalidade", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null )
        {
            qry.setParameter("sexo", tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        if ( tipagemDoente.getConclusao().getPkIdGrupoSanguineo() != null )
        {
            qry.setParameter("grupo", tipagemDoente.getConclusao().getPkIdGrupoSanguineo());
        }

        if ( tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getNome() != null && !tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            qry.setParameter("nomeFuncionario", tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getNome() + "%");
        }

        if ( tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getSobreNome() != null && !tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            qry.setParameter("sobreNomeFuncionario", tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getSobreNome() + "%");
        }

        if ( dataInicio != null && dataFim != null )
        {
            qry.setParameter("dataInicio", dataInicio);
            qry.setParameter("dataFim", dataFim);
        }

        if ( dataInicio != null && dataFim == null )
        {
            qry.setParameter("dataInicio", dataInicio);
        }

        if ( dataInicio == null && dataFim != null )
        {
            qry.setParameter("dataFim", dataFim);
        }

        qry.setMaxResults(numeroRegistos);
        List<DiagTipagemDoente> tipagens = qry.getResultList();

        return tipagens;
    }

    public String constroiQueryTipagem(DiagTipagemDoente tipagemDoente, Date dataInicio, Date dataFim)
    {
        boolean doc = false, docParentesis = false;

        String query = "SELECT d FROM DiagTipagemDoente d WHERE :pesquisar = :pesquisar";

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getPkIdPessoa() != null )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if ( tipagemDoente.getFkIdPaciente().getNumeroPaciente() != null && !tipagemDoente.getFkIdPaciente().getNumeroPaciente().trim().isEmpty() )
        {
            query += " AND d.fkIdPaciente.numeroPaciente LIKE :numProcesso";
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNome() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNomeDoMeio() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getNomeDoMeio().trim().isEmpty() )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getSobreNome() != null && !tipagemDoente.getFkIdPaciente().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if ( tipagemDoente.getFkIdPaciente().getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null )
        {
            query += " AND d.fkIdPaciente.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        if ( tipagemDoente.getConclusao().getPkIdGrupoSanguineo() != null )
        {
            query += " AND d.conclusao.pkIdGrupoSanguineo = :grupo";
        }

        if ( tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getNome() != null && !tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getNome().trim().isEmpty() )
        {
            query += " AND d.fkRealizadoPor.fkIdPessoa.nome LIKE :nomeFuncionario";
        }

        if ( tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getSobreNome() != null && !tipagemDoente.getFkRealizadoPor().getFkIdPessoa().getSobreNome().trim().isEmpty() )
        {
            query += " AND d.fkRealizadoPor.fkIdPessoa.sobreNome LIKE :sobreNomeFuncionario";
        }

        if ( dataInicio != null && dataFim != null )
        {
            query += " AND d.dataTipagem >= :dataInicio and d.dataTipagem <= :dataFim";
        }

        if ( dataInicio != null && dataFim == null )
        {
            query += " AND d.dataTipagem >= :dataInicio";
        }

        if ( dataInicio == null && dataFim != null )
        {
            query += " AND d.dataTipagem <= :dataFim";
        }

        query += " ORDER BY d.fkIdPaciente.fkIdPessoa.nome";

        return query;
    }
}
