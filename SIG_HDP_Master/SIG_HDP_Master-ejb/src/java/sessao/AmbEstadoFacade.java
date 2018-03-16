/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbEstado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbEstadoFacade extends AbstractFacade<AmbEstado>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbEstadoFacade()
    {
        super(AmbEstado.class);
    }
    
    public boolean isEstadoTabelaEmpty()
    {
        List<AmbEstado> listAmbEstado = this.findAll();
        return (listAmbEstado == null || listAmbEstado.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEstado)
    {
        AmbEstado reg = this.find(pkIdEstado);
        return reg != null;
    }
    
    public AmbEstado findByDescricao(String nome)
    {
        TypedQuery<AmbEstado> q = em.createQuery("SELECT p FROM AmbEstado p WHERE p.descricao = :nome",
                AmbEstado.class).setParameter("nome", nome);
        List<AmbEstado> list = q.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
