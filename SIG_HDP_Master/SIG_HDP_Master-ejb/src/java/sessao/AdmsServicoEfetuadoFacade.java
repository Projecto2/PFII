/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsServicoEfetuado;
import entidade.AdmsServicoSolicitado;
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
public class AdmsServicoEfetuadoFacade extends AbstractFacade<AdmsServicoEfetuado>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsServicoEfetuadoFacade()
    {
        super(AdmsServicoEfetuado.class);
    }
    
    
    public AdmsServicoEfetuado findServicoEfetuadoPorServicoSolicitado(AdmsServicoSolicitado servicoSolicitado)
    {
        Query qry = em.createQuery("SELECT servicoEfetuado FROM AdmsServicoEfetuado as servicoEfetuado WHERE servicoEfetuado.fkIdServicoSolicitado.pkIdServicoSolicitado = :idServicoSolicitado");

        qry.setParameter("idServicoSolicitado", servicoSolicitado.getPkIdServicoSolicitado());

        List<AdmsServicoEfetuado> servicoEfetuado = qry.getResultList();
        
        if(servicoEfetuado.isEmpty()) return null;
        
        return servicoEfetuado.get(0);
    }
    
    public AdmsServicoEfetuado findServicoEfetuadoInternamento(Long registo)
    {
        Query qry = em.createQuery("SELECT servicoEfetuado FROM AdmsServicoEfetuado as servicoEfetuado WHERE servicoEfetuado.descricaoTabelaBusca = 'inter_registo_internamento' AND servicoEfetuado.codigoTabelaBusca = :idServicoSolicitado");

        qry.setParameter("idServicoSolicitado", registo);

        List<AdmsServicoEfetuado> servicoEfetuado = qry.getResultList();
        
        if(servicoEfetuado.isEmpty()) return null;
        
        return servicoEfetuado.get(0);
    }
    
}
