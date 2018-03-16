/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import Utils.DataUtils;
import entidade.AdmsAgendamento;
import entidade.AmbNotificacoes;
import java.util.Date;
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
public class AmbNotificacoesFacade extends AbstractFacade<AmbNotificacoes>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbNotificacoesFacade()
    {
        super(AmbNotificacoes.class);
    }
}
