/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.interbean;

import entidade.FarmCategoriaMedicamento;
import entidade.FarmProduto;
import entidade.FarmProdutoHasAviso;
import entidade.FarmProdutoHasContraIndicacao;
import entidade.FarmProdutoHasEfeitoSecundario;
import entidade.FarmProdutoHasFarmaco;
import entidade.FarmProdutoHasIndicacao;
import entidade.FarmProdutoHasObservacao;
import entidade.FarmProdutoHasOutroComponente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import managedBean.farmbean.produto.FarmProdutoNovoBean;
import managedBean.farmbean.tabelasAuxiliares.FarmCategoriaMedicamentoListarBean;
import managedBean.farmbean.tabelasAuxiliares.FarmConsultaCategoriaMedicamentoBean;
import sessao.FarmCategoriaMedicamentoFacade;
import sessao.FarmProdutoFacade;
import sessao.FarmProdutoHasAvisoFacade;
import sessao.FarmProdutoHasContraIndicacaoFacade;
import sessao.FarmProdutoHasEfeitoSecundarioFacade;
import sessao.FarmProdutoHasFarmacoFacade;
import sessao.FarmProdutoHasIndicacaoFacade;
import sessao.FarmProdutoHasObservacaoFacade;
import sessao.FarmProdutoHasOutroComponenteFacade;
import util.GeradorCodigo;
import util.Mensagem;

/**
 *
 * @author armindo
 */
@ManagedBean
@SessionScoped
public class InterFarmProdutoListarBean implements Serializable
{

    @EJB
    FarmProdutoFacade produtoFacade;
    private FarmProduto produtoPesquisa;

    private List<FarmProduto> produtosPesquisados;
    @EJB
    FarmProdutoHasFarmacoFacade farmacoProdutoFacade;

    @EJB
    FarmProdutoHasEfeitoSecundarioFacade efeitoSecundarioFacade;

    @EJB
    FarmProdutoHasAvisoFacade avisoFacade;

    @EJB
    FarmProdutoHasObservacaoFacade observacaoFacade;

    @EJB
    FarmProdutoHasIndicacaoFacade indicacaoFacade;

    @EJB
    FarmProdutoHasContraIndicacaoFacade contraIndicacaoFacade;

    @EJB
    FarmCategoriaMedicamentoFacade categoriaMedicamentoFacade;
    @EJB
    FarmProdutoHasOutroComponenteFacade outroComponenteFacade;

    private String categoriaPesquisar;
    private FarmCategoriaMedicamento categoriaNivel1, categoriaNivel2, categoriaNivel3, categoriaNivel4;
    private List<FarmCategoriaMedicamento> listaNivel1, listaNivel2, listaNivel3, listaNivel4;

    /**
     * Creates a new instance of produtoListarBean
     */
    public InterFarmProdutoListarBean()
    {
    }

    public static InterFarmProdutoListarBean getInstanciaBean()
    {
        return (InterFarmProdutoListarBean) GeradorCodigo.getInstanciaBean("interFarmProdutoListarBean");
    }

    public void pesquisarProdutos()
    {
        if (getCategoriaNivel1().getPkIdCategoriaMedicamento() == null)
        {
            produtoPesquisa.setFkIdCategoriaMedicamento(new FarmCategoriaMedicamento());
        }
        else if (getCategoriaNivel2().getPkIdCategoriaMedicamento() == null)
        {
            produtoPesquisa.setFkIdCategoriaMedicamento(categoriaNivel1);
        }
        else if (getCategoriaNivel3().getPkIdCategoriaMedicamento() == null)
        {
            produtoPesquisa.setFkIdCategoriaMedicamento(categoriaNivel2);
        }
        else if (getCategoriaNivel4().getPkIdCategoriaMedicamento() == null)
        {
            produtoPesquisa.setFkIdCategoriaMedicamento(categoriaNivel3);
        }
        else
        {
            produtoPesquisa.setFkIdCategoriaMedicamento(categoriaNivel4);
        }

        setProdutosPesquisados(produtoFacade.findProduto(getProdutoPesquisa()));
        if (getProdutosPesquisados().isEmpty())
        {
            Mensagem.warnMsg("Nenhum registo encontrado para esta pesquisa.");
        }
        else
        {
            Mensagem.sucessoMsg("Pesquisa efectuada com sucesso. " + getProdutosPesquisados().size() + " registo(s) retornado(s).");
        }
    }

    public String obterFarmacosItem()
    {
        String farmacos = "Não Especificados";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasFarmaco> lista = farmacoProdutoFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                farmacos = "";
            }
            for (FarmProdutoHasFarmaco item : lista)
            {
                farmacos = farmacos + item.getFkIdFarmaco().getDescricao() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return farmacos;
    }

    public String obterEfeitosSecundariosItem()
    {
        String efeitosSecundarios = "Não Especificados";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasEfeitoSecundario> lista = efeitoSecundarioFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                efeitosSecundarios = "";
            }
            for (FarmProdutoHasEfeitoSecundario item : lista)
            {
                efeitosSecundarios = efeitosSecundarios + item.getFkIdEfeitoSecundario().getDescricaoEfeitoSecundario() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return efeitosSecundarios;
    }

