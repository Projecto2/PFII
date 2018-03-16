/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean.segbean;

import entidade.SegFuncionalidade;
import entidade.SegPerfil;
import entidade.SegPerfilHasFuncionalidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sessao.SegFuncionalidadeFacade;
import sessao.SegPerfilFacade;
import sessao.SegPerfilHasFuncionalidadeFacade;

/**
 *
 * @author adalberto
 */
@ManagedBean
@ViewScoped
public class SegPermissaoFuncionalidadesBean implements Serializable
{

    @EJB
    private SegPerfilHasFuncionalidadeFacade perfilPermissoesFacade;
    @EJB
    private SegPerfilFacade perfilFacade;
    @EJB
    private SegFuncionalidadeFacade funcionalidadeFacade;

    private TreeNode root;
    private TreeNode[] nosSelecionados;
    private TreeNode[] checkboxSelectedTreeNodes;
    private int perfilSelecionado;
    private List<SegPerfilHasFuncionalidade> listaPermissao = new ArrayList();
    private List<SegPerfilHasFuncionalidade> removerListaPermissao = new ArrayList();
    private List<SegFuncionalidade> saveListaPermissao = new ArrayList();
    private List<SegFuncionalidade> listaFuncionalidade = new ArrayList();
    private List<SegFuncionalidade> funcionalidadesPermitidos = new ArrayList<>();

    @ManagedProperty(value = "#{segPerfilListarBean}")
    private SegPerfilListarBean segPerfilListarBean;

    public SegPermissaoFuncionalidadesBean()
    {
    }

    public TreeNode getRoot()
    {
        return root;
    }

    public void setRoot(TreeNode root)
    {
        this.root = root;
    }

    public TreeNode[] getNosSelecionados()
    {
        return nosSelecionados;
    }

    public SegPerfilListarBean getSegPerfilListarBean()
    {
        return segPerfilListarBean;
    }

    public void setSegPerfilListarBean(SegPerfilListarBean segPerfilListarBean)
    {
        this.segPerfilListarBean = segPerfilListarBean;
    }

    public void setNosSelecionados(TreeNode[] nosSelecionados)
    {
        this.nosSelecionados = nosSelecionados;
    }

    public int getPerfilSelecionado()
    {
        SegPerfil perfilListar = segPerfilListarBean.getPerfilEditar();
        System.out.println("perfilListar:"+perfilListar);
        if (perfilListar != null )
        {
            perfilSelecionado = perfilListar.getPkIdPerfil();
        }
        return perfilSelecionado;
    }

    public void setPerfilSelecionado(int perfilSelecionado)
    {
        this.perfilSelecionado = perfilSelecionado;
    }

    public List<SegPerfilHasFuncionalidade> getListaPermissao()
    {
       if(listaPermissao == null)
          listaPermissao = new ArrayList<>();
        return listaPermissao;
    }

    public void setListaPermissao(List<SegPerfilHasFuncionalidade> listaPermissao)
    {
        this.listaPermissao = listaPermissao;
    }

    public List<SegFuncionalidade> getListaFuncionalidade()
    {
        return listaFuncionalidade;
    }

    public void setListaFuncionalidade(List<SegFuncionalidade> listaFuncionalidade)
    {
        this.listaFuncionalidade = listaFuncionalidade;
    }

    public List<SegFuncionalidade> getFuncionalidadesPermitidos()
    {
        return funcionalidadesPermitidos;
    }

    public void setFuncionalidadesPermitidos(List<SegFuncionalidade> funcionalidadesPermitidos)
    {
        this.funcionalidadesPermitidos = funcionalidadesPermitidos;
    }

    public List<SegPerfil> getListaPerfil()
    {
        return perfilFacade.findAll();
    }

