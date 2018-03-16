/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.supibean;
import entidade.GrlEndereco;
import entidade.GrlMunicipio;
import entidade.GrlPais;
import entidade.GrlProvincia;
import entidade.RhFuncionario;
import entidade.RhSeccaoTrabalho;
import entidade.SupiEstado;
import entidade.SupiFormacao;
import entidade.SupiFormacaoFuncionario;
import entidade.SupiFormacaoFuncionarioPk;
import entidade.SupiFormadorAux;
import entidade.SupiOpcao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.rhbean.funcionario.RhFuncionarioNovoBean;
import sessao.GrlEnderecoFacade;
import sessao.GrlMunicipioFacade;
import sessao.GrlProvinciaFacade;
import sessao.RhFuncionarioFacade;
import sessao.RhSeccaoTrabalhoFacade;
import sessao.SupiEstadoFacade;
import sessao.SupiFormacaoFacade;
import sessao.SupiFormacaoFuncionarioFacade;
import sessao.SupiFormacaoFuncionarioPkFacade;
import sessao.SupiOpcaoFacade;
import sessao.SupiSeccaoFacade;
import sessao.SupiFormadorAuxFacade;
import util.Constantes;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author helga
 */
@ManagedBean
@SessionScoped
public class SupiFormacaoBean implements Serializable
{

    @EJB
    private GrlProvinciaFacade grlProvinciaFacade;
    @EJB
    private GrlEnderecoFacade grlEnderecoFacade;
    @EJB
    private SupiFormacaoFuncionarioPkFacade supiFormacaoFuncionarioPkFacade;
    @EJB
    private SupiOpcaoFacade supiOpcaoFacade;
    @EJB
    private RhSeccaoTrabalhoFacade rhSeccaoTrabalhoFacade;
    @EJB
    private RhFuncionarioFacade rhFuncionarioFacade;
    @EJB
    private GrlMunicipioFacade grlMunicipioFacade;
    @EJB
    private SupiFormadorAuxFacade supiFormadorFacade;
    @EJB
    private SupiFormacaoFacade supiFormacaoFacade;
    @EJB
    private SupiEstadoFacade supiEstadoFacade;
    @EJB
    private SupiFormacaoFuncionarioFacade supiFormacaoFuncionarioFacade;
    @EJB
    private SupiSeccaoFacade supiSeccaoFacade;

    private GrlProvincia grlProvincia;
    private GrlEndereco grlEndereco;
    private RhFuncionario rhFuncionario;
    private GrlMunicipio grlMunicipio;
    private SupiFormadorAux supiFormador;
    private SupiFormacao supiFormacao, pesquisaFormacao;
    private List<SupiFormacao> listaFormacao;
    private SupiEstado supiEstado;
    private SupiFormacaoFuncionario supiFormacaoFuncionario;
    private RhSeccaoTrabalho rhSeccaoTrabalho;
    private SupiOpcao supiOpcao;
    private SupiFormacaoFuncionarioPk supiFormacaoFuncionarioPk;

    private int listaFuncionarios[];
    ManipulaData manipulaData;
    Mensagem mensagem;
    String mostrarData;
    String mostrarData1;

    /**
     * Creates a new instance of SupiFormacaoBean
     */
    public SupiFormacaoBean()
    {
        manipulaData = new ManipulaData();
        mensagem = new Mensagem();
        mostrarData = "";
        supiOpcao = new SupiOpcao();
        grlMunicipio = new GrlMunicipio();
        supiEstado = new SupiEstado();
        //formacaoFuncionarioPK = new SuFormacaoFuncionarioPK();
        supiFormacaoFuncionario = new SupiFormacaoFuncionario();
        rhFuncionario = new RhFuncionario();
        supiFormacao = new SupiFormacao();
        //pesquisaFormacao = new SupiFormacao();
        supiFormacao.setFkIdFormadorAux(supiFormador);
        rhSeccaoTrabalho = new RhSeccaoTrabalho();
        supiFormador = new SupiFormadorAux();
        grlEndereco = new GrlEndereco();
        grlProvincia = new GrlProvincia();

    }
    
    public static SupiFormacaoBean getInstanciaBean()
    {
        return (SupiFormacaoBean) GeradorCodigo.getInstanciaBean("supiFormacaoBean");
    }
    