    public String obterOutrosComponentesItem()
    {
        String outrosComponentes = "Não Especificados";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasOutroComponente> lista = outroComponenteFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                outrosComponentes = "";
            }
            for (FarmProdutoHasOutroComponente item : lista)
            {
                outrosComponentes = outrosComponentes + item.getFkIdOutroComponente().getDescricaoComponente() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return outrosComponentes;
    }

    public String obterAvisosItem()
    {
        String avisos = "Não Especificados";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasAviso> lista = avisoFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                avisos = "";
            }
            for (FarmProdutoHasAviso item : lista)
            {
                avisos = avisos + item.getFkIdAviso().getDescricao() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return avisos;
    }

    public String obterIndicacoesItem()
    {
        String indicacoes = "Não Especificadas";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasIndicacao> lista = indicacaoFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                indicacoes = "";
            }
            for (FarmProdutoHasIndicacao item : lista)
            {
                indicacoes = indicacoes + item.getFkIdIndicacao().getDescricao() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return indicacoes;
    }

    public String obterContraIndicacoesItem()
    {
        String contraIndicacoes = "Não Especificadas";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasContraIndicacao> lista = contraIndicacaoFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                contraIndicacoes = "";
            }
            for (FarmProdutoHasContraIndicacao item : lista)
            {
                contraIndicacoes = contraIndicacoes + item.getFkIdContraIndicacao().getDescricao() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return contraIndicacoes;
    }

    public String obterObservacoesItem()
    {
        String observacoes = "Não Especificadas";
        if (produtoPesquisa.getPkIdProduto() != null)
        {
            List<FarmProdutoHasObservacao> lista = observacaoFacade.findByIdProduto(produtoPesquisa);
            if (lista.size() > 0)
            {
                observacoes = "";
            }
            for (FarmProdutoHasObservacao item : lista)
            {
                observacoes = observacoes + item.getFkIdObservacao().getDescricao() + "; ";
            }
        }
        else
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return observacoes;
    }

    public void limparPesquisa()
    {
        setProdutoPesquisa(FarmProdutoNovoBean.getInstancia());
        produtosPesquisados = new ArrayList<>();
        produtoPesquisa = FarmProdutoNovoBean.getInstancia();
    }

    public void eliminar()
    {
        try
        {
            produtoFacade.remove(produtoPesquisa);
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
            pesquisarProdutos();
            Mensagem.sucessoMsg("O produto foi eliminado com Sucesso.");
        }
        catch (Exception ex)
        {
            Mensagem.warnMsg("O produto não poder ser eliminado, porque está a ser utilizado." + ex.getMessage());
        }

    }

    public void changeCategoriaNivel1(ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {
            int cat1 = (Integer) e.getNewValue();
            categoriaNivel1.setPkIdCategoriaMedicamento(cat1);
            categoriaNivel2 = null;
            categoriaNivel3 = null;
            categoriaNivel4 = null;
            actualizarCatNivel2(cat1);
        }
        else
        {
            listaNivel2 = new ArrayList<>();
            listaNivel3 = new ArrayList<>();
            listaNivel4 = new ArrayList<>();
        }
    }

    public void changeCategoriaNivel2(ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {

            int cat2 = (Integer) e.getNewValue();
            categoriaNivel3 = null;
            categoriaNivel4 = null;

            actualizarCatNivel3(cat2);
        }
        else
        {
            listaNivel2 = new ArrayList<>();
            listaNivel3 = new ArrayList<>();
            listaNivel4 = new ArrayList<>();
        }
    }

    public void changeCategoriaNivel3(ValueChangeEvent e)
    {
        if (e.getNewValue() != null)
        {
            int cat3 = (Integer) e.getNewValue();
            categoriaNivel4 = null;

            actualizarCatNivel4(cat3);
        }
        else
        {
            listaNivel2 = new ArrayList<>();
            listaNivel3 = new ArrayList<>();
            listaNivel4 = new ArrayList<>();
        }
    }

    private void actualizarCatNivel2(Integer cat1)
    {
        if (cat1 != null)
        {
            listaNivel2 = categoriaMedicamentoFacade.findFilhosCategoriaOrderByCapitulo(new FarmCategoriaMedicamento(cat1));
        }
    }

    private void actualizarCatNivel3(Integer cat2)
    {
        if (cat2 != null)
        {
            listaNivel3 = categoriaMedicamentoFacade.findFilhosCategoriaOrderByCapitulo(new FarmCategoriaMedicamento(cat2));
        }
    }

    private void actualizarCatNivel4(Integer cat3)
    {
        if (cat3 != null)
        {
            listaNivel4 = categoriaMedicamentoFacade.findFilhosCategoriaOrderByCapitulo(new FarmCategoriaMedicamento(cat3));
        }
    }

    public String irParaGestaoLotes()
    {
        return "loteProdutoGerir.xhtml?faces-redirect=true";
    }

    public String redireccionar()
    {
        return "produtoNovoEscolherTipoFarm.xhtml?faces-redirect=true";
    }

