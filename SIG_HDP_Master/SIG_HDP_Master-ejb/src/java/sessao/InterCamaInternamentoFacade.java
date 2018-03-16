/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterCamaInternamento;
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
public class InterCamaInternamentoFacade extends AbstractFacade<InterCamaInternamento>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterCamaInternamentoFacade()
    {
        super(InterCamaInternamento.class);
    }

    public List<InterCamaInternamento> findCama(String nomeCama, int enfermaria, int sala, int estado)
    {
        String query = constroiQuery(nomeCama, sala, estado, enfermaria);

        TypedQuery<InterCamaInternamento> t = em.createQuery(query, InterCamaInternamento.class);

        if (enfermaria > 0)
        {
            t.setParameter("idEnfermaria", enfermaria);
        }

        if (nomeCama != null && !nomeCama.trim().isEmpty())
        {
            t.setParameter("nomeCama", nomeCama);
        }

        if (sala > 0)
        {
            t.setParameter("idSala", sala);
        }

        if (estado > 0)
        {
            t.setParameter("status", estado);
        }

        List<InterCamaInternamento> resultado = t.getResultList();

        return resultado;
    }
    
    public List<InterCamaInternamento> findCamaLivre(String tipoServico, String nomeCama, int enfermaria, int sala)
    {
        String query = constroiQueryCamaLivre(nomeCama, sala, enfermaria);

        TypedQuery<InterCamaInternamento> t = em.createQuery(query, InterCamaInternamento.class);

        t.setParameter("nomeServico", tipoServico);
        t.setParameter("status1", 3);
        t.setParameter("status2", 4);

        if (enfermaria > 0)
        {
            t.setParameter("idEnfermaria", enfermaria);
        }

        if (nomeCama != null && !nomeCama.trim().isEmpty())
        {
            t.setParameter("nomeCama", nomeCama);
        }

        if (sala > 0)
        {
            t.setParameter("idSala", sala);
        }

        List<InterCamaInternamento> resultado = t.getResultList();

        return resultado;
    }

    public String constroiQuery(String nomeCama, int idSala, int status, int enfermaria)
    {
        String query = "SELECT o FROM InterCamaInternamento o WHERE o.pkIdCamaInternamento = o.pkIdCamaInternamento";

        if (enfermaria > 0)
        {
            query += " AND o.fkIdSalaInternamento.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria";
        }

        if (nomeCama != null && !nomeCama.trim().isEmpty())
        {
            query += " AND o.descricaoCamaInternamento = :nomeCama";
        }

        if (idSala > 0)
        {
            query += " AND o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala";
        }

        if (status > 0)
        {
            query += " AND o.fkIdEstadoCama.pkIdEstadoCama = :status";
        }

        query += " ORDER BY o.pkIdCamaInternamento";

        return query;
    }
    
    public String constroiQueryCamaLivre(String nomeCama, int idSala, int enfermaria)
    {
        String query = "SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico  AND (o.fkIdEstadoCama.pkIdEstadoCama = :status1 OR o.fkIdEstadoCama.pkIdEstadoCama = :status2)";

        if (enfermaria > 0)
        {
            query += " AND o.fkIdSalaInternamento.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria";
        }

        if (nomeCama != null && !nomeCama.trim().isEmpty())
        {
            query += " AND o.descricaoCamaInternamento = :nomeCama";
        }

        if (idSala > 0)
        {
            query += " AND o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala";
        }

        query += " ORDER BY o.pkIdCamaInternamento";

        return query;
    }

    public List<InterCamaInternamento> listarCamasPorTipoServico(String tipoServico)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico ORDER BY o.descricaoCamaInternamento");
        qrs.setParameter("nomeServico", tipoServico);

        return qrs.getResultList();
    }
    
    public List<InterCamaInternamento> listarCamasPorTipoServicoLivres(String tipoServico)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.fkIdEstadoCama.descricao != :estado ORDER BY o.descricaoCamaInternamento");
        qrs.setParameter("nomeServico", tipoServico).setParameter("estado", "Ocupada");

        return qrs.getResultList();
    }

    public List<InterCamaInternamento> pesquisarCama(String tipoServico, int pk_id_cama, int enfermaria, int sala, int estado)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND (o.pkIdCamaInternamento = :idCama OR o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala OR o.fkIdSalaInternamento.fkIdEnfermaria.pkIdEnfermaria = :idEnfermaria OR o.fkIdEstadoCama.pkIdEstadoCama = :status) ORDER BY o.descricaoCamaInternamento");
        qrs.setParameter("nomeServico", tipoServico).setParameter("idCama", pk_id_cama).setParameter("idEnfermaria", enfermaria).setParameter("idSala", sala).setParameter("status", estado);

        return qrs.getResultList();
    }

    public List<InterCamaInternamento> listarCamasPorTipoServico_AllSala(String tipoServico, int sala)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala ORDER BY o.descricaoCamaInternamento");
        qrs.setParameter("nomeServico", tipoServico).setParameter("idSala", sala);

        return qrs.getResultList();
    }

    public List<InterCamaInternamento> listarCamasPorTipoServico_Livre_AllSala(String tipoServico, int sala)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala ORDER BY o.descricaoCamaInternamento");
        qrs.setParameter("nomeServico", tipoServico).setParameter("idSala", sala);

        return qrs.getResultList();
    }

    public int totalCamas(int sala)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.pkIdSalaInternamento = :idSala");
        qrs.setParameter("idSala", sala);

        List<InterCamaInternamento> listaCamas = qrs.getResultList();

        return listaCamas.size() + 1;
    }

    public InterCamaInternamento getCama(String tipoServico, String cama)
    {
        Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.descricaoCamaInternamento = :nomeCama");
        qrs.setParameter("nomeServico", tipoServico).setParameter("nomeCama", cama);

        List<InterCamaInternamento> listaCamas = qrs.getResultList();

        return listaCamas.get(0);
    }

//    public InterCamaInternamento teste(String tipoServico, String cama)
//    {
//         Query qrs = em.createQuery("SELECT o FROM InterCamaInternamento o WHERE o.fkIdSalaInternamento.fkIdEnfermaria.fkIdServico.nomeServico = :nomeServico AND o.descricaoCamaInternamento = :nomeCama and o.fkIdSalaInternamento.fkIdEnfermaria.fkIdSexo.pkIdSexo");
//         qrs.setParameter("nomeServico", tipoServico).setParameter("nomeCama", cama);
//        
//         List<InterCamaInternamento> listaCamas = qrs.getResultList();
//         
//         return listaCamas.get(0);
//    }
    public boolean isCamaTabelaEmpty()
    {
        List<InterCamaInternamento> listCamas = this.findAll();
        return (listCamas == null || listCamas.isEmpty());
    }
}
