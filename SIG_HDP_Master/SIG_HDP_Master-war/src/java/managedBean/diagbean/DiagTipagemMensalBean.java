/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.diagbean;

import entidade.DiagReagente;
import entidade.DiagTipagemMensal;
import entidade.DiagTipagemMensalHasReagente;
import entidade.DiagTipoReagente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sessao.DiagReagenteFacade;
import sessao.DiagTipagemMensalFacade;
import sessao.DiagTipagemMensalHasReagenteFacade;
import sessao.DiagTipoReagenteFacade;
import util.Mensagem;

/**
 *
 * @author mauro
 */
@ManagedBean
@SessionScoped
public class DiagTipagemMensalBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DiagTipagemMensalFacade diagTipagemMensalFacade;
    @EJB
    private DiagTipagemMensalHasReagenteFacade diagTipagemMensalHasReagenteFacade;
    @EJB
    private DiagTipoReagenteFacade diagTipoReagenteFacade;
    @EJB
    private DiagReagenteFacade diagReagenteFacade;

    private DiagTipagemMensal diagTipagemMensal, diagTipagemMensalPesquisa, diagTipagemMensalVisualizar;
    private DiagReagente diagReagente;
    private DiagTipoReagente diagTipoReagente;

    private List<DiagReagente> listaReagentes;
    private List<DiagTipagemMensalHasReagente> listaReagentesSelecionados;
    private int quatidade;

    private String mes = "", mesPesquisa;
    private int ano = 0, anoPesquisa;

    private ArrayList<String> arrayMeses;

    private boolean pesquisar;
    
    /**
     * Creates a new instance of TipagemMensalBean
     */
    public DiagTipagemMensalBean()
    {
        diagTipagemMensal = new DiagTipagemMensal();
        diagTipagemMensalPesquisa = new DiagTipagemMensal();
        diagTipagemMensalVisualizar = new DiagTipagemMensal();

        diagReagente = new DiagReagente();
        diagTipoReagente = new DiagTipoReagente();

        listaReagentes = new ArrayList<>();

        inicializarListaReagentesSelecionados();

        inicializarArrayMeses();
        inicializarMes();

        ano = 1900 + new Date().getYear();
    }

    public static DiagTipagemMensalBean getInstanciaBean()
    {
        FacesContext c = FacesContext.getCurrentInstance();
        return (DiagTipagemMensalBean) c.getELContext().getELResolver()
                .getValue(c.getELContext(), null, "diagTipagemMensalBean");
    }

    public static DiagTipagemMensal getInstancia()
    {
        DiagTipagemMensal diagTipagemMensal = new DiagTipagemMensal();

        return diagTipagemMensal;
    }
    
    public final void inicializarListaReagentesSelecionados()
    {
        DiagTipagemMensalHasReagente tipagemMensalHasReagente;

        listaReagentesSelecionados = new ArrayList<>();

        for (DiagReagente reagente : diagReagenteFacade.findAll())
        {
            tipagemMensalHasReagente = new DiagTipagemMensalHasReagente();

            tipagemMensalHasReagente.setFkIdReagente(reagente);

            listaReagentesSelecionados.add(tipagemMensalHasReagente);
        }
    }

    public final void inicializarMes()
    {
        int mesAux = new Date().getMonth();

        switch (mesAux)
        {
            case 0:
                mes += "Janeiro";
                break;
            case 1:
                mes += "Fevereiro";
                break;
            case 2:
                mes += "Março";
                break;
            case 3:
                mes += "Abril";
                break;
            case 4:
                mes += "Maio";
                break;
            case 5:
                mes += "Junho";
                break;
            case 6:
                mes += "Julho";
                break;
            case 7:
                mes += "Agosto";
                break;
            case 8:
                mes += "Setembro";
                break;
            case 9:
                mes += "Outubro";
                break;
            case 10:
                mes += "Novembro";
                break;
            case 11:
                mes += "Dezembro";
                break;
        }
    }

    public final void inicializarArrayMeses()
    {
        arrayMeses = new ArrayList<>();
        arrayMeses.add("Janeiro");
        arrayMeses.add("Fevereiro");
        arrayMeses.add("Março");
        arrayMeses.add("Abril");
        arrayMeses.add("Maio");
        arrayMeses.add("Junho");
        arrayMeses.add("Julho");
        arrayMeses.add("Agosto");
        arrayMeses.add("Setembro");
        arrayMeses.add("Outubro");
        arrayMeses.add("Novembro");
        arrayMeses.add("Dezembro");
    }

    public ArrayList<String> getArrayMeses()
    {
        return arrayMeses;
    }

    public void setArrayMeses(ArrayList<String> arrayMeses)
    {
        this.arrayMeses = arrayMeses;
    }

    public boolean getPesquisar()
    {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar)
    {
        this.pesquisar = pesquisar;
    }

    public String getMes()
    {
        return mes;
    }

    public int getAno()
    {
        return ano;
    }

    public DiagReagente getDiagReagente()
    {
        if (diagReagente == null)
        {
            diagReagente = DiagReagenteBean.getInstancia();
        }
        return diagReagente;
    }

    public void setDiagReagente(DiagReagente diagReagente)
    {
        this.diagReagente = diagReagente;
    }

    public DiagTipagemMensal getDiagTipagemMensal()
    {
        return diagTipagemMensal;
    }

    public DiagTipoReagente getDiagTipoReagente()
    {
        return diagTipoReagente;
    }

    public void setDiagTipoReagente(DiagTipoReagente diagTipoReagente)
    {
        this.diagTipoReagente = diagTipoReagente;
    }

    public void setDiagTipagemMensal(DiagTipagemMensal diagTipagemMensal)
    {
        this.diagTipagemMensal = diagTipagemMensal;
    }

    public DiagTipagemMensal getDiagTipagemMensalPesquisa()
    {
        return diagTipagemMensalPesquisa;
    }

    public void setDiagTipagemMensalPesquisa(DiagTipagemMensal diagTipagemMensalPesquisa)
    {
        this.diagTipagemMensalPesquisa = diagTipagemMensalPesquisa;
    }

    public DiagTipagemMensal getDiagTipagemMensalVisualizar()
    {
        return diagTipagemMensalVisualizar;
    }

    public void setDiagTipagemMensalVisualizar(DiagTipagemMensal diagTipagemMensalVisualizar)
    {
        this.diagTipagemMensalVisualizar = diagTipagemMensalVisualizar;
    }

    public List<DiagReagente> getListaReagentes()
    {
        return listaReagentes;
    }

    public void setListaReagentes(List<DiagReagente> listaReagentes)
    {
        this.listaReagentes = listaReagentes;
    }

    public List<DiagTipagemMensalHasReagente> getListaReagentesSelecionados()
    {
        return listaReagentesSelecionados;
    }

    public void setListaReagentesSelecionados(List<DiagTipagemMensalHasReagente> listaReagentes)
    {
        this.listaReagentesSelecionados = listaReagentes;
    }

    public int getQuatidade()
    {
        return quatidade;
    }

    public void setQuatidade(int quatidade)
    {
        this.quatidade = quatidade;
    }

    public List<String> findAnosComTipagem()
    {
        return diagTipagemMensalFacade.findAnosComTipagem();
    }

    public List<DiagTipoReagente> findAllTiposReagentes()
    {
        return diagTipoReagenteFacade.findAll();
    }

    public void carregarListaReagentesPorTipo()
    {
        listaReagentes = diagReagenteFacade.findReagenteByTipo(diagTipoReagente);
    }

    private boolean quantidadeAdicionarEValida(DiagReagente reagenteAux, int quantidadeAdicionar)
    {
        System.out.println("quantidade reagente: " + reagenteAux.getQuantidade());
        System.out.println("quantidade adicionar: " + quantidadeAdicionar);

        return reagenteAux.getQuantidade() >= quantidadeAdicionar;
    }

