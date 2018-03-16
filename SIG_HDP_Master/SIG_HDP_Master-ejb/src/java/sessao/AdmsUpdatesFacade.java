/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AdmsUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gemix
 */
@Stateless
public class AdmsUpdatesFacade extends AbstractFacade<AdmsUpdates>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AdmsUpdatesFacade()
    {
        super(AdmsUpdates.class);
    }
    
    public AdmsUpdates obterRegistoUpdates()
    {
        List<AdmsUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }
    
    public Date dataTipoDeServico()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDeServico();
    }
    
    public Date dataGrupoServico()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getGrupoDeServico();
    }
    
    
    public Date dataCategoriaServico()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCategoriaServico();
    }

//    public Date dataAreaInterna()
//    {
//        AdmsUpdates reg = obterRegistoUpdates();
//        return reg == null ? null : reg.getArea();
//    }
    
    
    public Date dataServico()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getServico();
    }
    
    public Date dataClassificacaoServicoSolicitado()
    {
        System.out.println("ola  ");
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getClassificacaoServicoSolicitado();
    }
    
    public Date dataEstadoAgendamento()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoAgendamento();
    }
    
    public Date dataEstadoPagamento()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoPagamento();
    }
    
    public Date dataTipoSolicitacaoServico()
    {
        AdmsUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoSolicitacaoServico();
    }

    
    
    
    
    public void escreverDataActualizacaoTipoServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setTipoDeServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setTipoDeServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoGrupoServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setGrupoDeServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setGrupoDeServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
//    public void escreverDataActualizacaoAreaInternaTabela()
//    {
//        AdmsUpdates reg = obterRegistoUpdates();
//        
//        if (reg == null)
//        {
//            reg = new AdmsUpdates();
//            reg.setArea(new Date());
//            this.create(reg);
//        }
//        else
//        {
//            reg.setArea(new Date());
//            this.edit(reg);
//        }
//    }

    
    public void escreverDataActualizacaoServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataActualizacaoCategoriaServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setCategoriaServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setCategoriaServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataEstadoAgendamentoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setEstadoAgendamento(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setEstadoAgendamento(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataEstadoPagamentoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setEstadoPagamento(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setEstadoPagamento(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataTipoSolicitacaoServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setTipoSolicitacaoServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setTipoSolicitacaoServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataClassificacaoServicoSolicitadoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setClassificacaoServicoSolicitado(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setClassificacaoServicoSolicitado(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
    
    public void escreverDataCategoriaServicoTabela(Date dataDaAtualizacao)
    {
        System.out.println("ola");
        AdmsUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AdmsUpdates();
            reg.setCategoriaServico(dataDaAtualizacao);
            this.create(reg);
        }
        else
        {
            reg.setCategoriaServico(dataDaAtualizacao);
            this.edit(reg);
        }
    }
    
}
