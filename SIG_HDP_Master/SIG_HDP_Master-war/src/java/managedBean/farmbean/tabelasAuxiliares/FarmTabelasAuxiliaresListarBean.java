package managedBean.farmbean.tabelasAuxiliares;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import entidade.FarmAviso;
import entidade.FarmCategoriaMedicamento;
import entidade.FarmEfeitoSecundario;
import entidade.FarmFarmaco;
import entidade.FarmFormaFarmaceutica;
import entidade.FarmOutroComponente;
import entidade.FarmTipoUnidadeMedida;
import entidade.FarmUnidadeMedida;
import entidade.FarmViaAdministracao;
import entidade.GrlMarcaProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import managedBean.grlbean.carregamentoExcel.GrlMarcaProdutoExcelBean;
import sessao.FarmAvisoFacade;
import sessao.FarmCategoriaMedicamentoFacade;
import sessao.FarmEfeitoSecundarioFacade;
import sessao.FarmFarmacoFacade;
import sessao.FarmFormaFarmaceuticaFacade;
import sessao.FarmOutroComponenteFacade;
import sessao.FarmUnidadeMedidaFacade;
import sessao.FarmViaAdministracaoFacade;
import sessao.GrlMarcaProdutoFacade;
import util.Mensagem;

/**
 *
 * @author elisangela
 */
@ManagedBean
@SessionScoped
public class FarmTabelasAuxiliaresListarBean implements Serializable
{

    @EJB
    FarmFarmacoFacade farmacoFacade;
    @EJB
    FarmFormaFarmaceuticaFacade formafarmaceuticaFacade;
    @EJB
    FarmCategoriaMedicamentoFacade categoriaMedicamentoFacade;
    @EJB
    FarmViaAdministracaoFacade viaAdministracaoFacade;
    @EJB
    FarmUnidadeMedidaFacade unidadeMedidaFacade;
    @EJB
    FarmOutroComponenteFacade outroComponenteFacade;
    @EJB
    FarmEfeitoSecundarioFacade efeitoSecundarioFacade;
    @EJB
    GrlMarcaProdutoFacade marcaProdutoFacade;
    @EJB
    FarmAvisoFacade avisoFacade;

    private List<FarmCategoriaMedicamento> listaCategorias;
    private List<FarmFarmaco> listaFarmacos;
    private List<GrlMarcaProduto> listaMarcaProdutos;
    private List<FarmUnidadeMedida> listaUnidadesMedidas;
    private List<FarmFormaFarmaceutica> listaFormasFarmaceuticas;
    private List<FarmViaAdministracao> listaViaAdministracao;
    private List<FarmEfeitoSecundario> listaEfeitosSecundarios;
    private List<FarmOutroComponente> listaOutrosComponentes;
    private List<FarmAviso> listaAvisos;

    /**
     * Creates a new instance of FarmTabelasAuxiliaresListarBean
     */
    public FarmTabelasAuxiliaresListarBean ()
    {
    }

    public static FarmTabelasAuxiliaresListarBean getInstanciaBean ()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        FarmTabelasAuxiliaresListarBean farmTabelasAuxiliaresListarBean
                                        = (FarmTabelasAuxiliaresListarBean) context.getELContext().getELResolver().getValue(context.getELContext(),
                                                                                                                            null, "farmTabelasAuxiliaresListarBean");

