/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsTipoServico;
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
public class AdmsTipoServicoFacade extends AbstractFacade<AdmsTipoServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsTipoServicoFacade()
    {
        super(AdmsTipoServico.class);
    }
    
    /**
     *
     * @param tipoServico
     * @return
     */
    public List<AdmsTipoServico> findTiposServico(AdmsTipoServico tipoServico)
    {
        String query = constroiQuery(tipoServico);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (tipoServico.getDescricaoTipoServico() != null && !tipoServico.getDescricaoTipoServico().trim().isEmpty())
            qry.setParameter("descricaoTipoServico", tipoServico.getDescricaoTipoServico()+"%");

        List<AdmsTipoServico> tipoServicos = qry.getResultList();

        return tipoServicos;
    }


    public String constroiQuery(AdmsTipoServico tipoServico)
    {        
        String query = "SELECT tipoServico FROM AdmsTipoServico tipoServico WHERE :pesquisar = :pesquisar";

        if (tipoServico.getDescricaoTipoServico() != null && !tipoServico.getDescricaoTipoServico().trim().isEmpty())
           query += " AND tipoServico.descricaoTipoServico LIKE :descricaoTipoServico";

        query += " ORDER BY tipoServico.descricaoTipoServico";

        return query;
    }
    
    
    public boolean isTipoDeServicoTabelaEmpty()
    {
        List<AdmsTipoServico> listTipoServico = this.findAll();
        return (listTipoServico == null || listTipoServico.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTipoServico)
    {
        AdmsTipoServico reg = this.find(pkIdTipoServico);
        return reg != null;
    }
    
}
