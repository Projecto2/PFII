/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.InterUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author armindo
 */
@Stateless
public class InterUpdatesFacade extends AbstractFacade<InterUpdates>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public InterUpdatesFacade()
    {
        super(InterUpdates.class);
    }
    
    public Date dataEnfermariaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEnfermaria();
    }
    
    public Date dataSalaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSala();
    }
    
    public Date dataCamaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCama();
    }
    
    public Date dataEstadoCamaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoCama();
    }
    
    public Date dataTipoDoencaInternamentoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDoencaInternamento();
    }
    
    public Date dataTipoNotificacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoNotificacao();
    }
    
    public Date dataTipoAltaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoAlta();
    }
    
    public Date dataHoraMedicacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getHoraMedicacao();
    }
    
    public Date dataOpcaoMedicacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getOpcaoMedicacao();
    }
    
    public Date dataParametroVitalTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getParametroVital();
    }
    
    public Date dataPulsoUnidadeTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getPulsoUnidade();
    }
    
    public Date dataResultadoDoencaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getResultadoDoenca();
    }
    
    public InterUpdates obterRegistoUpdates()
    {
        List<InterUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }
    
    public void escreverDataActualizacaoEnfermariaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setEnfermaria(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEnfermaria(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoSalaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setSala(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSala(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoCamaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setCama(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCama(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoEstadoCamaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setEstadoCama(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoCama(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoTipoDoencaInternamentoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setTipoDoencaInternamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoDoencaInternamento(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoTipoNotificacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setTipoNotificacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoNotificacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoTipoAltaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setTipoAlta(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoAlta(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoHoraMedicacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setHoraMedicacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setHoraMedicacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoOpcaoMedicacaoTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setOpcaoMedicacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setOpcaoMedicacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoParametroVitalTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setParametroVital(new Date());
            this.create(reg);
        }
        else
        {
            reg.setParametroVital(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoPulsoUnidadeTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setPulsoUnidade(new Date());
            this.create(reg);
        }
        else
        {
            reg.setPulsoUnidade(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoResultadoDoencaTabela()
    {
        InterUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new InterUpdates();
            reg.setResultadoDoenca(new Date());
            this.create(reg);
        }
        else
        {
            reg.setResultadoDoenca(new Date());
            this.edit(reg);
        }
    }
}
