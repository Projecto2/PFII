/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.GrlGrauParentesco;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gemix
 */
@Stateless
public class GrlGrauParentescoFacade extends AbstractFacade<GrlGrauParentesco>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public GrlGrauParentescoFacade()
    {
        super(GrlGrauParentesco.class);
    }
    
    public boolean isGrlGrauParentescoTabelaEmpty()
    {
        List<GrlGrauParentesco> listGrlGrauParentesco = this.findAll();
        return (listGrlGrauParentesco == null || listGrlGrauParentesco.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdGrlGrauParentesco)
    {
        GrlGrauParentesco reg = this.find(pkIdGrlGrauParentesco);
        return reg != null;
    }
}
