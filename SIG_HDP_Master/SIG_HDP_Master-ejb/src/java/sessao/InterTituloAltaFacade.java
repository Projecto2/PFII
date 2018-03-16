/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterTituloAlta;
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
public class InterTituloAltaFacade extends AbstractFacade<InterTituloAlta> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterTituloAltaFacade() {
        super(InterTituloAlta.class);
    }

    public List<InterTituloAlta> pesquisarPorTipoServico(String tipoServico)
    {
        Query qrs = em.createQuery("SELECT o FROM InterTituloAlta o WHERE o.fkIdRegistoInternamento.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico  = :nomeServico");
        qrs.setParameter("nomeServico", tipoServico);
        
        return qrs.getResultList();
    }

    public List<InterTituloAlta> pesquisarTituloAlta(String tipoServico, String numeroTituloPesq, String tipoAltaPesq, Date dataAltaPesq1, Date dataAltaPesq2, String numeroInscricao)
    {
        String query = constroiQueryTituloAlta(numeroTituloPesq, tipoAltaPesq, dataAltaPesq1, dataAltaPesq2);
         
        TypedQuery<InterTituloAlta> t = em.createQuery(query, InterTituloAlta.class);

        t.setParameter("nomeServico", tipoServico).setParameter("numeroInscricao", numeroInscricao);  
        
        if (numeroTituloPesq != null && !numeroTituloPesq.trim().isEmpty())    
           t.setParameter("numeroTituloAlta", numeroTituloPesq);
        
        if (tipoAltaPesq != null && !tipoAltaPesq.trim().isEmpty())    
           t.setParameter("tipoAltaPesq", tipoAltaPesq);
        
        if (dataAltaPesq1 != null && dataAltaPesq2 != null)    
           t.setParameter("data1", dataAltaPesq1).setParameter("data2", dataAltaPesq2);
               
        List<InterTituloAlta> resultado = t.getResultList();

        return resultado;

    }
    
    public String constroiQueryTituloAlta (String numeroTituloPesq, String tipoAltaPesq, Date dataAltaPesq1, Date dataAltaPesq2)
    {       
        String query = "SELECT o FROM InterTituloAlta o WHERE o.fkIdRegistoInternamento.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico  =:nomeServico AND o.fkIdRegistoInternamento.fkIdInscricaoInternamento.numeroInscricao =:numeroInscricao";

        if (numeroTituloPesq != null && !numeroTituloPesq.trim().isEmpty())    
           query += " AND o.numeroTituloAlta =:numeroTituloAlta";
        
        if (tipoAltaPesq != null && !tipoAltaPesq.trim().isEmpty())    
           query += " AND o.fkIdTipoAlta.descricao =:tipoAltaPesq";
        
        if (dataAltaPesq1 != null && dataAltaPesq2 != null)       
            query += " AND o.dataAlta between :data1 AND :data2";
        
        query += " ORDER BY o.dataAlta DESC";
        
        return query;
   }

    
//    public List<InterTituloAlta> teste(String tipoServico)
//    {
//        Query qrs = em.createQuery("SELECT o FROM InterTituloAlta o WHERE o.fkIdRegistoInternamento.fkIdCamaInternamento.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico  = :nomeServico AND o.numeroTituloAlta = :n AND o.dataAlta = d AND o.fkIdRegistoInternamento.fkIdInscricaoInternamento.numeroInscricao = n and o.fkIdRegistoInternamento.pkIdRegistoInternamento AND o.fkIdTipoAlta.descricao = t");
//        qrs.setParameter("nomeServico", tipoServico);
//        
//        return qrs.getResultList();
//    }
//    
}
