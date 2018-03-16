/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.rhbean;

import entidade.RhCategoriaProfissional;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.UserTransaction;
import managedBean.rhbean.carregamentoExcel.RhCategoriaProfissionalExcelBean;
import sessao.RhCategoriaProfissionalFacade;
import util.Mensagem;

/**
 *
 * @author Ornela F. Boaventura
 */
@ManagedBean
@ViewScoped
public class RhCategoriaProfissionalBean implements Serializable
{

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private RhCategoriaProfissionalFacade categoriaProfissionalFacade;

    /**
     *
     * Entidades
     */
    private RhCategoriaProfissional categoria, categoriaPesquisa;
    private List<RhCategoriaProfissional> categoriaList;

    /**
     * Creates a new instance of CategoriaBean
     */
    public RhCategoriaProfissionalBean ()
    {
    }

    public RhCategoriaProfissional getCategoria ()
    {
        if (this.categoria == null)
        {
            categoria = new RhCategoriaProfissional();
        }

        return categoria;
    }

    public void setCategoria (RhCategoriaProfissional categoria)
    {
        this.categoria = categoria;
    }

    public RhCategoriaProfissional getCategoriaPesquisa ()
    {
        if (categoriaPesquisa == null)
        {
            categoriaPesquisa = new RhCategoriaProfissional();
        }
        return categoriaPesquisa;
    }

    public void setCategoriaPesquisa (RhCategoriaProfissional categoriaPesquisa)
    {
        this.categoriaPesquisa = categoriaPesquisa;
    }

    public List<RhCategoriaProfissional> getCategoriaList ()
    {
        if (categoriaList == null)
        {
            return findAll();
        }

        return categoriaList;
    }

    public List<RhCategoriaProfissional> findAll ()
    {
        return categoriaProfissionalFacade.findAll();
    }

    public void pesquisar ()
    {
        categoriaList = categoriaProfissionalFacade.findAllContainsDescricao(categoriaPesquisa.getDescricao());
        Mensagem.sucessoMsg("Número de registros encontrados (" + categoriaList.size() + ")");
    }

    public String limpar ()
    {
        categoria = null;
        return "categoriaRh?faces-redirect=true";
    }

}
