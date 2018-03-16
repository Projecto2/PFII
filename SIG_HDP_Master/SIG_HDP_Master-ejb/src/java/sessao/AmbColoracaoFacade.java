/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbColoracao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbColoracaoFacade extends AbstractFacade<AmbColoracao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbColoracaoFacade()
    {
        super(AmbColoracao.class);
    }
    
    public boolean isColoracaoTabelaEmpty()
    {
        List<AmbColoracao> listAmbColoracao = this.findAll();
        return (listAmbColoracao == null || listAmbColoracao.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdColoracao)
    {
        AmbColoracao reg = this.find(pkIdColoracao);
        return reg != null;
    }
}
