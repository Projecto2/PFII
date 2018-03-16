/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.AmbCeUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ivandro-colombo
 */
@Stateless
public class AmbCeUpdatesFacade extends AbstractFacade<AmbCeUpdates>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCeUpdatesFacade()
    {
        super(AmbCeUpdates.class);
    }
    
    public AmbCeUpdates obterRegistoUpdates()
    {
        List<AmbCeUpdates> list = this.findAll();
        
        if (list == null || list.isEmpty())
        {
            return null;
        }
        
        return list.get(0);
    }
    
    public Date dataAderencia()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getAderencia();
    }
    
    public Date dataClassificacaoDor()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getClassificacaoDor();
    }
       
    public Date dataColoracao()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getColoracao();
    }
    
    public Date dataConfirmacao()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getConfirmacao();
    }    
    
    public Date dataConsultorio()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getConsultorio();
    }
    
    public Date dataCor()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getCor();
    }
    
    public Date dataEspessura()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getEspessura();
    }
    
    public Date dataEstado()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getEstado();
    }
    
    public Date dataEstadoHidratacao()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getEstadoHidratacao();
    }
    
    public Date dataEstadoNotificacao()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getEstadoNotificacao();
    }    
    
    public Date dataEstadoPagamento()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getEstadoPagamento();
    }
    
    public Date dataImpressoesGerais()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getImpressoesGerais();
    }
    
    public Date dataObservacoesMedicas()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getObservacoesMedicas();
    }
    
    public Date dataSinal()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getSinal();
    }
    
    public Date dataTamanho()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getTamanho();
    }
    
    public Date dataTextura()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getTextura();
    }

    public Date dataTurgor()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        return reg == null ? null : reg.getTurgor();
    }

    public void escreverDataAderenciaTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setAderencia(new Date());
            this.create(reg);
        }
        else
        {
            reg.setAderencia(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataClassificacaoDorTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setClassificacaoDor(new Date());
            this.create(reg);
        }
        else
        {
            reg.setClassificacaoDor(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataColoracaoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setColoracao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setColoracao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataConfirmacaoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setConfirmacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setConfirmacao(new Date());
            this.edit(reg);
        }
    }    
    
    public void escreverDataConsultorioTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setConsultorio(new Date());
            this.create(reg);
        }
        else
        {
            reg.setConsultorio(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataCorTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setCor(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCor(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataEspessuraTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setEspessura(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEspessura(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataEstadoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setEstado(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstado(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataEstadoHidratacaoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setEstadoHidratacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoHidratacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataEstadoNotificacaoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setEstadoNotificacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoNotificacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataEstadoPagamentoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setEstadoPagamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoPagamento(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataImpressoesGeraisTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setImpressoesGerais(new Date());
            this.create(reg);
        }
        else
        {
            reg.setImpressoesGerais(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataObservacoesMedicasTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setObservacoesMedicas(new Date());
            this.create(reg);
        }
        else
        {
            reg.setObservacoesMedicas(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataSinalTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setSinal(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSinal(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataTamanhoTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setTamanho(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTamanho(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataTexturaTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setTextura(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTextura(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataTurgorTabela()
    {
        AmbCeUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new AmbCeUpdates();
            reg.setTurgor(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTurgor(new Date());
            this.edit(reg);
        }
    }
}
