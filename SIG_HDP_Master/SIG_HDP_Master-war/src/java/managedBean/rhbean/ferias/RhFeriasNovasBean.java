/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.ferias;

import entidade.RhEstadoFerias;
import entidade.RhFerias;
import entidade.RhFuncionario;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.rhbean.validacao.RhFeriasValidarBean;
import sessao.RhEstadoFeriasFacade;
import sessao.RhEstadoFuncionarioFacade;
import sessao.RhFeriasFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhFuncionarioHasRhTipoFaltaFacade;
import util.rh.Defs;
import util.Mensagem;
import util.rh.MetodosGerais;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@SessionScoped
public class RhFeriasNovasBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhEstadoFeriasFacade estadoFeriasFacade;
    @EJB
    private RhFeriasFacade feriasFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;
    @EJB
    private RhFuncionarioHasRhTipoFaltaFacade funcionarioHasRhTipoFaltaFacade;
    @EJB
    private RhEstadoFuncionarioFacade estadoFuncionarioFacade;

    /**
     * Entidades
     */
    private RhFerias ferias;

    private RhFuncionario funcionarioPesquisa;

    private List<RhFuncionario> funcionariosPesquisados;

    /**
     * Creates a new instance of contratoBean
     */
    public RhFeriasNovasBean ()
    {
    }

    public String limpar ()
    {
        funcionarioPesquisa = null;
        ferias = null;
        funcionariosPesquisados = null;
        return "feriasNovasRh.xhtml?faces-redirect=true";
    }

    public static RhFerias getInstancia ()
    {
        RhFerias fer = new RhFerias();

        fer.setFkIdEstadoFerias(new RhEstadoFerias());
        fer.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return fer;
    }

    public RhFerias getFerias ()
    {
        if (ferias == null)
        {
            ferias = getInstancia();
            ferias.setDataInicio(MetodosGerais.getDataDeHojeSemHora());
        }
        return ferias;
    }

    public RhFuncionario getFuncionarioPesquisa ()
    {
        if (funcionarioPesquisa == null)
        {
            funcionarioPesquisa = RhFuncionarioNovoBean.getInstancia();
        }
        return funcionarioPesquisa;
    }

    public void setFuncionarioPesquisa (RhFuncionario funcionarioPesquisa)
    {
        this.funcionarioPesquisa = funcionarioPesquisa;
    }

    public List<RhFuncionario> getFuncionariosPesquisados ()
    {
        return funcionariosPesquisados;
    }

    public void limparPesquisaFuncionarios ()
    {
        funcionariosPesquisados = null;
        funcionarioPesquisa = null;
    }

    public String create ()
    {
        try
        {
            RhFeriasValidarBean feriasValidar = RhFeriasValidarBean.getInstanciaBean();

            //Realiza as validações
            if (!feriasValidar.validarNovas(ferias))
            {
                return null;
            }

            userTransaction.begin();

            ferias.setDataCadastro(Calendar.getInstance().getTime());

            ferias.setFkIdEstadoFerias(estadoFeriasFacade.pesquisaPorDescricao(Defs.RH_MARCADO).get(0));
            ferias.setMes(MetodosGerais.obterNumeroDoMes(ferias.getDataInicio()));

            feriasFacade.create(ferias);

            userTransaction.commit();

            Mensagem.sucessoMsg("Férias marcadas com sucesso!");
            limpar();
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

        return null;
    }

    public void changeDiasGozar (AjaxBehaviorEvent e)
    {
        try
        {
            selecionarFuncionario(ferias.getFkIdFuncionario());
            Date data = MetodosGerais.calcularProximaData(ferias.getDataInicio(), ferias.getDiasGozar());
            ferias.setDataTermino(data);
        }
        catch (Exception ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
            ferias.setDataTermino(null);
        }
    }

    public String sair ()
    {
        limpar();

        return "feriasPlanoRh.xhtml?faces-redirect=true";
    }

    public void pesquisarFuncionarios ()
    {
        List<RhFuncionario> listFuncionarios = funcionarioFacade.findFuncionario(funcionarioPesquisa);

        funcionariosPesquisados = new ArrayList<>();

        Integer anoFerias = anoInicioDeFerias();
        
        for (RhFuncionario f : listFuncionarios)
        {
            List<RhFerias> feriasExistentes;
            
            feriasExistentes = feriasFacade.findFeriasActivasOuMarcadasFuncionario(f.getPkIdFuncionario(), MetodosGerais.getPrimeiraDataDoAno(anoFerias), MetodosGerais.getUltimaDataDoAno(anoFerias));
            
            if (feriasExistentes.isEmpty())
            {
                String estado = f.getFkIdEstadoFuncionario().getDescricao();
                if (!estado.equalsIgnoreCase(Defs.RH_REFORMADO) && !estado.equalsIgnoreCase(Defs.RH_DEMITIDO))
                {
                    funcionariosPesquisados.add(f);
                }
            }
        }

        if (funcionariosPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public void selecionarFuncionario (RhFuncionario funcionario)
    {
        try
        {
            Integer anoInicioFerias = anoInicioDeFerias();

            Integer anoAnterior = anoInicioFerias - 1;

            Date dataJaneiro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + anoAnterior);
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + anoAnterior);

            int faltas = funcionarioHasRhTipoFaltaFacade.findFaltasNaoJustificadas(funcionario.getPkIdFuncionario(), dataJaneiro, dataDezembro).size();

            getFerias().setDiasDescontar(faltas);
            getFerias().setFkIdFuncionario(funcionario);
        }
        catch (ParseException ex)
        {
            Mensagem.erroMsg(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Integer anoInicioDeFerias ()
    {
        if(getFerias().getDataInicio() == null)
            this.ferias = getInstancia();
        
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(getFerias().getDataInicio()));
    }
    
    public Date getUltimaDataDoAnoActual ()
    {
        return MetodosGerais.getUltimaDataDoAnoActual();
    }
    
    public Date getUltimaDataDoProximoAno ()
    {
        return MetodosGerais.getUltimaDataDoProximoAno();
    }

    public String urlDaPaginaRedirecionar ()
    {
        return "/faces/rhVisao/rhFerias/feriasNovasRh.xhtml?faces-redirect=true";
    }

}
