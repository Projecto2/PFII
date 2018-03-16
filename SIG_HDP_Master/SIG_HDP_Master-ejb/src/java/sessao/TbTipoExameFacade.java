/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.TbTipoExame;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author adelino
 */
@Stateless
public class TbTipoExameFacade extends AbstractFacade<TbTipoExame> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbTipoExameFacade() {
        super(TbTipoExame.class);
    }
    
    public boolean isTipoExameTabelaEmpty()
    {
        List<TbTipoExame> listTbTipoExame = this.findAll();
        return (listTbTipoExame == null || listTbTipoExame.isEmpty());
    }
    
    public boolean existeRegisto(int pkTipoExame)
    {
        TbTipoExame reg = this.find(pkTipoExame);
        return reg != null;
    }
    
    public List<TbTipoExame> findTipoExame(TbTipoExame tipoExame)
    {
        String query = constroiQuery(tipoExame);

        TypedQuery<TbTipoExame> t = em.createQuery(query, TbTipoExame.class);

        if (tipoExame.getDescricao() != null && !(tipoExame.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", tipoExame.getDescricao() + "%");
        }

        List<TbTipoExame> tipoExames = t.getResultList();

        return tipoExames;
    }

    public String constroiQuery(TbTipoExame tipoExame)
    {
        String query = "SELECT a FROM TbTipoExame a WHERE a.pkTipoExame = a.pkTipoExame";

        if (tipoExame.getDescricao() != null && !(tipoExame.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