    public static SupiFormacao getInstancia()
    {
        SupiFormacao form = new SupiFormacao();

        form.setFkIdEndereco(new GrlEndereco());
        form.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
        form.getFkIdEndereco().getFkIdMunicipio().setFkIdProvincia(new GrlProvincia());
        form.getFkIdEndereco().getFkIdMunicipio().getFkIdProvincia().setFkIdPais(new GrlPais());
        
        return form;
    }
    
    
    
    

    public void limpar()
    {
        grlMunicipio = new GrlMunicipio();
        supiEstado = new SupiEstado();
        //formacaoFuncionarioPK = new SuFormacaoFuncionarioPK();
        supiFormacaoFuncionario = new SupiFormacaoFuncionario();
        rhFuncionario = new RhFuncionario();
        supiFormacao = new SupiFormacao();
        supiFormador = new SupiFormadorAux();
        listaFuncionarios = null;
        supiOpcao = new SupiOpcao();
        mostrarData = "";
        mostrarData1 = "";

    }

//    public SupiFormacao getInstanciaFormacao()
//    {
//        SupiFormacao form = new SupiFormacao();
//
//        form.setFkIdEndereco(new GrlEndereco());
//        form.getFkIdEndereco().setFkIdMunicipio(new GrlMunicipio());
//        form.getFkIdEndereco().getFkIdMunicipio().setFkIdProvincia(new GrlProvincia());
//        form.getFkIdEndereco().getFkIdMunicipio().getFkIdProvincia().setFkIdPais(new GrlPais());
//        form.setFkIdFormador(new SupiFormadorAux());
//        //form.getFkIdFormador().setFkIdFuncionario(new RhFuncionario());
//        //form.getFkIdFormador().setFkIdFuncionario(RhFuncionarioNovoBean.getInstanciaFuncionario());
//        //form.setFkIdFuncionario(RhFuncionarioNovoBean.getInstanciaFuncionario());
//        form.setLocal(new GrlMunicipio());
//        //onario(RhFuncionarioNovoBean.getInstanciaFuncionario());
//
//        return form;
//    }
//
//    public List<SupiFormacao> getListaFormacao()
//    {
//        if(listaFormacao == null)
//            listaFormacao = new ArrayList<>();
//        return listaFormacao;
//    }
//
//    public void setListaFormacao(List<SupiFormacao> listaFormacao)
//    {
//        this.listaFormacao = listaFormacao;
//    }
//
//    public SupiFormacao getPesquisaFormacao()
//    {
//        if (pesquisaFormacao == null)
//        {
//            pesquisaFormacao = getInstanciaFormacao();
//        }
//
//        return pesquisaFormacao;
//    }
//
//    public void setPesquisaFormacao(SupiFormacao pesquisaFormacao)
//    {
//        this.pesquisaFormacao = pesquisaFormacao;
//    }

//    public String mensagemDuracaoFormacao()
//    {
//
//        mostrarData = "";
//        supiFormacao.setDuracao(manipulaData.diferencaEmMeses(supiFormacao.getDataTermino(), supiFormacao.getDataInicio()));
//        int anos = supiFormacao.getDuracao() / 12;
//        int meses = supiFormacao.getDuracao() % 12;
//
//        if (anos > 0)
//        {
//            mostrarData = " " + anos + " Anos ";
//        }
//        if (meses > 0)
//        {
//
//            mostrarData = mostrarData + " " + meses + " Meses";
//        }
//        Mensagem msg = new Mensagem();
//        msg.addMessage(mostrarData, mostrarData);
//        return mostrarData;
//
//    }
//
//    public String mendagemDuracaoFormacao1()
//    {
//
//        supiFormacao.setDuracao(manipulaData.diferencaHoras(supiFormacao.getDataTermino(), supiFormacao.getDataInicio()));
//        int horas = supiFormacao.getDuracao();
//
//        if (horas >= 0)
//        {
//            mostrarData1 = "" + horas + " Horas ";
//        } else
//        {
//            mostrarData1 = "Hora Invalida";
//        }
//        return mostrarData1;
//    }

    public RhFuncionario getRhFuncionario()
    {
        if (rhFuncionario == null)
        {
            rhFuncionario = new RhFuncionario();
        }
        return rhFuncionario;
    }

