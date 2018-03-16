/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhNotificacao;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ornela F. Boaventura
 */
@Stateless
public class RhNotificacaoFacade extends AbstractFacade<RhNotificacao>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhNotificacaoFacade ()
    {
        super(RhNotificacao.class);
    }

    /**
     * Retorna todas as notificações não lidas
     * @return
     */
    public List<RhNotificacao> findAllNaoLidas ()
    {
        TypedQuery<RhNotificacao> query;
        query = em.createQuery("SELECT n FROM RhNotificacao n WHERE n.lida = :lida",RhNotificacao.class)
                .setParameter("lida", false);
        return query.getResultList();
    }
    
    /**
     * Retorna todas as notificações lidas
     * @return
     */
    public List<RhNotificacao> findAllLidas ()
    {
        TypedQuery<RhNotificacao> query;
        query = em.createQuery("SELECT n FROM RhNotificacao n WHERE n.lida = :lida",RhNotificacao.class)
                .setParameter("lida", true);
        return query.getResultList();
    }
    
    /**
     * Retorna todas as notificações não lidas ordenadas 
     * pela data de notificação
     * 
     * @return
     */
    public List<RhNotificacao> findNaoLidasOrdenadas ()
    {
        TypedQuery<RhNotificacao> query;
        query = em.createQuery("SELECT n FROM RhNotificacao n WHERE n.lida = :naoLida ORDER BY n.dataDeNotificacao DESC",RhNotificacao.class).setParameter("naoLida", false);
        return query.getResultList();
    }
    
    /**
     * Retorna todas as notificações ordenadas 
     * pela data de notificação
     * 
     * @return
     */
    public List<RhNotificacao> findAllOrdenadas ()
    {
        TypedQuery<RhNotificacao> query;
        query = em.createQuery("SELECT n FROM RhNotificacao n ORDER BY n.dataDeNotificacao DESC",RhNotificacao.class);
        return query.getResultList();
    }
    
    /**
     * Procura todas as notificações de acordo com um determinado texto 
     * ou data
     * 
     * @param textoPesquisa
     * @param data
     * @return
     */
    public List<RhNotificacao> findNotificacao (String textoPesquisa, Date data)
    {
        TypedQuery<RhNotificacao> query;
        
        if(textoPesquisa == null)
            textoPesquisa = "";
        
        query = em.createQuery("SELECT n FROM RhNotificacao n WHERE "
                + "LOWER(n.assunto) LIKE LOWER(:textoPesquisa) OR "
                + "LOWER(n.descricao) LIKE LOWER(:textoPesquisa) OR "
                + "n.dataDeEvento = :data "
                + "ORDER BY n.dataDeNotificacao DESC", RhNotificacao.class).setParameter("data", data)
                .setParameter("textoPesquisa", "%"+(textoPesquisa.trim().isEmpty() ? "Sem Texto":textoPesquisa)+"%");

        return query.getResultList();
    }
    
    /**
     * Procura todas as notificações de acordo com um assunto, 
     * data do evento que causou a notificação e o id da entidade que causou
     * o evento notificado
     * 
     * @param assunto 
     * @param dataDeEvento 
     * @param idDaEntidadeDoEvento 
     * @return
     */
    public List<RhNotificacao> findNotificacao (String assunto, Date dataDeEvento, int idDaEntidadeDoEvento)
    {
        TypedQuery<RhNotificacao> query;
        query = em.createQuery("SELECT n FROM RhNotificacao n WHERE "
                + "n.assunto LIKE :assunto AND n.dataDeEvento = :dataDeEvento AND "
                + "n.idDaEntidadeDoEvento = :idDaEntidadeDoEvento", RhNotificacao.class)
                .setParameter("assunto", assunto)
                .setParameter("dataDeEvento", dataDeEvento)
                .setParameter("idDaEntidadeDoEvento", idDaEntidadeDoEvento);

        return query.getResultList();        
    }
    
}
