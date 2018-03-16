/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbEstadoHidratacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbEstadoHidratacaoFacade extends AbstractFacade<AmbEstadoHidratacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbEstadoHidratacaoFacade()
    {
        super(AmbEstadoHidratacao.class);
    }
    
    public boolean isEstadoHidratacaoTabelaEmpty()
    {
        List<AmbEstadoHidratacao> listAmbEstadoHidratacao = this.findAll();
        return (listAmbEstadoHidratacao == null || listAmbEstadoHidratacao.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEstadoHidratacao)
    {
        AmbEstadoHidratacao reg = this.find(pkIdEstadoHidratacao);
        return reg != null;
    }
}
