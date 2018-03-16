/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbEstadoPagamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbEstadoPagamentoFacade extends AbstractFacade<AmbEstadoPagamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbEstadoPagamentoFacade()
    {
        super(AmbEstadoPagamento.class);
    }
    
    public boolean isEstadoPagamentoTabelaEmpty()
    {
        List<AmbEstadoPagamento> listAmbEstadoPagamento = this.findAll();
        return (listAmbEstadoPagamento == null || listAmbEstadoPagamento.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEstadoPagamento)
    {
        AmbEstadoPagamento reg = this.find(pkIdEstadoPagamento);
        return reg != null;
    }
}
