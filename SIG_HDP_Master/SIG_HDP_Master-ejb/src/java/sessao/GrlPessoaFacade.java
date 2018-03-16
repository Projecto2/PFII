/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.GrlPessoa;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 * @author Garcia Paulo
 */
@Stateless
public class GrlPessoaFacade extends AbstractFacade<GrlPessoa>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlPessoaFacade()
    {
        super(GrlPessoa.class);
    }

    public List<GrlPessoa> findPessoa(GrlPessoa pessoa, boolean flagPaciente, boolean flagFuncionario, boolean flagCandidato,
        boolean flagEstagiario, boolean flagDador)
    {
        String query = constroiQuery(pessoa, flagPaciente, flagFuncionario, flagCandidato, flagEstagiario, flagDador);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (pessoa.getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", pessoa.getPkIdPessoa());
        }

        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            qry.setParameter("nome", pessoa.getNome() + "%");
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", pessoa.getNomeDoMeio() + "%");
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", pessoa.getSobreNome() + "%");
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
        {
            qry.setParameter("tipoDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
        {
            qry.setParameter("numeroDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() + "%");
        }

        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pessoa.getFkIdNacionalidade().getPkIdPais());
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pessoa.getFkIdSexo().getPkIdSexo());
        }

        qry.setMaxResults(100);
        List<GrlPessoa> pessoas = qry.getResultList();

        return pessoas;
    }

    public String constroiQuery(GrlPessoa pessoa, boolean flagPaciente, boolean flagFuncionario, boolean flagCandidato,
        boolean flagEstagiario, boolean flagDador)
    {
        boolean doc = false, docParentesis = false;
        String query = "SELECT p FROM GrlPessoa p WHERE :pesquisar = :pesquisar";

        if (pessoa.getPkIdPessoa() != null)
        {
            query += " AND p.pkIdPessoa = :pkIdPessoa";
        }

        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            query += " AND LOWER(p.nome) LIKE LOWER(:nome)";
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            query += " AND LOWER(p.nomeDoMeio) LIKE LOWER(:nomeDoMeio)";
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            query += " AND LOWER(p.sobreNome) LIKE LOWER(:sobreNome)";
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
        {
            doc = true;
            query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
        {
            docParentesis = true;
            if (doc)
            {
                query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
            }
            else
            {
                query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
            }
        }

        if ((doc) && (!docParentesis))
        {
            query += ")";
        }

        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND p.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND p.fkIdSexo.pkIdSexo = :sexo";
        }

        if (!flagPaciente)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT pac.fkIdPessoa.pkIdPessoa FROM AdmsPaciente pac)";
        }

        if (!flagFuncionario)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT f.fkIdPessoa.pkIdPessoa FROM RhFuncionario f)";
        }

        if (!flagCandidato)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT c.fkIdPessoa.pkIdPessoa FROM RhCandidato c)";
        }

        if (!flagEstagiario)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT e.fkIdPessoa.pkIdPessoa FROM RhEstagiario e)";
        }

        query += " ORDER BY p.nome, p.nomeDoMeio, p.sobreNome";

        return query;
    }

    public boolean isFuncionario(GrlPessoa pessoa)
    {
        TypedQuery<GrlPessoa> t = em.createQuery("SELECT p FROM GrlPessoa p WHERE p.pkIdPessoa = :idPessoa"
            + " AND p.pkIdPessoa IN (SELECT f.fkIdPessoa.pkIdPessoa FROM RhFuncionario f)", GrlPessoa.class)
            .setParameter("idPessoa", pessoa.getPkIdPessoa());

        if (t.getResultList().isEmpty())
        {
            return false;
        }
        return true;
    }

    public boolean isCandidato(GrlPessoa pessoa)
    {
        TypedQuery<GrlPessoa> t = em.createQuery("SELECT p FROM GrlPessoa p WHERE p.pkIdPessoa = :idPessoa"
            + " AND p.pkIdPessoa IN (SELECT c.fkIdPessoa.pkIdPessoa FROM RhCandidato c)", GrlPessoa.class)
            .setParameter("idPessoa", pessoa.getPkIdPessoa());

        if (t.getResultList().isEmpty())
        {
            return false;
        }
        return true;
    }

    public boolean isEstagiario(GrlPessoa pessoa)
    {
        TypedQuery<GrlPessoa> t = em.createQuery("SELECT p FROM GrlPessoa p WHERE p.pkIdPessoa = :idPessoa"
            + " AND p.pkIdPessoa IN (SELECT e.fkIdPessoa.pkIdPessoa FROM RhEstagiario e)", GrlPessoa.class)
            .setParameter("idPessoa", pessoa.getPkIdPessoa());

        if (t.getResultList().isEmpty())
        {
            return false;
        }
        return true;
    }

    public List<GrlPessoa> findPessoaAdmissoes(GrlPessoa pessoa, /*String nomeCompleto,*/ Integer quantidadeRegistos, Date dataNascimentoInicial,
        Date dataNascimentoFinal, boolean flagPaciente, boolean flagFuncionario, boolean flagCandidato, boolean flagEstagiario, boolean flagDador)
    {
        System.out.println("ola");
        String query = constroiQueryPessoaAdmissoes(pessoa, /*nomeCompleto,*/ quantidadeRegistos, dataNascimentoInicial, dataNascimentoFinal, 
            flagPaciente, flagFuncionario, flagCandidato, flagEstagiario, flagDador);

        Query qry = em.createQuery(query);

        if (pessoa.getPkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoa", pessoa.getPkIdPessoa());
        }

//      if (!nomeCompleto.trim().isEmpty())
//          qry.setParameter("nome", "%"+nomeCompleto+"%");
        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            qry.setParameter("nome", pessoa.getNome() + "%");
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", pessoa.getNomeDoMeio() + "%");
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", pessoa.getSobreNome() + "%");
        }

        if (dataNascimentoInicial != null)
        {
            qry.setParameter("dataNascimentoInicial", dataNascimentoInicial);
        }

        if (dataNascimentoFinal != null)
        {
            qry.setParameter("dataNascimentoFinal", dataNascimentoFinal);
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
        {
            qry.setParameter("tipoDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
        {
            qry.setParameter("numeroDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() + "%");
        }

        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pessoa.getFkIdNacionalidade().getPkIdPais());
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pessoa.getFkIdSexo().getPkIdSexo());
        }

//      if (pessoa.getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
//          qry.setParameter("grupoSanguineo", pessoa.getFkIdGrupoSanguineo().getPkIdGrupoSanguineo());
        if (pessoa.getFkIdContacto().getTelefone1() != null && !pessoa.getFkIdContacto().getTelefone1().trim().isEmpty())
        {
            qry.setParameter("telefone", "%" + pessoa.getFkIdContacto().getTelefone1() + "%");
        }

        if (quantidadeRegistos != null && quantidadeRegistos > 0)
        {
            qry.setMaxResults(quantidadeRegistos);
        }

        System.out.println("terminou");
        List<GrlPessoa> pessoas = qry.getResultList();

        return pessoas;
    }

    public String constroiQueryPessoaAdmissoes(GrlPessoa pessoa, /*String nomeCompleto,*/ Integer quantidadeRegistos, Date dataNascimentoInicial, Date dataNascimentoFinal, 
        boolean flagPaciente, boolean flagFuncionario, boolean flagCandidato, boolean flagEstagiario, boolean flagDador)
    {
        boolean doc = false, docParentesis = false;
        String query = "SELECT p FROM GrlPessoa as p WHERE TRUE = TRUE";
        String dataAgendadaQuery = "";

        if (pessoa.getPkIdPessoa() != null)
        {
            query += " AND p.pkIdPessoa = :pkIdPessoa";
        }

        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            query += " AND (LOWER(p.nome) LIKE LOWER(:nome))";
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            query += " AND (LOWER(p.nomeDoMeio) LIKE LOWER(:nomeDoMeio))";
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            query += " AND (LOWER(p.sobreNome) LIKE LOWER(:sobreNome))";
        }

        if (dataNascimentoInicial != null)
        {
            dataAgendadaQuery += " AND (p.dataNascimento >= :dataNascimentoInicial";
            if (dataNascimentoFinal != null)
            {
                dataAgendadaQuery += " AND p.dataNascimento <= :dataNascimentoFinal)";
            }
            else
            {
                dataAgendadaQuery += ")";
            }
        }
        else if (dataNascimentoFinal != null)
        {
            dataAgendadaQuery += " AND (p.dataNascimento <= :dataNascimentoFinal)";
        }

        query += dataAgendadaQuery;

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
        {
            doc = true;
            query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
        }

        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
        {
            docParentesis = true;
            if (doc)
            {
                query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
            }
            else
            {
                query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
            }
        }

        if ((doc) && (!docParentesis))
        {
            query += ")";
        }

        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND p.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND p.fkIdSexo.pkIdSexo = :sexo";
        }

        if (pessoa.getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
        {
            query += " AND p.fkIdGrupoSanguineo.pkIdGrupoSanguineo = :grupoSanguineo";
        }

        if (pessoa.getFkIdContacto().getTelefone1() != null && !pessoa.getFkIdContacto().getTelefone1().trim().isEmpty())
        {
            query += " AND (p.fkIdContacto.telefone1 LIKE :telefone OR p.fkIdContacto.telefone2 LIKE :telefone)";
        }
        
        if (!flagPaciente)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT pac.fkIdPessoa.pkIdPessoa FROM AdmsPaciente pac)";
        }

        if (!flagFuncionario)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT f.fkIdPessoa.pkIdPessoa FROM RhFuncionario f)";
        }

        if (!flagCandidato)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT c.fkIdPessoa.pkIdPessoa FROM RhCandidato c)";
        }

        if (!flagEstagiario)
        {
            query += " AND p.pkIdPessoa NOT IN (SELECT e.fkIdPessoa.pkIdPessoa FROM RhEstagiario e)";
        }

//      if (!nomeCompleto.trim().isEmpty())
        query += " ORDER BY p.nome ASC";

        return query;
    }

}
