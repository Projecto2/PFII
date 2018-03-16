/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.DiagUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mauro
 */
@Stateless
public class DiagUpdatesFacade extends AbstractFacade<DiagUpdates>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public DiagUpdatesFacade()
    {
        super(DiagUpdates.class);
    }

    public Date dataCategoriaExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCategoriaExame();
    }

    public Date dataSubcategoriaExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSubcategoriaExame();
    }

    public Date dataTipoAmostraTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoAmostra();
    }

    public Date dataGrupoSanguineoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getGrupoSanguineo();
    }

    public Date dataComponenteSanguineoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getComponenteSanguineo();
    }
    
    public Date dataExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getExame();
    }
    
    public Date dataCaracterTransfusaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCaracterTransfusao();
    }
    
    public Date dataEstadoClinicoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoClinico();
    }
    
    public Date dataNumeroDoacaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getNumeroDoacao();
    }
    
    public Date dataRespostasQuestoesRequisicaoComponentesTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getRespostasQuestoesRequisicaoComponentes();
    }
    
    public Date dataResultadoExameCandidatoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getResultadoExameCandidato();
    }
    
    public Date dataResultadoTesteCompatibilidadeTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getResultadoTesteCompatibilidade();
    }
    
    public Date dataResultadoTriagemTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getResultadoTriagem();
    }
    
    public Date dataSectorExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSectorExame();
    }
    
    public Date dataTipoDoacaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDoacao();
    }
    
    public Date dataTipoResultadoExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoResultadoExame();
    }

    public DiagUpdates obterRegistoUpdates()
    {
        List<DiagUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public void escreverDataActualizacaoCategoriaExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setCategoriaExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCategoriaExame(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoSubcategoriaExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setSubcategoriaExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSubcategoriaExame(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoAmostraTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setTipoAmostra(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoAmostra(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoGrupoSanguineoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setGrupoSanguineo(new Date());
            this.create(reg);
        }
        else
        {
            reg.setGrupoSanguineo(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoComponenteSanguineoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setComponenteSanguineo(new Date());
            this.create(reg);
        }
        else
        {
            reg.setComponenteSanguineo(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setExame(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoCaracterTransfusaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setCaracterTransfusao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCaracterTransfusao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoEstadoClinicoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setEstadoClinico(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoClinico(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoNumeroDoacaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setNumeroDoacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setNumeroDoacao(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoRespostasQuestoesRequisicaoComponentesTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setRespostasQuestoesRequisicaoComponentes(new Date());
            this.create(reg);
        }
        else
        {
            reg.setRespostasQuestoesRequisicaoComponentes(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoResultadoExameCandidatoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setResultadoExameCandidato(new Date());
            this.create(reg);
        }
        else
        {
            reg.setResultadoExameCandidato(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoResultadoTesteCompatibilidadeTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setResultadoTesteCompatibilidade(new Date());
            this.create(reg);
        }
        else
        {
            reg.setResultadoTesteCompatibilidade(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoResultadoTriagemTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setResultadoTriagem(new Date());
            this.create(reg);
        }
        else
        {
            reg.setResultadoTriagem(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoSectorExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setSectorExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSectorExame(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoTipoDoacaoTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setTipoDoacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoDoacao(new Date());
            this.edit(reg);
        }
    }
 
    public void escreverDataActualizacaoTipoResultadoExameTabela()
    {
        DiagUpdates reg = obterRegistoUpdates();
        if (reg == null)
        {
            reg = new DiagUpdates();
            reg.setTipoResultadoExame(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoResultadoExame(new Date());
            this.edit(reg);
        }
    }

}
