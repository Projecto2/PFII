/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessao;

import entidade.AmbCidUpdates;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aires
 */
@Stateless
public class AmbCidUpdatesFacade extends AbstractFacade<AmbCidUpdates>
{

    @PersistenceContext(unitName = "SIG_HDP_Master-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public AmbCidUpdatesFacade()
    {
        super(AmbCidUpdates.class);
    }

    public AmbCidUpdates obterRegistoCidUpdates()
    {
        List<AmbCidUpdates> list = this.findAll();
        if (list == null || list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    public Date dataCapitulosTabela()
    {
//System.err.println("01: AmbCidUpdatesFacade.dataCapitulosTabela()");
        AmbCidUpdates reg = obterRegistoCidUpdates();

//System.err.println("02: AmbCidUpdatesFacade.dataCapitulosTabela()");
        return reg == null ? null : reg.getCapitulos();
    }

    public Date dataAgrupamentosTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        return reg == null ? null : reg.getAgrupamentos();
    }

    public Date dataCategoriasTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        return reg == null ? null : reg.getCategorias();
    }

    public Date dataSubcategoriasTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        return reg == null ? null : reg.getSubcategorias();
    }

    public Date dataDoencasPrioridadesTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        return reg == null ? null : reg.getDoencasPrioridades();
    }

    public Date dataPerfilTiposTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        return reg == null ? null : reg.getPerfilTipos();
    }

    public void escreverDataActualizacaoCapitulosTabela()
    {
//System.err.println("0: AmbCidUpdatesFacade.escreverDataActualizacaoCapitulosTabela()");
        AmbCidUpdates reg = obterRegistoCidUpdates();

        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoCapitulosTabela()");
            reg = new AmbCidUpdates();
            reg.setCapitulos(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoCapitulosTabela()");
            reg.setCapitulos(new Date());
            this.edit(reg);
        }
//System.err.println("3: AmbCidUpdatesFacade.escreverDataActualizacaoCapitulosTabela()");
    }

    public void escreverDataActualizacaoAgrupamentosTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoAgrupamentosTabela()");
            reg = new AmbCidUpdates();
            reg.setAgrupamentos(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoAgrupamentosTabela()");
            reg.setAgrupamentos(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoCategoriasTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoCategoriasTabela()");
            reg = new AmbCidUpdates();
            reg.setCategorias(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoCategoriasTabela()");
            reg.setCategorias(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoSubcategoriasTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg = new AmbCidUpdates();
            reg.setSubcategorias(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg.setSubcategorias(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoDoencasPrioridadesTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg = new AmbCidUpdates();
            reg.setDoencasPrioridades(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg.setDoencasPrioridades(new Date());
            this.edit(reg);
        }
    }

    public void escreverDataActualizacaoPerfilTiposTabela()
    {
        AmbCidUpdates reg = obterRegistoCidUpdates();
        if (reg == null)
        {
//System.err.println("1: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg = new AmbCidUpdates();
            reg.setPerfilTipos(new Date());
            this.create(reg);
        }
        else
        {
//System.err.println("2: AmbCidUpdatesFacade.escreverDataActualizacaoSubCategoriasTabela()");
            reg.setPerfilTipos(new Date());
            this.edit(reg);
        }
    }
}
