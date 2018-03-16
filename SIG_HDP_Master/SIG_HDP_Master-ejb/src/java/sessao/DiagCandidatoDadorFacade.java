/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AdmsPaciente;
import entidade.DiagCandidatoDador;
import entidade.GrlInstituicao;
import entidade.GrlPais;
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
 * @author mauro
 */
@Stateless
public class DiagCandidatoDadorFacade extends AbstractFacade<DiagCandidatoDador>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagCandidatoDadorFacade()
    {
        super(DiagCandidatoDador.class);
    }

    public List<GrlPais> findAllPais()
    {
        TypedQuery typedQuery = em.createQuery("SELECT g FROM GrlPais g ORDER BY g.nacionalidade", GrlInstituicao.class);
        return typedQuery.getResultList();
    }
    
    public List<GrlInstituicao> findAllInstituicoes()
    {
        TypedQuery typedQuery = em.createQuery("SELECT g FROM GrlInstituicao g ORDER BY g.descricao", GrlInstituicao.class);
        return typedQuery.getResultList();
    }
    
    public List<DiagCandidatoDador> findCandidatoDadorByPessoa(GrlPessoa pessoa)
    {
        TypedQuery typedQuery = em.createQuery("SELECT d FROM DiagCandidatoDador d WHERE d.fkIdPessoa.pkIdPessoa = :idPessoa", DiagCandidatoDador.class).setParameter("idPessoa", pessoa.getPkIdPessoa());

        return typedQuery.getResultList();
    }

    //Pegar os candidatos de forma exclusiva, significa pegar as pessoas que já foram candidatos
    public List<DiagCandidatoDador> findPessoaCandidato(DiagCandidatoDador pessoa, Date dataInicioCadastro, Date dataFimCadastro, int numeroRegistos)
    {
        String query = constroiQuery(pessoa, dataInicioCadastro, dataFimCadastro);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (pessoa.getPkIdCandidatoDador() != null)
        {
            qry.setParameter("pkIdDador", pessoa.getPkIdCandidatoDador());
        }

        if (pessoa.getCodigoCandidatoDador() != null && !pessoa.getCodigoCandidatoDador().trim().isEmpty())
        {
            qry.setParameter("codDador", pessoa.getCodigoCandidatoDador());
        }

        if (pessoa.getFkIdPessoa().getNome() != null && !pessoa.getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", pessoa.getFkIdPessoa().getNome() + "%");
        }

        if (pessoa.getFkIdPessoa().getNome() != null && !pessoa.getFkIdPessoa().getNome().trim().isEmpty())
        {
            qry.setParameter("nome", pessoa.getFkIdPessoa().getNome() + "%");
        }

        if (pessoa.getFkIdPessoa().getNomeDoMeio() != null && !pessoa.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            qry.setParameter("nomeDoMeio", pessoa.getFkIdPessoa().getNomeDoMeio() + "%");
        }

        if (pessoa.getFkIdPessoa().getSobreNome() != null && !pessoa.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            qry.setParameter("sobreNome", pessoa.getFkIdPessoa().getSobreNome() + "%");
        }

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//        {
//            qry.setParameter("tipoDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());
//        }
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//        {
//            qry.setParameter("numeroDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() + "%");
//        }
        if (pessoa.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pessoa.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());
        }

        if (pessoa.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pessoa.getFkIdPessoa().getFkIdSexo().getPkIdSexo());
        }

        if (pessoa.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
        {
            qry.setParameter("grupo", pessoa.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo());
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
        List<DiagCandidatoDador> pessoas = qry.getResultList();

        return pessoas;
    }

    public String constroiQuery(DiagCandidatoDador pessoa, Date dataInicioCadastro, Date dataFimCadastro)
    {
        boolean doc = false, docParentesis = false;

        String query = "SELECT d FROM DiagCandidatoDador d WHERE :pesquisar = :pesquisar";

        if (pessoa.getFkIdPessoa().getPkIdPessoa() != null)
        {
            query += " AND d.pkIdCandidatoDador = :pkIdDador";
        }

        if (pessoa.getCodigoCandidatoDador() != null && !pessoa.getCodigoCandidatoDador().trim().isEmpty())
        {
            query += " AND d.codigoCandidatoDador LIKE :codDador";
        }

        if (pessoa.getFkIdPessoa().getNome() != null && !pessoa.getFkIdPessoa().getNome().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.nome LIKE :nome";
        }

        if (pessoa.getFkIdPessoa().getNomeDoMeio() != null && !pessoa.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (pessoa.getFkIdPessoa().getSobreNome() != null && !pessoa.getFkIdPessoa().getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//        {
//            doc = true;
//            query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
//        }
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//        {
//            docParentesis = true;
//            if (doc) query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
//            else   query += " AND d.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
//        }
//        
//        if((doc) && (!docParentesis)) query += ")";
        if (pessoa.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND d.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pessoa.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND d.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        if (pessoa.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
        {
            query += " AND d.fkIdPessoa.fkIdGrupoSanguineo.pkIdGrupoSanguineo = :grupo";
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

        query += " ORDER BY d.codigoCandidatoDador";

        return query;
    }

    //Pegar os candidatos que possuem bolsas de sangue e cuja compatibilidade nao foi testada com este paciente
    public List<DiagCandidatoDador> findCandidatoTesteCompatibilidade(GrlPessoa pessoa, AdmsPaciente paciente)
    {
        String query = constroiQueryTesteCompatibilidade(pessoa, paciente);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (paciente.getFkIdPessoa() != null)
        {
            qry.setParameter("pkIdPessoaPaciente", paciente.getFkIdPessoa().getPkIdPessoa());
        }

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

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//        {
//            qry.setParameter("tipoDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());
//        }
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//        {
//            qry.setParameter("numeroDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() + "%");
//        }
        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pessoa.getFkIdNacionalidade().getPkIdPais());
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pessoa.getFkIdSexo().getPkIdSexo());
        }

        List<DiagCandidatoDador> candidatos = qry.getResultList();

        return candidatos;
    }

    public String constroiQueryTesteCompatibilidade(GrlPessoa pessoa, AdmsPaciente paciente)
    {
        boolean doc = false, docParentesis = false;

        String query = "SELECT d FROM DiagCandidatoDador d WHERE :pesquisar = :pesquisar";

        if (pessoa.getPkIdPessoa() != null)
        {
            query += " AND d.fkIdPessoa.pkIdPessoa = :pkIdPessoa";
        }

        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.nome LIKE :nome";
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            query += " AND d.fkIdPessoa.sobreNome LIKE :sobreNome";
        }

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//        {
//            doc = true;
//            query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
//        }
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//        {
//            docParentesis = true;
//            if (doc) query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
//            else   query += " AND d.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
//        }
//        
//        if((doc) && (!docParentesis)) query += ")";
        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND d.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND d.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " AND d.fkIdPessoa.pkIdPessoa <> :pkIdPessoaPaciente AND "
                + "d.pkIdCandidatoDador IN (SELECT dd.fkIdCandidatoDador.pkIdCandidatoDador FROM DiagTipagemDador dd) AND "
                + "d.pkIdCandidatoDador IN (SELECT ddd.fkIdCandidatoDador.pkIdCandidatoDador FROM DiagBolsaSangue ddd) AND "
                + "d.pkIdCandidatoDador NOT IN (SELECT dddd.fkIdBolsaSangue.fkIdCandidatoDador.pkIdCandidatoDador FROM DiagTesteCompatibilidade dddd "
                + "WHERE dddd.fkIdPaciente.fkIdPessoa.pkIdPessoa = :pkIdPessoaPaciente) ORDER BY d.fkIdPessoa.nome";

        return query;
    }

    public List<GrlPessoa> findPessoaNaoCandidatos(GrlPessoa pessoa, int numeroRegistos)
    {
        String query = constroiQueryPesquisaPessoaNaoCandidato(pessoa);

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

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//            qry.setParameter("tipoDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//            qry.setParameter("numeroDocumento", pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() + "%");
        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            qry.setParameter("nacionalidade", pessoa.getFkIdNacionalidade().getPkIdPais());
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            qry.setParameter("sexo", pessoa.getFkIdSexo().getPkIdSexo());
        }
        
        qry.setMaxResults(numeroRegistos);
        List<GrlPessoa> pessoas = qry.getResultList();

        return pessoas;
    }

    public String constroiQueryPesquisaPessoaNaoCandidato(GrlPessoa pessoa)
    {
        boolean doc = false, docParentesis = false;
        String query = "SELECT p FROM GrlPessoa p WHERE :pesquisar = :pesquisar";

        if (pessoa.getPkIdPessoa() != null)
        {
            query += " AND p.pkIdPessoa = :pkIdPessoa";
        }

        if (pessoa.getNome() != null && !pessoa.getNome().trim().isEmpty())
        {
            query += " AND p.nome LIKE :nome";
        }

        if (pessoa.getNomeDoMeio() != null && !pessoa.getNomeDoMeio().trim().isEmpty())
        {
            query += " AND p.nomeDoMeio LIKE :nomeDoMeio";
        }

        if (pessoa.getSobreNome() != null && !pessoa.getSobreNome().trim().isEmpty())
        {
            query += " AND p.sobreNome LIKE :sobreNome";
        }

//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
//        {
//            doc = true;
//            query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
//        }
//
//        if (pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !pessoa.getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
//        {
//            docParentesis = true;
//            if (doc) query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
//            else   query += " AND p.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
//        }
//        
//        if((doc) && (!docParentesis)) query += ")";
        if (pessoa.getFkIdNacionalidade().getPkIdPais() != null)
        {
            query += " AND p.fkIdNacionalidade.pkIdPais = :nacionalidade";
        }

        if (pessoa.getFkIdSexo().getPkIdSexo() != null)
        {
            query += " AND p.fkIdSexo.pkIdSexo = :sexo";
        }

        query += " AND p.pkIdPessoa NOT IN (SELECT d.fkIdPessoa.pkIdPessoa FROM DiagCandidatoDador d) ORDER BY p.nome, p.nomeDoMeio, p.sobreNome";

        return query;
    }
}
