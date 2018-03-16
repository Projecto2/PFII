/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsCategoriaServico;
import entidade.AdmsServico;
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
public class AdmsCategoriaServicoFacade extends AbstractFacade<AdmsCategoriaServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsCategoriaServicoFacade()
    {
        super(AdmsCategoriaServico.class);
    }
    
    
    public List<AdmsCategoriaServico> findCategoriaServico(AdmsCategoriaServico preco)
    {
        String query = constroiQuery(preco);

        Query qry = em.createQuery(query);

        qry.setParameter("pkIdServico", preco.getFkIdServico().getPkIdServico());
        
        if (preco.getDescricaoCategoriaServico()!= null && !preco.getDescricaoCategoriaServico().trim().isEmpty())
            qry.setParameter("descricaoCategoriaServico", preco.getDescricaoCategoriaServico()+"%");

        List<AdmsCategoriaServico> precos = qry.getResultList();
        
        return precos;
    }

    public String constroiQuery(AdmsCategoriaServico preco)
    {
        String query = "SELECT categoriaServico FROM AdmsCategoriaServico as categoriaServico WHERE categoriaServico.fkIdServico.pkIdServico = :pkIdServico";

        if (preco.getDescricaoCategoriaServico()!= null && !preco.getDescricaoCategoriaServico().trim().isEmpty())
            query += " AND categoriaServico.descricaoCategoriaServico LIKE :descricaoCategoriaServico";

        query += " ORDER BY categoriaServico.descricaoCategoriaServico";
        return query;
    } 

    public List<AdmsCategoriaServico> findCategoriasServicoAtivos(AdmsServico servico)
    {
        Query qry = em.createQuery("SELECT categoriaServico FROM AdmsCategoriaServico as categoriaServico WHERE categoriaServico.fkIdServico.pkIdServico = :pkIdServico AND categoriaServico.estadoCategoriaServico = true");
        qry.setParameter("pkIdServico", servico.getPkIdServico());
        
        List<AdmsCategoriaServico> precos = qry.getResultList();
        return precos;
    }
    
    public String estadoServicoCategoriaServico(AdmsServico servico)
    {
        
        List<AdmsCategoriaServico> precos = findTodasCategoriasServico(servico);
        
        if(precos.isEmpty()) return "Sem Categorias";
        
        for(int i = 0; i < precos.size(); i++)
        {
            if(precos.get(i).getEstadoCategoriaServico()) return "Com Categorias";
        }
        return "Sem Categorias Ativas";
    }
    
    public List<AdmsCategoriaServico> findTodasCategoriasServico(AdmsServico servico)
    {
        Query qry;
        qry = em.createQuery("SELECT categoriaServico FROM AdmsCategoriaServico as categoriaServico WHERE categoriaServico.fkIdServico.pkIdServico = :pkIdServico");
        qry.setParameter("pkIdServico", servico.getPkIdServico());
        
        List<AdmsCategoriaServico> precos = qry.getResultList();
        return precos; 
    }
    
    public boolean isCategoriaServicoTabelaEmpty()
    {
        List<AdmsCategoriaServico> listCategoriaServico = this.findAll();
        return (listCategoriaServico == null || listCategoriaServico.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdCategoriaServico)
    {
        AdmsCategoriaServico reg = this.find(pkIdCategoriaServico);
        return reg != null;
    }
    
}
