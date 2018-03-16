/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.TbUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adelino
 */
@Stateless
public class TbUpdatesFacade extends AbstractFacade<TbUpdates> {
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbUpdatesFacade() {
        super(TbUpdates.class);
    }   
    
    public Date dataClassificacaoDoenca()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getClassificacaoDoenca();
    }
    
    public Date dataDoenteTratamento()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getDoenteTratamento();
    }
    
    public Date dataFaseTratamento()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getFaseTratamento();
    }
    
    public Date dataGrupoRisco()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getGrupoRisco();
    }
    
    public Date dataMedicamento()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getMedicamento();
    }
    
    public Date dataSintoma()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSintoma();
    }
    
    public Date dataSubClassificacaoDoenca()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSubclassificacaoDoenca();
    }
    
    public Date dataTipoDoente()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDoente();
    }
    
    public Date dataTipoExame()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoExame();
    }
    
    public Date dataTipoNotificacao()
    {
        TbUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoNotificacao();
    }
    
    public void escreverDataClassificacaoDoencaTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setClassificacaoDoenca(new Date());
            this.create(reg);
        }
        else
        {
            reg.setClassificacaoDoenca(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataDoenteTratamentoTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setDoenteTratamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setDoenteTratamento(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataFaseTratamentoTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setFaseTratamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setFaseTratamento(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataGrupoRiscoTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setGrupoRisco(new Date());
            this.create(reg);
        }
        else
        {
            reg.setGrupoRisco(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataMedicamentoTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setMedicamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setMedicamento(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataSubClassificacaoDoencaTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setSubclassificacaoDoenca(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSubclassificacaoDoenca(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataTipoDoenteTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setTipoDoente(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoDoente(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataTipoExameTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setTipoExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoExame(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataTipoNotificacaoTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setTipoNotificacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoNotificacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataSintomaTabela()
    {
        TbUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new TbUpdates();
            reg.setSintoma(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSintoma(new Date());
            this.edit(reg);
        }
    }
    
    public TbUpdates obterRegistoUpdates()
    {
        List<TbUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }
    
    
    
    
}