    public String voltar()
    {
        return "produtosListar.xhtml?faces-redirect=true";
    }

    /**
     * @return the produtoPesquisa
     */
    public FarmProduto getProdutoPesquisa()
    {
        if (produtoPesquisa == null)
        {
            produtoPesquisa = FarmProdutoNovoBean.getInstancia();
        }
        return produtoPesquisa;
    }

    /**
     * @param produtoPesquisa the produtoPesquisa to set
     */
    public void setProdutoPesquisa(FarmProduto produtoPesquisa)
    {
        FarmProdutoNovoBean.getInstanciaBean().setProdutoEditar(produtoPesquisa);
        this.produtoPesquisa = produtoPesquisa;
    }

    /**
     * @return the produtosPesquisados
     */
    public List<FarmProduto> getProdutosPesquisados()
    {
        return produtosPesquisados;
    }

    /**
     * @param produtosPesquisados the produtosPesquisados to set
     */
    public void setProdutosPesquisados(List<FarmProduto> produtosPesquisados)
    {
        this.produtosPesquisados = produtosPesquisados;
    }

    /**
     * @return the categoriaPesquisar
     */
    public String getCategoriaPesquisar()
    {
        return categoriaPesquisar;
    }

    /**
     * @param categoriaPesquisar the categoriaPesquisar to set
     */
    public void setCategoriaPesquisar(String categoriaPesquisar)
    {
        this.categoriaPesquisar = categoriaPesquisar;
    }

    public FarmCategoriaMedicamento getCategoriaNivel1()
    {
        if (categoriaNivel1 == null)
        {
            categoriaNivel1 = FarmCategoriaMedicamentoListarBean.getInstanciaCategoria();
        }

        return categoriaNivel1;
    }

    public void setCategoriaNivel1(FarmCategoriaMedicamento categoriaNivel1)
    {
        this.categoriaNivel1 = categoriaNivel1;
    }

    public FarmCategoriaMedicamento getCategoriaNivel2()
    {

        if (categoriaNivel2 == null)
        {
            categoriaNivel2 = FarmCategoriaMedicamentoListarBean.getInstanciaCategoria();
        }

        return categoriaNivel2;
    }

    public void setCategoriaNivel2(FarmCategoriaMedicamento categoriaNivel2)
    {
        this.categoriaNivel2 = categoriaNivel2;
    }

    public FarmCategoriaMedicamento getCategoriaNivel3()
    {

        if (categoriaNivel3 == null)
        {
            categoriaNivel3 = FarmCategoriaMedicamentoListarBean.getInstanciaCategoria();
        }

        return categoriaNivel3;
    }

    public void setCategoriaNivel3(FarmCategoriaMedicamento categoriaNivel3)
    {
        this.categoriaNivel3 = categoriaNivel3;
    }

    public FarmCategoriaMedicamento getCategoriaNivel4()
    {

        if (categoriaNivel4 == null)
        {
            categoriaNivel4 = FarmCategoriaMedicamentoListarBean.getInstanciaCategoria();
        }

        return categoriaNivel4;
    }

    public void setCategoriaNivel4(FarmCategoriaMedicamento categoriaNivel4)
    {
        this.categoriaNivel4 = categoriaNivel4;
    }

    public List<FarmCategoriaMedicamento> getListaNivel1()
    {
        if (listaNivel1 == null)
        {
            listaNivel1 = FarmConsultaCategoriaMedicamentoBean.getInstanciaBean().ordenarRaizesPorCapitulo(categoriaMedicamentoFacade.findCategoriasRaizesOrderByCapitulo());
        }
        return listaNivel1;
    }

    public void setListaNivel1(List<FarmCategoriaMedicamento> listaNivel1)
    {
        this.listaNivel1 = listaNivel1;
    }

    public List<FarmCategoriaMedicamento> getListaNivel2()
    {
        actualizarCatNivel2(categoriaNivel1.getPkIdCategoriaMedicamento());
        if (listaNivel2 == null)
        {
            listaNivel2 = new ArrayList<>();
        }
        return listaNivel2;
    }

    public void setListaNivel2(List<FarmCategoriaMedicamento> listaNivel2)
    {
        this.listaNivel2 = listaNivel2;
    }

    public List<FarmCategoriaMedicamento> getListaNivel3()
    {
        actualizarCatNivel3(categoriaNivel2.getPkIdCategoriaMedicamento());
        if (listaNivel3 == null)
        {
            listaNivel3 = new ArrayList<>();
        }
        return listaNivel3;
    }

    public void setListaNivel3(List<FarmCategoriaMedicamento> listaNivel3)
    {
        this.listaNivel3 = listaNivel3;
    }

    public List<FarmCategoriaMedicamento> getListaNivel4()
    {
        actualizarCatNivel4(categoriaNivel3.getPkIdCategoriaMedicamento());
        if (listaNivel4 == null)
        {
            listaNivel4 = new ArrayList<>();
        }
        return listaNivel4;
    }

    public void setListaNivel4(List<FarmCategoriaMedicamento> listaNivel4)
    {
        this.listaNivel4 = listaNivel4;
    }
}
