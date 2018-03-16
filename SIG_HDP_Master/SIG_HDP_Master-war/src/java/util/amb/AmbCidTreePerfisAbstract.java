/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.amb;

import entidade.AmbCidPerfis;
import java.util.List;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sessao.AmbCidPerfisFacade;
import util.TreeNodeInterface;

/**
 *
 * @author aires
 */
public abstract class AmbCidTreePerfisAbstract implements TreeNodeInterface
{

    private AmbCidPerfisFacade ambCidPerfisFacade;
    protected TreeNode root, root2, selectedNode, prevSelectedNode;

    public AmbCidTreePerfisAbstract(AmbCidPerfisFacade ambCidPerfisFacade)
    {
        this.ambCidPerfisFacade = ambCidPerfisFacade;
        selectedNode = prevSelectedNode = null;
    }

    // Inicio da implementação default dos métodos da Interface TreeNodeInterface
    @Override
    public void onNodeSelect(NodeSelectEvent event)
    {
        if (selectedNode != null)
            selectedNode.setSelected(false);
        prevSelectedNode = selectedNode;
        selectedNode = event.getTreeNode();
        selectedNode.setSelected(true);
        String node = selectedNode.getData().toString();
        //System.out.    println("0: AmbCidTreePerfisAbstract.onNodeSelect()\tnode: " + node);
    }

    @Override
    public void onNodeUnSelect(NodeUnselectEvent event)
    {
        //System.out.    println("Node Data ::" + event.getTreeNode().getData() + " :: UnSelected");
    }

    @Override
    public void onNodeExpand(NodeExpandEvent event)
    {
        String node = event.getTreeNode().getData().toString();
        //System.out.    println("0: AmbCidTreePerfisAbstract.onNodeExpand()\tnode: " + node);
    }

    @Override
    public void onNodeCollapse(NodeCollapseEvent event)
    {
        String node = event.getTreeNode().getData().toString();
        //System.out.    println("0: AmbCidTreePerfisAbstract.onNodeCollapse()\tnode: " + node);
    }

    @Override
    public void initSelectedNode()
    {
    }
    // Fim da implementação default dos métodos da Interface TreeNodeInterface

    public List<AmbCidPerfis> obterListaAmbCidPerfisFilhos(TreeNode noPai)
    {
        // 88888888888888888888
        return this.ambCidPerfisFacade.obterListaAmbCidPerfisFilhos(noPai.getData().toString());
    }
    
    public void gerarTreeNodes(TreeNode noPai, List<String> listaAmbCidPerfisNomes)
    {
        /*
            perfisFilhos = obterPerfisFilhos(noPai)
            if (!perfisFilhos)
                retornar
            para cada perfil de perfisFilhos
                se ! listaAmbCidPerfisNomes contem perfil
                    continuar
                criar no(perfil, noPai)
                gerarTreeNodes(no, listaAmbCidPerfisNomes)
        */
//System.out.    println("0: AmbCidTreePerfisAbstract.gerarTreeNodes()");
        List<AmbCidPerfis> listaAmbCidPerfisFilhos = obterListaAmbCidPerfisFilhos(noPai);
//System.out.    println("1: AmbCidTreePerfisAbstract.gerarTreeNodes()");        
        if (listaAmbCidPerfisFilhos == null || listaAmbCidPerfisFilhos.size() == 0)
            return;
//System.out.    println("2: AmbCidTreePerfisAbstract.gerarTreeNodes()");        
        for (AmbCidPerfis  perfisFilho : listaAmbCidPerfisFilhos)
        {
//System.out.    println("3: AmbCidTreePerfisAbstract.gerarTreeNodes()");            
            if (!listaAmbCidPerfisNomes.contains(perfisFilho.getPkIdNome()))
                continue;
//System.out.    println("4: AmbCidTreePerfisAbstract.gerarTreeNodes()");            
            TreeNode treeNode = new DefaultTreeNode(perfisFilho.getPkIdNome(), noPai);
//System.out.    println("5: AmbCidTreePerfisAbstract.gerarTreeNodes()");            
            gerarTreeNodes(treeNode, listaAmbCidPerfisNomes);
//System.out.    println("6: AmbCidTreePerfisAbstract.gerarTreeNodes()");            
        }
//System.out.    println("7: AmbCidTreePerfisAbstract.gerarTreeNodes()");        
    }
    
    public void initRootFromDataBase(List<String> listaAmbCidPerfisNomes)
    {
        gerarTreeNodes(root2, listaAmbCidPerfisNomes);
    }
     
    public void initRoot(List<String> listaAmbCidPerfisNomes)
    {
        root = new DefaultTreeNode("Root", null);
        root2 = new DefaultTreeNode(util.amb.Defs.CID_10, root);
        if (listaAmbCidPerfisNomes == null)
            return;
        initRootFromDataBase(listaAmbCidPerfisNomes);
        initSelectedNode();
    }

    public TreeNode getRoot()
    {
        return root;
    }

    public void setRoot(TreeNode root)
    {
        this.root = root;
    }

    public AmbCidPerfisFacade getAmbCidPerfisFacade()
    {
        return ambCidPerfisFacade;
    }

    public void setAmbCidPerfisFacade(AmbCidPerfisFacade ambCidPerfisFacade)
    {
        this.ambCidPerfisFacade = ambCidPerfisFacade;
    }

    public TreeNode getRoot2()
    {
        return root2;
    }

    public void setRoot2(TreeNode root2)
    {
        this.root2 = root2;
    }

    public TreeNode getSelectedNode()
    {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode)
    {
        this.selectedNode = selectedNode;
    }

}
