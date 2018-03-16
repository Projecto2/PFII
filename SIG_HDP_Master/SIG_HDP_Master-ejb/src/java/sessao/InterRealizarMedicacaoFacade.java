/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterRealizarMedicacao;
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
public class InterRealizarMedicacaoFacade extends AbstractFacade<InterRealizarMedicacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterRealizarMedicacaoFacade()
    {
        super(InterRealizarMedicacao.class);
    }
    
    public List<InterRealizarMedicacao> pesquisarRegisto(String tipoServico, String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegisto, Long pk_idRegisto, int hora, int fk_id_medicacao_farm_produto)
    {
        String query = constroiQueryRegisto(nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro, dataRegisto, pk_idRegisto, hora, fk_id_medicacao_farm_produto);

        TypedQuery<InterRealizarMedicacao> t = em.createQuery(query, InterRealizarMedicacao.class);

        t.setParameter("nomeServico", tipoServico);
       
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeEnfermeiro+"%");
        
        if (nomeDoMeioEnfermeiro != null && !nomeDoMeioEnfermeiro.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeDoMeioEnfermeiro+"%");
                
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomeEnfermeiro+"%");
        
        if (dataRegisto != null)    
           t.setParameter("data", dataRegisto);
        
        if (pk_idRegisto != null)   
           t.setParameter("pk_idRegisto", pk_idRegisto);
        
        if (hora > 0)   
           t.setParameter("hora", hora);
        
        if (fk_id_medicacao_farm_produto > 0)   
           t.setParameter("fk_id_medicacao_farm_produto", fk_id_medicacao_farm_produto);
                
        List<InterRealizarMedicacao> resultado = t.getResultList();

        return resultado;
    }
        
   public String constroiQueryRegisto (String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegisto, Long pk_idRegisto, int hora, int fk_id_medicacao_farm_produto)
   {       
        String query = "SELECT o FROM InterRealizarMedicacao o WHERE o.fkIdMedicacaoHasFarmProduto.fkIdMedicacao.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico";

        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())    
           query += " AND o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                
        if (nomeDoMeioEnfermeiro != null && !nomeDoMeioEnfermeiro.trim().isEmpty())    
           query += " AND o.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeMeio"; 
                
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty()) 
           query += " AND o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
         
        if (dataRegisto != null)     
           query += " AND o.dataRegistadaNoPaciente  =:data";
        
        if (pk_idRegisto != null)   
           query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdMedicacao.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_idRegisto";
        
        if (hora > 0)   
           query += " AND o.hora =:hora";
        
        if (fk_id_medicacao_farm_produto > 0)   
           query += " AND o.fkIdMedicacaoHasFarmProduto.pkIdInterMedicacaoHasFarmProduto =:fk_id_medicacao_farm_produto";
                
        query += " ORDER BY o.dataRegistadaNoPaciente  DESC";
        
        return query;
   }

   public List<InterRealizarMedicacao> pesquisarMedicacaoRealizada(String tipoServico, String medicamentoPesq, String viaPesq, String horarioPesq, String nomeEnfermeiro, Date dataRegistoPesq1, Date dataRegistoPesq2, int fkIdformaFarmaceutica, Long pkIdRegistoInternamento, int fkIdOpcaoMedicacao)
   {
        String query = constroiQueryMedicacaoRealizada(medicamentoPesq, viaPesq, horarioPesq, nomeEnfermeiro, dataRegistoPesq1, dataRegistoPesq2, fkIdformaFarmaceutica, pkIdRegistoInternamento, fkIdOpcaoMedicacao);

        TypedQuery<InterRealizarMedicacao> t = em.createQuery(query, InterRealizarMedicacao.class);

        t.setParameter("nomeServico", tipoServico);
       
        if (medicamentoPesq != null && !medicamentoPesq.trim().isEmpty())   
           t.setParameter("medicamento", "%"+medicamentoPesq+"%");
        
        if (viaPesq != null && !viaPesq.trim().isEmpty())   
           t.setParameter("via", viaPesq);
        
        if (horarioPesq != null && !horarioPesq.trim().isEmpty())   
           t.setParameter("hora", horarioPesq);
        
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeEnfermeiro+"%");
        
        if (dataRegistoPesq1 != null && dataRegistoPesq2 != null)    
           t.setParameter("data1", dataRegistoPesq1).setParameter("data2", dataRegistoPesq2);
        
        if (fkIdformaFarmaceutica > 0)
           t.setParameter("fkIdformaFarmaceutica", fkIdformaFarmaceutica);
            
        if (pkIdRegistoInternamento != null)   
           t.setParameter("pk_idRegisto", pkIdRegistoInternamento);
        
        if (fkIdOpcaoMedicacao > 0)
           t.setParameter("fkIdOpcaoMedicacao", fkIdOpcaoMedicacao);
        
        List<InterRealizarMedicacao> resultado = t.getResultList();

        return resultado;
   }

   private String constroiQueryMedicacaoRealizada(String medicamentoPesq, String viaPesq, String horarioPesq, String nomeEnfermeiro, Date dataRegistoPesq1, Date dataRegistoPesq2, int fkIdformaFarmaceutica, Long pkIdRegistoInternamento, int fkIdOpcaoMedicacao)
   {
        String query = "SELECT o FROM InterRealizarMedicacao o WHERE o.fkIdMedicacaoHasFarmProduto.fkIdMedicacao.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico";

        if (medicamentoPesq != null && !medicamentoPesq.trim().isEmpty())    
           query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdProduto.descricao LIKE :medicamento"; 
                
        if (viaPesq != null && !viaPesq.trim().isEmpty())    
           query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdProduto.fkIdViaAdministracao.descricao =:via"; 
                
        if (horarioPesq != null && !horarioPesq.trim().isEmpty())    
           query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdHoraMedicacao.descricao =:hora"; 
                
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())    
           query += " AND (o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome OR o.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nome OR o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :nome)";  
        
        if (dataRegistoPesq1 != null && dataRegistoPesq2 != null)       
            query += " AND o.dataRegistadaNoPaciente between :data1 AND :data2";
        
        if (fkIdformaFarmaceutica > 0)
            query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdProduto.fkIdFormaFarmaceutica.pkIdFormaFarmaceutica =:fkIdformaFarmaceutica";
            
        if (pkIdRegistoInternamento != null)   
           query += " AND o.fkIdMedicacaoHasFarmProduto.fkIdMedicacao.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_idRegisto";
        
        if (fkIdOpcaoMedicacao > 0)
           query += " AND o.fkIdOpcaoMedicacao.pkIdOpcaoMedicacao =:fkIdOpcaoMedicacao";

        query += " ORDER BY o.dataHora DESC";
        
        return query;    
    }
   
//         public List<InterRealizarMedicacao> teste(String tipoServico, String nomeEnfermeiro, Date dataRegisto)
//    {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterRealizarMedicacao o WHERE o.fkIdOpcaoMedicacao.pkIdOpcaoMedicacao");
//        
//        return qrs.getResultList();
//    }
}
