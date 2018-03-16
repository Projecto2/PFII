/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessao;

import entidade.RhUpdates;
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
public class RhUpdatesFacade extends AbstractFacade<RhUpdates>
{
    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager ()
    {
        return em;
    }

    public RhUpdatesFacade ()
    {
        super(RhUpdates.class);
    }

    public RhUpdates obterRegistoUpdates()
    {
        List<RhUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public Date dataCategoriaProfissional ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCategoriaProfissional();
    }

    public Date dataClassificacaoDoCriterio ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getClassificacaoDoCriterio();
    }
    
    public Date dataCriterioDeAvaliacao ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getCriterioDeAvaliacao();
    }
    
    public Date dataDepartamento ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getDepartamento();
    }

    public Date dataEstadoCandidato ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoCandidato();
    }
    
    public Date dataEstadoContrato ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoContrato();
    }

    public Date dataEstadoEstagiario ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoEstagiario();
    }

    public Date dataEstadoFerias ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoFerias();
    }

    public Date dataEstadoFuncionario ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getEstadoFuncionario();
    }

    public Date dataFormaPagamento ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getFormaPagamento();
    }

    public Date dataFuncao ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getFuncao();
    }

    public Date dataNivelAcademico ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getNivelAcademico();
    }

    public Date dataPeriodoAulas ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getPeriodoAulas();
    }

    public Date dataProfissao ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getProfissao();
    }

    public Date dataTipoDeHorarioTrabalho ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoDeHorarioTrabalho();
    }
    
    public Date dataSeccaoTrabalho ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSeccaoTrabalho();
    }

    public Date dataSubsidio ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getSubsidio();
    }

    public Date dataTipoContrato ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoContrato();
    }

    public Date dataTipoEstagio ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoEstagio();
    }

    public Date dataTipoFalta ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoFalta();
    }

    public Date dataTipoFuncionario ()
    {
        RhUpdates reg = obterRegistoUpdates();
        return reg == null ? null : reg.getTipoFuncionario();
    }
    

    public void escreverDataActualizacaoCategoriaProfissionalTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setCategoriaProfissional(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCategoriaProfissional(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoClassificacaoDoCriterioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setClassificacaoDoCriterio(new Date());
            this.create(reg);
        }
        else
        {
            reg.setClassificacaoDoCriterio(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoCriterioDeAvaliacaoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setCriterioDeAvaliacao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setCriterioDeAvaliacao(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoDepartamentoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setDepartamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setDepartamento(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEstadoCandidatoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setEstadoCandidato(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoCandidato(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoEstadoContratoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setEstadoContrato(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoContrato(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEstadoEstagiarioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setEstadoEstagiario(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoEstagiario(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEstadoFeriasTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setEstadoFerias(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoFerias(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoEstadoFuncionarioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setEstadoFuncionario(new Date());
            this.create(reg);
        }
        else
        {
            reg.setEstadoFuncionario(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoFuncaoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setFuncao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setFuncao(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoFormaPagamentoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setFormaPagamento(new Date());
            this.create(reg);
        }
        else
        {
            reg.setFormaPagamento(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoNivelAcademicoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setNivelAcademico(new Date());
            this.create(reg);
        }
        else
        {
            reg.setNivelAcademico(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoPeriodoAulasTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setPeriodoAulas(new Date());
            this.create(reg);
        }
        else
        {
            reg.setPeriodoAulas(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoProfissaoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setProfissao(new Date());
            this.create(reg);
        }
        else
        {
            reg.setProfissao(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoDeHorarioTrabalhoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setTipoDeHorarioTrabalho(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoDeHorarioTrabalho(new Date());
            this.edit(reg);
        }
    }
    
    public void escreverDataActualizacaoSeccaoTrabalhoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setSeccaoTrabalho(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSeccaoTrabalho(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoSubsidioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setSubsidio(new Date());
            this.create(reg);
        }
        else
        {
            reg.setSubsidio(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoContratoTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setTipoContrato(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoContrato(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoEstagioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setTipoEstagio(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoEstagio(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoFaltaTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setTipoFalta(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoFalta(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoTipoFuncionarioTabela ()
    {
        RhUpdates reg = obterRegistoUpdates();
        
        if (reg == null)
        {
            reg = new RhUpdates();
            reg.setTipoFuncionario(new Date());
            this.create(reg);
        }
        else
        {
            reg.setTipoFuncionario(new Date());
            this.edit(reg);
        }
    }
    
    
    
}
