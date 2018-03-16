/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.TbTipoNotificacao;
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
public class TbTipoNotificacaoFacade extends AbstractFacade<TbTipoNotificacao>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TbTipoNotificacaoFacade()
    {
        super(TbTipoNotificacao.class);
    }

    public boolean isTipoNotificacaoTabelaEmpty()
    {
        List<TbTipoNotificacao> listTbTipoNotificacao = this.findAll();
        return (listTbTipoNotificacao == null || listTbTipoNotificacao.isEmpty());
    }

    public boolean existeRegisto(int pkTipoNotificacao)
    {
        TbTipoNotificacao reg = this.find(pkTipoNotificacao);
        return reg != null;
    }

    public List<TbTipoNotificacao> findTipoNotificacao(TbTipoNotificacao tipoNotificacao)
    {
        String query = constroiQuery(tipoNotificacao);

        TypedQuery<TbTipoNotificacao> t = em.createQuery(query, TbTipoNotificacao.class);

        if (tipoNotificacao.getDescricao() != null && !(tipoNotificacao.getDescricao().isEmpty()))
        {
            t.setParameter("descricao", tipoNotificacao.getDescricao() + "%");
        }

        List<TbTipoNotificacao> tipoNotificacoes = t.getResultList();

        return tipoNotificacoes;
    }

    public String constroiQuery(TbTipoNotificacao tipoNotificacao)
    {
        String query = "SELECT a FROM TbTipoNotificacao a WHERE a.pkTipoNotificacao = a.pkTipoNotificacao";

        if (tipoNotificacao.getDescricao() != null && !(tipoNotificacao.getDescricao().isEmpty()))
        {
            query += " AND a.descricao LIKE :descricao";
        }

        query += " ORDER BY a.descricao";

        return query;
    }

}
