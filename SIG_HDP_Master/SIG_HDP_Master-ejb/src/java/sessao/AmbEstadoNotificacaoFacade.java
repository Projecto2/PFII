/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbEstadoNotificacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbEstadoNotificacaoFacade extends AbstractFacade<AmbEstadoNotificacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbEstadoNotificacaoFacade()
    {
        super(AmbEstadoNotificacao.class);
    }
    
    public boolean isNotificacaoTabelaEmpty()
    {
        List<AmbEstadoNotificacao> listAmbEstadoNotificacao = this.findAll();
        return (listAmbEstadoNotificacao == null || listAmbEstadoNotificacao.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdNotificacao)
    {
        AmbEstadoNotificacao reg = this.find(pkIdNotificacao);
        return reg != null;
    }
}
