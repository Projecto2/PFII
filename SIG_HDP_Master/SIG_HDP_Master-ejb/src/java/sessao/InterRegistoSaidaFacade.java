/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterRegistoSaida;
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
public class InterRegistoSaidaFacade extends AbstractFacade<InterRegistoSaida>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterRegistoSaidaFacade()
    {
        super(InterRegistoSaida.class);
    }
    
    public List<InterRegistoSaida> pesquisarRegistoSaida(String tipoServico, Long pkIdRegistoInternamento, Date dataRegistoSaida1, Date dataRegistoSaida2, int pkIdTipoAlta)
    {
        String query = constroiQueryRegistoSaida(pkIdRegistoInternamento, dataRegistoSaida1, dataRegistoSaida2, pkIdTipoAlta);

        TypedQuery<InterRegistoSaida> t = em.createQuery(query, InterRegistoSaida.class);

        t.setParameter("nomeServico", tipoServico);  
          
        if (pkIdRegistoInternamento != null)    
           t.setParameter("pkIdRegistoInternamento", pkIdRegistoInternamento);
        
        if (dataRegistoSaida1 != null && dataRegistoSaida2 != null)    
           t.setParameter("data1", dataRegistoSaida1).setParameter("data2", dataRegistoSaida2);
       
        if (pkIdTipoAlta > 0)    
           t.setParameter("pkIdTipoAlta", pkIdTipoAlta);
                 
        List<InterRegistoSaida> resultado = t.getResultList();

        return resultado;
   }
   
   public String constroiQueryRegistoSaida (Long pkIdRegistoInternamento, Date dataRegistoSaida1, Date dataRegistoSaida2, int pkIdTipoAlta)
   {       
        String query = "SELECT o FROM InterRegistoSaida o WHERE o.fkIdRegistoInternamento.fkIdServicoSolicitado.fkIdServico.nomeServico =:nomeServico";

        if (pkIdRegistoInternamento != null)    
           query += " AND o.fkIdRegistoInternamento.pkIdRegistoInternamento =:pkIdRegistoInternamento";
                
        if (dataRegistoSaida1 != null && dataRegistoSaida2 != null)    
            query += " AND o.data between :data1 AND :data2";
        
        if (pkIdTipoAlta > 0)    
            query += " AND o.fkIdTipoAlta.pkIdTipoAlta =:pkIdTipoAlta";
                
        query += " ORDER BY o.dataRegistoSaida";
        
        return query;
   }
}
