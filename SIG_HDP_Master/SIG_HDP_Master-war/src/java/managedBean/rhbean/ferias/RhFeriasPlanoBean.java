/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.ferias;

import entidade.RhDepartamento;
import entidade.RhFerias;
import entidade.RhSeccaoTrabalho;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.RhEstadoFeriasFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFeriasFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhSeccaoTrabalhoFacade;
import util.rh.Defs;
import util.Mensagem;
import util.rh.MetodosGerais;
import util.RelatorioJasper;

/**
 *
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhFeriasPlanoBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    RhEstadoFeriasFacade estadoFeriasFacade;
    @EJB
    RhEstadoFuncionarioFacade estadoFuncionarioFacade;
    @EJB
    RhSeccaoTrabalhoFacade seccaoTrabalhoFacade;
    @EJB
    RhFeriasFacade feriasFacade;
    @EJB
    RhFuncionarioFacade funcionarioFacade;

    /**
     * Entidades
     */
    private RhFerias feriasPesquisa, feriasVisualizar;
    private List<RhFerias> feriasPesquisadasList;
    private Integer anoPesquisa;
    private RhSeccaoTrabalho seccaoTrabalho;
    private List<RhSeccaoTrabalho> seccaoTrabalhoList;

    /**
     * Creates a new instance of PlanoFeriasBean
     */
    public RhFeriasPlanoBean ()
    {
    }

    public RhFerias getFeriasPesquisa ()
    {
        if (feriasPesquisa == null)
        {
            feriasPesquisa = RhFeriasNovasBean.getInstancia();
        }
        return feriasPesquisa;
    }

    public void setFeriasPesquisa (RhFerias feriasPesquisa)
    {
        this.feriasPesquisa = feriasPesquisa;
    }

    public RhFerias getFeriasVisualizar ()
    {
        if (feriasVisualizar == null)
        {
            feriasVisualizar = RhFeriasNovasBean.getInstancia();
        }

        return feriasVisualizar;
    }

    public void setFeriasVisualizar (RhFerias feriasVisualizar)
    {
        this.feriasVisualizar = feriasVisualizar;
    }

    public List<RhFerias> getFeriasPesquisadasList ()
    {
        return feriasPesquisadasList;
    }

    public Integer getAnoPesquisa ()
    {
        return anoPesquisa;
    }

    public void setAnoPesquisa (Integer anoPesquisa)
    {
        this.anoPesquisa = anoPesquisa;
    }

    public String limparPesquisa ()
    {
        feriasVisualizar = null;
        feriasPesquisadasList = null;
        anoPesquisa = null;

        return "feriasPlanoRh.xhtml?faces-redirect=true";
    }

    public RhSeccaoTrabalho getSeccaoTrabalho ()
    {
        if (seccaoTrabalho == null)
        {
            seccaoTrabalho = new RhSeccaoTrabalho();
            seccaoTrabalho.setFkIdDepartamento(new RhDepartamento());
        }
        return seccaoTrabalho;
    }

    public void setSeccaoTrabalho (RhSeccaoTrabalho seccaoTrabalho)
    {
        this.seccaoTrabalho = seccaoTrabalho;
    }

    public List<RhSeccaoTrabalho> getSeccaoTrabalhoList ()
    {
        return seccaoTrabalhoList;
    }

    public void pesquisar ()
    {
        try
        {
            Date dataJaneiro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + anoPesquisa);
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + anoPesquisa);

            feriasPesquisadasList = feriasFacade.findFeriasSeccaoTrabalho(dataJaneiro, dataDezembro, seccaoTrabalho);
            if (feriasPesquisadasList.isEmpty())
            {
                Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
            }
            else
            {
                Mensagem.sucessoMsg("Número de registros encontrados (" + feriasPesquisadasList.size() + ")");
            }
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void changeDepartamento (ValueChangeEvent e)
    {
        getSeccaoTrabalho().getFkIdDepartamento().setPkIdDepartamento((Integer) e.getNewValue());

        seccaoTrabalhoList = null;

        if (seccaoTrabalho.getFkIdDepartamento().getPkIdDepartamento() != null)
        {
            seccaoTrabalhoList = seccaoTrabalhoFacade.pesquisaPorDepartamento((Integer) e.getNewValue());
        }
    }

    public String obterMes (Date data)
    {
        return MetodosGerais.obterNomeDoMes(data);
    }

    public void cancelarFerias ()
    {
        try
        {
            userTransaction.begin();

            feriasVisualizar.setFkIdEstadoFerias(estadoFeriasFacade.pesquisaPorDescricao(Defs.RH_CANCELADO).get(0));

            if (feriasVisualizar.getFkIdFuncionario().getFkIdEstadoFuncionario().getDescricao().equals(Defs.RH_LICENCA_LIMITADA))
            {
                feriasVisualizar.getFkIdFuncionario().setFkIdEstadoFuncionario(estadoFuncionarioFacade.pesquisaPorDescricao(Defs.RH_ACTIVO).get(0));
            }

            funcionarioFacade.edit(feriasVisualizar.getFkIdFuncionario());
            feriasFacade.remove(feriasVisualizar);

            userTransaction.commit();

            Mensagem.sucessoMsg("Férias canceladas com sucesso!");
            pesquisar();
        }
        catch (Exception e)
        {
            try
            {
                e.printStackTrace();
                Mensagem.erroMsg(e.toString());
                userTransaction.rollback();
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                e.printStackTrace();
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

    }

    public boolean disabledCancelarFerias ()
    {
        if (feriasVisualizar == null)
        {
            return false;
        }

        return (Defs.RH_TERMINADO).equalsIgnoreCase(feriasVisualizar.getFkIdEstadoFerias().getDescricao())
               || (Defs.RH_CANCELADO).equalsIgnoreCase(feriasVisualizar.getFkIdEstadoFerias().getDescricao());
    }

    /**
     * Prepara o plano global de férias de um determinado ano para a impressão
     * em formato PDF
     *
     * @param evt
     */
    public void imprimirPlanoGlobalPDF (ActionEvent evt)
    {
        HashMap<String, Object> parametrosMap = new HashMap<>();
        parametrosMap.put("ANO_PLANO_FERIAS", anoPesquisa);

        List<RhFerias> planoDeFerias = new ArrayList<>();

        if (feriasPesquisadasList != null)
        {
            for (RhFerias fr : feriasPesquisadasList)
            {
                if (!fr.getFkIdEstadoFerias().getDescricao().equals(Defs.RH_CANCELADO))
                {
                    planoDeFerias.add(fr);
                }
            }
        }

        RelatorioJasper.exportPDF("rh/planoGlobalDeFerias.jasper", parametrosMap, planoDeFerias);
    }

}
