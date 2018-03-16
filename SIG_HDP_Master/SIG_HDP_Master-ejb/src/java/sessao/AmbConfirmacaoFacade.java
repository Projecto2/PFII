/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbConfirmacao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbConfirmacaoFacade extends AbstractFacade<AmbConfirmacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbConfirmacaoFacade()
    {
        super(AmbConfirmacao.class);
    }
    
    public boolean isAderenciaTabelaEmpty()
    {
        List<AmbConfirmacao> listAmbConfirmacao = this.findAll();
        return (listAmbConfirmacao == null || listAmbConfirmacao.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdConfirmaca)
    {
        AmbConfirmacao reg = this.find(pkIdConfirmaca);
        return reg != null;
    }       
}
