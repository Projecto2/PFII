/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterNotificacao;
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
public class InterNotificacaoFacade extends AbstractFacade<InterNotificacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterNotificacaoFacade()
    {
        super(InterNotificacao.class);
    }

    public List<InterNotificacao> findBy(String tipoServico, int fkIdTipoNotificacao, String assunto, Long pkIdPaciente, int pkIdParametro, int pkIdProduto)
    {
        String query = constroiQueryRegisto(fkIdTipoNotificacao, assunto, pkIdPaciente, pkIdParametro, pkIdProduto);

        TypedQuery<InterNotificacao> t = em.createQuery(query, InterNotificacao.class);

        t.setParameter("nomeServico", tipoServico);
        
        if (fkIdTipoNotificacao > 0)   
           t.setParameter("tipo", fkIdTipoNotificacao);
       
        if (assunto != null && !assunto.trim().isEmpty())   
           t.setParameter("assunto", assunto);
           
        if (pkIdPaciente != null)    
           t.setParameter("pkIdPaciente", pkIdPaciente);
        
        if (pkIdParametro > 0)   
           t.setParameter("pkIdParametro", pkIdParametro);

        if (pkIdProduto > 0)   
           t.setParameter("pkIdProduto", pkIdProduto);
        
        List<InterNotificacao> resultado = t.getResultList();

        return resultado;
    }
        
    public String constroiQueryRegisto (int fkIdTipoNotificacao, String assunto, Long pkIdPaciente, int pkIdParametro, int pkIdProduto)
    {       
        String query = "SELECT o FROM InterNotificacao o WHERE o.fkIdEnfermaria.fkIdServico.nomeServico =:nomeServico";

        if (fkIdTipoNotificacao > 0)   
           query += " AND o.fkIdTipoNotificacao.pkIdTipoNotificacao = :tipo"; 
        
        if (assunto != null && !assunto.trim().isEmpty())   
           query += " AND o.assunto = :assunto"; 
           
        if (pkIdPaciente != null)
           query += " AND o.fkIdPaciente.pkIdPaciente =:pkIdPaciente";

        if (pkIdParametro > 0)
           query += " AND o.fkIdParametroVital.pkIdParametroVital =:pkIdParametro";
        
        if (pkIdProduto > 0)
           query += " AND o.fkIdProduto.pkIdProduto =:pkIdProduto";

        query += " ORDER BY o.dataDeNotificacao DESC";
        
        return query;
    }   
}