//    public String getMensagemDialogAdicionar()
//    {
//        return mensagemDialogAdicionar;
//    }

//    public void adicionarReagenteALista()
//    {
//
//        System.out.println("" + diagReagente.getPkIdReagente());
//
//        if (diagReagente.getPkIdReagente() != null)
//        {
//            diagReagente = diagReagenteFacade.findReagenteById(diagReagente.getPkIdReagente());
//            diagReagente.setFkIdTipoReagente(diagTipoReagenteFacade.findTipoReagenteById(diagTipoReagente.getPkIdTipoReagente()));
//        }
//
////        if (quatidade == 0)
////        {
////            mensagemDialogAdicionar = "Erro: A quantidade a adicionar não pode ser nula!";
////        }
////        else if (!quantidadeAdicionarEValida(diagReagente, quatidade))
////        {
////            mensagemDialogAdicionar = "Erro: A quantidade a adicionar (" + quatidade + ") é superior a quantidade em stock (" + diagReagente.getQuantidade() + ")!";
////        }
////        else
////        {
//            DiagTipagemMensalHasReagente tipagemMensalHasReagente = new DiagTipagemMensalHasReagente();
//            tipagemMensalHasReagente.setFkIdReagente(diagReagente);
//            tipagemMensalHasReagente.setQuantidade(quatidade);
//
//            listaReagentesSelecionados.add(tipagemMensalHasReagente);
//
//            diagTipoReagente = new DiagTipoReagente();
//            diagReagente = new DiagReagente();
//
////            mensagemDialogAdicionar = "Reagente adicionado com sucesso!";
////        }
//    }

    public void removerReagenteDaLista(DiagTipagemMensalHasReagente tipagemMensalHasReagenteAux)
    {
        listaReagentesSelecionados.remove(tipagemMensalHasReagenteAux);
    }

    public void inserirReagentesTipagem()
    {
        for (DiagTipagemMensalHasReagente reagentesUtilizados : listaReagentesSelecionados)
        {
            reagentesUtilizados.setFkIdTipagemMensal(diagTipagemMensal);

            diagTipagemMensalHasReagenteFacade.create(reagentesUtilizados);
        }
    }

    public String create()
    {
        GregorianCalendar data = new GregorianCalendar();
        try
        {
            userTransaction.begin();

            diagTipagemMensal.setDataRealizacao(new Date(data.getTimeInMillis()));
            diagTipagemMensal.setMes(mes);
            diagTipagemMensal.setAno(ano);
            diagTipagemMensalFacade.create(diagTipagemMensal);

            inserirReagentesTipagem();

            userTransaction.commit();
            Mensagem.sucessoMsg("Registo mensal salvo com sucesso!");
        }
        catch (IllegalStateException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e)
        {
            try
            {
                userTransaction.rollback();
                Mensagem.erroMsg(e.toString());
            }
            catch (IllegalStateException | SecurityException | SystemException ex)
            {
                Mensagem.erroMsg("Rollback: " + ex.toString());
            }
        }

        return "tipagemMensal.xhtml?faces-redirect=true";
    }

    public boolean tipagemMensalJaCadatrada()
    {
        DiagTipagemMensal tipagemMensalAux = new DiagTipagemMensal();
        tipagemMensalAux.setMes(mes);
        tipagemMensalAux.setAno(ano);

        return !diagTipagemMensalFacade.findTipagem(tipagemMensalAux).isEmpty();
    }

    public String redirect()
    {
        if (tipagemMensalJaCadatrada())
        {
            Mensagem.erroMsg("Erro: O registo dos reagentes para o mês actual já foi efectuado! Por favor tente no próximo mês.");

            return null;
        }
        else
        {
            pesquisar = false;

            return "registarTipagemMensal.xhtml?faces-redirect=true";
        }

    }

    public String limpar()
    {
        diagTipagemMensal = getInstancia();
        listaReagentesSelecionados = new ArrayList<>();

        return "tipagemMensal.xhtml?faces-redirect=true";
    }

    public List<DiagTipagemMensal> pesquisarTipagemMensal()
    {
        if (pesquisar)
        {
            List<DiagTipagemMensal> tipagem;
            tipagem = diagTipagemMensalFacade.findTipagem(diagTipagemMensalPesquisa);
            if (tipagem.isEmpty() || tipagem == null)
            {
                Mensagem.erroMsg("Nenhum registo encontrado para esta pesquisa");
            }

            return tipagem;
        }

        return null;
    }

}
