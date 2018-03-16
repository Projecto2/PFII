/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterDoencaInternamentoPaciente;
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
public class InterDoencaInternamentoPacienteFacade extends AbstractFacade<InterDoencaInternamentoPaciente> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterDoencaInternamentoPacienteFacade() {
        super(InterDoencaInternamentoPaciente.class);
    }

    public List<InterDoencaInternamentoPaciente> pesquisarRegisto(String tipoServico, String nomeFuncionario, String sobreNomeFuncionario, Date dataRegisto1, Date dataRegisto2, Long pkIdRegistoInternamento, String doencaPesq, int tipoDoenca, String pkIdDoenca)
    {
        String query = constroiQueryRegisto(nomeFuncionario, sobreNomeFuncionario, dataRegisto1, dataRegisto2, doencaPesq, tipoDoenca, pkIdDoenca);

        TypedQuery<InterDoencaInternamentoPaciente> t = em.createQuery(query, InterDoencaInternamentoPaciente.class);

        t.setParameter("nomeServico", tipoServico);  
        
        t.setParameter("pk_id_registo", pkIdRegistoInternamento);  
        
        if (nomeFuncionario != null && !nomeFuncionario.trim().isEmpty())   
           t.setParameter("nome", "%"+nomeFuncionario+"%");
                       
        if (sobreNomeFuncionario != null && !sobreNomeFuncionario.trim().isEmpty())    
           t.setParameter("sobreNome", "%"+sobreNomeFuncionario+"%");
        
        if (dataRegisto1 != null && dataRegisto2 != null)    
           t.setParameter("data1", dataRegisto1).setParameter("data2", dataRegisto2);
        
        if (doencaPesq != null && !doencaPesq.trim().isEmpty())   
           t.setParameter("doenca", "%"+doencaPesq+"%");
        
        if (tipoDoenca > 0)   
           t.setParameter("tipoDoenca", tipoDoenca);
        
        if (pkIdDoenca != null && !pkIdDoenca.trim().isEmpty())   
           t.setParameter("pkIdDoenca", pkIdDoenca);
        
        List<InterDoencaInternamentoPaciente> resultado = t.getResultList();

        return resultado;
    }

    private String constroiQueryRegisto(String nomeFuncionario, String sobreNomeFuncionario, Date dataRegisto1, Date dataRegisto2, String doencaPesq, int tipoDoenca, String pkIdDoenca)
    {
        String query = "SELECT o FROM InterDoencaInternamentoPaciente o WHERE o.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico AND o.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_id_registo";

        if (nomeFuncionario != null && !nomeFuncionario.trim().isEmpty())    
           query += " AND o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome"; 
                     
        if (sobreNomeFuncionario != null && !sobreNomeFuncionario.trim().isEmpty())   
           query += " AND o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :sobreNome";
         
        if (dataRegisto1 != null && dataRegisto2 != null)     
           query += " AND o.dataRegisto between :data1 AND :data2";
        
        if (doencaPesq != null && !doencaPesq.trim().isEmpty())    
           query += " AND o.fkIdCidSubcategorias.nome LIKE :doenca";
         
        if (tipoDoenca > 0)
           query += " AND o.fkIdTipoDoencaIntenamento.pkIdTipoDoencaInternamento =:tipoDoenca";
        
        if (pkIdDoenca != null && !pkIdDoenca.trim().isEmpty())
            query += " AND o.fkIdCidSubcategorias.pkIdSubcategorias =:pkIdDoenca";
            
        query += " ORDER BY o.fkIdTipoDoencaIntenamento.descricao DESC";
        
        return query;
    }
    
//        public List<InterDoencaInternamentoPaciente> teste(String tipoServico, String nomeEnfermeiro, Date dataRegisto)
//    {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterDoencaInternamentoPaciente o WHERE o.dataRegisto and o.dataDiagnostico and o.fkIdDoenca.descricao and o.fkIdTipoDoencaIntenamento.pkIdTipoDoencaInternamento");
//        
//        return qrs.getResultList();
//    }
}
