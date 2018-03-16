/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean.avaliacaodedesempenho;

import entidade.RhAvaliacaoDeDesempenho;
import entidade.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao;
import entidade.RhClassificacaoDoCriterio;
import entidade.RhCriterioDeAvaliacao;
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
import javax.faces.context.FacesContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import managedBean.rhbean.validacao.RhAvaliacaoDeDesempenhoValidarBean;
import sessao.RhAvaliacaoDeDesempenhoFacade;
import sessao.RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade;
import sessao.RhClassificacaoDoCriterioFacade;
import sessao.RhCriterioDeAvaliacaoFacade;
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
public class RhAvaliacaoDeDesempenhoNovaBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhAvaliacaoDeDesempenhoFacade avaliacaoDeDesempenhoFacade;
    @EJB
    private RhCriterioDeAvaliacaoFacade criterioDeAvaliacaoFacade;
    @EJB
    private RhClassificacaoDoCriterioFacade classificacaoDoCriterioFacade;
    @EJB
    private RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade avaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade;
    @EJB
    private RhFuncionarioHasRhTipoFaltaFacade funcionarioHasRhTipoFaltaFacade;
    @EJB
    private RhFuncionarioFacade funcionarioFacade;

    /**
     * Entidades
     */
    private RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificacaoNoCriterio;
    private RhAvaliacaoDeDesempenho avaliacaoDeDesempenho;

    private RhFuncionario funcionarioPesquisa;

    private List<RhFuncionario> funcionariosPesquisados;

    /**
     * Creates a new instance of contratoBean
     */
    public RhAvaliacaoDeDesempenhoNovaBean ()
    {
    }

    public String limpar ()
    {
        funcionarioPesquisa = null;
        avaliacaoDeDesempenho = null;
        funcionariosPesquisados = null;
        prepararAvaliacaoDeDesempenho();
        return "avaliacaoDeDesempenhoHomeRh.xhtml?faces-redirect=true";
    }

    public static RhAvaliacaoDeDesempenhoNovaBean getInstanciaBean ()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (RhAvaliacaoDeDesempenhoNovaBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "rhAvaliacaoDeDesempenhoNovaBean");
    }

    public static RhAvaliacaoDeDesempenho getInstancia ()
    {
        RhAvaliacaoDeDesempenho avaliacao = new RhAvaliacaoDeDesempenho();

        avaliacao.setAno(MetodosGerais.getAnoActual());
        avaliacao.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return avaliacao;
    }

    public RhAvaliacaoDeDesempenho getAvaliacaoDeDesempenho ()
    {
        if (avaliacaoDeDesempenho == null)
        {
            avaliacaoDeDesempenho = getInstancia();
        }
        return avaliacaoDeDesempenho;
    }

    public RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao getClassificacaoNoCriterio ()
    {
        return classificacaoNoCriterio;
    }

    public void setClassificacaoNoCriterio (RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificacaoNoCriterio)
    {
        this.classificacaoNoCriterio = classificacaoNoCriterio;
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
            RhAvaliacaoDeDesempenhoValidarBean avaliacaoDeDesempenhoValidar = RhAvaliacaoDeDesempenhoValidarBean.getInstanciaBean();

            //Realiza as validações
            if (!avaliacaoDeDesempenhoValidar.validarNova(avaliacaoDeDesempenho))
            {
                return null;
            }

            userTransaction.begin();

            //Tirando da avaliação todos os critérios não avaliados
            for (int i = 0; i < avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList().size(); i++)
            {
                RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificNoCriterio;
                classificNoCriterio = avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList().get(i);

                if (classificNoCriterio.getDescricaoClassificacao() == null)
                {
                    avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList().remove(i);
                    i--;//Depois de remover continua na mesma posição
                }
            }

            avaliacaoDeDesempenhoFacade.create(avaliacaoDeDesempenho);
            for (RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificNoCriterio : avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList())
            {
                avaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoFacade.create(classificNoCriterio);
            }

            userTransaction.commit();

            Mensagem.sucessoMsg("Avaliação de desempenho gravada com sucesso!");
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

    public void prepararAvaliacaoDeDesempenho ()
    {
        avaliacaoDeDesempenho = getInstancia();

        List<RhCriterioDeAvaliacao> criteriosList = new ArrayList<>();
        //Carregando os critérios de avaliação e suas respectivas possíveis classificações
        for (RhCriterioDeAvaliacao criterio : criterioDeAvaliacaoFacade.findAll())
        {
            criterio.setRhClassificacaoDoCriterioList(classificacaoDoCriterioFacade.pesquisaPorCriterioDeAvaliacao(criterio.getPkIdCriterioDeAvaliacao()));
            criteriosList.add(criterio);
        }

        List<RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao> classificacoesNosCriteriosList = new ArrayList<>();
        //Para cada critério
        //Preparar uma classificação a ser dada para o critério
        //Dizer que esta classificação para este critério, pertence a determinada avaliação de desempenho
        for (RhCriterioDeAvaliacao criterio : criteriosList)
        {
            RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao classificacaoNoCriterio;
            classificacaoNoCriterio = new RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao();

            classificacaoNoCriterio.setFkIdCriterioAvaliacao(criterio);
            classificacaoNoCriterio.setFkIdAvaliacaoDesempenho(avaliacaoDeDesempenho);

            classificacoesNosCriteriosList.add(classificacaoNoCriterio);
        }

        avaliacaoDeDesempenho.setRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList(classificacoesNosCriteriosList);
    }

    public void actualizarClassificacaoNoCriterio ()
    {
        //Procura a descrição da classificação dada ao critério consoante o valor da mesma ou vice-versa
        for (RhClassificacaoDoCriterio classificacao : classificacaoNoCriterio.getFkIdCriterioAvaliacao().getRhClassificacaoDoCriterioList())
        {
            //Se encontrou consoante o valor, altera a descrição
            if (classificacao.getClassificacao() == classificacaoNoCriterio.getClassificacao())
            {
                classificacaoNoCriterio.setDescricaoClassificacao(classificacao.getDescricao());
                break;
            }
            //Se encontrou consoante a descrição, altera o valor
            else if (classificacao.getDescricao().equals(classificacaoNoCriterio.getDescricaoClassificacao()))
            {
                classificacaoNoCriterio.setClassificacao(classificacao.getClassificacao());
                break;

            }
        }

        classificacaoNoCriterio = null;
        actualizarClassificacaoGeral();
    }

    public void limparClassificacaoNoCriterio ()
    {
        classificacaoNoCriterio.setDescricaoClassificacao(null);
        classificacaoNoCriterio.setClassificacao(0);

        classificacaoNoCriterio = null;
        actualizarClassificacaoGeral();
    }

    public void actualizarClassificacaoGeral ()
    {
        double somaDasPontuacoes = 0;
        double numeroDeCriteriosAvaliados = 0;

        for (RhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacao avdHasCla : avaliacaoDeDesempenho.getRhAvaliacaoDeDesempenhoHasRhCriterioDeAvaliacaoList())
        {
            if (avdHasCla.getDescricaoClassificacao() != null)
            {
                somaDasPontuacoes += avdHasCla.getClassificacao();
                numeroDeCriteriosAvaliados++;
            }
        }

        getAvaliacaoDeDesempenho().setDescricaoDaClassificacao(null);

        if (numeroDeCriteriosAvaliados != 0)
        {
            String vString = "" + (somaDasPontuacoes / numeroDeCriteriosAvaliados);

            String valor2CasasDecimais = "";

            for (int i = 0; i <= vString.indexOf(".") + 2; i++)
            {
                if (i < vString.length())
                {
                    valor2CasasDecimais += vString.charAt(i);
                }
                else
                {
                    valor2CasasDecimais += "0";
                }
            }

            double valor = Double.parseDouble(valor2CasasDecimais);

            getAvaliacaoDeDesempenho().setClassificacao(valor);

            if (valor < 7.5)
            {
                getAvaliacaoDeDesempenho().setDescricaoDaClassificacao(Defs.RH_MAU);
            }
            else if (valor < 12.5)
            {
                getAvaliacaoDeDesempenho().setDescricaoDaClassificacao(Defs.RH_REGULAR);
            }
            else if (valor < 15.5)
            {
                getAvaliacaoDeDesempenho().setDescricaoDaClassificacao(Defs.RH_BOM);
            }
            else if (valor <= 17)
            {
                getAvaliacaoDeDesempenho().setDescricaoDaClassificacao(Defs.RH_MUITO_BOM);
            }
        }
    }

    public String corDaClassificacaoGeral ()
    {
        Double valor = getAvaliacaoDeDesempenho().getClassificacao();

        if (valor < 7.5)
        {
            return "red";
        }
        else if (valor < 12.5)
        {
            return "orange";
        }
        else if (valor < 15.5)
        {
            return "blue";
        }
        else if (valor <= 17)
        {
            return "green";
        }
        else
        {
            return null;
        }
    }

    public void pesquisarFuncionarios ()
    {
        List<RhFuncionario> funcList = funcionarioFacade.findFuncionario(funcionarioPesquisa);

        funcionariosPesquisados = new ArrayList<>();

        for (RhFuncionario rhFunc : funcList)
        {
            if (!avaliacaoDeDesempenhoFacade.temAvaliacao(rhFunc.getPkIdFuncionario(), avaliacaoDeDesempenho.getAno()))
            {
                funcionariosPesquisados.add(rhFunc);
            }
        }

        if (funcionariosPesquisados.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registro encontrado para esta pesquisa");
        }
    }

    public void selecionarFuncionario (RhFuncionario funcionario)
    {
        getAvaliacaoDeDesempenho().setFkIdFuncionario(funcionario);
        actualizarFaltasDoFuncionarioNoAnoSelecionado();
    }

    public void actualizarFaltasDoFuncionarioNoAnoSelecionado ()
    {
        try
        {

            Date dataJaneiro = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-" + getAvaliacaoDeDesempenho().getAno());
            Date dataDezembro = new SimpleDateFormat("dd-MM-yyyy").parse("31-12-" + getAvaliacaoDeDesempenho().getAno());

            Integer idFunc = getAvaliacaoDeDesempenho().getFkIdFuncionario().getPkIdFuncionario();

            if (idFunc != null)
            {
                getAvaliacaoDeDesempenho().getFkIdFuncionario().setRhFuncionarioHasRhTipoFaltaList(funcionarioHasRhTipoFaltaFacade.findFaltas(idFunc, dataJaneiro, dataDezembro));
            }
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
    }

    public String urlDaPaginaRedirecionarComNovaAvaliacao (RhFuncionario func)
    {
        RhAvaliacaoDeDesempenhoHomeBean.getInstanciaBean().selecionarNovaAvaliacao();

        getAvaliacaoDeDesempenho().setFkIdFuncionario(func);

        return "/faces/rhVisao/rhAssiduidade/avaliacaoDeDesempenhoHomeRh.xhtml?faces-redirect=true";
    }

    public String urlDaPaginaRedirecionar ()
    {
        RhAvaliacaoDeDesempenhoHomeBean.getInstanciaBean().selecionarNovaAvaliacao();
        return "/faces/rhVisao/rhAssiduidade/avaliacaoDeDesempenhoHomeRh.xhtml?faces-redirect=true";
    }
}
