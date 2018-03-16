/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;

import entidade.GrlEspecialidade;
import entidade.GrlFicheiroAnexado;
import entidade.GrlPessoa;
import entidade.RhEscolaUniversidade;
import entidade.RhEspecialidadeCurso;
import entidade.RhEstadoEstagiario;
import entidade.RhEstadoFuncionario;
import entidade.RhEstagiario;
import entidade.RhFuncao;
import entidade.RhFuncionario;
import entidade.RhNivelAcademico;
import entidade.RhPeriodoAulas;
import entidade.RhProfissao;
import entidade.RhSeccaoTrabalho;
import entidade.RhTipoEstagio;
import entidade.SupiAreaAvaliacao;
import entidade.SupiAvaliaCriterios;
import entidade.SupiAvaliacaoDesempenho;
import entidade.SupiCriterioAvaliacao;
import entidade.SupiItensAvaliacao;
import entidade.SupiPontuacao;
import java.io.Serializable;
//import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import managedBean.grlbean.GrlPessoaBean;
import managedBean.rhbean.estagiario.RhEstagiarioNovoBean;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.RhEscolaUniversidadeFacade;
import sessao.RhEstagiarioFacade;
import sessao.SupiAreaAvaliacaoFacade;
import sessao.SupiAvaliaCriteriosFacade;
import sessao.SupiAvaliacaoDesempenhoFacade;
import sessao.SupiCriterioAvaliacaoFacade;
import sessao.SupiItensAvaliacaoFacade;
import sessao.SupiPontuacaoFacade;
import util.Constantes;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@ViewScoped
public class SupiAvaliacaoNovaBean implements Serializable
{

    @EJB
    private SupiPontuacaoFacade supiPontuacaoFacade;

    @EJB
    private SupiAreaAvaliacaoFacade supiAreaAvaliacaoFacade;

    @EJB
    private RhEscolaUniversidadeFacade rhEscolaUniversidadeFacade;
    @EJB
    private SupiItensAvaliacaoFacade supiItensAvaliacaoFacade;
    @EJB
    private RhEstagiarioFacade rhEstagiarioFacade;
    @EJB
    private SupiCriterioAvaliacaoFacade supiCriterioAvaliacaoFacade;
    @EJB
    private SupiAvaliacaoDesempenhoFacade supiAvaliacaoDesempenhoFacade;
    @EJB
    private SupiAvaliaCriteriosFacade supiAvaliaCriteriosFacade;

    private RhEstagiario estagiario, pesquisaEstagiario, visualizarEstagiario;
    private SupiAvaliacaoDesempenho avaliacaoDesempenho, pesquisaAvaliacaoDesempenho, visualizarDesempenho;
    private List<SupiAvaliacaoDesempenho> listAvaliacaoDesempenho;
    private List<SupiCriterioAvaliacao> listaCriterios;
    private List<SupiAvaliaCriterios> listaCriteriosAv;
    private List<SupiAreaAvaliacao> listaSupiAreaAvaliacao;
    private SupiAvaliaCriterios CriteriosAv;
    private SupiAreaAvaliacao areaAvaliacao;
    private String[] numeros;
    private String observacao;
    private String observacaoInstituicao;
    private int idPontuacao;
    private double total;
    private double presenca;
    private double relatorio;

    Mensagem mensagem;

    public SupiAvaliacaoNovaBean()
    {
        listaCriteriosAv = new ArrayList<>();
    }

//    public SupiAvaliacaoNovaBean()
//    {
//        avaliacaoDesempenho = new SupiAvaliacaoDesempenho();
//        estagiario = new RhEstagiario();
//        listaCriterios = new ArrayList<>();
//        mensagem = new Mensagem();
//    }
    public int getIdPontuacao()
    {
        return idPontuacao;
    }

    public double getPresenca()
    {
        return presenca;
    }

    public void setPresenca(double presenca)
    {
        this.presenca = presenca;
    }

    public double getRelatorio()
    {
        return relatorio;
    }

    public void setRelatorio(double relatorio)
    {
        this.relatorio = relatorio;
    }
    
    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public SupiAvaliaCriterios getCriteriosAv()
    {
        return CriteriosAv;
    }

