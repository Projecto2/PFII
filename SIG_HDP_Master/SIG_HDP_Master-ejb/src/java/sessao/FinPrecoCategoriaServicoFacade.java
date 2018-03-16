/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsCategoriaServico;
import entidade.FinPrecoCategoriaServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class FinPrecoCategoriaServicoFacade extends AbstractFacade<FinPrecoCategoriaServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FinPrecoCategoriaServicoFacade()
    {
        super(FinPrecoCategoriaServico.class);
    }
    
    
    public List<FinPrecoCategoriaServico> findValorAtivo(AdmsCategoriaServico categoriaServico)
    {
        String query = "SELECT valorPreco FROM FinPrecoCategoriaServico as "
            + "valorPreco WHERE valorPreco.fkIdCategoriaServico.pkIdCategoriaServico = :pkIdCategoriaServico and valorPreco.estadoPreco = true";
        
        if(categoriaServico.getTipoUnico())
        {
            query += " AND valorPreco.fkIdTipoPrecoCategoriaServico.descricao LIKE 'Único'";
        }
        else{
            query += " AND (valorPreco.fkIdTipoPrecoCategoriaServico.descricao LIKE 'Primeira Vez' OR valorPreco.fkIdTipoPrecoCategoriaServico.descricao LIKE 'Retorno')";
        }
        
        Query qry = em.createQuery(query);
        
        qry.setParameter("pkIdCategoriaServico", categoriaServico.getPkIdCategoriaServico());
        List<FinPrecoCategoriaServico> valores = qry.getResultList();
        
        return valores;
    }
    
    public List<FinPrecoCategoriaServico> findValoresPreco(AdmsCategoriaServico categoriaServico, String tipo)
    {
        String query = "SELECT valorPreco FROM FinPrecoCategoriaServico as "
            + "valorPreco WHERE valorPreco.fkIdCategoriaServico.pkIdCategoriaServico = :pkIdCategoriaServico";
        
        if(tipo.equalsIgnoreCase("unico")) query += " AND valorPreco.fkIdTipoPrecoCategoriaServico.descricao like 'Único'";
        
        if(tipo.equalsIgnoreCase("primeira vez")) query += " AND valorPreco.fkIdTipoPrecoCategoriaServico.descricao like 'Primeira Vez'";
        
        if(tipo.equalsIgnoreCase("retorno")) query += " AND valorPreco.fkIdTipoPrecoCategoriaServico.descricao like 'Retorno'";
        
        Query qry = em.createQuery(query);
        
        qry.setParameter("pkIdCategoriaServico", categoriaServico.getPkIdCategoriaServico());
        List<FinPrecoCategoriaServico> valores = qry.getResultList();
        
        return valores;
    }
    
    public FinPrecoCategoriaServico findValorDoPrecoComTipo(AdmsCategoriaServico categoriaServico, String tipo)
    {
        Query qry = em.createQuery("SELECT valorPreco FROM FinPrecoCategoriaServico as valorPreco WHERE valorPreco.fkIdCategoriaServico.pkIdCategoriaServico = :pkIdCategoriaServico AND valorPreco.estadoPreco = true AND valorPreco.fkIdTipoPrecoCategoriaServico.descricao LIKE :tipo");
        
        qry.setParameter("pkIdCategoriaServico", categoriaServico.getPkIdCategoriaServico());
        qry.setParameter("tipo", tipo);
        
        List<FinPrecoCategoriaServico> valores = qry.getResultList();
        
        if(valores.isEmpty()) return null;
        
        return valores.get(0);
    }
    
}
