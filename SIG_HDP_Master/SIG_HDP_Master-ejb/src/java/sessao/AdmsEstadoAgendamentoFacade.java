/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsEstadoAgendamento;
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
public class AdmsEstadoAgendamentoFacade extends AbstractFacade<AdmsEstadoAgendamento>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsEstadoAgendamentoFacade()
    {
        super(AdmsEstadoAgendamento.class);
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamentoChegou()
    {
        return getEstadoAgendamento("Chegou");
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamentoAgendado()
    {
        return getEstadoAgendamento("Agendado");
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamentoCancelado()
    {
        return getEstadoAgendamento("Cancelado");
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamentoEfetuado()
    {
        return getEstadoAgendamento("Efetuado");
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamentoNaoApareceu()
    {
        return getEstadoAgendamento("Não Apareceu");
    }
    
    public AdmsEstadoAgendamento getEstadoAgendamento(String estado)
    {
        Query query = em.createQuery("SELECT estado from AdmsEstadoAgendamento estado WHERE estado.descricaoEstadoAgendamento = :estado");
        
        query.setParameter("estado", estado);
        
        List<AdmsEstadoAgendamento> estados = query.getResultList();
        if(estados.isEmpty()) return null;
        return estados.get(0);
    }
    
    public List<AdmsEstadoAgendamento> findEstadosAgendamentoSemCancelado()
    {
        Query query = em.createQuery("SELECT estado from AdmsEstadoAgendamento estado WHERE estado.descricaoEstadoAgendamento <> 'Cancelado'");
        
        List<AdmsEstadoAgendamento> estados = query.getResultList();
        return estados;
    }
    
    public boolean isEstadoAgendamentoTabelaEmpty()
    {
        List<AdmsEstadoAgendamento> listEstadoAgendamento = this.findAll();
        return (listEstadoAgendamento == null || listEstadoAgendamento.isEmpty());
    }
    
    public boolean existeRegisto(int pkIdEstadoAgendamento)
    {
        AdmsEstadoAgendamento reg = this.find(pkIdEstadoAgendamento);
        return reg != null;
    }
    
}
