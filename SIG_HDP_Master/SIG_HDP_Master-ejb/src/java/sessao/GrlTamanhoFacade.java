/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlTamanho;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class GrlTamanhoFacade extends AbstractFacade<GrlTamanho>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlTamanhoFacade()
    {
        super(GrlTamanho.class);
    }
    
    public boolean isTamanhoTabelaEmpty()
    {
        List<GrlTamanho> listGrlTamanho = this.findAll();
        return (listGrlTamanho == null || listGrlTamanho.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdTamanho)
    {
        GrlTamanho reg = this.find(pkIdTamanho);
        return reg != null;
    }
}
