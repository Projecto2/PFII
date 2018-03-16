/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterRegistoInternamentoHasParametroVital;
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
public class InterRegistoInternamentoHasParametroVitalFacade extends AbstractFacade<InterRegistoInternamentoHasParametroVital>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterRegistoInternamentoHasParametroVitalFacade()
    {
        super(InterRegistoInternamentoHasParametroVital.class);
    }

    public List<InterRegistoInternamentoHasParametroVital> pesquisarRegisto(String tipoServico, Long pkIdRegisto, int parametro)
    {
        String query = constroiQueryRegisto(pkIdRegisto, parametro);

        TypedQuery<InterRegistoInternamentoHasParametroVital> t = em.createQuery(query, InterRegistoInternamentoHasParametroVital.class);

//        t.setParameter("nomeServico", tipoServico);
        
        if (pkIdRegisto != null)   
           t.setParameter("pkIdRegisto", pkIdRegisto);  
        
        if (parametro > 0)   
           t.setParameter("parametro", parametro);
        
        List<InterRegistoInternamentoHasParametroVital> resultado = t.getResultList();

        return resultado;
    }
    
    public String constroiQueryRegisto (Long pkIdRegisto, int parametro)
    {       
//        String query = "SELECT o FROM InterRegistoInternamentoHasParametroVital o WHERE o.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico";
        String query = "SELECT o FROM InterRegistoInternamentoHasParametroVital o WHERE o.pkIdRegistoInternamentoHasParametroVital = o.pkIdRegistoInternamentoHasParametroVital";

        if (pkIdRegisto != null)   
           query += " AND o.fkIdRegistoInternamento.pkIdRegistoInternamento =:pkIdRegisto";
        
        if (parametro > 0)   
           query += " AND o.fkIdParametroVital.pkIdParametroVital =:parametro";
        
        query += " ORDER BY o.fkIdParametroVital.descricao";
        
        return query;
   }
    
//   public List<InterRegistoInternamentoHasParametroVital> teste(String nomeEnfermeiro, String nomeDoMeioEnfermeiro, String sobreNomeEnfermeiro, Date dataRegisto, int pk_id_medicacao)
//   {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterRegistoInternamentoHasParametroVital o WHERE o.fkIdRegistoInternamento.pkIdRegistoInternamento and o.fkIdParametroVital.pkIdParametroVital");
//                
//        return qrs.getResultList();
//    }
}