    private boolean isPermitido(SegFuncionalidade funcionalidade)
    {
        for (SegFuncionalidade f : funcionalidadesPermitidos)
        {
            if (f.equals(funcionalidade))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isFilho(String[] indicesPai, String[] indicesFilho)
    {
        boolean isFilho = false;
        int i = 0;
        if (indicesPai.length == indicesFilho.length - 1)
        {
            for (String string : indicesPai)
            {
                isFilho = string.equals(indicesFilho[i]);
                if (!isFilho)
                {
                    break;
                }
                i++;
            }
        }
        return isFilho;
    }

    private void carregarFilhos(List<SegFuncionalidade> funcionalidades, SegFuncionalidade funcionalidade, TreeNode treeNode)
    {
        for (SegFuncionalidade f : funcionalidades)
        {

            if (f.getFkIdFuncionalidadePai() != null)
            {
                int indicesPai = funcionalidade.getPkIdFuncionalidade();
                int indicesFilho = f.getFkIdFuncionalidadePai().getPkIdFuncionalidade();

                TreeNode tr;
                if (indicesFilho == indicesPai)
                {
                    //if (isFilho(indicesPai, indicesFilho))
                    //{
                    //if (f.getUrlPadrao().equals(""))
                    //{
                    tr = new DefaultTreeNode(f, treeNode);
                    if (isPermitido(f))
                    {
                        tr.setSelected(true);
                    }
                    carregarFilhos(funcionalidades, f, tr);
                    //}
                    //else
                    // {
                    //tr = new DefaultTreeNode(f, treeNode);

                    //}
                    //}
                }
            }
        }
    }

    public void carregaPermissoesPerfil()
    {
        if (perfilSelecionado != 0)
        {
            root = new DefaultTreeNode("Qp", null);
            funcionalidadesPermitidos = new ArrayList<SegFuncionalidade>();
            listaPermissao = perfilPermissoesFacade.buscaPorPerfil(perfilSelecionado);
            listaFuncionalidade = funcionalidadeFacade.findAll();

            if (listaPermissao != null && listaFuncionalidade != null)
            {
                for (SegPerfilHasFuncionalidade permissao : listaPermissao)
                {
                    funcionalidadesPermitidos.add(permissao.getFkIdFuncionalidade());
                }
                for (SegFuncionalidade funcionalidade : listaFuncionalidade)
                {
                    if (funcionalidade.getFkIdFuncionalidadePai() == null) // se vazio é pai
                    {
                        TreeNode treeNode = new DefaultTreeNode(funcionalidade, root);
                        if (isPermitido(funcionalidade))
                        {
                            treeNode.setSelected(true);
                        }
                        carregarFilhos(listaFuncionalidade, funcionalidade, treeNode);
                    }
                }
            }
            else
            {
                root = new DefaultTreeNode("Qp", null);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Nenhuma funcionalidades encontrada"));

            }
        }
        else
        {
            root = new DefaultTreeNode("Qp", null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Nenhum perfil selecionado"));

        }
    }

    public void salvaPermissoes()
    {
        if (perfilSelecionado == 0)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum perfil Selecionado", "Erro"));
        }
        else
        {
            removePermissao(perfilSelecionado);
            savePermissao(perfilSelecionado);
            salvaPai();
            carregaPermissoesPerfil();
            //inicializar
            removerListaPermissao = new ArrayList<>();
            saveListaPermissao = new ArrayList<>();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Permissões Salvas", "Permissões Salvas"));
        }
    }

    // excluir as funcionalidades na tabela PerfilHasFuncioalidades 
    //que nao constam nos nós nao selecionadas do perfil selecionado
    public void removePermissao(int perfilSelecionado)
    {
        listaPermissao = perfilPermissoesFacade.buscaPorPerfil(perfilSelecionado);

        for (SegPerfilHasFuncionalidade perfilPermissoes : listaPermissao)
        {
            boolean found = false;
            for (TreeNode no : nosSelecionados)
            {
                System.out.println("no.getData():" + no.getData());
                SegFuncionalidade funcionalidadeSelecionado = (SegFuncionalidade) no.getData();
                if (perfilPermissoes.getFkIdFuncionalidade().getPkIdFuncionalidade() == funcionalidadeSelecionado.getPkIdFuncionalidade())
                {
                    found = true;
                }
            }
            if (found == false)
            {
                removerListaPermissao.add(perfilPermissoes);
            }
        }
        //remover
        for (SegPerfilHasFuncionalidade removepermissao : removerListaPermissao)
        {
            System.out.println("Removeu da BD:" + removepermissao.getFkIdFuncionalidade().getNome());
            perfilPermissoesFacade.remove(removepermissao);

        }

    }

    //save novos nós selecionada para BD
    public void savePermissao(int perfilSelecionado)
    {
        listaPermissao = perfilPermissoesFacade.buscaPorPerfil(perfilSelecionado);
        for (TreeNode no : nosSelecionados)
        {
            if (no.isLeaf())
            {
                boolean found = false;
                SegFuncionalidade funcionalidadeSelecionado = (SegFuncionalidade) no.getData();
                System.out.println("listaPermissao: "+listaPermissao);
                System.out.println("getListaPermissao: "+getListaPermissao());
                for (SegPerfilHasFuncionalidade perfilPermissoes : getListaPermissao())
                {
                    if (funcionalidadeSelecionado.getPkIdFuncionalidade() == perfilPermissoes.getFkIdFuncionalidade().getPkIdFuncionalidade())
                    {
                        found = true;
                    }
                }
                if (found == false)
                {
                    //adicionar o no na lista
                    saveListaPermissao.add(funcionalidadeSelecionado);
                }
            }
        }

        //remover
        for (SegFuncionalidade func : saveListaPermissao)
        {
            SegPerfilHasFuncionalidade permissao = new SegPerfilHasFuncionalidade();
            permissao.setFkIdFuncionalidade(func);
            permissao.setFkIdPerfil(new SegPerfil(perfilSelecionado));
            System.out.println("Salvou na BD:" + func.getNome());
            perfilPermissoesFacade.create(permissao);
        }

    }

    public void salvaPai()
    {
        for (TreeNode no : nosSelecionados)
        {
            TreeNode tr = no.getParent();
            if (!tr.equals(root))
            {
                SegPerfilHasFuncionalidade pf;
                SegFuncionalidade funcionalidade = (SegFuncionalidade) tr.getData();
                pf = perfilPermissoesFacade.buscaPorFuncionalidadePerfil(funcionalidade.getPkIdFuncionalidade(), perfilSelecionado);
                if (pf == null)
                {
                    SegPerfilHasFuncionalidade permissao = new SegPerfilHasFuncionalidade();
                    permissao.setFkIdPerfil(new SegPerfil(perfilSelecionado));
                    permissao.setFkIdFuncionalidade(funcionalidade);
                    perfilPermissoesFacade.create(permissao);
                }
                // salvaPai(tr);
            }
        }
    }

    public void setCheckboxSelectedTreeNodes(TreeNode[] checkboxSelectedTreeNodes)
    {
        this.checkboxSelectedTreeNodes = checkboxSelectedTreeNodes;
    }

    public void onNodeSelect(NodeSelectEvent event)
    {
        System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: Selected");
    }

    public void onNodeUnSelect(NodeUnselectEvent event)
    {
        System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: UnSelected");
    }

    public void onNodeExpand(NodeExpandEvent event)
    {
        System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: Expanded");
    }

    public void onNodeCollapse(NodeCollapseEvent event)
    {
        System.out.println("Node Data ::" + event.getTreeNode().getData() + " :: Collapsed");
    }
}
