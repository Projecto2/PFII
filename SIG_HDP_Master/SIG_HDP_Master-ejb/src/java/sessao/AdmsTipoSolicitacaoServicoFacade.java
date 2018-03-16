/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsTipoSolicitacaoServico;
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
public class AdmsTipoSolicitacaoServicoFacade extends AbstractFacade<AdmsTipoSolicitacaoServico>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsTipoSolicitacaoServicoFacade()
    {
        super(AdmsTipoSolicitacaoServico.class);
    }
    
    public List<AdmsTipoSolicitacaoServico> findTipoSolicitacaoServico(AdmsTipoSolicitacaoServico tipoSolicitacaoServico)
    {
        String query = constroiQuery(tipoSolicitacaoServico);

        Query qry = em.createQuery(query);

        qry.setParameter("pesquisar", true);

        if (tipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico() != null && !tipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico().trim().isEmpty())
            qry.setParameter("descricaoTipoSolicitacaoServico", tipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico()+"%");

        List<AdmsTipoSolicitacaoServico> tipoSolicitacaoServicos = qry.getResultList();

        return tipoSolicitacaoServicos;
    }


    public String constroiQuery(AdmsTipoSolicitacaoServico tipoSolicitacaoServico)
    {        
        String query = "SELECT tipoSolicitacaoServico FROM AdmsTipoSolicitacaoServico tipoSolicitacaoServico WHERE :pesquisar = :pesquisar";

        if (tipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico() != null && !tipoSolicitacaoServico.getDescricaoTipoSolicitacaoServico().trim().isEmpty())
           query += " AND tipoSolicitacaoServico.descricaoTipoSolicitacaoServico LIKE :descricaoTipoSolicitacaoServico";

        query += " ORDER BY tipoSolicitacaoServico.descricaoTipoSolicitacaoServico";

        return query;
    }
    
    
    public boolean isTipoSolicitacaoServicoTabelaEmpty()
    {
        List<AdmsTipoSolicitacaoServico> listTipoSolicitacaoServico = this.findAll();
        return (listTipoSolicitacaoServico == null || listTipoSolicitacaoServico.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTipoSolicitacaoServico)
    {
        AdmsTipoSolicitacaoServico reg = this.find(pkIdTipoSolicitacaoServico);
        return reg != null;
    }
    
}