    public void setCriteriosAv(SupiAvaliaCriterios CriteriosAv)
    {
        this.CriteriosAv = CriteriosAv;
    }

    
    
    public void setIdPontuacao(int idPontuacao)
    {
        this.idPontuacao = idPontuacao;
    }

    public List<SupiAvaliaCriterios> getListaCriteriosAv()
    {
        return listaCriteriosAv;
    }

    public void setListaCriteriosAv(List<SupiAvaliaCriterios> listaCriteriosAv)
    {
        this.listaCriteriosAv = listaCriteriosAv;
    }

    public static SupiAvaliacaoNovaBean getInstanciaBean()
    {
        return (SupiAvaliacaoNovaBean) GeradorCodigo.getInstanciaBean("supiAvaliacaoNovaBean");
    }

    public static SupiAvaliacaoDesempenho getInstancia()
    {
        SupiAvaliacaoDesempenho areaDeformacao = new SupiAvaliacaoDesempenho();
        areaDeformacao.setFkIdEstagiario(RhEstagiarioNovoBean.getInstancia());
        areaDeformacao.setFkIdFuncionario(RhFuncionarioNovoBean.getInstancia());

        return areaDeformacao;
    }

    @PostConstruct
    public void initList()
    {
        numeros = new String[getListaCriterios().size()];
        for (int i = 0; i < numeros.length; i++)
        {
            numeros[i] = "0";
        }
    }

    public String[] getNumeros()
    {
        return numeros;
    }

    public void setNumeros(String[] numeros)
    {
        this.numeros = numeros;
    }

    public RhEstagiario getEstagiario()
    {
        if (estagiario == null)
        {
            estagiario = RhEstagiarioNovoBean.getInstancia();
        }
        return estagiario;
    }

    public void setEstagiario(RhEstagiario estagiario)
    {
        this.estagiario = estagiario;
    }

    public SupiAreaAvaliacao getAreaAvaliacao()
    {
        if (areaAvaliacao == null)
        {
            areaAvaliacao = new SupiAreaAvaliacao();
        }
        return areaAvaliacao;
    }

    public void setAreaAvaliacao(SupiAreaAvaliacao areaAvaliacao)
    {
        this.areaAvaliacao = areaAvaliacao;
    }

    public SupiAvaliacaoDesempenho getAvaliacaoDesempenho()
    {
        if (avaliacaoDesempenho == null)
        {
            avaliacaoDesempenho = SupiAvaliacaoNovaBean.getInstancia();
        }
        return avaliacaoDesempenho;
    }

    public void setAvaliacaoDesempenho(SupiAvaliacaoDesempenho avaliacaoDesempenho)
    {
        this.avaliacaoDesempenho = avaliacaoDesempenho;
    }

