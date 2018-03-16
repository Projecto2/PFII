/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsPaciente;
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
public class AdmsPacienteFacade extends AbstractFacade<AdmsPaciente>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsPacienteFacade()
    {
        super(AdmsPaciente.class);
    }
    
  public List<AdmsPaciente> findPaciente(AdmsPaciente paciente, /*String nomeCompleto,*/ Integer quantidadeRegistos, Date dataNascimentoInicial,
      Date dataNascimentoFinal)
  {
      System.out.println("ola");
      String query = constroiQuery(paciente, /*nomeCompleto,*/ quantidadeRegistos, dataNascimentoInicial, dataNascimentoFinal);

      Query qry = em.createQuery(query);
      
      if (paciente.getNumeroPaciente() != null && !paciente.getNumeroPaciente().trim().isEmpty())
          qry.setParameter("numeroPaciente", "%"+paciente.getNumeroPaciente()+"%");
      
//      if (!nomeCompleto.trim().isEmpty())
//          qry.setParameter("nome", "%"+nomeCompleto+"%");
      
      if (paciente.getFkIdPessoa().getNome() != null && !paciente.getFkIdPessoa().getNome().trim().isEmpty())
          qry.setParameter("nome", paciente.getFkIdPessoa().getNome()+"%");
      
      if (paciente.getFkIdPessoa().getNomeDoMeio() != null && !paciente.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          qry.setParameter("nomeDoMeio", paciente.getFkIdPessoa().getNomeDoMeio()+"%");
      
      if (paciente.getFkIdPessoa().getSobreNome() != null && !paciente.getFkIdPessoa().getSobreNome().trim().isEmpty())
          qry.setParameter("sobreNome", paciente.getFkIdPessoa().getSobreNome()+"%");
      
      if (dataNascimentoInicial != null)
          qry.setParameter("dataNascimentoInicial", dataNascimentoInicial);
      
      if (dataNascimentoFinal != null)
          qry.setParameter("dataNascimentoFinal", dataNascimentoFinal);

      if (paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao()!= null)
          qry.setParameter("tipoDocumento", paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao());

      if (paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && ! paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
          qry.setParameter("numeroDocumento", paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento()+"%");

      if (paciente.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
          qry.setParameter("nacionalidade", paciente.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais());

      if (paciente.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
          qry.setParameter("sexo", paciente.getFkIdPessoa().getFkIdSexo().getPkIdSexo());
      
      if (paciente.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
          qry.setParameter("grupoSanguineo", paciente.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo());
      
      if (paciente.getFkIdPessoa().getFkIdContacto().getTelefone1() != null && !paciente.getFkIdPessoa().getFkIdContacto().getTelefone1().trim().isEmpty())
          qry.setParameter("telefone", "%"+paciente.getFkIdPessoa().getFkIdContacto().getTelefone1()+"%");
      
      if (quantidadeRegistos != null && quantidadeRegistos > 0)
          qry.setMaxResults(quantidadeRegistos);
      
      
      List<AdmsPaciente> pacientes = qry.getResultList();

      return pacientes;
  }
  
  
  public String constroiQuery(AdmsPaciente paciente, /*String nomeCompleto,*/ Integer quantidadeRegistos, Date dataNascimentoInicial, Date dataNascimentoFinal)
  {        
      boolean doc = false, docParentesis = false;
      String query = "SELECT p FROM AdmsPaciente as p WHERE TRUE = TRUE";
      String dataAgendadaQuery = "";
//      String query = "SELECT p FROM AdmsPaciente as p WHERE true = true";
      
      if (paciente.getNumeroPaciente() != null && !paciente.getNumeroPaciente().trim().isEmpty())
         query += " AND LOWER(p.numeroPaciente) LIKE LOWER(:numeroPaciente)";
      
//      if (paciente.getFkIdPessoa().getNome() != null && !paciente.getFkIdPessoa().getNome().trim().isEmpty())
//         query += " AND p.fkIdPessoa.nome LIKE :nome";
      
      if (paciente.getFkIdPessoa().getNome() != null && !paciente.getFkIdPessoa().getNome().trim().isEmpty())
         query += " AND (LOWER(p.fkIdPessoa.nome) LIKE LOWER(:nome))";
      
      if (paciente.getFkIdPessoa().getNomeDoMeio() != null && !paciente.getFkIdPessoa().getNomeDoMeio().trim().isEmpty())
          query += " AND (LOWER(p.fkIdPessoa.nomeDoMeio) LIKE LOWER(:nomeDoMeio))";
//         query += " AND p.fkIdPessoa.nomeDoMeio LIKE :nomeDoMeio";
      
      if (paciente.getFkIdPessoa().getSobreNome() != null && !paciente.getFkIdPessoa().getSobreNome().trim().isEmpty())
          query += " AND (LOWER(p.fkIdPessoa.sobreNome) LIKE LOWER(:sobreNome))";
//         query += " AND p.fkIdPessoa.sobreNome LIKE :sobreNome";
      
      if(dataNascimentoInicial != null)
      {
          dataAgendadaQuery += " AND (p.fkIdPessoa.dataNascimento >= :dataNascimentoInicial";
          if(dataNascimentoFinal != null)
              dataAgendadaQuery += " AND p.fkIdPessoa.dataNascimento <= :dataNascimentoFinal)";
          else dataAgendadaQuery += ")";
      }
      else if(dataNascimentoFinal != null)
      {
          dataAgendadaQuery += " AND (p.fkIdPessoa.dataNascimento <= :dataNascimentoFinal)";
      }
      
      query += dataAgendadaQuery;

//      if (!nomeCompleto.trim().isEmpty())
//         query += " AND ( (LOWER(CONCAT(p.fkIdPessoa.nome, ' ', p.fkIdPessoa.nomeDoMeio,' ', p.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) "
//             + "OR (LOWER(CONCAT(p.fkIdPessoa.nome, ' ', p.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) )";

      if (paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getFkTipoDocumentoIdentificacao().getPkTipoDocumentoIdentificacao() != null)
      {
          doc = true;
          query += " AND p.fkIdPessoa.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.fkTipoDocumentoIdentificacao.pkTipoDocumentoIdentificacao = :tipoDocumento";
      }

      if (paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento() != null && !paciente.getFkIdPessoa().getGrlDocumentoIdentificacaoList().get(0).getNumeroDocumento().trim().isEmpty())
      {
          docParentesis = true;
          if (doc)
          {
              query += " AND doc.numeroDocumento LIKE :numeroDocumento)";
          }
          else
          {
              query += " AND p.fkIdPessoa.pkIdPessoa IN (SELECT doc.fkIdPessoa.pkIdPessoa FROM GrlDocumentoIdentificacao doc WHERE doc.numeroDocumento LIKE :numeroDocumento)";
          }
      }

      if ((doc) && (!docParentesis)) query += ")";

      if (paciente.getFkIdPessoa().getFkIdNacionalidade().getPkIdPais() != null)
         query += " AND p.fkIdPessoa.fkIdNacionalidade.pkIdPais = :nacionalidade";
      
      if (paciente.getFkIdPessoa().getFkIdSexo().getPkIdSexo() != null)
         query += " AND p.fkIdPessoa.fkIdSexo.pkIdSexo = :sexo";
      
      if (paciente.getFkIdPessoa().getFkIdGrupoSanguineo().getPkIdGrupoSanguineo() != null)
         query += " AND p.fkIdPessoa.fkIdGrupoSanguineo.pkIdGrupoSanguineo = :grupoSanguineo";
      
      if (paciente.getFkIdPessoa().getFkIdContacto().getTelefone1() != null && !paciente.getFkIdPessoa().getFkIdContacto().getTelefone1().trim().isEmpty())
          query += " AND (p.fkIdPessoa.fkIdContacto.telefone1 LIKE :telefone OR p.fkIdPessoa.fkIdContacto.telefone2 LIKE :telefone)";
          
//      if (!nomeCompleto.trim().isEmpty())
      query += " ORDER BY p.pkIdPaciente, p.fkIdPessoa.nome asc";
      

      return query;
  }
  
    public AdmsPaciente findPacienteByNumeroPaciente(String numeroPaciente)
    {
        System.out.println("ola");
        Query query = em.createQuery("SELECT DISTINCT paciente FROM AdmsPaciente paciente WHERE paciente.numeroPaciente = :numero ORDER BY paciente.pkIdPaciente DESC");
        
        query.setParameter("numero", numeroPaciente);
        
        query.setMaxResults(1);
        
        List<AdmsPaciente> paciente = query.getResultList();
        if(paciente.isEmpty()) return null;
        return paciente.get(0);
    }
    
}
