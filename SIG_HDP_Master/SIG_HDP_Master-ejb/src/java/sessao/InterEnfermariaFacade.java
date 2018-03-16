/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterEnfermaria;
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
public class InterEnfermariaFacade extends AbstractFacade<InterEnfermaria> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterEnfermariaFacade() {
        super(InterEnfermaria.class);
    }

    public List<InterEnfermaria> listarEnfermariasPorTipo(String tipoServico)
    {
         Query qrs = em.createQuery("SELECT o FROM InterEnfermaria o WHERE o.fkIdServico.nomeServico = :nomeServico");
         qrs.setParameter("nomeServico", tipoServico);
        
         return qrs.getResultList();
    }
    
    public List<InterEnfermaria> listarTodasEnfermarias()
    {
         Query qrs = em.createQuery("SELECT o FROM InterEnfermaria o ORDER BY o.fkIdServico.nomeServico");
        
         return qrs.getResultList();
    }
    
    public List<InterEnfermaria> listarEnfermariasPorNome_e_Codigo(String nomeEnfermaria, String codigoEnfermaria)
    {
         Query qrs = em.createQuery("SELECT o FROM InterEnfermaria o WHERE o.descricao = :nomeEnfermaria");
         qrs.setParameter("nomeEnfermaria", nomeEnfermaria);
        
         return qrs.getResultList();
    }

//    public List<InterEnfermaria> teste(String tipoServico, String enfermaria, int estado)
//    {
//         Query qrs = em.createQuery("SELECT o FROM InterEnfermaria o WHERE o.pkIdEnfermaria = o.pkIdEnfermaria AND o.codigoEnfermaria = c AND o.fkIdServico.pkIdServico = s AND (o.descricao = :descEnfermaria OR o.fkIdStatus.pkIdStatus = :status) and o.fkIdEstado.pkIdEstado");
//         qrs.setParameter("nomeServico", tipoServico).setParameter("descEnfermaria", enfermaria).setParameter("status", estado);
//        
//         return qrs.getResultList();
//    }
    
    public List<InterEnfermaria> pesquisarEnfermaria (String enfermaria, String codigoEnfermaria, int servicoPesq)
    {
        String query = constroiQuery(enfermaria, codigoEnfermaria, servicoPesq);

        TypedQuery<InterEnfermaria> t = em.createQuery(query, InterEnfermaria.class);  
        
        if (enfermaria != null && !enfermaria.trim().isEmpty()) 
           t.setParameter("nomeEnfermaria", enfermaria);

        if (codigoEnfermaria != null && !codigoEnfermaria.trim().isEmpty()) 
           t.setParameter("codigoEnfermaria", codigoEnfermaria);
        
         if (servicoPesq > 0)
            t.setParameter("servicoPesq", servicoPesq);
        
        List<InterEnfermaria> resultado = t.getResultList();

        return resultado;
   }

   public String constroiQuery (String nomeEnfermaria, String codigoEnfermaria, int servicoPesq)
   {       
        String query = "SELECT o FROM InterEnfermaria o WHERE o.pkIdEnfermaria = o.pkIdEnfermaria";

        if (nomeEnfermaria != null && !nomeEnfermaria.trim().isEmpty())    
           query += " AND o.descricao = :nomeEnfermaria";

        if (codigoEnfermaria != null && !codigoEnfermaria.trim().isEmpty())    
           query += " AND o.codigoEnfermaria = :codigoEnfermaria";
        
        if (servicoPesq > 0)
           query += " AND o.fkIdServico.pkIdServico = :servicoPesq";
        
        query += " ORDER BY o.descricao";

        return query;
   }
   
   public InterEnfermaria getEnfermariaFuncionario(String sessaoTrabalho)
   {
         Query qrs = em.createQuery("SELECT o FROM InterEnfermaria o WHERE o.fkIdServico.nomeServico = :nome");
         qrs.setParameter("nome", sessaoTrabalho);
        
         List<InterEnfermaria> results = qrs.getResultList();
         
         return results.get(0);
   }
   
   public boolean isEnfermariaTabelaEmpty()
   {
        List<InterEnfermaria> listEnfermarias = this.findAll();
        return (listEnfermarias == null || listEnfermarias.isEmpty());
   }
}