    public String getObservacao()
    {
        return observacao;
    }

    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }

    public String getObservacaoInstituicao()
    {
        return observacaoInstituicao;
    }

    public void setObservacaoInstituicao(String observacaoInstituicao)
    {
        this.observacaoInstituicao = observacaoInstituicao;
    }

    public List<SupiAvaliacaoDesempenho> getListAvaliacaoDesempenho()
    {
        return listAvaliacaoDesempenho;
    }

    public SupiAvaliacaoDesempenho getInstanciaAvaliacaoDesempenho()
    {
        SupiAvaliacaoDesempenho avDesempenho = new SupiAvaliacaoDesempenho();

        avDesempenho.setFkIdEstagiario(getInstanciaEstagiario());
        return avDesempenho;
    }

    public RhEstagiario getInstanciaEstagiario()
    {
        RhEstagiario estag = new RhEstagiario();
        estag.setFkIdPessoa(new GrlPessoaBean().getInstanciaPessoa());
        estag.setFkIdTipoEstagio(new RhTipoEstagio());
        estag.setFkIdEstadoEstagiario(new RhEstadoEstagiario());
        estag.setFkIdNivelAcademico(new RhNivelAcademico());
        estag.setFkIdEspecialidadeCurso(new RhEspecialidadeCurso());
        estag.setFkIdProfissao(new RhProfissao());
        estag.setFkIdEscolaUniversidade(new RhEscolaUniversidade());
        estag.setFkIdSeccaoTrabalho(new RhSeccaoTrabalho());
        estag.setFkIdPeriodoAulas(new RhPeriodoAulas());

        estag.setFkIdBi(new GrlFicheiroAnexado());
        estag.setFkIdCurriculum(new GrlFicheiroAnexado());
        estag.setFkIdDocumentoEscolar(new GrlFicheiroAnexado());

        return estag;
    }

    public SupiAvaliacaoDesempenho getPesquisaAvaliacaoDesempenho()
    {
        if (pesquisaAvaliacaoDesempenho == null)
        {
            pesquisaAvaliacaoDesempenho = getInstanciaAvaliacaoDesempenho();
        }
        return pesquisaAvaliacaoDesempenho;
    }

    public void setPesquisaAvaliacaoDesempenho(SupiAvaliacaoDesempenho pesquisaAvaliacaoDesempenho)
    {
        this.pesquisaAvaliacaoDesempenho = pesquisaAvaliacaoDesempenho;
    }

    public void setListAvaliacaoDesempenho(List<SupiAvaliacaoDesempenho> listAvaliacaoDesempenho)
    {
        this.listAvaliacaoDesempenho = listAvaliacaoDesempenho;
    }

    public String salvar()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (listaCriterios != null && listaCriterios.size() > -1)
        {
            avaliacaoDesempenho.setFkIdEstagiario(estagiario);
            avaliacaoDesempenho.setFkIdFuncionario(null);
            avaliacaoDesempenho.setDataAvaliacao(new Date());
            avaliacaoDesempenho.setTotalMensal(getPontuacaoTotal());
            avaliacaoDesempenho.setFkIdAreaAvaliacao(areaAvaliacao);
            /*if (verificarEstagiario(estagiario.getPkIdEstagiario(), avaliacaoDesempenho.getDataAvaliacao()))
             {
             mensagem.addMessage("Estagiário já avaliado nesta Data!", null);
             //facesContext.addMessage(null, new FacesMessage("Estagiário já avaliado nesta Data!"));
             limpar();
             return "avaliacao";
             } else
             {
             */
            supiAvaliacaoDesempenhoFacade.create(avaliacaoDesempenho);
            //itens de avalicao criterios definidos
            for (int i = 0; i < numeros.length; i++)
            {
                SupiItensAvaliacao supiItensAvaliacao = new SupiItensAvaliacao();
//                supiItensAvaliacao.setFkIdAvaliacao(avaliacaoDesempenho);
                supiItensAvaliacao.setFkIdCriterioAvaliacao(getListaCriterios().get(i));
                supiItensAvaliacao.setPontuacao(Double.parseDouble(numeros[i]));
                supiItensAvaliacaoFacade.create(supiItensAvaliacao);
            }

            /*for (SupiAvaliaCriterios lista : listaAvaliaCriterios)
             {
             lista.setFkIdAvaliacaoDesempenho(avaliacaoDesempenho);
             supiAvaliaCriteriosFacade.create(lista);
             }
             */
            limpar();
            //mensagem.addMessage("Dados Registados com sucesso", null);
            Mensagem.sucessoMsg("Dados Registados com sucesso!!!");
            System.out.println("Avaliacao:  Avaliacao  Registada Com Sucesso !! ");

            //}
        }
        return "avaliacaoSupi.xhtml?faces-redirect=true";
    }

    public double getPontuacaoTotal()
    {
        double totalParcial = 0;
        for (int i = 0; i < numeros.length; i++)
        {
            System.out.println("numeros:" + numeros[i]);
            totalParcial = totalParcial + (Double.parseDouble(numeros[i]) * 0.2); // o 0.2 correponde a 20/100

        }
        return totalParcial / getListaCriterios().size();
    }

    public void editar()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        /* if (listaAvaliaCriterios != null && listaAvaliaCriterios.size() > -1)
         {
         RhEstagiario est = rhEstagiarioFacade.find(estagiario.getPkIdEstagiario());
         avaliacaoDesempenho.setFkIdEstagiario(est);
         avaliacaoDesempenho.setDataAvaliacao(new Date());
         avaliacaoDesempenho.setTotalMensal(pontuacaoTotal());
         supiAvaliacaoDesempenhoFacade.edit(avaliacaoDesempenho);
         for (SupiAvaliaCriterios lista : listaAvaliaCriterios)
         {
         lista.setFkIdAvaliacaoDesempenho(avaliacaoDesempenho);
         supiAvaliaCriteriosFacade.edit(lista);
         }
         limpar();
         //facesContext.addMessage(null, new FacesMessage("Dados Actualizados com sucesso"));
         mensagem.addMessage("Dados Actualizados com sucesso", null);
         System.out.println("Avaliacao:  Avaliacao  Registada Com Sucesso !! ");

         }
         */
    }

    public void pesquisarDesempenho()
    {
        setListAvaliacaoDesempenho(supiAvaliacaoDesempenhoFacade.findDesempenho(pesquisaAvaliacaoDesempenho));
        if (getListAvaliacaoDesempenho().isEmpty())
        {
            Mensagem.warnMsg("Nenhum estagiario avaliado encontrado para esta pesquisa");
        }
    }

    public void buscaEstagiario(int idEstagiario)
    {
        List<RhEstagiario> lista = rhEstagiarioFacade.findAll();
        for (RhEstagiario resultado : lista)
        {
            if (resultado.getPkIdEstagiario() == idEstagiario)
            {
                estagiario = rhEstagiarioFacade.find(resultado.getPkIdEstagiario());
            }
        }

    }

    public void limpar()
    {
        //pontuacao = new SupiPontuacao();
        //criterioAvaliacao = new SupiCriterioAvaliacao();
        //avaliaCriterios = new SupiAvaliaCriterios();
        avaliacaoDesempenho = new SupiAvaliacaoDesempenho();
        estagiario = new RhEstagiario();
        //listaAvaliaCriterios = new ArrayList<>();
        listaCriterios = new ArrayList<>();
        //pontos = 0.0;
    }

    public String limparPesquisa()
    {
        return "listarAvaliacaoSupi.xhtml?faces-redirect=true";
    }

    public void limparPesquisas()
    {
        setPesquisaAvaliacaoDesempenho(getInstanciaAvaliacaoDesempenho());
        listAvaliacaoDesempenho = new ArrayList<>();
    }

    public void eliminar()
    {
        try
        {
            supiAvaliacaoDesempenhoFacade.remove(pesquisaAvaliacaoDesempenho);
            pesquisaAvaliacaoDesempenho = getInstancia();
            pesquisarDesempenho();
            Mensagem.sucessoMsg("A avaliação foi eliminada com sucesso!");
        } catch (Exception ex)
        {
            Mensagem.warnMsg("A avaliação não pode ser eliminada, porque está a ser utilizada.");
        }

    }

    public ArrayList<SelectItem> getTodasAreasUniversidades()
    {
        ArrayList<SelectItem> itens = new ArrayList<>();

        for (RhEscolaUniversidade e : rhEscolaUniversidadeFacade.findAll())
        {
            itens.add(new SelectItem(e.getPkIdEscolaUniversidade(), e.getDescricao()));
        }
        return itens;
    }

    public List<SupiItensAvaliacao> getListaItensCriterio()
    {
        return supiItensAvaliacaoFacade.findListaItensCriterio(pesquisaAvaliacaoDesempenho.getPkIdAvaliacaoDesempenho());
    }

    public List<RhEstagiario> listarEstagiarios()
    {

        List<RhEstagiario> lista = rhEstagiarioFacade.findAll();
        List<RhEstagiario> listaResultado = new ArrayList();

        for (RhEstagiario resultado : lista)
        {
            if (resultado.getFkIdSeccaoTrabalho() != null)
            {
                listaResultado.add(resultado);
            }
        }

        return listaResultado;
    }

    public List<SupiCriterioAvaliacao> getListaCriterios()
    {

        listaCriterios = supiCriterioAvaliacaoFacade.findAll();
        return listaCriterios;
    }

    public List<SupiAvaliaCriterios> listaCriteriosAva()
    {

        List<SupiAvaliaCriterios> listaX = new ArrayList<>();
        List<SupiCriterioAvaliacao> lista = getListaCriterios();
        for (SupiCriterioAvaliacao CA : lista)
        {
            SupiAvaliaCriterios modelo = new SupiAvaliaCriterios();
            modelo.setFkIdCriterioAvaliacao(CA);
            listaX.add(modelo);
        }
        this.listaCriteriosAv = listaX;
        System.out.println("Total Lista: " + listaCriteriosAv.size());
        return listaX;
    }

    public double totalAvaliacao()
    {
        if (listaCriteriosAv != null)
        {
            System.out.println("Total Lista 2: " + listaCriteriosAv.size());
            for (SupiAvaliaCriterios sca : listaCriteriosAv)
            {
                System.out.println("Pontuação: "+ sca.getPontuacao());
                SupiPontuacao pontos = new SupiPontuacao();
                pontos = supiPontuacaoFacade.find(sca.getPontuacao());
                if (pontos != null)
                {
                    this.total += pontos.getValor();
                }
            }
        }
        return this.total;
    }

    public List<SupiAreaAvaliacao> getListaAreaAvaliacao()
    {
        listaSupiAreaAvaliacao = supiAreaAvaliacaoFacade.findAll();
        return listaSupiAreaAvaliacao;
    }

    public List<SupiAvaliacaoDesempenho> getListaAvaliacaoDesempenho()
    {
        listAvaliacaoDesempenho = supiAvaliacaoDesempenhoFacade.findAll();
        return listAvaliacaoDesempenho;
    }

    public void buscaCNT(int idEstagiario)
    {
        List<SupiAvaliacaoDesempenho> lista = supiAvaliacaoDesempenhoFacade.findAll();
        for (SupiAvaliacaoDesempenho resultado : lista)
        {
            if (resultado.getFkIdEstagiario().getPkIdEstagiario() == idEstagiario && resultado.getFkIdAreaAvaliacao().getPkIdAreaAvaliacao() == 1)
            {
                avaliacaoDesempenho = supiAvaliacaoDesempenhoFacade.find(resultado.getFkIdEstagiario().getPkIdEstagiario());
            }
        }

    }

    public void buscaMedicina(int idEstagiario)
    {
        List<SupiAvaliacaoDesempenho> lista = supiAvaliacaoDesempenhoFacade.findAll();
        for (SupiAvaliacaoDesempenho resultado : lista)
        {
            if (resultado.getFkIdEstagiario().getPkIdEstagiario() == idEstagiario && resultado.getFkIdAreaAvaliacao().getPkIdAreaAvaliacao() == 2)
            {
                avaliacaoDesempenho = supiAvaliacaoDesempenhoFacade.find(resultado.getFkIdEstagiario().getPkIdEstagiario());
            }
        }

    }

    public void buscaPediatria(int idEstagiario)
    {
        List<SupiAvaliacaoDesempenho> lista = supiAvaliacaoDesempenhoFacade.findAll();
        for (SupiAvaliacaoDesempenho resultado : lista)
        {
            if (resultado.getFkIdEstagiario().getPkIdEstagiario() == idEstagiario && resultado.getFkIdAreaAvaliacao().getPkIdAreaAvaliacao() == 3)
            {
                avaliacaoDesempenho = supiAvaliacaoDesempenhoFacade.find(resultado.getFkIdEstagiario().getPkIdEstagiario());
            }
        }

    }

    public String verificaCNT(int idEstagiario)
    {

        List<SupiAvaliacaoDesempenho> lista = supiAvaliacaoDesempenhoFacade.findAll();

        for (SupiAvaliacaoDesempenho resultado : lista)
        {

            if (resultado.getFkIdEstagiario().getPkIdEstagiario() == idEstagiario && resultado.getFkIdAreaAvaliacao().getPkIdAreaAvaliacao() == 1)

            {
                return resultado.getFkIdAreaAvaliacao().getDescricao();
            }

        }
        return null;

    }

    public void chamar()
    {

        System.err.println("OIAntes");
        System.out.println(verificaCNT(1));
        System.err.println("OI");
    }

}
