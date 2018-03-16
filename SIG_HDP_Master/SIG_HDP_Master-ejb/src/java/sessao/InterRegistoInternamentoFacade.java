/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsAgendamento;
import entidade.AdmsServico;
import entidade.AdmsServicoSolicitado;
import entidade.InterRegistoInternamento;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author armindo
 */
@Stateless
public class InterRegistoInternamentoFacade extends AbstractFacade<InterRegistoInternamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterRegistoInternamentoFacade()
    {
        super(InterRegistoInternamento.class);
    }
    
    public List<AdmsServicoSolicitado> pesquisarServicosSolicitados (String tipoServico, String numeroProcesso, String nome, String nomeMeioPaciente, String sobreNomePaciente, String sexo, Date dataSolicitacao1, Date dataSolicitacao2, String areaSolicitante, String estado)
    {
        String query = constroiQuery(numeroProcesso, nome, nomeMeioPaciente, sobreNomePaciente, sexo, dataSolicitacao1, dataSolicitacao2, areaSolicitante, estado);

        TypedQuery<AdmsServicoSolicitado> t = em.createQuery(query, AdmsServicoSolicitado.class);

        t.setParameter("nomeServico", tipoServico);  
          
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           t.setParameter("numeroPaciente", numeroProcesso);
        
        if (nome != null && !nome.trim().isEmpty())   
           t.setParameter("nome", "%"+nome+"%");
        
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeMeioPaciente+"%");
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomePaciente+"%");
        
        if (sexo != null && !sexo.trim().isEmpty())    
           t.setParameter("sexo", sexo); 
        
        if (areaSolicitante != null && !areaSolicitante.trim().isEmpty())    
           t.setParameter("area", areaSolicitante); 
        
        if (estado != null && !estado.trim().isEmpty())    
           t.setParameter("estado", estado); 

        if (dataSolicitacao1 != null && dataSolicitacao2 != null)    
           t.setParameter("data1", dataSolicitacao1).setParameter("data2", dataSolicitacao2);
        
        List<AdmsServicoSolicitado> resultado = t.getResultList();

        return resultado;
   }

   public String constroiQuery (String numeroProcesso, String nome, String nomeMeioPaciente, String sobreNomePaciente, String sexo, Date dataSolicitacao1, Date dataSolicitacao2, String areaSolicitante, String estado)
   {       
        String query = "SELECT o FROM AdmsServicoSolicitado o WHERE o.fkIdServico.nomeServico = :nomeServico";

        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           query += " AND o.fkIdSolicitacao.fkIdPaciente.numeroPaciente = :numeroPaciente";
        
        if (nome != null && !nome.trim().isEmpty())    
           query += " AND ( (LOWER(CONCAT(o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, ' ', o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio,' ', o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) "
             + "OR (LOWER(CONCAT(o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, ' ', o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) )";
               
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           query += " AND o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeMeio";
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty()) 
           query += " AND o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
                        
        if (sexo != null && !sexo.trim().isEmpty())    
           query += " AND o.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.descricao = :sexo";

        if (areaSolicitante != null && !areaSolicitante.trim().isEmpty())    
           query += " AND o.fkIdSolicitacao.fkIdFuncionarioSolicitante.fkIdSeccaoTrabalho.descricao = :area";
        
        if (estado != null && !estado.trim().isEmpty())    
           query += " AND o.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado = :estado";
        
        if (dataSolicitacao1 != null && dataSolicitacao2 != null)
           query += " AND o.fkIdSolicitacao.data between :data1 AND :data2";

        query += " ORDER BY o.fkIdSolicitacao.data DESC";

        return query;
   }
   
   public List<AdmsAgendamento> findAllAgendamentosParaInternamento (String tipoServico, String numeroProcesso, String nome, String nomeMeioPaciente, String sobreNomePaciente, String sexo, Date dataSolicitacao1, Date dataSolicitacao2, String areaSolicitante, String estado)
   {
        String query = constroiQueryAgendamentos(numeroProcesso, nome, nomeMeioPaciente, sobreNomePaciente, sexo, dataSolicitacao1, dataSolicitacao2, areaSolicitante, estado);

        TypedQuery<AdmsAgendamento> t = em.createQuery(query, AdmsAgendamento.class);

        t.setParameter("nomeServico", tipoServico).setParameter("estado", "Chegou");  
          
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           t.setParameter("numeroPaciente", numeroProcesso);
        
        if (nome != null && !nome.trim().isEmpty())   
           t.setParameter("nome", "%"+nome+"%");
        
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeMeioPaciente+"%");
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomePaciente+"%");
        
        if (sexo != null && !sexo.trim().isEmpty())    
           t.setParameter("sexo", sexo); 
        
        if (areaSolicitante != null && !areaSolicitante.trim().isEmpty())    
           t.setParameter("area", areaSolicitante); 
        
        if (estado != null && !estado.trim().isEmpty())    
           t.setParameter("estado", estado); 

        if (dataSolicitacao1 != null && dataSolicitacao2 != null)    
           t.setParameter("data1", dataSolicitacao1).setParameter("data2", dataSolicitacao2);
        
        List<AdmsAgendamento> resultado = t.getResultList();

        return resultado;
   }

   public String constroiQueryAgendamentos (String numeroProcesso, String nome, String nomeMeioPaciente, String sobreNomePaciente, String sexo, Date dataSolicitacao1, Date dataSolicitacao2, String areaSolicitante, String estado)
   {       
        String query = "SELECT o FROM AdmsAgendamento o WHERE o.fkIdServicoSolicitado.fkIdServico.nomeServico = :nomeServico AND o.fkIdEstadoAgendamento.descricaoEstadoAgendamento = :estado";

        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente = :numeroPaciente";
        
        if (nome != null && !nome.trim().isEmpty())    
           query += " AND ( (LOWER(CONCAT(o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, ' ', o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio,' ', o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) "
             + "OR (LOWER(CONCAT(o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome, ' ', o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome)) LIKE LOWER(:nome)) )";
               
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeMeio";
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty()) 
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
                        
        if (sexo != null && !sexo.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.descricao = :sexo";

        if (areaSolicitante != null && !areaSolicitante.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdFuncionarioSolicitante.fkIdSeccaoTrabalho.descricao = :area";
        
        if (estado != null && !estado.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdClassificacaoServicoSolicitado.descricaoClassificacaoServicoSolicitado = :estado";
        
        if (dataSolicitacao1 != null && dataSolicitacao2 != null)
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.data between :data1 AND :data2";

        query += " ORDER BY o.fkIdServicoSolicitado.fkIdSolicitacao.data DESC";

        return query;
   }
    
   public Integer getServicoPorNome(String tipoServicoInter)
   {
       TypedQuery<AdmsServico> query = em.createNamedQuery("AdmsServico.findByNomeServico",
                AdmsServico.class)
                .setParameter("nomeServico", tipoServicoInter);
        
        List<AdmsServico> results = query.getResultList();
        
        if (results.isEmpty())
            return null;
        
        return results.get(0).getPkIdServico(); 
    }
    
    public List<AdmsServicoSolicitado> listarServicosSolicitados(String tipoServico)
    {
        Query qrs = em.createQuery("SELECT o FROM AdmsServicoSolicitado o WHERE o.fkIdServico.nomeServico = :nomeServico");
        qrs.setParameter("nomeServico", tipoServico);
        
        return qrs.getResultList();
    }

    public List<AdmsServicoSolicitado> pesquisarServicosSolicitadosVersao1(String tipoServico, Long pk_id_paciente, Date dataSolicitacao)
    {
        Query qrs = em.createQuery("SELECT o FROM AdmsServicoSolicitado o WHERE o.fkIdServico.nomeServico = :nomeServico AND (o.fkIdSolicitacao.fkIdPaciente.pkIdPaciente = :paciente or o.fkIdSolicitacao.data = :data)");
        qrs.setParameter("nomeServico", tipoServico).setParameter("paciente", pk_id_paciente).setParameter("data", dataSolicitacao);
        
        return qrs.getResultList();
    }

    public AdmsServicoSolicitado carregarSolicitacao(Long pk_id)
    {
        Query qrs = em.createQuery("SELECT o FROM AdmsServicoSolicitado o WHERE o.pkIdServicoSolicitado = : pk_id");
        qrs.setParameter("pk_id", pk_id);
        
        List<AdmsServicoSolicitado> lista = qrs.getResultList();
        
        return lista.get(0);    
    }
    
    public List<InterRegistoInternamento> pesquisarRegisto(Long pk_id_paciente, String tipoServico, String numeroProcesso, String numeroIncricao, String nomePaciente, String nomeMeioPaciente, String sobreNomePaciente, String sexo, int enfermaria, int sala, int cama, Long pkServicoSocilicitado, Date dataInternamento1, Date dataInternamento2)
    {
        String query = constroiQueryRegisto(pk_id_paciente, numeroProcesso, pkServicoSocilicitado, numeroIncricao, nomePaciente, nomeMeioPaciente, sobreNomePaciente, sexo, enfermaria, sala, cama, dataInternamento1, dataInternamento2);

        TypedQuery<InterRegistoInternamento> t = em.createQuery(query, InterRegistoInternamento.class);

        t.setParameter("nomeServico", tipoServico);  
          
        if (pk_id_paciente != null)    
           t.setParameter("fkPaciente", pk_id_paciente);
        
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           t.setParameter("paciente", numeroProcesso);
        
        if (pkServicoSocilicitado != null)    
           t.setParameter("pkServicoSocilicitado", pkServicoSocilicitado);
        
        if (numeroIncricao != null && !numeroIncricao.trim().isEmpty())    
           t.setParameter("numeroInscricao", numeroIncricao);
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty())   
           t.setParameter("nome", "%"+nomePaciente+"%");
        
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeMeioPaciente+"%");
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomePaciente+"%");
                
        if (sexo != null && !sexo.trim().isEmpty())    
           t.setParameter("sexo", sexo);
               
        if (enfermaria > 0)    
           t.setParameter("enfermaria", enfermaria);
        
        if (sala > 0)    
           t.setParameter("sala", sala);
        
        if (cama > 0)    
           t.setParameter("cama", cama);
        
        if (dataInternamento1 != null && dataInternamento2 != null)    
           t.setParameter("data1", dataInternamento1).setParameter("data2", dataInternamento2);
        
        List<InterRegistoInternamento> resultado = t.getResultList();

        return resultado;
   }
   
   public String constroiQueryRegisto (Long pk_id_paciente, String numeroProcesso, Long pkServicoSocilicitado, String numeroIncricao, String nomePaciente, String nomeMeioPaciente, String sobreNomePaciente, String sexo, int enfermaria, int sala, int cama, Date dataInternamento1, Date dataInternamento2)
   {       
        String query = "SELECT o FROM InterRegistoInternamento o WHERE o.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico AND o.ativo = true";

        if (pk_id_paciente != null)    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.pkIdPaciente =:fkPaciente";
                
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente =:paciente";
        
        if (pkServicoSocilicitado != null)         
            query += " AND o.fkIdServicoSolicitado.pkIdServicoSolicitado =:pkServicoSocilicitado";
        
        if (numeroIncricao != null && !numeroIncricao.trim().isEmpty())  
           query += " AND o.fkIdInscricaoInternamento.numeroInscricao =:numeroInscricao";
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
                
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeMeio";
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty()) 
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
                
        if (sexo != null && !sexo.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.descricao =:sexo";
        
        if (enfermaria > 0)        
           query += " AND o.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.pkIdEnfermaria =:enfermaria";
        
        if (sala > 0)        
           query += " AND o.fkIdCamaInternamento.fkIdSalaInternamento.pkIdSalaInternamento =:sala";
        
        if (cama > 0)        
           query += " AND o.fkIdCamaInternamento.pkIdCamaInternamento =:cama";           
        
        if (dataInternamento1 != null && dataInternamento2 != null)    
            query += " AND o.dataInternamento between :data1 AND :data2";
        
        query += " ORDER BY o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome";
        
        return query;
   }
   
   public List<InterRegistoInternamento> pesquisarRegistoArquivados(Long pk_id_paciente, String tipoServico, String numeroProcesso, String numeroIncricao, String nomePaciente, String nomeMeioPaciente, String sobreNomePaciente, String sexo, int sala, int cama, Long pkServicoSocilicitado)
   {
        String query = constroiQueryRegistoArquivado(pk_id_paciente, numeroProcesso, pkServicoSocilicitado, numeroIncricao, nomePaciente, nomeMeioPaciente, sobreNomePaciente, sexo, sala, cama);

        TypedQuery<InterRegistoInternamento> t = em.createQuery(query, InterRegistoInternamento.class);

        t.setParameter("nomeServico", tipoServico);  
          
        if (pk_id_paciente != null)    
           t.setParameter("fkPaciente", pk_id_paciente);
        
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           t.setParameter("paciente", numeroProcesso);
        
        if (pkServicoSocilicitado != null)    
           t.setParameter("pkServicoSocilicitado", pkServicoSocilicitado);
        
        if (numeroIncricao != null && !numeroIncricao.trim().isEmpty())    
           t.setParameter("numeroInscricao", numeroIncricao);
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty())   
           t.setParameter("nome", "%"+nomePaciente+"%");
        
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeMeioPaciente+"%");
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomePaciente+"%");
                
        if (sexo != null && !sexo.trim().isEmpty())    
           t.setParameter("sexo", sexo);
               
        if (sala > 0)    
           t.setParameter("sala", sala);
        
        if (cama > 0)    
           t.setParameter("cama", cama); 
       
        List<InterRegistoInternamento> resultado = t.getResultList();

        return resultado;
   }
   
   public String constroiQueryRegistoArquivado (Long pk_id_paciente, String numeroProcesso, Long pkServicoSocilicitado, String numeroIncricao, String nomePaciente, String nomeMeioPaciente, String sobreNomePaciente, String sexo, int sala, int cama)
   {       
        String query = "SELECT o FROM InterRegistoInternamento o WHERE o.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico AND o.ativo = false";

        if (pk_id_paciente != null)    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.pkIdPaciente =:fkPaciente";
                
        if (numeroProcesso != null && !numeroProcesso.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.numeroPaciente =:paciente";
        
        if (pkServicoSocilicitado != null)         
            query += " AND o.fkIdServicoSolicitado.pkIdServicoSolicitado =:pkServicoSocilicitado";
        
        if (numeroIncricao != null && !numeroIncricao.trim().isEmpty())  
           query += " AND o.fkIdInscricaoInternamento.numeroInscricao =:numeroInscricao";
        
        if (nomePaciente != null && !nomePaciente.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome LIKE :nome";
                
        if (nomeMeioPaciente != null && !nomeMeioPaciente.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio LIKE :nomeMeio";
                
        if (sobreNomePaciente != null && !sobreNomePaciente.trim().isEmpty()) 
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome LIKE :sobreNome";
                
        if (sexo != null && !sexo.trim().isEmpty())    
           query += " AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.descricao =:sexo";
        
        if (sala > 0)        
           query += " AND o.fkIdCamaInternamento.fkIdSalaInternamento.pkIdSalaInternamento =:sala";
        
        if (cama > 0)        
           query += " AND o.fkIdCamaInternamento.pkIdCamaInternamento =:cama";
        
        query += " ORDER BY o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome";
        
        return query;
   }
   
//   public List<InterRegistoInternamento> Teste(String tipoServico, Long pk_id_paciente, Date dataSolicitacao)
//    {
//        Query qrs = em.createQuery("SELECT o FROM InterRegistoInternamento o WHERE o.dataRegisto o.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.pkIdEnfermaria and o.fkIdServicoSolicitado.pkIdServicoSolicitado = :pk AND o.fkIdInscricaoInternamento.numeroInscricao AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nome = n AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.nomeDoMeio = nM AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.sobreNome = sN AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.fkIdPessoa.fkIdSexo.descricao = s AND o.dataRegisto and o.fkIdCamaInternamento.fkIdSalaInternamento.pkIdSalaInternamento and o.fkIdCamaInternamento.pkIdCamaInternamento AND o.fkIdServicoSolicitado.fkIdSolicitacao.fkIdPaciente.pkIdPaciente =: paciente AND o.fkIdDoencaPrincipal.descricao AND o.dataInternamento = d");
//        qrs.setParameter("nomeServico", tipoServico).setParameter("paciente", pk_id_paciente).setParameter("data", dataSolicitacao);
//        
//        return qrs.getResultList();
//   }
}
