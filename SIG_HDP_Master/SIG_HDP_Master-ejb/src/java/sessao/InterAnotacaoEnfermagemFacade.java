/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterAnotacaoEnfermagem;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author armindo
 */
@Stateless
public class InterAnotacaoEnfermagemFacade extends AbstractFacade<InterAnotacaoEnfermagem>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterAnotacaoEnfermagemFacade()
    {
        super(InterAnotacaoEnfermagem.class);
    }

    public List<InterAnotacaoEnfermagem> pesquisarRegisto(String tipoServico, String nomeEnfermeiro, String sobreNomeEnfermeiro, Date data1,  Date data2, Long pkIdRegistoInternamento)
    {
        String query = constroiQueryRegisto(nomeEnfermeiro, sobreNomeEnfermeiro, data1, data2);

        TypedQuery<InterAnotacaoEnfermagem> t = em.createQuery(query, InterAnotacaoEnfermagem.class);

        t.setParameter("nomeServico", tipoServico);  
        
        t.setParameter("pk_id_registo", pkIdRegistoInternamento);  
        
        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeEnfermeiro+"%");
                       
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomeEnfermeiro+"%");
        
        if (data1 != null && data2 != null)    
           t.setParameter("data1", data1).setParameter("data2", data2);
        
        List<InterAnotacaoEnfermagem> resultado = t.getResultList();

        return resultado;
    }
    
    public String constroiQueryRegisto (String nomeEnfermeiro, String sobreNomeEnfermeiro, Date data1, Date data2)
    {       
        String query = "SELECT o FROM InterAnotacaoEnfermagem o WHERE o.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico AND o.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_id_registo";

        if (nomeEnfermeiro != null && !nomeEnfermeiro.trim().isEmpty())    
           query += " AND o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                     
        if (sobreNomeEnfermeiro != null && !sobreNomeEnfermeiro.trim().isEmpty()) 
           query += " AND o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
         
        if (data1 != null && data2 != null)    
            query += " AND o.data between :data1 AND :data2";
        
        query += " ORDER BY o.dataHora DESC";
        
        return query;
    }   
    
}
