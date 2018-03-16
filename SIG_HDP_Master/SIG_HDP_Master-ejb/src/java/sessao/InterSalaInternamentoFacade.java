/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterSalaInternamento;
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
public class InterSalaInternamentoFacade extends AbstractFacade<InterSalaInternamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterSalaInternamentoFacade()
    {
        super(InterSalaInternamento.class);
    }
 
    public List<InterSalaInternamento> findSala (int idEnfermaria, String nomeSala)
    {
        String query = constroiQuery(nomeSala, idEnfermaria);

        TypedQuery<InterSalaInternamento> t = em.createQuery(query, InterSalaInternamento.class);
        
        if (nomeSala != null && !nomeSala.trim().isEmpty()) 
           t.setParameter("nomeSala", nomeSala);

        if (idEnfermaria > 0)
            t.setParameter("idEnfermaria", idEnfermaria);  
                 
        List<InterSalaInternamento> resultado = t.getResultList();

        return resultado;
   }

   public String constroiQuery (String nomeSala, int idEnfermaria)
   {       
        String query = "SELECT o FROM InterSalaInternamento o WHERE o.pkIdSalaInternamento = o.pkIdSalaInternamento";

        if (nomeSala != null && !nomeSala.trim().isEmpty())    
           query += " AND o.nomeSala = :nomeSala";

        if (idEnfermaria > 0)
           query += " AND o.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria";
        
        query += " ORDER BY o.pkIdSalaInternamento";

        return query;
   }
   
   public List<InterSalaInternamento> listarSalasPorTipoServico(String tipoServico)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico ORDER BY o.nomeSala");
         qrs.setParameter("nomeServico", tipoServico);
        
         return qrs.getResultList();
   }

   public List<InterSalaInternamento> listarSalasPorTipoServico_Enfermaria(String tipoServico, int enfermaria)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria AND o.fkIdEstado.descricaoEstado = :status ORDER BY o.nomeSala");
         qrs.setParameter("nomeServico", tipoServico).setParameter("idEnfermaria", enfermaria).setParameter("status", "Livre");
        
         return qrs.getResultList();
   }
   
   public List<InterSalaInternamento> listarSalasPorTipoServico_AllEnfermaria(int enfermaria)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria ORDER BY o.pkIdSalaInternamento");
         qrs.setParameter("idEnfermaria", enfermaria);
        
         return qrs.getResultList();
   }

   public int getUltimaSala(String servico)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.fkIdEnfermaria.fkIdServico.nomeServico = :servico");
         qrs.setParameter("servico", servico);
        
         List<InterSalaInternamento> listaSalas = qrs.getResultList();
         
         if (qrs.getResultList().isEmpty())
             return 1;
         return listaSalas.size()+1;
   }
    
   public InterSalaInternamento pesquisarSalaPorId(int pk_id_sala)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.pkIdSalaInternamento = :idSala");
         qrs.setParameter("idSala", pk_id_sala);
        
         List<InterSalaInternamento> listaSalas = qrs.getResultList();
         
         return listaSalas.get(0);
   }

   public InterSalaInternamento pesquisarPorNomeSala(String nomeQuarto, int idEnfermaria)
   {
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.nomeSala = :nome AND o.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria");
         qrs.setParameter("nome", nomeQuarto).setParameter("idEnfermaria", idEnfermaria);
        
         List<InterSalaInternamento> listaSalas = qrs.getResultList();
         
         return listaSalas.get(0);
   }

    public List<InterSalaInternamento> pesquisar(String tipoServico, int idEnfermaria, int pk_id_sala, int status)
    {
       
         Query qrs = em.createQuery("SELECT o FROM InterSalaInternamento o WHERE o.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria AND (o.pkIdSalaInternamento = :idSala OR o.fkIdEstado.pkIdEstado = :status) ORDER BY o.nomeSala");
         qrs.setParameter("nomeServico", tipoServico).setParameter("idEnfermaria", idEnfermaria).setParameter("idSala", pk_id_sala).setParameter("status", status);
        
         return qrs.getResultList();
    }
    
    public boolean isSalaTabelaEmpty()
    {
        List<InterSalaInternamento> listSalas = this.findAll();
        return (listSalas == null || listSalas.isEmpty());
    }
}
