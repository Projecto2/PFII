/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterAnotacaoMedica;
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
public class InterAnotacaoMedicaFacade extends AbstractFacade<InterAnotacaoMedica>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterAnotacaoMedicaFacade()
    {
        super(InterAnotacaoMedica.class);
    }

    public List<InterAnotacaoMedica> pesquisarRegisto(String tipoServico, String nomeMedico, String sobreNomeMedico, Date data1,  Date data2, Long pkIdRegistoInternamento)
    {
        String query = constroiQueryRegisto(nomeMedico, sobreNomeMedico, data1, data2);

        TypedQuery<InterAnotacaoMedica> t = em.createQuery(query, InterAnotacaoMedica.class);

        t.setParameter("nomeServico", tipoServico);  
        
        t.setParameter("pk_id_registo", pkIdRegistoInternamento);  
        
        if (nomeMedico != null && !nomeMedico.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeMedico+"%");
                       
        if (sobreNomeMedico != null && !sobreNomeMedico.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomeMedico+"%");
        
        if (data1 != null && data2 != null)    
           t.setParameter("data1", data1).setParameter("data2", data2);
        
        List<InterAnotacaoMedica> resultado = t.getResultList();

        return resultado;
    }
    
    public String constroiQueryRegisto (String nomeMedico, String sobreNomeMedico, Date data1, Date data2)
    {       
        String query = "SELECT o FROM InterAnotacaoMedica o WHERE o.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico AND o.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_id_registo";

        if (nomeMedico != null && !nomeMedico.trim().isEmpty())    
           query += " AND o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                     
        if (sobreNomeMedico != null && !sobreNomeMedico.trim().isEmpty()) 
           query += " AND o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
        
        if (data1 != null && data2 != null)       
            query += " AND o.data between :data1 AND :data2";
        
        query += " ORDER BY o.dataHora DESC";
        
        return query;
    }

//    public List<InterAnotacaoMedica> teste(String tipoServico, String nomeEnfermeiro, Date dataRegisto)
//    {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterAnotacaoMedica o WHERE o.dataHora");
//        
//        return qrs.getResultList();
//    }
    
}
