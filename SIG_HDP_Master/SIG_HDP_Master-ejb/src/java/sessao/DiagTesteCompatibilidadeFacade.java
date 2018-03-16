/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AdmsPaciente;
import entidade.DiagBolsaSangue;
import entidade.DiagRequisicaoComponenteSanguineo;
import entidade.DiagTesteCompatibilidade;
import entidade.DiagTipagemDador;
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
public class DiagTesteCompatibilidadeFacade extends AbstractFacade<DiagTesteCompatibilidade>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagTesteCompatibilidadeFacade()
    {
        super(DiagTesteCompatibilidade.class);
    }

    public List<DiagTesteCompatibilidade> findTesteCompatibilidadeByPacienteAndBolsaAndSolicitacao(AdmsPaciente paciente, DiagBolsaSangue bolsaSangue, DiagRequisicaoComponenteSanguineo requisicao)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTesteCompatibilidade d WHERE d.fkIdPaciente.pkIdPaciente = :idPaciente AND d.fkIdBolsaSangue.pkIdBolsaSangue = :idBolsa AND "
                + "d.fkIdRequisicaoComponente.pkIdRequisicaoComponenteSanguineo = :idRequisicao", DiagTesteCompatibilidade.class);

        typedQuery.setParameter("idPaciente", paciente.getPkIdPaciente());
        typedQuery.setParameter("idBolsa", bolsaSangue.getPkIdBolsaSangue());
        typedQuery.setParameter("idRequisicao", requisicao.getPkIdRequisicaoComponenteSanguineo());
        
        return typedQuery.getResultList();
    }
    
    public List<DiagTipagemDador> findCandidatosCompativeis(AdmsPaciente paciente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTipagemDador d WHERE d.fkIdCandidatoDador.pkIdCandidatoDador IN "
                + "(SELECT dd.fkIdBolsaSangue.fkIdCandidatoDador.pkIdCandidatoDador FROM DiagTesteCompatibilidade dd WHERE dd.fkIdPaciente.pkIdPaciente = :idPaciente "
                + "AND dd.fkIdResultadoTesteCompatibilidade.pkIdResultadoTesteCompatibilidade = 1)", DiagTipagemDador.class).setParameter("idPaciente",
                        paciente.getPkIdPaciente());

        return typedQuery.getResultList();
    }

    public List<DiagTesteCompatibilidade> findTesteCompatibilidadeByPaciente(AdmsPaciente paciente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagTesteCompatibilidade d WHERE d.fkIdPaciente.pkIdPaciente = :idPaciente", DiagTesteCompatibilidade.class).setParameter("idPaciente", paciente.getPkIdPaciente());

        return typedQuery.getResultList();
    }

    public List<DiagTesteCompatibilidade> findDistinctTesteCompatibilidadeByPaciente(AdmsPaciente paciente)
    {
        TypedQuery typedQuery = em.createQuery("SELECT DISTINCT d FROM DiagTesteCompatibilidade d WHERE d.fkIdPaciente.pkIdPaciente = :idPaciente", DiagTesteCompatibilidade.class).setParameter("idPaciente", paciente.getPkIdPaciente());

        return typedQuery.getResultList();
    }

    public List<AdmsPaciente> findPacienteComTesteCompatibilidade(AdmsPaciente pacienteAux)
    {
        String query = constroiQuery(pacienteAux);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (pacienteAux.getNumeroPaciente() != null && !pacienteAux.getNumeroPaciente().trim().isEmpty())
        {
            qry.setParameter("numProcesso", pacienteAux.getNumeroPaciente());
        }

        if (pacienteAux.getFkIdPessoa().getNome() != null && !pacienteAux.getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", pacienteAux.getFkIdPessoa().getNome() + "%");
        }

        if (pacienteAux.getFkIdPessoa().getNomeDoMeio() != null && !pacienteAux.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", pacienteAux.getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (pacienteAux.getFkIdPessoa().getSobreNome() != null && !pacienteAux.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", pacienteAux.getFkIdPessoa().getSobreNome() + "%");
        }

        if (pacienteAux.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pacienteAux.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());
        }

        if (pacienteAux.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pacienteAux.getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        List<AdmsPaciente> testes = qry.getResultList();

        return testes;
    }

    public String constroiQuery(AdmsPaciente pacienteAux)
    {
        String query = "SELECT DISTINCT a FROM AdmsPaciente a WHERE :pesquisar = :pesquisar";

        if (pacienteAux.getNumeroPaciente() != null && !pacienteAux.getNumeroPaciente().trim().isEmpty())
        {
            query += " AND a.numeroPaciente = :numProcesso";
        }

        if (pacienteAux.getFkIdPessoa().getNome() != null && !pacienteAux.getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND a.fkIdPessoa.nome LIKE :nome";
        }

        if (pacienteAux.getFkIdPessoa().getNomeDoMeio() != null && !pacienteAux.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND a.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (pacienteAux.getFkIdPessoa().getSobreNome() != null && !pacienteAux.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND a.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

        if (pacienteAux.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND a.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pacienteAux.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND a.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " AND a.pkIdPaciente IN (SELECT d.fkIdPaciente.pkIdPaciente FROM DiagTesteCompatibilidade d)"
                + " ORDER BY a.fkIdPessoa.nome";

        return query;
    }
}