        return farmTabelasAuxiliaresListarBean;
    }

    public void pesquisarFarmacos ()
    {
        listaFarmacos = farmacoFacade.findFarmaco(new FarmFarmaco());
        if (listaFarmacos.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaFarmacos.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarAvisos ()
    {
        listaAvisos = avisoFacade.findAviso(new FarmAviso());
        if (listaAvisos.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaFarmacos.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarFormasFarmaceuticas ()
    {
        listaFormasFarmaceuticas = formafarmaceuticaFacade.findFormaFarmaceutica(new FarmFormaFarmaceutica());
        if (listaFormasFarmaceuticas.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getListaFarmacos().size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarViasDeAdministracao ()
    {
        listaViaAdministracao = viaAdministracaoFacade.findViaAdministracao(new FarmViaAdministracao());
        if (listaViaAdministracao.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaViaAdministracao.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarMarcadDeProduto ()
    {
        listaMarcaProdutos = marcaProdutoFacade.findMarcaProduto(new GrlMarcaProduto());
        if (listaMarcaProdutos.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaMarcaProdutos.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarUnidadesDeMedidas ()
    {
        FarmUnidadeMedida unidadePesquisa = new FarmUnidadeMedida();
        unidadePesquisa.setFkIdTipoUnidadeMedida(new FarmTipoUnidadeMedida());
        listaUnidadesMedidas = unidadeMedidaFacade.findUnidadeMedida(unidadePesquisa);
        
        if (listaUnidadesMedidas.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaUnidadesMedidas.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarEfeitosSecundarios ()
    {
        listaEfeitosSecundarios = efeitoSecundarioFacade.findEfeitoSecundario(new FarmEfeitoSecundario());
        if (listaEfeitosSecundarios.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaEfeitosSecundarios.size() + " registo(s) retornado(s).");
        }
    }

    public void pesquisarOutrosComponentes ()
    {
        listaOutrosComponentes = outroComponenteFacade.findOutroComponente(new FarmOutroComponente());
        if (listaOutrosComponentes.isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + listaOutrosComponentes.size() + " registo(s) retornado(s).");
        }
    }

    /**
     * @return the listaCategorias
     */
    public List<FarmCategoriaMedicamento> getListaCategorias ()
    {
        if (listaCategorias == null)
        {
            listaCategorias = new ArrayList<>();
        }
        return listaCategorias;
    }

    /**
     * @return the listaFarmacos
     */
    public List<FarmFarmaco> getListaFarmacos ()
    {
        if (listaFarmacos == null)
        {
            pesquisarFarmacos();
        }
        return listaFarmacos;
    }

    /**
     * @return the listaMarcaProdutos
     */
    public List<GrlMarcaProduto> getListaMarcaProdutos ()
    {
        if (listaMarcaProdutos == null)
        {
            pesquisarMarcadDeProduto();
        }
        return listaMarcaProdutos;
    }

    /**
     * @return the listaUnidadesMedidas
     */
    public List<FarmUnidadeMedida> getListaUnidadesMedidas ()
    {
        if (listaUnidadesMedidas == null)
        {
            pesquisarUnidadesDeMedidas();
        }
        return listaUnidadesMedidas;
    }

    /**
     * @return the listaFormasFarmaceuticas
     */
    public List<FarmFormaFarmaceutica> getListaFormasFarmaceuticas ()
    {
        if (listaFormasFarmaceuticas == null)
        {
            pesquisarFormasFarmaceuticas();
        }
        return listaFormasFarmaceuticas;
    }

    /**
     * @return the listaViaAdministracao
     */
    public List<FarmViaAdministracao> getListaViaAdministracao ()
    {
        if (listaViaAdministracao == null)
        {
            pesquisarViasDeAdministracao();
        }
        return listaViaAdministracao;
    }

    /**
     * @return the listaEfeitosSecundarios
     */
    public List<FarmEfeitoSecundario> getListaEfeitosSecundarios ()
    {
        if (listaViaAdministracao == null)
        {
            pesquisarEfeitosSecundarios();
        }
        return listaEfeitosSecundarios;
    }

    /**
     * @return the listaOutrosComponentes
     */
    public List<FarmOutroComponente> getListaOutrosComponentes ()
    {
        if (listaViaAdministracao == null)
        {
            pesquisarOutrosComponentes();
        }
        return listaOutrosComponentes;
    }

    public List<FarmAviso> getListaAvisos ()
    {
        if (listaAvisos == null)
        {
            pesquisarAvisos();
        }
        return listaAvisos;
    }

    public void setListaAvisos (List<FarmAviso> listaAvisos)
    {
        this.listaAvisos = listaAvisos;
    }

    public void carregarMarcasDeProduto ()
    {
        GrlMarcaProdutoExcelBean.getInstanciaBean().carregarMarcaProdutoTabela();
        Mensagem.sucessoMsg("Carregamento Efectuado com sucesso.");

        pesquisarMarcadDeProduto();
    }
}