    public void setRhFuncionario(RhFuncionario rhFuncionario)
    {
        this.rhFuncionario = rhFuncionario;
    }

    public GrlMunicipio getGrlMunicipio()
    {
        return grlMunicipio;
    }

    public void setGrlMunicipio(GrlMunicipio grlMunicipio)
    {
        this.grlMunicipio = grlMunicipio;
    }

    public SupiFormadorAux getSupiFormador()
    {
        return supiFormador;
    }

    public void setSupiFormador(SupiFormadorAux supiFormador)
    {
        this.supiFormador = supiFormador;
    }

    public GrlEndereco getGrlEndereco()
    {

        if (grlEndereco == null)
        {
            grlEndereco = new GrlEndereco();
        }
        return grlEndereco;

    }

    public void setGrlEndereco(GrlEndereco grlEndereco)
    {
        this.grlEndereco = grlEndereco;
    }

    public GrlProvincia getGrlProvincia()
    {
        return grlProvincia;
    }

    public void setGrlProvincia(GrlProvincia grlProvincia)
    {
        this.grlProvincia = grlProvincia;
    }

    public SupiFormacao getSupiFormacao()
    {

        return supiFormacao;
    }

    public void setSupiFormacao(SupiFormacao supiFormacao)
    {
        this.supiFormacao = supiFormacao;
    }

    public SupiEstado getSupiEstado()
    {
        return supiEstado;
    }

    public void setSupiEstado(SupiEstado supiEstado)
    {
        this.supiEstado = supiEstado;
    }

    public SupiFormacaoFuncionario getSupiFormacaoFuncionario()
    {
        return supiFormacaoFuncionario;
    }

    public void setSupiFormacaoFuncionario(SupiFormacaoFuncionario supiFormacaoFuncionario)
    {
        this.supiFormacaoFuncionario = supiFormacaoFuncionario;
    }

    public RhSeccaoTrabalho getRhSeccaoTrabalho()
    {
        return rhSeccaoTrabalho;
    }

    public void setRhSeccaoTrabalho(RhSeccaoTrabalho rhSeccaoTrabalho)
    {
        this.rhSeccaoTrabalho = rhSeccaoTrabalho;
    }

    public SupiOpcao getSupiOpcao()
    {
        return supiOpcao;
    }

    public void setSupiOpcao(SupiOpcao supiOpcao)
    {
        this.supiOpcao = supiOpcao;
    }

