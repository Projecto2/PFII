/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsSubprocesso;
import entidade.AdmsTipoPagamentoSubprocesso;
import entidade.FinTipoPagamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsTipoPagamentoSubprocessoFacade extends AbstractFacade<AdmsTipoPagamentoSubprocesso>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsTipoPagamentoSubprocessoFacade()
    {
        super(AdmsTipoPagamentoSubprocesso.class);
    }
    
    public FinTipoPagamento findTipoPagamentoBySubprocesso(AdmsSubprocesso subprocesso)
    {
        Query qry = em.createQuery("SELECT tipoPagamentoSubProcesso FROM AdmsTipoPagamentoSubprocesso tipoPagamentoSubProcesso WHERE tipoPagamentoSubProcesso.fkIdSubprocesso.pkIdSubprocesso = :idSubprocesso");

        qry.setParameter("idSubprocesso", subprocesso.getPkIdSubprocesso());

        List<AdmsTipoPagamentoSubprocesso> encargo = qry.getResultList();

        if (encargo.isEmpty())
        {
            return null;
        }

        return encargo.get(0).getFkIdTipoPagamento();
    }
    
}
