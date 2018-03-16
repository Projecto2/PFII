/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.InterControloParametrosVitais;
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
public class InterControloParametrosVitaisFacade extends AbstractFacade<InterControloParametrosVitais>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterControloParametrosVitaisFacade()
    {
        super(InterControloParametrosVitais.class);
    }

    public List<InterControloParametrosVitais> pesquisarParametro(Long pkIdRegistoInternamento, String parametro, Date data, int hora, Date data1, Date data2, String enfermeiro)
    {
        String query = constroiQueryParametros(parametro, data, hora, data1, data2, enfermeiro);

        TypedQuery<InterControloParametrosVitais> t = em.createQuery(query, InterControloParametrosVitais.class);

        t.setParameter("pk_id_registo", pkIdRegistoInternamento);

        if (parametro != null && !parametro.trim().isEmpty())
        {
            t.setParameter("parametro", parametro);
        }

        if (data != null)
        {
            t.setParameter("data", data);
        }

        if (hora > 0)
        {
            t.setParameter("hora", hora);
        }

        if (data1 != null && data2 != null)
        {
            t.setParameter("data1", data1).setParameter("data2", data2);
        }

        if (enfermeiro != null && !enfermeiro.trim().isEmpty())
        {
            t.setParameter("nome", "%" + enfermeiro + "%");
        }

        List<InterControloParametrosVitais> resultado = t.getResultList();

        return resultado;
    }

    private String constroiQueryParametros(String parametro, Date data, int hora, Date data1, Date data2, String enfermeiro)
    {
        String query = "SELECT o FROM InterControloParametrosVitais o WHERE o.fkIdRegistoInternamentoHasParametroVital.fkIdRegistoInternamento.pkIdRegistoInternamento =:pk_id_registo";

        if (parametro != null && !parametro.trim().isEmpty())
        {
            query += " AND o.fkIdRegistoInternamentoHasParametroVital.fkIdParametroVital.descricao =:parametro";
        }

        if (data != null)
        {
            query += " AND o.dataRegistadaNoPaciente =:data";
        }

        if (hora > 0)
        {
            query += " AND o.hora =:hora";
        }

        if (data1 != null && data2 != null)
        {
            query += " AND o.dataRegistadaNoPaciente between :data1 AND :data2";
        }

        if (enfermeiro != null && !enfermeiro.trim().isEmpty())
        {
            query += " AND (o.fkIdFuncionario.fkIdPessoa.nome LIKE :nome";
            query += " OR o.fkIdFuncionario.fkIdPessoa.nomeDoMeio LIKE :nome";
            query += " OR o.fkIdFuncionario.fkIdPessoa.sobreNome LIKE :nome)";
        }

        query += " ORDER BY o.dataRegistadaNoPaciente ASC";

        return query;
    }

//       public List<InterControloParametrosVitais> teste()
//    {
//        
//        Query qrs = em.createQuery("SELECT o FROM InterControloParametrosVitais o WHERE o");
//        
//        return qrs.getResultList();
//    }
}