    public int[] getListaFuncionarios()
    {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(int[] listaFuncionarios)
    {
        this.listaFuncionarios = listaFuncionarios;
    }

    public ManipulaData getManipulaData()
    {
        return manipulaData;
    }

    public void setManipulaData(ManipulaData manipulaData)
    {
        this.manipulaData = manipulaData;
    }

    public Mensagem getMensagem()
    {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem)
    {
        this.mensagem = mensagem;
    }

    public String getMostrarData()
    {
        return mostrarData;
    }

    public SupiFormacaoFuncionarioPk getSupiFormacaoFuncionarioPk()
    {
        return supiFormacaoFuncionarioPk;
    }

    public void setSupiFormacaoFuncionarioPk(SupiFormacaoFuncionarioPk supiFormacaoFuncionarioPk)
    {
        this.supiFormacaoFuncionarioPk = supiFormacaoFuncionarioPk;
    }

    public void setMostrarData(String mostrarData)
    {
        this.mostrarData = mostrarData;
    }

    public String getMostrarData1()
    {
        return mostrarData1;
    }

    public void setMostrarData1(String mostrarData1)
    {
        this.mostrarData1 = mostrarData1;
    }

    public List<SupiEstado> listarEstados()
    {
        return supiEstadoFacade.findAll();
    }

    public List<GrlMunicipio> listaMunicipios()
    {
        return grlMunicipioFacade.findAll();
    }

    public List<GrlProvincia> listaProvincias()
    {
        return grlProvinciaFacade.findAll();
    }

//    public void pesquisarFormacao()
//    {
//        listaFormacao = supiFormacaoFacade.findFormacao(pesquisaFormacao);
//
//        if (listaFormacao.isEmpty())
//        {
//            Mensagem.erroMsg("Nenhum Formador encontrado para esta pesquisa");
//        }
//    }

//    public void limparPesquisas()
//    {
//        setPesquisaFormacao(getInstanciaFormacao());
//        listaFormacao = new ArrayList<>();
//    }

    public List<RhSeccaoTrabalho> buscaSeccao()
    {

        List<RhSeccaoTrabalho> lista = rhSeccaoTrabalhoFacade.findAll();
        List<RhSeccaoTrabalho> listaResultado = new ArrayList();

        for (RhSeccaoTrabalho resultado : lista)
        {
            if (resultado.getFkIdDepartamento().getPkIdDepartamento() == Constantes.DEPARTAMENTO_ENFERMAGEM)
            {
                listaResultado.add(resultado);
            }

        }
        return listaResultado;

    }

    public List<SupiFormacaoFuncionario> buscaFormando()
    {

        List<SupiFormacaoFuncionario> lista = supiFormacaoFuncionarioFacade.findAll();
        List<SupiFormacaoFuncionario> listaResultado = new ArrayList();

        for (SupiFormacaoFuncionario resultado : lista)
        {
            if (resultado.getFkIdFormacao().getPkIdFormacao() == supiFormacao.getPkIdFormacao())
            {
                listaResultado.add(resultado);
            }

        }
        return listaResultado;

    }

    ////   METODO RETORNA FUNCIONARIO DE UMA DETERMINADASECCAO
    public List<RhFuncionario> buscaFuncionarios(Integer idSeccaoTrabalho)
    {

        List<RhFuncionario> listaFuncionario = rhFuncionarioFacade.findAll();
        List<RhFuncionario> listaRetorno = new ArrayList<>();

        for (RhFuncionario f : listaFuncionario)
        {
            if (f.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() == idSeccaoTrabalho)
            {
                listaRetorno.add(f);
            }

        }
        return listaRetorno;

    }

    public List<RhFuncionario> retornaFuncionarios(int idSeccaoTrabalho)
    {
        List<RhFuncionario> listaFunc = rhFuncionarioFacade.findAll();
        List<RhFuncionario> listaFuncRetorna = new ArrayList<>();

        for (RhFuncionario f : listaFunc)
        {
            if (f.getFkIdSeccaoTrabalho().getPkIdSeccaoTrabalho() == idSeccaoTrabalho)
            {
                listaFuncRetorna.add(f);
            }
        }
        return listaFuncRetorna;
    }

    /*
     METODOS DE PREPARACAO DE EDICAO
     */
    public String prepararEditarAbertura(Integer idFormacao)
    {
        supiFormacao = supiFormacaoFacade.find(idFormacao);
        return "editarAberturaDeFormacao";
    }

    public String prepararEditarFormacao(Integer idFormacao)
    {
        supiFormacaoFuncionario = supiFormacaoFuncionarioFacade.find(idFormacao);
        return "editarFormacao";
    }

    /*
     METODOS DE GRAVACAO
     */
//    public String gravarFormacao()
//    {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//
//        try
//        {
//            GrlMunicipio cen = grlMunicipioFacade.find(grlMunicipio.getPkIdMunicipio());
//            SupiFormadorAux form = supiFormadorFacade.find(supiFormador.getPkIdFormadorAux());
//            GrlEndereco end = new GrlEndereco();
//            end.setFkIdMunicipio(cen);
//            supiFormacao.setLocal(cen);
//            supiFormacao.setFkIdFormador(form);
//            supiFormacao.setFkIdEndereco(end);
//            supiFormacao.setDataInicio(new Date());
//            supiFormacao.setDataTermino(new Date());
//
//            if (supiFormacao.getDataInicio().after(supiFormacao.getDataTermino()))
//            {
//                mensagem.addMessage("Hora Inicial não pode ser Maior", null);
//                return "formacao";
//            } else
//            {
//                supiFormacao.setDuracao(manipulaData.diferencaHoras(supiFormacao.getDataTermino(), supiFormacao.getDataInicio()));
//                grlEnderecoFacade.create(end);
//                supiFormacaoFacade.create(supiFormacao);
//                facesContext.addMessage(null, new FacesMessage("Dados Registados com sucesso"));
//                limpar();
//            }
//
//        } catch (Exception e)
//        {
//            mensagem.addMessage("Dados Não Registados", null);
//        }
//        return "listarAberturaDeFormacao";
//
//    }
//
////    public String gravarFormacaoFuncionario()
////    {
////
////        FacesContext facesContext = FacesContext.getCurrentInstance();
////
////        try
////        {
////            supiFormacaoFuncionario.setDataCadastro(new Date());
////            //formacaoFuncionario.setSuformacaoTemporaria(formacaoTemporaria);
////            supiFormacaoFuncionario.setSupiFormacao(supiFormacao);
////            supiFormacaoFuncionario.setFkIdEstado(supiEstadoFacade.find(1));
////
////            if (listaFuncionarios.length > 0)
////            {
////
////                for (int i : listaFuncionarios)
////                {
////
////                    RhFuncionario func = new RhFuncionario();
////                    func = rhFuncionarioFacade.find(i);
////
////                    if (!verificaFuncionario(supiFormacao.getPkIdFormacao(), func.getPkIdFuncionario()))
////                    {
////
////                        //formacaoFuncionario.setGrhFuncionario(func);
////                        supiFormacaoFuncionario.setFkIdFuncionario(func);
////
////                        //formacaoFuncionarioPK.setIdFuncionario(formacaoFuncionario.getGrhFuncionario().getIdFuncionario());
////                        //formacaoFuncionarioPK.setSuformacaoTemporariaidFormacaoTemporaria(formacaoFuncionario.getSuformacaoTemporaria().getIdFormacaoTemporaria());
////                        supiFormacaoFuncionarioPk.setPkIdFormacaoFuncionarioPk(supiFormacaoFuncionario.getFkIdFuncionario().getPkIdFuncionario());
////                        supiFormacaoFuncionarioPk.setFkIdFormacao(supiFormacaoFuncionario.getFkIdFormacao());
////
////                        supiFormacaoFuncionario.setSupiFormacaoFuncionarioPk(supiFormacaoFuncionarioPk);
////                        supiFormacaoFuncionarioFacade.create(supiFormacaoFuncionario);
////
////                    } else
////                    {
////                        facesContext.addMessage(null, new FacesMessage("Este Funcionário já foi adicionado:" + func.getFkIdPessoa().getNome()));
////                    }
////
////                }
////                facesContext.addMessage(null, new FacesMessage("Dados Registados com sucesso"));
////                limpar();
////
////            }
////        } catch (Exception e)
////        {
////            facesContext.addMessage(null, new FacesMessage("Dados Não Registados"));
////        }
////
////        return "listarFormandos";
////    }
//
//    public boolean verificaFuncionario(int idFormacao, int idFuncionario)
//    {
//
//        List<SupiFormacaoFuncionario> lista = supiFormacaoFuncionarioFacade.findAll();
//
//        for (SupiFormacaoFuncionario ff : lista)
//        {
//
//            if (ff.getFkIdFormacao().getPkIdFormacao() == idFormacao && ff.getFkIdFuncionario().getPkIdFuncionario() == idFuncionario)
//            {
//
//                return true;
//
//            }
//
//        }
//        return false;
//
//    }

    /*
     METODOS DE ACTUALIZACAO
     */
//    public void editarFormacao()
//    {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        GrlMunicipio cen = grlMunicipioFacade.find(grlMunicipio.getPkIdMunicipio());
//        SupiFormadorAux form = supiFormadorFacade.find(supiFormador.getPkIdFormadorAux());
//
//        supiFormacao.setLocal(cen);
//        supiFormacao.setFkIdFormador(form);
//        supiFormacao.setDataInicio(new Date());
//        supiFormacao.setDataTermino(new Date());
//        
//
//        if (supiFormacao.getDataInicio().after(supiFormacao.getDataTermino()))
//        {
//            facesContext.addMessage(null, new FacesMessage("Data Inicial não pode ser Maior"));
//        } else
//        {
//            supiFormacao.setDuracao(manipulaData.diferencaEmMeses(supiFormacao.getDataTermino(), supiFormacao.getDataInicio()));
//            supiFormacaoFacade.edit(supiFormacao);
//            facesContext.addMessage(null, new FacesMessage("Dados Actualizados com sucesso"));
//        }
//
//    }

//    public void editarFormacaoFuncionario()
//    {
//
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//
//        try
//        {
//            supiFormacaoFuncionario.setDataCadastro(new Date());
//            supiFormacaoFuncionario.setSupiFormacao(supiFormacao);
//            supiFormacaoFuncionario.setFkIdEstado(supiEstadoFacade.find(supiEstado.getPkIdEstado()));
//
//            if (listaFuncionarios.length > 0)
//            {
//
//                for (int i : listaFuncionarios)
//                {
//                    RhFuncionario func = new RhFuncionario();
//                    func = rhFuncionarioFacade.find(i);
//                    supiFormacaoFuncionario.setRhFuncionario(func);
//
//                    supiFormacaoFuncionarioPk.setFkIdFuncionario(supiFormacaoFuncionario.getFkIdFuncionario());
//                    supiFormacaoFuncionarioPk.setFkIdFormacao(supiFormacaoFuncionario.getFkIdFormacao());
//
//                    supiFormacaoFuncionario.setSupiFormacaoFuncionarioPk(supiFormacaoFuncionarioPk);
//                    supiFormacaoFuncionarioFacade.edit(supiFormacaoFuncionario);
//
//                }
//                facesContext.addMessage(null, new FacesMessage("Dados Actualizados com sucesso"));
//                //limpar();
//
//            }
//        } catch (Exception e)
//        {
//            facesContext.addMessage(null, new FacesMessage("Dados Não Actualiados"));
//        }
//
//    }

    //METODOS DE LISTAGENS
    public List<SupiFormacaoFuncionario> listarTodas()
    {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        try
        {

            return supiFormacaoFuncionarioFacade.findAll();
        } catch (Exception e)
        {
        }
        return null;
    }

    public List<SupiFormacao> listarAberturas()
    {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        try
        {
            List<SupiFormacao> avaliacoes = supiFormacaoFacade.findAll();
            return avaliacoes;

        } catch (Exception e)
        {
        }
        return null;
    }

    /*
     METODOS DE REMOCAO
     */
//    public void eliminarAbertura(int idFormacao)
//    {
//
//        FacesContext fc = FacesContext.getCurrentInstance();
//
//        try
//        {
//
//            supiFormacao = supiFormacaoFacade.find(idFormacao);
//
//            if (supiFormacao.getSupiFormacaoFuncionarioList() != null)
//            {
//
//                supiFormacaoFacade.remove(supiFormacao);
//
//                fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));
//
//                supiFormacao = new SupiFormacao();
//
//            } else
//            {
//                fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));
//
//            }
//
//        } catch (Exception ex)
//        {
//            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
//        }
//    }

    public void eliminarFormacao(int idFormacao)
    {

        FacesContext fc = FacesContext.getCurrentInstance();

        try
        {

            supiFormacaoFuncionario = supiFormacaoFuncionarioFacade.find(idFormacao);

            if (supiFormacaoFuncionario != null)
            {

                supiFormacaoFuncionarioFacade.remove(supiFormacaoFuncionario);

                fc.addMessage(null, new FacesMessage("Dados Removidos com sucesso!"));

                supiFormacaoFuncionario = new SupiFormacaoFuncionario();

            } else
            {
                fc.addMessage(null, new FacesMessage("Erro ao Eliminar " + ""));

            }

        } catch (Exception ex)
        {
            fc.addMessage(null, new FacesMessage("Erro :" + "" + ex.getMessage()));
        }
    }

    /**
     * **********************************
     * Vizualizar DADOS ** **********************************
     */
    public void visualizarDadosFormacao(Integer idFormacaoFuncionario)
    {
        supiFormacaoFuncionario = supiFormacaoFuncionarioFacade.find(idFormacaoFuncionario);
    }

    public void visualizarDadosAbertura(Integer idFormacao)
    {
        supiFormacaoFuncionario = supiFormacaoFuncionarioFacade.find(idFormacao);
    }

    public String carregarFormcao(Integer idFormacao)
    {
        supiFormacao = supiFormacaoFacade.find(idFormacao);
        return "adicionarFormandos";
    }

    public String selecionarFormacao(Integer idFormacao)
    {

        supiFormacao = supiFormacaoFacade.find(idFormacao);
        return "listarFormandos";
    }
    
    
    
    
}
