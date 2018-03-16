/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsClassificacaoServicoSolicitado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsClassificacaoServicoSolicitadoFacade extends AbstractFacade<AdmsClassificacaoServicoSolicitado>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsClassificacaoServicoSolicitadoFacade()
    {
        super(AdmsClassificacaoServicoSolicitado.class);
    }
    
    public boolean isClassificacaoServicoSolicitadoTabelaEmpty()
    {
        List<AdmsClassificacaoServicoSolicitado> listClassificacaoServicoSolicitado = this.findAll();
        return (listClassificacaoServicoSolicitado == null || listClassificacaoServicoSolicitado.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdClassificacaoServicoSolicitado)
    {
        AdmsClassificacaoServicoSolicitado reg = this.find(pkIdClassificacaoServicoSolicitado);
        return reg != null;
    }
    
}
