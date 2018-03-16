/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.FarmLoteProduto;
import entidade.FarmProduto;
import entidade.InterMedicacaoHasFarmProduto;
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
public class InterMedicacaoHasFarmProdutoFacade extends AbstractFacade<InterMedicacaoHasFarmProduto>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterMedicacaoHasFarmProdutoFacade()
    {
        super(InterMedicacaoHasFarmProduto.class);
    }

    public List<InterMedicacaoHasFarmProduto> pesquisarRegisto(String tipoServico, String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegistoPesq, Long pk_idRegisto, int viaAdmissao, int formaFarmaceutica, int pkIdMedicamento, String horario)
    {
        String query = constroiQueryRegisto(nomeEnfermeiro, nomeDoMeioEnfermeiro, sobreNomeEnfermeiro, dataRegistoPesq, viaAdmissao, formaFarmaceutica, pkIdMedicamento, horario, pk_idRegisto);

        TypedQuery<InterMedicacaoHasFarmProduto> t = em.createQuery(query, InterMedicacaoHasFarmProduto.class);

//        t.setParameter("nomeServico", tipoServico);  
        
        if (pk_idRegisto != null)   
           t.setParameter("pk_id_registo", pk_idRegisto);  
        
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeEnfermeiro+"%");
        
        if (nomeDoMeioEnfermeiro != null && !nomeDoMeioEnfermeiro.trim().isEmpty())    
           t.setParameter("nomeMeio", "%"+nomeDoMeioEnfermeiro+"%");
                
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomeEnfermeiro+"%");
        
        if (dataRegistoPesq != null)    
           t.setParameter("data", dataRegistoPesq);
        
        if (viaAdmissao > 0)    
           t.setParameter("viaAdmissao", viaAdmissao);
        
        if (formaFarmaceutica > 0)    
           t.setParameter("forma", formaFarmaceutica);
        
        if (pkIdMedicamento > 0)    
           t.setParameter("pkIdMedicamento", pkIdMedicamento);
        
        if (horario != null && !horario.trim().isEmpty())    
           t.setParameter("horario", horario);
                
        List<InterMedicacaoHasFarmProduto> resultado = t.getResultList();

        return resultado;
    }
   
   public String constroiQueryRegisto (String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegisto, int viaAdmissao, int formaFarmaceutica, int pkIdMedicamento, String horario, Long pk_idRegisto)
   {       
        String query = "SELECT o FROM InterMedicacaoHasFarmProduto o WHERE o.pkIdInterMedicacaoHasFarmProduto = o.pkIdInterMedicacaoHasFarmProduto";

        if (pk_idRegisto != null)   
           query += " AND o.fkIdMedicacao.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_id_registo";
                 
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())    
           query += " AND o.fkIdMedicacao.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                
        if (nomeDoMeioEnfermeiro != null && !nomeDoMeioEnfermeiro.trim().isEmpty())    
           query += " AND o.fkIdMedicacao.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nomeMeio"; 
                
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty()) 
           query += " AND o.fkIdMedicacao.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
         
        if (dataRegisto != null)     
           query += " AND o.fkIdMedicacao.dataHora =:data";
        
        if (pkIdMedicamento > 0)
           query += " AND o.fkIdProduto.pkIdProduto =:pkIdMedicamento"; 
                 
        if (viaAdmissao > 0)
           query += " AND o.fkIdProduto.fkIdViaAdministracao.pkIdViaAdministracao =:viaAdmissao";
             
        if (formaFarmaceutica > 0)
           query += " AND o.fkIdProduto.fkIdFormaFarmaceutica.pkIdFormaFarmaceutica =:forma";
       
        if (horario != null && !horario.trim().isEmpty())
           query += " AND o.fkIdHoraMedicacao.descricao =:horario";
        
        query += " ORDER BY o.fkIdProduto.descricao";
        
        return query;
   }
    
   public List<FarmProduto> produtosPorViaAdmissaoV1(String nomeVia1, String nomeVia2, int categoriaNivel1, int categoriaNivel2, int categoriaNivel3, int categoriaNivel4)
   {        
        Query qrs = em.createQuery("SELECT o FROM FarmProduto o WHERE o.fkIdViaAdministracao.descricao = :nomeVia1 OR o.fkIdViaAdministracao.descricao = :nomeVia2 AND (o.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento = :categoriaNivel1 OR o.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento = :categoriaNivel2 OR o.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento = :categoriaNivel3 OR o.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento = :categoriaNivel4) ORDER BY o.descricao");
        qrs.setParameter("nomeVia1",  nomeVia1).setParameter("nomeVia2",  nomeVia2).setParameter("categoriaNivel1",  categoriaNivel1).setParameter("categoriaNivel2",  categoriaNivel2).setParameter("categoriaNivel3",  categoriaNivel3).setParameter("categoriaNivel4",  categoriaNivel4);
        
        return qrs.getResultList();
   }
   
   public List<FarmProduto> produtosPorViaAdmissao(int viaAdmissao, int formaFarmaceutica, int categoria)
    {
        String query = constroiQueryFarmProduto(viaAdmissao, formaFarmaceutica, categoria);

        TypedQuery<FarmProduto> t = em.createQuery(query, FarmProduto.class);
        
        if (viaAdmissao > 0)    
           t.setParameter("viaAdmissao", viaAdmissao);
        
        if (formaFarmaceutica > 0)    
           t.setParameter("forma", formaFarmaceutica);
                
        if (categoria > 0)    
           t.setParameter("categoria", categoria);
        
        List<FarmProduto> resultado = t.getResultList();

        return resultado;
    }
   
   public String constroiQueryFarmProduto (int viaAdmissao, int formaFarmaceutica, int categoria)
   {       
        String query = "SELECT o FROM FarmProduto o WHERE o.pkIdProduto = o.pkIdProduto";        
        
        if (viaAdmissao > 0)
           query += " AND o.fkIdViaAdministracao.pkIdViaAdministracao =:viaAdmissao";
             
        if (formaFarmaceutica > 0)
           query += " AND o.fkIdFormaFarmaceutica.pkIdFormaFarmaceutica =:forma";
               
        if (categoria > 0)    
           query += " AND o.fkIdCategoriaMedicamento.pkIdCategoriaMedicamento =:categoria";
        
        query += " ORDER BY o.descricao";
        
        return query;
   }
   
//      public List<InterMedicacaoHasFarmProduto> teste(String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegisto, int pk_id_medicacao)
//    {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterMedicacaoHasFarmProduto o WHERE o.fkIdMedicacao.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico and o.fkIdMedicacao.fkIdRegistoInternamento.pkIdRegistoInternamento = :ns AND o.fkIdMedicacao.fkIdFuncionario.fkIdPessoa.nome = :np and o.fkIdMedicacao.dataHora = :dt AND o.fkIdMedicacao.pkIdMedicacao = md and o.fkIdProduto.fkIdProduto.fkIdViaAdministracao.descricao and o.fkIdProduto.fkIdProduto.descricao ");
//        qrs.setParameter("nomeServico", tipoServico).setParameter("paciente", pk_id_paciente).setParameter("data", dataSolicitacao);
//        
//        return qrs.getResultList();
//    }
}
